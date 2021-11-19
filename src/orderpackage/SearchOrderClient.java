package orderpackage;

import java.io.IOException;
import java.sql.ResultSet;
import ocsf.client.AbstractClient;

public class SearchOrderClient extends AbstractClient{

	OrderClientController clientUI;
	 public static boolean awaitResponse = false;

	public static Order o1 = new Order(null,null,null,null);
	
	public SearchOrderClient(String host, int port, OrderClientController clientUI) throws IOException 
		  {
		    super(host, port); //Call the superclass constructor
		    this.clientUI = clientUI;
		    //openConnection();
		  }
	@Override
	protected void handleMessageFromServer(Object msg) {
		// TODO Auto-generated method stub
		System.out.println("Msg: "+msg +" recieved");
		ResultSet res = ((ResultSet)msg);//details from db
		try {
		o1.setRestuarant(res.getNString(1));//need to insert to order
		o1.setOrder_num(Integer.parseInt(res.getNString(2)));
		o1.setOrder_time(res.getNString(3));
		o1.setPhone(res.getNString(4));
		o1.setOrder_type(res.getNString(5));
		o1.setAddress(res.getNString(6));
		}catch(Exception e) {System.out.println("Ordere wanst found");}
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
