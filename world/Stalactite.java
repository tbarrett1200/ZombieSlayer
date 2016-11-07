package world;

import java.awt.Graphics;
import java.util.Random;

import main.GameObject;
import main.Sprite;
import scenes.GameScene;

public class Stalactite extends GameObject{


	static Sprite img = new Sprite("img/stalactite.png");
	
	long dripRate;
	long lastDrip;
	
	Random rnd = new Random();
	
	public Stalactite(GameScene s, int x, int y) {
		super(s, x, y, 50, 50);
		dripRate=(int)(Math.random()*5000+3000);
	}

	public void update()
	{
		long currentTime=System.currentTimeMillis();
		if(currentTime-lastDrip>dripRate)
		{
			lastDrip=currentTime;
			scene.add(new AcidBlood((GameScene)scene,x+11,y+45));
		}
		
		super.update();
	}
	public void paint(Graphics g)
	{
		img.paint(g, x, y, width, height, 0);
	}
}
