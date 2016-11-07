package scenes;

/*******************************************************************************
 * Thomas Barrett
 * Period 1
 * Mar 24, 2016
 * Description: A scene which allows for the creation and editing of game scenes
 ******************************************************************************/

 import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import main.GameWindow;
import main.Scene;
import main.WorldReader;
import main.WorldWriter;
public class EditorScene extends Scene implements MouseListener, KeyListener, MouseMotionListener{

	HashMap<Point,HashMap<String,String>> sceneMap=new HashMap<Point,HashMap<String,String>>();
	HashMap<Point,HashMap<String,String>> backGroundMap=new HashMap<Point,HashMap<String,String>>();

	HashMap<String, BufferedImage> imageMap=new HashMap<String, BufferedImage>();
	String current="top";
	Boolean erase=false;

	int playerX=200;
	int playerY=200;
	
	int horizontalOffset=0;
	int verticalOffset=0;

	int level;
	
	boolean replace;
	boolean box=false;
	boolean backgroundErase=false;
	
	int bx=-1, by=-1;
	
	int currentitem=0;
	ArrayList<String> itemList;
		
	public EditorScene(GameWindow main, int level)
	{
		super(main);
		this.level=level;

		try
		{
			imageMap.put("spikes", ImageIO.read(new File("bin/img/spikes.png")));
			imageMap.put("top", ImageIO.read(new File("bin/img/WallTop.png")));
			imageMap.put("bottom", ImageIO.read(new File("bin/img/WallBottom.png")));
			imageMap.put("left", ImageIO.read(new File("bin/img/WallLeft.png")));
			imageMap.put("right", ImageIO.read(new File("bin/img/WallRight.png")));
			imageMap.put("top-left", ImageIO.read(new File("bin/img/WallTopLeft.png")));
			imageMap.put("top-right", ImageIO.read(new File("bin/img/WallTopRight.png")));
			imageMap.put("bottom-left", ImageIO.read(new File("bin/img/WallBottomLeft.png")));
			imageMap.put("bottom-right", ImageIO.read(new File("bin/img/WallBottomRight.png")));
			imageMap.put("player", ImageIO.read(new File("bin/img/player-walk-1.png")));
			imageMap.put("bull", ImageIO.read(new File("bin/img/bull-walk-1.png")));
			imageMap.put("zombie", ImageIO.read(new File("bin/img/zombie-walk-1.png")));
			imageMap.put("glompus", ImageIO.read(new File("bin/img/glompus-walk-1.png")));
			imageMap.put("door", ImageIO.read(new File("bin/img/doorway.png")));
			imageMap.put("back", ImageIO.read(new File("bin/img/brick.png")));
			imageMap.put("inner-1", ImageIO.read(new File("bin/img/inner-corner.png")));
			imageMap.put("inner-2", ImageIO.read(new File("bin/img/inner-corner-2.png")));
			imageMap.put("inner-3", ImageIO.read(new File("bin/img/inner-corner-3.png")));
			imageMap.put("ladder", ImageIO.read(new File("bin/img/ladder.png")));
			imageMap.put("inner-4", ImageIO.read(new File("bin/img/inner-corner-4.png")));
			imageMap.put("stalactite", ImageIO.read(new File("bin/img/stalactite.png")));
			imageMap.put("lava", ImageIO.read(new File("bin/img/lava-1.png")));
			imageMap.put("health-pack", ImageIO.read(new File("bin/img/health-pack.png")));






		}
		catch (IOException e) {
			System.out.println("Images Not Found");
		}

		itemList = new ArrayList<String>(imageMap.keySet());
		
		try {
			WorldReader read = new WorldReader("levels/stage" + level + ".txt");

			HashMap<String, String> object;

			
				while (true)
				{
					object = read.nextObject();
					if (object == null) break;
					if (object.get("name").equals("background"))
					{
						backGroundMap.put(new Point(Integer.parseInt(object.get("xcoord")),(Integer.parseInt(object.get("ycoord")))), object);

					}
					else
					sceneMap.put(new Point(Integer.parseInt(object.get("xcoord")),(Integer.parseInt(object.get("ycoord")))), object);
				}
		

		} catch (FileNotFoundException e) {
			System.out.println("Not Found");
			main.changeScene(new MenuScene(main));
		}

	}

