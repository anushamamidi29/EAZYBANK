package com.valuemomentum.training1.EAZYBANK;

import java.util.Scanner;

import java.sql.*;

public class Transactions {
	Connection con;
	PreparedStatement stmt, pstmt;
	ResultSet rs;
	Statement st;
	double i;
	Scanner s = new Scanner(System.in);
	// balance check method
	 void balanceCheck(int account_number)
	 {
		 try{
	        	con=DBConnection.getConnection();
	            String query="select balance from customerdetails where account_number=? ";
	            stmt=con.prepareStatement(query);
	            
	            
	             
	             stmt.setInt(1, account_number);
	             rs=stmt.executeQuery();
	             System.out.println("Available Balance is ");
	             while(rs.next()) {
	            	 System.out.println("Balance: "+rs.getInt(1));

	             }
	             
	             System.out.println(" ");
	           
		 } catch(Exception e) {
			 System.out.println(e);
		 }}
	// withdraw method

	void withdraw(int account_number) {

		try {
			con = DBConnection.getConnection();
			

			System.out.println("Enter Withdrawal Amount:");
			int amount = s.nextInt();

			String query="INSERT INTO transactions(account_number,transaction_type,TYPE,amount,Current_balance) "
					+  "VALUES(? ,'Withdrawal','DB',?,(SELECT balance FROM customerdetails WHERE account_number=? ) - ? ) ";
			stmt=con.prepareStatement(query);
			 stmt.setInt(1, account_number);
			
	    	  stmt.setInt(2, amount);
	    	  stmt.setInt(3, account_number);
	    	  stmt.setInt(4, amount);
	    	  
	    	  stmt.executeUpdate();
			
			String query2="UPDATE customerdetails SET balance=(SELECT Current_balance FROM transactions where account_number=? order BY t_id DESC LIMIT 1) WHERE account_number=? ";
			stmt=con.prepareStatement(query2);
			 stmt.setInt(1, account_number);
	    	  stmt.setInt(2, account_number);
	    	  stmt.executeUpdate();
	    	  
	    	  String query3="select balance from customerdetails where account_number=? ";
				stmt=con.prepareStatement(query3);
				stmt.setInt(1, account_number);

				rs=stmt.executeQuery();
				 
				 				 
		    	  System.out.println("********** Amount Withdrawn **********");
		    	  while(rs.next()) {
			     System.out.println("Balance: "+rs.getInt(1));
					 }
		    	  System.out.println(" ");

			//s.close();
			rs.close();
			stmt.close();
			con.close();

		} catch (Exception e) {
			System.out.println("Insufficient Balance");
		}
	}

//Deposit Method
	void deposit(int account_number) {
		try {
			con = DBConnection.getConnection();
			

			
			System.out.println("Enter Deposit Amount:");
			int amount = s.nextInt();

			String query="INSERT INTO transactions(account_number,transaction_type,TYPE,amount,Current_balance) "
					+ "	 VALUES(?,'Deposit','Cr',?,?+(SELECT balance FROM customerdetails WHERE account_number=? ))   ";
			stmt=con.prepareStatement(query);
			
			stmt.setInt(1, account_number);
			
	    	  stmt.setInt(2, amount);
	    	  stmt.setInt(3, amount);
	    	  stmt.setInt(4, account_number);
	    	  stmt.executeUpdate();
			
			String query2="UPDATE customerdetails SET balance=(SELECT Current_balance FROM transactions where account_number=? order BY t_id DESC LIMIT 1) WHERE account_number=? ";
			stmt=con.prepareStatement(query2);
			 stmt.setInt(1, account_number);
	    	  stmt.setInt(2, account_number);
	    	  
	    	  stmt.executeUpdate();
			
			String query3="select balance from customerdetails where account_number=? ";
			stmt=con.prepareStatement(query3);
			 stmt.setInt(1, account_number);

			
	    	  rs=stmt.executeQuery();
	    	  
	    	  System.out.println("************* Amount Deposited Successfully **************");
	    	  while(rs.next()) {
				     System.out.println("Balance: "+rs.getInt(1));
						 }
	    	  System.out.println(" ");
			//s.close();
			rs.close();
			stmt.close();
			con.close();
		}

		catch (Exception e) {
			System.out.println("Invalid details....");
		}

	}

//funds transfer method

