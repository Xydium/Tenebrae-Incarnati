package fizzion.tenebrae.entity;

import engine.components.RectRenderer;
import engine.math.Vector2i;
import engine.physics.AABBCollider;
import engine.rendering.Texture;

public class TestEnemy extends Enemy
{

	public TestEnemy(int x, int y)
	{
		super(200);
		
		RectRenderer rr = new RectRenderer(new Vector2i(64, 64), new Texture("tiles/004.png"));
		addComponent(rr);
		
		AABBCollider c = new AABBCollider(new Vector2i(64, 64));
		setCollider(c);
		addComponent(c);
		
		getTransform().setGlobalPosition(x, y);
	}

}
