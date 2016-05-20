package fizzion.tenebrae.entities;

import engine.collisions.AABBCollider;
import engine.components.RectRenderer;
import engine.components.RectRenderer.UniformConfig;
import engine.core.Input;
import engine.core.InputMap;
import engine.math.Vector2i;
import engine.rendering.Color;
import engine.rendering.Shader;
import engine.rendering.Texture;
import fizzion.tenebrae.map.Dungeon;

public class Player extends Entity
{
	private static final float OVERLAY_REFRESH_SPEED = 5;
	
	private Vector2i velocity;
	
	private InputMap input;
	
	private float overlayPercent;
	
	public Player(Dungeon dungeon)
	{
		super(100, dungeon);
		
		Texture t = new Texture("tiles/001.png");
		
		RectRenderer player = new RectRenderer(new Vector2i(64, 64), t);
		//player.setAllowLighting(false);
		Shader s = new Shader("color-shader");
		player.setShader(s);
		player.setUniformConfig(new UniformConfig() {
			public void setUniforms(Shader s) {
				s.setUniform("color", new Color(1.0f, 0.0f, 0.0f, 1.0f));
			}
		});
		
		AABBCollider c = new AABBCollider(new Vector2i(64, 64));
		setCollider(c);
		addAllComponents(player, c);
		velocity = new Vector2i();
		
		input = new InputMap();
		input.addKey("move_left", Input.KEY_LEFT, Input.KEY_A);
		input.addKey("move_right", Input.KEY_RIGHT, Input.KEY_D);
		input.addKey("move_up", Input.KEY_UP, Input.KEY_W);
		input.addKey("move_down", Input.KEY_DOWN, Input.KEY_S);
		
		overlayPercent = 0.f;
	}
	
	public void input()
	{
		readMovement();
		
		if (Input.getMouseDown(Input.MOUSE_LEFT))
		{
			overlayPercent += 0.25f;
		}
	}
	
	public void update()
	{
		getTransform().translateBy(velocity);
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
			velocity.setX(-5);
		}
		if(right || (stillRight && !stillLeft))
		{
			velocity.setX(5);
		}
		if(!stillLeft && !stillRight)
		{
			velocity.setX(0);
		}
		
		if(up || (stillUp && !stillDown))
		{
			velocity.setY(-5);
		}
		if(down || (stillDown && !stillUp))
		{
			velocity.setY(5);
		}
		if(!stillUp && !stillDown) 
		{
			velocity.setY(0);
		}
	}
	
}
