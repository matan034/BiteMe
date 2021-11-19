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
	
	public static boolean createInstance(int port)
    {
        if (single_instance == null)
        {
            single_instance = new BiteMeServer(port);
            return true;
        } 
        return false;
    }
	public BiteMeServer(int port) {
		super(port);
		// TODO Auto-generated constructor stub
	}
	public static String connectToDB(String ip, String port, String db_name, String db_user, String db_password)
	{
		if(!createInstance(Integer.parseInt(port)))
		{
			return "Server is already connected";
		}
		
		String res="";
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
        	
            Connection conn = DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db_name+"?serverTimezone=IST",db_user,db_password);
            myCon = conn;
           res += "SQL connection succeed";
           return res;

     	} catch (SQLException ex) 
     	    {/* handle any errors*/
             res+= "SQLException: " + ex.getMessage();
             return res;
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
	
	public static ResultSet getOrder( int orderID)
	{
		Statement stmt;
		try 
		{
			stmt = myCon.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM biteme.order WHERE OrderNumber="+orderID);
	 		if(rs.next())
	 		{
				 return rs;
			} 
			rs.close();
			return null;
			
		} catch (SQLException e) {return null;}
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
		    Statement stmt;
			try {
				ResultSet rs;
				stmt = myCon.createStatement();
				if(res[0]=="Insert_order")
					stmt.executeUpdate(String.format("INSERT INTO biteme.order (Restaurant, PhoneNumber, TypeOfOrder, OrderAddress) VALUES ('%s', '%s', '%s', '%s');",res[1],res[2],res[3],res[4]));
		 		if(res[0]=="Update_order")
		 			stmt.executeUpdate(String.format("INSERT INTO biteme.order (Restaurant, PhoneNumber, TypeOfOrder, OrderAddress) VALUES ('%s', '%s', '%s', '%s');",res[1]));
		 		if(res[0]=="Search_order")
		 		{
		 			rs =stmt.executeQuery("SELECT * FROM biteme.order WHERE OrderNumber="+res[1]);
		 			if(rs.next())
		 			{
		 				System.out.println("Order Found");
		 				this.sendToAllClients(rs);
		 			} 
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
	
}


