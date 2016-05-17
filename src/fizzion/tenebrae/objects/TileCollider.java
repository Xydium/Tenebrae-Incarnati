package fizzion.tenebrae.objects;

import engine.components.RectRenderer;
import engine.components.RectRenderer.UniformConfig;
import engine.core.GameObject;
import engine.math.Vector2i;
import engine.physics.AABBCollider;
import engine.rendering.Color;
import engine.rendering.Shader;
import engine.rendering.Texture;

public class TileCollider extends GameObject
{
	public TileCollider(int x, int y, int maxX, int maxY)
	{
		float halfWidth = (float)maxX / 2;
		float halfHeight = (float)maxY / 2;
		
		float nx = (x - halfWidth) / halfWidth;
		float ny = (y - halfHeight) / halfHeight;
		
		getTransform().setPosition(nx, ny);
		
		//Vector2 size = new Vector2(1.f / (maxX / 2.f), 1.f / (maxY / 2.f));
		Vector2i size = new Vector2i(64, 64);
		
		addComponent(new AABBCollider(size));
		
		RectRenderer rr = new RectRenderer(size, new Texture("tiles/000.png"));
		rr.setShader(new Shader("color-shader"));
		
		rr.setUniformConfig(new UniformConfig()
		{

			@Override
			public void setUniforms(Shader s) {
				s.setUniform("color", new Color(0, 0, 1, 1));
			}
			
		});
		
		addComponent(rr);
	}
}
