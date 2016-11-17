package scenes;

/*******************************************************************************
 * Thomas Barrett
 * Period 1
 * Mar 24, 2016
 * Description: scene in which all game-play occurs
 ******************************************************************************/

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import entities.Bull;
import entities.Glompus;
import entities.Player;
import entities.Zombie;
import main.Directions;
import main.GameObject;
import main.GameWindow;
import main.Scene;
import main.Sprite;
import main.WorldReader;
import misc.Button;
import misc.PlayerData;
import world.BackgroundBlock;
import world.Doorway;
import world.HealthPack;
import world.Ladder;
import world.LavaBlock;
import world.SolidBlock;
import world.Spikes;
import world.Stalactite;

public class GameScene extends Scene implements KeyListener, MouseListener,Directions
{
	//gravity in pixels/frame^2
	public int gravity = 1;
	
	//main player
	public Player player;
	
	public static GameScene pause;
	
	Random rnd = new Random();

	public boolean inFrontOfDoor=false;
	boolean paused=false;
	
	//the total offset to all coordinates in the scene
	int verticalOffset, horizontalOffset;
		
	public static String user;
	
	//the current level
	public static int level=0;
	public static PlayerData data;
	
	Sprite pause1=new Sprite("img/pause.png"), pause2=new Sprite("img/pause-2.png"), play1=new Sprite("img/play.png"), play2=new Sprite("img/play-2.png");
	public Button pauseButton = new Button(this, "pause", 0, 0 , 50, 50);
	public Button storeButton = new Button(this, "store", 0, 0 , 50, 50);
	public Button quitButton = new Button(this,"quit",0,0,50,50);
	public Button enterButton = new Button(this,"enter",0,0,50,50);

	//all the game objects in the scene
	ArrayList<GameObject> background = new ArrayList<GameObject>();
	ArrayList<GameObject> foreground = new ArrayList<GameObject>();
	
	ArrayList<GameObject> userInterface = new ArrayList<GameObject>();


	/*******************************************************************************
	 * constructor
	 ******************************************************************************/

	public GameScene(GameWindow main,String path,String user)
	{
		super(main);
		if (user.length()>0)
		{
			GameScene.user=user;
		}
		data = new PlayerData(GameScene.user);
		loadSceneFromFile(path);

	}
	
	/*******************************************************************************
	 * reads a list of objects from file and adds them to the scene
	 ******************************************************************************/
	public void loadSceneFromFile(String path)
	{
		try {
			
			WorldReader worldReader = new WorldReader(path);
			ArrayList<GameObject> enemylist=new ArrayList<GameObject>();
			
			
			while (worldReader.hasNext())
			{
				HashMap<String,String> object = worldReader.nextObject();

				if (object == null)
				{
					break;
				}
				int x =  horizontalOffset + Integer.parseInt(object.get("xcoord"));
				int y = verticalOffset + Integer.parseInt(object.get("ycoord"));

				if (object.get("name").equals("solid-block"))
				{
					scene.add(new SolidBlock(this,x, y,object.get("type")));
				}
				else if (object.get("name").equals("doorway"))
				{
					scene.add(new Doorway(this,x,y));
				}
				else if (object.get("name").equals("background"))
				{
					background.add(new BackgroundBlock(this,x,y,object.get("type")));
				}
				else if (object.get("name").equals("bull"))
				{
					enemylist.add(new Bull(this,x,y));
				}
				else if (object.get("name").equals("zombie"))
				{
					enemylist.add(new Zombie(this,x,y));
				}
				else if (object.get("name").equals("glompus"))
				{
					enemylist.add(new Glompus(this,x,y));
				}
				else if (object.get("name").equals("spikes"))
				{
					scene.add(new Spikes(this,x,y));
				}
				else if (object.get("name").equals("ladder"))
				{
					scene.add(new Ladder(this,x,y));
				}
				else if (object.get("name").equals("stalactite"))
				{
					scene.add(new Stalactite(this,x,y));
				}
				else if (object.get("name").equals("lava"))
				{
					scene.add(new LavaBlock(this,x,y));
				}
				else if (object.get("name").equals("health-pack"))
				{
					scene.add(new HealthPack(this,x+5,y+5));
				}
			}
			
			scene.addAll(enemylist);
			player = new Player(this,0,0);
			scene.add(player);
			
			
			pauseButton.setImage(pause1, pause2);
			storeButton.setImage(new Sprite("img/store.png"), new Sprite("img/store-2.png"));
			quitButton.setImage(new Sprite("img/quit.png"), new Sprite("img/quit-2.png"));
			
			enterButton.setImage(new Sprite("img/enter.png"), new Sprite("img/enter-2.png"));
			enterButton.visible=false;
			focusOnPlayer();

			
		} catch (FileNotFoundException e) {
			main.changeScene(new MenuScene(main));
			System.out.println(path + "Not Found");
		}

	}

