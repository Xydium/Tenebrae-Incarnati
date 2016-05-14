package fizzion.tenebrae.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Dungeon 
{

	private Room[] rooms;
	private Room currentRoom;
	
	public Dungeon(String name)
	{
		loadDungeon(name);
		currentRoom = rooms[0];
	}
	
	private void loadDungeon(String name)
	{
		//Create a way to read from file
		BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("assets/maps/" + name + ".dungeon")));
		
		//Make an array of rooms
		rooms = new Room[Integer.parseInt(nextDungeonLine(reader))];
		
		//Populate that array
		for(int i = 0; i < rooms.length; i++)
		{
			rooms[i] = new Room();	
		}
		
		//Map rooms to eachother
		for(int i = 0; i < rooms.length; i++)
		{
			char[] pointers = nextDungeonLine(reader).substring(1).toCharArray();
			final int ABOVE = 0, BELOW = 1, LEFT = 2, RIGHT = 3;
			if(Character.isDigit(pointers[ABOVE])) rooms[i].setAbove(rooms[Integer.parseInt("" + pointers[ABOVE])]);
			if(Character.isDigit(pointers[BELOW])) rooms[i].setBelow(rooms[Integer.parseInt("" + pointers[BELOW])]);
			if(Character.isDigit(pointers[LEFT])) rooms[i].setLeft(rooms[Integer.parseInt("" + pointers[LEFT])]);
			if(Character.isDigit(pointers[RIGHT])) rooms[i].setAbove(rooms[Integer.parseInt("" + pointers[RIGHT])]);
		}
		
		//Map tiles to each room
		for(Room r : rooms)
		{
			//Floor map
			for(int row = 0; row < Room.HEIGHT; row++)
			{
				String[] currentRow = nextDungeonLine(reader).split(" ");
				for(int col = 0; col < currentRow.length; col++)
				{
					int tile = Integer.parseInt(currentRow[col]);
					//r.getFloor().setTileAt(col, row, tile);
				}
			}
			//Decor map
			for(int row = 0; row < Room.HEIGHT; row++)
			{
				String[] currentRow = nextDungeonLine(reader).split(" ");
				for(int col = 0; col < currentRow.length; col++)
				{
					int tile = Integer.parseInt(currentRow[col]);
					//r.getDecor().setTileAt(col, row, tile);
				}
			}
		}
	}
	
	private String nextDungeonLine(BufferedReader reader)
	{
		String result = "#";
		while(result.charAt(0) == '#')
		{
			try 
			{
				result = reader.readLine();
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		return result;
	}
	
}
