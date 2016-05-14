package fizzion.tenebrae.map;

public class Room 
{
	
	public static final int WIDTH = 16;
	public static final int HEIGHT = 9;
	
	private Room above;
	private Room below;
	private Room left;
	private Room right;
	
	private Tilemap floor;
	private Tilemap decor;
	
	public Room getAbove() 
	{
		return above;
	}
	
	public void setAbove(Room above) 
	{
		this.above = above;
	}
	
	public Room getBelow() 
	{
		return below;
	}
	
	public void setBelow(Room below) 
	{
		this.below = below;
	}
	
	public Room getLeft() 
	{
		return left;
	}
	
	public void setLeft(Room left) 
	{
		this.left = left;
	}
	
	public Room getRight() 
	{
		return right;
	}
	
	public void setRight(Room right) 
	{
		this.right = right;
	}
	
	public Tilemap getFloor() 
	{
		return floor;
	}
	
	public void setFloor(Tilemap floor) 
	{
		this.floor = floor;
	}
	
	public Tilemap getDecor() 
	{
		return decor;
	}
	
	public void setDecor(Tilemap decor) 
	{
		this.decor = decor;
	}

}
