package assignment2;

import javax.swing.JOptionPane;


/**
 * Driver class intended to implement Customer and BankAccount classes. 
 * Handles any actual transactions and errors.
 * 
 * @author Grant Peltier & John Nelson
 *
 */
public class Driver {

	public final static int NUMCUST = 2;		// Number of customers in database
	public final static int TRANSTYPE = 2;		// Offset in input string for type of transaction (T, W, D, I, G)
	public final static int CUSTOFF = 0;		// Offset for customer ID number in input string

	/**
	 * Main method for the entire program. Sets a boolean check and iterates through loop
	 * of completing transactions until boolean is set to true.
	 * @param args
	 */
	public static void main(String[] args) {
		boolean isDone = false;
		Customer[] custDB = new Customer[NUMCUST];
		customerInit(custDB);											
		while(isDone == false){					// Boolean check to control main loop iterations
		String output = new String(receiveTransaction(custDB));
		JOptionPane.showMessageDialog(null, output);		// Display message from completed transaction
		String keepGoing = JOptionPane.showInputDialog("Would you like to do another transaction? (Y/N)");
		if(keepGoing.indexOf("N") != -1 || keepGoing.indexOf("n") != -1){
			isDone = true;						// If no more transactions, change boolean to true and exit
			}
		}
		displaySummaries(custDB);
	}

	/**
	 * Primary data gathering method of program. Gathers transaction input in separate chunks
	 * and passes along vital data to respective transaction methods. Also checks and 
	 * handles errors with transaction codes and invalid customer numbers.
	 * @param data
	 * @return message to be put out by the transaction that was completed
	 */

	public static String receiveTransaction(Customer[] data){
		String output = new String();
		String custNum = new String(JOptionPane.showInputDialog("Input Customer Number"));
		if(custNum.isEmpty()){
			JOptionPane.showMessageDialog(null, "Invalid Customer Number","Error",JOptionPane.ERROR_MESSAGE);
			output = receiveTransaction(data);
			return output;
		}
		if(custNum.charAt(0) < '1' || custNum.charAt(0) > '9'){
			JOptionPane.showMessageDialog(null, "Invalid Customer Number","Error",JOptionPane.ERROR_MESSAGE);
			output = receiveTransaction(data);
			return output;
		}
		int check = Integer.parseInt(custNum);
		if(check <= 0 || check > NUMCUST){				// Check that customer number is valid
			JOptionPane.showMessageDialog(null, "Invalid Customer Number","Error",JOptionPane.ERROR_MESSAGE);
			output = receiveTransaction(data);
			return output;
		}
		Customer currentCust = data[check-1];			// Locate customer data
		String trans = new String(JOptionPane.showInputDialog("Input Transaction Type\n(uppercase only please)"));
		if(trans.indexOf("T") != -1 && trans.length() == 1){									// Transfer case
			String amt = new String(JOptionPane.showInputDialog("Input Amount"));
			String from = new String(JOptionPane.showInputDialog("Input Account to Transfer From (C,S,A,L)"));
			String to = new String(JOptionPane.showInputDialog("Input Account to Transfer To (C,S,A,L)"));
			return transfer(currentCust, amt, from, to);
		}else if(trans.indexOf("W") != -1 && trans.length() == 1){								// Withdraw case
			String amt = new String(JOptionPane.showInputDialog("Input Amount"));
			String acct = new String(JOptionPane.showInputDialog("Input Account (C,S,A,L)"));
			return withdraw(currentCust, amt, acct);
		}else if(trans.indexOf("D") != -1 && trans.length() == 1){								// Deposit case
			String amt = new String(JOptionPane.showInputDialog("Input Amount"));
			String acct = new String(JOptionPane.showInputDialog("Input Account (C,S,A,L)"));
			return deposit(currentCust, amt, acct);
		}else if(trans.indexOf("I") != -1 && trans.length() == 1){								// Interest case
			String acct = new String(JOptionPane.showInputDialog("Input Account (C,S,A,L)"));
			return interest(currentCust, acct);
		}else if(trans.indexOf("G") != -1 && trans.length() == 1){								// Summary Case
			String acct = new String(JOptionPane.showInputDialog("Input Account (C,S,A,L)"));
			return summarize(currentCust, acct);
		}else{															// Handle invalid transaction type
			JOptionPane.showMessageDialog(null, "Invalid Transaction Type","Error",JOptionPane.ERROR_MESSAGE);
			output = receiveTransaction(data);
			}
		return output;
	}

