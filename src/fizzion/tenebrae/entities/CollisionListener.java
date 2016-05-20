package fizzion.tenebrae.entities;

import engine.collisions.Collider;

public interface CollisionListener
{
	public void onCollision(Collider other);
}
