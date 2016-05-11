package fizzion.tenebrae.scene;

import engine.components.RectRenderer;
import engine.core.Scene;
import engine.math.Vector2;
import engine.rendering.Shader;
import engine.rendering.Texture;
import engine.utility.Time;
import engine.utility.Util;

public class DebugSplash extends Scene 
{
	
	private Texture splash;
	private Shader splashShader;
	
	private double startTime;
	
	public void activate()
	{
		splash = new Texture("backgrounds/prototype_build.png");
		splashShader = new Shader("basic-shader");
		RectRenderer r = new RectRenderer(Util.pixelDToGL(new Vector2(1024f, 576f)), splash);
		r.setShader(splashShader);
		getRootObject().addComponent(r);
		startTime = Time.getTime();
	}
	
	public void update()
	{
		if(Time.getTime() - startTime > 3)
		{
			getApplication().getGame().setScene(new Scene());
		}
	}
	
	public void deactivate()
	{
		splash = null;
		splashShader = null;
	}
	
}
