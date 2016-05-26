package fizzion.tenebrae.ui;

import engine.components.RectRenderer;
import engine.components.TextRenderer;
import engine.components.UniformConfig;
import engine.core.GameObject;
import engine.math.Vector2i;
import engine.rendering.Color;
import engine.rendering.Shader;
import engine.rendering.Window;
import engine.utility.Time;
import fizzion.tenebrae.map.Dungeon;

/**
 * 
 * @author Lenny Litvak
 *
 */
public class DeathScreen extends GameObject
{
	private static final float DURATION = 6.f;
	
	private Dungeon dungeon;
	
	private double startTime;
	private double passedTime;
	
	private static final String[] DEATH_MESSAGES = {
		"YOU DIED", "YOU SUCK", "BETTER LUCK NEXT TIME",
		"AT LEAST YOU TRIED", "GIT GUD", "CASUL",
		"THANKS OBAMA", "THIS IS WHY I NEED FEMINISM",
		"INSERT COIN TO CONTINUE", "THIS IS WHY I NEED COMMUNISM"
	};
	
	public DeathScreen(Dungeon dungeon)
	{
		this.dungeon = dungeon;
		startTime = Time.getTime();
		passedTime = 0.f;
		
		RectRenderer bg = new RectRenderer(Window.getSize());
		bg.setAllowLighting(false);
		bg.setShader(new Shader("color-shader"));
		
		bg.setUniformConfig(new UniformConfig()
		{
			public void setUniforms(Shader s)
			{
				s.setUniform("color", new Color(0, 0, 0, (float)passedTime / DURATION));
			}
		});
		
		addComponent(bg);
		
		GameObject youDied = new GameObject();
		youDied.setTag("youDied");
		youDied.addComponent(new TextRenderer(DEATH_MESSAGES[(int)(DEATH_MESSAGES.length * Math.random())], "Comic Sans MS", 64, new Color(0.7f, 0, 0)));
		youDied.getComponents().get(0).setTag("text");
		youDied.getTransform().setGlobalPosition(new Vector2i(Window.getWidth() / 2, Window.getHeight() / 2));
		addChild(youDied);
	}
	
	public void update()
	{
		passedTime = Time.getTime() - startTime;
		
		if (passedTime >= DURATION)
		{
			dungeon.reload();
		}
		
		/*if(passedTime >= 1 && Math.random() < 0.05)
		{
			String text = "";
			for(int i = 0; i < 8; i++)
			{
				text += (char) (Math.random() * (126 - 33) + 33);
			}
			((TextRenderer) getChildWithTag("youDied").getComponentWithTag("text")).setText(text);
		}*/
	}
}
