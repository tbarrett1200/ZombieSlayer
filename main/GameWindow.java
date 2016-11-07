package main;

/*******************************************************************************
 * Thomas Barrett
 * Period 1
 * Mar 24, 2016
 * Description: Creates window and paints and updates scene
 ******************************************************************************/

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import scenes.MenuScene;

public class GameWindow extends JPanel
{
	private static final long serialVersionUID = 1L;

	//window
	JFrame window = new JFrame();
	
	//update interval in frames per second
	private final int FPS=30;
	
	//the scene which is currently being updated and painted
	Scene currentScene;
	
	//constructor
	public GameWindow(int width, int height)
	{
		//set window size
		setPreferredSize(new Dimension(width,height));
		
		//sets minimum size
		window.setMinimumSize(new Dimension(width,height));

		//adds panel to window
		window.getContentPane().add(this);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
		//turns on double buffering
		setDoubleBuffered(true);
		
		//sets the initial scene
		changeScene(new MenuScene(this));
	}
	
	//game loop
	public void run()
	{
		//saves the current time
		long time = System.currentTimeMillis();

		while(true)
		{
			//gives window input focus
			requestFocus();

			//sets the scene width
			currentScene.width=getWidth();
			currentScene.height=getHeight();
			
			//update and paint scene
			repaint();
			
			//sleep until next frame
			try 
			{
				time += 1000 / FPS;
    			Thread.sleep(Math.max(0, time - System.currentTimeMillis()));
			} 
			catch (InterruptedException e)
			{
				break;
			}
			
		}
	}
	
	
	//manages listeners and changes scene
	public void changeScene(Scene scene)
	{
		//removes event listeners from last scene
		if (currentScene instanceof KeyListener)
			removeKeyListener((KeyListener)currentScene);

		if (currentScene instanceof MouseListener)
			removeMouseListener((MouseListener)currentScene);

		if (currentScene instanceof MouseMotionListener)
			removeMouseMotionListener((MouseMotionListener)currentScene);

		/* adds event listeners from new scene */
		if (scene instanceof KeyListener)
			addKeyListener((KeyListener)scene);

		if (scene instanceof MouseListener)
			addMouseListener((MouseListener)scene);

		if (scene instanceof MouseMotionListener)
			addMouseMotionListener((MouseMotionListener)scene);

		//sets the current scene to the new scene
		currentScene = scene;
	}
	

	//paints and updates scene
	public void paint(Graphics g)
	{
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		
		if (currentScene!=null)
		{
			currentScene.update();
			currentScene.paint(g);
		}
	}
	
	public static void main(String[] args)
	{
		new GameWindow(800,600).run();
	}

}

