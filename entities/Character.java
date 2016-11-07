package entities;

import main.PhysicsObject;
import main.Sprite;
import scenes.GameScene;
import world.HealthBar;

/*******************************************************************************
 * Thomas Barrett
 * Period 1
 * Mar 24, 2016
 * Description: A base class for characters
 ******************************************************************************/

public class Character extends PhysicsObject{

	
	//direction character is facing
	public int facing=Right;
	
	//direction character is moving
	public int moving=None;

	//gun offset from center
	public int gunx;
	public int guny;
	
	//health bar
	public HealthBar health=new HealthBar(this);

	//walking animation data
	Sprite[] walkCycle;	
	int currentFrame=0;
	long walkCycleFrameDuration=0;
	long walkCycleFrameChange=0;
	
	/***************************************************************************
	* constructor
	***************************************************************************/
	public Character(GameScene scene, String name, int x, int y, int width, int height) {
		super(scene, name, x, y, width, height);
	}

	/***************************************************************************
	* updates object each frame
	***************************************************************************/
	public void update()
	{	
		//physics object update
		super.update();

		//updates the walk cycle if player is moving
		if (dx!=0) updateWalkCycle();
		
		//resets the walk cycle if player is standing
		else currentFrame=0;
	}
	
	/***************************************************************************
	* updates walk cycle image
	***************************************************************************/
	public void updateWalkCycle()
	{
		//gets current time in milliseconds
		long currentTime = System.currentTimeMillis();
			
		//if the frame duration has elapsed
		if (currentTime-walkCycleFrameChange >= walkCycleFrameDuration)
		{
			//rotate animation frame
			if (currentFrame<walkCycle.length-1) currentFrame++;
			else currentFrame=0;
			
			//set last frame change to current time
			walkCycleFrameChange=currentTime;
		}
	}
}
