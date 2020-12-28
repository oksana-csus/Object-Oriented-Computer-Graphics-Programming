package com.mycompany.a3;

import com.codename1.charts.models.Point;
import java.util.ArrayList;
import com.codename1.charts.util.ColorUtil;

public abstract class GameObject implements IDrawable, ICollider
{
	private int size;
	private Point location;
	private int objColor;
	private ArrayList<GameObject> collideObject = new ArrayList<>();
	private boolean collidable;
	
	public GameObject(int size, float x, float y) 
	{
		location = new Point(x, y);
		this.size = size;
	}

	public GameObject(int seq, int size, float x, float y) {
		// TODO Auto-generated constructor stub
	}

	public int getSize() 
	{
		return size;
	}
	
	public void setSize(int size) 
	{
		this.size = size;
	}
	
	public void setLocation(float x, float y) 
	{
		location = new Point(x, y);
	}
	
	public Point getLocation() 
	{
		return this.location;
	}
	
	public void setX(float x)
	{
		location.setX(x);
	}
	
	public void setY(float y) 
	{
		location.setY(y);
	}  
	
	public int getX()
	{
		return (int)location.getX();
	}
	
	public int getY() 
	{
		return (int)location.getY();
	}
	
	public void setColor(int color) 
	{
		this.objColor = color;
	}
	
	public int getColor()
	{
		return objColor;
	}
	
	public boolean isCollidable() 
	{
		return this.collidable;
	}
	
	protected void setCollidable(boolean collidable) 
	{
		this.collidable = collidable;
	}
	
	public boolean collidesWith(GameObject otherObject) 
	{
		boolean result = false;
		int r1 = (int) this.getX() + this.getSize()/2;
		int l1 = (int) this.getX() - this.getSize()/2;
		int t1 = (int) this.getY() + this.getSize()/2;
		int b1 = (int) this.getY() - this.getSize()/2;
		
		int r2 = (int) otherObject.getX() + otherObject.getSize()/2;
		int l2 = (int) otherObject.getX() - otherObject.getSize()/2;
		int t2 = (int) otherObject.getY() + otherObject.getSize()/2;
		int b2 = (int) otherObject.getY() - otherObject.getSize()/2;
		
		if((r1<l2 || l1>r2) || (t2<b1 || t1<b2))
			result = false;
		else if(!this.collideObject.contains(otherObject))
		{
			result = true;
			this.collideObject.add(otherObject);
		}
		if(this.collideObject.contains(otherObject)) 
		{
			if((r1<l2 || l1>r2) || (t2<b1 || t1<b2)) 
			{
				result = false;
				this.collideObject.remove(otherObject);
			}
		}
		
		return result;
	}
	
	public String toString() 
	{
		String myDesc = " loc= "+location.getX()+ location.getY()+" color=["+ ColorUtil.red(objColor) + "," + ColorUtil.green(objColor) + "," + ColorUtil.blue(objColor) + "]";
		return myDesc;
	}
}
