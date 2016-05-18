package fizzion.tenebrae.objects;

import engine.core.GameObject;

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
}
