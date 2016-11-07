package scenes;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import main.GameWindow;
import main.Scene;
import main.Sprite;
import misc.Button;
import misc.TextField;


/*
Thomas Barrett
Period 1
Mar 11, 2016
Description:
*/
public class MenuScene extends Scene implements KeyListener, MouseListener
{
	
	
	Random rnd=new Random();
	Color color=new Color(124,24,240);
	
	TextField login = new TextField(this,10,200,main.getWidth()-20,50);
	
	Button editorButton=new Button(this, "Level Editor", 10, 300, 300, 50);
	Button startButton=new Button(this, "Start", 10, 360, 300, 50);
	Sprite background = new Sprite("img/brick.png");
	Sprite title = new Sprite("img/title.png");
	
	/***************************************************************************
	* constructor
	***************************************************************************/
	public MenuScene(GameWindow main) {
		super(main);
		
		GameScene.level=0;
		
		if (GameScene.user == null)
		{
			login.placeholder="enter username";
		}
		else
		{
			login.placeholder="welcome back " + GameScene.user;
		}
		scene.add(login);
		scene.add(editorButton);
		scene.add(startButton);

	}
	
	/***************************************************************************
	* paints text to screen
	***************************************************************************/
	public void paint(Graphics g)
	{
		
		for (int i=0; i<width; i+=50)
		{
			for (int j=0; j<height; j+=50)
			{
				background.paint(g, i, j, 50, 50, 0);
			}
		
		}
	
		int twidth=485;
		int theight=98;
		title.paint(g, width/2-twidth/2, 50, twidth, theight, 0);
		
		super.paint(g);

	}

	public void update()
	{
		editorButton.x=width/2-150;
		startButton.x=width/2-150;
		login.width=width-2*login.x;

		super.update();
	}
	/***************************************************************************
	* generates a random color
	***************************************************************************/
	public Color randomColor()
	{
		return new Color(rnd.nextInt(255),rnd.nextInt(255),rnd.nextInt(255));
	}
	
	/***************************************************************************
	* key input
	***************************************************************************/
	public void keyPressed(KeyEvent e)
	{
		int key=e.getKeyCode();
		
		switch(key)
		{
		case KeyEvent.VK_ENTER:
			if (login.text!="" || GameScene.user!=null)
			{
				main.changeScene(new GameScene(main, "levels/stage" + GameScene.level + ".txt", login.text));
			}
			else
			{
				login.placeholder="enter your username you blithering idiot";
			}
			break;
		case KeyEvent.VK_BACK_SPACE:
			if (login.selected && login.text.length()>0)
				login.text=login.text.substring(0, login.text.length()-1);
			break;
		default:
			if (login.selected && e.getKeyChar()>33 && e.getKeyChar()<126)
			login.text+=e.getKeyChar();
		}
	}
	
	public void mousePressed(MouseEvent e) {
		login.mousePressed(e);
		editorButton.mousePressed(e);
		startButton.mousePressed(e);

	}
	
	public void mouseReleased(MouseEvent e) {
		editorButton.mouseReleased(e, ()->	main.changeScene(new EditorMenuScene(main)));
		startButton.mouseReleased(e, ()-> {
			if (login.text!="" || GameScene.user!=null)
			{
				main.changeScene(new GameScene(main, "levels/stage" + GameScene.level + ".txt", login.text));
			}
			else
			{
				login.placeholder="enter your username you blithering idiot";
			}
		});

	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
