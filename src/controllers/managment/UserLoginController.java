package managment;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import utility.UserImportUtility;

/**
 * Controller for user login screen here we allow users to log in via username and password only after accounts have been imported by outside user import
 * @param username_lbl = text area for username
 * @param password_lbl = text area for password
 * @param login_btn = button for logging in
 * @param login_res_lbl = label for displaying login result to user incase of failure
 *  @author      matan weisberg
 * @version     1.0               
 * @since       01.01.2022  
 **/
public class UserLoginController {

    @FXML
    private TextField username_lbl;

    @FXML
    private PasswordField password_lbl;

    @FXML
    private Button login_btn;
    
    @FXML
    private Label login_res_lbl;
    
  

  
    
    /**
     * func to initialize our text areas to allow ENTER presses to attempt to login*/
    public void initialize()
    {
    	username_lbl.setOnKeyPressed( event -> {
    		  if( event.getCode() == KeyCode.ENTER ) {
    		    login_action(event);
    		  }
    		} );
    	password_lbl.setOnKeyPressed( event -> {
  		  if( event.getCode() == KeyCode.ENTER ) {
  			login_action(event);
  		  }
  		} );
    }
    
    
    /**
     * Activates on button press we check for correct input by user if the username and password are correct we get the user information and log in the user showing him a home screen that fits that user type
     * if input is incorrect we dispaly the correct error message 
     * @param event Action event for event details*/
    @FXML
    void login_action(Event event) {
    	if(username_lbl.getText().trim().isEmpty())login_res_lbl.setText("Please Input User Name");
    	if(password_lbl.getText().trim().isEmpty())login_res_lbl.setText("Please Input Password");
    	if(!username_lbl.getText().trim().isEmpty() && !password_lbl.getText().trim().isEmpty()) {
    		String msg="User_login~"+username_lbl.getText()+"~"+password_lbl.getText();
        	StartClient.order.accept(msg);
        	if(!OrderClient.user_login_msg.equals(""))
        		login_res_lbl.setText(OrderClient.user_login_msg);
        	else {
        		
        		Globals.loadFXML(null,Globals.indexFXML, event,null);
        		if(OrderClient.user.getType().equals("Customer"))
        			Globals.loadInsideFXML(Globals.homePageCustomer);
        		else {
        		if(OrderClient.user.getType().equals("CEO"))
        			Globals.loadInsideFXML(Globals.homePageCeo);
        		else {
        		if(OrderClient.user.getType().equals("Supplier"))
        			Globals.loadInsideFXML(Globals.homePageSupplier);
        		else
        			if(OrderClient.user.getType().equals("Base User"))
            			Globals.loadInsideFXML(Globals.homePageBaseUser);
        		}
        	  }
        	}
    	}
    	else if(username_lbl.getText().trim().isEmpty() && password_lbl.getText().trim().isEmpty())
    		login_res_lbl.setText("Please input User name and Password");
    	
    }

}