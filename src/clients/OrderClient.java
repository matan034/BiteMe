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
 * 
 */
public class OrderClient extends AbstractClient {

	/**
	 *part of OCSF we wait for server response */
	public static boolean awaitResponse = false;
	/**flag to check correct user info in user table*/
	public static boolean AccountInfo;
	/**flag to know when an order is late 1 means late order 0 means not late*/
	public static int orderLateFlag=0;
	/**menu maps a restaurant to its menu*/
	/**map for mapping Dish Type (main ,sides) to menus in a specific branch*/
	public static Map<String, ArrayList<DishInRestaurant>> branch_menu = new HashMap<String, ArrayList<DishInRestaurant>>();
	/**string messages for displaying connection status*/
	public static String connection_ip="",connection_host="",connection_status="",employer_reg_msg="";
	/**array list of all users in DB*/
	public static ArrayList<User> all_users=new ArrayList<>();
	/** param account_reg_errors map of what error happened while creating an account for instance an error in ID field or telephone field*/
	public static Map<String,Boolean> account_reg_errors=new HashMap<>();
	/** map of errors that happened while creating an employer for instance name*/
	public static Map<String,Boolean> employer_reg_errors=new HashMap<>();
	/** orders in a specific restaurant*/
	public static Map<Integer,Order> OrdersInBranch=new HashMap<>();
	/** mapping of an order to it's is approved status IE order #5 = 1 (approved) */
	public static Map<Integer,ArrayList<Integer>> IsOrderApproved=new HashMap<>();
	/**string messages for displaying database results back to user like "user inserted" or "user insert failed" and so on*/
	public static String update_msg,insert_msg,user_login_msg="",account_reg_msg,w4c_status,user_import_msg,income,addDish,dishId;
	/**list of W4c cards */
	public static ObservableList<String> w4cList=FXCollections.observableArrayList();
	/** array list of orders in a restaurant*/
	public static ArrayList<Order> ordersInBranch=new ArrayList<>(); 
	/**a specific order we search for*/
	public static Order found_order = new Order(null,null,null,null,null);
	/**list of accounts that need to be approved*/
	public static ArrayList<BusinessAccount> usersToApprove=new ArrayList<>();
	/**list of all customers in a branch*/
	public static ArrayList<Customer> branch_customers=new ArrayList<>();
	/** details on a w4c card from current user*/
	public static W4C w4c_card;
	/**used to get details on currenct supplier that is connected or is being accessed */
	public static Supplier supplier;
	/**used to get details on the customer that is currently operating in the system */
	public static Customer customer=new Customer(0,0,0, null, null);
	/** used to get details on the current user that is logged in */
	public static User user;
	/** Observable list that we use to update logged in customers orders*/
	public static ObservableList<Order> myOrders=FXCollections.observableArrayList();
	/**observable list we use to update current restaurants displayed in places like combo boxes*/
	public static ObservableList<String> myRestaurants=FXCollections.observableArrayList();
	/**obeservable list we usee to update current employes displayed in places like combo boxes*/
	public static ObservableList<Employer> myEmployers=FXCollections.observableArrayList();
	/**observable list of connection info*/
	public static ObservableList<String> connection_info = FXCollections.observableArrayList(connection_ip,connection_host,connection_status);
	/**string messages for displaying database results back to user like "user inserted" or "user insert failed" and so on*/
	public static String system_error_msg,load_Dishes_msg,Insert_Menu,insert_New_Dish_msg,w4c_msg,orderAmount,insert_dishes_to_restaurant_msg;
	/**dishes_by_type_list a list of dishes orginized by it's type (main,second) and so on*/
	public static ArrayList<Dish> dishes_by_type_list=new ArrayList<>();
	/** details on an account that's loaded such as first name and so on*/
	public static Account account =new Account(null,null,null,null,null);
	/**details on private account thats loaded(customer thats logged in with that private account) or a private we search for and update in functions like deleting*/
	public static PrivateAccount paccount;
	/** details on business account thats loaded(customer thats logged in with that business account) or a business account we search for and update in functions like deleting*/
	public static BusinessAccount baccount;
	/**current branch id number*/
	public int branchID;
	/**image thats currently being loaded*/
	public static File loaded_file;
	/**verify employer if w4c is valid true if it is false if not valid*/
	public static boolean employerW4cVerify;
	
