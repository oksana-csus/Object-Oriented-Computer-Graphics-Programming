package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.mycompany.a3.GWInfo;

public abstract class Moveable extends GameObject 
{
	private int heading = 0;
	private int speed = 0;

	public Moveable(int size, float x, float y) 
	{
		super(size, x, y);
	}

	public void move(int elapsedTime) 
	{
		double theta = 90 - heading;
		theta = Math.toRadians(theta);
		float deltaX = (float) (Math.cos(theta)* speed);
		float deltaY = (float) (Math.sin(theta)* speed);
		
		float newLocationX = super.getX() + deltaX;
		float newLocationY = super.getY() + deltaY;
		
		if(newLocationX < 5.0 || newLocationX > GWInfo.gameSizeX()) 
		{
			if(newLocationX < 0)
				newLocationX = 0;
			if(newLocationX > GWInfo.gameSizeX() )
				newLocationX = GWInfo.gameSizeX();
			
			int turnAround = (this.getHeading() + 180) % 359;

			this.setHeading(turnAround);
		}

		else if(newLocationY < 5.0 || newLocationY > GWInfo.gameSizeY()) 
		{
			if(newLocationY <0)
				newLocationY = 0;
			if(newLocationY > GWInfo.gameSizeY())
				newLocationY = GWInfo.gameSizeY();
			
			int turnAround = (this.getHeading() + 180) % 359;

			this.setHeading(turnAround);
		}
				
		super.setLocation(newLocationX, newLocationY);
	}
	
	public void setSpeed(int speed) 
	{
		this.speed = speed;
	}
	public int getSpeed()
	{
		return speed;
	}
	public int getHeading()
	{
		return heading;
	}

	protected void setHeading(int newHeading) 
	{
		this.heading = newHeading;
	}
	
	
	public String toString() {
		String parentDesc = super.toString();
		String myDesc = " Heading = "+heading+" Speed = "+speed;
		return parentDesc+myDesc;
	}

}
