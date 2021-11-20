package orderpackage;

import java.io.IOException;

import ocsf.client.AbstractClient;

public class IndexClient extends AbstractClient {
	 
		IndexController clientUI;
		 public static boolean awaitResponse = false;
		 public static String connection_info;
		
		public IndexClient(String host, int port, IndexController clientUI) throws IOException 
	    {
			    super(host, port); //Call the superclass constructor
			    this.clientUI = clientUI;
			    openConnection();
		}
		
		
		@Override
		protected void handleMessageFromServer(Object msg) {
			// TODO Auto-generated method stub
			awaitResponse = false;
			connection_info= (String)msg;
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


