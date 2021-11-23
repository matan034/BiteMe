
/**
*Controller class for our login screen we use it to interact with UI events in login screen
*@param ip string to save ip address user inputs
*@param ip_address textfield for user to input
*@param login_btn button for loging in
*
*/

package controllers;
import java.net.ConnectException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController {
	public static String ip;
    @FXML
    private TextField ip_address;

    @FXML
    private Button login_btn;
	
    public void initialize() {
    	ip_address.textProperty().addListener((obs,oldValue, newValue)-> {
    	    if(newValue!=null)
    	    {
    	    	validate_login();
    	    }
    	});
    }
    	
    public void validate_login()
    {
    	if(ip_address.getText()!="") login_btn.setDisable(false);
    	else login_btn.setDisable(true);
    }
	/**
	*LoginToServer func loads our IndexScreen after user logs in to server also saves ip address in our param ip
	*/
    @FXML
    void LoginToServer(ActionEvent event) {
    	ip=ip_address.getText();
    	
    	FXMLLoader loader = new FXMLLoader();
    	try {
    	((Node) event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		Pane root ;
		loader.setLocation(getClass().getResource("/ui/IndexScreen.fxml"));
	    root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/ui/GeneralStyleSheet.css").toExternalForm());
		//scene.getStylesheets().add(getClass().getResource("/gui/IndexScreen.css").toExternalForm());
		primaryStage.setTitle("Index");
		primaryStage.setScene(scene);		
		primaryStage.show();
    	}
    	catch(ConnectException e) {System.out.println("Server is closed");}
    	catch (Exception e) {System.out.println("Index load exception");}
    	
    }
}
