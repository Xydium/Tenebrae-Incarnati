package fizzion.tenebrae.objects;

import engine.core.GameObject;
import fizzion.tenebrae.entities.Enemy;
import fizzion.tenebrae.entities.Knight;
import fizzion.tenebrae.map.Dungeon;

public class ObjectLoader
{
	public static GameObject load(int num, int x, int y)
	{
		switch (num)
		{
			case 0:
				return null;
			default:
				return new TileCollider(x, y);
		}
	}
	
	public static Enemy loadEnemy(String name, int x, int y, Dungeon dungeon)
	{
		if (name.equals("Knight"))
		{
			return new Knight(x, y, dungeon);
		}
		
		return null;
	}
}
