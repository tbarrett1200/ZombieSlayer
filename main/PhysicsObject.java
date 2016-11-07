package main;

import java.util.ArrayList;

import scenes.GameScene;



public class PhysicsObject extends GameObject{

	protected int gravity;
		
	public String name;
	
	public ArrayList<String> collidesWith = new ArrayList<String>();
	public ArrayList<String> intersectsWith = new ArrayList<String>();
	public ArrayList<GameObject> intersections=new ArrayList<GameObject>();

	public void didBeginIntersection(GameObject o){}
	public void didEndIntersection(GameObject o){}
		
	//constructor
	public PhysicsObject(GameScene scene, String name, double x, double y, int width, int height)
	{
		super(scene,x, y, width, height);
		this.name=name;
	}

	//sets gravity
	public void setGravity(int gravity)
	{
		this.gravity=gravity;
	}

	//tests if an object is within a certain horizontal or vertical range
	public boolean objectInRange(GameObject o, int horizontal, int vertical)
	{
		if (Math.abs((o.x+o.width/2)-(x+width/2))<=horizontal)
		{
			if (Math.abs((o.y+o.height/2)-(y+height/2))<=vertical)
			{
				return true;
			}
		}
		return false;
	}
	
	public double horizontalOverlap(GameObject o)
	{
		double l=(o.x-(x+width))/dx;
		double r=((o.x+o.width)-x)/dx;
		
		return Math.min(l, r);
	}
	public double verticalOverlap(GameObject o)
	{
		double t=(o.y-(y+height))/dy;
		double b=((o.y+o.height)-(y))/dy;
		
		return Math.min(t, b);

	}
	public boolean touchingGround()
	{
		for (GameObject object:new ArrayList<GameObject>(scene.scene))
		{
			if (object instanceof PhysicsObject)
			{
				if (new GameObject(x,y+1,width,height).intersects(object) && collidesWith.contains(((PhysicsObject)object).name))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean touchingSides()
	{
		for (GameObject object:new ArrayList<GameObject>(scene.scene))
		{
			if (object instanceof PhysicsObject)
			{
				if (new GameObject(x-1,y,width+2,height).intersects(object) && collidesWith.contains(((PhysicsObject)object).name))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public void update()
	{
		
		for (GameObject object:new ArrayList<GameObject>(intersections))
		{
			if (object instanceof PhysicsObject)
			{
				if (!new GameObject(x+dx,y+dy,width,height).intersects(object) && intersectsWith.contains(((PhysicsObject)object).name))
				{
					didEndIntersection(object);
					intersections.remove(object);
				}
			}
		}

				
		double nearestHorizontalCollision=1;
		double nearestVerticalCollision=1;
		
		for (GameObject o:scene.scene)
		{
			if (o!=this && o instanceof PhysicsObject && new GameObject(x+dx,y+dy,width,height).intersects(o))
			{
			
				for(String s: intersectsWith)
				{
					if(((PhysicsObject)o).name.contains(s))
					{
						if (!intersections.contains(o))
						{
							didBeginIntersection(o);
							intersections.add(o);
						}
						break;
					}
				}
				
				for(String s: collidesWith)
				{
					if(((PhysicsObject)o).name.contains(s))
					{
						double possibleHorizontalCollision=horizontalOverlap(o);
						double possibleVerticalCollision=verticalOverlap(o);
						
						double collision=Math.max(possibleHorizontalCollision, possibleVerticalCollision);

						
						if (collision==possibleHorizontalCollision)
						{
							if (collision>=0 && collision<nearestHorizontalCollision) nearestHorizontalCollision = collision;
						}
						else if (collision==possibleVerticalCollision)
						{
							if (collision>=0 && collision<nearestVerticalCollision) nearestVerticalCollision = collision;
						}
					}
				}
	
			}	
		}
			
		x+=nearestHorizontalCollision*dx;
		y+=nearestVerticalCollision*dy;
		
		if (nearestVerticalCollision == 0.0) dy=0;
		else dy+=gravity;
		
	}
	

}

