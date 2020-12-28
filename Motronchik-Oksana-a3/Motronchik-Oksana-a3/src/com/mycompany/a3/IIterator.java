package com.mycompany.a3;

/*which defines at least two methods: for checking whether there are more elements to be processed in the collection 
 * (i.e., hasNext()) and returning the next element to be processed from the collection (i.e., getNext())
 */
public interface IIterator 
{
	public boolean hasNext();
	public Object getNext();

}
