package fizzion.tenebrae.launch;

import engine.components.RectRenderer;
import engine.core.Game;
import engine.core.GameObject;
import engine.math.Vector2;
import engine.rendering.Color;
import engine.rendering.Shader;
import engine.rendering.Texture;
import engine.rendering.Window;

public class TenebraeIncarnati extends Game 
{
	
	private Texture background;
	private Shader texshader;
	
	public void start()
	{
		background = new Texture("backgrounds/prototype_build.png");
		texshader = new Shader("basic-shader");
		GameObject screen = new GameObject();
		RectRenderer rr = new RectRenderer(new Vector2(0.5625f, 1.0f), background);
		rr.setShader(texshader);
		screen.addComponent(rr);
//		screen.getTransform().rotateBy((float) (Math.PI * 0.5));
		add(screen);
	}
	
}