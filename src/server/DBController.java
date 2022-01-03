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

/**
 * This class is used to route all server DB calls into their specific controller, it splits the msg into key word and uses cases to call upon the corresponding function
 * @param myCon connection to DB
 * @param dbUser class instance of dbUser which holds db calls specific for users
 * @param dbOrder class instance of dbUser which holds db calls specific for orders
 * @param dbReport class instance of dbUser which holds db calls specific for reports
 * @param dbMenu class instance of dbUser which holds db calls specific for menu
 * @author      Dorin bahar
 * @version     1.0               
 * @since       01.01.2022        
 */

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

	
	/*
	   * This method connects us to DB 
	   * @param ip=ip to connect to
	   * @param port =port to connect 
	   * @param db_name =data base nameto connect to
	   * @param db_user = user to connect
	   * @param db_password = password to connect 
	   * 
	   */
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
	   * It splits the msg into res and check res[0] to route to correct function
	   * we also check msg to be an instance of an entity to route to a a specific function
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
					dbMenu.insertDishesToRestaurant((ArrayList<DishInRestaurant>)message.getData(),client,myCon,this);break;
				case "remove": dbMenu.removeDishes((ArrayList<DishInRestaurant>)message.getData(),client,myCon,this);break;
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
				case "Load_orders": dbOrder.loadOrders(res,client,myCon,this); break;
				case "Load_Myorders": dbOrder.loadMyOrders(res,client,myCon,this); break;
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
				case "Check_refund": dbOrder.checkRefund(res,client,myCon,this);break;
				case "Update_BaccountBalance": dbOrder.updateBusinessAccountBalnce(res,client,myCon,this);break;
				case "Update_refund" : dbOrder.updateRefund(res,client,myCon,this);break;
				case "Check_employer_w4c" : dbOrder.checkEmployerW4C(res,client,myCon,this);break;
				
				
				
				case "Get_connection": getClientInfo(client); break;

				//cases for DB user control
				case "Get_my_supplier": dbUser.getMySupplier(res,client,myCon,this); break;
				case "Create_certifies_employee": dbUser.createCertifiedEmployee(res,client,myCon,this); break;
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
				case "Load_customer": dbUser.loadCustomer(res,client,myCon,this);break;
				case "Load private Account": dbUser.loadPrivateAccount(res,client,myCon,this);break;
				case "Load business Account": dbUser.loadSpecificBusinessAccount(res,client,myCon,this);break;
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
				case "Delete_account":dbUser.deleteAccount(res, client, myCon, this);break;
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
	
	  

	  /**
		 * Func for getting clients info such as host name , ip and so on
		 * 
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







	  
}


	  


