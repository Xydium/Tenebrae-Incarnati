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
	private Texture atmo, nova, fore, obetext, skull, title;
	private Shader texShader, colorShader, disShader;
	
	public void start()
	{
		atmo = new Texture("backgrounds/menu_background_atmo.png");
		nova = new Texture("backgrounds/menu_background_nova.png");
		fore = new Texture("backgrounds/menu_background_fore.png");
		obetext = new Texture("backgrounds/menu_background_obetext.png");
		skull = new Texture("backgrounds/menu_background_skull.png");
		title = new Texture("backgrounds/menu_background_title.png");
		texShader = new Shader("basic-shader");
		colorShader = new Shader("color-shader");
		disShader = new Shader("distort-shader");
		colorShader.setUniform("color", new Color(0, 0, 0, 0));
		screen = new GameObject();
		GlobalAudio.addMusic("main", "assets/music/menu_loop.wav");
		GlobalAudio.loopMusic("main", 0.25);
		RectRenderer rr1 = new RectRenderer(Util.pixelDToGL(new Vector2(1280f, 720f)), atmo);
		RectRenderer rr5 = new RectRenderer(Util.pixelDToGL(new Vector2(1280f, 720f)), nova);
		RectRenderer rr2 = new RectRenderer(Util.pixelDToGL(new Vector2(1280f, 720f)), fore);
		RectRenderer rr3 = new RectRenderer(Util.pixelDToGL(new Vector2(1280f, 720f)), obetext);
		RectRenderer rr4 = new RectRenderer(Util.pixelDToGL(new Vector2(1280f, 720f)), skull);
		RectRenderer rr6 = new RectRenderer(Util.pixelDToGL(new Vector2(1280f, 720f)), title);
		rr1.setShader(disShader);
		rr1.setUniformConfig(() -> {
			rr1.getShader().setUniform("time", i);
			rr1.getShader().setUniform("frequency", 5.0f);
			rr1.getShader().setUniform("amplitude", 0.03f);
		});
		rr5.setShader(disShader);
		rr5.setUniformConfig(() -> {
			rr5.getShader().setUniform("time", i);
			rr5.getShader().setUniform("frequency", 10.0f);
			rr5.getShader().setUniform("amplitude", 0.001f);
		});
		rr3.setUniformConfig(() -> {
			if(rr3.getShader().getFileName().equals("distort-shader")) {
				rr3.getShader().setUniform("time", -i / 2);
				rr3.getShader().setUniform("frequency", -10.0f);
				rr3.getShader().setUniform("amplitude", -0.0007f);
			}
		});
		rr6.setShader(disShader);
		rr6.setUniformConfig(() -> {
			rr5.getShader().setUniform("time", i);
			rr5.getShader().setUniform("frequency", 1.0f);
			rr5.getShader().setUniform("amplitude", 0.01f);
		});
		rr2.setShader(texShader);
		rr3.setShader(texShader);
		rr4.setShader(texShader);
		screen.addAllComponents(rr1, rr2, rr3, rr4, rr6);
		screen.getTransform().setPosition(Util.pixelCToGL(new Vector2(640f, 640f)));
		add(screen);
	}
	
	public void input() {
		Vector2 mp = Input.getMousePosition();
		if(mp.getX() > 435 && mp.getX() < 835 && mp.getY() > 195 && mp.getY() < 335) {
			((RectRenderer) screen.getComponents().get(2)).setShader(disShader);
		} else {
			((RectRenderer) screen.getComponents().get(2)).setShader(colorShader);
		}
	}
	
	private float i;
	public void update() 
	{
		i += 0.02;
	}
	
}
