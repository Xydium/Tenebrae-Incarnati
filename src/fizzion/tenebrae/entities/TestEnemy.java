package fizzion.tenebrae.entities;

import java.util.Random;

import engine.collisions.AABBCollider;
import engine.components.RectRenderer;
import engine.math.Vector2i;
import engine.rendering.Texture;
import fizzion.tenebrae.map.Dungeon;

public class TestEnemy extends Enemy
{

	private boolean follow;
	
	public TestEnemy(int x, int y, Dungeon dungeon)
	{
		super(200, dungeon);
		
		RectRenderer rr = new RectRenderer(new Vector2i(64, 64), new Texture("entities/knight1.png"));
		addComponent(rr);
		
		AABBCollider c = new AABBCollider(new Vector2i(64, 64));
		setCollider(c);
		addComponent(c);
		
		getTransform().setGlobalPosition(x, y);
		
		follow = (r = new Random()).nextBoolean();
	}

	private Random r;
	public void update()
	{
		if(follow) {
			getTransform().lookAt(getDungeon().getPlayer().getTransform().getGlobalPosition());
		} else {
			if(Math.random() == 0) {
				getTransform().lookAt(new Vector2i(r.nextInt(1024), r.nextInt(576)));
			}
		}
		getTransform().translateBy(new Vector2i(0, 4));
	}
}
