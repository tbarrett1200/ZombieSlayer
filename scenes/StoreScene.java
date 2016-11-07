package scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import main.GameWindow;
import main.Scene;
import main.Sprite;
import misc.Button;
import world.Gun;

public class StoreScene extends Scene implements KeyListener, MouseListener{

	public Button quitButton = new Button(this, "quit", 10, 10 , 100, 50);
	public Button returnButton = new Button(this, "return", 120, 10 , 150, 50);
	public Button buyButton = new Button(this, "buy", 0, 0 , 100, 50);

	ArrayList<Sprite> gunsImage = new ArrayList<Sprite>(); 
	ArrayList<String> gunNames = new ArrayList<String>();
	ArrayList<Integer> gunPrice = new ArrayList<Integer>();

	int currentGun=0;
	
	//constructor
	public StoreScene(GameWindow main) {
		super(main);
		
		scene.add(quitButton);
		scene.add(returnButton);
		scene.add(buyButton);

		gunsImage.add(new Sprite("img/gun-basic.png"));
		gunNames.add("basic");
		gunPrice.add(0);

		gunsImage.add(new Sprite("img/gun-basic-2.png"));
		gunNames.add("basic-2");
		gunPrice.add(100);
		
		gunsImage.add(new Sprite("img/gun-rocket.png"));
		gunNames.add("rocket");
		gunPrice.add(200);
		
		gunsImage.add(new Sprite("img/gun-machine.png"));
		gunNames.add("machine");
		gunPrice.add(500);
		
		gunsImage.add(new Sprite("img/gun-laser.png"));
		gunNames.add("laser");
		gunPrice.add(1000);
		
	}
	
	//updates scene
	public void update()
	{
		super.update();
		
		buyButton.x=width/2-buyButton.width/2;
		buyButton.y=height-buyButton.height-10;
	}
	
	//paints scene
	public void paint(Graphics g)
	{
		
		super.paint(g);
		
		Sprite gun = gunsImage.get(currentGun);
		gun.paint(g, width/2-gun.width*4, height/2-gun.height*4, gun.width*8, gun.height*8, 0);
		
		g.setColor(Color.WHITE);
		g.drawString("$" + GameScene.data.coins,width-400, 100);
		g.drawString(Integer.toString(gunPrice.get(currentGun)), 100, 100);
	}

	//key input
	public void keyPressed(KeyEvent e)
	{
		int keyCode = e.getKeyCode();
		
		switch (keyCode)
		{
		case KeyEvent.VK_B:
			buy();
			break;
		case KeyEvent.VK_ENTER:
			main.changeScene(GameScene.pause);
			break;
		case KeyEvent.VK_RIGHT:
			if (currentGun == gunNames.size()-1) currentGun=0;
			else currentGun++;
			break;
		case KeyEvent.VK_LEFT:
			if (currentGun == 0) currentGun=gunNames.size()-1;
			else currentGun--;
			break;
		}
		
	}
	
	//buys current gun
	public void buy()
	{
		if (GameScene.data.coins>=gunPrice.get(currentGun))
		{
			GameScene.data.gun=gunNames.get(currentGun);
			GameScene.data.coins-=gunPrice.get(currentGun);
			GameScene.data.exportData();
			GameScene.pause.player.gun = new Gun(GameScene.pause,GameScene.pause.player,gunNames.get(currentGun));
		}
	}
	
	//mouse input
	public void mousePressed(MouseEvent e)
	{
		quitButton.mousePressed(e);
		returnButton.mousePressed(e);	
		buyButton.mousePressed(e);		

	}
	
	public void mouseReleased(MouseEvent e)
	{
		quitButton.mouseReleased(e, ()-> main.changeScene(new MenuScene(main)));
		returnButton.mouseReleased(e, ()-> 	main.changeScene(GameScene.pause));
		buyButton.mouseReleased(e, ()->buy());
	}


	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
}
