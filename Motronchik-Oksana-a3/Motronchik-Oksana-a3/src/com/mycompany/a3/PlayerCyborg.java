package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class PlayerCyborg extends Cyborg
{
	private static PlayerCyborg playerCyborg;
	private static int cyborgSize = 40;
	
	private PlayerCyborg(int size, float x, float y) 
	{
		super(size,x,y);
	}
	
	public static void resetCyborg() 
	{
		playerCyborg = null;
	} 
	public static PlayerCyborg getPlayerCyborg() 
	{
		if (playerCyborg == null)
			playerCyborg = new PlayerCyborg(cyborgSize, 500, 300 );
		return playerCyborg;
	}
		
	public void draw(Graphics g, Point containerOrigin) 
	{
		int halfSize = getSize()/2;
		int centerX = (int)containerOrigin.getX() + (int)this.getLocation().getX();
		int centerY = (int)containerOrigin.getY() + (int)this.getLocation().getY();
		
		int xCorner = centerX - halfSize;
		int yCorner = centerY - halfSize;
		
		g.setColor(this.getColor());

		g.fillRect(xCorner, yCorner, this.getSize(), this.getSize());
		
	}
	
	@Override
	public void handleCollision(GameObject otherObject) 
	{
		if(otherObject instanceof NonPlayerCyborg) 
		{ 
			this.cybCollide();
		}
		else if(otherObject instanceof Base) 
		{
			int x = ((Base) otherObject).getSequenceNumber();
			this.baseCollide(x);
		}
		else if(otherObject instanceof EnergyStation) 
		{
			int capacity = ((EnergyStation) otherObject).getCapacity();
			this.setEnergyLevel(capacity);
			
		}
		else if(otherObject instanceof Drone) 
		{
			this.droneCollide();
		}			
	}
	
	//override of toString
	@Override
	public String toString() {
		String parentDesc = super.toString();
		return "Player Cyborg:" + parentDesc + " lives: " + getLives();
	}

}
