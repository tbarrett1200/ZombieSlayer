package entities;

import main.GameObject;
import main.Sprite;
import scenes.GameScene;
import world.SolidBlock;

/*******************************************************************************
 * Thomas Barrett
 * Period 1
 * Mar 24, 2016
 * Description: Bull
 ******************************************************************************/

public class Bull extends Enemy{

	
	//constructor
	public Bull(GameScene scene, int x, int y) {
		super(scene, "enemy-bull", x, y-20, 80, 120);
		
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
			walkCycle[0] = new Sprite("img/bull-walk-1.png");
			walkCycle[1] = new Sprite("img/bull-walk-2.png");
		}
		
		//set animation frame length
		walkCycleFrameDuration = 200;

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
}
