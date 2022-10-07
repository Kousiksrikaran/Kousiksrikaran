package ATM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class c {
@SuppressWarnings({ "resource", "unused" })
public static void main(String[] args) throws Exception{
		Scanner s= new Scanner(System.in);
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection c=DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment","root","root");
		Statement st= c.createStatement();
		ResultSet rs;
		int total=0;
		rs=st.executeQuery("Select value from atm");
		while(rs.next()) 
			total+=rs.getInt(1);
		System.out.println("1. Check Balance");
		System.out.println("2. Withdraw Money");
		System.out.println("3. Transfer Money");
		int choice=s.nextInt();
		int account_no=0,pin_no=0,account_balance=0,amount=0,accountno=0,withdraw_amount=0,h_no=0;
		String name="";
		boolean b=true;
		switch(choice) {
		case 1: 
			System.out.println("Enter your account number : ");
			accountno=s.nextInt();
			rs=st.executeQuery("Select count(*) from customers where Acc_No="+accountno);
			while(rs.next()) {
				if(rs.getInt(1)==0)
					b=false;
			}
			if(!b) System.out.println("Account number is INVALID!!!");
			else {
			rs=st.executeQuery("select * from customers where Acc_No="+accountno);
			while(rs.next()) {
				account_no=rs.getInt(1);
				name=rs.getString(2);
				pin_no=rs.getInt(3);
				account_balance=rs.getInt(4);
			}
			System.out.println("Enter your pin number : ");
			int pin=s.nextInt();
			if(pin==pin_no) {
				System.out.println("Account Number : "+account_no);
				System.out.println("Account Holder Name : "+name);
				System.out.println("Account Balance : Rs."+account_balance);
			}
			else {
				System.out.println("Account number and Pin Number does not match!!!");
			}
			}
			break;
		case 2:
			
				System.out.println("Enter your account number : ");
				accountno=s.nextInt();
				rs=st.executeQuery("Select count(*) from customers where Acc_No="+accountno);
				while(rs.next()) {
					if(rs.getInt(1)==0)
						b=false;
				}
				if(!b) System.out.println("Account number is INVALID!!!");
				else {
				rs=st.executeQuery("select * from customers where Acc_No="+accountno);
				while(rs.next()) {
					account_no=rs.getInt(1);
					name=rs.getString(2);
					pin_no=rs.getInt(3);
					account_balance=rs.getInt(4);
				}
				System.out.println("Enter your pin number : ");
				int pin=s.nextInt();
				if(pin==pin_no) {
					System.out.println("Enter amount to be withdrawn ");
					amount=s.nextInt();
					int temp=0,temp1=amount;
					if((amount>10000 || amount<100) && amount%100==0 )
						System.out.println("Amount to be withdraw should be in range 100 and 10000");
					else if(amount>account_balance)
						System.out.println("Withdraw amount is higher than the entered Account balance");
					else if(amount>total)
						System.out.println("Amount in ATM is less than withdraw amount, Kindly check with the bank.. ");
					else {
							while(amount>3000) {
								withdraw_amount+=2000;
								st.executeUpdate("update customers set Account_Balance=Account_Balance-2000 where Acc_No="+account_no);
								st.executeUpdate("update atm set number=number-1 where denomination=2000");
								st.executeUpdate("update atm set value=value-2000 where denomination=2000");
								amount-=2000;
								temp+=2000;
							}
							while(amount>1000) {
								withdraw_amount+=500;
								st.executeUpdate("update customers set Account_Balance=Account_Balance-500 where Acc_No="+account_no);
								st.executeUpdate("update atm set number=number-1 where denomination=500");
								st.executeUpdate("update atm set value=value-500 where denomination=500");
								amount-=500;
								temp+=500;
							}
							while(amount>0) {
								h_no+=100;
								amount-=100;
								temp+=100;
							}
							withdraw_amount+=h_no;
							st.executeUpdate("update customers set Account_Balance=Account_Balance-"+h_no+" where Acc_No="+account_no);
							st.executeUpdate("update atm set number=number-"+(h_no/100) +" where denomination=100");
							st.executeUpdate("update atm set value=value-"+h_no +" where denomination=100");
							
					}
					System.out.println("Amount "+temp+" is withdrawn !");
					if(temp!=temp1) {
						System.out.println("Sorry :( "+(temp1-temp)+" is not available in the ATM");
					}
				}
				else {
					System.out.println("Account number and Pin Number does not match!!!");
				}
			}
			break;
		case 3:
			System.out.println("Enter your account number : ");
			accountno=s.nextInt();
			rs=st.executeQuery("Select count(*) from customers where Acc_No="+accountno);
			while(rs.next()) {
				if(rs.getInt(1)==0)
					b=false;
			}
			if(!b) System.out.println("Account number is INVALID!!!");
			else {
			rs=st.executeQuery("select * from customers where Acc_No="+accountno);
			while(rs.next()) {
				account_no=rs.getInt(1);
				name=rs.getString(2);
				pin_no=rs.getInt(3);
				account_balance=rs.getInt(4);
			}
			System.out.println("Enter your pin number : ");
			int pin=s.nextInt();
			if(pin==pin_no) {
				System.out.println("Enter the account number you want to transfer your money");
				int saccno=s.nextInt();
				boolean bb=true;
				rs=st.executeQuery("Select count(*) from customers where Acc_No="+saccno);
				while(rs.next()) {
					if(rs.getInt(1)==0)
						bb=false;
				}
				if(!bb) System.out.println("Account number is INVALID!!!");
				else {
					System.out.println("Enter the amount to transfer : ");
					int temp3=s.nextInt();
					if(temp3>account_balance) {
						System.out.println("Account Balance is low!!!");
					}
					else {
						st.executeUpdate("update customers set Account_Balance=Account_Balance-"+temp3+" where Acc_No="+account_no);
						st.executeUpdate("update customers set Account_Balance=Account_Balance+"+temp3+" where Acc_No="+saccno);
						System.out.println("Amount "+temp3+" transfered successfully !");
					}
				}
			}
			else {
				System.out.println("Account No. and Pin No. does not match !");
			}
		}
			break;
		default:System.out.println("Enter valid option !");
		
	}
		s.close();
}
}