package main;

import java.awt.*;
import java.util.ArrayList;

/*******************************************************************************
 * Thomas Barrett
 * Period 1
 * Mar 24, 2016
 * Description: Basic scene that updates and paints a list of game objects
 ******************************************************************************/

public abstract class Scene implements Directions {

	/** the main GameApplet */
	public GameWindow main;

	/** the dimensions of the scene */
	public int width=0;
	public int height=0;

    /** all game objects in the scene */
    public ArrayList<GameObject> scene = new ArrayList<GameObject>();

	/** all game objects to be added to the scene before the next frame */
    private ArrayList<GameObject> toBeAdded = new ArrayList<GameObject>();
    
    /** all game objects to be removed to the scene before the next frame */
    private ArrayList<GameObject> toBeRemoved = new ArrayList<GameObject>();

    /***************************************************************************
     * constructor
     ***************************************************************************/
    public Scene(GameWindow main)
    {
    	this.main=main;
    	this.width=main.getWidth();
    	this.height=main.getHeight();
    }
    
    /***************************************************************************
     * Adds an object to the scene
     ***************************************************************************/
    public void add(GameObject o)
    {
    	toBeAdded.add(o);
    }
    
    /***************************************************************************
     * Removes an object from the scene
     ***************************************************************************/
    public void remove(GameObject o)
    {
    	toBeRemoved.add(o);
    }

    
    /***************************************************************************
    * updates all game objects, or removes them if no longer alive
    ***************************************************************************/
    public void update()
    {
    	scene.addAll(toBeAdded);
    	scene.removeAll(toBeRemoved);
    	
        toBeAdded=new ArrayList<GameObject>();
        toBeRemoved=new ArrayList<GameObject>();

    	for (GameObject o: scene)
    	{
    		o.update();
    	}
   
    }

    /***************************************************************************
     * returns size of string in pixels
     ***************************************************************************/
    public int stringWidth(Graphics g, Font f, String s)
    {
    	return g.getFontMetrics(f).stringWidth(s);
    }
    
    /***************************************************************************
     * paints all game objects
     ***************************************************************************/
     public void paint(Graphics g)
     {
         for (GameObject o : scene)
         {
             o.paint(g);
         }
     }
  
}
