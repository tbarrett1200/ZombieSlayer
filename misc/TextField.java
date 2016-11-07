package misc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import main.GameObject;
import main.Scene;

public class TextField extends GameObject{



	public TextField(Scene s, int x, int y, int width, int height) {
		super(s, x, y, width, height);
		offset=(int)(height*0.1);
		fontsize=(int)(0.75*(height-offset*2));
		font = new Font("Courier",Font.BOLD,fontsize);	}
	private Font font;
	private int offset;
	private int fontsize;
	private int fontheight;
	public boolean selected=false;
	public String placeholder = "type to enter text";
	public String text = "";
	
	
	public String stringToDisplay(Graphics g)
	{

		String toDisplay=new String(text);
		
		g.setFont(font);
		
		int dotdotdot = g.getFontMetrics(font).stringWidth("...");
		
		boolean tooBig = (g.getFontMetrics(font).stringWidth(toDisplay)>width);
		
		while(g.getFontMetrics(font).stringWidth(toDisplay)>width-offset*2-dotdotdot && toDisplay.length()>0)
		{
			toDisplay=toDisplay.substring(0,toDisplay.length()-1);
		}
		
		if (tooBig)
		{
			toDisplay+="...";
		}
		
		return toDisplay;
		
	}
	
	public void mousePressed(MouseEvent e)
	{
		if(contains(e.getX(),e.getY()))
		{
			selected=true;
		}
		else
		{
			selected=false;
		}
	}
	public void paint(Graphics g)
	{
		
		if (selected)
		{
			g.setColor(new Color(51,153,255));
			g.fillRect((int)(x-2), (int)y-2, (int)width+4, (int)height+4);
		}
		
		g.setColor(Color.WHITE);
		g.fillRect((int)x, (int)y,(int)width, (int)height);
		
		g.setColor(Color.BLACK);
		g.setFont(font);
		
		String toDisplay = stringToDisplay(g);
		
		if (text.equals(""))
		{
			g.setColor(Color.LIGHT_GRAY);
			toDisplay=placeholder;
		}
			
		fontheight=g.getFontMetrics(font).getHeight();
		
		g.drawString(toDisplay, (int)(x+offset), (int)(y+fontheight+offset));
		
	}

}
