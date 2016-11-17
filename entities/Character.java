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

@SuppressWarnings("serial")
public abstract class Character extends PhysicsObject{

	
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
	private Sprite[] walkCycle;	
	private int currentFrame=0;
	private long walkCycleFrameDuration=0;
	private long walkCycleFrameChange=0;
	
	/***************************************************************************
	* constructor
	***************************************************************************/
	public Character(GameScene scene, String name, int x, int y, int width, int height) {
		super(scene, name, x, y, width, height);
	}

	public boolean isWalkCycleSet() {
		return walkCycle != null;
	}
	public void setWalkCycle(Sprite img[],long duration) {
		walkCycle = img;
		walkCycleFrameDuration = duration;
	}
	public Sprite getSprite() {
		return walkCycle[currentFrame];
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
	private void updateWalkCycle()
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
