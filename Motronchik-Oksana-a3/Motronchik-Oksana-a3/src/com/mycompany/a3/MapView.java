package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.models.Point;
import com.codename1.ui.*;
import com.codename1.ui.plaf.Border;
import java.util.Observer;
import java.util.Observable;

public class MapView extends Container implements Observer
{
	private GameWorld gw;
	
	public MapView(Observable o) 
	{
		gw = (GameWorld) o;
		gw.addObserver(this);
		//this.setHeight(1000);
		//this.setWidth(1000);
		this.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.rgb(255, 0, 0)));
	}
	
	public void update (Observable o, Object arg) 
	{
		gw = (GameWorld)o;		
		gw.addObserver(this);
		gw.displayMap(); 		
		repaint();
	}
	
	public void paint(Graphics g) 
	{
		super.paint(g);
		IIterator allObjects = gw.getObjectCollection().getIterator();
		Point thisOrigin = new Point(getX(), getY());
		while(allObjects.hasNext()) {
			GameObject o = (GameObject)allObjects.getNext();
			o.draw(g, thisOrigin);
		}
	}
	
	@Override
	public void pointerPressed(int x, int y) 
	{
        if (!gw.isPaused()) 
        {
            return;
        }
		if(gw.isPaused()) 
		{
			x = x - getX() - getParent().getAbsoluteX();;
			y = y - getY() - getParent().getAbsoluteY();
			Point mousePointer = new Point(x, y);
			IIterator allObjects = gw.getObjectCollection().getIterator();
			while(allObjects.hasNext()) 
			{
				GameObject object = (GameObject)allObjects.getNext();
				if(object instanceof Fixed) 
				{
					Fixed fixedObj = (Fixed)object;
					
					if(fixedObj.isToBeMoved() && fixedObj.isSelected()) 
					{
						System.out.println(fixedObj.toString());
						fixedObj.setLocation(x, y);
						fixedObj.setSelected(false);
						break;
					}
					
					else if(fixedObj.contains(mousePointer)) 
					{
						gw.deselectAll();
						fixedObj.setSelected(true);
						break;
					}
					else fixedObj.setSelected(false);					
				}
			}
			repaint();
		}
	}


}