	// for reports
	/**observable list that we update to display in table where we report number of dishes ordered by type*/
	public static ObservableList<OrdersByComponents> componentsOfDishes = FXCollections.observableArrayList();
	/**observable list that we update to display in table where we report a restuarants income*/
	public static ObservableList<TotalIncomesOfRestaurants> totalIncomesOfRestaurant = FXCollections.observableArrayList();
	/**observable list that we update to display in table where we report a restaurants monthly income */
	public static ObservableList<MonthlyPerformance> monthlyPerformance = FXCollections.observableArrayList();
	/**observable list that we update to display in table where a restaurants sees the amount of money it owes biteme for using it's systems*/
	public static ObservableList<IntakeOrder> monthIntake;
	/**observable list of all dishes*/
	public static ObservableList<Dish> allDishes=FXCollections.observableArrayList();
	/***/
	public static double refund;
	/** a mapping of all dishes to it's specific type for instance Main= hamburger,chicken , Side=fires,rice*/
	public static Map<String, ArrayList<Dish>> all_dishes=new HashMap<String, ArrayList<Dish>>();
	/** a mapping of all dishes in a menu(we can have a dish not in a menu currently) to a specific type type for instance Main= hamburger,chicken , Side=fires,rice*/
	public static Map<String, ArrayList<DishInRestaurant>> dishes_in_menu=new HashMap<String, ArrayList<DishInRestaurant>>();
	
