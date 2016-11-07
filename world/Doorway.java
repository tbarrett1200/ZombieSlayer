package world;

/*******************************************************************************
 * Thomas Barrett
 * Period 1
 * Mar 24, 2016
 * A doorway that transports player to next level
 ******************************************************************************/

import java.awt.Graphics;

import main.GameObject;
import main.Sprite;
import scenes.GameScene;

public class Doorway extends GameObject{

	
	//image of doorway
	Sprite img = new Sprite("img/doorway.png");
	
	/**************************************************************************
	 * constructor
	 *************************************************************************/
	public Doorway(GameScene scene, int x, int y)
	{
		super(scene, x, y, 100, 150);
	}
	
	/**************************************************************************
	 * changes scene to next level if intersected by player
	 *************************************************************************/
	public void update()
	{
		super.update();
		//if the player presses enter while intersecting the door
		if (intersects(((GameScene)scene).player))
		{
			((GameScene)scene).enterButton.visible=true;
			((GameScene)scene).inFrontOfDoor=true;

		}
		else
		{
			((GameScene)scene).enterButton.visible=false;
			((GameScene)scene).inFrontOfDoor=false;

		}
		
	}

		
					
	/**************************************************************************
	 * paints doorway image
	 *************************************************************************/
	public void paint(Graphics g)
	{
		img.paint(g, x, y, width, height, 0);
	}
}
