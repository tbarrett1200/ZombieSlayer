package world;

import java.awt.Graphics;

import main.GameObject;
import main.Sprite;
import scenes.GameScene;

public class Ladder extends GameObject{

	static Sprite ladder = new Sprite("img/ladder.png");
	
	
	/***************************************************************************
	* constructor
	***************************************************************************/
	public Ladder(GameScene scene, int x, int y) {
		super(scene, x, y, 50, 50);
	}

	
	/***************************************************************************
	* collision game logic: start collision
	***************************************************************************/
	public void update()
	{
		super.update();

		if (intersects(((GameScene)scene).player))
		{
			((GameScene)scene).player.jumpsRemaining=((GameScene)scene).player.MAX_JUMPS;
		}
	}
	
	/***************************************************************************
	* paints object to screen
	***************************************************************************/
	public void paint(Graphics g)
	{
		ladder.paint(g, x, y, width, height, 0);
	}
	
	
}
