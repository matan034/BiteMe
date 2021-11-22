package orderpackage;

import java.io.IOException;
import java.util.ArrayList;

import ocsf.client.AbstractClient;

public class OrderClient extends AbstractClient {

	 public static boolean awaitResponse = false;
	 public static String connection_info;
	 public static ArrayList<Order> all_orders=new ArrayList<Order>();
	public static String update_msg;
	public static Order o1 = new Order(null,null,null,null);
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
					o1.setRestuarant(res[0]);//need to insert to order
					o1.setOrder_num(Integer.parseInt(res[1]));
					o1.setOrder_time(res[2]);
					o1.setPhone(res[3]);
					o1.setOrder_type(res[4]);
					o1.setAddress(res[5]);
				}
				else {System.out.println("Order wasn't found");}
				break;
				
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
