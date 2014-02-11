package assignment2;

import javax.swing.JOptionPane;



public class Driver {
	
	public final static int NUMCUST = 2;		// Number of customers in database
	public final static int TRANSTYPE = 2;		// Offset in input string for type of transaction (T, W, D, I, G)
	public final static int CUSTOFF = 0;		// Offset for customer ID number in input string

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean isDone = false;
		Customer[] custDB = new Customer[NUMCUST];
		customerInit(custDB);											
		while(isDone == false){
		String output = new String(receiveTransaction(custDB));
		JOptionPane.showMessageDialog(null, output);
		String keepGoing = JOptionPane.showInputDialog("Would you like to do another transaction? (Y/N)");
		if(keepGoing.indexOf("N") != -1 || keepGoing.indexOf("n") != -1){
			isDone = true;
			}
		}
		displaySummaries(custDB);
	}
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	
	public static String receiveTransaction(Customer[] data){
		String output = new String();
		String custNum = new String(JOptionPane.showInputDialog("Input Customer Number"));
		int check = Integer.parseInt(custNum);
		if(check < 0 || check > NUMCUST){
			JOptionPane.showMessageDialog(null, "Invalid Customer Number","Error",JOptionPane.ERROR_MESSAGE);
			output = receiveTransaction(data);
			return output;
		}
		Customer currentCust = data[check-1];
		String trans = new String(JOptionPane.showInputDialog("Input Transaction Type\n(uppercase only please)"));
		if(trans.indexOf("T") != -1){
			String amt = new String(JOptionPane.showInputDialog("Input Amount"));
			String from = new String(JOptionPane.showInputDialog("Input Account to Transfer From (C,S,A,L)"));
			String to = new String(JOptionPane.showInputDialog("Input Account to Transfer To (C,S,A,L)"));
			return transfer(currentCust, amt, from, to);
		}else if(trans.indexOf("W") != -1){
			String amt = new String(JOptionPane.showInputDialog("Input Amount"));
			String acct = new String(JOptionPane.showInputDialog("Input Account (C,S,A,L)"));
			return withdraw(currentCust, amt, acct);
		}else if(trans.indexOf("D") != -1){
			String amt = new String(JOptionPane.showInputDialog("Input Amount"));
			String acct = new String(JOptionPane.showInputDialog("Input Account (C,S,A,L)"));
			return deposit(currentCust, amt, acct);
		}else if(trans.indexOf("I") != -1){
			String acct = new String(JOptionPane.showInputDialog("Input Account (C,S,A,L)"));
			return interest(currentCust, acct);
		}else if(trans.indexOf("G") != -1){
			String acct = new String(JOptionPane.showInputDialog("Input Account (C,S,A,L)"));
			return summarize(currentCust, acct);
		}else{
			JOptionPane.showMessageDialog(null, "Invalid Transaction Type","Error",JOptionPane.ERROR_MESSAGE);
			output = receiveTransaction(data);
			}
		return output;
	}
	
	/**
	 * 
	 */
	public static void displaySummaries(Customer[] db){
			String person1 = new String(db[0].getName());
			int custNum1 = db[0].getNumber();
			double cBalance1 = db[0].locateAccount("C").getBalance();
			double sBalance1 = db[0].locateAccount("S").getBalance();
			double aBalance1 = db[0].locateAccount("A").getBalance();
			double lBalance1 = db[0].locateAccount("L").getBalance();
			String person2 = new String(db[1].getName());
			int custNum2 = db[1].getNumber();
			double cBalance2 = db[1].locateAccount("C").getBalance();
			double sBalance2 = db[1].locateAccount("S").getBalance();
			double aBalance2 = db[1].locateAccount("A").getBalance();
			double lBalance2 = db[1].locateAccount("L").getBalance();
			JOptionPane.showMessageDialog(null, "Customer: " + person1 + "\n"+
												"ID Number: " + custNum1 + "\n"+
												"Checking: " + cBalance1 + "\n"+
												"Savings: " + sBalance1 + "\n"+
												"Auto: " + aBalance1 + "\n"+
												"Loan: " + lBalance1 + "\n\n\n"+
												"Customer: " + person2 + "\n"+
												"ID Number: " + custNum2 + "\n"+
												"Checking: " + cBalance2 + "\n"+
												"Savings: " + sBalance2 + "\n"+
												"Auto: " + aBalance2 + "\n"+
												"Loan: " + lBalance2 + "\n\n\n", "Customer Summaries", JOptionPane.PLAIN_MESSAGE);
	}
	
	/**
	 * 
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
		return output;
	}
	
	public static String deposit(Customer person, String amt, String acct){
		String output = new String();
		return output;
	}
	
	public static String withdraw(Customer person, String amt, String acct){
		String output = new String();
		return output;
	}
	
	public static String interest(Customer person, String acct){
		String output = new String();
		return output;
	}
	
	public static String summarize(Customer person, String acct){
		String output = new String();
		return output;
	}
	
	/**
	 * 
	 * @param custDB
	 */
	public static void customerInit(Customer[] custDB){
		BankAccount[] custAccounts1 = new BankAccount[4];
		BankAccount[] custAccounts2 = new BankAccount[4];
		custDB[0] = new Customer("Grant Peltier", 1, "3108 Glenmere Court", null);
		custDB[1] = new Customer("John Nelson", 2, "1234 That Street", null);
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
