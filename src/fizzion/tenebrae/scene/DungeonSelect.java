package fizzion.tenebrae.scene;

import engine.core.Scene;
import engine.rendering.Texture;
import fizzion.tenebrae.ui.Button;
import fizzion.tenebrae.ui.Button.ButtonCallback;

public class DungeonSelect extends Scene 
{

	public void activate()
	{
		Texture castle = new Texture("ui/select_castle_icon.png");
		ButtonCallback choose = new ButtonCallback() {
			public void hovered() {}
			public void unhovered() {}
			public void clicked() 
			{
			}
		};
		final int startX = 32;
		for(int i = 0; i < 5; i++) {
			Button b = new Button(startX + i * 192, 576 - (int) (96 * 1.5f), 192, 192, castle, choose);
			getRootObject().addChild(b);
		}
		
		Texture back = new Texture("ui/select_back_to_menu.png");
		ButtonCallback backCall = new ButtonCallback() {
			public void hovered() {}
			public void unhovered() {}
			public void clicked() 
			{
				getApplication().getGame().setScene(new MainMenu());
			}
		};
		Button backButton = new Button(15, 15, 384, 96, back, backCall);
		getRootObject().addChild(backButton);
		Texture select = new Texture("ui/select_choose_dungeon.png");
		ButtonCallback selectCall = new ButtonCallback() {
			public void hovered() {}
			public void unhovered() {}
			public void clicked() 
			{
				getApplication().getGame().setScene(new MainMenu());
			}
		};
		Button selectButton = new Button(1024 - 384, 15, 384, 96, select, selectCall);
		getRootObject().addChild(selectButton);
	}
	
}
