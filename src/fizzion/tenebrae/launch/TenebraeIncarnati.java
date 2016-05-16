package fizzion.tenebrae.launch;

import java.util.HashMap;

import engine.core.Application;
import engine.core.Game;
import engine.core.Scene;
import engine.rendering.WindowFlags;
import engine.utility.Log;
import engine.utility.Log.LogLevel;
import fizzion.tenebrae.scene.DungeonSelect;
import fizzion.tenebrae.scene.MainMenu;

public class TenebraeIncarnati extends Game
{
	
	public static WindowFlags flags;
	
	private HashMap<String, Scene> scenes;
	
	public void start()
	{
		scenes = new HashMap<String, Scene>();
		scenes.put("MainMenu", new MainMenu());
		scenes.put("DungeonSelect", new DungeonSelect());
		
		setScene(scenes.get("MainMenu"));
	}
	
	public static void main(String[] args)
	{
		try
		{
			flags = new WindowFlags("Tenebrae Incarnati", 512 * 2, 288 * 2);
			Log.setLogLevel(LogLevel.INTERNAL);
			Log.setWindowEnabled(true);
			
			Application app = new Application(new TenebraeIncarnati(), 60.0, flags);
			app.start();
		}
		catch (Exception e)
		{
			Log.error(e);
			e.printStackTrace();
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
}
