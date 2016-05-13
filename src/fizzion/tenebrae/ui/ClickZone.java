package fizzion.tenebrae.ui;

import engine.core.GameComponent;
import engine.core.Input;
import engine.math.Vector2;

/**
 * A clickable area with methods for determining if
 * the mouse is currently within that area and if it
 * has been clicked.
 * 
 * @author Tim Hornick
 */
public class ClickZone extends GameComponent 
{

	private int topLeftX; 
	private int	topLeftY;
	private int bottomRightX; 
	private int bottomRightY;
	
	private boolean hovered;
	private boolean clicked;
	
	/**
	 * Constructs a rectangular area starting at the corner
	 * represented by x,y and stretching to the corner
	 * at x + width, y + height.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
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
	
	/**
	 * @return if the mouse is in the click zone
	 */
	public boolean isHovered()
	{
		return hovered;
	}
	
	/**
	 * @return if the mouse is in the click zone and clicked
	 */
	public boolean isClicked()
	{
		return clicked;
	}
	
	public int getX() {
		return topLeftX;
	}

	public int getY() {
		return topLeftY;
	}

	public int getWidth() {
		return bottomRightX - getX();
	}

	public int getHeight() {
		return bottomRightY - getY();
	}
	
}
