package assignment2;

/**
 * Model for general bank account object.  The purpose is to record money,
 * and allow for various financial transactions to be performed over the
 * life of a specific bank account.
 * 
 * @author ee322c teaching team
 */
class BankAccount 
{   
// instance variables (protected to allow inheriting them)
	/**
	 * A unique number that identifies the account
	 */
	protected int accountNumber;
	
	/**
	 * The name of the person that this account belongs to
	 */
    protected Customer ownersName;
    
    /**
     * the current value (in dollars) of the money in this account
     */
    protected double balance;
    
    /**
     * Contains character code for type of account [checking(C), savings(S), auto(A), student(L)]
     */
    
    protected char accountType;

    
//constructors
    public BankAccount(){
    	balance = 0;
    	accountType = 0;
    }
    /**
     * Create an account with an initial balance.
     * @param initialBalance     The initial balance of the account
     */
    public BankAccount(double initialBalance, char type)
    {
        balance = initialBalance;
        accountType = type;
    }
    
    /**
     * Create an account with initial parameters.
     * @param acct               The account number
     * @param owner              The owner of the account
     * @param initBalance        The initial balance of the account
     */
    public BankAccount(int acct, Customer owner, double initBalance, char type)
    {
        accountNumber = acct;
        ownersName = owner;
        balance = initBalance;
        accountType = type;
    }
 
    
// balance changing methods
    /**
     * Updates the current balance by adding in a given amount.
     * Post condition: the new balance is increased by the amount.
     * @param amount                The amount to add
     */
    public void deposit(double amount) 
    {  
       balance = balance + amount; 
    }
    
    /**
     * Update the current balance by subtracting the given amount.
     * Precondition: the current balance must have at least the amount in it.
     * Postcondition: the new balance is decreased by the given amount.
     * @param amount  The amout to subtract
     */
    public void withdraw(double amount) 
    {  
        if (balance >=  amount)
            balance = balance - amount; 
    }

    
// get and set methods
    /**
     * @return The available balance.
     */
    public double getBalance( )
    {
        return balance;
    }
    
    /**
     * @return The account number.
     */
    public int getAccountNumber( )
    {
        return accountNumber;
    }
    
    /**
     * @return The owner's name.
     */
    public Customer getOwner( )
    {
        return ownersName;
    }

    
// set: postconditions- these all are used to set new values for the instance variables
    /**
     * Set the balance.
     * @param newBalance  The new balance.
     */
    public void setBalance(double newBalance )
    {
        balance = newBalance;
    }
    
    /**
     * Set the acount number.
     * @param newAcctNumber The new account number.
     */
    public void setAccountNumber(int newAcctNumber )
    {
        accountNumber = newAcctNumber;
    }
    
    /**
     * Set the new owner of the account.
     * @param newOwner
     */
    public void setOwner(Customer newOwner )
    {
        ownersName = newOwner;
    }
}
