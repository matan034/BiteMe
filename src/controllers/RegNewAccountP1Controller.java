package controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import entity.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegNewAccountP1Controller {

    @FXML
    private TextField last_name_lbl;

    @FXML
    private TextField first_name_lbl;

    @FXML
    private TextField id_lbl;

    @FXML
    private TextField telephone_lbl;

    @FXML
    private TextField email_lbl;

    @FXML
    private TextField confirm_email_lbl;

    @FXML
    private Button before_btn;

    @FXML
    private Button next_btn;

    @FXML
    private Label res_lbl;
    
    @FXML
    private Label id_error_lbl;

    @FXML
    private Label phone_error_lbl;

    @FXML
    private Label email_error_lbl;
    
    @FXML
    private Label confirm_email_error_lbl;
    
    public static Account new_account;
    
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
    	    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
  

    @FXML
    void NextPage(ActionEvent event) {
    	new_account= new Account(first_name_lbl.getText(),last_name_lbl.getText(),id_lbl.getText(),telephone_lbl.getText(),email_lbl.getText());
    	if(CheckInput()){//if input is valid for this page we can move to the next page
    		Globals.loadFXML(null, Globals.regnewaccountp2FXML, event);
    	}
    		

    }
    
    /**
     * Private function CheckInput checks for valid input in Account registration form
     * returns True for valid input, False for invalid input */
    private boolean CheckInput() {
    	email_error_lbl.setText("");
    	confirm_email_error_lbl.setText("");
    	id_error_lbl.setText("");
    	phone_error_lbl.setText("");
    	boolean flag=true;
    	if(!email_lbl.getText().equals(confirm_email_lbl.getText())) {
    		confirm_email_error_lbl.setText("Emails must match");
    		flag=false;
    	}
    	if(!validateEmail(email_lbl.getText())) {
    		email_error_lbl.setText("Email address must be valid");
    		flag=false;
    	}
 
    	if(id_lbl.getText().trim().isEmpty()){
    		id_error_lbl.setText("Must input ID");
    		flag=false;
    	}
    		
    	if(telephone_lbl.getText().trim().isEmpty()) {
    		phone_error_lbl.setText("Must input Telephone");
    		flag=false;
    	}
    	if(flag==true) {
           	StartClient.order.accept("Check_account_input~"+new_account.toString());
           	if(OrderClient.account_reg_errors.get("Errors")==false) {//no errors found in input
           		return flag;
           	}
           	else
           	{
           		flag=false;
           		if(OrderClient.account_reg_errors.get("ID")==true)
           			id_error_lbl.setText("User with this ID already exists");
           		if(OrderClient.account_reg_errors.get("Telephone")==true)
           			phone_error_lbl.setText("User with this Telephone already exists");
           		if(OrderClient.account_reg_errors.get("Email")==true)
           			email_error_lbl.setText("User with this Email already exists");
           		return flag;
           	}
           	
           		
           	
    	}
    	else
    		return flag;
    	
    }
    
  	public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
}

 

}

