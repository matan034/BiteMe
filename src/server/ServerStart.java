package server;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ServerStart extends Application {
	
	private static BiteMeServer sv;
	public static void main(String[] args) {
		launch(args);
}
		
	@Override
	public void start(Stage stage) throws Exception{
		serverController aFrame = new serverController(); // create StudentFrame 
		aFrame.start(stage);

	}
	public static void runServer(String p)
	{
		 int port = 0; //Port to listen on

	        try
	        {
	        	port = Integer.parseInt(p); //Set port to 5555
	          
	        }
	        catch(Throwable t)
	        {
	        	System.out.println("ERROR - Could not connect!");
	        }
	    	
	         sv = new BiteMeServer(port);
	        
	        try 
	        {
	          sv.listen(); //Start listening for connections
	        } 
	        catch (Exception ex) 
	        {
	          System.out.println("ERROR - Could not listen for clients!");
	        }
	}
	public static void closeServer()
	{
		try 
        {
          sv.close(); //stop listenning for connections
        } 
        catch (Exception ex) 
        {
          System.out.println("ERROR - Could not stop listen for clients!");
        }
	}
}
