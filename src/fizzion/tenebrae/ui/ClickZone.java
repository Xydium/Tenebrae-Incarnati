package fizzion.tenebrae.ui;

import engine.core.GameComponent;
import engine.core.Input;
import engine.math.Vector2i;

/**
 * A clickable area with methods for determining if
 * the mouse is currently within that area and if it
 * has been clicked.
 * 
 * @author Tim Hornick
 */
public class ClickZone extends GameComponent 
{

	private int bottomLeftX; 
	private int	bottomLeftY;
	private int topRightX; 
	private int topRightY;
	
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
		this.bottomLeftX = (int) x;
		this.bottomLeftY = (int) y;
		this.topRightX = (int) (x + width);
		this.topRightY = (int) (y + height);
	}
	
	public void input()
	{
		hovered = isMouseInside();
		clicked = hovered && Input.getMouseDown(Input.MOUSE_LEFT);
	}
	
	private boolean isMouseInside()
	{
		Vector2i m = Input.getMousePosition();
		return m.getX() >= bottomLeftX && m.getY() >= bottomLeftY && m.getX() <= topRightX && m.getY() <= topRightY;
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
		return bottomLeftX;
	}

	public int getY() {
		return bottomLeftY;
	}

	public int getWidth() {
		return topRightX - getX();
	}

	public int getHeight() {
		return topRightY - getY();
	}
	
}
