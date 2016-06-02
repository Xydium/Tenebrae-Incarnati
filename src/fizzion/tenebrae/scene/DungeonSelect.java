package fizzion.tenebrae.scene;

import java.util.HashMap;

import engine.audio.GlobalAudio;
import engine.components.RectRenderer;
import engine.components.TextRenderer;
import engine.components.UniformConfig;
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

/**
 * 
 * @author Tim Hornick
 *
 */
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

		Texture bg = new Texture("backgrounds/select_blur.png");
		RectRenderer background = new RectRenderer(new Vector2i(1024 + 256, 576 + 256), bg);
		background.setAllowLighting(false);
		Shader s = new Shader("distort-shader");
		background.setUniformConfig(new UniformConfig() {
			public void setUniforms(Shader s)
			{
				s.setUniform("time", (float) Time.getTime());
				s.setUniform("frequency", 1f);
				s.setUniform("amplitude", 0.01f);
			}
		});
		background.setShader(s);

		GameObject go = new GameObject();
		go.addComponent(background);
		go.getTransform().setGlobalPosition(new Vector2i(-128, -128));

		GlobalAudio.playMusic("menu");
		currentSelection = DungeonSelection.CASTLE;
		Texture castle = new Texture("ui/select_castle_icon.png");
		ClickZoneListener choose = new SelectionListener(0);
		Button b = new Button(512 - 192 / 2, Window.getHeight() / 2 - 192 / 2, 192, 192, castle);
		b.addListener(choose);

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
				GlobalAudio.playSound("button_click", 0.15);
				TenebraeIncarnati ti = (TenebraeIncarnati)getApplication().getGame();
				ti.setScene(ti.getScene("MainMenu"));
			}
		};
		Button backButton = new Button(15, Window.getHeight() - 15 - 96, 384, 96, back);
		backButton.addListener(backCall);
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
				GlobalAudio.playSound("button_click", 0.15);
				GlobalAudio.stopMusic("menu");
				loadDungeon(currentSelection);
			}
		};
		Button selectButton = new Button(1024 - 384, Window.getHeight() - 15 - 96, 384, 96, select);
		selectButton.addListener(selectCall);

		GameObject titleOb = new GameObject();
		TextRenderer title = new TextRenderer("D U N G E O N       S E L E C T", "Papyrus", 60, new Color(.85f, .4f, 1, 1f));
		titleOb.addComponent(title);
		title.getTransform().setPosition(new Vector2i(512, 80));
		
		getRootObject().addAllChildren(go, b, backButton, selectButton, titleOb);
	}

	public void reloadCurrentDungeon()
	{
		loadedDungeons.put(currentSelection, new Dungeon(currentSelection.tag));
		TenebraeIncarnati ti = (TenebraeIncarnati)getApplication().getGame();
		ti.setScene(loadedDungeons.get(currentSelection));
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
		reloadCurrentDungeon();
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
			GlobalAudio.playSound("button_click", 0.15);
			currentSelection = DungeonSelection.values()[i];
			Log.info(currentSelection.name());
		}
	}

}
