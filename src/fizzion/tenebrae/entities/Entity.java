package fizzion.tenebrae.entities;

import java.util.ArrayList;

import engine.collisions.Collider;
import engine.core.GameObject;
import fizzion.tenebrae.map.Dungeon;

public abstract class Entity extends GameObject
{
	private float health;
	private float maxHealth;
	
	private Collider collider;
	
	private Dungeon dungeon;
	
	private ArrayList<CollisionListener> collListeners;
	
	public Entity(float health, Dungeon dungeon)
	{
		this.health = health;
		this.maxHealth = health;
		this.collider = null;
		this.dungeon = dungeon;
		
		collListeners = new ArrayList<CollisionListener>();
	}
	
	public void addCollisionListener(CollisionListener cListener)
	{
		collListeners.add(cListener);
	}
	
	public void invokeCollisionEvent(Collider... others)
	{
		for (CollisionListener cl : collListeners)
		{
			for (Collider c : others)
			{
				cl.onCollision(c);
			}
		}
	}
	
	/**
	 * Sets the health of the entity, performs a check
	 * to see whether the given health is 0 < health < maxHealth
	 * 
	 * @param health the health to set to
	 */
	public void setHealth(float health)
	{
		if (health < 0)
		{
			this.health = 0;
		}
		else if (health > maxHealth)
		{
			this.health = maxHealth;
		}
		else
		{
			this.health = health;
		}
	}
	
	public void setMaxHealth(float maxHealth)
	{
		this.maxHealth = maxHealth;
	}
	
	public void setCollider(Collider collider)
	{
		this.collider = collider;
	}
	
	public float getHealth()
	{
		return health;
	}
	
	public float getMaxHealth()
	{
		return maxHealth;
	}
	
	public Collider getCollider()
	{
		return collider;
	}
	
	public Dungeon getDungeon()
	{
		return dungeon;
	}
}
