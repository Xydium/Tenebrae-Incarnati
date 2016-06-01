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
import fizzion.tenebrae.launch.TenebraeIncarnati;

public class WinScreen extends GameObject
{
	private static final float DURATION = 7.f;

	private double startTime;
	private double passedTime;
	
	private static final String[] WIN_MESSAGES = {
		"YOU WIN!", "SUCCESS!", "GREAT SUCCESS!", "VERY NICE!",
		"WINNER", "COMMUNISM PREVAILS", "MISSION ACCOMPLISHED",
		"MISSION COMPLETE", "DUNGEON CLEARED!"
	};
	
	public WinScreen()
	{
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
		
		setTag("winScreen");
		
		GameObject youDied = new GameObject();
		youDied.setTag("youWin");
		youDied.addComponent(new TextRenderer(WIN_MESSAGES[(int)(WIN_MESSAGES.length * Math.random())], "Comic Sans MS",
			72, new Color(1, 1, 0)));
		youDied.getComponents().get(0).setTag("text");
		youDied.getTransform().setGlobalPosition(new Vector2i(Window.getWidth() / 2, Window.getHeight() / 2));
		addChild(youDied);
	}
	
	public void update()
	{
		passedTime = Time.getTime() - startTime;
		
		if (passedTime >= DURATION)
		{
			TenebraeIncarnati ti = (TenebraeIncarnati)getApplication().getGame();
			ti.setScene(ti.getScene("MainMenu"));
		}
	}
}
