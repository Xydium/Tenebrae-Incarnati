package fizzion.tenebrae.entity;

import engine.components.RectRenderer;
import engine.math.Vector2i;
import engine.physics.AABBCollider;
import engine.rendering.Texture;
import fizzion.tenebrae.map.Dungeon;

public class TestEnemy extends Enemy
{

	public TestEnemy(int x, int y, Dungeon dungeon)
	{
		super(200, dungeon);
		
		RectRenderer rr = new RectRenderer(new Vector2i(64, 64), new Texture("tiles/252.png"));
		addComponent(rr);
		
		AABBCollider c = new AABBCollider(new Vector2i(64, 64));
		setCollider(c);
		addComponent(c);
		
		getTransform().setGlobalPosition(x, y);
	}

	public void update()
	{
		getTransform().lookAt(getDungeon().getPlayer().getTransform().getGlobalPosition());
		getTransform().translateBy(new Vector2i(0, 2));
	}
}
