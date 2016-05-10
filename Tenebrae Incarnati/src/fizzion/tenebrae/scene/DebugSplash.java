package fizzion.tenebrae.scene;

import engine.components.RectRenderer;
import engine.core.Scene;
import engine.math.Vector2;
import engine.rendering.Shader;
import engine.rendering.Texture;
import engine.utility.Util;

public class DebugSplash extends Scene {

	private Texture splashScreen;
	private Shader alphaShader;
	
	private float alpha;
	
	public DebugSplash()
	{
		splashScreen = new Texture("prototype_build.png");
		alphaShader = new Shader("alpha-shader");
		
		RectRenderer r = new RectRenderer(Util.pixelDToGL(new Vector2(1280f, 720f)), splashScreen);
		r.setShader(alphaShader);
		r.setUniformConfig(() -> {
			alphaShader.setUniform("alpha", alpha);
		});
		
		rootObject.addComponent(r);
	}
	
}
