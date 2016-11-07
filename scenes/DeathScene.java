package scenes;

/*******************************************************************************
 * Thomas Barrett
 * Period 1
 * Mar 24, 2016
 * Description: The scene to be displayed upon a player's death
 ******************************************************************************/

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GameWindow;
import main.Scene;

public class DeathScene extends Scene implements KeyListener {

	Font font1 = new Font("Courier",Font.BOLD,60);
	Font font2 = new Font("Courier",Font.ITALIC,30);

	String s1 = "You Died";
	String s2 = "Press [Q] to Quit";
	String s3 = "Press [Enter] to Restart";

	/***************************************************************************
	* constructor
	***************************************************************************/
	public DeathScene(GameWindow main)
	{
		super(main);
		
		//resets level to 0
		GameScene.level=0;

	}

	/***************************************************************************
	* paints scene
	***************************************************************************/
	public void paint(Graphics g)
	{
		main.setBackground(Color.RED);
		
		//draws s1, s2, and s3 centered on screen
		g.setColor(Color.BLACK);
		g.setFont(font1);
		g.drawString(s1,width/2-stringWidth(g, font1, s1)/2, 100);
		g.setFont(font2);
		g.drawString(s2, width/2-stringWidth(g, font2, s2)/2, 160);
		g.drawString(s3, width/2-stringWidth(g, font2, s3)/2, 200);

	}
	
	/***************************************************************************
	* key handling
	***************************************************************************/
	public void keyPressed(KeyEvent e)
	{
		//key code of key pressed
		int keyCode = e.getKeyCode();

		switch (keyCode)
		{
		//if Q is pressed, go to main menu
		case KeyEvent.VK_Q:
			main.changeScene(new MenuScene(main));
			break;
		//if ENTER is pressed, restart game
		case KeyEvent.VK_ENTER:
			main.changeScene(new GameScene(main,"levels/stage0.txt",GameScene.user));
			break;
		}
	}
	
	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
	
}
