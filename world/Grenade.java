package world;

/*******************************************************************************
 * Thomas Barrett
 * Period 1
 * Mar 24, 2016
 * Description: Grenade
 ******************************************************************************/

import java.awt.Graphics;

import entities.Character;
import main.GameObject;
import main.PhysicsObject;
import main.Sprite;
import scenes.GameScene;

public class Grenade extends PhysicsObject{


	long timeThrown;
	final long delay=1000;
	final long radius=200;
	
	Sprite img=new Sprite("img/grenade.png");
	
	/***************************************************************************
	* constructor
	***************************************************************************/
	public Grenade(GameScene scene, Character owner) {
		super(scene, "grenade", owner.x, owner.y, 20, 20);
		
		this.collidesWith.add("solid-block");
		this.collidesWith.add("enemy");

		if (owner.facing==Right)
		{
			x=owner.x+owner.width+owner.gunx;
			y=owner.y+owner.guny;
			dx=15;
		}
		else if (owner.facing==Left)
		{
			x=owner.x-owner.gunx-width;
			y=owner.y+owner.guny;
			dx=-15;
		}
		
		timeThrown=System.currentTimeMillis();
		
		dy=-10;
		
		this.gravity=1;
	}
	
	/***************************************************************************
	* BOOM!!!
	***************************************************************************/
	public void explode()
	{
		for(GameObject object:scene.scene)
		{
			if (object instanceof Character)
			{
				if (distance(object)<radius)
				{
					((Character)object).health.health-=2;
				}
			}
			
		}
		
		for(int i=0; i<100; i++)
		{
			scene.add(new Cloud((GameScene)scene,x,y));
		}
		scene.remove(this);
	}
	
	/***************************************************************************
	* collision game logic
	***************************************************************************/
	public void didBeginIntersection(GameObject o)
	{
		
		

		if (dx<0)
			dx=-1;
		if (dx>0)
			dx=1;
	}
	
	/***************************************************************************
	* updates object each frame
	***************************************************************************/
	public void update()
	{
		if (System.currentTimeMillis()-timeThrown>delay)
			explode();
		
		super.update();
	}
	
	/***************************************************************************
	* paints image to screen
	***************************************************************************/
	public void paint(Graphics g)
	{
		img.paint(g, x, y, width, height, 0);
	}

}
