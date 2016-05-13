package fizzion.tenebrae.ui;

import engine.components.RectRenderer;
import engine.core.GameObject;
import engine.math.Vector2;
import engine.rendering.Shader;
import engine.rendering.Texture;
import engine.utility.Util;

public class Button extends GameObject
{

	private ButtonCallback callback;
	private ClickZone clickZone;
	
	private boolean lastHovered;
	
	public Button(ButtonCallback callback, ClickZone clickZone, Texture texture, Shader shader)
	{
		this.callback = callback;
		this.clickZone = clickZone;
		RectRenderer renderer = new RectRenderer(Util.pixelDToGL(new Vector2(clickZone.getWidth(), clickZone.getHeight())), texture);
		renderer.setShader(shader);
		addAllComponents(renderer, clickZone);
		getTransform().setGlobalPosition(Util.pixelCToGL(new Vector2(clickZone.getX() + clickZone.getWidth() / 2, clickZone.getY() + clickZone.getHeight() / 2)));
	}
	
	public void update()
	{
		if(clickZone.isHovered())
		{
			if(!lastHovered)
			{
				callback.hovered();
				lastHovered = true;
			}
		}
		else
		{
			if(lastHovered)
			{
				callback.unhovered();
				lastHovered = false;
			}
		}
		
		if(clickZone.isClicked())
		{
			callback.clicked();
		}
	}
	
	public interface ButtonCallback
	{
		public void hovered();
		public void unhovered();
		public void clicked();
	}
	
}
