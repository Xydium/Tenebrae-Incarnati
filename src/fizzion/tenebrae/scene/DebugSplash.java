package fizzion.tenebrae.scene;

import engine.components.RectRenderer;
import engine.components.RectRenderer.UniformConfig;
import engine.core.Scene;
import engine.math.Vector2;
import engine.rendering.Color;
import engine.rendering.Shader;
import engine.rendering.Texture;
import engine.utility.Time;
import engine.utility.Util;

public class DebugSplash extends Scene 
{
	
	private Texture splash;
	private Shader texShader, colorShader;
	
	private double startTime;
	
	public void activate()
	{
		splash = new Texture("backgrounds/prototype_build.png");
		texShader = new Shader("basic-shader");
		colorShader = new Shader("color-shader");
		final RectRenderer splashRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), splash);
		final RectRenderer veilRect = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), splash);
		splashRect.setShader(texShader);
		veilRect.setShader(colorShader);
		
		veilRect.setUniformConfig(new UniformConfig() 
		{
			public void setUniforms() 
			{
				veilRect.getShader().setUniform("color", new Color(0.0f, 0.0f, 0.0f, calculateAlpha()));
			}
		});
		
		getRootObject().addAllComponents(splashRect, veilRect);
		startTime = Time.getTime();
	}
	
	public void update()
	{
		if(Time.getTime() - startTime > 10)
		{
			getApplication().getGame().setScene(new MainMenu());
		}
	}
	
	public void deactivate()
	{
		splash = null;
		texShader = colorShader = null;
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
