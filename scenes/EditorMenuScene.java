package scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import main.GameWindow;
import main.Scene;
import main.Sprite;
import misc.TextField;

public class EditorMenuScene extends Scene implements KeyListener, MouseListener{

	//text input
	TextField text = new TextField(this,50,100,100,50);
	Sprite background = new Sprite("img/brick.png");

	/**************************************************************************
	 * constructor
	 *************************************************************************/
	public EditorMenuScene(GameWindow main)
	{
		super(main);
	
		text.placeholder="enter level number";

		//adds text field to scene
		scene.add(text);
		
		//sets background to grey
		main.setBackground(new Color(133,135,138));
	}

	public void update()
	{
		super.update();
		
		text.width=width-2*text.x;
	}
	
	public void paint(Graphics g)
	{
		for (int i=0; i<width; i+=50)
		{
			for (int j=0; j<height; j+=50)
			{
				background.paint(g, i, j, 50, 50, 0);
			}
		
		}
		
		super.paint(g);
	}
	/**************************************************************************
	 * key handling
	 *************************************************************************/
	public void keyPressed(KeyEvent e)
	{
		//get key code
		int keyCode = e.getKeyCode();
		
		switch (keyCode)
		{
		case KeyEvent.VK_Q:
			//go to main menu
			main.changeScene(new MenuScene(main));
			break;
		case KeyEvent.VK_ENTER:
			//edit the selected level
			main.changeScene(new EditorScene(main,Integer.parseInt(text.text)));
			break;
		case KeyEvent.VK_BACK_SPACE:
			//delete letter from text field
			if (text.text.length()!=0)
			{
				text.text=text.text.substring(0, text.text.length()-1);
			}
			break;
		}
		
		if (text.selected)
		{
			
		
		//get char value of key
		char keyChar = e.getKeyChar();
		
		//if keyChar is a number
		if (keyChar>='0' && keyChar<='9')
		{
			//if appending the keyChar would not overflow an integer value
			if (Long.parseLong(new String(text.text) + keyChar) < Integer.MAX_VALUE)
			{
				//append keyChar to the text field
				text.text += keyChar;
			}
		}
		}
	}
	
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		text.mousePressed(e);		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
