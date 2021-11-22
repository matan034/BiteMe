package controllers;

//This file contains material supporting section 3.7 of the textbook:
//"Object Oriented Software Engineering" and is issued under the open-source
//license found at www.lloseng.com 
import java.io.*;

import clients.OrderClient;



/**
* This class constructs the UI for a chat client.  It implements the
* chat interface in order to activate the display() method.
* Warning: Some of the code here is cloned in ServerConsole 
*
* @author Fran&ccedil;ois B&eacute;langer
* @author Dr Timothy C. Lethbridge  
* @author Dr Robert Lagani&egrave;re
* @version July 2000
*/
public class OrderClientController 
{
//Class variables *************************************************

/**
* The default port to connect on.
*/
public static int DEFAULT_PORT ;

//Instance variables **********************************************

/**
* The instance of the client that created this client
*/
public static OrderClient order_client;


//Constructors ****************************************************

/**
* Constructs an instance of the ClientConsole UI.
*
* @param host The host to connect to.
* @param port The port to connect on.
*/
public OrderClientController(String host, int port) 
{
	
	try 
		{
		   order_client= new OrderClient(host, port);
		 } 
		 catch(IOException exception) 
		 {
		   System.out.println("Error: Can't setup connection!"+ " Terminating client.");
		   System.exit(1);
		 }

		
}
 


//Instance methods ************************************************

/**
* This method waits for input from the console.  Once it is 
* received, it sends it to the client's message handler.
*/
public void accept(String str) 
{
	order_client.handleMessageFromClientUI(str);
	 
}

/**
* This method overrides the method in the ChatIF interface.  It
* displays a message onto the screen.
*
* @param message The string to be displayed.
*/
public void display(String message) 
{
 System.out.println("> " + message);
}
}
//End of ConsoleChat class

