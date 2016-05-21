package fizzion.tenebrae.scene;

import engine.components.RectRenderer;
import engine.components.RectRenderer.UniformConfig;
import engine.core.GameObject;
import engine.core.Input;
import engine.core.Scene;
import engine.math.Vector2i;
import engine.rendering.Color;
import engine.rendering.Shader;
import engine.rendering.Texture;
import engine.utility.Time;
import fizzion.tenebrae.launch.TenebraeIncarnati;

/**
 *
 * @author Tim Hornick
 *
 */
public class DebugSplash extends Scene
{

	private double startTime;

	public void load()
	{
		GameObject splash = new Splash();
		GameObject veil = new Veil();

		getRootObject().addAllChildren(splash, veil);
		startTime = Time.getTime();
	}

	public void input()
	{
		if (Input.getMouseDown(Input.MOUSE_LEFT) || Input.getKeyDown(Input.KEY_ESCAPE))
		{
			TenebraeIncarnati ti = (TenebraeIncarnati)getApplication().getGame();
			ti.setScene(ti.getScene("MainMenu"));
		}
	}

	public void update()
	{
		if(Time.getTime() - startTime > 5)
		{
			TenebraeIncarnati ti = (TenebraeIncarnati)getApplication().getGame();
			ti.setScene(ti.getScene("MainMenu"));
		}
	}

	private class Splash extends GameObject
	{
		public Splash()
		{
			Texture splash = new Texture("backgrounds/prototype_build.png");
			Shader textureShader = new Shader("texture-shader");
			RectRenderer splashRect = new RectRenderer(new Vector2i(1024, 576), splash);
			splashRect.setAllowLighting(false);
			splashRect.setShader(textureShader);
			addComponent(splashRect);
		}
	}

	private class Veil extends GameObject
	{
		public Veil()
		{
			Shader colorShader = new Shader("color-shader");
			RectRenderer veilRect = new RectRenderer(new Vector2i(1024f, 576f), null);
			veilRect.setAllowLighting(false);
			veilRect.setShader(colorShader);
			veilRect.setUniformConfig(new UniformConfig()
			{
				public void setUniforms(Shader s)
				{
					s.setUniform("color", new Color(0.0f, 0.0f, 0.0f, calculateAlpha()));
				}
			});
			addComponent(veilRect);
		}

		private float calculateAlpha()
		{
			float timeDif = (float) (Time.getTime() - startTime);
			if(timeDif <= 3)
			{
				return 1.0f - (timeDif / 3);
			}
			else if(timeDif >= 7)
			{
				return (timeDif - 7) / 3;
			}
			return 0.0f;
		}
	}

}
