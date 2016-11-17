package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {

	public static int FLIP_VERTICAL=1;
	public static int FLIP_HORIZONTAL=2;
	
	public BufferedImage original;
	BufferedImage vertical;
	BufferedImage horizontal;
	BufferedImage rotate90;

	public int width,height;
	
	public Sprite(String path)
	{
		try {
			original=ImageIO.read(new File(path));
			vertical=reflectVertical(original);
			horizontal=reflectHorizontal(original);
			rotate90=reflectVertical(reflectHorizontal(original));
			width=original.getWidth();
			height=original.getHeight();
		} catch (IOException e) {
			System.out.println("Image at " + path + " not found");
		}
	}
	
	public void paint(Graphics g, double x, double y, double width2, double height2, int rotate)
	{
		
		if ((rotate&FLIP_VERTICAL) != 0)
		{
			if ((rotate&FLIP_HORIZONTAL) != 0)
			{
				g.drawImage(rotate90, (int)x, (int)y, (int)width2, (int)height2, null);
			}
			else
			{
				g.drawImage(vertical, (int)x, (int)y, (int)width2, (int)height2, null);
			}
		}
		else if ((rotate&FLIP_HORIZONTAL) != 0)
		{
			g.drawImage(horizontal, (int)x, (int)y, (int)width2, (int)height2, null);
		}
		else
		{
			g.drawImage(original, (int)x, (int)y, (int)width2, (int)height2, null);
		}
	}
	
	public BufferedImage reflectHorizontal(BufferedImage a)
	{
		BufferedImage b = new BufferedImage(a.getWidth(),a.getHeight(),a.getType());
		
		for (int x=0; x<a.getWidth(); x++)
		{
			for (int y=0; y<a.getHeight(); y++)
			{
				b.setRGB(a.getWidth()-x-1, y, a.getRGB(x, y));
			}
		}
		
		return b;
	}
	
	public BufferedImage reflectVertical(BufferedImage a)
	{
		BufferedImage b = new BufferedImage(a.getWidth(),a.getHeight(),a.getType());
		
		for (int x=0; x<a.getWidth(); x++)
		{
			for (int y=0; y<a.getHeight(); y++)
			{
				b.setRGB(x, a.getHeight()-y-1, a.getRGB(x, y));
			}
		}
		
		return b;
	}
}
