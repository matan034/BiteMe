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

public class UserLoginController {

    @FXML
    private TextField username_lbl;

    @FXML
    private PasswordField password_lbl;

    @FXML
    private Button login_btn;
    
    @FXML
    private Label login_res_lbl;
    
    @FXML
    private Button import_btn;

    @FXML
    void import_users(ActionEvent event) {
    	UserImportUtility importUsers=new UserImportUtility();
    	importUsers.ImportUsers();
    }
    
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
        		
        		Globals.loadFXML(null,Globals.indexFXML, event);
        	}
        		
    	}
    	else if(username_lbl.getText().trim().isEmpty() && password_lbl.getText().trim().isEmpty())
    		login_res_lbl.setText("Please input User name and Password");
    	
    }

}