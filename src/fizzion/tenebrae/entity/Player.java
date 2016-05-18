package fizzion.tenebrae.entity;

import engine.components.RectRenderer;
import engine.components.RectRenderer.UniformConfig;
import engine.core.Input;
import engine.math.Vector2i;
import engine.physics.AABBCollider;
import engine.rendering.Color;
import engine.rendering.Shader;
import engine.rendering.Texture;

public class Player extends Entity
{

	private Vector2i velocity;
	private AABBCollider c;
	
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
		
		c = new AABBCollider(new Vector2i(64, 64));
		setCollider(c);
		addAllComponents(player, c);
		velocity = new Vector2i();
	}
	
	public void input()
	{	
		velocity.setX(0);
		velocity.setY(0);
		
		if(Input.getKey(Input.KEY_LEFT) || Input.getKey(Input.KEY_A))
		{
			velocity.setX(-5);
		}
		else if(Input.getKey(Input.KEY_RIGHT) || Input.getKey(Input.KEY_D))
		{
			velocity.setX(5);
		}
		if(Input.getKey(Input.KEY_UP) || Input.getKey(Input.KEY_W))
		{
			velocity.setY(-5);
		}
		else if(Input.getKey(Input.KEY_DOWN) || Input.getKey(Input.KEY_S))
		{
			velocity.setY(5);
		}
	}
	
	public void update()
	{
		getTransform().translateBy(velocity);
	}
	
}