	void FundTransfer(int account_number) throws SQLException {

		con = DBConnection.getConnection();
		st = con.createStatement();
		double amount;
		double b = 0;
		
		
		System.out.println("Enter To Account Number");
		int account_number1 = s.nextInt();

		rs = st.executeQuery("Select balance from customerdetails where account_number = " + account_number);
		while (rs.next()) {
			i = rs.getDouble(1);

		}

		// To Check if balance is greater than zero or not
		if (i > 0) {
			System.out.println("Enter Amount");
			amount = s.nextDouble();
			// To check if amount is smaller than available balance
			if (amount < i) {
				try {

					i = i - amount;

					st = con.createStatement();
					int o = st.executeUpdate(
							"update customerdetails set balance = " + i + " where account_number = " + account_number);

					String sqlInsert = "INSERT INTO transactions(account_number,transaction_type,to_acc,type,amount,Current_balance) "
	    					+ "values (?,'Fund Transfer',?,'Db',?,?)";	

					
					pstmt=con.prepareStatement(sqlInsert);
			        
	    			pstmt.setInt(1,account_number);
	    			pstmt.setInt(2,account_number1);
	    			pstmt.setDouble(3, amount);
	    			pstmt.setDouble(4, i);
	    		
	    			pstmt.executeUpdate();
					rs = st.executeQuery(
							"Select balance from customerdetails where account_number = " + account_number1);

					while (rs.next()) {
						b = rs.getDouble(1);

					}
					// Updating balance
					b += amount;

					// Updating the Balance after receiving the amount from sender
					st = con.createStatement();
					int q = st.executeUpdate(
							"update customerdetails set balance = " + b + " where account_number = " + account_number1);

					rs = st.executeQuery(
							"Select balance from customerdetails where account_number = " + account_number);
					while (rs.next()) {
						double j = rs.getDouble(1);
						// Displaying the Balance of the sender
						System.out.println("*****Transaction successful*****");
						System.out.println("*****Funds have been transfered to :" + account_number1);
						System.out.println("Available balance is:" + j);
					}
					System.out.println(" ");
					//s.close();
					rs.close();
					st.close();
					con.close();
				} catch (Exception d) {
					System.out.println(" ");
				}
			} else {
				System.out.println("*****AMOUNT EXCEEDS BALANCE*****");
				System.out.println("*****TRANSACTION FAILED*****");
			}

		} else {
			System.out.println("*****INSUFFICIENT BALANCE*****");
			System.out.println("*****TRANSACTION FAILED*****");
		}

	}

	// Fixed-Deposit

