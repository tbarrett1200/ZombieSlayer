package world;

import java.awt.Graphics;

import entities.Character;
import entities.Enemy;
import entities.Player;
import main.GameObject;
import main.PhysicsObject;
import main.Sprite;
import scenes.GameScene;

public class Rocket extends PhysicsObject{

	static Sprite img = new Sprite("img/rocket.png");
	
	int facing;
	
	Gun owner;
	
	/***************************************************************************
	*  constructor
	***************************************************************************/
	public Rocket(GameScene scene, Gun owner) {
		super(scene, "rocket", 0, 0, 40, 20);
		
		this.owner=owner;
		
		facing=owner.facing();
		if (facing==Right)
		{
			x=owner.barrelX();
			y=owner.barrelY();
			dx=10;
		}
		else if (facing==Left)
		{
			x=owner.barrelX();
			y=owner.barrelY();
			dx=-10;
		}
		collidesWith.add("solid-block");
		intersectsWith.add("solid-block");

		if (owner.owner instanceof Player)
			intersectsWith.add("enemy");
		if (owner.owner instanceof Enemy)
			intersectsWith.add("player");



	}
	
	/***************************************************************************
	* collision game logic
	***************************************************************************/
	public void didBeginIntersection(GameObject o)
	{
			scene.remove(this);
			
			for (int i=0; i<30; i++)
			{
				scene.add(new Cloud((GameScene)scene,x,y));
			}
			for (GameObject obj:scene.scene)
			{
				if (obj instanceof Player && distance(obj)<150 && owner.owner instanceof Enemy)
				{
					((Character)obj).health.health-=2;
				}
				if (obj instanceof Enemy && distance(obj)<150 && owner.owner instanceof Player)
				{
					((Character)obj).health.health-=2;
				}
			}
	}
	
	/***************************************************************************
	* paints to screen
	***************************************************************************/
	public void paint(Graphics g)
	{
		
		if (facing==Right)
		{
			img.paint(g, x, y, width, height, 0);
		}
		else
		{
			img.paint(g, x, y, width, height, Sprite.FLIP_HORIZONTAL);

		}
	}

}
