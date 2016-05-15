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
	
	public Button(int x, int y, int width, int height, Texture texture, ButtonCallback c)
	{
		this.callback = c;
		this.clickZone = new ClickZone(x, y, width, height);
		RectRenderer renderer = new RectRenderer(Util.pixelDToGL(new Vector2(width, height)), texture);
		renderer.setShader(new Shader("texture-shader"));
		renderer.setAllowLighting(false);
		addAllComponents(renderer, clickZone);
		Vector2 position = new Vector2();
		position.setX(x + width / 2);
		position.setY(y + height);
		Util.pixelCToGL(position);
		getTransform().setPosition(position);
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
