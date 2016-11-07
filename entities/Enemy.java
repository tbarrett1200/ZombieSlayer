package entities;

import java.awt.Graphics;

import main.GameObject;
import main.Sprite;
import scenes.GameScene;
import world.Coin;
import world.SolidBlock;

/*******************************************************************************
 * Thomas Barrett
 * Period 1
 * Mar 24, 2016
 * Description: A base class for enemies
 ******************************************************************************/

public class Enemy extends Character
{

	//walking speed in pixels per frame
	int speed;
	
	//amount of coins dropped
	int coinValue;

	
	/***************************************************************************
	*  constructor
	***************************************************************************/
	public Enemy(GameScene scene, String name, int x, int y, int width, int height) {
		super(scene, name, x, y, width, height);
		
		//initial position is facing and moving right
		facing=Right;
		moving=Right;
		
		//set collision detection
		collidesWith.add("solid-block");
		
		//sets gravity
		this.setGravity(scene.gravity);
	}

	/***************************************************************************
	* checks whether enemy has a gap in front of it
	***************************************************************************/
	public boolean foundEdge()
	{
		for (GameObject o:scene.scene)
		{
			if (o instanceof SolidBlock)
			{
				if (facing==Right)
				{
					if (o.contains(x+width+dx+1,y+height+dy+1))
						return false;
				}
				else
				{
					if (o.contains(x+dx+1,y+height+dy+1))
						return false;
				}
			}
		}
		return true;
	}

	/***************************************************************************
	* updates object each frame
	***************************************************************************/
	public void turnAround()
	{
		if (facing==Left)
		{
			facing=Right;
			if (moving!=None)
			{
				moving=Right;
			}
		}
		else
		{
			facing=Left;
			if (moving!=None)
			{
				moving=Left;
			}
		}
		
	}
	
	/***************************************************************************
	* deletes enemy and adds coins
	***************************************************************************/
	public void blowUpCoins()
	{
		if (health.health<=0)
		{
			for (int i=0; i<coinValue; i++)
			{
				scene.add(new Coin((GameScene)scene,x,y));
			}
			scene.remove(this);
		}
	}
	
	/***************************************************************************
	* updates object each frame
	***************************************************************************/
	public void update()
	{
		
		if (moving==Right) dx=speed;
		else if (moving==Left) dx=-speed;
		
		if (moving!=None)
			updateWalkCycle();
		
		if (health.health<=0)
		{
			scene.remove(this);
			blowUpCoins();
		}
			
		if (touchingSides())
		{
			turnAround();
			if (facing==Left)
				x-=10;
			else
				x+=10;
				
		}
		else if (foundEdge())
		{
			turnAround();
			if (facing==Left)
				x-=10;
			else
				x+=10;
				
		}
		
		super.update();
		
		
	}
	
	/***************************************************************************
	* paints object to screen
	***************************************************************************/
	public void paint(Graphics g)
	{
		if (facing==Left)
		{
			walkCycle[currentFrame].paint(g, x, y, width, height, Sprite.FLIP_HORIZONTAL);
		}
		else
		{
			walkCycle[currentFrame].paint(g, x, y, width, height, 0);
		}
		
		health.paint(g);
		
	}
}
