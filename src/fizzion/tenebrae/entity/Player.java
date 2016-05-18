package fizzion.tenebrae.entity;

import engine.components.RectRenderer;
import engine.components.RectRenderer.UniformConfig;
import engine.core.GameObject;
import engine.core.Input;
import engine.math.Vector2f;
import engine.math.Vector2i;
import engine.physics.AABBCollider;
import engine.rendering.Color;
import engine.rendering.Shader;
import engine.rendering.Texture;

public class Player extends GameObject
{

	private Vector2i velocity;
	private AABBCollider c;
	
	public Player()
	{
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
		addAllComponents(player, c);
		getTransform().setGlobalPosition(512f, 288f);
		velocity = new Vector2i();
	}
	
	public void input()
	{	
		velocity.setX(0); velocity.setY(0);
		
		if(Input.getKey(Input.KEY_LEFT))
		{
			velocity.setX(-5);
		}
		else if(Input.getKey(Input.KEY_RIGHT))
		{
			velocity.setX(5);
		}
		if(Input.getKey(Input.KEY_UP))
		{
			velocity.setY(-5);
		}
		else if(Input.getKey(Input.KEY_DOWN))
		{
			velocity.setY(5);
		}
	}
	
	public void update()
	{
		getTransform().translateBy(velocity);
	}
	
}
