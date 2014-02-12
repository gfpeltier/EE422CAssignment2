/**
 * @author Grant Peltier & John Nelson
 * EID:gfp237,jkn387
 * EE422C-Assignment 2
 */

package assignment2;


/**
 * Customer class gives template for storing customer data. Meant to keep track of necessary
 * customer information as well as provide functions to access that data.
 * 
 * @author Grant Peltier & John Nelson
 *
 */
public class Customer {
	public final static int NUMACCTS = 4; 	// Number of accounts every Customer owns
	
	protected String name;
	protected int number;
	protected String address;
	protected BankAccount[] accounts;		// Holds account references for Customer

	/**
	 * Default constructor
	 */
	public Customer(){
		name = new String();
		number = -1;
		address = new String();
		accounts = new BankAccount[NUMACCTS];
	}
	
	/**
	 * Constructor with all fields provided
	 * @param person
	 * @param id
	 * @param add
	 * @param accts
	 */
	public Customer(String person, int id, String add, BankAccount[] accts){
		name = person;
		number = id;
		address = add;
		accounts = accts;
	}
	
	//Get methods
	/**
	 * Gets name of customer
	 * @return customer name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Gets customer number
	 * @return customer's unique ID number
	 */
	public int getNumber(){
		return number;
	}
	
	/**
	 * Gets customer address
	 * @return customer's address
	 */
	public String getAddress(){
		return address;
	}
	
	//Set methods
	/**
	 * Change the name of the customer
	 * @param newName
	 */
	public void updateName(String newName){
		name = newName;
	}
	
	/**
	 * Change customer ID number
	 * @param newNum
	 */
	public void updateNumber(int newNum){
		number = newNum;
	}
	
	/**
	 * Change customer address
	 * @param newAdd
	 */
	public void updateAddress(String newAdd){
		address = newAdd;
	}
	
	/**
	 * Change customer to new suite of bank accounts (Should only be done in the 
	 * case of an error where old accounts needed to be destroyed)
	 * @param accts
	 */
	public void updateAccounts(BankAccount[] accts){
		accounts = accts;
	}
	
	/**
	 * Based on the give account type (C, S, A, L), return the desired account to be manipulated. 
	 * @param type
	 * @return BankAccount of the desired type that belongs to the customer
	 */
	public BankAccount locateAccount(String type){
		for(int k = 0; k < NUMACCTS; k++){
			if((accounts[k].getType()).equals(type)){
				return accounts[k];
			}
		}
		return null;
	}
}
