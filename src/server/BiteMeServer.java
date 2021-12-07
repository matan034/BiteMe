package server;

import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
				result= "User login~"+rs.getString(2)+" "+rs.getString(3);
				sendToClient(result,client);
			} 
			else sendToClient("User login~User not Found", client);
			rs.close();
			stmt.close();
	  }
	  
	  
	  protected void insertOrder(String[]res,ConnectionToClient client) throws SQLException
	  {
		  Statement stmt;
		  stmt = myCon.createStatement();
		  int flag;
		  flag=stmt.executeUpdate(String.format("INSERT INTO biteme.order (Restaurant, PhoneNumber, TypeOfOrder, OrderAddress) VALUES ('%s', '%s', '%s', '%s');",res[1],res[2],res[3],res[4]));
			if(flag>0) sendToClient("Insert~Your Order Has Been Registered", client);
			else sendToClient("Insert~Error saving your order", client);
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
	  

	  @Override
	  protected void clientConnected(ConnectionToClient client) {
		 
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
	
}


