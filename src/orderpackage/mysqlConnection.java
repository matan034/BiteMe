package orderpackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class mysqlConnection {

	public static Connection connectToDB()
	{
		try 
		{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("Driver definition succeed");
        } catch (Exception ex) {
        	/* handle the error*/
        	 System.out.println("Driver definition failed");
        	 }
        
        try 
        {
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/biteme?serverTimezone=IST","root","M220794w!");
            System.out.println("SQL connection succeed");
            return conn;

     	} catch (SQLException ex) 
     	    {/* handle any errors*/
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return null;
            }
   	}
	
	public static ResultSet getOrder(Connection con , int orderID)
	{
		Statement stmt;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM biteme.order WHERE OrderNumber="+orderID);
	 		if(rs.next())
	 		{
				 return rs;
			} 
			rs.close();
			return null;
			
		} catch (SQLException e) {return null;}
	}

	
	public static void insertOrder(Connection con1 , String restaurant, String phone_num, String type_of_order, String order_address){
		Statement stmt;
		try {
			stmt = con1.createStatement();
			stmt.executeUpdate(String.format("INSERT INTO biteme.order (Restaurant, PhoneNumber, TypeOfOrder, OrderAddress) VALUES ('%s', '%s', '%s', '%s');",restaurant,phone_num,type_of_order,order_address));
	 		
		} catch (SQLException e) {	e.printStackTrace();}
		 		
	}
	
	public static void update(Connection con, String field, String val, int order_num)
	{
		Statement stmt;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(String.format("UPDATE biteme.order SET %s = '%s' WHERE OrderNumber = %d;",field,val,order_num));
	 		
		} catch (SQLException e) {	e.printStackTrace();}
	}
	
	
}


