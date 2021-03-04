package com.valuemomentum.training1.EAZYBANK;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Admin_operations {
	Connection con;
	Statement stmt;
	ResultSet rs;
	PreparedStatement pstmt;
	Scanner sc = new Scanner(System.in);

	public void getTransaction_records() {
		try {
			con = DBConnection.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("select t_id,account_number,transaction_type,type,amount from Transactions ");
			System.out.println("t_id\t Account-Number\t\tTransaction-Type\tType\t\tAmount\t\t");

			while (rs.next()) {
				System.out.println(rs.getInt(1) + "\t\t " + rs.getInt(2) + "\t\t " + rs.getString(3) + "\t\t " 
				+rs.getString(4)+ "\t\t " + rs.getDouble(5));
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void getSelectedTransaction_records() {

		try {
			con=DBConnection.getConnection();
			Scanner s=new Scanner(System.in);
			System.out.println("Enter Account Number : ");
			int account_number=s.nextInt();
		stmt=con.createStatement();
        // fetching all the contents from transactions table
	rs=stmt.executeQuery("SELECT t.t_id,t.account_number,c.first_name,c.last_name,t.date,t.transaction_type,t.to_acc,t.TYPE,t.amount\r\n"
			+ "\r\n"
			+ "        FROM transactions t\r\n"
			+ "        INNER JOIN customerdetails c\r\n"
			+ "        ON t.account_number=c.account_number WHERE t.account_number="+account_number);
	System.out.println("t_id\t Account-Number\t FirstName  LastName\t   Date Time \t\tTransaction-Type\t  ToAccount\t\tType\t\tAmount\t");
        while(rs.next()) {
			System.out.println(rs.getInt(1)+"\t\t"+rs.getInt(2)+"\t  "+rs.getString(3)+
					
					 "\t\t"+rs.getString(4)+" \t   "+rs.getString(5)+"\t\t "+rs.getString(6)+
					" \t\t "+rs.getInt(7)+" \t\t"+rs.getString(8)+" \t\t"+rs.getDouble(9));
        }
        rs.close();
        stmt.close();
		con.close();
		}catch(Exception a) {
			System.out.println("Invalid Details....");
		}
	}
	//view requests of the customer for changes or to delete the account
	void details() {
		try {
			con=DBConnection.getConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery("Select * from Update_Requests");
			System.out.println("Serial-no\tAccount-Number\t Description");
			if(rs.next()) {
				
			while(rs.next()) {
				System.out.println(rs.getInt(1)+"\t\t "+rs.getInt(2)+"\t\t "+rs.getString(3));
			}}
			else {
				System.out.println("No Records Found........");
			}
			rs.close();
			stmt.close();
			con.close();
		}catch(Exception t) {
			System.out.println(" ");
		}
	}
	//METHOD TO UPDATE THE REQUESTS
	
	void ChangeDetails() {
	 	
	 	int count=0;
		System.out.println("Enter Account Number");
		int account_number=sc.nextInt();
		System.out.println("Enter ur choice of update");
		String choice=sc.next();
		System.out.println("Enter final change");
		String change=sc.next();
		
		try {
			
			con=DBConnection.getConnection();
			stmt=con.createStatement();
			
		stmt.executeUpdate("update customerdetails set "+choice+" = '"+change+"' where account_number = "+account_number);
			count++;
			if(count>0) {
			System.out.println("*****Customer Details have been Updated Successfully*****");}
			else {
				System.out.println("invalid Details");
			}
		
		stmt.close();
		con.close();
		}catch(Exception e){
			System.out.println(e);
		}
		
	 
 }
	// if customer wants to delete their account they will send a request to admin
	// then Admin will delete it

	// method to delete
	public void delete_account() {
int count=0;
		try {
			
			System.out.println("enter Account-number to be Deleted:");
			int account_number = sc.nextInt();
			String sqlUpdate = "delete  from customerdetails where account_number=" + account_number;
			con = DBConnection.getConnection();
			stmt = con.createStatement();

			 count = stmt.executeUpdate(sqlUpdate);
			if (count > 0) {
				System.out.println("Account Deleted Sucessfully");
			} else {
				System.out.println("Invalid Account-number!! Check the account number and Try Again .....");

			}
		} catch (SQLException e) {
			System.out.println(" ");
		}

	}
	//view fd records
	void fdstatement1()
	{
		try {
			con=DBConnection.getConnection();
 Scanner s=new Scanner(System.in);
			 
			
			 String query="\r\n"
			 		+ "        SELECT f.fd_no,f.DATE,f.acc_number,f.Rate_of_Interest,f.Period,f.Maturity_Amount,c.first_name,c.last_name\r\n"
			 		+ "		  FROM fixed_deposit f  INNER JOIN customerdetails c  \r\n"
			 		+ "		  ON f.acc_number=c.account_number; \r\n"
			 		+ "         ";
			 stmt=con.createStatement();
			 rs=stmt.executeQuery(query);
			 System.out.println("FD number"+"   "+"Date Of FD Start"+"   "+"Account Number"+"   "+"First Name"+"    "+"Last Name"+"   "+"Period In Years"+"   "+"Rate Of Interest"+"   "+"Maturity Amount");
			 while(rs.next()) {
			     System.out.println(rs.getInt("fd_no")+"            "+rs.getDate("Date")+"               "+rs.getInt("acc_number")+"           "+rs.getString("first_name")+"      "+rs.getString("last_name")+"          "+rs.getInt("Period")+"               "+rs.getInt("Rate_of_Interest")+"                   "+rs.getInt("Maturity_Amount"));
					 }

		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
