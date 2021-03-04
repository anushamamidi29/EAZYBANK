package com.valuemomentum.training1.EAZYBANK;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Admin_user_login {
	
	Connection con;
	Statement stmt;
	ResultSet rs;
	
	String query2 = "select user_name ,password from customerdetails ";

	public void Admin_login() {

		String email_id1="admin";
		String password1="admin";
		Scanner   sc=new Scanner(System.in);
		System.out.println("User-name:");
	String	email_id = sc.next();
		System.out.println("Password:");
	String	password = sc.next();
	int count=0;
	if(email_id1.equals(email_id) && password1.equals(password)) 
	{
		count++;
			if (count >0) {
				System.out.println("Login Sucessfull");
				// after login as admin we have to write if-loop
				System.out.println(" ");
				while (true) {
					System.out.println("1.VIEW TRANSACTION RECORDS");
					System.out.println("2.VIEW TRANSACTION RECORDS OF A CUSTOMER ");
					System.out.println("3.VIEW FD RECORDS");
					System.out.println("4.VIEW CUSTOMER REQUESTS");
					System.out.println("5.UPDATE CUSTOMER REQUESTS");
					System.out.println("6.DELETE ACCOUNT");
					System.out.println("7.LOGOUT");

					int option = sc.nextInt();
					if (option == 1) {
						// method from admin_operations;
						new Admin_operations().getTransaction_records();
						System.out.println(" ");

					}

					else if (option == 2) {
						// method of getting selected transaction records from admin_operations;
						new Admin_operations().getSelectedTransaction_records();
						System.out.println(" ");

					}
					else if(option==3){
						new Admin_operations().fdstatement1(); // method to view fd records
						System.out.println(" ");
					}
					else if(option==4){
						new Admin_operations().details(); // method to CUSTOMER REQUESTS
						System.out.println(" ");
					}
					else if(option==5){
						new Admin_operations().ChangeDetails(); // method to CUSTOMER REQUESTS
						System.out.println(" ");
					}

					else if(option==6){
						new Admin_operations().delete_account(); // method to delete account
						System.out.println(" ");
					}
					else if(option==7){
						
						System.out.println(" logout Sucessful");
						break;
					}
				}
				}
			 else {
				System.out.println("please check the email-id and password");
				new Admin_user_login().Admin_login();}
	}}

			

	public void Customer_login() {

		System.out.println("User-Name:");
		Scanner s=new Scanner(System.in);
		String User_id = s.next();
		System.out.println("Password:");
		String password2 = s.next();
		int account_number=0;
		try {
			con = DBConnection.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery(query2);
			int count = 0;
			while (rs.next()) {
				String email_id = rs.getString("User_name");
				String password = rs.getString("password");
				if (User_id.contentEquals(email_id) && password2.contentEquals(password)) {
					count++;
				}
			}
			if (count == 1) {
				System.out.println("Login Sucessfull");
				System.out.println(" ");
				
				PreparedStatement psmt=con.prepareStatement("select account_number,CONCAT(last_name,' ', first_name) as Name from customerdetails where user_name=?");
				psmt.setString(1,User_id);
				ResultSet rs=psmt.executeQuery();
				
				while(rs.next()) {
					account_number=rs.getInt(1);
				System.out.println(" Account-Number : "+rs.getInt(1));
				System.out.println(" Name           :" +rs.getString(2));
				}
				
				
				System.out.println(" ");
				while (true) {
				System.out.println("1.BALANCE");
				System.out.println("2.WITHDRAWAL");
				System.out.println("3.DEPOSIT");
				System.out.println("4.FUND TRANSFER");
				System.out.println("5.FIXED-DEPOSIT");
				System.out.println("6.MINI-STATEMENT");
				System.out.println("7.REQUEST FOR CHANGES");
				System.out.println("8.LOGOUT");
				
				int option = s.nextInt();
				if (option == 1) {
					new Transactions().balanceCheck(account_number);   // Method for balance check
				} else if (option == 2) {
					new Transactions(). withdraw(account_number);// Method for withdrawal
				} else if (option == 3) {
					new Transactions().deposit(account_number);// Method for Deposit
				} else if (option == 4){
					new Transactions().FundTransfer(account_number);// method for fund transfer
				}
				 else if (option == 5){
						new Transactions().fixedDeposit(account_number); //method for fixed deposit
				 }
				 else if (option == 6){
						new Transactions().MiniStatement(account_number) ;    //method for mini statement
			}
				 else if (option == 7){
					 new Transactions().User_Requests(account_number);     //method for user request
			}
				 else if(option==8){
						
						System.out.println(" logout Sucessful");
						break;
					}
				}}else {
				System.out.println("please check the email-id and password");
				new Admin_user_login().Customer_login();
			}
			rs.close();
			stmt.close();
			con.close();

		} catch (Exception e) {
			System.out.println();
		}
		
	}
	
}