	public void paint(Graphics g)
	{
		for (HashMap<String,String> item : backGroundMap.values())
		{
			int x=Integer.parseInt(item.get("xcoord"))+horizontalOffset;
			int y=Integer.parseInt(item.get("ycoord"))+verticalOffset;
			g.drawImage(imageMap.get("back"), x, y, 50,50,null);
		}

		for (HashMap<String,String> item : sceneMap.values())
		{
			int x=Integer.parseInt(item.get("xcoord"))+horizontalOffset;
			int y=Integer.parseInt(item.get("ycoord"))+verticalOffset;
			
			if (item.get("name").equals("solid-block"))
			{
				g.drawImage(imageMap.get(item.get("type")), x, y, 50, 50, null);
			}
			else if (item.get("name").equals("bull"))
			{
				g.drawImage(imageMap.get("bull"), x, y-20, 72, 120, null);
			}
			else if (item.get("name").equals("zombie"))
			{
				g.drawImage(imageMap.get("zombie"), x, y-20, 80, 120, null);
			}
			else if(item.get("name").equals("doorway"))
			{
				g.drawImage(imageMap.get("door"), x, y, 100, 150, null);
			}
			else if(item.get("name").equals("spikes"))
			{
				g.drawImage(imageMap.get("spikes"), x, y, 50, 50, null);
			}
			else if(item.get("name").equals("ladder"))
			{
				g.drawImage(imageMap.get("ladder"), x, y, 50, 50, null);
			}
			else if(item.get("name").equals("stalactite"))
			{
				g.drawImage(imageMap.get("stalactite"), x, y, 50, 50, null);
			}
			else if(item.get("name").equals("lava"))
			{
				g.drawImage(imageMap.get("lava"), x, y, 50, 50, null);
			}
			else if(item.get("name").equals("glompus"))
			{
				g.drawImage(imageMap.get("glompus"), x, y, 50, 100, null);
			}
			else if(item.get("name").equals("health-pack"))
			{
				g.drawImage(imageMap.get("health-pack"), x+5, y+5, 40, 40, null);
			}

		}
		g.drawImage(imageMap.get(current), width-150, 50, 100, 100, null);
		g.drawImage(imageMap.get("player"), horizontalOffset, verticalOffset, 50, 100, null);
	}
	
	public void exportScene()
	{
		WorldWriter write;
		try {
			write = new WorldWriter("levels/stage" + level + ".txt");
		for (HashMap<String, String> o: sceneMap.values())
		{
			write.writeObject(o);
		}
		for (HashMap<String, String> o: backGroundMap.values())
		{
			write.writeObject(o);
		}

		main.changeScene(new MenuScene(main));

		}
		catch (FileNotFoundException e1) {
			System.out.println("Not Found");
			main.changeScene(new MenuScene(main));
		}
	}

