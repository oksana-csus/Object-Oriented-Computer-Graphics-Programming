package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public abstract class Cyborg extends Moveable implements ISteerable 
{	
	private int maximumSpeed=50;
	private int energyLevel=300;
	private double energyConsumptionRate = 1;
	private int damageLevel=0;
	private int maxDamage = 100;
	private int lastBaseReached =1;
	private int steeringDirection = 0;
	private int rgb1 = 255;
	private int rgb2 = 0;
	private int rgb3 = 0;
	private int lives=3;
	
	public Cyborg(int size, float x, float y) 
	{
		super(size,x,y);
		super.setSpeed(2);
		super.setColor(ColorUtil.rgb(rgb1, rgb2, rgb3));
	}
	
	@Override
	public void changeHeading(char s) 
	{
		if(s=='l')
			if(steeringDirection >= (-40))
				steeringDirection-=5;
		if(s=='r')
			if(steeringDirection <= 40)
				steeringDirection+=5;
	}
	
	public void steerRight() 
	{
		System.out.println("Steered right invoked");
		//if(canSteerRight())
			//steeringDirection = steeringDirection + GWInfo.TurnAmount;
		this.steeringDirection = ((this.steeringDirection + 45)+359) % 359;
		this.setHeading(this.steeringDirection);
	}
	
	public void steerLeft()
	{
		System.out.println("Steered left invoked");
		//if (canSteerLeft()) 
		//{
		//	steeringDirection = steeringDirection - GWInfo.TurnAmount;
		//}
		this.steeringDirection = ((this.steeringDirection - 45)+359) % 359;
		this.setHeading(this.steeringDirection);

	}
	
	@Override
	public void setHeading(int heading) 
	{
		super.setHeading(heading);
	}
	
	public boolean canSteerRight() 
	{
		if (steeringDirection >= GWInfo.MAX_RIGHT)
			return false;
		else return true;
	}
	
	public boolean canSteerLeft() 
	{
		if (steeringDirection <= GWInfo.MAX_LEFT)
			return false;
		else return true;
	}
	//collides with base
	public void baseCollide(int base) 
	{
		if(base==(lastBaseReached+1))
			lastBaseReached=base;
	}
	
	//collides with cyborg
	public void cybCollide() 
	{
		if(damageLevel < maxDamage) 
		{
			damageLevel++;
			fadeColor();
			setColor(ColorUtil.rgb(rgb1, rgb2, rgb3));
			maximumSpeed -= maximumSpeed*(damageLevel/maxDamage);
			if(maximumSpeed == 0)
				lives-=1;
		}
	}
	private void fadeColor()
	{
		if(rgb2<250) 
		{
			rgb2+=10;
			rgb3+=10;
		}

	}
	

	public void move(int timeElapsed) 
	{
		super.move(timeElapsed);
		this.energyLevel = (int) (this.energyLevel - (energyConsumptionRate/timeElapsed));
	}
	
	//collides with drone
	public void droneCollide()
	{
		if(damageLevel < maxDamage) 
		{
			damageLevel+=1;
			fadeColor();
			setColor(ColorUtil.rgb(rgb1, rgb2, rgb3));
			maximumSpeed -= maximumSpeed*(damageLevel/maxDamage);
			if(maximumSpeed == 0)
				lives-=1;
		}
	}
	
	//setting energy level with int 
	public void setEnergyLevel(int energy) 
	{
			this.energyLevel = energy;
	}
	
	//set energy by consumption rate
	public void setEnergyLevel() 
	{
			this.energyLevel -= energyConsumptionRate;
	}
	
	public int getEnergyLevel() 
	{
		return this.energyLevel;
	}
	
	public int getDamageLevel() 
	{
		return damageLevel;
	}
	
	public int getLives() 
	{
		return lives;
	}
	
	public int getSteeringDirection() 
	{
		return steeringDirection;
	}
	
	public void setSteeringDirection(int dir) 
	{
		this.steeringDirection = dir;
	}
	
	@Override
	public void setSpeed(int speed) 
	{
		if(speed <= maximumSpeed )
			super.setSpeed(speed);
	}
	
	public int getLastBaseReached() 
	{
		return lastBaseReached;
	}
	
	//set all values to original
	public void reInitializeCyborg() 
	{
		setX(500);
		setY(300);
		damageLevel=0;
		this.energyLevel=300;
		lives-=1;
	}
	
	public String toString() 
	{
		String parentDesc = super.toString();
		String myDesc = " maxSpeed=" + maximumSpeed + " steeringDirection= "+steeringDirection+ " energyLevel= "+ energyLevel+ " damageLevel= " + damageLevel;
		return "" + parentDesc + myDesc;
		
	}
	
}
