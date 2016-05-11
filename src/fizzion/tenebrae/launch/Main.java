package fizzion.tenebrae.launch;

import engine.core.Application;
import engine.core.Game;
import engine.rendering.WindowFlags;
import engine.utility.Log.LogLevel;
import fizzion.tenebrae.scene.DebugSplash;

public class Main extends Game
{
	
	public static WindowFlags flags;
	
	public void start()
	{
		setScene(new DebugSplash());
	}
	
	public static void main(String[] args)
	{
		flags = new WindowFlags("Tenebrae Incarnati", 512 * 2, 288 * 2);
		flags.setLogLevel(LogLevel.INTERNAL);
		flags.setConsoleEnabled(true);
		
		Application app = new Application(new Main(), 60.0, flags);
		app.start();
	}
	
}
