package fizzion.tenebrae.entity;

import engine.components.RectRenderer;
import engine.components.RectRenderer.UniformConfig;
import engine.core.Input;
import engine.core.InputMap;
import engine.math.Vector2i;
import engine.physics.AABBCollider;
import engine.rendering.Color;
import engine.rendering.Shader;
import engine.rendering.Texture;

public class Player extends Entity
{

	private Vector2i velocity;
	
	private InputMap input;
	
	public Player()
	{
		super(100);
		
		Texture t = new Texture("tiles/001.png");
		
		RectRenderer player = new RectRenderer(new Vector2i(64, 64), t);
		player.setAllowLighting(false);
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
	}
	
	public void input()
	{
		readMovement();
	}
	
	public void update()
	{
		getTransform().translateBy(velocity);
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
