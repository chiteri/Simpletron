// Author: Martin Akolo 
// Date: 25th July, 2007; 14:43 Hrs
// A program to abstract the data item in a simpletron 

public class Data 
{
	// data member for the class
	private int data;

	public Data () 
	{
		this ( 0 );
	}	// end no-argument constructor 
	
	// a one argument constructor for the class 
	public Data ( int value ) 
	{
		setData ( value );
	}	// end method getData 

	// a set method for the class initialise the data
	public void setData ( int item ) 
	{
		data = ( item >= 0 ) ? item : 0;
	}	// end constructor 

	// a get method for the class 
	public int getData ( ) 
	{
		return data;
	}	// end method getData
	

}	// end class Data
