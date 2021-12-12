package clients;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import common.Globals;
import controllers.IndexController;
import entity.Account;
import entity.Branch;
import entity.Customer;
import entity.Dish;
import entity.Order;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ocsf.client.AbstractClient;

public class OrderClient extends AbstractClient {

	public static boolean awaitResponse = false;

	public static String connection_ip="",connection_host="",connection_status="",employer_reg_msg="";
	public static ArrayList<User> all_users=new ArrayList<>();
	public static Map<String,Boolean> account_reg_errors=new HashMap<>();
	public static Map<String,Boolean> employer_reg_errors=new HashMap<>();
	public static String update_msg,insert_msg,user_login_msg,account_reg_msg,w4c_status;

	public static ObservableList<String> w4cList=FXCollections.observableArrayList();
	public static ArrayList<Dish> branch_menu=new ArrayList<>();
	public static Order found_order = new Order(null,null);
	public static Account account =new Account(null,null,null,null,null);
	public static Customer customer=new Customer(0, 0, null, null);
	

	public static ObservableList<String> connection_info = FXCollections.observableArrayList(connection_ip,connection_host,connection_status);
	
	public OrderClient(String host, int port) throws IOException {
		super(host, port);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Overridden func for handling messages from server
	 * @param msg. Msg received from server. The msg is constructed with ~. We split according to ~ to enter correct switch case to update the correct variable with our message*/
	@Override
	protected void handleMessageFromServer(Object msg) {
		System.out.println("Msg: "+msg +" recieved");
		awaitResponse = false;
		if(msg instanceof ArrayList) {

			Object[]arr=((ArrayList) msg).toArray();
			if(arr[0] instanceof User)
				all_users = ((ArrayList<User>)msg);
			if(arr[0] instanceof Dish)
				branch_menu = ((ArrayList<Dish>)msg);
			if(arr[0] instanceof Branch)
				Globals.branches=FXCollections.observableArrayList((ArrayList<Branch>)msg);
		}
		
		else {
			String [] res = ((String)msg).split("~");
			switch(res[0]) {
			case "Server Offline":
				serverOffline(res);
				break;
			case "Insert"://update our msg variable we use in our controller to set our label to know if order has been updated correctly
				Globals.newOrder.setOrder_num(Integer.parseInt(res[1]));
				break;
			case "Update"://update our msg variable we use in our controller to set our label to know if order has been updated correctly
				update_msg= res[1];
				break;
			case "Search"://update found_order that we later use to update label in controller with our found order from DB
				if(res.length>1)
				{
					//found_order.setRestuarant(res[1]);//need to insert to order
					found_order.setOrder_num(Integer.parseInt(res[2]));
					found_order.setOrder_time(res[3]);
					found_order.setPhone(res[4]);
					found_order.setOrder_type(res[5]);
					//found_order.setAddress(res[6]);
				}
				else {System.out.println("Order wasn't found");}
				break;
			case "IP"://update connection info, we use this variable in our controller to set our label
				connection_info.set(0,res[1]);
				connection_info.set(1,res[2]);
				connection_info.set(2,res[3]);
				break;
			case "User login":
				user_login_msg=res[1];
				break;
			case "New Account":
				account_reg_msg=res[1];
				break;
			case "Check Account Input":
				boolean flag=false;
				if(res[1].equals("ID")) {
					account_reg_errors.put("ID",true);
					flag=true;
				}
				else
					account_reg_errors.put("ID",false);
				
				if(res[2].equals("Telephone")) {
					account_reg_errors.put("Telephone",true);
					flag=true;
				}
				else
					account_reg_errors.put("Telephone",false);
				
				if(res[3].equals("Email")) {
					account_reg_errors.put("Email",true);
					flag=true;
				}
				else
					account_reg_errors.put("Email",false);
				if(flag==true)account_reg_errors.put("Errors",true);
				else account_reg_errors.put("Errors",false);
				break;
			case "Check Employer Input"://FIX THIS ERRORS ALWAYS EQUALS FALSE;
				boolean flag1=false;
				employer_reg_errors.put("NameError", res[1].equals("Name")?false:true);//if res[1]=="Name" then employer name was found -->No errors --> false if it's not found error-->true
				employer_reg_errors.put("ApprovedError", res[2].equals("Approved")?false:true);//if res[2]=="Approved" then employer is approved -->No errors --> false if it's not approved error-->true
				if(res[1].equals("NoName")|| res[2].equals("NoApproved"))flag1=true;
				employer_reg_errors.put("Errors", flag1);
			case "Employer register":
				employer_reg_msg=res[1];

			case "W4C verify"://change according to type
				account.setAccountNum(Integer.parseInt(res[1]));
				account.setBalance(Integer.parseInt(res[2]));
				account.setFirstName(res[3]);
				account.setLastName(res[4]);
				account.setID(res[5]);
				account.setTelephone(res[6]);
				account.setEmail(res[7]);
				account.setW4cNum(Integer.parseInt(res[8]));
			
				break;
			case "Customer load":
				customer.setCustomerNumber(Integer.parseInt(res[1]));
				customer.setId(res[2]);
				customer.setAccount_num(Integer.parseInt(res[3]));
				customer.setStatus(res[4]);
				break;
			case "W4C_load_list":
				w4cList(res);break;

			}
		
		}	
	}
	
	private void w4cList(String[]res)
	{
		if(res.length>1)
		{
			for(int i=1;i<res.length;i++)
			{
				w4cList.add(res[i]);
			}
		}
	}
	/**
	*func for closing our client*/
	public void quit()
	  {
	    try
	    {
	      closeConnection();
	    }
	    catch(IOException e) {}
	    System.exit(0);
	  }
	  /**
	   * This method handles all data coming from the UI            
	   *
	   * @param message The message from the UI.    
	   */
	  
	  
	  public void handleMessageFromClientUI(String message)  
	  {
	    try
	    {
	    	openConnection();//in order to send more than one message
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
}
