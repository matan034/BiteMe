package orderpackage;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import ocsf.client.AbstractClient;

public class UpdateOrderClient extends AbstractClient{

	UpdateOrderController clientUI;
	 public static boolean awaitResponse = false;

	public static ArrayList<Order> all_orders=new ArrayList<Order>();
	public static String res;
	
	
	public UpdateOrderClient(String host, int port, UpdateOrderController clientUI) throws IOException 
		  {
		    super(host, port); //Call the superclass constructor
		    this.clientUI = clientUI;
		    //openConnection();
		  }
	@Override
	protected void handleMessageFromServer(Object msg) {
		// TODO Auto-generated method stub
		awaitResponse = false;
		System.out.println(msg);
		if(msg instanceof String)
		{
			res= (String)msg;
		}
		else {
		all_orders = ((ArrayList<Order>)msg);//details from db
		}

	}
	/**
	   * This method terminates the client.
	   */
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
