

package general;

import clients.StartClient;
import common.Globals;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/**
*Controller class for our login screen we use it to interact with UI events in login screen
*
* @author      muhamad abu assad
* @version     1.0               
 * @since       01.01.2022     
*@param ip string to save ip address user inputs
*@param ip_address textfield for user to input
*@param login_btn button for loging in
*
*/

public class ClientStartController {
	public static String ip;
    @FXML
    private TextField ip_address;

    @FXML
    private Button login_btn;
    
    /**
     * this func initializes our client to listen for a value in ip address text once we press enter it checks if the input is valid and if it it is it logins to server
	   */
	
    public void initialize() {
    	ip_address.textProperty().addListener((obs,oldValue, newValue)-> {
    	    if(newValue!=null)
    	    {
    	    	validate_login();
    	    }
    	});
    	ip_address.setOnKeyPressed( event -> {
  		  if( event.getCode() == KeyCode.ENTER ) {
  		    if(validate_login())
  		    	LoginToServer(event);
  		  }
  		} );
    }
    	
    public boolean validate_login()
    {
    	if(ip_address.getText()!="") {
    		login_btn.setDisable(false);
    		return true;
    	}
    	else {
    		login_btn.setDisable(true);
    		return false;
    	}
    }
	/**
	*LoginToServer func loads our login scrren we display to user after user logs in to server also saves ip address in our param ip
	*/
    @FXML
    void LoginToServer(Event event) {
    	ip=ip_address.getText();
    	StartClient.order= new OrderClientController(ip, 5555);

    	Globals.loadFXML(null, Globals.userloginFXML, event,null);
    }
}
