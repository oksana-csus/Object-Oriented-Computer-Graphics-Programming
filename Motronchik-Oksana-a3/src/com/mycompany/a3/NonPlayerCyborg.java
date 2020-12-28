package com.mycompany.a3;

import java.util.Random;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class NonPlayerCyborg extends Cyborg
{
	private Strategy currentStrategy;
	private int energyLevel = 200;
	Random rand = new Random();

	public NonPlayerCyborg(int size, float x, float y) 
	{
		super(size, x,y);
		this.setEnergyLevel(energyLevel);
	}
	
	public Strategy getStrategy() 
	{
		return currentStrategy;
	}
	
	public void setStrategy(Strategy s) 
	{
		currentStrategy = s;
	}
	
	public void invokeStrategy() 
	{
		currentStrategy.apply();
	}
	
	public void draw(Graphics g, Point containerOrigin) 
	{
		int halfSize = getSize()/2;
		int centerX = (int)containerOrigin.getX() + (int)this.getLocation().getX();
		int centerY = (int)containerOrigin.getY() + (int)this.getLocation().getY();
		
		int xCorner = centerX - halfSize;
		int yCorner = centerY - halfSize;
		
		g.setColor(this.getColor());

		g.drawRect(xCorner, yCorner, this.getSize(), this.getSize());
		
	}
	
	
	@Override
	public String toString() 
	{
		String parentDesc = super.toString();
		String thisDesc;
		try 
		{
			thisDesc = " Strategy: " + getStrategy().toString();
		}
		catch(Exception e) 
		{
			thisDesc = " Strategy=" + "none";
		}
		return "NPC: " + parentDesc + thisDesc;
	
	}
	
	@Override
	public void handleCollision(GameObject otherObject) 
	{
		if(otherObject instanceof PlayerCyborg) 
		{ 
			this.cybCollide();
		}
		else if(otherObject instanceof Base) 
		{
			int x = ((Base) otherObject).getSequenceNumber();
			this.baseCollide(x);
		}
		else if(otherObject instanceof Drone) 
		{
			this.droneCollide();
		}
	}
}
