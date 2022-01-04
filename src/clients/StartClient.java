

package clients;



import common.Globals;

import general.OrderClientController;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;
/**
*This class launches our LoginScreenUI, the screen where we input ip to connect to server
*@author      dorin bahar 
 * @version     1.0                
 * @since       01.01.2022  
**/
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
		HostServices hostServices = getHostServices();
		Globals.loadFXML(stage,Globals.clientStartFXML , null,hostServices);
	}
}
