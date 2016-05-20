package fizzion.tenebrae.entities;

import java.util.Random;

import engine.collisions.AABBCollider;
import engine.components.RectRenderer;
import engine.math.Transform;
import engine.math.Vector2i;
import engine.rendering.Texture;
import fizzion.tenebrae.map.Dungeon;

public class Knight extends Enemy
{
	
	private static final int TRACKING_DISTANCE = 250;
	
	public Knight(int x, int y, Dungeon dungeon)
	{
		super(200, dungeon);
		
		RectRenderer rr = new RectRenderer(new Vector2i(64, 64), new Texture("entities/knight1.png"));
		addComponent(rr);
		
		AABBCollider c = new AABBCollider(new Vector2i(64, 64));
		setCollider(c);
		addComponent(c);
		
		r = new Random();
		
		getTransform().setGlobalPosition(x, y);
	}

	private Random r;
	public void update()
	{
		Transform p = getDungeon().getPlayer().getTransform();
		if(p.distanceTo(getTransform()) < TRACKING_DISTANCE) {
			getTransform().lookAt(getDungeon().getPlayer().getTransform().getGlobalPosition());
			getTransform().translateBy(new Vector2i(0, 6));
		} else {
			if(Math.random() <= 0.01) {
				getTransform().lookAt(new Vector2i(r.nextInt(1024 - 256) + 128, r.nextInt(576 - 256) + 128));
			}
			getTransform().translateBy(new Vector2i(0, 4));
		}
	}
}
