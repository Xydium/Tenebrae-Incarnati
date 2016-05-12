package fizzion.tenebrae.scene;

import engine.audio.GlobalAudio;
import engine.components.RectRenderer;
import engine.components.RectRenderer.UniformConfig;
import engine.core.Input;
import engine.core.Scene;
import engine.math.Vector2;
import engine.rendering.Color;
import engine.rendering.Shader;
import engine.rendering.Texture;
import engine.utility.Util;

public class MainMenu extends Scene 
{
	
	private Texture atmo, platform, obetext, skull, title;
	private Shader texShader, disShader, colorShader, flickerShader;
	
	private float i;
	
	public void activate()
	{
		atmo = new Texture("backgrounds/menu_background_atmo.png");
		platform = new Texture("backgrounds/menu_background_fore.png");
		obetext = new Texture("backgrounds/menu_background_obetext.png");
		skull = new Texture("backgrounds/menu_background_skull.png");
		title = new Texture("backgrounds/menu_background_title.png");
		
		GlobalAudio.addMusic("menu", "assets/music/menu_loop.wav");
		GlobalAudio.loopMusic("menu", 0.25);
		
		texShader = new Shader("basic-shader");
		disShader = new Shader("distort-shader");
		colorShader = new Shader("color-shader");
		colorShader.setUniform("color", new Color(0f, 0f, 0f, 0f));
		flickerShader = new Shader("color-shader");
		
		final RectRenderer atmoRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), atmo);
		final RectRenderer platformRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), platform);
		final RectRenderer obetextRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), obetext);
		final RectRenderer skullRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), skull);
		final RectRenderer titleRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), title);
		final RectRenderer flickerRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), atmo);
		
		atmoRect.setShader(disShader);
		platformRect.setShader(texShader);
		obetextRect.setShader(disShader);
		obetextRect.setTag("obetext");
		skullRect.setShader(texShader);
		titleRect.setShader(disShader);
		flickerRect.setShader(flickerShader);
		
		atmoRect.setUniformConfig(new UniformConfig() 
		{ 
			public void setUniforms() 
			{
				atmoRect.getShader().setUniform("time", i);
				atmoRect.getShader().setUniform("frequency", 20.0f);
				atmoRect.getShader().setUniform("amplitude", 0.007f);
			}
		});
		
		obetextRect.setUniformConfig(new UniformConfig() 
		{ 
			public void setUniforms() 
			{
				if(obetextRect.getShader().getFileName().equals("distort-shader")) 
				{
					obetextRect.getShader().setUniform("time", -i / 2);
					obetextRect.getShader().setUniform("frequency", -10.0f);
					obetextRect.getShader().setUniform("amplitude", -0.0007f);
				}
			}
		});
		
		titleRect.setUniformConfig(new UniformConfig() 
		{ 
			public void setUniforms() 
			{
				titleRect.getShader().setUniform("time", i);
				titleRect.getShader().setUniform("frequency", 1.0f);
				titleRect.getShader().setUniform("amplitude", 0.01f);
			}
		});
		
		flickerRect.setUniformConfig(new UniformConfig() 
		{
			private boolean flickerOn = true;
			public void setUniforms() 
			{
				flickerRect.getShader().setUniform("color", new Color(0f, 0f, 0f, flickerOn ? 0.2f : 0f));
				if(Math.random() < 0.05) flickerOn = !flickerOn;
			}
		});
		
		getRootObject().addAllComponents(atmoRect, platformRect, obetextRect, skullRect, titleRect, flickerRect);
	}

	public void input()
	{
		Vector2 mp = Input.getMousePosition();
		if(mp.getX() > 360 && mp.getY() < 660 && mp.getY() > 150 && mp.getY() < 260) 
		{
			((RectRenderer) getRootObject().getComponentWithTag("obetext")).setShader(disShader);
		}
		else
		{
			((RectRenderer) getRootObject().getComponentWithTag("obetext")).setShader(colorShader);
		}
	}
	
	public void update()
	{
		i += 0.02;
	}
	
	public void deactivate()
	{
		atmo = platform = obetext = skull = title = null;
		texShader = disShader = colorShader = null;
	}
	
}
