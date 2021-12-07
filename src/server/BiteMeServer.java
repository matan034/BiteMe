package server;

import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entity.Branch;
import entity.Dish;
import entity.Order;
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
				case "Load_orders": loadOrders(res,client); break;
				case "W4C_verify": w4cVerify(res,client);break;
				case "Load_dishes": loadDishes(res,client);break;
				case "Load_customer": loadCustomer(res,client);break;
				case "Load_branches": loadBranches(res,client);break;
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
	  protected void insertOrder(String[]res,ConnectionToClient client) throws SQLException
	  {
		  Statement stmt;
		  stmt = myCon.createStatement();
		  int flag,branch_id,customer_num,isEarlyOrder;
		  String supply_way,Delivery_type,requested_order_time;
		  try {
		  branch_id=Integer.parseInt(res[1]);
		  customer_num=Integer.parseInt(res[2]);
		  isEarlyOrder=Integer.parseInt(res[3]);
		  supply_way=res[5];
		  
		  requested_order_time=res[4];
		  if(supply_way.equals("Delivery"))
		  {
			  Delivery_type=res[6];
			  flag=stmt.executeUpdate(String.format("INSERT INTO biteme.order (BranchID, CustomerNumber, SupplyWay, DeliveryType,IsEarlyOrder,RequestOrderTime) OUTPUT OrderID VALUES ('%d','%d', '%s', '%s','%d','%s');",branch_id,customer_num,supply_way,Delivery_type,isEarlyOrder,requested_order_time));
		  }
		  else flag=stmt.executeUpdate(String.format("INSERT INTO biteme.order (BranchID, CustomerNumber, SupplyWay,IsEarlyOrder,RequestOrderTime) OUTPUT OrderID VALUES ('%d','%d', '%s', '%d','%s');",branch_id,customer_num,supply_way,isEarlyOrder,requested_order_time));
		  
			if(flag>0) sendToClient("Insert~Your Order Has Been Registered", client);
			
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
	  protected void loadOrders(String[]res,ConnectionToClient client) throws SQLException
	  {
		  Statement stmt;
		  stmt = myCon.createStatement();
		  ResultSet rs;
		  rs =stmt.executeQuery("SELECT * FROM biteme.order");
			ArrayList<Order> all_orders = new ArrayList<>();
			while(rs.next())
			{
				String resturant=rs.getString(1);
				int id=Integer.parseInt(rs.getString(2));
				String order_time=rs.getString(3);
				String phone_num=rs.getString(4);
				String type_of_order=rs.getString(5);
				String address=rs.getString(6);
				//all_orders.add(new Order(resturant,id,order_time,phone_num,type_of_order,address));
				
			}
			sendToClient(all_orders,client);
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
		  
		  rs =stmt.executeQuery("SELECT * FROM biteme.account WHERE CardNumber="+res[1]);
			if(rs.next())
			{
				System.out.println("card found"); 
				result= "W4C verify~"+rs.getString(1)+"~"+rs.getString(2)+"~"+rs.getString(3)+"~"+rs.getString(4)+"~"+rs.getString(5);
				sendToClient(result,client);
			} 
			else sendToClient("W4C verify~W4C Wasnt found", client);
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
					  int price=rs.getInt(4);
					  int chooseSize=rs.getInt(5);
					  int chooseCookLvl=rs.getInt(6);
					  int chooseExtras=rs.getInt(7);
					  dishes.add(new Dish(dishID,chooseSize,chooseCookLvl,chooseExtras,price,DishName,DishType));		 	
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
}


