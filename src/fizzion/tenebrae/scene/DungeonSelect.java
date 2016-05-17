package fizzion.tenebrae.scene;

import java.util.HashMap;

import engine.audio.GlobalAudio;
import engine.core.Scene;
import engine.math.Vector2i;
import engine.rendering.Texture;
import engine.rendering.Window;
import engine.utility.Log;
import fizzion.tenebrae.launch.TenebraeIncarnati;
import fizzion.tenebrae.map.Dungeon;
import fizzion.tenebrae.ui.Button;
import fizzion.tenebrae.ui.ClickZoneListener;

public class DungeonSelect extends Scene 
{
	private DungeonSelection currentSelection;
	private HashMap<DungeonSelection, Dungeon> loadedDungeons;
	
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
	
	public void load()
	{
		loadedDungeons = new HashMap<DungeonSelection, Dungeon>();
		
		GlobalAudio.playMusic("menu");
		currentSelection = DungeonSelection.CASTLE;
		final int startX = 32;
		for (int i = 0; i < 5; i++) {
			Texture castle = new Texture("ui/select_castle_icon.png");
			ClickZoneListener choose = new SelectionListener(i);
			Button b = new Button(startX + i * 192, Window.getHeight() / 2 - 192 / 2 - 30, 192, 192, castle);
			b.addListener(choose);
			//.getTransform().setPosition(new Vector2i(startX * 4 + i * 192, 576));
			getRootObject().addChild(b);
		}
		
		Texture back = new Texture("ui/select_back_to_menu.png");
		ClickZoneListener backCall = new ClickZoneListener() {
			@Override
			public void onMouseEnter() {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onMouseLeave() {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onMouseClicked() {
				// TODO Auto-generated method stub
				TenebraeIncarnati ti = (TenebraeIncarnati)getApplication().getGame();
				ti.setScene(ti.getScene("MainMenu"));
			}
		};
		Button backButton = new Button(15, Window.getHeight() - 15 - 96, 384, 96, back);
		backButton.addListener(backCall);
		getRootObject().addChild(backButton);
		Texture select = new Texture("ui/select_choose_dungeon.png");
		ClickZoneListener selectCall = new ClickZoneListener() {
			@Override
			public void onMouseEnter() {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onMouseLeave() {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onMouseClicked() {
				GlobalAudio.stopMusic("menu");
				loadDungeon(currentSelection);
			}
		};
		Button selectButton = new Button(1024 - 384, Window.getHeight() - 15 - 96, 384, 96, select);
		selectButton.addListener(selectCall);
		
		getRootObject().addChild(selectButton);
	}
	
	public void activate()
	{
		currentSelection = DungeonSelection.CASTLE;
	}
	
	private void loadDungeon(DungeonSelection dSel)
	{
		Dungeon dungeon = loadedDungeons.get(dSel);
		
		if (dungeon == null)
		{
			dungeon = new Dungeon(dSel.tag);
			loadedDungeons.put(dSel, dungeon);
		}
		
		TenebraeIncarnati ti = (TenebraeIncarnati)getApplication().getGame();
		ti.setScene(dungeon);
	}
	
	private class SelectionListener implements ClickZoneListener
	{
		private int i;
		
		public SelectionListener(int i)
		{
			this.i = i;
		}

		@Override
		public void onMouseEnter() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onMouseLeave() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onMouseClicked() {
			currentSelection = DungeonSelection.values()[i];
			Log.info(currentSelection.name());
		}
	}
	
}
