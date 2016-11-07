package world;

import java.awt.Graphics;

import main.GameObject;
import main.PhysicsObject;
import main.Sprite;
import scenes.GameScene;
;

public class Spikes extends PhysicsObject{

	
	static Sprite img = new Sprite("img/spikes.png");

	long damageInterval=500;
	static long lastDamage;
	static boolean hitPlayer=false;
	
	
	public Spikes(GameScene scene,  int x, int y) {
		super(scene, "spikes", x, y+25, 50, 25);
		intersectsWith.add("player");
		
	}
	
	public void update()
	{
		long currentTime = System.currentTimeMillis();
		
		if(hitPlayer && currentTime-lastDamage>damageInterval)
		{
			lastDamage=currentTime;
			((GameScene)scene).player.health.decreaseLife(1);;
		}
		super.update();
	}
	
	//start intersection
	public void didBeginIntersection(GameObject o) { hitPlayer = true; }
	
	//end intersection
	public void didEndIntersection(GameObject o)  { hitPlayer = false; }
	{
		hitPlayer=false;
	}
	
	public void paint(Graphics g)
	{
		img.paint(g, x, y-25, 50, 50, 0);
	}

}
