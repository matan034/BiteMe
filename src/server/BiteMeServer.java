package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;


public class BiteMeServer extends AbstractServer
{
	private static Connection myCon;
	private static BiteMeServer single_instance = null;
	

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
            myCon = DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db_name+"?serverTimezone=IST",db_user,db_password);
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
		single_instance = null;
		return "Disconnected Successfuly";
		}
		catch(Exception e) {return "Couldn't disconnect from server";}
	}
	
	

	
	public static void update( String field, String val, int order_num)
	{
		Statement stmt;
		try {
			stmt = myCon.createStatement();
			stmt.executeUpdate(String.format("UPDATE biteme.order SET %s = '%s' WHERE OrderNumber = %d;",field,val,order_num));
	 		
		} catch (SQLException e) {	e.printStackTrace();}
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
		    String [] res = ((String)msg).split(" ");
		    int flag;
		    Statement stmt;
		    String result="";
			try {
				ResultSet rs;
				stmt = myCon.createStatement();
				if(res[0].equals("Insert_order"))
				{
					flag=stmt.executeUpdate(String.format("INSERT INTO biteme.order (Restaurant, PhoneNumber, TypeOfOrder, OrderAddress) VALUES ('%s', '%s', '%s', '%s');",res[1],res[2],res[3],res[4]));
					if(flag>0) sendToClient("Your Order Has Been Registered", client);
					else sendToClient("Error saving your order", client);
				}
				if(res[0].equals("Update_order"))
		 		{
		 			//need to implement
		 		}
		 		if(res[0].equals("Search_order"))
		 		{
		 			rs =stmt.executeQuery("SELECT * FROM biteme.order WHERE OrderNumber="+res[1]);
		 			if(rs.next())
		 			{
		 				System.out.println("Order Found");
		 				result= rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5);
		 				sendToClient(result,client);
		 			} 
		 			else sendToClient("Order Wasnt found", client);
		 			rs.close();
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
	
}


