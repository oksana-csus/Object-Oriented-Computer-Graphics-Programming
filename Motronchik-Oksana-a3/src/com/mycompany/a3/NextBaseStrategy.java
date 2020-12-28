package com.mycompany.a3;

import com.codename1.util.MathUtil;

public class NextBaseStrategy implements Strategy 
{

	private NonPlayerCyborg nonPlayerCyborg;
	private GameObjectCollection allObjects;
	private int newHeading;
	private int lastBaseReached = 0;
	private int targetBase;
	private int targetBaseX;
	private int targetBaseY;
	
	public NextBaseStrategy(NonPlayerCyborg npc, GameObjectCollection objects) 
	{
		nonPlayerCyborg = npc;
		allObjects = objects;
	}
	
	public void apply() 
	{
		getNewBaseLoacation();
		
		float a = Math.abs(nonPlayerCyborg.getX() - targetBaseX);
		float b = Math.abs(nonPlayerCyborg.getY() - targetBaseY);
		double thetaRads = MathUtil.atan2((double)a, (double)b);
		int angle = (int)Math.toDegrees(thetaRads);
		newHeading = calculateHeading(angle, nonPlayerCyborg.getX(), nonPlayerCyborg.getY(), targetBaseX, targetBaseY);
		nonPlayerCyborg.setHeading(newHeading);

	}
	
	private void getNewBaseLoacation() 
	{
		IIterator tempObjList = allObjects.getIterator();
		lastBaseReached = nonPlayerCyborg.getLastBaseReached();
		while(targetBase != (lastBaseReached+1)) 
		{
			GameObject currentObject = (GameObject)tempObjList.getNext();
			if(currentObject instanceof Base) 
			{
				Base b = (Base)currentObject;
				if (b.getSequenceNumber() == (nonPlayerCyborg.getLastBaseReached() + 1)) 
				{
					targetBase = b.getSequenceNumber();
					targetBaseX = b.getX();
					targetBaseY = b.getY();
				}
			}
		}

	}
	
	private int calculateHeading(int angle, float x1, float y1, int targetX, int targetY) 
	{
		int heading;
		if((targetX > x1) && (targetY < y1)) 
		{ 
			heading = 180 - angle;
		}
		else if((targetX < x1) && (targetY < y1)) 
		{ 
			heading = angle + 180;
		}
		else if ((targetX < x1) && (targetY > y1)) 
		{ 
			heading = 360 - angle;
		}
		else heading = angle; 
		return heading;
	}
	public String toString() 
	{
		return "NextBaseStrategy";
	}
}
