package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

import java.util.Random;

public class Drone extends Moveable 
{
	private Random rand = new Random();
	
	public Drone(int size, float x, float y)
	{
		super(size, x, y);
		super.setColor(ColorUtil.rgb(128, 0, 128));
		setHeading(rand.nextInt(359));
		//speed between 5 and 10
		setSpeed(rand.nextInt(6) + 5);
	}
	
	@Override
	public void setColor(int color) 
	{
		
	}
	
	@Override
	public void setHeading(int heading) 
	{
		super.setHeading(rand.nextInt(359));
	}

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
		g.drawPolygon(xPoints, yPoints, 3);
	}
	
	@Override
	public void handleCollision(GameObject otherObject) 
	{
		if(otherObject instanceof PlayerCyborg) 
		{
			int newHeading = ((Cyborg) otherObject).getHeading();
			this.setHeading(newHeading);
		}
	}
	
	public String toString() 
	{
		String parentDesc = super.toString();
		String myDesc = " size= "+getSize();
		return "Drone:" + parentDesc+ myDesc;
	}
	
}
