package fizzion.tenebrae.launch;

import engine.components.RectRenderer;
import engine.core.Game;
import engine.core.GameObject;
import engine.math.Vector2;
import engine.rendering.Color;
import engine.rendering.Shader;
import engine.rendering.Texture;
import engine.rendering.Window;
import engine.utility.Util;

public class TenebraeIncarnati extends Game 
{
	
	private Texture background;
	private Shader texshader;
	
	public void start()
	{
		background = new Texture("backgrounds/prototype_build.png");
		texshader = new Shader("basic-shader");
		GameObject screen = new GameObject();
		RectRenderer rr = new RectRenderer(Util.pixelDToGL(new Vector2(1280f, 720f)), background);
		rr.setShader(texshader);
		screen.addComponent(rr);
		screen.getTransform().setPosition(Util.pixelCToGL(new Vector2(640f, 640f)));
		add(screen);
	}
	
}
