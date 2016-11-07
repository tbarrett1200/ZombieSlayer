package entities;

import java.awt.Graphics;

import main.GameObject;
import main.Sprite;
import scenes.GameScene;
import world.Gun;
import world.SolidBlock;

/*******************************************************************************
 * Thomas Barrett
 * Period 1
 * Mar 24, 2016
 * Description: Zombie
 ******************************************************************************/

public class Zombie extends Enemy{


	Gun gun;
	
	long lastShot;
	long shotRate=1000;
	
	//constructor
	public Zombie(GameScene scene, int x, int y) {
		super(scene, "enemy-zombie", x, y-20, 80, 120);

		//collisions
		intersectsWith.add("player");
		intersectsWith.add("solid-block");

		//defaults
		speed=4;
		coinValue=10;
		health.set(3);

		gun = new Gun(scene,this, "rocket");

		gunx=-20;
		guny=45;
				

		//if image set is not yet loaded
		if (walkCycle==null)
		{
			//creates the walk cycle Sprite array
			walkCycle = new Sprite[2];
			walkCycle[0] = new Sprite("img/zombie-walk-1.png");
			walkCycle[1] = new Sprite("img/zombie-walk-2.png");
		}
		
		//sets the time between image changes to be 200ms
		walkCycleFrameDuration = 200;

	}

	//updates enemy each frame
	public void update()
	{
		gun.update();

		if (objectInRange(((GameScene)scene).player, 300, 100) && directionComparedTo(((GameScene)scene).player)==Left && facing==Right)
		{
			gun.shoot();
		}
		else if (objectInRange(((GameScene)scene).player, 300, 100) && directionComparedTo(((GameScene)scene).player)==Right && facing==Left)
		{
			gun.shoot();
		}

		super.update();
	}
	
	//collision response
	public void didBeginIntersection(GameObject o)
	{	
		if (o instanceof Player) ((Player) o).health.decreaseLife(1);
		else if (o instanceof SolidBlock  && o.y>y+height) turnAround();
		super.didBeginIntersection(o);
	}
	
	//paints enemy and enemies gun
	public void paint(Graphics g)
	{
		super.paint(g);
		gun.paint(g);
	}
}
