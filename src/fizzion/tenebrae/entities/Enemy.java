package fizzion.tenebrae.entities;

import fizzion.tenebrae.map.Dungeon;

/**
 * 
 * @author Lenny Litvak
 *
 */
public class Enemy extends Entity
{	
	public Enemy(float health, Dungeon dungeon)
	{
		super(health, dungeon);
	}
	
	public void update()
	{
		if (getHealth() <= 0)
		{
			if (getCollider() != null)
			{
				getDungeon().getCurrentRoom().getEnemies().remove(this);
			}
			
			getDungeon().remove(this);
		}
	}
}
