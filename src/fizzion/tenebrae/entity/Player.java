package fizzion.tenebrae.entity;

import engine.components.RectRenderer;
import engine.components.RectRenderer.UniformConfig;
import engine.core.GameObject;
import engine.core.Input;
import engine.math.Vector2;
import engine.physics.AABBCollider;
import engine.rendering.Color;
import engine.rendering.Rectangle;
import engine.rendering.Shader;
import engine.rendering.Texture;
import engine.utility.Log;
import engine.utility.Util;

public class Player extends GameObject
{

	private Vector2 velocity;
	private AABBCollider c;
	
	public Player()
	{
		Texture t = new Texture("tiles/001.png");
		RectRenderer player = new RectRenderer(Util.pixelDToGL(new Vector2(64f, 64f)), t);
		player.setAllowLighting(false);
		Shader s = new Shader("color-shader");
		player.setShader(s);
		player.setUniformConfig(new UniformConfig() {
			public void setUniforms(Shader s) {
				s.setUniform("color", new Color(1.0f, 0.0f, 0.0f, 1.0f));
			}
		});
		c = new AABBCollider(new Rectangle(new Vector2(64f, 64f)));
		addAllComponents(player, c);
	}
	
	public void input()
	{
		velocity = new Vector2(0, 0);
		
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
		
		velocity = velocity.mul(0.1f);
	}
	
	public void update()
	{
		getTransform().translateBy(velocity);
		Log.debug(getTransform().getPosition().toString());
		Log.debug(velocity.toString());
	}
	
}
