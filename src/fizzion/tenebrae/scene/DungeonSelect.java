package fizzion.tenebrae.scene;

import java.awt.Font;
import java.util.HashMap;

import engine.audio.GlobalAudio;
import engine.components.RectRenderer;
import engine.components.RectRenderer.UniformConfig;
import engine.core.GameObject;
import engine.core.Scene;
import engine.math.Vector2i;
import engine.rendering.Color;
import engine.rendering.Shader;
import engine.rendering.Texture;
import engine.rendering.Window;
import engine.utility.Log;
import engine.utility.Time;
import fizzion.tenebrae.launch.TenebraeIncarnati;
import fizzion.tenebrae.map.Dungeon;
import fizzion.tenebrae.ui.Button;
import fizzion.tenebrae.ui.ClickZoneListener;
import fizzion.tenebrae.ui.Message;

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

		RectRenderer background = new RectRenderer(new Vector2i(1024, 576));
		Shader s = new Shader("sinc-shader");
		s.setUniform("color", new Color(.75f, .3f, 1, 1f));
		background.setUniformConfig(new UniformConfig() {
			public void setUniforms(Shader s)
			{
				s.setUniform("deg", (int) Time.getTime() % 360);
			}
		});
		background.setShader(s);

		GameObject go = new GameObject();
		go.addComponent(background);

		GlobalAudio.playMusic("menu");
		currentSelection = DungeonSelection.CASTLE;
		final int startX = 32;
		for (int i = 0; i < 5; i++) {
			Texture castle = new Texture("ui/select_castle_icon.png");
			ClickZoneListener choose = new SelectionListener(i);
			Button b = new Button(startX + i * 192, Window.getHeight() / 2 - 192 / 2 - 30, 192, 192, castle);
			b.addListener(choose);
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

		Message title = new Message("D U N G E O N       S E L E C T", "Papyrus", 60, new Color(.85f, .4f, 1, 1f), new Vector2i(TenebraeIncarnati.WIDTH/2, 75), Message.Placement.CENTER);

		getRootObject().addAllChildren(go, selectButton, title);
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
