package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;

public class EnergyStation extends Fixed
{
	private int capacity;
	
	public EnergyStation(int size, float x, float y) 
	{
		super(size, x, y);
		super.setColor(ColorUtil.rgb(34, 139, 34)); //green 34,139,34
		this.capacity = size; 
	}
	
	public void decreaseEnergy() 
	{
		this.capacity = 0;
		super.setColor(ColorUtil.rgb(196, 255, 231));
	}
	
	public int getCapacity() 
	{
		return capacity;
	}
	
	public void setCapacity(int size) 
	{
		this.capacity = size;
	}

	public void setCapacity() 
	{
		capacity=0;
	}
	
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) 
	{
		int objSize = getSize()/2;
		int centerX = (int)pCmpRelPrnt.getX() + (int)this.getLocation().getX();
		int centerY = (int)pCmpRelPrnt.getY() + (int)this.getLocation().getY();
		
		int xCorner = centerX - objSize;
		int yCorner = centerY - objSize;

		// draw filled circle
		g.setColor(this.getColor());
		
		/* Filled circle if not selected.
		 * Unfilled if selected */
		if(this.isSelected()) {
			g.drawArc(xCorner, yCorner, this.getSize(), this.getSize(), 0, 360);
			g.setColor(ColorUtil.WHITE); // sets color of font
		}
		else {
			g.fillArc(xCorner, yCorner, this.getSize(), this.getSize(), 0, 360);
			g.setColor(ColorUtil.BLACK); // sets color of font
		}
		// draw capacity size in center of circle
		g.setFont(Font.createTrueTypeFont("native:MainRegular").
                derive(objSize, Font.STYLE_PLAIN));
		g.drawString(String.valueOf(capacity), centerX - (objSize/2), centerY - (objSize/2));
	}
	
	@Override
	public void handleCollision(GameObject otherObject) 
	{
		if(otherObject instanceof PlayerCyborg) 
		{ 
			this.decreaseEnergy();
		}
	}
	
	@Override
	public String toString() 
	{
		String parentDesc = super.toString();
		String myDesc =  "size= "+getSize()+" capacity="+capacity;
		return "Energy Station:" + parentDesc + myDesc;
	
	}



}
