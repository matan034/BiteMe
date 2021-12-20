package clients;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import common.Globals;

import entity.Account;
import entity.Branch;
import entity.BusinessAccount;
import entity.ComponentsRating;
import entity.Customer;
import entity.Dish;
import entity.DishInOrder;
import entity.Employer;
import entity.Order;
import entity.PrivateAccount;
import entity.Supplier;
import entity.User;
import entity.W4C;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ocsf.client.AbstractClient;

public class OrderClient extends AbstractClient {

	public static boolean awaitResponse = false;

	public static int orderLateFlag=0;
	public static Map<String, ArrayList<Dish>> branch_menu = new HashMap<String, ArrayList<Dish>>();

	public static String connection_ip="",connection_host="",connection_status="",employer_reg_msg="";
	public static ArrayList<User> all_users=new ArrayList<>();
	public static Map<String,Boolean> account_reg_errors=new HashMap<>();
	public static Map<String,Boolean> employer_reg_errors=new HashMap<>();
	public static Map<Integer,Order> OrdersInBranch=new HashMap<>();
	public static Map<Integer,Integer> IsOrderApproved=new HashMap<>();
	public static String update_msg,insert_msg,user_login_msg,account_reg_msg,w4c_status,user_import_msg;
	public static ObservableList<String> w4cList=FXCollections.observableArrayList();
	public static ArrayList<Order> ordersInBranch=new ArrayList<>(); 
	public static Order found_order = new Order(null,null);
	
	public static ArrayList<BusinessAccount> usersToApprove=new ArrayList<>();
	
	public static W4C w4c_card;
	
	public static Customer customer=new Customer(0, 0, null, null);
	public static User user;
	public static ObservableList<Order> myOrders=FXCollections.observableArrayList();
	public static ObservableList<Employer> myEmployers=FXCollections.observableArrayList();
	public static ObservableList<String> connection_info = FXCollections.observableArrayList(connection_ip,connection_host,connection_status);
	
	
	// for reports
	public static ObservableList<ComponentsRating> componentsOfDishes = FXCollections.observableArrayList();

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
		if (msg instanceof ArrayList) {
			Object[] arr = ((ArrayList) msg).toArray();
			if (arr[0] instanceof User)
				all_users = ((ArrayList<User>) msg);
			if (arr[0] instanceof Dish)
				loadBranchMenu((ArrayList<Dish>) msg);
			if (arr[0] instanceof Branch)
				Globals.branches = FXCollections.observableArrayList((ArrayList<Branch>) msg);
			if (arr[0] instanceof Supplier)
				Globals.suppliers = FXCollections.observableArrayList((ArrayList<Supplier>) msg);
			if (arr[0] instanceof String) {
				if (((String) arr[0]).equals("load my orders")) {
					((ArrayList<String>) msg).remove(0);
					getMyOrders((ArrayList<String>) msg);
				}
				if(((String)arr[0]).equals("load my employer"))
				{
					((ArrayList<String>)msg).remove(0);
					getMyEmployers((ArrayList<String>)msg);
				}
				// order components rating report
				if (((String) arr[0]).equals("Components_load")) {
					((ArrayList<String>) msg).remove(0);
					percentageOfComponents((ArrayList<String>) msg);
				}
				// monthly performance report
				if (((String) arr[0]).equals("Monthly_performance_load")) {
					((ArrayList<String>) msg).remove(0);
					wellServedOrDelaySupply((ArrayList<String>) msg);
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
					for(int i=1;i<arr.length;i++) {
						String [] res = ((String)arr[i]).split("~");
						BusinessAccount approveaccount=new BusinessAccount(new Account(res[2],res[3],res[1]));
						approveaccount.setEmployerName(res[0]);
						approveaccount.setAccountNum(Integer.parseInt(res[4]));
						usersToApprove.add(approveaccount);
						
					}
				}
				

			}
			if (arr[0] instanceof DishInOrder)
				Globals.order_dishes = (ArrayList<DishInOrder>) msg;

		}

