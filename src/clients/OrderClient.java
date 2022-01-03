package clients;


import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;




import common.Globals;

import entity.Account;
import entity.Branch;
import entity.BusinessAccount;
import entity.Customer;
import entity.Dish;
import entity.DishInOrder;
import entity.DishInRestaurant;
import entity.Employer;
import entity.IntakeOrder;
import entity.MonthlyPerformance;
import entity.MyFile;
import entity.Order;
import entity.OrdersByComponents;
import entity.PrivateAccount;
import entity.Supplier;
import entity.TotalIncomesOfRestaurants;
import entity.User;
import entity.W4C;
import entity.clientData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ocsf.client.AbstractClient;

/**
 * This class is used to receive back data and messages from server, we save that dat in static params for controllers to later on access 
 * Used by all controllers
 * @author      dorin bahar 
 * @version     1.0                
 * @since       01.01.2022      
 * @param awaitResponse part of OCSF we wait for server response
 * @param AccountInfo flag to check correct user info in user table
 * @param branch_menu maps a restaurant to its menu
 * @param all_users array list of all users in DB
 * @param account_reg_errors= map of what error happened while creating an account for instance an error in ID field or telephone field
 * @param employer_reg_erros= map of errors that happened while creating an employer for instance name
 * @param OrderIsBranch = orders in a specific restaurant
 * @param IsOrderApproved = mapping of an order to it's is approved status   
 */
public class OrderClient extends AbstractClient {

	public static boolean awaitResponse = false;
	public static boolean AccountInfo;

	public static int orderLateFlag=0;
	public static Map<String, ArrayList<DishInRestaurant>> branch_menu = new HashMap<String, ArrayList<DishInRestaurant>>();

	public static String connection_ip="",connection_host="",connection_status="",employer_reg_msg="";
	public static ArrayList<User> all_users=new ArrayList<>();
	public static Map<String,Boolean> account_reg_errors=new HashMap<>();
	public static Map<String,Boolean> employer_reg_errors=new HashMap<>();
	public static Map<Integer,Order> OrdersInBranch=new HashMap<>();
	public static Map<Integer,ArrayList<Integer>> IsOrderApproved=new HashMap<>();

	public static String update_msg,insert_msg,user_login_msg="",account_reg_msg,w4c_status,user_import_msg,income,addDish,dishId;
	public static ObservableList<String> w4cList=FXCollections.observableArrayList();
	public static ArrayList<Order> ordersInBranch=new ArrayList<>(); 
	public static Order found_order = new Order(null,null,null,null,null);
	
	public static ArrayList<BusinessAccount> usersToApprove=new ArrayList<>();
	public static ArrayList<Customer> branch_customers=new ArrayList<>();
	public static W4C w4c_card;
	public static Supplier supplier;
	public static Customer customer=new Customer(0,0,0, null, null);
	public static User user;
	public static ObservableList<Order> myOrders=FXCollections.observableArrayList();
	public static ObservableList<String> myRestaurants=FXCollections.observableArrayList();
	public static ObservableList<Employer> myEmployers=FXCollections.observableArrayList();
	public static ObservableList<String> connection_info = FXCollections.observableArrayList(connection_ip,connection_host,connection_status);
	public static String system_error_msg,load_Dishes_msg,Insert_Menu,insert_New_Dish_msg,w4c_msg,orderAmount,insert_dishes_to_restaurant_msg;
	public static ArrayList<Dish> dishes_by_type_list=new ArrayList<>();
	public static Account account =new Account(null,null,null,null,null);
	public static PrivateAccount paccount;
	public static BusinessAccount baccount;
	public int branchID;
	public static File loaded_file;

	// for reports
	public static ObservableList<OrdersByComponents> componentsOfDishes = FXCollections.observableArrayList();
	public static ObservableList<TotalIncomesOfRestaurants> totalIncomesOfRestaurant = FXCollections.observableArrayList();
	public static ObservableList<MonthlyPerformance> monthlyPerformance = FXCollections.observableArrayList();
	public static ObservableList<IntakeOrder> monthIntake;
	public static ObservableList<Dish> allDishes=FXCollections.observableArrayList();
	
