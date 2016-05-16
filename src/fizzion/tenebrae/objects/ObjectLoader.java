package fizzion.tenebrae.objects;

import engine.core.GameObject;

public class ObjectLoader
{
	public static GameObject load(int num, int x, int y, int maxX, int maxY)
	{
		switch (num)
		{
			case 255:
				return new TileCollider(x, y, maxX, maxY);
			default:
				return null;
		}
	}
}
