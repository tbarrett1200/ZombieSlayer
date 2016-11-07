package world;

import java.awt.Graphics;

import entities.Character;
import entities.Enemy;
import entities.Player;
import main.GameObject;
import main.PhysicsObject;
import main.Sprite;
import scenes.GameScene;

public class Laser extends PhysicsObject{

	Sprite img = new Sprite("img/bullet-laser.png");
	int speed;
	Gun owner;
	
	public Laser(GameScene scene, Gun owner, int x, int y) {
		super(scene, "laser", x, y, 16, 8);

		//will be notified about intersections with enemy and solid-block
		intersectsWith.add("enemy");
			intersectsWith.add("player");
			intersectsWith.add("solid-block");

			//location will be corrected when colliding with solid-block
			collidesWith.add("solid-block");
				
			//sets the owner
			this.owner=owner;
				
			speed=16;

			//adjusts speed depending on the direction of fire
			if (owner.facing()==Right)
			{
				this.x-=width/2;
				dx=speed;
			}
			else if (owner.facing()==Left)
			{
				this.x-=width/2;
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
	
	public void paint(Graphics g)
	{
		img.paint(g, x, y, width, height, 0);
	}


}
