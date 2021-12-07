package common;

import java.io.IOException;

import controllers.RegNewAccountP1Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Globals {
	public static String insertFXML="/order/InsertScreen.fxml",
			searchFXML="/order/SearchScreen.fxml",
			updateFxml="/order/UpdateScreen.fxml",
			indexFXML="/order/IndexScreen.fxml",
			serverFXML="/server/serverFXML.fxml",
			loginFXML="/general/LoginScreen.fxml",
			userloginFXML="/general/UserLoginScreen.fxml",
			regnewaccountp1FXML="/general/RegNewAccountP1Screen.fxml",
			regnewaccountp2FXML="/general/RegNewAccountP2Screen.fxml",
			regnewemployerFXML="/general/RegNewEmployerScreen.fxml",
			changeuserstatusFXML="/general/ChangeUserStatusScreen.fxml";
	
	public static void loadFXML(Stage stage,String fxml_name,ActionEvent event)
	{
		Pane pane;
		try {
		((Node) event.getSource()).getScene().getWindow().hide(); //hiding primary window
		}
		catch(Exception e) {}
		if(stage==null) 
			 stage= new Stage();
		try {
		    FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(Globals.class.getResource(fxml_name));
		    pane = loader.load();
			Scene scene=new Scene(pane);
				 
			stage.setTitle("Login");
			stage.setScene(scene);		
			stage.show();
		} catch (IOException e) {
		    e.printStackTrace();
		    return;
		}
	}
}


