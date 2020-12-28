package com.mycompany.a3;

import com.codename1.charts.models.Point;

public abstract class Fixed extends GameObject implements ISelectable
{
	private boolean isSelected = false;
	private boolean isToBeMoved = false;
	
	public Fixed(int size, float x, float y) 
	{
		super(size, x, y);
	}
	
	public void setLocation(float x, float y) 
	{
		super.setLocation(x, y);
		isToBeMoved = false;
	}
	
	public void setSelected(boolean s) 
	{
		isSelected = s;
	
	}
	public boolean isSelected() 
	{
		return isSelected;
	}
	public void willBeMoved() 
	{ 
		isToBeMoved = true; 
	}
	public boolean isToBeMoved() 
	{ 
		return isToBeMoved; 
	}
	
	public Fixed(int seq, int size, float x, float y) 
	{
		super(seq, size, x, y);
	}
	
	public boolean contains(Point mousePointer) 
	{
		float mouseX = mousePointer.getX();
		float mouseY = mousePointer.getY();
		
		float fixedX = this.getX() - (this.getSize()/2);
		float fixedY = this.getY() - (this.getSize()/2);
		
		if((mouseX >= fixedX) && (mouseX <= (fixedX + getSize()) 
				&& (mouseY >= fixedY) && (mouseY <= (fixedY + getSize()))))
			return true;
		else return false;
	}

}
