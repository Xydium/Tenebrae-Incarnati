package fizzion.tenebrae.map;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import engine.core.GameObject;
import engine.rendering.Texture;
import fizzion.tenebrae.objects.ObjectLoader;

public class Room
{
	private Dungeon dungeon;
	
	private Room above;
	private Room below;
	private Room left;
	private Room right;
	
	private Texture roomTexture;
	private ArrayList<GameObject> tileObjects;
	
	public Room(Dungeon dungeon)
	{
		this.dungeon = dungeon;
		tileObjects = new ArrayList<GameObject>();
	}
	
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
	
	public Texture getRoomTexture()
	{
		return roomTexture;
	}
	
	public ArrayList<GameObject> getTileObjects()
	{
		return tileObjects;
	}
	
	public void genTexture(int roomNumber)
	{
		try
		{
			BufferedImage roomMap = ImageIO.read(getClass().getResourceAsStream("/assets/maps/" + dungeon.getName()
				+ "/room" + roomNumber + ".png"));
			
			int[] pixels = roomMap.getRGB(0, 0, roomMap.getWidth(), roomMap.getHeight(), null, 0, roomMap.getWidth());
			int pixel;

			BufferedImage texImage = new BufferedImage(roomMap.getWidth() * Dungeon.TILE_SIZE_PIXELS,
				roomMap.getHeight() * Dungeon.TILE_SIZE_PIXELS, BufferedImage.TYPE_INT_ARGB);
			
			int r, g;
			BufferedImage floorImage, decorImage;
			HashMap<Integer, BufferedImage> loadedTextures = new HashMap<Integer, BufferedImage>();
			
			for (int y = 0; y < roomMap.getHeight(); y++)
			{
				for (int x = 0; x < roomMap.getWidth(); x++)
				{
					pixel = pixels[y * roomMap.getWidth() + x];
					r = (pixel >> 16) & 0xFF;
					g = (pixel >> 8) & 0xFF;
					
					floorImage = loadedTextures.get(r);
					
					if (floorImage == null)
					{
						floorImage = Texture.loadBufferedImage(createTextureName(r));
						loadedTextures.put(r, floorImage);
					}
					
					decorImage = loadedTextures.get(g);
					
					if (decorImage == null)
					{
						decorImage = Texture.loadBufferedImage(createTextureName(g));
						loadedTextures.put(g, decorImage);
					}

					floorImage.getGraphics().drawImage(decorImage, 0, 0, null);
					texImage.getGraphics().drawImage(floorImage, x * Dungeon.TILE_SIZE_PIXELS,
						y * Dungeon.TILE_SIZE_PIXELS, null);
					
					GameObject obj = ObjectLoader.load(pixel & 0xFF, x, y, roomMap.getWidth(), roomMap.getHeight());
					
					if (obj != null)
					{
						tileObjects.add(obj);
					}
				}
			}
			
			roomTexture = new Texture(texImage, dungeon.getName() + "_room" + roomNumber);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private String createTextureName(int texNum)
	{
		String texName = "tiles/";
		
		if (texNum < 100)
		{
			texName += "0";
		}
		
		if (texNum < 10)
		{
			texName += "0";
		}
		
		return texName + texNum + ".png";
	}
}
