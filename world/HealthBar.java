package world;

/*******************************************************************************
 * Thomas Barrett
 * Period 1
 * Mar 24, 2016
 * Description: A health-bar
 ******************************************************************************/

import java.awt.Color;
import java.awt.Graphics;

import entities.Character;
import main.GameObject;

public class HealthBar extends GameObject{


	GameObject owner;

	public int max=0;
	public int health=0;

	int width=100;
	int height=10;
	
	/***************************************************************************
	* constructor
	***************************************************************************/
	public HealthBar(Character o) {
		super(null,0,0,100,10);
		owner=o;
	}
	
	public void set(int max)
	{
		this.max=max;
		health=max;
	}
	/***************************************************************************
	* updates object each frame
	***************************************************************************/
	public void update()
	{
		x=owner.x+owner.width/2-width/2;
		y=owner.y-height-10;
	}
	/***************************************************************************
	* decreases life by specified amount
	***************************************************************************/
	public void decreaseLife(int amount)
	{
		health-=amount;
		health = (health>=0) ? health : 0;
	}
	
	/***************************************************************************
	* updates object each frame
	***************************************************************************/
	public void paint(Graphics g)
	{
		update();
		g.setColor(Color.BLACK);
		g.drawRect((int)x, (int)y, (int)width, (int)height);
		g.setColor(Color.RED);
		g.fillRect((int)x+1, (int)y+1, (int)width-1, (int)height-1);
		g.setColor(Color.GREEN);
		g.fillRect((int)x+1, (int)y+1, (int)(((double) health/max)*width)-1, (int)height-1);
	}

}
