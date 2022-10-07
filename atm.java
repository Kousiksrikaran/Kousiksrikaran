package ATM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class atm {
public static void main(String[] args) throws Exception{
	try {
	Scanner s=new Scanner(System.in);
	Class.forName("com.mysql.cj.jdbc.Driver");
	Connection c=DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment","root","root");
	Statement st= c.createStatement();
	ResultSet rs;
	System.out.println("1. Load cash into ATM");
	System.out.println("2. Show customer details");
	System.out.println("3. Check ATM Balance");
	int choice=s.nextInt();
	switch(choice) {
	case 1:System.out.println("Enter the Denominations");
	System.out.println("2000 = ");
	int a=s.nextInt();
	System.out.println("500 = ");
	int b=s.nextInt();
	System.out.println("100 = ");
	int d=s.nextInt();
	
		st.executeUpdate("update atm set number=number+"+a+" where denomination=2000");
		st.executeUpdate("update atm set number=number+"+b+" where denomination=500");
		st.executeUpdate("update atm set number=number+"+d+" where denomination=100");
		st.executeUpdate("update atm set value=value+"+(a*2000)+" where denomination=2000");
		st.executeUpdate("update atm set value=value+"+(b*500)+" where denomination=500");
		st.executeUpdate("update atm set value=value+"+(d*100)+" where denomination=100");	
	break;
	case 2: rs=st.executeQuery("Select * from customers");
	System.out.println("Acc_No		Account_Holder		Pin_Number		Account_Balance");
	System.out.println();
	while(rs.next()) {
		System.out.println(rs.getInt(1)+"		"+rs.getString(2)+"			"+rs.getInt(3)+"			"+rs.getInt(4));
	}
	break;
	case 3:
		int total=0;
		rs=st.executeQuery("Select * from atm");
		System.out.println("Denomination   Number   Value");
		System.out.println();
		while(rs.next()) {
			System.out.println(rs.getInt(1)+"             "+rs.getInt(2)+"       "+rs.getInt(3));
			total+=rs.getInt(3);
		}
		System.out.println();
		System.out.println("Total amount in ATM = "+total);
		break;
		default:System.out.println("Enter valid option !");
		}
		s.close();
	}
	catch(Exception e) {
		System.out.println(e);
	}
	}
	
}