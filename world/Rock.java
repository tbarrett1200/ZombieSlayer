package world;

import java.awt.Graphics;

import entities.Player;
import main.GameObject;
import main.PhysicsObject;
import main.Sprite;
import scenes.GameScene;

public class Rock extends PhysicsObject{

	static Sprite img = new Sprite("img/rock.png");
	
	public Rock(GameScene scene, double x, double y, double dx, double dy) {
		super(scene, "rock", x, y, 48, 48);
		
		//will be notified about intersections
		collidesWith.add("solid-block");
		intersectsWith.add("solid-block");

		intersectsWith.add("player");

		//sets gravity to the scenes gravity
		this.setGravity(((GameScene)scene).gravity);
		
		this.dy=dy;
		this.dx=dx;
	}
	
	public void didBeginIntersection(GameObject o)
	{
		if (o instanceof Player)
		{
			((Player)o).health.decreaseLife(1);
			scene.remove(this);
		}
		else
		{
			scene.remove(this);
		}
	}
	public void paint(Graphics g)
	{
		img.paint(g, x, y, width, height, 0);
	}

}
