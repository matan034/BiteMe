package server;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entity.Supplier;
import entity.User;
import ocsf.server.ConnectionToClient;

public class DBUserController {
	
	/**
	Func for logging user in , when user logs in we update IsLoggedIn to 1
	 * @param res res[0] used to start function, rest of res for details we need for queries,  res[0]=""User_login"" ,res[1...]= user name and password from user
	 * @param client The connection from which the message originated.
	 * @param myCon the connection to mySql DB
	 * @param db the main database controller used in order to send message back to client */
	protected void userLogin(String[] res, ConnectionToClient client,Connection myCon,DBController db) throws SQLException {
		String result;
		Statement stmtJoin= myCon.createStatement();
		ResultSet rsJoin;
		int flag;//flag for answer after execute update
		rsJoin = stmtJoin.executeQuery(
				String.format("SELECT * FROM biteme.users\r\n"
						+ "INNER JOIN biteme.branch\r\n"
						+ "	ON branch.BranchID=users.HomeBranch\r\n"
						+ "WHERE UserName='%s' AND Password='%s';", res[1], res[2]));
		if (rsJoin.next()) {
			result = "User login~" + rsJoin.getString(1) + "~" + rsJoin.getString(2) + "~" + rsJoin.getString(3) + "~"
					+ rsJoin.getString(4)+"~"+rsJoin.getString(5)+"~"+rsJoin.getString(6) + "~" + rsJoin.getString(7) + "~" + rsJoin.getString(8) + "~" + rsJoin.getString(9)+ "~" + rsJoin.getString(10) + "~" + rsJoin.getString(11)+"~"+rsJoin.getString(13);
			System.out.println("User found:logging in");

			flag =stmtJoin.executeUpdate(String.format("UPDATE biteme.users SET IsLoggedIn = '1' WHERE UserName='%s' AND Password='%s'",res[1],res[2]));
	
			
			db.sendToClient(result, client);
		} else
			db.sendToClient("User login~User not Found", client);
		rsJoin.close();
		stmtJoin.close();
	}
	/**
	Func for loading users for when we need to view users. Used in approve users screen, change user premission screen
	* @param res  res[0] used to start function, rest of res for details we need for queries, res[0]=""Load_users"";
	 * @param client The connection from which the message originated.
	 * @param myCon the connection to mySql DB
	 * @param db the main database controller used in order to send message back to client */
		protected void loadUsers(String[] res, ConnectionToClient client,Connection myCon,DBController db) throws SQLException {
		Statement stmt;
		stmt = myCon.createStatement();
		ResultSet rs;
		rs = stmt.executeQuery("SELECT * FROM biteme.users");
		ArrayList<User> all_users = new ArrayList<>();
		while (rs.next()) {

			all_users.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(10)));

		}
		db.sendToClient(all_users, client);
		stmt.close();
		rs.close();
	}
	/**
	Func for registering either a private account or business account
	* @param res  res[0] used to start function, rest of res for details we need for queries, res[0]="Business_account" res[1..]=Business account toString  OR res[0]="Private_account res[1...]=Private account toString "
	 * @param client The connection from which the message originated.
	 * @param myCon the connection to mySql DB
	 * @param db the main database controller used in order to send message back to client */
	protected void privateOrBusinessAccountReg(String[] res, ConnectionToClient client,Connection myCon,DBController db) throws SQLException {
		Statement stmt;
		stmt = myCon.createStatement();
		int flagAccountReg = 1, flagReg = 0;
		ResultSet rs;
		rs = stmt.executeQuery(String.format("SELECT * FROM biteme.account WHERE ID='%s'", res[3]));
		if (!rs.isBeforeFirst()) {// check if we need to create an account first
			flagAccountReg = accountReg(res, client,myCon,db);
		}

		if (flagAccountReg == 1)// account created successfully
		{
			if (res[0].equals("Private_account")) {
				flagReg = stmt.executeUpdate(String.format(
						"INSERT INTO biteme.privateaccount (AccountNum, CreditCardNumber) VALUES ((SELECT AccountNum from biteme.account WHERE ID='%s'),'%s');",
						res[3], res[6]));
			} else// business_account
				flagReg = stmt.executeUpdate(String.format(
						"INSERT INTO biteme.businessaccount (AccountNum, EmployerNum, MonthlyLimit) VALUES ((SELECT AccountNum from biteme.account WHERE ID='%s'),(SELECT EmployerNum from biteme.employer WHERE Name='%s'), '%d');",
						res[3], res[6], Integer.parseInt(res[7])));

			if (flagReg > 0) {
				db.sendToClient("New Account~Created Succesfully", client);
			} else
				db.sendToClient("New Account~Failed business account creation", client);
		} else
			db.sendToClient("New Account~Failed new account creation", client);
		rs.close();
		stmt.close();
	}
	/**
	Func for registering Account details 
	* @param res  res[1]=FirstName,res[2]=LastName,res[3]=ID,res[4]=Telephone,res[5]=Email"
	 * @param client The connection from which the message originated.
	 * @param myCon the connection to mySql DB
	 * @param db the main database controller used in order to send message back to client */
		protected int accountReg(String[] res, ConnectionToClient client,Connection myCon,DBController db) throws SQLException {
		Statement stmt;
		stmt = myCon.createStatement();
		int flag;
		flag = stmt.executeUpdate(String.format(
				"INSERT INTO biteme.account (FirstName, LastName,ID, Telephone,Email) VALUES ('%s', '%s', '%s', '%s', '%s');",
				res[1], res[2], res[3], res[4], res[5]));
		if (flag > 0) {
			stmt.close();
			return 1;
		} else {
			stmt.close();
			return 0;
		}
	}
	/**
	Func for checking valid Account details inputted by user during account registration
	* @param res  res[0] used to start function, rest of res for details we need for queries, res[0]=Check_account_input"
	 * @param client The connection from which the message originated.
	 * @param myCon the connection to mySql DB
	 * @param db the main database controller used in order to send message back to client */
	protected void checkAccountInput(String[] res, ConnectionToClient client,Connection myCon,DBController db) throws SQLException {
		Statement stmt;
		stmt = myCon.createStatement();
		String result = "Check Account Input~";
		ResultSet rs;
		rs = stmt.executeQuery(String.format("SELECT * FROM biteme.account WHERE ID='%s'", res[3]));
		if (rs.isBeforeFirst()) {// check if we got a result
			result += "ID~";
		} else
			result += "NoIDError~";
		rs = stmt.executeQuery(String.format("SELECT * FROM biteme.account WHERE Telephone='%s'", res[4]));
		if (rs.isBeforeFirst()) {// check if we got a result
			result += "Telephone~";
		} else
			result += "NoTelephoneError~";
		rs = stmt.executeQuery(String.format("SELECT * FROM biteme.account WHERE Email='%s'", res[5]));
		if (rs.isBeforeFirst()) {// check if we got a result
			result += "Email";
		} else
			result += "NoEmailError";
		db.sendToClient(result, client);
		rs.close();
		stmt.close();

	}
	
	/**
	Func for checking if Employed is Approved and if Employer name is in the database
	* @param res  res[0] used to start function, rest of res for details we need for queries, res[0]="Check_employer",res[1]=Employer name.
	 * @param client The connection from which the message originated.
	 * @param myCon the connection to mySql DB
	 * @param db the main database controller used in order to send message back to client */
		protected void checkEmployer(String[] res, ConnectionToClient client,Connection myCon,DBController db) throws SQLException {
		Statement stmt;
		stmt = myCon.createStatement();
		String result = "Check Employer Input~";
		ResultSet rs;
		rs = stmt.executeQuery(String.format("SELECT * FROM biteme.employer WHERE Name='%s'", res[1]));
		if (rs.isBeforeFirst()) {// check if we got a result
			result += "Name~";
			rs = stmt.executeQuery(
					String.format("SELECT * FROM biteme.employer WHERE Name='%s' AND  IsApproved=%d", res[1], 1));
			if (rs.isBeforeFirst()) {// check if we got a result
				result += "Approved~";
			} else
				result += "NoApproved~";
		} else {
			result += "NoName~";
			result += "NoApproved~";
		}

		db.sendToClient(result, client);
		rs.close();
		stmt.close();
	}
	/**
	Func for registering an employer to DB
	* @param res  res[0] used to start function, rest of res for details we need for queries, res[0]="Reg_employer",res[1..]=employer details by employer.Tostring.
	 * @param client The connection from which the message originated.
	 * @param myCon the connection to mySql DB
	 * @param db the main database controller used in order to send message back to client */
		protected void regEmployer(String[] res, ConnectionToClient client,Connection myCon,DBController db) throws SQLException {
		Statement stmt;
		stmt = myCon.createStatement();
		int flag;
		flag = stmt.executeUpdate(
				String.format("INSERT INTO biteme.employer (Name, Address, Telephone) VALUES ('%s', '%s', '%s');",
						res[1], res[2], res[3]));
		if (flag > 0)
			db.sendToClient("Employer Register~Employer Has Been Registered", client);
		else
			db.sendToClient("Employer Register ~Error Registering Employer", client);
		stmt.close();
	}
	
	/**
	Func for updating user Status
	* @param res  res[0] used to start function, rest of res for details we need for queries, res[0]="Update_user",res[1]=Status to update, res[2]=User ID
	 * @param client The connection from which the message originated.
	 * @param myCon the connection to mySql DB
	 * @param db the main database controller used in order to send message back to client */
	protected void updateUser(String[] res, ConnectionToClient client,Connection myCon,DBController db) throws SQLException {
		Statement stmt;
		int flag;
		try {
			stmt = myCon.createStatement();
			flag = stmt.executeUpdate(
					String.format("UPDATE biteme.users SET Status = '%s' WHERE ID = %s;", res[1], res[2]));
			if (flag > 0)
				db.sendToClient("Update~Updated Successfuly", client);
			else
				db.sendToClient("Update~Failed to update", client);
			stmt.close();
			}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	
	
	 /**
	Func for importing users to DB from outside "USER IMPORT utility"
	* @param res  res[0] used to start function, rest of res for details we need for queries, res[0]="Import_user",res[1..]=User details from ToString
	 * @param client The connection from which the message originated.
	 * @param myCon the connection to mySql DB
	 * @param db the main database controller used in order to send message back to client */

	  protected void importUsers(String []res,ConnectionToClient client,Connection myCon,DBController db) throws SQLException{
		  Statement stmt;
		  stmt = myCon.createStatement();
		  int flag;
		  flag=stmt.executeUpdate(String.format("INSERT INTO biteme.users (ID, FirstName, LastName,Email,Phone,Type,UserName,Password,IsLoggedIn,Status) VALUES ('%s', '%s', '%s','%s','%s','%s','%s','%s','%d','%s');",res[1],res[2],res[3],res[4],res[5],res[6],res[7],res[8],Integer.parseInt(res[9]),res[10]));
			if(flag>0) db.sendToClient("User import~Users Imported", client);
			else db.sendToClient("User import ~Error importing users", client);
		  stmt.close();
	  }
	  
	  
	  
	  //maybe delete
	  	protected void loadCustomer(String[] res, ConnectionToClient client,Connection myCon,DBController db) {
		Statement stmt;
		ResultSet rs;
		String result;
		try {
			stmt = myCon.createStatement();
			rs = stmt.executeQuery("SELECT * FROM biteme.customers WHERE PrivateAccount=" + Integer.parseInt(res[1]));
			if (rs.next()) {
				System.out.println("customer found");
				result = "Customer load~" + rs.getString(1) + "~" + rs.getString(2) + "~" + rs.getString(3) + "~"
						+ rs.getString(4)+"~"+rs.getString(5);
				db.sendToClient(result, client);
			} else
				db.sendToClient("Customer load~Customer Wasnt found", client);
			rs.close();
			stmt.close();
		} catch (Exception e) {

		}
	}
	
	 /**
	Func for getting a specific private account by it's accout num
	* @param res  res[0] used to start function, rest of res for details we need for queries, res[0]="Load private Account",res[1..]=AccountNum
	 * @param client The connection from which the message originated.
	 * @param myCon the connection to mySql DB
	 * @param db the main database controller used in order to send message back to client */
		protected void loadPrivateAccount(String[] res, ConnectionToClient client,Connection myCon,DBController db) {
		Statement stmt;
		ResultSet rs;
		String result;
		try {
			stmt = myCon.createStatement();
			rs = stmt.executeQuery(String.format("SELECT x.AccountNum,x.FirstName,x.LastName,x.ID,x.Telephone,x.Email,privateaccount.CreditCardNumber\r\n"
					+ "FROM (SELECT * FROM biteme.account where AccountNum='%d') as x\r\n"
					+ "INNER JOIN privateaccount ON x.AccountNum=privateaccount.AccountNum;",Integer.parseInt(res[1])));
			if (rs.next()) {
				System.out.println("account found");
				result = "private account load~" + rs.getString(1) + "~" + rs.getString(2) + "~" + rs.getString(3) + "~"
						+ rs.getString(4)+"~" + rs.getString(5)+"~" + rs.getString(6)+"~" + rs.getString(7);
				db.sendToClient(result, client);
			} else
				db.sendToClient("private account load~account Wasnt found", client);
			rs.close();
			stmt.close();
		} catch (Exception e) {

		}
	}
	
	 /**
	Func for getting a specific supplier by it's Branchnum
	* @param res  res[0] used to start function, rest of res for details we need for queries, res[0]="Load_suppliers",res[1..]=BranchNum
	 * @param client The connection from which the message originated.
	 * @param myCon the connection to mySql DB
	 * @param db the main database controller used in order to send message back to client */
	protected void loadSuppliers(String[] res, ConnectionToClient client,Connection myCon,DBController db) {
		Statement stmt;
		ResultSet rs;

		try {
			stmt = myCon.createStatement();
			rs = stmt.executeQuery("SELECT * FROM biteme.restaurant WHERE BranchNum = "+res[1]);
			ArrayList<Supplier> suppliers = new ArrayList<>();
			while (rs.next()) {
				int supplierNum = rs.getInt(1);
				int branchNum = rs.getInt(2);
				int isApproved = rs.getInt(3);
				String name = rs.getString(4);
				String address = rs.getString(5);
				String city = rs.getString(6);
				String type = rs.getString(7);
				String manager = rs.getString(8);
				suppliers.add(new Supplier(supplierNum, branchNum, isApproved, name, address, city, type, manager));
			}

			db.sendToClient(suppliers, client);
			rs.close();
			stmt.close();
		} catch (Exception e) {

		}
	}
	
	/**
	Func for getting loading all business accounts that need to be approved
	* @param res  res[0] used to start function, rest of res for details we need for queries, res[0]="Load_business_account"
	 * @param client The connection from which the message originated.
	 * @param myCon the connection to mySql DB
	 * @param db the main database controller used in order to send message back to client */
	  protected void LoadBusinessAccount(String []res,ConnectionToClient client,Connection myCon,DBController db) throws SQLException{
		  String result;
		  Statement stmt;
		  stmt = myCon.createStatement();
		  ResultSet rs;
		  ArrayList<String> usersToApprove=new ArrayList<>();
		  rs =stmt.executeQuery("SELECT employer.Name,AccountWithBusiness.ID,AccountWithBusiness.FirstName,AccountWithBusiness.LastName,AccountWithBusiness.IsApproved,AccountWithBusiness.AccountNum\r\n"
		  		+ "FROM biteme.employer\r\n"
		  		+ "INNER JOIN (SELECT account.ID,account.AccountNum, account.FirstName,account.LastName,businessaccount.EmployerNum,businessaccount.IsApproved\r\n"
		  		+ "	FROM biteme.account\r\n"
		  		+ "	INNER JOIN biteme.businessaccount\r\n"
		  		+ "	ON biteme.businessaccount.AccountNum=account.AccountNum\r\n"
		  		+ ")As AccountWithBusiness\r\n"
		  		+ "ON employer.EmployerNum=AccountWithBusiness.EmployerNum\r\n"
		  		+ "WHERE AccountWithBusiness.IsApproved=0 AND employer.IsApproved=1");
		  usersToApprove.add("Approve Business");
			while(rs.next())
			{
				System.out.println("Business User to approve found Found");
				usersToApprove.add(rs.getString(1)+"~"+rs.getString(2)+"~"+rs.getString(3)+"~"+rs.getString(4)+"~"+rs.getString(6));
				db.sendToClient(usersToApprove,client);
			}
			rs.close();
			stmt.close();
	  }
	/**
	Func for getting a specific business account details 
	* @param res  res[0] used to start function, rest of res for details we need for queries, res[0]="Load business Account",res[1]=AccountNum
	 * @param client The connection from which the message originated.
	 * @param myCon the connection to mySql DB
	 * @param db the main database controller used in order to send message back to client */
	  	protected void loadSpecificBusinessAccount(String[] res, ConnectionToClient client,Connection myCon,DBController db) {
		Statement stmt;
		ResultSet rs;
		String result;
		try {
			stmt = myCon.createStatement();
			rs = stmt.executeQuery(String.format("SELECT x.AccountNum,account.FirstName,account.LastName,account.ID,account.Telephone,account.Email,x.EmployerNum,x.MonthlyLimit,x.IsApproved,x.Name\r\n"
					+ "FROM (SELECT myAcconut.*,employer.name\r\n"
					+ "	FROM (SELECT * FROM biteme.businessaccount where AccountNum='%d') as myAcconut\r\n"
					+ "	INNER JOIN employer ON myAcconut.EmployerNum=employer.EmployerNum) as x\r\n"
					+ "INNER JOIN account ON x.AccountNum=account.AccountNum;",Integer.parseInt(res[1])));
			if (rs.next()) {
				System.out.println("account found");
				result = "business account load~" + rs.getString(1) + "~" + rs.getString(2) + "~" + rs.getString(3) + "~"
						+ rs.getString(4)+"~" + rs.getString(5)+"~" + rs.getString(6)+"~" + rs.getString(7)+ "~" + rs.getString(8)+ "~" + rs.getString(9)+"~" + rs.getString(10);
				db.sendToClient(result, client);
			} else
				db.sendToClient("business account load~account Wasnt found", client);
			rs.close();
			stmt.close();
		} catch (Exception e) {

		}
	}
	
	/**
	Func for loading all eployers in specific branch
	* @param res  res[0] used to start function, rest of res for details we need for queries, res[0]="Load_myEmployers",res[1]=BranchID
	 * @param client The connection from which the message originated.
	 * @param myCon the connection to mySql DB
	 * @param db the main database controller used in order to send message back to client */
		protected void loadMyEmployers(String[]res,ConnectionToClient  client,Connection myCon,DBController db)
		{
		  Statement stmt;
		  ResultSet rs;
		
		  try {
		  stmt = myCon.createStatement();
		  rs =stmt.executeQuery("SELECT EmployerNum,Name,IsApproved,Address,Telephone FROM biteme.employer WHERE BranchID="+res[1]);
		  ArrayList<String> myEmployers=new ArrayList<>();
		  myEmployers.add("load my employer");
		  while(rs.next())
		  {			  
			  int emplyerNum=rs.getInt(1);
			  String name=rs.getString(2);
			  int isApproved=rs.getInt(3);    
			  String address=rs.getString(4);
			  String telephone=rs.getString(5);
			  String temp=emplyerNum+"~"+name+"~"+address+"~"+telephone+"~"+isApproved;
			  myEmployers.add(temp);		 	
		  }
	
		  db.sendToClient(myEmployers,client);
			rs.close();
			stmt.close();
		  }
		  catch (Exception e) {
			System.out.println(e);
		}
	}


	  	/**
		Func for approving new employer 
		* @param res  res[0] used to start function, rest of res for details we need for queries,res[0]=Employer_approved res[1]=1 res[2] =employer num
		 * @param client The connection from which the message originated.
		 * @param myCon the connection to mySql DB
		 * @param db the main database controller used in order to send message back to client */
	  	protected void approveEmployer(String[]res,ConnectionToClient  client,Connection myCon,DBController db)
	  	{
	  		  Statement stmt;
	  		  int flag;
	  		  try {
	  		  stmt = myCon.createStatement();
	  		  flag =stmt.executeUpdate(String.format("UPDATE biteme.employer SET IsApproved = %d WHERE EmployerNum = %d;",Integer.parseInt(res[1]) ,Integer.parseInt(res[2])));
	  		  db.sendToClient("Order_arrived~Updatet Successfully",client);
	  			stmt.close();
	  		  }
	  		  catch (Exception e) {
	  			
	  		}

		}
		
		/**
		Func for cheking if order is approved
		* @param res  res[0] used to start function, rest of res for details we need for queries,res[0]=Check_approved res[1]=OrderID
		 * @param client The connection from which the message originated.
		 * @param myCon the connection to mySql DB
		 * @param db the main database controller used in order to send message back to client */
		 protected void CheckApproved(String []res,ConnectionToClient client,Connection myCon,DBController db) throws SQLException{
		  Statement stmt;
		  stmt = myCon.createStatement();
		  ResultSet rs;
		  String result;
		  rs=stmt.executeQuery(String.format("SELECT IsApproved FROM biteme.order WHERE OrderID='%d'",Integer.parseInt(res[1])));
		  try {
			  if(rs.next())
				{
					System.out.println("Order found"); 
					result= "Check Approved Order~"+res[1]+"~"+rs.getInt(1);
					db.sendToClient(result,client);
				} 
				else db.sendToClient("Check Approved Order~Order Wasnt found", client);
				rs.close();
				stmt.close();
		  }catch(Exception e) {};
		 }
		 
		 /**
			Func for approving a business account
			* @param res  res[0] used to start function, rest of res for details we need for queries,res[0]=Approve_account res[1]=AccountNum
			 * @param client The connection from which the message originated.
			 * @param myCon the connection to mySql DB
			 * @param db the main database controller used in order to send message back to client */
			 protected void ApproveAccount(String []res,ConnectionToClient client,Connection myCon,DBController db) throws SQLException{
				 Statement stmt;
		  		  int flag;
		  		  try {
		  		  stmt = myCon.createStatement();
		  		  flag =stmt.executeUpdate(String.format("UPDATE biteme.businessaccount SET IsApproved = '%d' WHERE AccountNum = '%d';",1 ,Integer.parseInt(res[1])));
		  		  System.out.println("Account approved");
		  		  db.sendToClient("AccountApproved~Updatet Successfully",client);
		  			stmt.close();
		  		  }
		  		  catch (Exception e) {
		  			
		  		}

			 }
	  
		
		
}
