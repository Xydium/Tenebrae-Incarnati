package fizzion.tenebrae.objects;

import engine.core.GameObject;
import engine.math.Vector2;
import engine.physics.AABBCollider;

public class TileCollider extends GameObject
{
	public TileCollider(int x, int y, int maxX, int maxY)
	{
		float halfWidth = (float)maxX / 2;
		float halfHeight = (float)maxY / 2;
		
		float nx = (x - halfWidth) / halfWidth;
		float ny = (y - halfHeight) / halfHeight;
		
		getTransform().setPosition(nx, ny);
		
		addComponent(new AABBCollider(new Vector2(1.f / (maxX / 2.f), 1.f / (maxY / 2.f))));
	}
}