	public void addRoom(int x,int y, int width, int height)
	{
		
		for (int xc=0; xc<=width; xc+=50)
		{
			for (int yc=0; yc<=height; yc+=50)
			{
				
				if (replace || !backGroundMap.containsKey(new Point(x+xc,y+yc)))
				{
				if (xc==0)
				{
					if (yc==0)
					{
						
							addItem(sceneMap,"solid-block","inner-4",x+xc,y+yc);
					}
					else if (yc==height)
					{
						if (sceneMap.containsKey(new Point(x+xc,y+yc)))
						{
							
						}
						else addItem(sceneMap,"solid-block","inner-2",x+xc,y+yc);
					}
					else
					{
						if (sceneMap.containsKey(new Point(x+xc,y+yc)))
						{
					
							if (sceneMap.get(new Point(x+xc,y+yc)).get("type").equals("bottom"))
							{
								addItem(sceneMap,"solid-block","bottom-right",x+xc,y+yc);

							}
							else if (sceneMap.get(new Point(x+xc,y+yc)).get("type").equals("top"))
							{
								addItem(sceneMap,"solid-block","top-right",x+xc,y+yc);

							}
							else addItem(sceneMap,"solid-block","right",x+xc,y+yc);
						}
						else addItem(sceneMap,"solid-block","right",x+xc,y+yc);
					}
				}
				else if (xc==width)
				{
					if (yc==0)
					{
						if (sceneMap.containsKey(new Point(x+xc,y+yc)))
						{
							
						}
						else addItem(sceneMap,"solid-block","inner-3",x+xc,y+yc);
					}
					else if (yc==height)
					{
						if (sceneMap.containsKey(new Point(x+xc,y+yc)))
						{
							
						}
						else addItem(sceneMap,"solid-block","inner-1",x+xc,y+yc);
					}
					else
					{
						if (sceneMap.containsKey(new Point(x+xc,y+yc)))
						{
							if (sceneMap.get(new Point(x+xc,y+yc)).get("type").equals("bottom"))
							{
								addItem(sceneMap,"solid-block","bottom-left",x+xc,y+yc);

							}
							else if (sceneMap.get(new Point(x+xc,y+yc)).get("type").equals("top"))
							{
								addItem(sceneMap,"solid-block","top-left",x+xc,y+yc);

							}
							else
							{
						
								addItem(sceneMap,"solid-block","left",x+xc,y+yc);
							}
						}
						else addItem(sceneMap,"solid-block","left",x+xc,y+yc);
						
					}
				}
				else if (yc==0)
				{
					if (sceneMap.containsKey(new Point(x+xc,y+yc)))
					{
						if (sceneMap.get(new Point(x+xc,y+yc)).get("type").equals("left"))
						{
							addItem(sceneMap,"solid-block","bottom-left",x+xc,y+yc);

						}
						else if (sceneMap.get(new Point(x+xc,y+yc)).get("type").equals("right"))
						{
							addItem(sceneMap,"solid-block","bottom-right",x+xc,y+yc);

						}
						else addItem(sceneMap,"solid-block","bottom",x+xc,y+yc);
						
					}
					else addItem(sceneMap,"solid-block","bottom",x+xc,y+yc);
					
				}
				else if (yc==height)
				{
					if (sceneMap.containsKey(new Point(x+xc,y+yc)))
					{
						if (sceneMap.get(new Point(x+xc,y+yc)).get("type").equals("left"))
						{
							addItem(sceneMap,"solid-block","top-left",x+xc,y+yc);

						}
						else if (sceneMap.get(new Point(x+xc,y+yc)).get("type").equals("right"))
						{
							addItem(sceneMap,"solid-block","top-right",x+xc,y+yc);

						}
						else addItem(sceneMap,"solid-block","top",x+xc,y+yc);
						
					}
					else addItem(sceneMap,"solid-block","top",x+xc,y+yc);
				}
				else
				{
					addItem(backGroundMap,"background","brick",x+xc,y+yc);
					sceneMap.remove(new Point(x+xc,y+yc));

				}
				
			}
			}
		}
	}
	
	public void clearRect(int x,int y, int width, int height)
	{
		for (int xc=0; xc<=width; xc+=50)
		{
			for (int yc=0; yc<=height; yc+=50)
			{
				sceneMap.remove(new Point(x+xc,y+yc));	
				backGroundMap.remove(new Point(x+xc,y+yc));	

			}
		}
	}
	public void addItem(HashMap<Point, HashMap<String, String>> map,String name, int x, int y)
	{
		HashMap<String, String> create = new HashMap<String, String>();
		create.put("name", name);
		create.put("xcoord", Integer.toString(x));
		create.put("ycoord", Integer.toString(y));
		map.put(new Point(x, y), create);
	}
	
	public void addItem(HashMap<Point, HashMap<String, String>> map,String name,String type, int x, int y)
	{
		HashMap<String, String> create = new HashMap<String, String>();
		create.put("name", name);
		create.put("type", type);
		create.put("xcoord", Integer.toString(x));
		create.put("ycoord", Integer.toString(y));
		map.put(new Point(x, y), create);
	}
	
