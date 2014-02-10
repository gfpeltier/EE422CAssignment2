package assignment2;

import javax.swing.JOptionPane;



public class Driver {
	
	public final static int NUMCUST = 2;
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Customer[] custDB = new Customer[NUMCUST];
		customerInit(custDB);											//Issue within customerInit() method. java.lang.NullPointerException
		String input = new String(receiveTransaction());
		
	}
	
	public static String receiveTransaction(){
		String output = new String();
		String custNum = new String(JOptionPane.showInputDialog("Input Customer Number"));
		int check = Integer.parseInt(custNum);
		if(check < 0 || check > NUMCUST){
			JOptionPane.showMessageDialog(null, "Invalid Customer Number","Error",JOptionPane.ERROR_MESSAGE);
			output = receiveTransaction();
			return output;
		}
		String trans = new String(JOptionPane.showInputDialog("Input Transaction Type\n(uppercase only please)"));
		output += custNum + " " + trans;
		if(trans.indexOf("T") != -1){
			String amt = new String(JOptionPane.showInputDialog("Input Amount"));
			String from = new String(JOptionPane.showInputDialog("Input Account to Transfer From (C,S,A,L)"));
			String to = new String(JOptionPane.showInputDialog("Input Account to Transfer To (C,S,A,L)"));
			output += " " + amt + " " + from + " " + to;
		}else if(trans.indexOf("W") != -1 || trans.indexOf("D") != -1){
			String amt = new String(JOptionPane.showInputDialog("Input Amount"));
			String acc = new String(JOptionPane.showInputDialog("Input Account (C,S,A,L)"));
			output += " " +  amt + " " + acc;
		}else if(trans.indexOf("I") != -1 || trans.indexOf("G") != -1){
			String acc = new String(JOptionPane.showInputDialog("Input Account (C,S,A,L)"));
			output += " " +  acc;
		}else{
			JOptionPane.showMessageDialog(null, "Invalid Transaction Type","Error",JOptionPane.ERROR_MESSAGE);
			output = receiveTransaction();
			}
		return output;
	}
	
	public static int getAccount(Customer person, char accType){
		int acc = -1;
		return acc;
	}
	
	public static void executeTransaction(){
		
	}
	
	public static void customerInit(Customer[] custDB){
		BankAccount[] custAccounts1 = new BankAccount[4];
		BankAccount[] custAccounts2 = new BankAccount[4];
		custDB[0] = new Customer("Grant Peltier", 1, "3108 Glenmere Court", null);
		custDB[1] = new Customer("John Nelson", 2, "1234 That Street", null);
		custAccounts1[0] = new BankAccount(1, custDB[0], 0.00, 'C');
		custAccounts1[1] = new BankAccount(2, custDB[0], 0.00, 'S');
		custAccounts1[2] = new BankAccount(3, custDB[0], 0.00, 'L');
		custAccounts1[3] = new BankAccount(4, custDB[0], 0.00, 'A');
		custAccounts2[0] = new BankAccount(5, custDB[1], 0.00, 'C');
		custAccounts2[1] = new BankAccount(6, custDB[1], 0.00, 'S');
		custAccounts2[2] = new BankAccount(7, custDB[1], 0.00, 'L');
		custAccounts2[3] = new BankAccount(8, custDB[1], 0.00, 'A');
		custDB[0].updateAccounts(custAccounts1);
		custDB[1].updateAccounts(custAccounts2);
	}

}
