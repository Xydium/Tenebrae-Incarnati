package fizzion.tenebrae.scene;

import engine.core.Scene;
import engine.math.Vector2;
import engine.rendering.Texture;
import engine.utility.Log;
import engine.utility.Util;
import fizzion.tenebrae.map.Dungeon;
import fizzion.tenebrae.ui.Button;
import fizzion.tenebrae.ui.Button.ButtonCallback;

public class DungeonSelect extends Scene 
{

	private DungeonSelection currentSelection;
	
	private enum DungeonSelection
	{
		CASTLE("castle"),
		DESERT("desert"),
		UNDERWATER("underwater"),
		SKY("sky"),
		DREAM("dream");
		
		public String tag;
		
		private DungeonSelection(String tag)
		{
			this.tag = tag;
		}
	}
	
	public void activate()
	{
		final int startX = 32;
		for(int i = 0; i < 5; i++) {
			Texture castle = new Texture("ui/select_castle_icon.png");
			ButtonCallback choose = new SelectionCallback(i);
			Button b = new Button(startX + i * 192, 288 - 96, 192, 192, castle, choose);
			b.getTransform().setPosition(Util.pixelCToGL(new Vector2(startX * 4 + i * 192, 576)));
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
				getApplication().getGame().setScene(new Dungeon(currentSelection.tag));
			}
		};
		Button selectButton = new Button(1024 - 384, 15, 384, 96, select, selectCall);
		getRootObject().addChild(selectButton);
	}
	
	private class SelectionCallback implements ButtonCallback
	{
		private int i;
		
		public SelectionCallback(int i)
		{
			this.i = i;
		}
		
		public void hovered() {}
		public void unhovered() {}
		public void clicked() {
			currentSelection = DungeonSelection.values()[i];
			Log.info(currentSelection.name());
		}
	}
	
}
