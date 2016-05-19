package fizzion.tenebrae.objects;

import engine.core.GameObject;
import fizzion.tenebrae.entity.Enemy;
import fizzion.tenebrae.entity.TestEnemy;
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
		if (name.equals("TestEnemy"))
		{
			return new TestEnemy(x, y, dungeon);
		}
		
		return null;
	}
}
