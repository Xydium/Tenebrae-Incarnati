package fizzion.tenebrae.objects;

import engine.core.GameObject;
import fizzion.tenebrae.entity.Enemy;
import fizzion.tenebrae.entity.TestEnemy;

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
	
	public static Enemy loadEnemy(String name, int x, int y)
	{
		if (name.equals("TestEnemy"))
		{
			return new TestEnemy(x, y);
		}
		
		return null;
	}
}
