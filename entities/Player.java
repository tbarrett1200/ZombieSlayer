package entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import main.GameObject;
import main.Sprite;
import scenes.GameScene;
import world.Gun;
import world.SolidBlock;

/*******************************************************************************
 * Thomas Barrett
 * Period 1
 * Mar 24, 2016
 * Description: User controlled player
 ******************************************************************************/

@SuppressWarnings("serial")
public class Player extends Character {

	//font used to display number of coins
	Font coinFont = new Font("Courier",Font.BOLD,30);

	//jump and punch images
	public static Sprite jump=new Sprite("img/jumpr.png");
	public static Sprite punch=new Sprite("img/punchr.png");

	//gun that player is holding
	public Gun gun;

	//player movement speed
	double speed=8;

	//Attack timing information
	int attackDuration=200;
	long timeOfLastAttack;
	long timeOfAttackStart;
	
	//the number of jumps player has left
	public final int MAX_JUMPS=2;
	public int jumpsRemaining=MAX_JUMPS;
	
	//time of last shot
	long lastShot;
		
	//whether or not player is attacking
	Boolean attack=false;
	
	//last direction player wall jumped
	int lastWallJump=None;
	
	/***************************************************************************
	* constructor
	***************************************************************************/
	public Player(GameScene scene,int x, int y) {
		super(scene,"player",x, y, 64, 100);
		
		//collision information
		collidesWith.add("solid-block");
		intersectsWith.add("solid-block");
		intersectsWith.add("enemy");

		//gun information
		gun=new Gun(scene,this,GameScene.data.gun);
		gunx=-10;
		guny=45;

		//gravity
		setGravity(scene.gravity);
		
		//health
		health.set(5);
		
		if (!isWalkCycleSet()) {
			Sprite walkCycle[] = { new Sprite("img/player-walk-1.png"), new Sprite("img/player-walk-2.png") };
			setWalkCycle(walkCycle, 100);
		}

	}
	
	//cancels jump
	public void down()
	{
		dy=Math.max(dy, 5);
	}
	
	//jumps
	public void jump()
	{
		if (jumpsRemaining>0)
		{
			setSpeedInDirection(Up,15);
			jumpsRemaining--;
		}
	}
	
	//starts attack
	public void startAttack()
	{
		attack=true;
		gunx=0;
	}
	
	//stops attack
	public void stopAttack()
	{
		attack=false;
		gunx=-10;
	}
	
	//turns to left
	public void left()
	{
		facing=Left;
		moving=Left;
	}
	
	//turns to right
	public void right()
	{
		facing=Right;
		moving=Right;
	}

	//implements wall jumping
	public void didBeginIntersection(GameObject o)
	{
		if (o instanceof SolidBlock)
		{
			if (touchingGround())
			{
				jumpsRemaining=MAX_JUMPS;
				lastWallJump=None;
			}
			else if (lastWallJump!=None)
			{
				if (directionComparedTo(o)!=lastWallJump && o.y+o.height - y > 0)
				{		
					lastWallJump=directionComparedTo(o);
					jumpsRemaining=1;
				}
			}
			else 
			{
				lastWallJump=directionComparedTo(o);
				jumpsRemaining=1;

			}
		}
		
	}

	//updates player each frame
	public void update()
	{
		if (attack) gun.shoot();
		
		if (moving==Right) dx=speed;
		else if (moving==Left) dx=-speed;
		else if (moving==None) dx=0;
		
		if (health.health<=0) scene.remove(this);

		super.update();
	}

	//paints player to screen
	public void paint(Graphics g)
	{	
		if (facing == Left)
		{
			if (attack)
				punch.paint(g, x-12, y, 76, height, Sprite.FLIP_HORIZONTAL);
			else if (!touchingGround()) //jump
				jump.paint(g, x, y, width, height, Sprite.FLIP_HORIZONTAL);
			else
				getSprite().paint(g, x, y, width, height, Sprite.FLIP_HORIZONTAL);
		}
		else
		{
			if (attack)
				punch.paint(g, x, y, 76, height, 0);
			else if (!touchingGround()) //jump
				jump.paint(g, x, y, width, height, 0);
			else
				getSprite().paint(g, x, y, width, height, 0);
		}
		
		g.setFont(coinFont);
		g.setColor(Color.green);
		g.drawString(Integer.toString(GameScene.data.coins), (int)(x+width+50), (int)y);
		
		health.paint(g);
		gun.paint(g);
	}


}
