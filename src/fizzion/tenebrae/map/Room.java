package fizzion.tenebrae.map;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import engine.collisions.AABBCollider;
import engine.collisions.Collider;
import engine.core.GameObject;
import engine.rendering.Texture;
import fizzion.tenebrae.entities.Enemy;
import fizzion.tenebrae.entities.Entity;
import fizzion.tenebrae.objects.ObjectLoader;
import fizzion.tenebrae.objects.TileCollider;

/**
 * 
 * @author Lenny Litvak
 *
 */
public class Room
{
	private Dungeon dungeon;
	
	private Room above;
	private Room below;
	private Room left;
	private Room right;
	
	private Texture roomTexture;
	private ArrayList<GameObject> tileObjects;
	private ArrayList<AABBCollider> colliders;
	private ArrayList<Enemy> enemies;
	
	public Room(Dungeon dungeon)
	{
		this.dungeon = dungeon;
		tileObjects = new ArrayList<GameObject>();
		colliders = new ArrayList<AABBCollider>();
		enemies = new ArrayList<Enemy>();
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
	
	public void resolveCollisions()
	{
		resolveEntityCollisions(dungeon.getPlayer());
		
		ArrayList<Collider> hitColliders = new ArrayList<Collider>();
		
		for (Enemy e : enemies)
		{
			if (dungeon.getPlayer().getCollider().collidesWith(e.getCollider()))
			{
				hitColliders.add(e.getCollider());
				e.invokeCollisionEvent(dungeon.getPlayer().getCollider());
			}
			
			for (Enemy e2 : enemies)
			{
				if (e != e2 && e.getCollider().collidesWith(e2.getCollider()))
				{
					e.getCollider().resolveCollision(e2.getCollider());
				}
			}
			
			resolveEntityCollisions(e);
		}
		
		Collider[] cArray = new Collider[hitColliders.size()];
		hitColliders.toArray(cArray);
		dungeon.getPlayer().invokeCollisionEvent(cArray);
	}
	
	public void resolveEntityCollisions(Entity entity)
	{
		for (AABBCollider c : colliders)
		{
			if (c.collidesWith(entity.getCollider()))
			{
				entity.getCollider().resolveCollision(c);
			}
		}
	}
	
	public void addEnemy(Enemy enemy)
	{
		enemies.add(enemy);
	}
	
	public ArrayList<Enemy> getEnemies()
	{
		return enemies;
	}
	
	public void addObjectsToDungeon()
	{
		for (GameObject o : tileObjects)
		{
			dungeon.add(o);
		}
		
		for (Enemy e : enemies)
		{
			dungeon.add(e);
		}
	}
	
	public void removeObjectsFromDungeon()
	{
		for (GameObject o : tileObjects)
		{
			dungeon.remove(o);
		}
		
		for (Enemy e : enemies)
		{
			dungeon.remove(e);
		}
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
					
					GameObject obj = ObjectLoader.load(pixel & 0xFF, x * 64, y * 64);
					
					if (obj != null)
					{
						tileObjects.add(obj);
						
						if (obj instanceof TileCollider)
						{
							//TODO: sort via 2d array to cut down possible colliders?
							colliders.add(((TileCollider)obj).getCollider());
						}
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
