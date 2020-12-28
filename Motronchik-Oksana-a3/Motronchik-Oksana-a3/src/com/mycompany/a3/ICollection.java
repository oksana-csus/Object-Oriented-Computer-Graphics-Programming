package com.mycompany.a3;

/*defines at least two methods: for adding an object to the collection (i.e., add()) and for obtaining an iterator over the collection (i.e., getIterator()). 
 * The iterator should exist as a private inner class inside game object collection class and should implement an interface called IIterator
 */
public interface ICollection 
{
	public void add(Object newObject);
	public IIterator getIterator();

}
