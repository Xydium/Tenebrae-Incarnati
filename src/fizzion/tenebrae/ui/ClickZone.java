package fizzion.tenebrae.ui;

import java.util.ArrayList;

import engine.core.GameComponent;
import engine.core.Input;
import engine.math.Vector2i;

/**
 * A clickable area with methods for determining if
 * the mouse is currently within that area and if it
 * has been clicked.
 * 
 * @author Lenny Litvak
 * @author Tim Hornick
 */
public class ClickZone extends GameComponent 
{
	private ArrayList<ClickZoneListener> czListeners;
	
	private Vector2i size;
	
	private boolean lastInside;
	private boolean inside;
	private boolean clicked;
	
	/**
	 * Creates a click size with the given width and height
	 * in pixels
	 * 
	 * @param width
	 * @param height
	 */
	public ClickZone(float width, float height)
	{
		size = new Vector2i(width, height);
		czListeners = new ArrayList<ClickZoneListener>();
		lastInside = false;
		inside = false;
		clicked = false;
	}
	
	public void input()
	{
		inside = isMouseInside();
		clicked = inside && Input.getMouseDown(Input.MOUSE_LEFT);
	}
	
	public void update()
	{
		for (ClickZoneListener czl : czListeners)
		{
			if (clicked)
			{
				czl.onMouseClicked();
			}
			
			if (!lastInside && inside)
			{
				czl.onMouseEnter();
			}
			else if (lastInside && !inside)
			{
				czl.onMouseLeave();
			}
		}
		
		lastInside = inside;
	}
	
	public void addListener(ClickZoneListener czl)
	{
		czListeners.add(czl);
	}
	
	private boolean isMouseInside()
	{
		Vector2i m = Input.getMousePosition();
		Vector2i pos = getTransform().getGlobalPosition();
		
		return m.getX() >= pos.getX() && m.getY() >= pos.getY()
						&& m.getX() <= pos.getX() + size.getX()
						&& m.getY() <= pos.getY() + size.getY();
	}
	
	public Vector2i getSize()
	{
		return size;
	}
}
