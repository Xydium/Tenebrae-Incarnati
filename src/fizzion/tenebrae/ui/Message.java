package fizzion.tenebrae.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import engine.components.RectRenderer;
import engine.core.GameObject;
import engine.math.Vector2;
import engine.math.Vector2i;
import engine.rendering.Color;
import engine.rendering.Shader;
import engine.rendering.Texture;
import engine.utility.Log;
import engine.utility.Util;

/**
 * A message to display on the screen
 * @author Chris Jerrett
 * @version Alpha
 */
public class Message extends GameObject
{

	private String message;
	private Font font;
	private Vector2i loc;
	private Color color;

	public Message(String message, Font font, Color color, Vector2i loc) {
		this.message = message;
		this.font = font;
		this.color = color;
		this.loc = loc;
		BufferedImage img = combineLetters();
		Texture texture = new Texture(img);
		RectRenderer renderer = new RectRenderer(new Vector2i(img.getWidth(), img.getHeight()), texture);
		renderer.setShader(new Shader("texture-shader"));
		renderer.setAllowLighting(false);
		addComponent(renderer);
		getTransform().setPosition(loc);
	}

	public Message(String message, String font, int size, Color color, Vector2i loc) {
		this.message = message;
		this.loc = loc;
		loadFont(font, size);
	}

	private BufferedImage combineLetters()
	{
		List<BufferedImage> imgs = getLettersTextures();
		int width = 0;
		for(BufferedImage im : imgs)
		{
			width += im.getWidth();
		}
		BufferedImage result = new BufferedImage(width, imgs.get(0).getHeight(), BufferedImage.TYPE_INT_ARGB);
		int x = 0;
		for(BufferedImage img : imgs)
		{
			result.getGraphics().drawImage(img, x, 0, null);
			x += img.getWidth();
		}
		return result;
	}
	
	private void loadFont(String font, int size) {
		try {
			InputStream stream = getClass().getResourceAsStream("/assets/fonts/" + font + ".ttf");
			Font fnt = Font.createFont(Font.PLAIN, stream);
			fnt = fnt.deriveFont(size);
			this.font = fnt;
		} catch (FontFormatException e) {
			Log.error("Invalid FFT file: " + font + ".fft");
		} catch (IOException e) {
			Log.error("Could not find fft file: " + font + ".fft");
		}
	}

	/**
	 * @return the loc
	 */
	public Vector2i getLoc() {
		return loc;
	}

	public List<BufferedImage> getLettersTextures() {
		List<BufferedImage> imgs = new ArrayList<BufferedImage>();

		char[] letters = this.message.toCharArray();

		for(char letter : letters) {
			BufferedImage img = new BufferedImage(1,1, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = img.createGraphics();
			g2d.setFont(font);
			FontMetrics fm = g2d.getFontMetrics();
			int width = fm.stringWidth(String.valueOf(letter));
			int height = fm.getHeight();
			g2d.dispose();

			img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			g2d = img.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
			g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
			g2d.setFont(font);
			fm = g2d.getFontMetrics();
			g2d.setColor(Util.colorToAwt(color));
			g2d.drawString(String.valueOf(letter), 0, fm.getAscent());
			g2d.dispose();
			imgs.add(img);
		}

		return imgs;
	}

	/**
	 * @deprecated
	 */
	public List<BufferedImage> getRectangles(Shader texShader) {
		List<BufferedImage> textures = getLettersTextures();
		List<RectRenderer> rectangles = new ArrayList<RectRenderer>();

		int x = 0;
		for(BufferedImage bi : textures) {
			Texture texture = new Texture(bi);
			//RectRenderer rec = new RectRenderer(new Vector2(this.loc.getX() + x, this.loc.getY()), texture);
			//rec.setShader(texShader);
			//rectangles.add(rec);
			System.out.println(bi);
			x += bi.getWidth();
		}
		return null;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the font
	 */
	public Font getFont() {
		return font;
	}
}