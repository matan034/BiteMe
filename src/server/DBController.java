package server;

import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Dish;
import entity.DishInRestaurant;
import entity.MyFile;
import entity.clientData;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class DBController extends AbstractServer {
	private static Connection myCon;
	// private static BiteMeServer single_instance = null;

	private DBUserController dbUser=new DBUserController();
	private DBOrderController dbOrder=new DBOrderController();
	private DBReportController dbReport=new DBReportController();
	private DBMenuController dbMenu=new DBMenuController();
	
	public DBController(int port) {
		super(port);
	}

	public static String connectToDB(String ip, String port, String db_name, String db_user, String db_password) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			// res += "Driver definition succeed ";
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
			return "Server Login Failed";
		}
		try {
			myCon = DriverManager.getConnection(
					"jdbc:mysql://" + ip + "/" + db_name + "?serverTimezone=IST&useSSL=false", db_user, db_password);
			return "SQL connection succeed";
		} catch (SQLException ex) {/* handle any errors */
			return "SQLException: " + ex.getMessage();
		}
	}

	public static String disconnectDB() {
		try {
			myCon.close();
			return "SQL Disconnected Successfuly";
		} catch (Exception e) {
			return "Couldn't disconnect from SQL";
		}
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
		    
		    //muhammad
		    if(msg instanceof clientData)
		    {
		    	clientData message=(clientData)msg;
				switch(message.getOperation())
				{
				case "add_to_restaurant"://muhammad
					dbMenu.insertDishesToRestaurant((ArrayList<DishInRestaurant>)message.getData(),client,myCon,this);
				case "remove": dbMenu.removeDishes((ArrayList<DishInRestaurant>)message.getData(),client,myCon,this);
				}
		    }
		    if(msg instanceof MyFile) {		    	
		    		dbReport.UploadReport(msg, client, myCon, this);
		    }
		    if(msg instanceof Dish)
			{
				Dish newDish=((Dish)msg);
				//dbMenu.insertNewDish(newDish,client,myCon,this);
			}
		    if(msg instanceof DishInRestaurant)
			{
		    	DishInRestaurant newDish=((DishInRestaurant)msg);
				dbMenu.insertDishToRestaurant(newDish,client,myCon,this);
			}
		    else {
		    String [] res = ((String)msg).split("~");
		    String result="";
			try {
				switch(res[0])
				{
				
				//cases for DB Order control
				case "Insert_order": dbOrder.insertOrder(res, client,myCon,this); break;
				case "Load_orders": dbOrder.loadOrders(res,client,myCon,this); break;//daniel
				case "Load_Myorders": dbOrder.loadMyOrders(res,client,myCon,this); break;//matan
				//case"Load_MyOrders":loadMyOrders(res,client); break;
				case "W4C_verify": dbOrder.w4cVerify(res,client,myCon,this);break;
				case "W4C_load_list": dbOrder.w4cLoadList(res,client,myCon,this);break;
				case "Load_dishes": dbOrder.loadDishes(res,client,myCon,this);break;
				case "Load_all_dishes":dbOrder.loadAllDishes(res,client,myCon,this);break;
				case "Add_dishInOrder":dbOrder.addDishInOrder(res,client,myCon,this);break;
				case "Order_arrived": dbOrder.orderArrived(res,client,myCon,this);break;
				case "Load_orderDishes":dbOrder.loadOrderDishes(res,client,myCon,this);break;
				case "Load_branches": dbOrder.loadBranches(res,client,myCon,this);break;
				case "Approve_order":dbOrder.ApproveOrder(res,client,myCon,this);break;
				case "Refund Account" : dbOrder.refundAccount(res,client,myCon,this);break;
				case "Update_recieve_time": dbOrder.updateRecieveTime(res,client,myCon,this);break;
				case "Deliver_order":dbOrder.DeliverOrder(res,client,myCon,this);break;
				
				//case "Update_order": updateOrder(res, client); break; from prototype
				//case "Search_order": searchOrder(res, client); break;from prototype
				
				case "Get_connection": getClientInfo(client); break;

				//cases for DB user control
				case "Load_branch_customers": dbUser.loadBranchCustomers(res,client,myCon,this); break;
				case "Load_users": dbUser.loadUsers(res,client,myCon,this); break;
				case "User_login":dbUser.userLogin(res,client,myCon,this);break;
				case "Business_account":dbUser.privateOrBusinessAccountReg(res,client,myCon,this);break;
				case "Private_account":dbUser.privateOrBusinessAccountReg(res,client,myCon,this);break;
				case "Check_account_info":dbUser.checkAccountInfo(res, client, myCon, this);break;
				case "Check_user":dbUser.checkUser(res, client, myCon, this);break;
				case "Check_account_input":dbUser.checkAccountInput(res,client,myCon,this);break;
				case "Check_employer":dbUser.checkEmployer(res,client,myCon,this);break;
				case "Reg_employer":dbUser.regEmployer(res,client,myCon,this);break;
				case "Update_customer":dbUser.updateCustomer(res,client,myCon,this);break;
				case "Import_users":dbUser.importUsers(res,client,myCon,this);break;
				case "Load_customer": dbUser.loadCustomer(res,client,myCon,this);break;//maybe delete
				case "Load private Account": dbUser.loadPrivateAccount(res,client,myCon,this);break;
				case "Load business Account": dbUser.loadSpecificBusinessAccount(res,client,myCon,this);break;//matan
				case "Load_supplier":dbUser.loadSupplier(res,client,myCon,this);break;
				case "Load_suppliers": dbUser.loadSuppliers(res,client,myCon,this);break;
				case "Load_myEmployers": dbUser.loadMyEmployers(res,client,myCon,this);break;
				case "Employer_approved": dbUser.approveEmployer(res,client,myCon,this);break;
				case "Check_approved":dbUser.CheckApproved(res,client,myCon,this);break;		
				case "Load_business_account":dbUser.LoadBusinessAccount(res,client,myCon,this);break;
				case "Approve_account":dbUser.ApproveAccount(res,client,myCon,this);break;
				case "Get_restaurants":dbUser.GetRestaurants(res, client, myCon, this);break;
				case "Approve_restaurant":dbUser.ApproveRestaurant(res, client, myCon, this);break;
				case "Update_private_account":dbUser.UpdatePrivateAccount(res, client, myCon, this);break;
				case "Update_business_account":dbUser.UpdateBusinessAccount(res, client, myCon, this);break;
				case "Logout" :dbUser.logout(res, client, myCon, this);break;
				//cases for DB report control
				case "Load_components":dbReport.loadComponentsOfOrder(res, client,myCon,this);break;
				case "Insert_quantity": dbReport.insertItemsAmount(res,client,myCon,this);break;
				case "Load_monthly_performance":dbReport.loadMonthlyPerformance(res, client,myCon,this);break;	
				case "updateRestaurantData": dbReport.updateRestaurantData(res,client,myCon,this);break;			
				case "Update Supplier Late Cnt": dbReport.updateSupplierLateCnt(res,client,myCon,this);break;
				case "Load_quarter_data":dbReport.getSupplierQuarterData(res, client, myCon, this);break;
				case "Open_pdf":dbReport.getBlob(res, client, myCon, this);break;
				case "Load_myIntake":dbReport.getSupplierIntake(res, client, myCon, this);break;
				
				//cases for DB menu control
				case "Add_Dish": dbMenu.addDish(res, client, myCon, this);break;
				case "Add_to_rest_menu": dbMenu.addDishToRestMenu(res, client, myCon, this);break;
				
				//muhammad
				case "load_all_dishes":dbMenu.loadAllDishs(res, client, myCon, this);break;
				case "load_dishes_in_menu":dbMenu.loadAllDishsInMenu(res, client, myCon, this);break;
				case "load_all_dish_in_restaurant":dbMenu.loadAllDishInRestaurant(res, client, myCon, this);break;

				case "Create_New_Menu":dbMenu.createNewMenu(res,client,myCon,this);break;
				
				}
				}catch (Exception e) {
					// TODO: handle exception
				
				}
				}
	
			}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());

		}

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
	
	  
	  
	 
	   
	
	  

	  
	/*
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
		  try {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


*/
	


	protected void getClientInfo(ConnectionToClient client) {
		Thread[] clientThreadList = getClientConnections();
		String connectionflag;
		for (int i = 0; i < clientThreadList.length; i++) {
			try {
				if ((ConnectionToClient) clientThreadList[i] == client) {
					Socket s = ((ConnectionToClient) clientThreadList[i]).getClientSocket();
					if (s.isConnected()) {
						connectionflag = "Client online";
					} else
						connectionflag = "Client offline";

					String msg = s.getLocalAddress() + "~Host name: "
							+ s.getInetAddress().getLocalHost().getCanonicalHostName() + "~Connection: "
							+ connectionflag;
					msg = msg.substring(1);
					msg = "IP~Ip Adress: " + msg;
					((ConnectionToClient) clientThreadList[i]).sendToClient(msg);
				}

			} catch (Exception ex) {
				System.out.println(ex);
			}
		}
	}



//	  protected void loadCustomer(String[]res,ConnectionToClient  client)
//	  {
//		  Statement stmt;
//		  ResultSet rs;
//		  String result;
//		  try {
//		  stmt = myCon.createStatement();
//		  rs =stmt.executeQuery("SELECT * FROM biteme.customers WHERE AccountNum="+Integer.parseInt(res[1]));
//		  if(rs.next())
//			{
//				System.out.println("customer found"); 
//				result= "Customer load~"+rs.getString(1)+"~"+rs.getString(2)+"~"+rs.getString(3)+"~"+rs.getString(4);
//				sendToClient(result,client);
//			} 
//			else sendToClient("Customer load~Customer Wasnt found", client);
//			rs.close();
//			stmt.close();
//		  }
//		  catch (Exception e) {
//			
//		}
//	  }




	  
}


	  


