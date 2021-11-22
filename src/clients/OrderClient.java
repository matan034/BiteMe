package clients;

import java.io.IOException;
import java.util.ArrayList;

import entity.Order;
import ocsf.client.AbstractClient;

public class OrderClient extends AbstractClient {

	public static boolean awaitResponse = false;
	public static String connection_info;
	public static ArrayList<Order> all_orders=new ArrayList<Order>();
	public static String update_msg;
	public static Order found_order = new Order(null,null,null,null);
	
	public OrderClient(String host, int port) throws IOException {
		super(host, port);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		System.out.println("Msg: "+msg +" recieved");
		awaitResponse = false;
		if(msg instanceof ArrayList) {
			all_orders = ((ArrayList<Order>)msg);//details from db
		}
		else {
			String [] res = ((String)msg).split("~");//details from db
			switch(res[0]) {
			case "Index":
				connection_info= (String)msg;
				break;
			case "Insert":
				break;
			case "Update":
				update_msg= (String)msg;
				break;
			case "Search":
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
			case "IP":
				connection_info=res[1];
			}
		}	
	}
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
	    catch(IOException e)
	    {
	    	e.printStackTrace();
	      //IndexOrderUI.display("Could not send message to server: Terminating client."+ e);
	    	System.out.println("Could not send message to server: Terminating client." + e);
	      quit();
	    }
	  }

}
