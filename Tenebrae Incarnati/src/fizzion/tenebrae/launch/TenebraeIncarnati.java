package fizzion.tenebrae.launch;

import engine.audio.GlobalAudio;
import engine.components.RectRenderer;
import engine.core.Game;
import engine.core.GameObject;
import engine.core.Input;
import engine.math.Vector2;
import engine.rendering.Color;
import engine.rendering.Shader;
import engine.rendering.Texture;
import engine.utility.Util;

public class TenebraeIncarnati extends Game 
{
	
	private GameObject screen;
	private Texture atmo, fore, obetext, skull;
	private Shader texShader, colorShader, disShader;
	
	public void start()
	{
		atmo = new Texture("backgrounds/menu_background_atmo.png");
		fore = new Texture("backgrounds/menu_background_fore.png");
		obetext = new Texture("backgrounds/menu_background_obetext.png");
		skull = new Texture("backgrounds/menu_background_skull.png");
		texShader = new Shader("basic-shader");
		colorShader = new Shader("color-shader");
		disShader = new Shader("distort-shader");
		colorShader.setUniform("color", new Color(0, 0, 0, 0));
		screen = new GameObject();
		GlobalAudio.addMusic("main", "assets/music/menu_loop.wav");
		GlobalAudio.loopMusic("main", 0.25);
		RectRenderer rr1 = new RectRenderer(Util.pixelDToGL(new Vector2(1280f, 720f)), atmo);
		RectRenderer rr2 = new RectRenderer(Util.pixelDToGL(new Vector2(1280f, 720f)), fore);
		RectRenderer rr3 = new RectRenderer(Util.pixelDToGL(new Vector2(1280f, 720f)), obetext);
		RectRenderer rr4 = new RectRenderer(Util.pixelDToGL(new Vector2(1280f, 720f)), skull);
		rr1.setShader(disShader);
		rr2.setShader(texShader);
		rr3.setShader(texShader);
		rr4.setShader(texShader);
		screen.addAllComponents(rr1, rr2, rr3, rr4);
		screen.getTransform().setPosition(Util.pixelCToGL(new Vector2(640f, 640f)));
		add(screen);
	}
	
	public void input() {
		if(!Input.getKey(Input.KEY_RETURN)) {
			((RectRenderer) screen.getComponents().get(2)).setShader(colorShader);
		} else {
			((RectRenderer) screen.getComponents().get(2)).setShader(texShader);
		}
	}
	
	private float i;
	public void update() 
	{
		i += 0.02;
		disShader.setUniform("time", i);
	}
	
}
