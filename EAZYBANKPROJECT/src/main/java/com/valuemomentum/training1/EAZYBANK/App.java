package com.valuemomentum.training1.EAZYBANK;

import java.util.Scanner;

public class App {
	public static void main(String[] args) throws Exception {
		Admin_user_login admin = new Admin_user_login();

		System.out.println("-------------------------------------------------");
		System.out.println("|                    EazyBank                   |");
		System.out.println("-------------------------------------------------");

		System.out.println("1.LOGIN ");
		System.out.println("2.CREATE A NEW ACCOUNT ");
		System.out.println("3.ABOUT US");
		System.out.println("4.EXIT");

		Scanner s = new Scanner(System.in);
		int option = s.nextInt();

		if (option == 1) // 1.LOGIN
		{
			System.out.println("-------------------------------------------------");
			System.out.println("|                   LOGIN                       |");
			System.out.println("-------------------------------------------------");

			System.out.println("1.ADMIN");
			System.out.println("2.CUSTOMER");
			

			option = s.nextInt();
			if (option == 1) {
				System.out.println("-------------------------------------------------");
				System.out.println("|                  ADMIN                        |");
				System.out.println("-------------------------------------------------");

				admin.Admin_login(); // call method for admin login
			}
			
			else if (option == 2) {
				System.out.println("-------------------------------------------------");
				System.out.println("|                    CUSTOMER                   |");
				System.out.println("-------------------------------------------------");

				admin.Customer_login(); // call method for customer login
				// after login as customer we have to write while with switch
				
			}
			else {
				System.out.println("Invalid option");
				System.out.println("please Enter Correct option");
			}
		}

		else if (option == 2) // 2.OPEN ACCOUNT
		{
			System.out.println("-------------------------------------------------");
			System.out.println("|            TO OPEN A NEW BANK ACCOUNT         |");
			System.out.println("-------------------------------------------------");

			System.out.println("1.REGISTER FOR NEW ACCOUNT");
			System.out.println("2.EXIT");

			option = s.nextInt();
			if (option == 1) {
			new Newcustomer(); // CALL METHOD OF NEW ACCOUNT

			} else if(option==2) {
				System.out.println("application closed ");
			}
			else {
				System.out.println("Invalid option");
				System.out.println("please Enter Correct option");
			}
			
		}
		else if (option == 3) // 3.ABOUT BANK
		{
			System.out.println("-------------------------------------------------");
			System.out.println("|                ABOUT EazyBank                 |");
			System.out.println("-------------------------------------------------");

			System.out.println("*****************Online Banking Made Easy**********");
			System.out.println("****************Services Offered:**************** ");
			System.out.println("-> Deposit");
			System.out.println("-> Withdrawal");
			System.out.println("-> Funds Transfer");
			System.out.println("-> Fixed Deposit");
			System.out.println("-> Mini- Statement");
			System.out.println("-> Balance");

		}

		else if (option == 4) // CLOSED
		{
			System.out.println(" application closed");
			System.exit(0);
		}

		else {
			System.out.println("Invalid option");
			System.out.println("please Enter Correct option");
		}
	}

}