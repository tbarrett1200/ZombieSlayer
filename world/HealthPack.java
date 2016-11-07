package world;

import java.awt.Graphics;

import entities.Player;
import main.GameObject;
import main.PhysicsObject;
import main.Sprite;
import scenes.GameScene;

public class HealthPack extends PhysicsObject{


	static Sprite img = new Sprite("img/health-pack.png");
	
	/***************************************************************************
	* constructor
	***************************************************************************/
	public HealthPack(GameScene scene,int x, int y) {
		super(scene, "health-pack", x, y, 40, 40);
		intersectsWith.add("player");
		
	}
	
	/***************************************************************************
	* collision game logic
	***************************************************************************/
	public void didBeginIntersection(GameObject o)
	{
		if (o instanceof Player)
		{
			((Player)o).health.health=((Player)o).health.max;
			scene.remove(this);
		}
	}
	
	/***************************************************************************
	* updates object each frame
	***************************************************************************/
	public void paint(Graphics g)
	{
		img.paint(g, x, y, width, height, 0);
	}

}
