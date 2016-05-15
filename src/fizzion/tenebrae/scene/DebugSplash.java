package fizzion.tenebrae.scene;

import engine.components.RectRenderer;
import engine.components.RectRenderer.UniformConfig;
import engine.core.GameObject;
import engine.core.Scene;
import engine.math.Vector2;
import engine.rendering.Color;
import engine.rendering.Shader;
import engine.rendering.Texture;
import engine.utility.Time;
import engine.utility.Util;

public class DebugSplash extends Scene 
{
	
	private double startTime;
	
	public void activate()
	{
		GameObject splash = new Splash();
		GameObject veil = new Veil();
		
		getRootObject().addAllChildren(splash);
		startTime = Time.getTime();
	}
	
	public void update()
	{
		if(Time.getTime() - startTime > 10)
		{
			getApplication().getGame().setScene(new MainMenu());
		}
	}
	
	private class Splash extends GameObject
	{
		public Splash()
		{
			Texture splash = new Texture("backgrounds/prototype_build.png");
			Shader textureShader = new Shader("basic-shader");
			RectRenderer splashRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), splash);
			splashRect.setShader(textureShader);
			addComponent(splashRect);
		}	
	}
	
	private class Veil extends GameObject
	{
		public Veil()
		{
			Shader colorShader = new Shader("color-shader");
			RectRenderer veilRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), null);
			veilRect.setShader(colorShader);
			veilRect.setUniformConfig(new UniformConfig() 
			{
				public void setUniforms(Shader s) 
				{
					s.setUniform("color", new Color(0.0f, 0.0f, 0.0f, calculateAlpha()));
				}
			});
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
