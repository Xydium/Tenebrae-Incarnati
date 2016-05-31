package fizzion.tenebrae.entities;

import java.util.Random;

import engine.collisions.AABBCollider;
import engine.collisions.Collider;
import engine.components.RectRenderer;
import engine.math.Vector2i;
import engine.rendering.Texture;
import engine.rendering.Window;
import engine.utility.Time;
import fizzion.tenebrae.map.Dungeon;

public class Skeleton extends Enemy {
	
	private static final int BASE_DAMAGE = 15;
	private static final double DAMAGE_COOLDOWN = 1.0;
	
	private double lastAttacked;
	
	public Skeleton(int x, int y, Dungeon dungeon) {
		super(75, dungeon);

		lastAttacked = 0.0;
		
		RectRenderer rr = new RectRenderer(new Vector2i(64, 64), new Texture("entities/skeleton.png"));
		addComponent(rr);
		
		AABBCollider c = new AABBCollider(new Vector2i(64, 64));
		setCollider(c);
		addComponent(c);
		
		addCollisionListener(new CollisionListener()
		{
			public void onCollision(Collider other)
			{
				if (other.getParent() instanceof Player && ((Player) other.getParent()).getMovementState() != Player.CHARGING)
				{
					if (Time.getTime() - lastAttacked >= DAMAGE_COOLDOWN)
					{
						lastAttacked = Time.getTime();
						Player p = (Player)(other.getParent());
						p.setHealth(p.getHealth() - BASE_DAMAGE);
					}
				}
			}
		});
		
		r = new Random();
		getTransform().setGlobalPosition(x, y);
	}
	
	private Random r;
	public void update()
	{
		super.update();
		
		if(r.nextInt(400) == 0)
		{
			getTransform().setGlobalPosition(r.nextInt(Window.getWidth() - 128) + 64, r.nextInt(Window.getHeight() - 128) + 64);
		}
	}
	
}