	public void mousePressed(MouseEvent e) {

		int mx=e.getX()/50*50-horizontalOffset;
		int my=e.getY()/50*50-verticalOffset;


		if (box && !erase  && !backgroundErase)
		{
			if (bx==-1 && by==-1)
			{
				bx=mx;
				by=my;
			}
			else
			{
				addRoom(Math.min(bx, mx),Math.min(by, my),Math.abs(mx-bx),Math.abs(my-by));
				bx=-1;
				by=-1;
			}
		}
		else if (box && erase  && !backgroundErase)
		{
			if (bx==-1 && by==-1)
			{
				bx=mx;
				by=my;
			}
			else
			{
				clearRect(Math.min(bx, mx),Math.min(by, my),Math.abs(mx-bx),Math.abs(my-by));
				bx=-1;
				by=-1;
			}
		}
		else if (!box && !erase  && !backgroundErase)
		{	
			if(current.equals("door")) addItem(sceneMap,"doorway",mx,my);
			else if(current.equals("bull")) addItem(sceneMap,"bull",mx,my);
			else if(current.equals("zombie")) addItem(sceneMap,"zombie",mx,my);
			else if(current.equals("glompus")) addItem(sceneMap,"glompus",mx,my);
			else if(current.equals("spikes")) addItem(sceneMap,"spikes",mx,my);
			else if(current.equals("back")) addItem(backGroundMap,"background","brick",mx,my);
			else if(current.equals("ladder")) addItem(sceneMap,"ladder",mx,my);
			else if(current.equals("stalactite")) addItem(sceneMap,"stalactite",mx,my);
			else if(current.equals("lava")) addItem(sceneMap,"lava",mx,my);
			else if(current.equals("health-pack")) addItem(sceneMap,"health-pack",mx,my);

			else addItem(sceneMap,"solid-block",current,mx,my);
		}
		else if (!box && erase  && !backgroundErase)
		{
			sceneMap.remove(new Point(mx,my));	
		}
		else if (backgroundErase)
		{
			backGroundMap.remove(new Point(mx,my));
		}
		
	}

	public void mouseDragged(MouseEvent e) {
		int mx=e.getX()/50*50-horizontalOffset;
		int my=e.getY()/50*50-verticalOffset;
		
		if (!box && !erase && !backgroundErase)
		{	
			if(current.equals("door")) addItem(sceneMap,"doorway",mx,my);
			else if(current.equals("bull")) addItem(sceneMap,"bull",mx,my);
			else if(current.equals("zombie")) addItem(sceneMap,"zombie",mx,my);
			else if(current.equals("spikes")) addItem(sceneMap,"spikes",mx,my);
			else if(current.equals("back")) addItem(backGroundMap,"background","brick",mx,my);
			else if(current.equals("ladder")) addItem(sceneMap,"ladder",mx,my);
			else if(current.equals("glompus")) addItem(sceneMap,"glompus",mx,my);
			else if(current.equals("stalactite")) addItem(sceneMap,"stalactite",mx,my);
			else if(current.equals("lava")) addItem(sceneMap,"lava",mx,my);
			else if(current.equals("health-pack")) addItem(sceneMap,"health-pack",mx,my);

			else addItem(sceneMap,"solid-block",current,mx,my);
		}
		else if (!box && erase  && !backgroundErase)
		{
			sceneMap.remove(new Point(mx,my));	
		}
		else if (!box && backgroundErase)
		{
			backGroundMap.remove(new Point(mx,my));
		}

	}
	
	public void keyPressed(KeyEvent e) {

		int keyCode=e.getKeyCode();
		switch(keyCode)
		{
		case KeyEvent.VK_LEFT:
			if (currentitem==itemList.size()-1)
				currentitem=0;
			else
				currentitem++;
			current=itemList.get(currentitem);
			break;
		case KeyEvent.VK_RIGHT:
			if (currentitem==0)
				currentitem=itemList.size()-1;
			else
				currentitem--;
			current=itemList.get(currentitem);
			break;
		case KeyEvent.VK_C:
			erase=!erase;
			break;
		case KeyEvent.VK_M:
			sceneMap=new HashMap<Point,HashMap<String,String>>();
			backGroundMap=new HashMap<Point,HashMap<String,String>>();

			break;
		case KeyEvent.VK_W:
			verticalOffset+=50;
			break;
		case KeyEvent.VK_A:
			horizontalOffset+=50;
			break;
		case KeyEvent.VK_S:
			verticalOffset-=50;
			break;
		case KeyEvent.VK_D:
			horizontalOffset-=50;
			break;
		case KeyEvent.VK_G:
			box=!box;
			bx=-1;
			by=-1;
			break;
		case KeyEvent.VK_R:
			replace=!replace;
			break;
		case KeyEvent.VK_Q:
			exportScene();
			break;
		case KeyEvent.VK_B:
			backgroundErase=!backgroundErase;
			break;
		}


	}

	//Unused Listener Methods
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}

}
