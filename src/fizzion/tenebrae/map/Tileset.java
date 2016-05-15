package fizzion.tenebrae.map;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.rendering.Texture;

public class Tileset 
{
	private BufferedImage baseImage;

	private int squareSize;
	private int tileSetWidth;
	
	private String fileName;
	
	public Tileset(String fileName, int squareSize, int tsWidth)
	{
		this.fileName = fileName;
		baseImage = loadBufferedImage(fileName);
		this.squareSize = squareSize;
		this.tileSetWidth = tsWidth;
	}
	
	public BufferedImage getBufferedImageAt(int tileX, int tileY)
	{
		return baseImage.getSubimage(tileX * squareSize, tileY * squareSize, squareSize, squareSize);
	}
	
	public BufferedImage getBufferedImageAt(int i)
	{
		int x = i % tileSetWidth;
		int y = (i - x) / tileSetWidth;
		
		return getBufferedImageAt(x, y);
	}
	
	public Texture getTextureAt(int tileX, int tileY)
	{
		return new Texture(getBufferedImageAt(tileX, tileY), genTextureName(tileX, tileY));
	}
	
	public Texture getTextureAt(int i)
	{
		int x = i % tileSetWidth;
		int y = (i - x) / tileSetWidth;
		
		return getTextureAt(x, y);
	}
	
	public BufferedImage getBaseImage()
	{
		return baseImage;
	}
	
	public void setBaseImage(BufferedImage baseImage)
	{
		this.baseImage = baseImage;
	}
	
	private String genTextureName(int tileX, int tileY)
	{
		StringBuilder out = new StringBuilder();
		
		out.append("tilemaps/").append(fileName).append("_")
			.append(tileX).append(tileY);
		
		return out.toString();
	}
	
	private static BufferedImage loadBufferedImage(String name)
	{
		BufferedImage out = null;
		
		try
		{
			// tilemap format: assets/textures/tilemaps/dungeon.png
			out = ImageIO.read(Tileset.class.getResourceAsStream("/assets/textures/tilesets/" + name + ".png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return out;
	}
}