	/*******************************************************************************
	 * called when key is pressed
	 ******************************************************************************/
	public void keyPressed(KeyEvent e)
	{
		int keyCode = e.getKeyCode();

		if (!paused)
		switch(keyCode)
		{
		case KeyEvent.VK_ENTER:
			
			if (inFrontOfDoor)
			{
			//move on to the next level
			GameScene.level++;
					
			//save player data
			GameScene.data.exportData();
					
					
				
			//load next scene file
			File next = new File("levels/stage" + GameScene.level + ".txt");
					
			//load the next level or go to main menu
			if (next.exists())
			{
				main.changeScene(new GameScene(main,"levels/stage" + GameScene.level + ".txt",GameScene.user));
			}
			else
			{
				main.changeScene(new MenuScene(main));
			}
			}
			break;
		case KeyEvent.VK_SPACE:
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
			player.jump();
			break;
		case KeyEvent.VK_SHIFT:
		case KeyEvent.VK_Z:
			player.startAttack();
			break;
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
			player.left();
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			player.right();
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			player.down();
			break;
		}
	}
	
	/*******************************************************************************
	 * called when key is released
	 ******************************************************************************/
	public void keyReleased(KeyEvent e)
	{
		int keyCode = e.getKeyCode();
		
		switch(keyCode)
		{
		case KeyEvent.VK_SHIFT:
		case KeyEvent.VK_Z:
			player.stopAttack();
			break;
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
			if (player.moving==Left) player.moving=None;
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			if (player.moving==Right) player.moving=None;
			break;
		}
	}
	
	

	/*******************************************************************************
	 * updates all objects in scene and centers player
	 ******************************************************************************/
	public void update()
	{

		if (!paused)
		super.update();

		/* changes to DeathScene upon player's death */
		if (!scene.contains(player))
		{
			data.exportData();
			main.changeScene(new DeathScene(main));
		}
		
		focusOnPlayer();
		
		pauseButton.x=main.getWidth()-pauseButton.width-10;
		pauseButton.y=10;
		pauseButton.update();
		
		storeButton.x=pauseButton.x-storeButton.width-10;
		storeButton.y=10;
		storeButton.update();
		
		quitButton.x=storeButton.x-quitButton.width-10;
		quitButton.y=10;
		quitButton.update();

		enterButton.x=main.getWidth()-enterButton.width-10;
		enterButton.y=main.getHeight()/2-enterButton.height/2;
		enterButton.update();

	}

	public void focusOnPlayer()
	{
		/* calculates the players offset from center */
		double horizontalAdjust=main.getWidth()/2-(player.x+player.width/2);
		double verticalAdjust=main.getHeight()/2-(player.y+player.height/2);
		
		/* applies offset to total offset */
		horizontalOffset+=horizontalAdjust;
		verticalOffset+=verticalAdjust;
		

		for (GameObject o: scene)
		{
			o.x+=horizontalAdjust;
			o.y+=verticalAdjust;
		}
		
		for (GameObject o: background)
		{
			o.x+=horizontalAdjust;
			o.y+=verticalAdjust;
		}
	}
	/*******************************************************************************
	 * paints background, scene, and foreground
	 ******************************************************************************/
	public void paint(Graphics g)
	{
		main.setBackground(new Color(133,135,138));

		for (GameObject object : background)
		{
			object.paint(g);
		}
		
		super.paint(g);
		
		for (GameObject object : foreground)
		{
			object.paint(g);
		}
		
		
		pauseButton.paint(g);
		storeButton.paint(g);
		quitButton.paint(g);
		enterButton.paint(g);

		
	}
	

	public void mousePressed(MouseEvent e) {
		pauseButton.mousePressed(e);
		storeButton.mousePressed(e);
		quitButton.mousePressed(e);
		enterButton.mousePressed(e);

	}

	public void mouseReleased(MouseEvent e) {
		pauseButton.mouseReleased(e, ()->	{
			if (pauseButton.imgStart==pause1)
			{
				pauseButton.setImage(play1, play2);
			}
			else
			{
				pauseButton.setImage(pause1, pause2);

			}
			paused=!paused;
			}
		);
		storeButton.mouseReleased(e, ()-> 	{
			data.exportData();
			pause=this;main.changeScene(new StoreScene(main));
		});
		enterButton.mouseReleased(e, ()-> 	{

			//move on to the next level
			GameScene.level++;
					
			//save player data
			GameScene.data.exportData();
					
			//load next scene file
			File next = new File("levels/stage" + GameScene.level + ".txt");
					
			//load the next level or go to main menu
			if (next.exists())
			{
				System.out.println("Change Level");
				main.changeScene(new GameScene(main,"levels/stage" + GameScene.level + ".txt",GameScene.user));
			}
			else
			{
				System.out.println("Go To Main");
				main.changeScene(new MenuScene(main));
			}
		});
		quitButton.mouseReleased(e, ()-> 
		{
			data.exportData();
			main.changeScene(new MenuScene(main));
		});
	}

	public void keyTyped(KeyEvent e){}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
