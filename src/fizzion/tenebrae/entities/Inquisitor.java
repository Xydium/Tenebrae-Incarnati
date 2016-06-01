package fizzion.tenebrae.entities;

import java.util.Random;

import engine.collisions.AABBCollider;
import engine.components.RectRenderer;
import engine.math.Vector2i;
import engine.rendering.Texture;
import fizzion.tenebrae.map.Dungeon;
import fizzion.tenebrae.ui.WinScreen;

public class Inquisitor extends Enemy {

	private Vector2i velocity;
	
	private boolean won;
	
	public Inquisitor(int x, int y, Dungeon dungeon) {
		super(1000, dungeon);
		
		RectRenderer rr = new RectRenderer(new Vector2i(64, 64), new Texture("entities/inquisitor.png"));
		addComponent(rr);
		
		AABBCollider c = new AABBCollider(new Vector2i(64, 64));
		setCollider(c);
		addComponent(c);
		
		getTransform().setGlobalPosition(x, y);
		
		velocity = new Vector2i(3, 0);
		r = new Random();
	}
	
	private Random r;
	private int tickCount;
	public void update() {
		if(!won) {
			tickCount++;
			if(getTransform().getPosition().getX() < 256 && velocity.getX() < 0 || 
			   getTransform().getPosition().getX() > 1024 - 256 && velocity.getX() > 0) 
			{
				velocity.setX(-velocity.getX());
			}
			getTransform().translateBy(velocity);
			if(tickCount % 120 == 0) {
				Bat b = new Bat(r.nextInt(1024 - 256) + 256, 64, getDungeon());
				getDungeon().getRootObject().addChildSafely(b);
				getDungeon().getCurrentRoom().addEnemy(b);
			}
			if(getHealth() <= 0) {
				won = true;
				getDungeon().getRootObject().addChildSafely(new WinScreen());
			}
		}
	}
	
}