	void fixedDeposit(int account_number) {
		try {
			con = DBConnection.getConnection();
			
			
			System.out.println("Enter Maturity Period:");
			int period = s.nextInt();

			System.out.println("Enter FD Amount:");
			int amount = s.nextInt();

			String query="INSERT INTO transactions(account_number,transaction_type,TYPE,amount,Current_balance) "
					+  "VALUES(? ,'Fixed Deposit','Db',?,(SELECT balance FROM customerdetails WHERE account_number=? ) - ? ) ";
			stmt=con.prepareStatement(query);
			 stmt.setInt(1, account_number);
			 
	    	  stmt.setInt(2, amount);
	    	  stmt.setInt(3, account_number);
	    	  stmt.setInt(4, amount);
	    	  
	    	  stmt.executeUpdate();
			
			String query2="UPDATE customerdetails SET balance=(SELECT Current_balance FROM transactions where account_number=? order BY t_id DESC LIMIT 1) WHERE account_number=? ";
			stmt=con.prepareStatement(query2);
			 stmt.setInt(1, account_number);
	    	  stmt.setInt(2, account_number);
	    	  
	    	  stmt.executeUpdate();
			String query3="select balance from customerdetails where account_number=? ";
			stmt=con.prepareStatement(query3);
			stmt.setInt(1, account_number);
			 rs=stmt.executeQuery();
			 while(rs.next()) {
			     System.out.println("Balance After Fixed Deposit: "+rs.getInt(1));
					 }
			String query4="INSERT INTO Fixed_deposit(acc_number,Rate_of_Interest,Period,Maturity_Amount)"
					+ "VALUES(? ,4,? ,(? *(0.04 * ? )) + ? ) ";
			
			stmt=con.prepareStatement(query4);
			 stmt.setInt(1, account_number);
	    	  stmt.setInt(2, period);
	    	  stmt.setInt(3, amount);
	    	  stmt.setInt(4, period);
	    	  stmt.setInt(5, amount);

	    	  stmt.executeUpdate();
			 
	    	  String query5="SELECT Maturity_Amount FROM Fixed_deposit where acc_number=? order BY fd_no DESC LIMIT 1 ";
				stmt=con.prepareStatement(query5);
				stmt.setInt(1, account_number);
				
				rs=stmt.executeQuery();
				System.out.println("********** Fixed Deposit Made **********");
				 while(rs.next()) {
				     System.out.println("Maturity Amount: "+rs.getInt(1));
						 }
				 System.out.println(" ");		 
		
			//s.close();
			rs.close();
			stmt.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
//mini-statement Method

	void MiniStatement(int account_number) {
		try {
			con=DBConnection.getConnection();
			
			
		st=con.createStatement();
        // fetching all the contents from transactions table
	rs=st.executeQuery("SELECT t.t_id,t.account_number,c.first_name,c.last_name,t.date,t.transaction_type,t.to_acc,t.TYPE,t.amount,t.Current_balance\r\n"
			+ "\r\n"
			+ "        FROM transactions t\r\n"
			+ "        INNER JOIN customerdetails c\r\n"
			+ "        ON t.account_number=c.account_number WHERE t.account_number="+account_number);
       
	System.out.println("t_id\t Account-Number\t FirstName\tLastName\tDate Time \t\tTransaction-Type\tToAccount\t Type\t Amount\t\tCurrent-Balance\t");

	while(rs.next()) {
		System.out.println(rs.getInt(1)+"\t\t"+rs.getInt(2)+"\t  "+rs.getString(3)+
				
				 "\t\t"+rs.getString(4)+" \t\t"+rs.getString(5)+"\t\t "+rs.getString(6)+
				" \t\t "+rs.getInt(7)+" \t "+rs.getString(8)+" \t  "+rs.getDouble(9)+"\t\t"+rs.getDouble(10)+"\t");
        }	
	System.out.println(" ");
        rs.close();
        st.close();
		con.close();
		}catch(Exception a) {
			System.out.println(a);
		}
	}
	// Update-Request method

	void User_Requests(int account_number) {
		try {
			con = DBConnection.getConnection();
			pstmt = con.prepareStatement("Insert into Update_Requests (account_number,description) values(?,?)");
			
			
			System.out.println("Enter Details to be Updated ");
			s.nextLine();
			String description = s.nextLine();

			pstmt.setInt(1, account_number);
			pstmt.setString(2, description);
			pstmt.executeUpdate();

			System.out.println("Request Successfully generated");
			
			System.out.println(" ");
			pstmt.close();
			con.close();
		} catch (Exception r) {
			r.printStackTrace();
		}
	}
//view fd records of customer
	void fdstatement(int account_number)
	{
		try {
			con=DBConnection.getConnection();
 Scanner s=new Scanner(System.in);
			 
			 
			 String query="\r\n"
			 		+ "        SELECT f.fd_no,f.DATE,f.acc_number,f.Rate_of_Interest,f.Period,f.Maturity_Amount,c.first_name,c.last_name\r\n"
			 		+ "		  FROM fd f INNER JOIN transactions t ON f.acc_number=t.t_id INNER JOIN customerdetails c \r\n"
			 		+ "		  ON t.account_number=c.account_number WHERE f.acc_number=?; \r\n"
			 		+ "         ";
			 stmt=con.prepareStatement(query);
			 stmt.setInt(1, account_number);
			 rs=stmt.executeQuery();
			 System.out.println("FD number"+" "+"Date Of FD Start"+" "+"Account Number"+" "+"Rate Of Interest"+" "+"Period in Years"+" "+"Maturity Amount"+" "+"First Name"+" "+"Last Name");
			 while(rs.next()) {
			     System.out.println(rs.getInt(1)+"        "+rs.getDate(2)+"             "+rs.getInt(3)+"          "+rs.getInt(4)+"                 "+rs.getInt(5)+"               "+rs.getInt(6)+"              "+rs.getString(7)+"         "+rs.getString(8));
					 }
			 System.out.println(" ");
		}
		catch(Exception e)
		{
			System.out.println("Invalid detail");
		}
	}
}
