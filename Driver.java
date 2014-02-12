/**
 * @author Grant Peltier & John Nelson
 * EID:gfp237,jkn387
 * EE422C-Assignment 2
 */

package assignment2;

import java.text.DecimalFormat;

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
		JOptionPane.showMessageDialog(null, output, "Transaction Summary" ,JOptionPane.PLAIN_MESSAGE);		// Display message from completed transaction
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
			return inputCustomerError(data);
		}
		if(custNum.charAt(0) < '1' || custNum.charAt(0) > '9'){
			return inputCustomerError(data);
		}
		int check = Integer.parseInt(custNum);
		if(check <= 0 || check > NUMCUST){				// Check that customer number is valid
			return inputCustomerError(data);
		}
		Customer currentCust = data[check-1];			// Locate customer data
		String trans = new String(JOptionPane.showInputDialog("Input Transaction Type\n(uppercase only please)"));
		if(trans.indexOf("T") != -1 && trans.length() == 1){									// Transfer case
			String amt = new String(JOptionPane.showInputDialog("Input Amount"));
			if(Double.parseDouble(amt) < 0){return inputAmountError(data);}
			String from = new String(JOptionPane.showInputDialog("Input Account to Transfer From (C,S,A,L)"));
			if(from.charAt(0) != 'C' && from.charAt(0) != 'S' && from.charAt(0) != 'A' && from.charAt(0) != 'L'){return inputAcctTypeError(data);}
			String to = new String(JOptionPane.showInputDialog("Input Account to Transfer To (C,S,A,L)"));
			if(to.charAt(0) != 'C' && to.charAt(0) != 'S' && to.charAt(0) != 'A' && to.charAt(0) != 'L'){return inputAcctTypeError(data);}
			return transfer(currentCust, amt, from, to);
		}else if(trans.indexOf("W") != -1 && trans.length() == 1){								// Withdraw case
			String amt = new String(JOptionPane.showInputDialog("Input Amount"));
			if(Double.parseDouble(amt) < 0){return inputAmountError(data);}
			String acct = new String(JOptionPane.showInputDialog("Input Account (C,S,A,L)"));
			if(acct.charAt(0) != 'C' && acct.charAt(0) != 'S' && acct.charAt(0) != 'A' && acct.charAt(0) != 'L'){return inputAcctTypeError(data);}
			return withdraw(currentCust, amt, acct);
		}else if(trans.indexOf("D") != -1 && trans.length() == 1){								// Deposit case
			String amt = new String(JOptionPane.showInputDialog("Input Amount"));
			if(Double.parseDouble(amt) < 0){return inputAmountError(data);}
			String acct = new String(JOptionPane.showInputDialog("Input Account (C,S,A,L)"));
			if(acct.charAt(0) != 'C' && acct.charAt(0) != 'S' && acct.charAt(0) != 'A' && acct.charAt(0) != 'L'){return inputAcctTypeError(data);}
			return deposit(currentCust, amt, acct);
		}else if(trans.indexOf("I") != -1 && trans.length() == 1){								// Interest case
			String acct = new String(JOptionPane.showInputDialog("Input Account (C,S,A,L)"));
			if(acct.charAt(0) != 'C' && acct.charAt(0) != 'S' && acct.charAt(0) != 'A' && acct.charAt(0) != 'L'){return inputAcctTypeError(data);}
			return interest(currentCust, acct);
		}else if(trans.indexOf("G") != -1 && trans.length() == 1){								// Summary Case
			String acct = new String(JOptionPane.showInputDialog("Input Account (C,S,A,L)"));
			if(acct.charAt(0) != 'C' && acct.charAt(0) != 'S' && acct.charAt(0) != 'A' && acct.charAt(0) != 'L'){return inputAcctTypeError(data);}
			return summarize(currentCust, acct);
		}else{															// Handle invalid transaction type
			JOptionPane.showMessageDialog(null, "Invalid Transaction Type","Error",JOptionPane.ERROR_MESSAGE);
			output = receiveTransaction(data);
			}
		return output;
	}
	
	/**
	 * Handles error when input of account type is not S, C, A, or L
	 * @param data
	 * @return output string from appropriate transaction
	 */
	public static String inputAcctTypeError(Customer[] data){
		JOptionPane.showMessageDialog(null, "Invalid Account Type","Error",JOptionPane.ERROR_MESSAGE);
		return receiveTransaction(data);
	}
	
	/**
	 * Handles error when an inappropriate customer number input is detected
	 * @param data
	 * @return output string from appropriate transaction
	 */
	public static String inputCustomerError(Customer[] data){
		JOptionPane.showMessageDialog(null, "Invalid Customer Number","Error",JOptionPane.ERROR_MESSAGE);
		return receiveTransaction(data);
	}
	
	/**
	 * Handles error when a negative dollar amount input is detected
	 * @param data
	 * @return output string from appropriate transaction
	 */
	public static String inputAmountError(Customer[] data){
		JOptionPane.showMessageDialog(null, "Invalid Amount. Must Be Greater Than or Equal To 0","Error",JOptionPane.ERROR_MESSAGE);
		return receiveTransaction(data);
	}

	/**
	 * This method is to be called right before the program terminates. It 
	 * gathers data from all customers ands then displays it in a dialog box.
	 * @param db
	 */
	public static void displaySummaries(Customer[] db){
			DecimalFormat df = new DecimalFormat("0.00");
			JOptionPane.showMessageDialog(null, "Customer: " + db[0].getName() + "\n"+
												"Address: " + db[0].getAddress() + "\n"+
												"ID Number: " + db[0].getNumber() + "\n"+
												"Checking: $" + df.format(db[0].locateAccount("C").getBalance()) + "\n"+
												"Savings: $" + df.format(db[0].locateAccount("S").getBalance()) + "\n"+
												"Auto: $" + df.format(db[0].locateAccount("A").getBalance()) + "\n"+
												"Loan: $" + df.format(db[0].locateAccount("L").getBalance()) + "\n\n\n"+
												"Customer: " + db[1].getName() + "\n"+
												"Address: " + db[1].getAddress() + "\n"+
												"ID Number: " + db[1].getNumber() + "\n"+
												"Checking: $" + df.format(db[1].locateAccount("C").getBalance()) + "\n"+
												"Savings: $" + df.format(db[1].locateAccount("S").getBalance()) + "\n"+
												"Auto: $" + df.format(db[1].locateAccount("A").getBalance()) + "\n"+
												"Loan: $" + df.format(db[1].locateAccount("L").getBalance()) + "\n", "Customer Summaries", JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Locates and returns the BankAccount of a particular type (C, S, A, L) for the
	 * customer given.
	 * @param person
	 * @param accType
	 * @return Proper BankAccount based on person and type
	 */
	public static BankAccount getAccount(Customer person, String accType){
		return person.locateAccount(accType);
	}

	/**
	 * Completes "transfer" transaction and generates proper output string
	 * @param person
	 * @param amt
	 * @param from
	 * @param to
	 * @return "transfer" output string
	 */
	public static String transfer(Customer person, String amt, String from, String to){
		DecimalFormat df = new DecimalFormat("0.00");
		String output = new String();
		BankAccount fromAccount = person.locateAccount(from);
		BankAccount toAccount = person.locateAccount(to);
		String fromType = null;
		String toType = null;
		if(fromAccount.getType() == "S"){fromType = "PRIMARY SAVINGS";}
		if(fromAccount.getType() == "C"){fromType = "CHECKING";}
		if(fromAccount.getType() == "A"){fromType = "AUTO LOAN SAVINGS";}
		if(fromAccount.getType() == "L"){fromType = "STUDENT LOAN SAVINGS";}
		if(toAccount.getType() == "S"){toType = "PRIMARY SAVINGS";}
		if(toAccount.getType() == "C"){toType = "CHECKING";}
		if(toAccount.getType() == "A"){toType = "AUTO LOAN SAVINGS";}
		if(toAccount.getType() == "L"){toType = "STUDENT LOAN SAVINGS";}
		double Amount = Double.parseDouble(amt);
		if(person.locateAccount(from).getBalance() < Amount){//case where "from" account has insufficient funds
			output = "ERROR: " + person.getName() + "'s " + fromType + " account has insufficient funds to complete the transfer of $"
					 + df.format(Amount) + " to their " + toType + " account." ;
			return output;
		}
		else{//case where transfer is successful
			double fromInitial = person.locateAccount(from).getBalance();
			double toInitial = person.locateAccount(to).getBalance();
			person.locateAccount(from).withdraw(Amount);
			person.locateAccount(to).deposit(Amount);
			output = "Transfer from " + person.getName() + "'s " + fromType + " to their " + toType + " account:" +
					 "\n\nInitial " + fromType + " Balance: $" + df.format(fromInitial) + 
					 "\nInitial " + toType + " Balance: $" + df.format(toInitial) +
					 "\n\n$" + df.format(Amount) + " transferred from " + person.getName() + "'s " + fromType + " to their " + toType +
					 "\n\nFinal " + fromType + " Balance: $" + df.format(person.locateAccount(from).getBalance()) +
					 "\nFinal " + toType + " Balance: $" + df.format(person.locateAccount(to).getBalance());
		}
		return output;
	}

	/**
	 * Completes "deposit" transaction and generates proper output string
	 * @param person
	 * @param amt
	 * @param acct
	 * @return "deposit" output string
	 */
	public static String deposit(Customer person, String amt, String acct){
		DecimalFormat df = new DecimalFormat("0.00");
		String output = new String();
		double Initial = person.locateAccount(acct).getBalance();
		double Amount = Double.parseDouble(amt);
		person.locateAccount(acct).deposit(Amount);
		String Type = "";
		BankAccount Account = person.locateAccount(acct);
		if(Account.getType() == "S"){Type = "PRIMARY SAVINGS";}
		if(Account.getType() == "C"){Type = "CHECKING";}
		if(Account.getType() == "A"){Type = "AUTO LOAN SAVINGS";}
		if(Account.getType() == "L"){Type = "STUDENT LOAN SAVINGS";}
		output = "Deposit to " + person.getName() + "'s " + Type + " account:\n" + "Balance before adding deposit: $"
				 + df.format(Initial) + "\nAmount Deposited: $" + df.format(Amount) + "\nCurrent balance after deposit: $" + df.format(person.locateAccount(acct).getBalance());
		return output;
	}

	/**
	 * Completes "withdraw" transaction and generates proper output string
	 * @param person
	 * @param amt
	 * @param acct
	 * @return "withdraw" output string
	 */
	public static String withdraw(Customer person, String amt, String acct){
		DecimalFormat df = new DecimalFormat("0.00");
		String output = new String();
		BankAccount Account = person.locateAccount(acct);
		String Type = null;
		double amount = Double.parseDouble(amt);
		if(Account.getType() == "C"){
			Type = "CHECKING";
			if(amount > person.locateAccount(acct).getBalance()){//case for savings account covering checking
				if(person.locateAccount("S").getBalance() >= (amount - person.locateAccount(acct).getBalance() + 20)){
					double initialChecking = person.locateAccount("C").getBalance();
					double initialSavings = person.locateAccount("S").getBalance();
					double overflow = Double.parseDouble(amt) + 20 - person.locateAccount("C").getBalance();
					person.locateAccount("C").setBalance(0);
					person.locateAccount("S").withdraw(overflow);
					output = person.getName() + "'s CHECKING account has inssuficient funds to complete the whole withdrawal.\nThe remaining amount was withdrawn from their primary savings account."
							 + "\nInitial CHECKING account balance: $" + df.format(initialChecking) + "\nFinal CHECKING account balance: $" + df.format(person.locateAccount("C").getBalance()) + "\nInitial PRIMARY SAVINGS account balance: $" +
							 df.format(initialSavings) + "\nFinal PRIMARY SAVINGS account balance: $" + df.format(person.locateAccount("S").getBalance()) + "\n\nAmount Withdrawn: $" + df.format(amount);
					return output;
				}
				else{//case where checking AND savings have insufficient funds
					output = "ERROR: " + person.getName() + "'s PRIMARY SAVINGS account has insufficient funds to cover the overdraw of their CHECKING account.";
					return output;
				}
			}
		}
		if(Account.getType() == "S"){Type = "PRIMARY SAVINGS";}
		if(Account.getType() == "A"){Type = "AUTO LOAN SAVINGS";}
		if(Account.getType() == "L"){Type = "STUDENT LOAN SAVINGS";}
		if(Double.parseDouble(amt) > person.locateAccount(acct).getBalance()){//case where funds are insufficient
			output = "Error, " + person.getName() + "'s " + Type + " account has insufficient funds to complete the withdrawal.";
			return output;
		}
		else{//successful withdrawal
			double Initial = person.locateAccount(acct).getBalance();
			person.locateAccount(acct).withdraw(Double.parseDouble(amt));
			double Final = person.locateAccount(acct).getBalance();
			output = "Withdrawal from:" + person.getName() + "'s " + Type + " account:\n" + "Initial Balance: $"
			+ df.format(Initial) + "\nAmount Withdrawn: $" + df.format(amount) + "\nFinal Balance: $" + df.format(Final);
		}
		return output;
	}

	/**
	 * Completes "interest" transaction and generates proper output string
	 * @param person
	 * @param acct
	 * @return "interest" output string
	 */
	public static String interest(Customer person, String acct){
		DecimalFormat df = new DecimalFormat("0.00");
		String output = new String();
		if(person.locateAccount(acct).getType() != "S"){
			output = "ERROR: Interest only applies to PRIMARY SAVINGS accounts.";
			return output;
		}
		if(person.locateAccount(acct).getBalance() < 1000.00){
			output = "ERROR: Interest only applies to PRIMARY SAVINGS accounts with balances greater than $1000.00.";
			return output;
		}
		double Initial = person.locateAccount(acct).getBalance();
		double Interest = person.locateAccount(acct).getBalance() * .04;
		person.locateAccount(acct).deposit(Interest);
		output = "Interest computed and added for: " + person.getName() + "'s PRIMARY SAVINGS account\n" + "Balance before computing interest adding interest: $"
				 + df.format(Initial) + "\nInterest added: $" + df.format(Interest) + "\nCurrent balance after computing and adding interest: $" + df.format(person.locateAccount(acct).getBalance());
		return output;
	}

	/**
	 * Completes "summarize" transaction and generates proper output string
	 * @param person
	 * @param acct
	 * @return "summarize" output string
	 */
	public static String summarize(Customer person, String acct){
		DecimalFormat df = new DecimalFormat("0.00");
		String output = new String();
		BankAccount Account = person.locateAccount(acct);
		String Type = null;
		if(Account.getType() == "S"){Type = "PRIMARY SAVINGS";}
		if(Account.getType() == "C"){Type = "CHECKING";}
		if(Account.getType() == "A"){Type = "AUTON LOAN SAVINGS";}
		if(Account.getType() == "L"){Type = "STUDENT LOAN SAVINGS";}
		output = "The balance of " + person.getName() + "'s " + Type + " account is $" + df.format(Account.getBalance()) + ".";
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
		custDB[0] = new Customer("Grant Peltier", 1, "2515 Pearl Street", null);
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
