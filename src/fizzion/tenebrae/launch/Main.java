package fizzion.tenebrae.launch;

import engine.core.Application;
import engine.core.Game;
import engine.rendering.WindowFlags;
import engine.utility.Log;
import engine.utility.Log.LogLevel;
import fizzion.tenebrae.scene.MainMenu;

public class Main extends Game
{
	
	public static WindowFlags flags;
	
	public void start()
	{
		setScene(new MainMenu());
	}
	
	public static void main(String[] args)
	{
		flags = new WindowFlags("Tenebrae Incarnati", 512 * 2, 288 * 2);
		Log.setLogLevel(LogLevel.INTERNAL);
		Log.setWindowEnabled(true);
		Application app = new Application(new Main(), 60.0, flags);
		app.start();
	}
	
}
