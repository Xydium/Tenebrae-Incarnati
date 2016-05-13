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
		//Create a game object for the background
		GameObject background = new GameObject();
		background.setTag("background");
		//Load the necessary textures
		atmo = new Texture("backgrounds/menu_background_atmo.png");
		platform = new Texture("backgrounds/menu_background_fore.png");
		title = new Texture("backgrounds/menu_background_title.png");
		//Create renderers for the textures and flicker
		RectRenderer atmoRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), atmo);
		RectRenderer platformRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), platform);
		RectRenderer flickerRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), atmo);
		RectRenderer titleRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), title);
		//Load shaders
		texShader = new Shader("basic-shader");
		disShader = new Shader("distort-shader");
		flickerShader = new Shader("color-shader");
		//Set the shaders for the renderers
		atmoRect.setShader(disShader);
		platformRect.setShader(texShader);
		titleRect.setShader(disShader);
		flickerRect.setShader(flickerShader);
		//Set the uniform configs for the shaders in the renderers
		atmoRect.setUniformConfig(new UniformConfig()
		{ 
			public void setUniforms(Shader s) 
			{
				s.setUniform("time", i);
				s.setUniform("frequency", 20.0f);
				s.setUniform("amplitude", 0.007f);
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
		//Add the components to the game object
		background.addAllComponents(atmoRect, platformRect, flickerRect, titleRect);
		
		//Create a gameobject for the text on the obelisk
		GameObject obeliskText = new GameObject();
		obeliskText.setTag("obeliskText");
		//Load the texture for the obelisk text
		obetext = new Texture("backgrounds/menu_background_obetext.png");
		//Create the render for the obelisk text
		RectRenderer obetextRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), obetext);
		obetextRect.setTag("obetextRect");
		//Load the shader for the obelisk text
		clearShader = new Shader("color-shader");
		clearShader.setUniform("color", new Color(0f, 0f, 0f, 0f));
		//Set the shader for the obelisk text
		obetextRect.setShader(clearShader);
		//Set the uniform config for the obelisk text
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
		//Defines the click zone to control the obelisk shader
		play = new ClickZone(360, 150, 300, 110);
		//Add the components to the game object
		obeliskText.addAllComponents(obetextRect, play);
		
		//Create a gameobject for the skull
		GameObject skullStick = new GameObject();
		skullStick.setTag("skullStick");
		//Load the texture for the skull
		skull = new Texture("backgrounds/menu_background_skull.png");
		//Create the renderer for the skull
		RectRenderer skullRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), skull);
		//Set the shader for the renderer
		skullRect.setShader(texShader);
		//Add the renderer for the skull
		skullStick.addAllComponents(skullRect);
		
		//Add all three objects to the root of the scene
		getRootObject().addAllChildren(background, obeliskText, skullStick);
		
		//Load and play menu track
		GlobalAudio.addMusic("menu", "assets/music/menu_loop.wav");
		GlobalAudio.loopMusic("menu", 0.25);
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
