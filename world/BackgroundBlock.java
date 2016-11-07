package world;

/*******************************************************************************
 * Thomas Barrett
 * Period 1
 * Mar 24, 2016
 * Description: An image block that doesn't collide with player
 ******************************************************************************/

import java.awt.Graphics;
import java.util.HashMap;

import main.GameObject;
import main.Sprite;
import scenes.GameScene;

public class BackgroundBlock extends GameObject
{
	
	//the background type
	String type;
	
	//all possible background blocks accessed by a string with their name
	static HashMap<String,Sprite> img;
	
	//constructor
	public BackgroundBlock(GameScene scene, int x, int y, String type) {
		super(scene, x, y, 50, 50);
		
		//sets type
		this.type=type;
		
		//if images for this class have not yet been loaded
		if (img == null)
		{
			//creates a new HashMap object
			img = new HashMap<String,Sprite>();
			
			//loads all images
			img.put("brick", new Sprite("img/brick.png"));
		}
		
		if (!img.containsKey(type))
		{
			System.err.println("background type " + type + " not found: defaulting to brick");
			type="brick";
		}
		
		
	}

	//paints image to screen
	public void paint(Graphics g)
	{
		//paints the proper image
		img.get(type).paint(g, x, y, 50, 50, 0);
	}
}
