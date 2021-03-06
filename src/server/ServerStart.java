package server;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ServerStart extends Application {
	
	public static DBController sv;
	
	public static void main(String[] args) {
		launch(args);
}
		
	@Override
	public void start(Stage stage) throws Exception{
		serverController aFrame = new serverController(); // create 
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
	    	
	         sv = new DBController(port);
	        
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
			sv.sendToAllClients("Server Offline");
          sv.close(); //stop listenning for connections
        } 
        catch (Exception ex) 
        {
          System.out.println("ERROR - Could not stop listen for clients!");
        }
	}
}
