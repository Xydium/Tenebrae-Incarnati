package fizzion.tenebrae.ui;

import engine.core.GameComponent;
import engine.core.Input;
import engine.math.Vector2;

public class ClickZone extends GameComponent 
{

	private int topLeftX, topLeftY, bottomRightX, bottomRightY;
	
	private boolean hovered;
	private boolean clicked;
	
	public ClickZone(float x, float y, float width, float height)
	{
		this.topLeftX = (int) x;
		this.topLeftY = (int) y;
		this.bottomRightX = (int) (x + width);
		this.bottomRightY = (int) (y + height);
	}
	
	public void input()
	{
		hovered = isMouseInside();
		clicked = hovered && Input.getMouseDown(Input.MOUSE_LEFT);
	}
	
	private boolean isMouseInside()
	{
		Vector2 m = Input.getMousePosition();
		return m.getX() >= topLeftX && m.getY() >= topLeftY && m.getX() <= bottomRightX && m.getY() <= bottomRightY;
	}
	
	public boolean isHovered()
	{
		return hovered;
	}
	
	public boolean isClicked()
	{
		return clicked;
	}
	
}
