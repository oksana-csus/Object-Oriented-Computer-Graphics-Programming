package com.mycompany.a3;
import java.util.ArrayList;

/*The iterator should exist as a private inner class inside game object collection class and should implement an interface called IIterator 
 * Implements the Iterator Design Pattern */
public class GameObjectCollection implements ICollection 
{
	private ArrayList<GameObject> theCollection;
	
	public GameObjectCollection() 
	{
		theCollection = new ArrayList<>();
	}
	
	public void add(Object newObject) 
	{
		GameObject currentObject = (GameObject)newObject;
		theCollection.add(currentObject);
	}

	public IIterator getIterator() 
	{
		return new GameObjectIterator();
	}

	private class GameObjectIterator implements IIterator 
	{
		private int currElementIndex;
		
		public GameObjectIterator() 
		{
			currElementIndex = -1;
		}
		
		@Override
		public boolean hasNext() 
		{
			if (theCollection.size() <= 0) 
				return false;
			if (currElementIndex == theCollection.size() -1)
				return false;
			return true;
		}
		
		@Override
		public Object getNext() 
		{
			currElementIndex ++;
			return(theCollection.get(currElementIndex));
		}
	} //end private iterator class

}
