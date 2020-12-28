package com.mycompany.a3;

import com.codename1.util.MathUtil;

public class AttackStrategy implements Strategy 
{
	private NonPlayerCyborg nonPlayerCyborg;
	private int newHeading;
	
	public AttackStrategy(NonPlayerCyborg npc) 
	{
		nonPlayerCyborg = npc;
	}
	
	public void apply() 
	{
		float npcX = nonPlayerCyborg.getX();
		float npcY = nonPlayerCyborg.getY();
		float playerX = PlayerCyborg.getPlayerCyborg().getX();
		float playerY = PlayerCyborg.getPlayerCyborg().getY();
		
		float a = Math.abs(nonPlayerCyborg.getX() - playerX);
		float b = Math.abs(nonPlayerCyborg.getY() - playerY);
		double thetaRads = MathUtil.atan2((double)a, (double)b);
		int angle = (int)Math.toDegrees(thetaRads);
		newHeading = calculateHeading(angle, npcX, npcY, playerX, playerY);
		nonPlayerCyborg.setHeading(newHeading);
	}
	
	private int calculateHeading(int angle, float x1, float y1, float targetX, float targetY) 
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
		return "AttackStrategy";
	}

}
