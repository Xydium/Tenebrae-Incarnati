package fizzion.tenebrae.entities;

import engine.collisions.AABBCollider;
import engine.collisions.Collider;
import engine.components.RectRenderer;
import engine.components.UniformConfig;
import engine.core.Input;
import engine.core.InputMap;
import engine.math.Vector2i;
import engine.rendering.Color;
import engine.rendering.Shader;
import engine.rendering.Texture;
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
	
	private Vector2i velocity;
	
	private InputMap input;
	
	private float overlayPercent;
	
	private boolean died;
	
	public Player(Dungeon dungeon)
	{
		super(100, dungeon);
		
		died = false;
		
		Texture t = new Texture("tiles/001.png");
		
		RectRenderer player = new RectRenderer(new Vector2i(64, 64), t);
		Shader s = new Shader("color-shader");
		player.setShader(s);
		
		player.setUniformConfig(new UniformConfig()
		{
			public void setUniforms(Shader s)
			{
				s.setUniform("color", new Color(1.0f, 0.0f, 0.0f, 1.0f));
			}
		});
		
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
			break;
		case MOVING:
			readMovement();
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
				getTransform().translateBy(new Vector2i(0, CHARGE_SPEED));
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
		
		getTransform().lookAt(velocity.add(getTransform().getGlobalPosition()));
		
		if(input.getKeyDown("charge") && !velocity.equals(new Vector2i(0, 0))) {
			movementState = CHARGING;
			chargeStart = Time.getTime();
		} else if(velocity.equals(new Vector2i(0, 0))) {
			movementState = IDLE;
		} else {
			movementState = MOVING;
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
	
}
