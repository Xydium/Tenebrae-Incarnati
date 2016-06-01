package fizzion.tenebrae.launch;

import java.util.HashMap;

import engine.audio.GlobalAudio;
import engine.core.Application;
import engine.core.Game;
import engine.core.Input;
import engine.core.Scene;
import engine.rendering.WindowFlags;
import engine.utility.Log;
import engine.utility.Log.LogLevel;
import fizzion.tenebrae.scene.DebugSplash;
import fizzion.tenebrae.scene.DungeonSelect;
import fizzion.tenebrae.scene.MainMenu;

/**
 * 
 * @author Tim Hornick
 * @author Lenny Litvak
 *
 */
public class TenebraeIncarnati extends Game
{

	public static WindowFlags flags;

	private HashMap<String, Scene> scenes;

	public final static int WIDTH = 512 * 2;
	public final static int HEIGHT = 288 * 2;

	public void start()
	{
		scenes = new HashMap<String, Scene>();
		scenes.put("DebugSplash", new DebugSplash());
		scenes.put("MainMenu", new MainMenu());
		scenes.put("DungeonSelect", new DungeonSelect());

		setScene(scenes.get("DebugSplash"));
		
		GlobalAudio.addSound("button_click", "assets/sfx/button_click.wav");
	}

	public static void main(String[] args)
	{
		try
		{
			flags = new WindowFlags("Tenebrae Incarnati", WIDTH, HEIGHT);
			Log.setLogLevel(LogLevel.INTERNAL);
			Log.setWindowEnabled(true);

			flags.setIconFiles("", "");

			Application app = new Application(new TenebraeIncarnati(), 60.0, flags);
			app.start();
		}
		catch (Exception e)
		{
			Log.error(e);
			e.printStackTrace();
		}
	}

	public void input()
	{
		if (Input.getKeyDown(Input.KEY_ESCAPE))
		{
			String name = getApplication().getGame().getScene().getClass().getSimpleName();

			if(name.equals("MainMenu"))
			{
				getApplication().stop();
			}
			else if(name.equals("DungeonSelect"))
			{
				setScene(getScene("MainMenu"));
			}
			else if(name.equals("Dungeon"))
			{
				setScene(getScene("DungeonSelect"));
			}
		}
	}

	public void addScene(String name, Scene scene)
	{
		scenes.put(name, scene);
	}

	public Scene getScene(String name)
	{
		return scenes.get(name);
	}
	
	@Override
	public void setScene(Scene scene) {
		super.setScene(scene);
		if(!scene.getClass().getSimpleName().equals("Dungeon")) {
			try {
				getApplication().getRenderingEngine().setOverlayBrightness(1);
			} catch(Exception e) {}
		}
	}
	
}
