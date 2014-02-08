package assignment2;

import javax.swing.JOptionPane;

public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String input = new String(receiveTransaction());
		
	}
	
	public static String receiveTransaction(){
		String output = new String();
		String custNum = new String(JOptionPane.showInputDialog("Input Customer Number"));
		String trans = new String(JOptionPane.showInputDialog("Input Transaction Type"));
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
			JOptionPane.showMessageDialog(null, "Invalid Transaction Type");
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

}
