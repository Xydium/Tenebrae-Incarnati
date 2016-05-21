package fizzion.tenebrae.scene;

import engine.audio.GlobalAudio;
import engine.components.RectRenderer;
import engine.components.RectRenderer.UniformConfig;
import engine.core.GameObject;
import engine.core.Scene;
import engine.math.Vector2i;
import engine.rendering.Color;
import engine.rendering.Shader;
import engine.rendering.Texture;
import engine.rendering.Window;
import fizzion.tenebrae.launch.TenebraeIncarnati;
import fizzion.tenebrae.ui.Button;
import fizzion.tenebrae.ui.ClickZoneListener;
import fizzion.tenebrae.ui.Message;
import fizzion.tenebrae.ui.Message.Placement;

/**
 * 
 * @author Tim Hornick
 *
 */
public class MainMenu extends Scene 
{
	
	private float shaderTime;
	
	public void load()
	{
		Background background = new Background();
		ObeliskText obeliskText = new ObeliskText();
		Skull skull = new Skull();
		Message m = new Message("PLAY", "Papyrus", 48, new Color(.75f, .3f, 1, 0.7f), new Vector2i(TenebraeIncarnati.WIDTH/2, 360), Placement.CENTER);
		Message m2 = new Message("QUIT", "Papyrus", 24, new Color(.75f, .3f, 1, 0.4f), new Vector2i(633, 450), Placement.BOTTOM_LEFT);
		
		getRootObject().addAllChildren(background, obeliskText, obeliskText.play, skull, skull.quit, m, m2);
		
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
			
			RectRenderer atmoRect = new RectRenderer(new Vector2i(1024, 576), atmo);
			RectRenderer platformRect = new RectRenderer(new Vector2i(1024, 576), platform);
			RectRenderer flickerRect = new RectRenderer(new Vector2i(1024, 576), atmo);
			RectRenderer titleRect = new RectRenderer(new Vector2i(1024, 576), title);
			
			Shader textureShader = new Shader("texture-shader");
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
					s.setUniform("color", new Color(0f, 0f, 0f, flickerOn ? 0.1f : 0f));
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
		
		public Button play;
		
		public ObeliskText()
		{
			setTag("obeliskText");
			
			Texture obetext = new Texture("backgrounds/menu_background_obetext.png");
			
			final RectRenderer obetextRect = new RectRenderer(new Vector2i(1024, 576), obetext);
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

			addComponent(obetextRect);
			
			play = new Button(360, 310, 300, 110);
			
			play.addListener(new ClickZoneListener()
			{
				public void onMouseEnter()
				{
					obetextRect.setShader(distortionShader);
				}

				public void onMouseLeave()
				{
					obetextRect.setShader(clearShader);
				}

				public void onMouseClicked()
				{
					TenebraeIncarnati ti = (TenebraeIncarnati)getApplication().getGame();
					ti.setScene(ti.getScene("DungeonSelect"));
				}
			});
		}
	}
	
	private class Skull extends GameObject
	{
		
		private Shader distortionShader;
		private Shader clearShader;
		
		public Button quit;
		
		public Skull()
		{
			setTag("skull");
			Texture skull = new Texture("backgrounds/menu_background_skull.png");
			Shader textureShader = new Shader("texture-shader");
			RectRenderer skullRect = new RectRenderer(new Vector2i(1024, 576), skull);
			skullRect.setShader(textureShader);
			addComponent(skullRect);
			
			Texture eyes = new Texture("backgrounds/menu_background_skull_eyes.png");
			distortionShader = new Shader("distort-shader");
			clearShader = new Shader("color-shader");
			clearShader.setUniform("color", new Color(0, 0, 0, 0));
			final RectRenderer eyesRect = new RectRenderer(new Vector2i(1024, 576), eyes);
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
			
			quit = new Button(570, Window.getHeight() - 130, 150, 50);
			
			quit.addListener(new ClickZoneListener()
			{
				public void onMouseEnter()
				{
					eyesRect.setShader(distortionShader);
				}

				public void onMouseLeave()
				{
					eyesRect.setShader(clearShader);
				}

				public void onMouseClicked()
				{
					getApplication().stop();
				}
			});
		}
	}
}
