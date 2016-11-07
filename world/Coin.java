package world;

/*******************************************************************************
 * Thomas Barrett
 * Period 1
 * Mar 24, 2016
 * Description: A coin object
 ******************************************************************************/

import java.awt.Graphics;
import java.util.Random;

import entities.Player;
import main.GameObject;
import main.PhysicsObject;
import main.Sprite;
import scenes.GameScene;

public class Coin extends PhysicsObject{

	//random number generator
	Random rnd = new Random();

	//coin sprite
	static Sprite img;


	/***************************************************************************
	* updates object each frame
	***************************************************************************/
	public Coin(GameScene scene, double x, double y) {
		super(scene,"coin", x, y, 16, 16);

		//will be notified about intersections
		collidesWith.add("solid-block");
		intersectsWith.add("solid-block");

		intersectsWith.add("player");

		//sets gravity to the scenes gravity
		this.setGravity(((GameScene)scene).gravity);

		//x speed is a random number from -10 to 10
		dx=rnd.nextInt(11)-5;

		//y speed is a random number from 0 to 10
		dy=rnd.nextInt(11);

		//if image has not been loaded
		if (img == null)
		{
			img=new Sprite("img/goldcoin.png");
		}
	}

	/***************************************************************************
	* updates object each frame
	***************************************************************************/
	public void didBeginIntersection(GameObject o)
	{
		//if coin intersects block
		if (o instanceof SolidBlock)
		{
			dx=0;
		} 
		//if coin intersects player
		else if (o instanceof Player) 
		{
			GameScene.data.coins++;
			scene.remove(this);
		}
	}

	/***************************************************************************
	* updates object each frame
	***************************************************************************/
	public void paint(Graphics g)
	{
		//paints coin sprite
		img.paint(g, x, y, width, height, 0);
	}

}
