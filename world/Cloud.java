package world;

/*******************************************************************************
 * Thomas Barrett
 * Period 1
 * Mar 24, 2016
 * Description: Cloud
 ******************************************************************************/

import java.awt.Graphics;
import java.util.Random;

import main.GameObject;
import main.Sprite;
import scenes.GameScene;

public class Cloud extends GameObject
{

	//random number generator
	static Random rnd = new Random();
	
	//time before cloud disappears
	long lifetime;
	//time that cloud was created
	long timeCreated;
	
	//cloud images
	static Sprite[] cloud;
	
	//cloud image type
	int type;
	
	/***************************************************************************
	* constructor
	***************************************************************************/
	public Cloud(GameScene scene, double x, double y) {
		super(scene,x+rnd.nextInt(100)-50, y+rnd.nextInt(50)-25, 16, 16);
		
		//sets the speed to a random number the range [-1,1] 
		dx=rnd.nextInt(3)-1;
		dy=rnd.nextInt(3)-1;
		
		//sets the time created to the current time
		timeCreated=System.currentTimeMillis();
		
		//sets lifetime to a random number from 1 ms to 2000ms;
		lifetime=rnd.nextInt(2000)+1;
		
		//if cloud images have not yet been loaded
		if (cloud==null)
		{
			cloud=new Sprite[3];
			cloud[0]=new Sprite("img/cloud-1.png");
			cloud[1]=new Sprite("img/cloud-2.png");
			cloud[2]=new Sprite("img/cloud-3.png");
		}
		
		//set cloud type to random type
		type = rnd.nextInt(3);
	}
	
	/***************************************************************************
	* updates object each frame
	***************************************************************************/
	public void update()
	{
		//updates physics object
		super.update();

		//if cloud has been present for longer than it's lifetime
		if (System.currentTimeMillis()-timeCreated>lifetime)
		{
			scene.remove(this);
		}
	}
	
	/***************************************************************************
	* updates object each frame
	***************************************************************************/
	public void paint(Graphics g)
	{
		//paints cloud
		cloud[type].paint(g, x, y, width, height, 0);
	}

}
