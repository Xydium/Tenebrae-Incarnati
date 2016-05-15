package fizzion.tenebrae.scene;

import engine.core.Scene;
import engine.rendering.Texture;
import fizzion.tenebrae.ui.Button;
import fizzion.tenebrae.ui.Button.ButtonCallback;

public class DungeonSelect extends Scene 
{

	public void activate()
	{
		Texture texture = new Texture("ui/select_castle_icon.png");
		ButtonCallback action = new ButtonCallback() {
			public void hovered() {}
			public void unhovered() {}
			public void clicked() 
			{
				System.exit(0);
			}
		};
		final int startX = 64;
		for(int i = 0; i < 5; i++) {
			Button b = new Button(startX + i * 192, 288 - 64, 128, 128, texture, action);
			getRootObject().addChild(b);
		}
	}
	
}
