package fizzion.tenebrae.launch;

import engine.core.Application;
import engine.rendering.WindowFlags;

public class Main 
{
	
	public static WindowFlags flags;
	
	public static void main(String[] args)
	{
		flags = new WindowFlags("Tenebrae Incarnati", 1280, 720);
		
		Application app = new Application(new TenebraeIncarnati(), 60.0, flags);
		app.start();
	}
	
}
