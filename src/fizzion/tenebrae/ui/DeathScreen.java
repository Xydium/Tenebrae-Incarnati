package fizzion.tenebrae.ui;

import engine.components.RectRenderer;
import engine.components.RectRenderer.UniformConfig;
import engine.core.GameObject;
import engine.rendering.Color;
import engine.rendering.Shader;
import engine.rendering.Window;
import engine.utility.Time;
import fizzion.tenebrae.map.Dungeon;
import fizzion.tenebrae.ui.Message.Placement;

/**
 * 
 * @author Lenny Litvak
 *
 */
public class DeathScreen extends GameObject
{
	private static final float DURATION = 4.f;
	
	private Dungeon dungeon;
	
	private double startTime;
	private double passedTime;
	
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
		
		addChild(new Message("YOU DIED", "Papyrus", 64, new Color(1, 0, 0), Window.getSize().div(2), Placement.CENTER));
	}
	
	public void update()
	{
		passedTime = Time.getTime() - startTime;
		
		if (passedTime >= DURATION)
		{
			dungeon.reload();
		}
	}
}