	//muhammad
		public static Map<String, ArrayList<Dish>> all_dishes=new HashMap<String, ArrayList<Dish>>();
		//muhammad
		public static Map<String, ArrayList<DishInRestaurant>> dishes_in_menu=new HashMap<String, ArrayList<DishInRestaurant>>();
	

	public OrderClient(String host, int port) throws IOException {
		super(host, port);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Overridden func for handling messages from server
	 * 
	 * @param msg. Msg received from server. The msg is constructed with ~. We split
	 *             according to ~ to enter correct switch case to update the correct
	 *             variable with our message
	 */
	@Override
	protected void handleMessageFromServer(Object msg) {
		System.out.println("Msg: " + msg + " recieved");
		awaitResponse = false;
		if(msg instanceof MyFile)
		{
			MyFile out=(MyFile)msg;
			 loaded_file=out.getFile();
		}
		if(msg instanceof Supplier)
		{
			supplier=(Supplier)msg;
		}
		if(msg instanceof clientData)
		{
			clientData message=(clientData)msg;
			switch(message.getOperation())
			{
			case "laod_all_dishes":
				all_dishes=fromListToMenu((ArrayList<Dish>)message.getData());
				break;
			case "load_all_dish_in_menu":
				 dishes_in_menu=MenuOfDishInRestaurant((ArrayList<DishInRestaurant>)message.getData());
			}
		}
		if (msg instanceof ArrayList) {
			Object[] arr = ((ArrayList) msg).toArray();
			if (arr[0] instanceof User)
				all_users = ((ArrayList<User>) msg);
			if (arr[0] instanceof Customer)
				branch_customers = ((ArrayList<Customer>) msg);
			if (arr[0] instanceof DishInRestaurant)
				loadBranchMenu((ArrayList<DishInRestaurant>) msg);
			if (arr[0] instanceof Branch)
				Globals.branches = FXCollections.observableArrayList((ArrayList<Branch>) msg);
			if (arr[0] instanceof Supplier)
				Globals.suppliers = FXCollections.observableArrayList((ArrayList<Supplier>) msg);
			if (arr[0] instanceof IntakeOrder)
				monthIntake = FXCollections.observableArrayList((ArrayList<IntakeOrder>) msg);
			if (arr[0] instanceof String) {
				if (((String) arr[0]).equals("load my orders")) {
					((ArrayList<String>) msg).remove(0);
					getMyOrders((ArrayList<String>) msg);
				}
				if (((String) arr[0]).equals("load all dishes")) {
					((ArrayList<String>) msg).remove(0);
					loadAllDishes((ArrayList<String>) msg);
				}
				if(((String)arr[0]).equals("load my employer"))
				{
					((ArrayList<String>)msg).remove(0);
					getMyEmployers((ArrayList<String>)msg);
				}
				// order components rating report
				if (((String) arr[0]).equals("Components_load")) {
					((ArrayList<String>) msg).remove(0);
					ordersByComponents((ArrayList<String>) msg);
				}
				// monthly performance report
				if (((String) arr[0]).equals("Monthly_performance_load")) {
					((ArrayList<String>) msg).remove(0);
					wellServedOrDelaySupply((ArrayList<String>) msg);
				}
				
				if (((String) arr[0]).equals("Incomes_load")) {
					((ArrayList<String>) msg).remove(0);
					monthlyIncomesOfSuppliers((ArrayList<String>) msg);
				}
	
				
				if (((String) arr[0]).equals("Order")){
					for(int i=1;i<arr.length;i++) {
						String [] res = ((String)arr[i]).split("~");
						if(OrdersInBranch.containsKey(Integer.parseInt(res[5]))) {
							OrdersInBranch.get(Integer.parseInt(res[5])).setDishesInOrder(res[0]);
						}
						else {
							Order new_order=new Order();
							new_order.setDishesInOrder(res[0]);
							new_order.setRecieving_name(res[1]+" "+res[2]);
							 new_order.setOrder_type(res[3]);
							 new_order.setOrder_time(res[4]);
							 new_order.setOrder_num(Integer.parseInt(res[5]));
							 OrdersInBranch.put(Integer.parseInt(res[5]), new_order);
						}
					}
				}
				if(((String) arr[0]).equals("Approve Business")){
					usersToApprove.clear();
					for(int i=1;i<arr.length;i++) {
						String [] res = ((String)arr[i]).split("~");
						BusinessAccount approveaccount=new BusinessAccount(new Account(res[2],res[3],res[1]));
						approveaccount.setEmployerName(res[0]);
						approveaccount.setAccountNum(Integer.parseInt(res[4]));
						usersToApprove.add(approveaccount);
						
					}
				}
				if(((String) arr[0]).equals("LoadRestaurants")){
					for(int i=1;i<arr.length;i++) {
						myRestaurants.add((String) arr[i]);
					}
				}
			}
			if (arr[0] instanceof DishInOrder)
				Globals.order_dishes = (ArrayList<DishInOrder>) msg;
		}

		else {
			String[] res = ((String) msg).split("~");
			switch (res[0]) {
			case "insert_New_Dish_msg":insert_New_Dish_msg=res[1];break;
			case "Insert_Menu":Insert_Menu=res[1];break;
			case "load_Dishes_msg": load_Dishes_msg=res[1];break;
			case "Server Offline":
				serverOffline(res);
				break;
			case "Insert":// update our msg variable we use in our controller to set our label to know if
							// order has been updated correctly
				Globals.newOrder.setOrder_num(Integer.parseInt(res[1]));
				break;
			case "Update":// update our msg variable we use in our controller to set our label to know if
							// order has been updated correctly
				update_msg = res[1];
				break;
			case "Search":// update found_order that we later use to update label in controller with our
							// found order from DB
				if (res.length > 1) {
					// found_order.setRestuarant(res[1]);//need to insert to order
					found_order.setOrder_num(Integer.parseInt(res[2]));
					found_order.setOrder_time(res[3]);
					found_order.setPhone(res[4]);
					found_order.setOrder_type(res[5]);
					// found_order.setAddress(res[6]);
				} else {
					System.out.println("Order wasn't found");
				}
				break;
			case "IP":// update connection info, we use this variable in our controller to set our
						// label	
				connection_info.set(0, res[1]);
				connection_info.set(1, res[2]);
				connection_info.set(2, res[3]);
				break;
			case "User login":
				if(!res[1].equals("User not Found"))
				{
					user_login_msg="";
					user = new User(res[1], res[2], res[3], res[4],res[5],res[6],res[7],res[8],Integer.parseInt(res[9]),res[10],Integer.parseInt(res[11]),res[12]);
				}
				else user_login_msg=res[2];
				break;
			case "New Account":
				account_reg_msg = res[1];
				break;
			case "Check Account Input":
				boolean flag = false;
				if (res[1].equals("ID")) {
					account_reg_errors.put("ID", true);
					flag = true;
				} else
					account_reg_errors.put("ID", false);

				if (res[2].equals("Telephone")) {
					account_reg_errors.put("Telephone", true);
					flag = true;
				} else
					account_reg_errors.put("Telephone", false);

				if (res[3].equals("Email")) {
					account_reg_errors.put("Email", true);
					flag = true;
				} else
					account_reg_errors.put("Email", false);
				if(res[4].equals("UserID")) {
					account_reg_errors.put("UserID", true);
					flag = true;
				}
				else
					account_reg_errors.put("UserID", false);
				if (flag == true)
					account_reg_errors.put("Errors", true);
				else
					account_reg_errors.put("Errors", false);
				break;
			case "Check Employer Input":
				boolean flag1 = false;
				employer_reg_errors.put("NameError", res[1].equals("Name") ? false : true);// if res[1]=="Name" then
																							// employer name was found
																							// -->No errors --> false if
																							// it's not found
																							// error-->true
				employer_reg_errors.put("ApprovedError", res[2].equals("Approved") ? false : true);// if
																									// res[2]=="Approved"
																									// then employer is
																									// approved -->No
																									// errors --> false
																									// if it's not
																									// approved
																									// error-->true
				if (res[1].equals("NoName") || res[2].equals("NoApproved"))
					flag1 = true;
				employer_reg_errors.put("Errors", flag1);
				break;
			case "Employer Register":
				employer_reg_msg = res[1];

			case "User import":
				user_import_msg = res[1];

			case "W4C verify":// change according to type
				w4cVerify(res);
				break;
			case "private account load":
				paccount =new PrivateAccount(res[2],res[3],res[4],res[5],res[6],Integer.parseInt(res[1]),res[7]) ;
				//Globals.newOrder.setpAccount(paccount);
				break;
			case "business account load":
				 baccount =new BusinessAccount(Integer.parseInt(res[1]),res[2],res[3],res[4],res[5],res[6],Integer.parseInt(res[7]),Integer.parseInt(res[8]),Integer.parseInt(res[9]),res[10]) ;
				//Globals.newOrder.setbAccount(baccount);
				break;
			case "Customer load":
				customer.setCustomerNumber(Integer.parseInt(res[1]));
				customer.setId(res[2]);
				if(!res[4].equals("null")) 
					customer.setpAccount(Integer.parseInt(res[4]));
				if(!res[5].equals("null")) 
					customer.setbAccount(Integer.parseInt(res[5]));
				customer.setStatus(res[3]);
				break;
				
			case "W4C_load_list":
				w4cList.clear();
				w4cList(res);
				break;

			case "Check Approved Order":
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(Integer.parseInt(res[2]));
				temp.add(Integer.parseInt(res[3]));
				IsOrderApproved.put(Integer.parseInt(res[1]),temp) ;
				break;
			case "Order_customer recieved time": checkIfWasLate(res);
			case "Supplier Quarter Data": orderAmount=res[1]; income=res[2];
			case "Add_dish": addDish=res[1]; dishId=res[2];
			case "Add_dish_to_rest" : addDish=res[1];
			case "AccountInfo":
				if(res[1].equals("Found")) AccountInfo=true;
				else
					AccountInfo=false;
				break;
			case "insert_dishes_to_restaurant_msg":insert_dishes_to_restaurant_msg=res[1];break;
			}

		}
				
			

	}

	private void w4cVerify(String[] res) {
		w4c_card=null;
		if(res.length==4)
			w4c_card=new W4C(Integer.parseInt(res[1]), Integer.parseInt(res[2]), Integer.parseInt(res[3]));
		else
			w4c_msg=res[1];
	}
		
				
	private void checkIfWasLate(String[]res)
	{
		try {
			if(Integer.parseInt(res[1])==1)
			{
				orderLateFlag=1;
			}
		}
		catch (Exception e) {
			
		}
	}

	private void w4cList(String[] res) {
		if (res.length > 1) {
			for (int i = 1; i < res.length; i++) {
				w4cList.add(res[i]);
			}
		}
	}

	/**
	 * func for closing our client
	 */
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}

