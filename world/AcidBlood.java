package world;

import java.awt.Graphics;

import entities.Player;
import main.GameObject;
import main.PhysicsObject;
import main.Sprite;
import scenes.GameScene;

public class AcidBlood extends PhysicsObject{
	
	//array of animation images
	static Sprite[] img = 
	{
		new Sprite("img/blood-blip.png"),
		new Sprite("img/blood-blop.png"),
		new Sprite("img/blood-bloop.png")
	};
	
	//animation data
	long frameLength=400;
	long lastFrameChange = 0;
	int currentFrame = 0;
	
	//constructor
	public AcidBlood(GameScene scene, double x, double y) {
		super(scene, "acid-blood", x, y, 12, 12);
		
		//adjusts for collision with solid-block
		collidesWith.add("solid-block");

		//gets notified for collisions with solid-block and player
		intersectsWith.add("solid-block");
		intersectsWith.add("player");
	
		//sets the last frame change to the current time
		lastFrameChange = System.currentTimeMillis();
	}
	
	//updates object each frame
	public void update()
	{
		//finds the current time
		long currentTime=System.currentTimeMillis();
		
		//if the proper time has elapsed and the current frame is not the last frame
		if (currentTime-lastFrameChange>frameLength && currentFrame<img.length-1)
		{
			//increment the current frame
			currentFrame++;
			lastFrameChange=currentTime;
		}
		
		//if the current frame is the last frame
		if (currentFrame==img.length-1)
		{
			//set the gravity to 1
			gravity=1;
		}
		
		//calculates physics and collisions
		super.update();
	}
	
	//runs game logic for collision
	public void didBeginIntersection(GameObject o)
	{
		//if collided with solid-block
		if (o instanceof SolidBlock)
		{ 
			//add 10 clouds
			for (int i=0; i<10; i++)
			{
				scene.add(new Cloud((GameScene)scene,x,y));
			}
			
			//disappear
			scene.remove(this);	
		}
		
		//if collided with the player
		else if (o instanceof Player)
		{
			//decrease player's life by 10
			((Player)o).health.decreaseLife(1);
			//disappear
			scene.remove(this);
		}
	}
	
	//paints image to screen
	public void paint(Graphics g)
	{
		//paint the current animation frame
		img[currentFrame].paint(g, x, y, width, height, 0);
	}

}
