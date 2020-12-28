package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;

public class Base extends Fixed
{
	private int sequenceNumber;
	
	public Base(int size, int sequenceNumber, float x, float y) 
	{
		super(size, x, y);
		super.setColor(ColorUtil.BLUE);
		this.sequenceNumber = sequenceNumber;
	}

	@Override
	public void setColor(int color) 
	{
		
	}
	
	public int getSequenceNumber() 
	{
		return sequenceNumber;
		
	}
	
	//draw base as filled triangle
	public void draw(Graphics g, Point containerOrigin) 
	{
		int halfSize = this.getSize() / 2;
		int centerX = (int)containerOrigin.getX() + (int)this.getLocation().getX();
		int centerY = (int)containerOrigin.getY() + (int)this.getLocation().getY();
		
		int xCorner1 = centerX; 
		int yCorner1 = centerY + halfSize;

		int xCorner2 = centerX - halfSize;
		int yCorner2 = centerY - halfSize;
		
		int xCorner3 = centerX + halfSize;
		int yCorner3 = centerY - halfSize;
		
		int xPoints[] = {xCorner1, xCorner2, xCorner3};
		int yPoints[] = {yCorner1, yCorner2, yCorner3};
		g.setColor(this.getColor());
		
		/* Draws a filled triangle if the base isn't selected. 
		 * Unfilled if it is selected */
		if(this.isSelected()) 
		{
			g.drawPolygon(xPoints, yPoints, 3);
			g.setColor(ColorUtil.BLACK);
		}
		else 
		{
			g.fillPolygon(xPoints, yPoints, 3);
			g.setColor(ColorUtil.BLACK);
		}
		
		g.setFont(Font.createTrueTypeFont("native:MainRegular").
                derive(halfSize, Font.STYLE_PLAIN));
		g.setColor(ColorUtil.WHITE);
		g.drawString(String.valueOf(sequenceNumber), centerX - (halfSize/3), 
				(int)(centerY - (double)(halfSize/1.5)));

	}
	
	public void handleCollision(GameObject otherObject) 
	{
		
	}
	
	@Override
	public String toString() 
	{
		String parentDesc = super.toString();
		String myDesc = "size= "+getSize()+" seqNum = "+sequenceNumber;
		return "Base:" + parentDesc + myDesc;
	}

}
