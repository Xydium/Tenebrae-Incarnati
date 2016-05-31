package fizzion.tenebrae.entities;

import java.util.ArrayList;

import engine.collisions.AABBCollider;
import engine.collisions.Collider;
import engine.components.RectRenderer;
import engine.core.Input;
import engine.core.InputMap;
import engine.math.Mathf;
import engine.math.Transform;
import engine.math.Vector2f;
import engine.math.Vector2i;
import engine.rendering.Shader;
import engine.rendering.Texture;
import engine.utility.Log;
import engine.utility.Time;
import fizzion.tenebrae.map.Dungeon;
import fizzion.tenebrae.ui.DeathScreen;

/**
 * 
 * @author Lenny Litvak
 * @author Tim Hornick
 *
 */
public class Player extends Entity
{
	private static final float OVERLAY_REFRESH_SPEED = 1;
	private static final int MOVE_SPEED = 5;
	private static final int CHARGE_SPEED = 20;
	private static final int MELEE_RANGE = 200;
	private static final float TARGETING_ARC_SIZE = (float) Math.PI / 3;
	
	private Vector2i velocity;
	private Vector2f lastNonZeroVel;
	
	private InputMap input;
	
	private float overlayPercent;
	
	private boolean died;
	
	public Player(Dungeon dungeon)
	{
		super(100, dungeon);
		
		died = false;
		
		Texture t = new Texture("entities/Player.png");
		
		RectRenderer player = new RectRenderer(new Vector2i(64, 64), t);
		Shader s = new Shader("texture-shader");
		player.setShader(s);
		
		AABBCollider c = new AABBCollider(new Vector2i(64, 64));
		setCollider(c);
		addAllComponents(player, c);
		
		addCollisionListener(new CollisionListener()
		{
			public void onCollision(Collider other)
			{
				if(movementState == CHARGING) {
					if(other.getParent() instanceof Enemy) {
						getDungeon().getCurrentRoom().getEnemies().remove(other.getParent());
						getDungeon().remove(other.getParent());
						overlayPercent += .25;
					}
					movementState = IDLE;
				}
				other.resolveCollision(getCollider());
			}
			
		});
		
		velocity = new Vector2i();
		
		input = new InputMap();
		input.addKey("move_left", Input.KEY_LEFT, Input.KEY_A);
		input.addKey("move_right", Input.KEY_RIGHT, Input.KEY_D);
		input.addKey("move_up", Input.KEY_UP, Input.KEY_W);
		input.addKey("move_down", Input.KEY_DOWN, Input.KEY_S);
		input.addKey("charge", Input.KEY_SPACE);
		input.addKey("melee", Input.KEY_LSHIFT);
		
		overlayPercent = 0.f;
	}
	
	private int movementState;
	static final int IDLE = 0, MOVING = 1, CHARGING = 2;
	private double chargeStart;
	public void input()
	{
		switch(movementState)
		{
		case IDLE:
			readMovement();
			attemptAttack();
			break;
		case MOVING:
			readMovement();
			attemptAttack();
			break;
		case CHARGING:
			if (input.getKeyDown("charge") || overlayPercent > 1 || Time.getTime() - chargeStart > 0.2)
			{
				movementState = IDLE;
			}
			if(input.getKeyDown("charge") || Time.getTime() - chargeStart > 0.5) movementState = IDLE;
			break;
		}
	}
	
	public void update()
	{
		if (getHealth() <= 0.0001f && !died)
		{
			died = true;
			//GlobalAudio.playSound("death_player");
			getDungeon().getRootObject().addChildSafely(new DeathScreen(getDungeon()));
		}
		
		if (died)
		{
			return;
		}
		
		switch(movementState)
		{
			case IDLE:
				break;
			case MOVING:
				getTransform().setPosition(getTransform().getPosition().add(velocity));
				break;
			case CHARGING:
				overlayPercent += 0.01;
				getTransform().translateBy(velocity.div(MOVE_SPEED).mul(CHARGE_SPEED));
				break;
		}
		
		if(Time.getTime() - lastAttacked > 5) setHealth(getHealth() + 10f);
		
		getApplication().getRenderingEngine().setOverlayBrightness(1.f - overlayPercent);
		
		if (overlayPercent >= OVERLAY_REFRESH_SPEED * getApplication().getDeltaTime())
		{
			overlayPercent -= OVERLAY_REFRESH_SPEED * (float)getApplication().getDeltaTime();
		}
		else
		{
			overlayPercent = 0.f;
		}
	}
	
	private boolean left, right, up, down, stillLeft, stillRight, stillUp, stillDown;
	private void readMovement()
	{
		if(overlayPercent > 1) return;
		left = input.getKeyDown("move_left");
		right = input.getKeyDown("move_right");
		up = input.getKeyDown("move_up");
		down = input.getKeyDown("move_down");
		
		stillLeft = input.getKey("move_left");
		stillRight = input.getKey("move_right");
		stillUp = input.getKey("move_up");
		stillDown = input.getKey("move_down");
		
		if(left || (stillLeft && !stillRight))
		{
			velocity.setX(-MOVE_SPEED);
		}
		if(right || (stillRight && !stillLeft))
		{
			velocity.setX(MOVE_SPEED);
		}
		if(!stillLeft && !stillRight)
		{
			velocity.setX(0);
		}
		
		if(up || (stillUp && !stillDown))
		{
			velocity.setY(-MOVE_SPEED);
		}
		if(down || (stillDown && !stillUp))
		{
			velocity.setY(MOVE_SPEED);
		}
		if(!stillUp && !stillDown) 
		{
			velocity.setY(0);
		}
		
		if(input.getKeyDown("charge") && !velocity.equals(new Vector2i(0, 0))) {
			movementState = CHARGING;
			chargeStart = Time.getTime();
		} else if(velocity.equals(new Vector2i(0, 0))) {
			movementState = IDLE;
		} else {
			movementState = MOVING;
			lastNonZeroVel = new Vector2f(velocity);
		}
	}
	
	public int getMovementState()
	{
		return movementState;
	}
	
	private double lastAttacked = Time.getTime();
	public void setHealth(float health) {
		super.setHealth(health);
		lastAttacked = Time.getTime();
	}
	
	private void attemptAttack()
	{
		if(!input.getKeyDown("melee")) return;
		ArrayList<Enemy> enemies = getDungeon().getCurrentRoom().getEnemies();
		for(Enemy e : enemies)
		{
			Transform et = e.getTransform();
			Transform pt = getTransform();
			
			Vector2f lookVec = new Vector2f(et.getGlobalPosition()).sub(new Vector2f(pt.getGlobalPosition()));
			Vector2f normalizedVelocity = lastNonZeroVel.getUnit();
			
			float diffAngle = (float)Math.abs(Math.acos(lookVec.getUnit().dot(normalizedVelocity)));
			//Log.debug("diffAngle = " + Math.toDegrees(diffAngle));
			
			if (diffAngle <= TARGETING_ARC_SIZE && pt.distanceTo(et) < MELEE_RANGE)
			{
				e.setHealth(e.getHealth() - 75);
				break;
			}
		}
	}
	
}
