package fizzion.tenebrae.entities;

import engine.audio.GlobalAudio;
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
		
		addChild(new EntityHealthBar(this));
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
			
			GlobalAudio.playSound("enemy_death", 0.25);
		}
	}
}
