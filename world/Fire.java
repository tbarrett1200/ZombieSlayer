package world;

/*******************************************************************************
 * Thomas Barrett
 * Period 1
 * Mar 24, 2016
 * Description: Fire created with a particle generator
 ******************************************************************************/

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import entities.Character;
import entities.Enemy;
import main.GameObject;
import main.PhysicsObject;
import scenes.GameScene;

public class Fire extends PhysicsObject{


	Character owner;
	static Random rnd=new Random();
	
	int maxdist=1000;
	int mindist=800;

	int directionLastCollision;

	/***************************************************************************
	* constructor
	***************************************************************************/
	public Fire(GameScene scene,Character owner,  int gunElevation) {
		super(scene, "fire", owner.x, owner.y, 6, 6);
		this.owner=owner;
		intersectsWith.add("enemy-bull");
		intersectsWith.add("enemy-zombie");
		intersectsWith.add("player");

		intersectsWith.add("solid-block");
		collidesWith.add("solid-block");


			dy=gunElevation+(int) (Math.random()*rnd.nextInt(3));

			if (owner.facing==Right)
			{
				x=owner.x+owner.width+owner.gunx;
				y=owner.y+owner.guny;
				dx=20+rnd.nextInt(7)-3;
			}
			else if (owner.facing==Left)
			{
				x=owner.x-owner.gunx-width;
				y=owner.y+owner.guny;
				dx=-20+rnd.nextInt(7)-3;
			}

	}

	/***************************************************************************
	* updates object each frame
	***************************************************************************/
	public void update()
	{
		if (Math.abs(x-owner.x)>(maxdist-rnd.nextInt(maxdist-mindist)) )
			scene.remove(this);

		dy+=(int) (Math.random()*(rnd.nextInt(5)-2));
		dx+=rnd.nextInt(5)-2;


		super.update();
	}
	
	/***************************************************************************
	* collision game logic
	***************************************************************************/
	public void didBeginIntersection(GameObject o)
	{
		if (o instanceof SolidBlock)
		{
			scene.remove(this);
		}
		else if (o instanceof Enemy)
		{
			double dist=Math.abs(x-owner.x);

			((Enemy)o).health.health-=0.01*Math.abs(((double)maxdist-dist)/maxdist);
		}
	}
	
	/***************************************************************************
	* paints object to screen
	***************************************************************************/
	public void paint(Graphics g)
	{
		int dist=Math.abs((int)(x-owner.x));

		g.setColor(new Color(Math.abs((255-rnd.nextInt(81))*(maxdist+200-dist)/(maxdist+200)),Math.abs((165-rnd.nextInt(81))*(maxdist+200-dist)/(maxdist+200)),0,Math.abs(255*(maxdist+50-dist)/(maxdist+50))));
		g.fillOval((int)x, (int)y, (int)width, (int)height);
	}

}
