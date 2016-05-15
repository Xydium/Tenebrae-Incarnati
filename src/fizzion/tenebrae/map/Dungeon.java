package fizzion.tenebrae.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import engine.components.RectRenderer;
import engine.core.GameObject;
import engine.core.Input;
import engine.core.Scene;
import engine.math.Vector2;
import engine.rendering.Window;
import engine.utility.Log;
import fizzion.tenebrae.scene.DungeonSelect;

public class Dungeon extends Scene
{
	public static final int TILE_SIZE_PIXELS = 32;
	
	private Room[] rooms;
	private Room currentRoom;
	
	private String name;
	private int width;
	private int height;
	
	private RectRenderer roomRenderer;
	
	public Dungeon(String name)
	{
		this.name = name;
		loadDungeon();
	}
	
	public void activate()
	{
		GameObject rrObj = new GameObject();
		roomRenderer = new RectRenderer(new Vector2(1, 1 / (float)Window.getAspectRatio()), currentRoom.getRoomTexture());
		rrObj.addComponent(roomRenderer);
		add(rrObj);
	}
	
	public void input()
	{
		if(Input.getKeyDown(Input.KEY_LEFT) && currentRoom.getLeft() != null)
		{
			setCurrentRoom(currentRoom.getLeft());
		}
		else if(Input.getKeyDown(Input.KEY_RIGHT) && currentRoom.getRight() != null)
		{
			setCurrentRoom(currentRoom.getRight());
		}
		else if(Input.getKeyDown(Input.KEY_UP) && currentRoom.getAbove() != null)
		{
			setCurrentRoom(currentRoom.getAbove());
		}
		else if(Input.getKeyDown(Input.KEY_DOWN) && currentRoom.getBelow() != null)
		{
			setCurrentRoom(currentRoom.getBelow());
		}
		
		if(Input.getKeyDown(Input.KEY_ESCAPE))
		{
			getApplication().getGame().setScene(new DungeonSelect());
		}
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
		this.currentRoom = room;
		roomRenderer.setTexture(room.getRoomTexture());
		Log.info("" + currentRoom.getRoomTexture().getID());
	}
	
	public Room getCurrentRoom()
	{
		return currentRoom;
	}
	
	public Room[] getRooms()
	{
		return rooms;
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
					roomList.add(new Room(this));
					linkList.add(new LinkSet(line.substring(2)));
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

			currentRoom = rooms[0];
		}
		catch (IOException e)
		{
			Log.error(e);
		}
	}
}
