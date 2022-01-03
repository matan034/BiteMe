package managment;

import java.util.Collections;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import general.MyListener;
import general.VerifyListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * controller for Useralreadyhasaccountscreen we get to this screen via hpyer link in RegNewAccountP1
 * here a user inputs ID for an account that already exists if the id is linked to an account user can move to RegNewAccountP2
 * without reentering account details 
 * @param id_lbl = text area to input id
 * @param next_btn = button to confirm input
 * @param id_error_lbl = display error in id to user
 * @param account_id = static variable that we move to regNewAccountP2 containing the users inputted id*/
public class UserAlreadyHasAccountController {

    @FXML
    private TextField id_lbl;

    @FXML
    private Button next_btn;
   

    @FXML
    private Label id_error_lbl;
    
    public static String account_id;
    
    /**
     * We initialize the controller by setting a verifyListener to verify valid ID input
     * We also check according to the database if there is an account with ID inputted or not*/
    public void initialize() {
    	id_lbl.setText("");

 	   Globals.VerifyInputListener(id_lbl, new VerifyListener() {

 		@Override
 		public boolean verify() {
 			if(id_lbl.getText().trim().isEmpty()){
 	    		id_error_lbl.setText("Must input ID");
 	    		return false;
 	    	}

 			if (id_lbl.getText().length()!=9) {
 	    		id_error_lbl.setText("ID must be 9 digits");
 	    		return false;
 	    	}else {
 	    		StartClient.order.accept("Check_account_info~ID~"+id_lbl.getText());
 	 			if(OrderClient.AccountInfo==true) {
 	 				id_error_lbl.setText("");
 	 				return true;
 	 			}
 	 			if(OrderClient.AccountInfo==false) {
 	 				id_error_lbl.setText("No account with this ID");
 	 				return false;
 	 			}
 	 			StartClient.order.accept("Check_user~"+id_lbl.getText());
 	 			if(OrderClient.AccountInfo==false) {
 	 				id_error_lbl.setText("No user with this ID");
 	 				return false;
 	 			}
 	 	    	else {
 	 	    		id_error_lbl.setText("");
 	 	    		return true;
 	 	    	}
 	    	}
 			
 	    	
 		}
 		   
 	   });
    }
    /**
     * Event for button when the user enters ID and presses button if the ID is indeed the database we move user to regnewaccountp2screen and hide the popup
     * @param event actionevent for event details*/
    @FXML
    void NextPage(ActionEvent event) {
    		StartClient.order.accept("Check_account_info~ID~"+id_lbl.getText());
    		if(OrderClient.AccountInfo==true) {
    			account_id=id_lbl.getText();
    			((Node) event.getSource()).getScene().getWindow().hide();
    			Globals.loadInsideFXML(Globals.regnewaccountp2FXML);
    		}
    }
    
 

}
