package fizzion.tenebrae.ui;

import engine.components.RectRenderer;
import engine.core.GameObject;
import engine.math.Vector2i;
import engine.rendering.Shader;
import engine.rendering.Texture;

public class Button extends GameObject
{
	private ClickZone clickZone;
	
	public Button(int x, int y, int width, int height)
	{
		this(x, y, width, height, new Texture("tiles/000.png"));
	}
	
	public Button(int x, int y, int width, int height, Texture texture)
	{
		clickZone = new ClickZone(width, height);
		
		RectRenderer renderer = new RectRenderer(new Vector2i(width, height), texture);
		renderer.setShader(new Shader("texture-shader"));
		renderer.setAllowLighting(false);
		addAllComponents(renderer, clickZone);
		
		getTransform().setPosition(x, y);
	}
	
	public void addListener(ClickZoneListener czl)
	{
		clickZone.addListener(czl);
	}
}
