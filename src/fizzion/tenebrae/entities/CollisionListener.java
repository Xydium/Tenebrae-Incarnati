package fizzion.tenebrae.entities;

import engine.collisions.Collider;

/**
 * 
 * @author Lenny Litvak
 *
 */
public interface CollisionListener
{
	public void onCollision(Collider other);
}
