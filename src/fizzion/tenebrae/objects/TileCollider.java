package fizzion.tenebrae.objects;

import engine.collisions.AABBCollider;
import engine.core.GameObject;
import engine.math.Vector2i;

/**
 * 
 * @author Lenny Litvak
 *
 */
public class TileCollider extends GameObject
{
	private AABBCollider collider;
	
	public TileCollider(int x, int y)
	{
		getTransform().setPosition(x, y);
		
		collider = new AABBCollider(new Vector2i(64, 64));
		addComponent(collider);
	}
	
	public AABBCollider getCollider()
	{
		return collider;
	}
}