	/**generate a OrderClient with host and ip port calls super function from OCSF
	 * @param host host name
	 * @param port host port*/
	public OrderClient(String host, int port) throws IOException {
		super(host, port);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Overridden func for handling messages from server
	 * 
	 * @param msg Msg received from server. The msg is constructed with ~. We split
	 *             according to ~ to enter correct switch case to update the correct
	 *             variable with our message
	 *             To see all cases you must enter the .java file and see each case 
	 *           	
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
			{
				
				Globals.suppliers = FXCollections.observableArrayList((ArrayList<Supplier>) msg);
			}
				
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
					OrdersInBranch.clear();
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
							 new_order.setPhone(res[7]);
							 new_order.setMail(res[6]);
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
					myRestaurants.clear();
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
				
				break;
			case "business account load":
				 baccount =new BusinessAccount(Integer.parseInt(res[1]),res[2],res[3],res[4],res[5],res[6],Integer.parseInt(res[7]),Double.parseDouble(res[8]),Integer.parseInt(res[9]),res[10]) ;
				
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
			case "get my supplier":
				
					if(res[1].equals("-1")) {}
					else supplier=new Supplier(Integer.parseInt(res[1]), Integer.parseInt(res[2]), Integer.parseInt(res[3]),res[4],
					res[5], res[6],res[7], res[8]);
					break;
			case "Check_refund": 
				refund=Double.parseDouble(res[1]);
				break;
				
			case "Check_employer_w4c_code": 
				if(res[1].equals("true")) employerW4cVerify=true;
				if(res[1].equals("false")) employerW4cVerify=false;
			}

		}
				
			

	}

	/**function to verify is w4c input back from DB is correct if it is we insert new w4c entity into w4c_card if its not 
	 * we display the servers error message back to user*/
	private void w4cVerify(String[] res) {
		w4c_card=null;
		if(res.length==4)
			w4c_card=new W4C(Integer.parseInt(res[1]), Integer.parseInt(res[2]), Integer.parseInt(res[3]));
		else
			w4c_msg=res[1];
	}
		
	/**checks if order is late by DB result if its late change orderLateFlag to 1*/			
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

	/**adds card to list of w4c cards (w4cList) used to display w4c options to user when scanning QR code*/
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
	  
		/**sets connection info to be offline when server is disconnected
		 * @param res[] array with string message from server*/
	  public void serverOffline(String[]res)
	  {
		  connection_info.set(2, res[0]);
	  }
	  
	  /**
	   * function to map dish to it's branch menu used when loading and editing menus 
	   * saves to static branch_menu
	   * @param all_dishes an arraylist of all dishes we recieved back from database*/
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
	  /**
	   * function for getting all orders to display back to user his orders
	   * saves to static myOrders
	   * @param myOrdersAsString a string of order details that came back from DB*/
	  private void getMyOrders(ArrayList<String> myOrdersAsString)
	  {
		  this.myOrders.clear();
		  for(String order:myOrdersAsString)
		  {
			  String[]temp=order.split("~");
			  Order o=new Order(Integer.parseInt(temp[0]),temp[1],temp[2],Integer.parseInt(temp[3]),Integer.parseInt(temp[4]),Integer.parseInt(temp[5]),temp[6]);
			 o.setSupplierNum(Integer.parseInt(temp[7]));
			 o.setCustomerNum(Integer.parseInt(temp[8]));
			 o.setPrice(Double.parseDouble(temp[9]));
			  myOrders.add(o);
			
		  }
	  }
	  
	  /**
	   * function to get a business accounts employer gets all employer details back from DB
	   * saves to static myEmployers
	   * @param myEmployers the employer details that DB found for specific user*/
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

	

	/**
	 * funtion for getting amount of orders each dish type had and save it in componentsofDishes to be displayed in componentofdishesReport
	 * @param res an arraylist with numbers for each type( 0 - starters, 1- drinks, ... for example)*/
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

	/**
	 * function for getting if a dish has been served in time or not and save it in monthlyPerformance 
	 * used to display data in table for MonthlyPerforamnce report
	 * @param res array list containing amount of orders and amount of late orders*/
	private void wellServedOrDelaySupply(ArrayList<String> res) {
		for (String restaurant : res) {
			String[] temp = restaurant.split("~");
			MonthlyPerformance tempRes = new MonthlyPerformance(temp[0],temp[4],temp[3]);
			OrderClient.monthlyPerformance.add(tempRes);
		}
	}
	
	/**
	 * function for getting monthly income of a restuarant saves it in totalIncomesOfRestaurants
	 * @param res arraylist with a restuarant number and its total income*/
	private void monthlyIncomesOfSuppliers(ArrayList<String> res) {
		for (String restaurant : res) {
			String[] temp = restaurant.split("~");
			TotalIncomesOfRestaurants tempIncome = new TotalIncomesOfRestaurants(temp[0], temp[2]);
			OrderClient.totalIncomesOfRestaurant.add(tempIncome);

		}
	}
	
	/**
	 * function for loading all dishes in DB inserts into allDishes
	 * @param res an array list containing all dishes*/
	private void loadAllDishes(ArrayList<String> res) {
		for (String dish : res) {
			String[] temp = dish.split("~");
			Dish tempDish = new Dish(Integer.parseInt(temp[0]),temp[1], temp[2]);
			OrderClient.allDishes.add(tempDish);

		}
	}
	
	/**
	 * Function to map an list of dishes in a menu to it's type used in EditMenu to get a map of dishes to insert into list of items that can be added to menu
	 * @param all_dishes an array list of all dishes in menu
	 * @return Map a map of dishes to a type (a menu)*/
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
	  /**
	   * Function to map dishes in a restaurant to to it's type creating a menu for a restaurant (type refers to appetaizer,salad,main etc...)
	   * @param all_dishes an array list of all dishes in the restaurants DB
	   * @return Map a menu for a specific restuarant*/
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




