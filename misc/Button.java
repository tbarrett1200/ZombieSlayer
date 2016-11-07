package misc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import main.GameObject;
import main.Scene;
import main.Sprite;

public class Button extends GameObject{

	public interface ButtonAction
	{
		public void doStuff();
	}
	
	String message;

	public Sprite imgStart, imgClick;
	
	public boolean visible=true;
	
	boolean clicked=false;
	
	Font font = new Font("Courier",Font.PLAIN,30);
	Color start=Color.WHITE, click=Color.LIGHT_GRAY;
	
	public Button(Scene scene, String message,int x, int y, int width, int height) {
		super(scene, x, y, width, height);
		this.message=message;
	}
	
	public void setColor(Color start, Color click)
	{
		this.start=start;
		this.click=click;
	}
	
	public void setImage(Sprite start, Sprite click)
	{
		imgStart=start;
		imgClick=click;
	}
	
	public void mousePressed(MouseEvent e)
	{

		if (visible && contains(e.getX(),e.getY()))
		{
			clicked=true;
		}			
	}
	
	public void mouseReleased(MouseEvent e, ButtonAction action)
	{
		if (visible && clicked==true)
		{
			action.doStuff();
			clicked=false;
		}
	}
	
	public void paint(Graphics g)
	{
		if (visible)
		{
		if (imgStart == null || imgClick == null)
		{
		g.setColor(click);
		g.fillRect((int)x-2, (int)y-2, (int)width+4, (int)height+4);
		if (clicked) g.setColor(click);
		else g.setColor(start);
		
		g.fillRect((int)x, (int)y, (int)width, (int)height);
		g.setColor(Color.BLACK);
		g.setFont(font);
		g.drawString(message, (int)(x+width/2-scene.stringWidth(g, font, message)/2), (int)(y+height/2+5));
		}
		else
		{
			if (clicked)
			{
				imgClick.paint(g, x, y, width, height, 0);
			}
			else
			{
				imgStart.paint(g, x, y, width, height, 0);
			}
		}
	}
	}

}
