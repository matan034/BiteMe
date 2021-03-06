
/**
*Controller class for our login screen we use it to interact with UI events in login screen
*@param ip string to save ip address user inputs
*@param ip_address textfield for user to input
*@param login_btn button for loging in
*
*/

package general;

import clients.StartClient;
import common.Globals;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class ClientStartController {
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
    	StartClient.order= new OrderClientController(ip, 5555);

    	Globals.loadFXML(null, Globals.userloginFXML, event,null);
    }
}