	/**
	 * This method handles all data coming from the UI
	 *
	 * @param message The message from the UI.
	 */

	public void handleMessageFromClientUI(Object msg) {
		try {
			openConnection();// in order to send more than one message
			awaitResponse = true;
			sendToServer(msg);
			// wait for response
			while (awaitResponse) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
	    }
	    catch(Exception e)
	    {
	    	//e.printStackTrace();
	      //IndexOrderUI.display("Could not send message to server: Terminating client."+ e);
	    	System.out.println("Could not send message to server: Terminating client." + e);
	      quit();
	    }
	  }
	  
	  public void serverOffline(String[]res)
	  {
		  connection_info.set(2, res[0]);
	  }
	  
	  private void loadBranchMenu(ArrayList<DishInRestaurant> all_dishes)
	  {
		  branch_menu.put("Appetizer",new ArrayList<DishInRestaurant>());
		  branch_menu.put("Salad",new ArrayList<DishInRestaurant>());
		  branch_menu.put("Main",new ArrayList<DishInRestaurant>());
		  branch_menu.put("Dessert",new ArrayList<DishInRestaurant>());
		  branch_menu.put("Drink",new ArrayList<DishInRestaurant>());
		 for(DishInRestaurant dish:all_dishes)
		 {
			 ArrayList<DishInRestaurant> temp=branch_menu.get(dish.getType());
			 temp.add(dish);
			 branch_menu.put(dish.getType(),temp );
		 }
	  }
	  private void getMyOrders(ArrayList<String> myOrders)
	  {
		  this.myOrders.clear();
		  for(String order:myOrders)
		  {
			  String[]temp=order.split("~");
			  this.myOrders.add(new Order(Integer.parseInt(temp[0]),temp[1],temp[2],Integer.parseInt(temp[3]),Integer.parseInt(temp[4]),temp[5]));
			
		  }
	  }
	  private void getMyEmployers(ArrayList<String> myEmployers)
	  {
		  this.myEmployers.clear();
		  for(String employer:myEmployers)
		  {
			  String[]temp=employer.split("~");
			  //name address phone isapproved
			  this.myEmployers.add(new Employer(Integer.parseInt(temp[0]),temp[1],temp[2],temp[3],Integer.parseInt(temp[4])));
			
		  }
	  }

	

	
	private void ordersByComponents(ArrayList<String> res) {
		for (String restaurant : res) {
			String[] temp = restaurant.split("~");
			String restaurantName = temp[0];

			int starters = Integer.parseInt(temp[1]);
			int drinks = Integer.parseInt(temp[2]);
			int desserts = Integer.parseInt(temp[3]);
			int salads = Integer.parseInt(temp[4]);
			int mains = Integer.parseInt(temp[5]);

	

			String startersPerString = String.format("%d", starters);
			String drinksPerString = String.format("%d", drinks);
			String dessertsPerString = String.format("%d", desserts);
			String saladsPerString = String.format("%d", salads);
			String mainsPerString = String.format("%d", mains);

			OrdersByComponents temp1 = new OrdersByComponents(restaurantName, startersPerString, drinksPerString,
					dessertsPerString, saladsPerString, mainsPerString);
			OrderClient.componentsOfDishes.add(temp1);

		}
	}

