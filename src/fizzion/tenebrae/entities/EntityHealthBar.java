package fizzion.tenebrae.entities;

import engine.components.RectRenderer;
import engine.components.UniformConfig;
import engine.core.GameObject;
import engine.math.Mathf;
import engine.math.Vector2i;
import engine.rendering.Color;
import engine.rendering.Shader;

public class EntityHealthBar extends GameObject
{
	private static final float LERP_RATE = 0.2f;
	private static final int BAR_SIZE = 64;
	
	private Entity entity;
	
	private RectRenderer bar;
	
	private float currentSize;
	private float goalSize;
	
	public EntityHealthBar(Entity entity)
	{
		this.entity = entity;
		
		bar = new RectRenderer(new Vector2i(BAR_SIZE, 5));
		bar.setShader(new Shader("color-shader"));
		
		bar.setUniformConfig(new UniformConfig()
		{

			public void setUniforms(Shader s)
			{
				s.setUniform("color", new Color(1.f, 0.f, 0.f));
			}
			
		});
		
		addComponent(bar);
	}
	
	public void update()
	{
		getTransform().setGlobalRotation(0);
		
		goalSize = entity.getHealth() / entity.getMaxHealth() * BAR_SIZE;
		currentSize = Mathf.lerp(currentSize, goalSize, LERP_RATE);
		
		if (Mathf.abs(goalSize - currentSize) > 0.01f)
		{
			bar.setSize(new Vector2i((int)(currentSize + 0.5f), bar.getSize().getY()));
		}
	}
}
