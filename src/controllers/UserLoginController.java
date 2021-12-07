	  package controllers;

import clients.OrderClient;
import clients.StartClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UserLoginController {

    @FXML
    private TextField username_lbl;

    @FXML
    private TextField password_lbl;

    @FXML
    private Button login_btn;
    
    @FXML
    private Label login_res_lbl;
    
    @FXML
    void login_action(ActionEvent event) {
    	String msg="User_login~"+username_lbl.getText()+"~"+password_lbl.getText();
    	StartClient.order.accept(msg);
    	if(OrderClient.user_login_msg=="User not Found")
    		login_res_lbl.setText(OrderClient.user_login_msg);
    	else
    		login_res_lbl.setText(OrderClient.user_login_msg);
    }

}