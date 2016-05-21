package fizzion.tenebrae.ui;

import engine.components.RectRenderer;
import engine.components.RectRenderer.UniformConfig;
import engine.core.GameObject;
import engine.math.Mathf;
import engine.math.Vector2i;
import engine.rendering.Color;
import engine.rendering.Shader;
import fizzion.tenebrae.entities.Entity;

public class HealthBar extends GameObject
{
	private static final float LERP_RATE = 0.2f;
	
	private Entity entity;
	private Vector2i size;
	
	private RectRenderer bar;
	
	private float currentSize;
	private float goalSize;
	
	public HealthBar(Entity entity, Vector2i size)
	{
		this.entity = entity;
		this.size = size;
		
		bar = new RectRenderer(size);
		bar.setAllowLighting(false);
		bar.setShader(new Shader("color-shader"));
		
		bar.setUniformConfig(new UniformConfig()
		{
			public void setUniforms(Shader s)
			{
				s.setUniform("color", new Color(1, 0, 0));
			}
		});
		
		RectRenderer tray = new RectRenderer(size);
		tray.setAllowLighting(false);
		tray.setShader(new Shader("color-shader"));
		
		tray.setUniformConfig(new UniformConfig()
		{
			public void setUniforms(Shader s)
			{
				s.setUniform("color", new Color(.2f, .2f, .2f));
			}
		});
		
		addAllComponents(tray, bar);
	}
	
	public void update()
	{
		goalSize = entity.getHealth() / entity.getMaxHealth() * size.getX();
		currentSize = Mathf.lerp(currentSize, goalSize, LERP_RATE);
		
		if (Mathf.abs(goalSize - currentSize) > 0.01f)
		{
			bar.setSize(new Vector2i((int)(currentSize + 0.5f), size.getY()));
		}
	}
	
	public void setEntity(Entity entity)
	{
		this.entity = entity;
	}
	
	public Entity getEntity()
	{
		return entity;
	}
}
