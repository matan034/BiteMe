package server;

import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entity.Branch;
import entity.Customer;
import entity.Dish;
import entity.Order;
import entity.User;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;


public class BiteMeServer extends AbstractServer
{
	private static Connection myCon;
	//private static BiteMeServer single_instance = null;
	

	public BiteMeServer(int port) {
		super(port);
		// TODO Auto-generated constructor stub
	}
	public static String connectToDB(String ip, String port, String db_name, String db_user, String db_password)
	{

		try 
		{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //res += "Driver definition succeed ";
        } catch (Exception ex) {
        	/* handle the error*/
        	System.out.println( "Driver definition failed");
        	 return "Server Login Failed";
        	 }
        try 
        {
            myCon = DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db_name+"?serverTimezone=IST&useSSL=false",db_user,db_password);
           return "SQL connection succeed";
     	} catch (SQLException ex) 
     	    {/* handle any errors*/
             return "SQLException: " + ex.getMessage();
            }
   	}
	public static String disconnectDB()
	{
		try {
		myCon.close();
		return "SQL Disconnected Successfuly";
		}
		catch(Exception e) {return "Couldn't disconnect from SQL";}
	}
	
	
	@Override
	/**
	   * This method handles any messages received from the client.
	   *
	   * @param msg The message received from the client.
	   * @param client The connection from which the message originated.
	   * @param 
	   */
	  public void handleMessageFromClient  (Object msg, ConnectionToClient client)
	  {
		    System.out.println("Message received: " + msg + " from " + client); 
		    String [] res = ((String)msg).split("~");
		    String result="";
			try {
				switch(res[0])
				{
				case "Insert_order": insertOrder(res, client); break;
				case "Update_order": updateOrder(res, client); break;
				case "Search_order": searchOrder(res, client); break;
				case "Get_connection": getClientInfo(client); break;

				case "Load_users": loadUsers(res,client); break;
				case "User_login":userLogin(res,client);break;
				case "Business_account":privateOrBusinessAccountReg(res,client);break;
				case "Private_account":privateOrBusinessAccountReg(res,client);break;
				case "Check_account_input":checkAccountInput(res,client);break;
				case "Check_employer":checkEmployer(res,client);break;
				case "Reg_employer":regEmployer(res,client);break;
				case "Update_user":updateUser(res,client);break;
				case "Import_users":importUsers(res,client);break;

				case "Load_orders": loadOrders(res,client); break;
				case "W4C_verify": w4cVerify(res,client);break;
				case "W4C_load_list": w4cLoadList(res,client);break;
				case "Load_dishes": loadDishes(res,client);break;
				case "Load_customer": loadCustomer(res,client);break;
				case "Load_branches": loadBranches(res,client);break;
				case "Add_dishInOrder":addDishInOrder(res,client);break;
				}
	

			} catch (SQLException e) {	e.printStackTrace();}
			 		
	  }
	
	/**
	   * This method overrides the one in the superclass.  Called
	   * when the server starts listening for connections.
	   */
	  protected void serverStarted()
	  {
	    System.out.println ("Server listening for connections on port " + getPort());

	  }
	  /**
	   * This method overrides the one in the superclass.  Called
	   * when the server stops listening for connections.
	   */
	  protected void serverStopped()  {
		
	    System.out.println ("Server has stopped listening for connections.");
	    
	    System.out.println( disconnectDB());
	  }  
	  
	  
	  protected void serverClosed() {
		
	  }
	  
	  protected void sendToClient(Object msg,ConnectionToClient client)
	  {
		    Thread[] clientThreadList = getClientConnections();

		    for (int i=0; i<clientThreadList.length; i++)
		    {
		      try
		      {
		    	  if((ConnectionToClient)clientThreadList[i]==client)
		        ((ConnectionToClient)clientThreadList[i]).sendToClient(msg);
		      }
		      catch (Exception ex) {System.out.println(ex);}
		    }
		  }
	  
	  protected void loadOrders(String []res,ConnectionToClient client) throws SQLException{
		  Statement stmt;
		  stmt = myCon.createStatement();
		  String result="Load Orders~";
		  ArrayList <Order> orders=new ArrayList<>();
		  int flag;
		  ResultSet rs;
		  flag=stmt.executeUpdate("CREATE TEMPORARY TABLE biteme.DishesInOrders \r\n"
		  		+ "SELECT * \r\n"
		  		+ "FROM (\r\n"
		  		+ "	SELECT biteme.dishes.DishName,DishesByOrderNumber.DishID,DishesByOrderNumber.OrderNumber\r\n"
		  		+ "	FROM biteme.dishes\r\n"
		  		+ "	INNER JOIN (SELECT dishinorder.DishID,dishinorder.OrderNumber\r\n"
		  		+ "	FROM biteme.dishinorder\r\n"
		  		+ "		INNER JOIN (SELECT biteme.order.OrderID, biteme.order.CustomerNumber\r\n"
		  		+ "					FROM biteme.order\r\n"
		  		+ "					WHERE biteme.order.ResturantNumber=1)AS OrderByRestaurant\r\n"
		  		+ "	ON OrderNumber=OrderID)AS DishesByOrderNumber\r\n"
		  		+ "	WHERE dishes.DishID=DishesByOrderNumber.DishID)As x;");
		  flag=stmt.executeUpdate("CREATE TEMPORARY TABLE biteme.CustomersInOrders \r\n"
		  		+ "SELECT * \r\n"
		  		+ "FROM(\r\n"
		  		+ "SELECT account.FirstName, account.LastName,CustomerOrderJoin.SupplyWay, CustomerOrderJoin.RequestOrderTime,CustomerOrderJoin.OrderID\r\n"
		  		+ "FROM biteme.account\r\n"
		  		+ "INNER JOIN (SELECT biteme.order.SupplyWay, biteme.order.RequestOrderTime, customers.AccountNum,biteme.order.OrderID\r\n"
		  		+ "	FROM biteme.order\r\n"
		  		+ "	INNER JOIN biteme.customers\r\n"
		  		+ "	ON biteme.order.CustomerNumber=customers.CustomerNumber)AS CustomerOrderJoin\r\n"
		  		+ "ON account.AccountNum=CustomerOrderJoin.AccountNum)AS y;");
		  
		  rs=stmt.executeQuery("SELECT DishesInOrders.DishName, CustomersInOrders.FirstName,CustomersInOrders.LastName,CustomersInOrders.SupplyWay,CustomersInOrders.RequestOrderTime\r\n"
		  		+ "FROM biteme.DishesInOrders\r\n"
		  		+ "INNER JOIN biteme.CustomersInOrders\r\n"
		  		+ "ON CustomersInOrders.OrderID=DishesInOrders.OrderNumber;");
		  while(rs.next())
		  {
			  Order new_order=new Order();
			  new_order.setDish_name(rs.getString(1));
			  new_order.setRecieving_name(rs.getString(2)+" "+ rs.getString(3));
			  new_order.setOrder_type(rs.getString(4));
			  new_order.setOrder_time(rs.getString(5));
			  orders.add(new_order);
		  }
		  
		  sendToClient(orders,client);
		  rs.close();
		  stmt.close();
		  
	
		  
		 
	  }
	  
	  
	  protected void importUsers(String []res,ConnectionToClient client) throws SQLException{
		  Statement stmt;
		  stmt = myCon.createStatement();
		  int flag;
		  flag=stmt.executeUpdate(String.format("INSERT INTO biteme.users (ID, FirstName, LastName,Email,Phone,Type,UserName,Password,IsLoggedIn,Status) VALUES ('%s', '%s', '%s','%s','%s','%s','%s','%s','%d','%s');",res[1],res[2],res[3],res[4],res[5],res[6],res[7],res[8],Integer.parseInt(res[9]),res[10]));
			if(flag>0) sendToClient("User import~Users Imported", client);
			else sendToClient("User import ~Error importing users", client);
		  stmt.close();
	  }
	  
	  protected void updateUser(String []res,ConnectionToClient client) throws SQLException{
		  Statement stmt;
		  int flag;
			try {
				stmt = myCon.createStatement();
				flag=stmt.executeUpdate(String.format("UPDATE biteme.users SET Status = '%s' WHERE ID = %s;",res[1],res[2]));
				if(flag>0)	sendToClient("Update~Updated Successfuly", client);
				else sendToClient("Update~Failed to update", client);
				stmt.close();	
			} catch (SQLException e) {	e.printStackTrace();}
	  }
	  
	  protected void regEmployer(String []res,ConnectionToClient client) throws SQLException{
		  Statement stmt;
		  stmt = myCon.createStatement();
		  int flag;
		  flag=stmt.executeUpdate(String.format("INSERT INTO biteme.employer (Name, Address, Telephone) VALUES ('%s', '%s', '%s');",res[1],res[2],res[3]));
			if(flag>0) sendToClient("Employer Register~Employer Has Been Registered", client);
			else sendToClient("Employer Register ~Error Registering Employer", client);
		  stmt.close();
	  }
	  
	  protected void checkEmployer(String []res,ConnectionToClient client) throws SQLException{
		  Statement stmt;
		  stmt = myCon.createStatement();
		  String result="Check Employer Input~";
		  ResultSet rs;
		  rs =stmt.executeQuery(String.format("SELECT * FROM biteme.employer WHERE Name='%s'",res[1]));
		  if (rs.isBeforeFirst()) {//check if we got a result
			  result+="Name~";
			  rs =stmt.executeQuery(String.format("SELECT * FROM biteme.employer WHERE Name='%s' AND  IsApproved=%d",res[1],1));
			  if (rs.isBeforeFirst()) {//check if we got a result
				  result+="Approved~";
			  }
			  else
				  result+="NoApproved~";
		  }
		  else {
			  result+="NoName~";
			  result+="NoApproved~";
		  }
			 
		  
		
		  sendToClient(result,client);
		  rs.close();
		  stmt.close();
	  }
	  
	  protected void checkAccountInput(String []res,ConnectionToClient client) throws SQLException{
		  Statement stmt;
		  stmt = myCon.createStatement();
		  String result="Check Account Input~";
		  ResultSet rs;
		  rs =stmt.executeQuery(String.format("SELECT * FROM biteme.account WHERE ID='%s'",res[3]));
		  if (rs.isBeforeFirst()) {//check if we got a result
			  result+="ID~";
		  }
		  else
			  result+="NoIDError~";
		  rs =stmt.executeQuery(String.format("SELECT * FROM biteme.account WHERE Telephone='%s'",res[4]));
		  if (rs.isBeforeFirst()) {//check if we got a result
			  result+="Telephone~";
		  }
		  else
			  result+="NoTelephoneError~";
		  rs =stmt.executeQuery(String.format("SELECT * FROM biteme.account WHERE Email='%s'",res[5]));
		  if (rs.isBeforeFirst()) {//check if we got a result
			  result+="Email";
		  }
		  else
			  result+="NoEmailError";
		  sendToClient(result,client);
		  rs.close();
		  stmt.close();
		  
	  }
	  protected int accountReg(String []res,ConnectionToClient client) throws SQLException{
		  Statement stmt;
		  stmt = myCon.createStatement();
		  int flag;
		  flag=stmt.executeUpdate(String.format("INSERT INTO biteme.account (FirstName, LastName,ID, Telephone,Email) VALUES ('%s', '%s', '%s', '%s', '%s');",res[1],res[2],res[3],res[4],res[5]));
			if(flag>0) {stmt.close();return 1;}
			else {stmt.close();return 0;}
	  }
	  
	  protected void privateOrBusinessAccountReg(String []res,ConnectionToClient client) throws SQLException{
		  Statement stmt;
		  stmt = myCon.createStatement();
		  int flagAccountReg=1,flagReg=0;
		  ResultSet rs;
		  rs =stmt.executeQuery(String.format("SELECT * FROM biteme.account WHERE ID='%s'",res[3]));
		  if (!rs.isBeforeFirst()) {//check if we need to create an account first
			  flagAccountReg=accountReg(res,client);
		  }  
		 
		  if(flagAccountReg==1)//account created successfully
		  {
			  if(res[0].equals("Private_account")) {
			  flagReg=stmt.executeUpdate(String.format("INSERT INTO biteme.privateaccount (AccountNum, CreditCardNumber) VALUES ((SELECT AccountNum from biteme.account WHERE ID='%s'),'%s');"
					  							,res[3],res[6]));}
			  else//business_account
				  flagReg=stmt.executeUpdate(String.format("INSERT INTO biteme.businessaccount (AccountNum, EmployerNum, MonthlyLimit) VALUES ((SELECT AccountNum from biteme.account WHERE ID='%s'),(SELECT EmployerNum from biteme.employer WHERE Name='%s'), '%d');"
							,res[3],res[6],Integer.parseInt(res[7])));
			 
			  if(flagReg>0) {
				  sendToClient("New Account~Created Succesfully",client);
			  }
			  else sendToClient("New Account~Failed business account creation", client);
		  }
		  else sendToClient("New Account~Failed new account creation", client);
		  rs.close();
		  stmt.close();
	  }

	  protected void userLogin(String []res,ConnectionToClient client) throws SQLException{
		  String result;
		Statement stmt = myCon.createStatement(
                  ResultSet.TYPE_SCROLL_INSENSITIVE,
                  ResultSet.CONCUR_UPDATABLE);
		  //Statement stmt = myCon.createStatement();
		  ResultSet rs;
		  rs =stmt.executeQuery(String.format("SELECT * FROM biteme.users WHERE UserName='%s' AND Password='%s'",res[1],res[2]));
			if(rs.next())
			{
				System.out.println("User found:logging in");
				rs.updateInt("IsLoggedIn",1);
				rs.updateRow();
				result= "User login~"+rs.getString(2)+"~"+rs.getString(3)+"~"+rs.getString(6);
				sendToClient(result,client);
			} 
			else sendToClient("User login~User not Found", client);
			rs.close();
			stmt.close();
	  }
	  
	  
	  protected void insertOrder(String[]res,ConnectionToClient client) throws SQLException
	  {
		  Statement stmt;
		  ResultSet rs;
		  stmt = myCon.createStatement();
		  int flag,branch_id,customer_num,isEarlyOrder;
		  String supply_way,Delivery_type,requested_order_time,delivery_query,takeAway_query,recieverName,recieverPhone,businessName,street,city,zip;
		  PreparedStatement preparedStmt;
		  delivery_query = "INSERT INTO biteme.order (BranchID, CustomerNumber, SupplyWay, DeliveryType,IsEarlyOrder,RequestOrderTime,RecieverName,BusinessName,RecieverPhone,DeliveryStreet,DeliveryCity,DeliveryZip) "
		  		+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
		  takeAway_query="INSERT INTO biteme.order (BranchID, CustomerNumber, SupplyWay,IsEarlyOrder,RequestOrderTime)  VALUES (?,?, ?, ?,?);";
		  try {
		  branch_id=Integer.parseInt(res[1]);
		  customer_num=Integer.parseInt(res[2]);
		  supply_way=res[3];
		  isEarlyOrder=Integer.parseInt(res[4]);
		  requested_order_time=res[5];
		  if(supply_way.equals("Delivery"))
		  {
			  Delivery_type=res[6];
			  recieverName=res[7];  
			  businessName=res[8];
			  recieverPhone=res[9];
			  street=res[10];
			  city=res[11];
			  zip=res[12];
			  // create the mysql insert preparedstatement	  
		      preparedStmt = myCon.prepareStatement(delivery_query);
		      preparedStmt.setInt (1,branch_id);
		      preparedStmt.setInt (2,customer_num);
		      preparedStmt.setString (3,supply_way);
		      preparedStmt.setString (4,Delivery_type);
		      preparedStmt.setInt (5,isEarlyOrder);
		      preparedStmt.setString (6,requested_order_time);
		      preparedStmt.setString (7,recieverName);
		      preparedStmt.setString (8,businessName);
		      preparedStmt.setString (9,recieverPhone);
		      preparedStmt.setString (10,street);
		      preparedStmt.setString (11,city);
		      preparedStmt.setString (12,zip);
		      preparedStmt.execute();		
		  }
		  else
			  {
			  preparedStmt = myCon.prepareStatement(takeAway_query);
		      preparedStmt.setInt (1,branch_id);
		      preparedStmt.setInt (2,customer_num);
		      preparedStmt.setString (3,supply_way);
		      preparedStmt.setInt (4,isEarlyOrder);
		      preparedStmt.setString (5,requested_order_time);
		      preparedStmt.execute();			
			  }
		  rs=stmt.executeQuery("SELECT last_insert_id()");//get new order id
			if(rs.next()) 
				sendToClient("Insert~"+rs.getString(1), client);
			
			else sendToClient("Insert~Error saving your order", client);
		  }
		  catch (Exception e) {
			  System.out.println(e);
			  }
		  stmt.close();
	  }
	  protected void updateOrder(String[]res,ConnectionToClient client) throws SQLException
	  {
		  Statement stmt;
		  int flag;
			try {
				stmt = myCon.createStatement();
				flag=stmt.executeUpdate(String.format("UPDATE biteme.order SET %s = '%s', %s = '%s' WHERE OrderNumber = %d;",res[1],res[2],res[3],res[4],Integer.parseInt(res[5])));
				if(flag>0)	sendToClient("Update~Updated Successfuly", client);
				else sendToClient("Update~Failed to update", client);
				stmt.close();	
			} catch (SQLException e) {	e.printStackTrace();}
			
	  }
	  
	  protected void searchOrder(String[]res,ConnectionToClient client) throws SQLException
	  {
		  String result;
		  Statement stmt;
		  stmt = myCon.createStatement();
		  ResultSet rs;
		  rs =stmt.executeQuery("SELECT * FROM biteme.order WHERE OrderNumber="+res[1]);
			if(rs.next())
			{
				System.out.println("Order Found");
				result= "Search~"+rs.getString(1)+"~"+rs.getString(2)+"~"+rs.getString(3)+"~"+rs.getString(4)+"~"+rs.getString(5)+"~"+rs.getString(6);
				sendToClient(result,client);
			} 
			else sendToClient("Search~Order Wasnt found", client);
			rs.close();
			stmt.close();
	  }
	  


	  
	  protected void getClientInfo(ConnectionToClient client)
	  {
		  Thread[] clientThreadList = getClientConnections();
		  String connectionflag;
		    for (int i=0; i<clientThreadList.length; i++)
		    {
		      try
		      {
		    	  if((ConnectionToClient)clientThreadList[i]==client)
		    	  {
		    		  Socket s =((ConnectionToClient)clientThreadList[i]).getClientSocket();
		    		  if(s.isConnected()) {
		    			  connectionflag="Client online";
		    		  }
		    		  else
		    			  connectionflag="Client offline";
		    		  
		    		  String msg=s.getLocalAddress()+"~Host name: "+s.getInetAddress().getLocalHost().getCanonicalHostName()+"~Connection: "+connectionflag;
		    		  msg=msg.substring(1);
		    		  msg="IP~Ip Adress: "+msg;
		    		  ((ConnectionToClient)clientThreadList[i]).sendToClient(msg);
		    	  }
		    	  
		       
		      }
		      catch (Exception ex) {System.out.println(ex);}
		    }
	  }
	  protected void loadUsers(String[]res,ConnectionToClient client) throws SQLException
	  {
		  Statement stmt;
		  stmt = myCon.createStatement();
		  ResultSet rs;
		  rs =stmt.executeQuery("SELECT * FROM biteme.users");
			ArrayList<User> all_users = new ArrayList<>();
			while(rs.next())
			{

				all_users.add(new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(10)));
		
			}
			sendToClient(all_users,client);
			stmt.close();
			rs.close();
	  }
	  
	  protected void w4cVerify(String[]res,ConnectionToClient client) 
	  {
		  String result;
		  Statement stmt;
		  ResultSet rs;
		  try {
		  stmt = myCon.createStatement();
		  
		  rs =stmt.executeQuery("SELECT * FROM biteme.account WHERE W4C="+res[1]);
			if(rs.next())
			{
				System.out.println("card found"); 
				result= "W4C verify~"+rs.getString(1)+"~"+rs.getString(2)+"~"+rs.getString(3)+"~"+rs.getString(4)+"~"+rs.getString(5)+"~"+rs.getString(6)+"~"+rs.getString(7)+"~"+rs.getString(8);
				sendToClient(result,client);
			} 
			else sendToClient("W4C verify~W4C Wasnt found", client);
			rs.close();
			stmt.close();
		  }catch (Exception e) {
			// TODO: handle exception
		}
	  }
	  protected void w4cLoadList(String[]res,ConnectionToClient client) 
	  {
		  String result="W4C_load_list";
		  Statement stmt;
		  ResultSet rs;
		  try {
		  stmt = myCon.createStatement();		  
		  rs =stmt.executeQuery("SELECT W4C FROM biteme.account");
			while(rs.next())
			{
				result+="~"+rs.getString(1);
				
			} 
			sendToClient(result,client);
			rs.close();
			stmt.close();
		  }catch (Exception e) {
			// TODO: handle exception
		}
	  }
	  protected void loadDishes(String[]res,ConnectionToClient  client)
	  {
		  
		  Statement stmt;
		  ResultSet rs;
		  ArrayList<Dish>dishes=new ArrayList<>();
		  try {
		  stmt = myCon.createStatement();
		  rs =stmt.executeQuery("SELECT *\r\n"
		  		+ "FROM biteme.dishes\r\n"
		  		+ "INNER JOIN \r\n"
		  		+ "(\r\n"
		  		+ "SELECT DishID FROM biteme.dishinmenu WHERE MenuID=\r\n"
		  		+ "(SELECT MenuID FROM biteme.menu WHERE BranchID=\r\n"
		  		+ "(SELECT BranchID FROM biteme.branch WHERE BranchName=\""+res[1] +"\"))\r\n"
		  		+ ") as x\r\n"
		  		+ " ON biteme.dishes.dishID=x.DishID");

		  	  while(rs.next())
		  	  {			  
		  		  	
					int dishID=rs.getInt(1);
					String DishName=rs.getString(2);
					String DishType=rs.getString(3);
					Double price=rs.getDouble(4);
					int chooseSize=rs.getInt(5);
					int chooseCookLvl=rs.getInt(6);
					int chooseExtras=rs.getInt(7);
					String imgSrc=rs.getString(8);
					Dish newDish=new Dish(dishID, chooseSize, chooseCookLvl, chooseExtras, price, DishName, DishType, imgSrc);	  
					dishes.add(newDish);		 	
		  	  }
		  
		  	sendToClient(dishes,client);
			stmt.close();
			rs.close();


		  }
		  catch (Exception e) {
			// TODO: handle exception
			  sendToClient("Cant Load Menu "+e,client);
		}
	  }
	  protected void loadCustomer(String[]res,ConnectionToClient  client)
	  {
		  Statement stmt;
		  ResultSet rs;
		  String result;
		  try {
		  stmt = myCon.createStatement();
		  rs =stmt.executeQuery("SELECT * FROM biteme.customers WHERE AccountNum="+Integer.parseInt(res[1]));
		  if(rs.next())
			{
				System.out.println("customer found"); 
				result= "Customer load~"+rs.getString(1)+"~"+rs.getString(2)+"~"+rs.getString(3)+"~"+rs.getString(4);
				sendToClient(result,client);
			} 
			else sendToClient("Customer load~Customer Wasnt found", client);
			rs.close();
			stmt.close();
		  }
		  catch (Exception e) {
			
		}
	  }
	  protected void loadBranches(String[]res,ConnectionToClient  client)
	  {
		  Statement stmt;
		  ResultSet rs;
		
		  try {
		  stmt = myCon.createStatement();
		  rs =stmt.executeQuery("SELECT * FROM biteme.branch");
		  ArrayList<Branch> branches=new ArrayList<>();
		  while(rs.next())
	  	  {			  
				  int branchID=rs.getInt(1);
				  String branchName=rs.getString(2);
				  branches.add(new Branch(branchID,branchName));		 	
	  	  }
	  
		  sendToClient(branches,client);
			rs.close();
			stmt.close();
		  }
		  catch (Exception e) {
			
		}
	  }
	  protected void addDishInOrder(String[]res,ConnectionToClient  client)
	  {
		  Statement stmt;
		  int flag;
		  try {
		  stmt = myCon.createStatement();
		  flag=stmt.executeUpdate(String.format("INSERT INTO biteme.dishinorder (DishID, OrderNumber, Size, CookingLevel,Extras)  VALUES ('%d','%d', '%s', '%s','%s')",Integer.parseInt(res[1]),Integer.parseInt(res[2]),res[3],res[4],res[5]));
		  if(flag==1) sendToClient("Insert dishinorder~Inserted successfuly",client);
		  else sendToClient("Insert dishinorder~Insert failed", client);
			stmt.close();
		  }
		  catch (Exception e) {
			
		}
	  }
}


