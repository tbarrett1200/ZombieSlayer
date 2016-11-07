package entities;

import java.awt.Graphics;

import main.GameObject;
import main.Sprite;
import scenes.GameScene;
import world.Rock;
import world.SolidBlock;

/*******************************************************************************
 * Thomas Barrett
 * Period 1
 * Mar 24, 2016
 * Description: An enemy that throws rocks
 ******************************************************************************/

public class Glompus extends Enemy{

	//timing variables
	long lastThrow;
	long throwRate=3000;
	boolean attack=false;
	
	static Sprite attackImg = new Sprite("img/glompus-attack-1.png");

	public Glompus(GameScene scene, int x, int y) {
		super(scene, "enemy-glompus", x, y-20, 60, 120);
		
		//gets notified when colliding with player or solid-block
		intersectsWith.add("player");
		intersectsWith.add("solid-block");

		//sets walking speed to 4
		speed=4;

		//drops 10 coins on death
		coinValue=10;
				
		//sets health to 10
		health.set(3);
						
		//if animation images not yet loaded
		if (walkCycle==null)
		{
			walkCycle = new Sprite[2];
			walkCycle[0] = new Sprite("img/glompus-walk-1.png");
			walkCycle[1] = new Sprite("img/glompus-walk-2.png");
		}
		
		//set animation frame length
		walkCycleFrameDuration = 200;
	}
	


	public void update()
	{		
		Player player = ((GameScene)scene).player;
		
		//gets current time
		long currentTime = System.currentTimeMillis();

		//if player is in range
		if (objectInRange(player, 300, 100))
		{
			//if facing player on right
			if (directionComparedTo(player)==Left && facing==Right)
			{
				//stop and attack
				attack=true;
				speed=0;
				
				//throw rock
				if (currentTime-lastThrow>throwRate)
				{
					scene.add(new Rock((GameScene)scene,x,y-24,10,-8));
					lastThrow=currentTime;
				}
			}
			//if facing player on left
			else if (directionComparedTo(player)==Right && facing==Left)
			{
				//stop and attack
				attack=true;
				speed=0;

				//throw rock
				if (currentTime-lastThrow>throwRate)
				{
					scene.add(new Rock((GameScene)scene,x,y-24,-10,-8));
					lastThrow=currentTime;
				}
			}
		}
		else
		{
			//walk
			speed=4;
			attack=false;
		}

		super.update();
	}

	
	//runs collision game logic
	public void didBeginIntersection(GameObject o)
	{
		//enemy collision detection
		super.didBeginIntersection(o);

		//if collide with player
		if (o instanceof Player)
		{
			//decrement player health
			((Player) o).health.decreaseLife(1);
		}
		
		//if collide with wall
		else if (o instanceof SolidBlock && o.y>y+height)
		{
			turnAround();
		}		
	}
	
	//paints glompus
	public void paint(Graphics g)
	{
		if (!attack)
		{
			super.paint(g);
		}
		else
		{
			if (facing==Right)
			{
				attackImg.paint(g, x, y, width, height, 0);
			}
			else
			{
				attackImg.paint(g, x, y, width, height, Sprite.FLIP_HORIZONTAL);

			}
			health.paint(g);
		}
	}

}
