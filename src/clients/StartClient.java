/**
*This class launches our LoginScreenUI, the screen where we input ip to connect to server*/

package clients;



import common.Globals;

import general.OrderClientController;
import javafx.application.Application;

import javafx.stage.Stage;

public class StartClient extends Application{
	
	public static OrderClientController order; //only one instance
	/**
	*this func launches our start*/
	public static void main(String[] args) {
		launch(args);
	}
	/**
	*start func loads our fxml onto a stage and displays it
	*@param stage is our stage where we set our screen to display*/
	public void start(Stage stage) {

		Globals.loadFXML(stage,Globals.clientStartFXML , null);
	}
}
