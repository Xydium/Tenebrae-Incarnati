package fizzion.tenebrae.objects;

//import engine.components.RectRenderer;
//import engine.components.RectRenderer.UniformConfig;
import engine.collisions.AABBCollider;
import engine.core.GameObject;
import engine.math.Vector2i;

public class TileCollider extends GameObject
{
	private AABBCollider collider;
	
	public TileCollider(int x, int y)
	{
		getTransform().setPosition(x, y);
		
		collider = new AABBCollider(new Vector2i(64, 64));
		addComponent(collider);
		
		/*RectRenderer rr = new RectRenderer(new Vector2i(64, 64), new Texture("tiles/000.png"));
		rr.setShader(new Shader("color-shader"));
		
		rr.setUniformConfig(new UniformConfig()
		{

			@Override
			public void setUniforms(Shader s) {
				s.setUniform("color", new Color(0, 0, 1, 1));
			}
			
		});
		
		addComponent(rr);*/
	}
	
	public AABBCollider getCollider()
	{
		return collider;
	}
}
