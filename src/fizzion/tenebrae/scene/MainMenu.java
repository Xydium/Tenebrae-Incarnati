package fizzion.tenebrae.scene;

import engine.audio.GlobalAudio;
import engine.components.RectRenderer;
import engine.components.RectRenderer.UniformConfig;
import engine.core.GameObject;
import engine.core.Scene;
import engine.math.Vector2;
import engine.rendering.Color;
import engine.rendering.Shader;
import engine.rendering.Texture;
import engine.utility.Util;
import fizzion.tenebrae.ui.ClickZone;

public class MainMenu extends Scene 
{
	
	private Texture atmo, platform, obetext, skull, title;
	private Shader texShader, disShader, clearShader, flickerShader;
	
	private ClickZone play;
	
	private float i;
	
	public void activate()
	{
		GameObject background = new GameObject();
		background.setTag("background");
		GameObject obeliskText = new GameObject();
		obeliskText.setTag("obeliskText");
		GameObject skullStick = new GameObject();
		skullStick.setTag("skullStick");
		
		atmo = new Texture("backgrounds/menu_background_atmo.png");
		platform = new Texture("backgrounds/menu_background_fore.png");
		obetext = new Texture("backgrounds/menu_background_obetext.png");
		skull = new Texture("backgrounds/menu_background_skull.png");
		title = new Texture("backgrounds/menu_background_title.png");
		
		GlobalAudio.addMusic("menu", "assets/music/menu_loop.wav");
		GlobalAudio.loopMusic("menu", 0.25);
		
		texShader = new Shader("basic-shader");
		disShader = new Shader("distort-shader");
		clearShader = new Shader("color-shader");
		clearShader.setUniform("color", new Color(0f, 0f, 0f, 0f));
		flickerShader = new Shader("color-shader");
		
		RectRenderer atmoRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), atmo);
		RectRenderer platformRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), platform);
		RectRenderer obetextRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), obetext);
		RectRenderer skullRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), skull);
		RectRenderer titleRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), title);
		RectRenderer flickerRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), atmo);
		
		play = new ClickZone(360, 150, 300, 110);
		
		atmoRect.setShader(disShader);
		platformRect.setShader(texShader);
		obetextRect.setShader(disShader);
		obetextRect.setTag("obetextRect");
		skullRect.setShader(texShader);
		titleRect.setShader(disShader);
		flickerRect.setShader(flickerShader);
		
		atmoRect.setUniformConfig(new UniformConfig()
		{ 
			public void setUniforms(Shader s) 
			{
				s.setUniform("time", i);
				s.setUniform("frequency", 20.0f);
				s.setUniform("amplitude", 0.007f);
			}
		});
		
		obetextRect.setUniformConfig(new UniformConfig() 
		{ 
			public void setUniforms(Shader s) 
			{
				if(s.getFileName().equals("distort-shader")) 
				{
					s.setUniform("time", -i / 2);
					s.setUniform("frequency", -10.0f);
					s.setUniform("amplitude", -0.0007f);
				}
			}
		});
		
		titleRect.setUniformConfig(new UniformConfig() 
		{ 
			public void setUniforms(Shader s) 
			{
				s.setUniform("time", i);
				s.setUniform("frequency", 1.0f);
				s.setUniform("amplitude", 0.01f);
			}
		});
		
		flickerRect.setUniformConfig(new UniformConfig() 
		{
			private boolean flickerOn = true;
			public void setUniforms(Shader s) 
			{
				s.setUniform("color", new Color(0f, 0f, 0f, flickerOn ? 0.2f : 0f));
				if(Math.random() < 0.05) flickerOn = !flickerOn;
			}
		});
		
		background.addAllComponents(atmoRect, platformRect, flickerRect, titleRect);
		obeliskText.addAllComponents(obetextRect, play);
		skullStick.addAllComponents(skullRect);
		
		getRootObject().addAllChildren(background, obeliskText, skullStick);
	}
	
	public void update()
	{
		i += 0.02;
		((RectRenderer) getRootObject().getChildWithTag("obeliskText").getComponentWithTag("obetextRect")).setShader(play.isHovered() ? disShader : clearShader);
		if(play.isClicked())
		{
			System.exit(0);
		}
	}
	
	public void deactivate()
	{
		atmo = platform = obetext = skull = title = null;
		texShader = disShader = clearShader = null;
	}
	
}