	private void wellServedOrDelaySupply(ArrayList<String> res) {
		for (String restaurant : res) {
			String[] temp = restaurant.split("~");
			MonthlyPerformance tempRes = new MonthlyPerformance(temp[0],temp[4],temp[3]);
			OrderClient.monthlyPerformance.add(tempRes);
		}
	}
	
	
	private void monthlyIncomesOfSuppliers(ArrayList<String> res) {
		for (String restaurant : res) {
			String[] temp = restaurant.split("~");
			TotalIncomesOfRestaurants tempIncome = new TotalIncomesOfRestaurants(temp[0], temp[2]);
			OrderClient.totalIncomesOfRestaurant.add(tempIncome);

		}
	}
	private void loadAllDishes(ArrayList<String> res) {
		for (String dish : res) {
			String[] temp = dish.split("~");
			Dish tempDish = new Dish(Integer.parseInt(temp[0]),temp[1], temp[2]);
			OrderClient.allDishes.add(tempDish);

		}
	}
	
	//muhammad
	  private Map<String,ArrayList<Dish>> fromListToMenu(ArrayList<? extends Dish> all_dishes) {
		  Map<String,ArrayList<Dish>> menu=new HashMap<String,ArrayList<Dish>>();
		  menu.put("Appetizer",new ArrayList<Dish>());
		  menu.put("Salad",new ArrayList<Dish>());
		  menu.put("Main",new ArrayList<Dish>());
		  menu.put("Dessert",new ArrayList<Dish>());
		  menu.put("Drink",new ArrayList<Dish>());
		 for(Dish dish:all_dishes)
		 {
			 ArrayList<Dish> temp=menu.get(dish.getType());
			 temp.add(dish);
			 menu.put(dish.getType(),temp );
		 }
		 return menu;
	  }
	  //muhammad
	  private Map<String,ArrayList<DishInRestaurant>> MenuOfDishInRestaurant(ArrayList<DishInRestaurant> all_dishes) {
		  Map<String,ArrayList<DishInRestaurant>> menu=new HashMap<String,ArrayList<DishInRestaurant>>();
		  menu.put("Appetizer",new ArrayList<DishInRestaurant>());
		  menu.put("Salad",new ArrayList<DishInRestaurant>());
		  menu.put("Main",new ArrayList<DishInRestaurant>());
		  menu.put("Dessert",new ArrayList<DishInRestaurant>());
		  menu.put("Drink",new ArrayList<DishInRestaurant>());
		 for(DishInRestaurant dish:all_dishes)
		 {
			 ArrayList<DishInRestaurant> temp=menu.get(dish.getType());
			 temp.add(dish);
			 menu.put(dish.getType(),temp );
		 }
		 return menu;
	  }
}




