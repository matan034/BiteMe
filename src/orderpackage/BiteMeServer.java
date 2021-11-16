package orderpackage;

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
            res += "Driver definition succeed ";
        } catch (Exception ex) {
        	/* handle the error*/
        	res += "Driver definition failed";
        	 return res;
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

	
	public static void insertOrder( String restaurant, String phone_num, String type_of_order, String order_address){
		Statement stmt;
		try {
			stmt = myCon.createStatement();
			stmt.executeUpdate(String.format("INSERT INTO biteme.order (Restaurant, PhoneNumber, TypeOfOrder, OrderAddress) VALUES ('%s', '%s', '%s', '%s');",restaurant,phone_num,type_of_order,order_address));
	 		
		} catch (SQLException e) {	e.printStackTrace();}
		 		
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
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		// TODO Auto-generated method stub
		
	}
	
	
}


