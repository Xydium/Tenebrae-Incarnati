package fizzion.tenebrae.scene;

import engine.audio.GlobalAudio;
import engine.components.RectRenderer;
import engine.components.RectRenderer.UniformConfig;
import engine.core.GameObject;
import engine.core.Input;
import engine.core.Scene;
import engine.math.Vector2;
import engine.rendering.Color;
import engine.rendering.Shader;
import engine.rendering.Texture;
import engine.utility.Log;
import engine.utility.Util;
import fizzion.tenebrae.ui.ClickZone;

public class MainMenu extends Scene 
{
	
	private float shaderTime;
	
	public void activate()
	{
		GameObject background = new Background();
		GameObject obeliskText = new ObeliskText();
		GameObject skull = new Skull();
		
		getRootObject().addAllChildren(background, obeliskText, skull);
		
		GlobalAudio.addMusic("menu", "assets/music/menu_loop_2.wav");
		GlobalAudio.loopMusic("menu", 0.25);
	}
	
	public void update()
	{
		shaderTime += 0.02;
	}
	
	private class Background extends GameObject
	{
		public Background()
		{
			setTag("background");
			
			Texture atmo = new Texture("backgrounds/menu_background_atmo.png");
			Texture platform = new Texture("backgrounds/menu_background_fore.png");
			Texture title = new Texture("backgrounds/menu_background_title.png");
			
			RectRenderer atmoRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), atmo);
			RectRenderer platformRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), platform);
			RectRenderer flickerRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), atmo);
			RectRenderer titleRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), title);
			
			Shader textureShader = new Shader("basic-shader");
			Shader distortionShader = new Shader("distort-shader");
			Shader flickerShader = new Shader("color-shader");
			
			atmoRect.setShader(distortionShader);
			platformRect.setShader(textureShader);
			titleRect.setShader(distortionShader);
			flickerRect.setShader(flickerShader);
			
			atmoRect.setUniformConfig(new UniformConfig()
			{ 
				public void setUniforms(Shader s) 
				{
					s.setUniform("time", shaderTime);
					s.setUniform("frequency", 20.0f);
					s.setUniform("amplitude", 0.007f);
				}
			});
			
			titleRect.setUniformConfig(new UniformConfig() 
			{ 
				public void setUniforms(Shader s) 
				{
					s.setUniform("time", shaderTime);
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
			
			addAllComponents(atmoRect, platformRect, flickerRect, titleRect);
		}
	}
	
	private class ObeliskText extends GameObject
	{
		
		private Shader clearShader;
		private Shader distortionShader;
		
		private ClickZone play;
		
		public ObeliskText()
		{
			setTag("obeliskText");
			
			Texture obetext = new Texture("backgrounds/menu_background_obetext.png");
			
			RectRenderer obetextRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), obetext);
			obetextRect.setTag("obetextRect");
			
			clearShader = new Shader("color-shader");
			clearShader.setUniform("color", new Color(0f, 0f, 0f, 0f));
			
			distortionShader = new Shader("distort-shader");
			
			obetextRect.setShader(clearShader);
			
			obetextRect.setUniformConfig(new UniformConfig() 
			{ 
				public void setUniforms(Shader s) 
				{
					if(s.getFileName().equals("distort-shader")) 
					{
						s.setUniform("time", -shaderTime / 2);
						s.setUniform("frequency", -10.0f);
						s.setUniform("amplitude", -0.0007f);
					}
				}
			});
			
			play = new ClickZone(360, 150, 300, 110);
			
			addAllComponents(obetextRect, play);
		}
		
		public void update()
		{
			((RectRenderer) getComponentWithTag("obetextRect")).setShader(play.isHovered() ? distortionShader : clearShader);
			if(play.isClicked())
			{
				System.exit(0);
			}
		}
		
	}
	
	private class Skull extends GameObject
	{
		
		private Shader distortionShader;
		private Shader clearShader;
		
		private ClickZone quit;
		
		public Skull()
		{
			setTag("skull");
			Texture skull = new Texture("backgrounds/menu_background_skull.png");
			Shader textureShader = new Shader("basic-shader");
			RectRenderer skullRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), skull);
			skullRect.setShader(textureShader);
			addComponent(skullRect);
			
			Texture eyes = new Texture("backgrounds/menu_background_skull_eyes.png");
			distortionShader = new Shader("distort-shader");
			clearShader = new Shader("color-shader");
			clearShader.setUniform("color", new Color(0, 0, 0, 0));
			RectRenderer eyesRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), eyes);
			eyesRect.setTag("eyesRect");
			eyesRect.setShader(clearShader);
			
			eyesRect.setUniformConfig(new UniformConfig() 
			{ 
				public void setUniforms(Shader s) 
				{
					if(s.getFileName().equals("distort-shader")) 
					{
						s.setUniform("time", shaderTime);
						s.setUniform("frequency", 20.0f);
						s.setUniform("amplitude", 0.0005f);
					}
				}
			});
			
			addComponent(eyesRect);
			
			quit = new ClickZone(570, 0, 55, 128);
			
			addComponent(quit);
		}
		
		public void update()
		{
			((RectRenderer) getComponentWithTag("eyesRect")).setShader(quit.isHovered() ? distortionShader : clearShader);
			if(quit.isClicked())
			{
				System.exit(0);
			}
		}
		
	}
	
}
