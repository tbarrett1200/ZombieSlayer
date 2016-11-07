package world;

import java.awt.Graphics;

import main.GameObject;
import main.PhysicsObject;
import main.Sprite;
import scenes.GameScene;

public class LavaBlock extends PhysicsObject{


	static Sprite[] lava = {new Sprite("img/lava-1.png"),new Sprite("img/lava-2.png")};
	
	int currentFrame=0;
	long lastUpdate;
	long frameLength=100;
	
	long damageInterval=500;
	static long lastDamage;
	static boolean hitPlayer=false;
	
	public LavaBlock(GameScene scene, int x, int y) {
		super(scene, "lava", x, y, 50, 50);
		intersectsWith.add("player");
		hitPlayer=false;
	}

	/***************************************************************************
	* updates object each frame
	***************************************************************************/
	public void update()
	{
		long currentTime = System.currentTimeMillis();
		
		if (currentTime-lastUpdate>frameLength)
		{
			lastUpdate=currentTime;
			
			if (currentFrame==lava.length-1)
				currentFrame=0;
			else
				currentFrame++;
		}
		if(hitPlayer && currentTime-lastDamage>damageInterval)
		{
			lastDamage=currentTime;
			((GameScene)scene).player.health.decreaseLife(1);;
		}
		super.update();
	}
	
	/***************************************************************************
	* collision game logic: start collision
	***************************************************************************/
	public void didBeginIntersection(GameObject o)
	{
		hitPlayer=true;
	}
	
	/***************************************************************************
	* collision game logic: end collision
	***************************************************************************/
	public void didEndIntersection(GameObject o)
	{
		hitPlayer=false;
	}
	
	/***************************************************************************
	* updates object each frame
	***************************************************************************/
	public void paint(Graphics g)
	{
		lava[currentFrame].paint(g, x, y, width, height, 0);
	}
}
