package orderpackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/biteme?serverTimezone=IST","root","Aa123456");
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
	
	public static void getOrder(Connection con , int orderID)
	{
		Statement stmt;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM biteme.order WHERE OrderNumber="+orderID);
	 		while(rs.next())
	 		{
				 // Print out the values
				 System.out.println(rs.getString(1)+"  " +rs.getString(2));
			} 
			rs.close();
			//stmt.executeUpdate("UPDATE course SET semestr=\"W08\" WHERE num=61309");
		} catch (SQLException e) {e.printStackTrace();}
	}

	
	public static void insertOrder(Connection con1 , String restaurant, String order_num, String order_time, String phone_num, String type_of_order, String order_address){
		Statement stmt;
		try {
			stmt = con1.createStatement();
			stmt.executeUpdate("INSERT INTO biteme.order (Restaurant, OrderNumber, OrderTime, PhoneNumber, TypeOfOrder, OrderAddress) VALUES ('HFA', 1, '14:21', '052-7281546', 'take-away', 'braude karmiel');");
			//stmt.executeUpdate("load data local infile \"courses.txt\" into table courses");
	 		
		} catch (SQLException e) {	e.printStackTrace();}
		 		
	}
	
	
	
}


