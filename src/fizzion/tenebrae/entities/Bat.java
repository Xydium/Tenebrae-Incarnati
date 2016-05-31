package fizzion.tenebrae.entities;

import engine.collisions.AABBCollider;
import engine.collisions.Collider;
import engine.components.RectRenderer;
import engine.math.Vector2i;
import engine.rendering.Texture;
import engine.utility.Log;
import fizzion.tenebrae.map.Dungeon;

public class Bat extends Enemy {

	private static final int BASE_DAMAGE = 10;
	private boolean removed;
	
	public Bat(int x, int y, Dungeon dungeon) 
	{
		super(10, dungeon);
		
		RectRenderer rr = new RectRenderer(new Vector2i(64, 64), new Texture("entities/bat1.png"));
		addComponent(rr);
		
		AABBCollider c = new AABBCollider(new Vector2i(64, 64));
		setCollider(c);
		addComponent(c);
		
		addCollisionListener(new CollisionListener()
		{
			public void onCollision(Collider other)
			{
				if (other.getParent() instanceof Player)
				{
					Player p = (Player)(other.getParent());
					p.setHealth(p.getHealth() - BASE_DAMAGE);
					removed = true;
				} else {
					getTransform().rotateBy((float) Math.PI * 2.0f / 3.0f);
				}
			}
		});
		
		getTransform().setGlobalPosition(x, y);
	}
	
	public void update() {
		super.update();
		if(removed) { getDungeon().getCurrentRoom().getEnemies().remove(this); getDungeon().remove(this); }
		getTransform().translateBy(new Vector2i(0, 5));
	}

}
