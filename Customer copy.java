package assignment2;

public class Customer {
	protected String name;
	protected int number;
	protected String address;
	protected int[] accounts;		// Holds account numbers for Customer

	
	public Customer(){				// Default constructor
		name = new String();
		number = -1;
		address = new String();
		accounts = new int[4];
	}
	
	public Customer(String person, int id, String add, int[] accs){		// Constructor with all fields provided
		name = person;
		number = id;
		address = add;
		accounts = accs;
	}
	
	//Get methods
	public String getName(){
		return name;
	}
	
	public int getNumber(){
		return number;
	}
	
	public String getAddress(){
		return address;
	}
	
	//Set methods
	public void updateName(String newName){
		name = newName;
	}
	
	public void updateNumber(int newNum){
		number = newNum;
	}
	
	public void updateAddress(String newAdd){
		address = newAdd;
	}
}
