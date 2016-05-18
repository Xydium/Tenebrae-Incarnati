package fizzion.tenebrae.entity;

import engine.core.GameObject;
import engine.physics.Collider;

public abstract class Entity extends GameObject
{
	private float health;
	private float maxHealth;
	
	private Collider collider;
	
	public Entity(float health)
	{
		this.health = health;
		this.maxHealth = health;
		this.collider = null;
	}
	
	public void setHealth(float health)
	{
		this.health = health;
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
}
