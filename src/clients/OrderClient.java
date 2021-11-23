package clients;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Observable;

import controllers.IndexController;
import entity.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ocsf.client.AbstractClient;

public class OrderClient extends AbstractClient {

	public static boolean awaitResponse = false;
	public static String connection_ip="",connection_host="",connection_status="";
	public static ArrayList<Order> all_orders=new ArrayList<Order>();
	public static String update_msg,insert_msg;
	public static Order found_order = new Order(null,null,null,null);
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
			all_orders = ((ArrayList<Order>)msg);//details from db
		}
		else {
			String [] res = ((String)msg).split("~");
			switch(res[0]) {
			case "Server Offline":
				connection_info.set(2, res[0]);
				break;
			case "Insert"://update our msg variable we use in our controller to set our label to know if order has been updated correctly
				insert_msg=res[1];
				break;
			case "Update"://update our msg variable we use in our controller to set our label to know if order has been updated correctly
				update_msg= res[1];
				break;
			case "Search"://update found_order that we later use to update label in controller with our found order from DB
				if(res.length>1)
				{
					found_order.setRestuarant(res[1]);//need to insert to order
					found_order.setOrder_num(Integer.parseInt(res[2]));
					found_order.setOrder_time(res[3]);
					found_order.setPhone(res[4]);
					found_order.setOrder_type(res[5]);
					found_order.setAddress(res[6]);
				}
				else {System.out.println("Order wasn't found");}
				break;
			case "IP"://update connection info, we use this variable in our controller to set our label
				connection_info.set(0,res[1]);
				connection_info.set(1,res[2]);
				connection_info.set(2,res[3]);
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
	  

}
