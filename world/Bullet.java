package world;

import entities.Character;
import entities.Enemy;
import entities.Player;
import main.GameObject;
import main.PhysicsObject;
import scenes.GameScene;

/*******************************************************************************
 * Thomas Barrett
 * Period 1
 * Mar 24, 2016
 * Description: A basic projectile
 ******************************************************************************/

public class Bullet extends PhysicsObject
{
	
	//bullet speed
	int speed;
	
	//the gun that shot the bullet
	Gun owner;
	
	/***************************************************************************
	* constructor
	***************************************************************************/
	public Bullet(GameScene scene,Gun owner,int x,int y) {
		super(scene,"bullet", x, y, 8, 4);

		//will be notified about intersections with enemy and solid-block
		intersectsWith.add("enemy");
		intersectsWith.add("player");
		intersectsWith.add("solid-block");

		//location will be corrected when colliding with solid-block
		collidesWith.add("solid-block");
		
		//sets the owner
		this.owner=owner;
		
		speed=10;
		
		//adjusts speed depending on the direction of fire
		if (owner.facing()==Right)
		{
			dx=speed;
		}
		else if (owner.facing()==Left)
		{
			x-=width;
			dx=-speed;
		}
		
	}

	/***************************************************************************
	* collision game logic
	***************************************************************************/
	public void didBeginIntersection(GameObject o)
	{
		//if collides with solid block
		if (o instanceof SolidBlock)
		{
			scene.remove(this);
		}
		
		//if collides with character
		if (o instanceof Player && owner.owner instanceof Enemy)
		{	
			((Character)o).health.decreaseLife(1);
			scene.remove(this);
		}
		
		if (o instanceof Enemy && owner.owner instanceof Player)
		{	
			((Character)o).health.decreaseLife(1);
			scene.remove(this);
		}
	}
}