		else {
			String[] res = ((String) msg).split("~");
			switch (res[0]) {
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
				user = new User(res[1], res[2], res[3], res[4],res[5],res[6],res[7],res[8],Integer.parseInt(res[9]),res[10],Integer.parseInt(res[11]),res[12]);
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
				if(res.length==4)
					w4c_card=new W4C(Integer.parseInt(res[1]), Integer.parseInt(res[2]), Integer.parseInt(res[3]));
				else
					w4c_card=new W4C(Integer.parseInt(res[1]), Integer.parseInt(res[2]), 0);


				break;
			case "private account load":
				PrivateAccount paccount =new PrivateAccount(res[2],res[3],res[4],res[5],res[6],Integer.parseInt(res[1]),res[7]) ;
				Globals.newOrder.setpAccount(paccount);
				break;
			case "business account load":
				BusinessAccount baccount =new BusinessAccount(Integer.parseInt(res[1]),res[2],res[3],res[4],res[5],res[6],Integer.parseInt(res[7]),Integer.parseInt(res[8]),Integer.parseInt(res[9]),res[10]) ;
				Globals.newOrder.setbAccount(baccount);
				break;
			case "Customer load"://maybe delete
				customer.setCustomerNumber(Integer.parseInt(res[1]));
				customer.setId(res[2]);
				customer.setAccount_num(Integer.parseInt(res[3]));
				customer.setStatus(res[4]);
				break;
			case "W4C_load_list":
				w4cList(res);
				break;

			case "Check Approved Order":
				IsOrderApproved.put(Integer.parseInt(res[1]), Integer.parseInt(res[2]));
				break;
			case "Order_customer recieved time": checkIfWasLate(res);
			}

		}
				
			

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

	public void handleMessageFromClientUI(String message) {
		try {
			openConnection();// in order to send more than one message
			awaitResponse = true;
			sendToServer(message);
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
	  
	  private void loadBranchMenu(ArrayList<Dish> all_dishes)
	  {
		  branch_menu.put("Appetizer",new ArrayList<Dish>());
		  branch_menu.put("Salad",new ArrayList<Dish>());
		  branch_menu.put("Main",new ArrayList<Dish>());
		  branch_menu.put("Dessert",new ArrayList<Dish>());
		  branch_menu.put("Drink",new ArrayList<Dish>());
		 for(Dish dish:all_dishes)
		 {
			 ArrayList<Dish> temp=branch_menu.get(dish.getType());
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
			  this.myOrders.add(new Order(Integer.parseInt(temp[0]),temp[1],temp[2],Integer.parseInt(temp[3])));
			
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

	  

	private void percentageOfComponents(ArrayList<String> res) {
		for (String restaurant : res) {
			String[] temp = restaurant.split("~");
			String restaurantName = temp[0];

			double starters = Double.parseDouble(temp[1]);
			double drinks = Double.parseDouble(temp[2]);
			double desserts = Double.parseDouble(temp[3]);
			double salads = Double.parseDouble(temp[4]);
			double mains = Double.parseDouble(temp[5]);

			double total = starters + drinks + desserts + salads + mains;

			double startersPer = (starters / total) * 100;
			double drinksPer = (drinks / total) * 100;
			double dessertsPer = (desserts / total) * 100;
			double saladsPer = (salads / total) * 100;
			double mainsPer = (mains / total) * 100;

			String startersPerString = String.format("%,.2f", startersPer) + "%";
			String drinksPerString = String.format("%,.2f", drinksPer) + "%";
			String dessertsPerString = String.format("%,.2f", dessertsPer) + "%";
			String saladsPerString = String.format("%,.2f", saladsPer) + "%";
			String mainsPerString = String.format("%,.2f", mainsPer) + "%";

			ComponentsRating temp1 = new ComponentsRating(restaurantName, startersPerString, drinksPerString,
					dessertsPerString, saladsPerString, mainsPerString);
			OrderClient.componentsOfDishes.add(temp1);

		}
	}

	private void wellServedOrDelaySupply(ArrayList<String> res) {
		for (String restaurant : res) {
			String[] temp = restaurant.split("~");
			String restaurantName = temp[0];
		}
	}
}