	/**
	 * This method is to be called right before the program terminates. It 
	 * gathers data from all customers ands then displays it in a dialog box.
	 * @param db
	 */
	public static void displaySummaries(Customer[] db){
			JOptionPane.showMessageDialog(null, "Customer: " + db[0].getName() + "\n"+
												"Address: " + db[0].getAddress() + "\n"+
												"ID Number: " + db[0].getNumber() + "\n"+
												"Checking: " + db[0].locateAccount("C").getBalance() + "\n"+
												"Savings: " + db[0].locateAccount("S").getBalance() + "\n"+
												"Auto: " + db[0].locateAccount("A").getBalance() + "\n"+
												"Loan: " + db[0].locateAccount("L").getBalance() + "\n\n\n"+
												"Customer: " + db[1].getName() + "\n"+
												"Address: " + db[1].getAddress() + "\n"+
												"ID Number: " + db[1].getNumber() + "\n"+
												"Checking: " + db[1].locateAccount("C").getBalance() + "\n"+
												"Savings: " + db[1].locateAccount("S").getBalance() + "\n"+
												"Auto: " + db[1].locateAccount("A").getBalance() + "\n"+
												"Loan: " + db[1].locateAccount("L").getBalance() + "\n", "Customer Summaries", JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Locates and returns the BankAccount of a particular type (C, S, A, L) for the
	 * customer given.
	 * @param person
	 * @param accType
	 * @return
	 */
	public static BankAccount getAccount(Customer person, String accType){
		return person.locateAccount(accType);
	}

	//TODO: independent methods for every transaction
	public static String transfer(Customer person, String amt, String from, String to){
		String output = new String();
		BankAccount fromAccount = person.locateAccount(from);
		BankAccount toAccount = person.locateAccount(from);
		String fromType = null;
		String toType = null;
		if(fromAccount.getType() == "S"){fromType = "primary savings";}
		if(fromAccount.getType() == "C"){fromType = "checking";}
		if(fromAccount.getType() == "A"){fromType = "auto loan savings";}
		if(fromAccount.getType() == "L"){fromType = "student loan savings";}
		if(toAccount.getType() == "S"){toType = "primary savings";}
		if(toAccount.getType() == "C"){toType = "checking";}
		if(toAccount.getType() == "A"){toType = "auto loan savings";}
		if(toAccount.getType() == "L"){toType = "student loan savings";}
		double Amount = Double.parseDouble(amt);
		if(person.locateAccount(from).getBalance() < Amount){//case where "from" account has insufficient funds
			output = "ERROR: " + person.getName() + "'s" + fromType + " account has insufficient funds to complete the transfer of "
					 + amt + " to their" + toType + " account." ;
			return output;
		}
		else{//case where transfer is successful
			double fromInitial = person.locateAccount(from).getBalance();
			double toInitial = person.locateAccount(to).getBalance();
			person.locateAccount(from).withdraw(Amount);
			person.locateAccount(to).deposit(Amount);
			output = "Transfer from" + person.getName() + "'s " + fromType + " to their " + toType + "account:" +
					 "\n\ninitial " + fromType + " balance: " + fromInitial + 
					 "\ninitial " + toType + "balance: " + toInitial +
					 "\n\n" + Amount + " transferred from " + person.getName() + "'s " + fromType + " to their " + toType +
					 "\n\nfinal " + fromType + " balance: " + person.locateAccount(from).getBalance() +
					 "\nfinal " + toType + " balance: " + person.locateAccount(to).getBalance();
		}
		return output;
	}

	public static String deposit(Customer person, String amt, String acct){
		String output = new String();
		double Initial = person.locateAccount(acct).getBalance();
		double Amount = Double.parseDouble(amt);
		person.locateAccount(acct).deposit(Amount);
		String Type = "";
		BankAccount Account = person.locateAccount(acct);
		if(Account.getType() == "S"){Type = "primary savings";}
		if(Account.getType() == "C"){Type = "checking";}
		if(Account.getType() == "A"){Type = "auto loan savings";}
		if(Account.getType() == "L"){Type = "student loan savings";}
		output = "Deposit to " + person.getName() + "'s " + Type + " account:\n" + "balance before adding deposit: "
				 + Initial + "\namount deposited: " + amt + "\ncurrent balance after deposit:" + person.locateAccount(acct).getBalance();
		return output;
	}

	public static String withdraw(Customer person, String amt, String acct){
		String output = new String();
		BankAccount Account = person.locateAccount(acct);
		String Type = null;
		if(Account.getType() == "C"){
			Type = "checking";
			if(Double.parseDouble(amt) > person.locateAccount(acct).getBalance()){//case for savings account covering checking
				if(person.locateAccount("S").getBalance() + person.locateAccount(acct).getBalance() + 20 > Double.parseDouble(amt)){
					double initialChecking = person.locateAccount("C").getBalance();
					double initialSavings = person.locateAccount("S").getBalance();
					double overflow = Double.parseDouble(amt) + 20 - person.locateAccount("C").getBalance();
					person.locateAccount("C").setBalance(0);
					person.locateAccount("S").withdraw(overflow);
					output = person.getName() + "'s checking account has inssuficient funds to complete the whole withdrawal.\nThe remainding amount was withdrawn from their primary savings account."
							 + "\ninitial checking account balance: " + initialChecking + "\nfinal checking account balance: " + person.locateAccount("C").getBalance() + "\ninitial primary savings account balance: " +
							 initialSavings + "\nfinal primary savings account balance: " + person.locateAccount("S").getBalance() + "\n\namount withdrawn: " + amt;
					return output;
				}
				else{//case where checking AND savings have insufficient funds
					output = "ERROR: " + person.getName() + "'s savings account has insufficient funds to cover the overdraw of their checking account.";
					return output;
				}
			}
		}
		if(Account.getType() == "S"){Type = "primary savings";}
		if(Account.getType() == "A"){Type = "auto loan savings";}
		if(Account.getType() == "L"){Type = "student loan savings";}
		if(Double.parseDouble(amt) > person.locateAccount(acct).getBalance()){//case where funds are insufficient
			output = "Error, " + person.getName() + "'s " + Type + " account has insufficient funds to complete the withdrawal.";
			return output;
		}
		else{//successful withdrawal
			double Initial = person.locateAccount(acct).getBalance();
			person.locateAccount(acct).withdraw(Double.parseDouble(amt));
			double Final = person.locateAccount(acct).getBalance();
			output = "Withdrawal from:" + person.getName() + "'s " + Type + " account:\n" + "initial amount: "
			+ Initial + "\namount withdrawn: " + amt + "\nfinal amount: " + Final;
		}
		return output;
	}

	public static String interest(Customer person, String acct){
		String output = new String();
		if(person.locateAccount(acct).getType() != "S"){
			output = "ERROR: Interest only applies to savings accounts.";
			return output;
		}
		if(person.locateAccount(acct).getBalance() < 1000.00){
			output = "ERROR: Interest only applies to savings accounts with balances greater than $1000.00.";
			return output;
		}
		double Initial = person.locateAccount(acct).getBalance();
		double Interest = person.locateAccount(acct).getBalance() * .04;
		person.locateAccount(acct).deposit(Interest);
		output = "Interest computed and added for: " + person.getName() + "'s savings account\n" + "balance before computing interest adding interest: "
				 + Initial + "\ninterest added: " + Interest + "\ncurrent balance after computing and adding interest: " + person.locateAccount(acct).getBalance();
		return output;
	}

	public static String summarize(Customer person, String acct){
		String output = new String();
		BankAccount Account = person.locateAccount(acct);
		String Type = null;
		if(Account.getType() == "S"){Type = "primary savings";}
		if(Account.getType() == "C"){Type = "checking";}
		if(Account.getType() == "A"){Type = "auto loan savings";}
		if(Account.getType() == "L"){Type = "student loan savings";}
		output = "The Balance of " + person.getName() + "'s " + Type + " account is " + Account.getBalance() + ".";
		return output;
	}

	/**
	 * Initializes the customer database with the 2 necessary entries. Initializes all
	 * vital data as well as each persons 4 accounts.
	 * @param custDB
	 */
	public static void customerInit(Customer[] custDB){
		BankAccount[] custAccounts1 = new BankAccount[4];
		BankAccount[] custAccounts2 = new BankAccount[4];
		custDB[0] = new Customer("Grant Peltier", 1, "3108 Glenmere Court", null);
		custDB[1] = new Customer("John Nelson", 2, "5315 Duval Street", null);
		custAccounts1[0] = new BankAccount(1, custDB[0], 0.00, "C");
		custAccounts1[1] = new BankAccount(2, custDB[0], 0.00, "S");
		custAccounts1[2] = new BankAccount(3, custDB[0], 0.00, "L");
		custAccounts1[3] = new BankAccount(4, custDB[0], 0.00, "A");
		custAccounts2[0] = new BankAccount(5, custDB[1], 0.00, "C");
		custAccounts2[1] = new BankAccount(6, custDB[1], 0.00, "S");
		custAccounts2[2] = new BankAccount(7, custDB[1], 0.00, "L");
		custAccounts2[3] = new BankAccount(8, custDB[1], 0.00, "A");
		custDB[0].updateAccounts(custAccounts1);
		custDB[1].updateAccounts(custAccounts2);
	}

}
