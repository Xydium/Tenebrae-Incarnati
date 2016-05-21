package fizzion.tenebrae.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import engine.collisions.AABBCollider;
import engine.components.RectRenderer;
import engine.core.GameObject;
import engine.core.Input;
import engine.core.Scene;
import engine.math.Vector2i;
import engine.rendering.Window;
import engine.utility.Log;
import fizzion.tenebrae.entities.Enemy;
import fizzion.tenebrae.entities.Player;
import fizzion.tenebrae.launch.TenebraeIncarnati;
import fizzion.tenebrae.objects.ObjectLoader;
import fizzion.tenebrae.scene.DungeonSelect;
import fizzion.tenebrae.ui.HealthBar;

/**
 * 
 * @author Lenny Litvak
 *
 */
public class Dungeon extends Scene
{
	public static final int TILE_SIZE_PIXELS = 32;
	
	private Room[] rooms;
	private Room currentRoom;
	
	private String name;
	private int width;
	private int height;
	
	private RectRenderer roomRenderer;
	
	private Player player;
	
	public Dungeon(String name)
	{
		this.name = name;
		loadDungeon();
	}
	
	public void input()
	{
		if (Input.getKeyDown(Input.KEY_R))
		{
			reload();
		}
	}
	
	public void load()
	{
		GameObject rrObj = new GameObject();
		roomRenderer = new RectRenderer(new Vector2i(Window.getWidth(), Window.getHeight()), null);
		rrObj.addComponent(roomRenderer);
		
		player = new Player(this);
		addAll(rrObj, player);
		
		HealthBar healthBar = new HealthBar(player, new Vector2i(200, 20));
		healthBar.getTransform().setPosition(64, Window.getHeight() - 42);
		add(healthBar);
	}
	
	public void activate()
	{
		setCurrentRoom(rooms[0]);
		player.getTransform().setPosition(Window.getWidth() / 2 - 32, Window.getHeight() / 2 - 32);
	}
	
	public void lateUpdate()
	{
		currentRoom.resolveCollisions();
		
		Vector2i pos = player.getTransform().getGlobalPosition();
		AABBCollider col = (AABBCollider)player.getCollider();
		
		Room nextRoom = null;
		Vector2i startPos = new Vector2i();
		
		if (pos.getY() < 0)
		{
			nextRoom = currentRoom.getAbove();
			startPos = new Vector2i(Window.getWidth() / 2 - 32, Window.getHeight() - 64 - 20);
		}
		else if (pos.getY() + col.getSize().getY() > Window.getHeight())
		{
			nextRoom = currentRoom.getBelow();
			startPos = new Vector2i(Window.getWidth() / 2 - 32, 20);
		}
		else if (pos.getX() < 0)
		{
			nextRoom = currentRoom.getLeft();
			startPos = new Vector2i(Window.getWidth() - 64 - 20, Window.getHeight() / 2 - 32);
		}
		else if (pos.getX() + col.getSize().getX() > Window.getWidth())
		{
			nextRoom = currentRoom.getRight();
			startPos = new Vector2i(20, Window.getHeight() / 2 - 32);
		}
		
		if (nextRoom != null)
		{
			setCurrentRoom(nextRoom);
			player.getTransform().setPosition(startPos);
		}
	}
	
	public void reload()
	{
		TenebraeIncarnati ti = (TenebraeIncarnati)getApplication().getGame();
		DungeonSelect ds = (DungeonSelect)ti.getScene("DungeonSelect");
		
		ds.reloadCurrentDungeon();
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void setCurrentRoom(Room room)
	{
		if (currentRoom != null)
		{
			currentRoom.removeObjectsFromDungeon();
		}
		
		this.currentRoom = room;
		roomRenderer.setTexture(room.getRoomTexture());
		currentRoom.addObjectsToDungeon();
	}
	
	public Room getCurrentRoom()
	{
		return currentRoom;
	}
	
	public Room[] getRooms()
	{
		return rooms;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	private class LinkSet
	{
		public LinkSet(String linkLine)
		{
			String[] nums = linkLine.split("\\s"); // split by whitespace

			if (nums.length != 4)
			{
				Log.error("ERROR: Invalid number of room links");
			}

			above = nums[0].equals("$") ? -1 : Integer.parseInt(nums[0]);
			below = nums[1].equals("$") ? -1 : Integer.parseInt(nums[1]);
			left = nums[2].equals("$") ? -1 : Integer.parseInt(nums[2]);
			right = nums[3].equals("$") ? -1 : Integer.parseInt(nums[3]);
		}

		public int above;
		public int below;
		public int left;
		public int right;
	}
	
	private void loadDungeon()
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/assets/maps/"
			+ name + "/layout.dat")));
		
		String line;
		Room workingRoom = null;
		
		try
		{
			ArrayList<Room> roomList = new ArrayList<Room>();
			ArrayList<LinkSet> linkList = new ArrayList<LinkSet>();

			while ((line = reader.readLine()) != null)
			{
				line = line.trim();
				
				if (line.length() == 0 || line.charAt(0) == '#')
				{
					continue;
				}

				if (line.charAt(0) == 'w')
				{
					width = Integer.parseInt(line.substring(2));
				}
				else if (line.charAt(0) == 'h')
				{
					height = Integer.parseInt(line.substring(2));
				}
				else if (line.charAt(0) == 'r')
				{
					workingRoom = new Room(this);
					roomList.add(workingRoom);
					linkList.add(new LinkSet(line.substring(2)));
				}
				else if (line.charAt(0) == 'e')
				{
					if (workingRoom != null)
					{
						addEnemy(workingRoom, line.substring(2));
					}
				}
			}

			rooms = new Room[roomList.size()];

			for (int i = 0; i < roomList.size(); i++)
			{
				Room r = roomList.get(i);
				LinkSet ls = linkList.get(i);
				
				r.setAbove(ls.above == -1 ? null : roomList.get(ls.above));
				r.setBelow(ls.below == -1 ? null : roomList.get(ls.below));
				r.setLeft(ls.left == -1 ? null : roomList.get(ls.left));
				r.setRight(ls.right == -1 ? null : roomList.get(ls.right));

				r.genTexture(i);

				rooms[i] = r;
			}

			//currentRoom = rooms[0];
		}
		catch (IOException e)
		{
			Log.error(e);
		}
	}
	
	private void addEnemy(Room room, String line)
	{
		String[] vals = line.split("\\s");
		
		if (vals.length != 3)
		{
			System.out.println("no right values");
			return;
		}
		
		Enemy e = ObjectLoader.loadEnemy(vals[0], Integer.parseInt(vals[1]), Integer.parseInt(vals[2]), this);
		
		if (e != null)
		{
			room.addEnemy(e);
		}
	}
}
