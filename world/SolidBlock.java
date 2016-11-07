package world;

/*******************************************************************************
 * Thomas Barrett
 * Period 1
 * Mar 24, 2016
 * Description: A solid block
 ******************************************************************************/

import java.awt.Graphics;
import java.util.HashMap;

import main.PhysicsObject;
import main.Sprite;
import scenes.GameScene;


public class SolidBlock extends PhysicsObject{

	
	static HashMap<String,Sprite> imageMap;

	String type;

	public SolidBlock(GameScene scene, int x, int y, String type) {
		super(scene, "solid-block", x, y, 50, 50);
		this.type=type;
		loadImages();
	}

	/***************************************************************************
	* looads images to hash-map
	***************************************************************************/
	public void loadImages()
	{
		if (imageMap == null)
		{
			imageMap = new HashMap<String,Sprite>();
			
			imageMap.put("top", new Sprite("img/WallTop.png"));
			imageMap.put("bottom", new Sprite("img/WallBottom.png"));
			imageMap.put("left", new Sprite("img/WallLeft.png"));
			imageMap.put("right", new Sprite("img/WallRight.png"));
			imageMap.put("top-left", new Sprite("img/WallTopLeft.png"));
			imageMap.put("top-right", new Sprite("img/WallTopRight.png"));
			imageMap.put("bottom-left", new Sprite("img/WallBottomLeft.png"));
			imageMap.put("bottom-right", new Sprite("img/WallBottomRight.png"));
			imageMap.put("inner-1", new Sprite("img/inner-corner.png"));
			imageMap.put("inner-2", new Sprite("img/inner-corner-2.png"));
			imageMap.put("inner-3", new Sprite("img/inner-corner-3.png"));
			imageMap.put("inner-4", new Sprite("img/inner-corner-4.png"));

		}
	}

	
	 
	/***************************************************************************
	* returns the object's image
	***************************************************************************/
	public Sprite getImage(String type)
	{
		loadImages();
		return imageMap.get(type);
	}

	/***************************************************************************
	* paints to screen
	***************************************************************************/
	public void paint(Graphics g)
	{
		imageMap.get(type).paint(g, x, y, width, height, 0);;
	}
}
