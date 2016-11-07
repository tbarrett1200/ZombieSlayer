package world;

import java.awt.Graphics;

import entities.Character;
import main.GameObject;
import main.Sprite;
import scenes.GameScene;

public class Gun extends GameObject {


	//offset from the gun coordinates to the end of the barrel
	int barrelx, barrely;
	int gunoffsetx, gunoffsety;	
	//timing variables
	long lastShot;
	long shotRate;
	
	//character holding the gun
	public Character owner;
	
	//gun image
	Sprite gun;

	String type;
	
	//constructor
	public Gun(GameScene scene, Character owner, String type) {
		super(scene, owner.x, owner.y, 60, 32);
		this.owner=owner;
		
		this.type=type;
		
		if (type.equals("basic"))
		{
			barrely=10;
			shotRate=1000;
			gun = new Sprite("img/gun-basic.png");
			barrelx=gun.width*4;

		}
		
		else if (type.equals("basic-2"))
		{
			barrely=10;
			shotRate=500;
			gun = new Sprite("img/gun-basic-2.png");
			barrelx=gun.width*4;

		}
		
		else if (type.equals("rocket"))
		{
			barrely=4;
			shotRate=1000;
			gun = new Sprite("img/gun-rocket.png");
			gunoffsetx=-20;
			barrelx=gun.width*4;

		}
		
		else if (type.equals("machine"))
		{
			barrely=10;
			shotRate=100;
			gun = new Sprite("img/gun-machine.png");
			gunoffsety=8;
			gunoffsetx=-8;
			barrelx=gun.width*4;

		}
		
		else if (type.equals("laser"))
		{
			barrely=16;
			shotRate=0;
			gun = new Sprite("img/gun-laser.png");
			gunoffsety=-8;
			barrelx=gun.width*4;

		}
	}
	
	//shoots a bullet if delay has been long enough
	public void shoot()
	{
		long currentTime = System.currentTimeMillis();
		
		if (currentTime-lastShot>shotRate)
		{
			if (type.equals("basic") || type.equals("basic-2") || type.equals("machine"))
			{
				scene.add(new Bullet((GameScene)scene,this,barrelX(),barrelY()));
			}
			else if (type.equals("rocket"))
			{
				scene.add(new Rocket((GameScene)scene,this));
			}
			else if (type.equals("laser"))
			{
				scene.add(new Laser((GameScene)scene,this,barrelX(),barrelY()));
			}
			lastShot=currentTime;
		}
	}
	
	//returns the x coordinate of the end of the gun barrel
	public int barrelX()
	{
		if (owner.facing==Right)
		{
			return (int) (owner.x+owner.width+owner.gunx+barrelx+gunoffsetx);
		}
		else
		{
			return (int) (owner.x-owner.gunx-barrelx+gunoffsetx);
		}	
	}
	
	//returns the y coordinate of the end of the gun barrel
	public int barrelY()
	{
		return (int) (owner.y+owner.guny+barrely+gunoffsety);
	}
	
	//returns the direction that the gun is facing
	public int facing()
	{
		return owner.facing;
	}
	
	//paints the gun
	public void paint(Graphics g)
	{

		int width = gun.original.getWidth()*4;
		int height = gun.original.getHeight()*4;
		
		if (owner.facing==Right)
		{
			gun.paint(g, owner.x+owner.width+owner.gunx+gunoffsetx, owner.y+owner.guny+gunoffsety, width, height, 0);
		}
		else
		{
			gun.paint(g, owner.x-owner.gunx-width-gunoffsetx, owner.y+owner.guny+gunoffsety, width, height, Sprite.FLIP_HORIZONTAL);
		}
	}
}
