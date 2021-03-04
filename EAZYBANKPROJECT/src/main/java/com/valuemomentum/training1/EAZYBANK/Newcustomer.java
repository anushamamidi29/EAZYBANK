package com.valuemomentum.training1.EAZYBANK;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Scanner;

public class Newcustomer extends DBConnection{
	
	
	Connection  con=DBConnection.getConnection();
	Scanner s=new Scanner(System.in);
	public Newcustomer() throws SQLException {
		
		System.out.println("PLEASE PROVIDE ALL DETAILS");
		System.out.println();
		System.out.println();
		System.out.println("Enter Account Type(Savings/Current):");
		String accttype=s.next();
		System.out.println("Enter First-Name:");
		String firstname=s.next();
		System.out.println("Enter Last-Name:");
		String lastname=s.next();
		System.out.println("Enter Gender:");
		String gender=s.next();
		System.out.println("Enter DOB (YYYY/MM/DD):");
		String dob=s.next();
		System.out.println("Enter City:");
		String city=s.next();
		System.out.println("Enter State:");
		String state=s.next();
		System.out.println("Enter Aadhaarnumber:");
		s.nextLine();
		String Adhaar=s.nextLine();
		System.out.println("Enter UserName:");
		
		String email=s.next();
     System.out.println("Enter Password:");
		String password=s.next();
		
		
		PreparedStatement pstmt=con.prepareStatement("INSERT INTO customerdetails(account_type,first_name,last_name,gender,dob,city,state,Aadhaar_number,balance,user_name,PASSWORD) VALUES(?,?,?,?,?,?,?,?,?,?,?)");
		pstmt.setString(1, accttype);
		pstmt.setString(2, firstname);
		pstmt.setString(3, lastname);
		pstmt.setString(4, gender);
		pstmt.setString(5, dob);
		pstmt.setString(6, city);
		pstmt.setString(7, state);
		pstmt.setString(8, Adhaar);
		if(accttype.equals("current")) {
			pstmt.setDouble(9, 0);}
		if(accttype.equals("savings")) {
			pstmt.setDouble(9, 500);}
		
		pstmt.setString(10, email);
		pstmt.setString(11, password);
	  
		pstmt.executeUpdate();
		System.out.println("Thanks for creating account with eazybank");
		
		PreparedStatement psmt=con.prepareStatement("select account_number from customerdetails where user_name=?");
		psmt.setString(1,email);
		ResultSet rs=psmt.executeQuery();
		while(rs.next()) {
		System.out.println("Your Account-Number is: "+rs.getInt(1));
		}
		System.out.println("Please Restart to Login.....");
		
		rs.close();
	    psmt.close();
		con.close();
		s.close();
		
	}

}
