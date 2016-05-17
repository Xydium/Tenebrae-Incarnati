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

	private Vector2f velocity;
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
		//c = new AABBCollider(new Rectangle(new Vector2(64f, 64f)));
		addAllComponents(player);
	}
	
	public void input()
	{
		velocity = new Vector2f(0, 0);
		
		if(Input.getKey(Input.KEY_LEFT))
		{
			velocity.setX(-0.05f);
		}
		else if(Input.getKey(Input.KEY_RIGHT))
		{
			velocity.setX(0.05f);
		}
		if(Input.getKey(Input.KEY_UP))
		{
			velocity.setY(0.05f);
		}
		else if(Input.getKey(Input.KEY_DOWN))
		{
			velocity.setY(-0.05f);
		}
		
		if(velocity.getX() != 0 && velocity.getY() != 0) {
			velocity = velocity.mul(0.07f);
		} else {
			velocity = velocity.mul(0.1f);
		}
	}
	
	public void update()
	{
		getTransform().translateBy(new Vector2i(velocity));
	}
	
}
