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
    
    @FXML
    private Label first_error_lbl;

    @FXML
    private Label last_error_lbl;
    
    public static Account new_account;
    
    private Pattern namePattern=Pattern.compile("^(?=.{2,40}$)[a-zA-Z]+(?:[-'\\s][a-zA-Z]+)*$");
    private Pattern VALID_EMAIL_ADDRESS_REGEX = 
    	    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
  
    private Pattern pattern = Pattern.compile("\\d+");
    


    /**
     * Function to go to next page, account details in this page are saved to new_account*/
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
    	first_error_lbl.setText("");
    	last_error_lbl.setText("");
    	boolean flag=true;
    	if(id_lbl.getText().trim().isEmpty()){
    		id_error_lbl.setText("Must input ID");
    		flag=false;
    	}
    	if(first_name_lbl.getText().trim().isEmpty()){
    		first_error_lbl.setText("Must input a first name");
    		flag=false;
    	}
    	else if(!isValidName(first_name_lbl.getText())){
    		first_error_lbl.setText("Name must be with valid characters");
    		flag=false;
    	}
    	if(last_name_lbl.getText().trim().isEmpty()){
    		last_error_lbl.setText("Must input a last name");
    		flag=false;
    	}
    	else if(!isValidName(last_name_lbl.getText())){
    		last_error_lbl.setText("Last name must be with valid characters");
    		flag=false;
    	}
    	if(!isNumeric(id_lbl.getText()))
    	{
    		id_error_lbl.setText("ID must be a number");
    		flag=false;
    	}
    	else if (id_lbl.getText().length()!=9) {
    		id_error_lbl.setText("ID must be 9 digits");
    		flag=false;
    	}
    	if(!isNumeric(telephone_lbl.getText())) {
    		phone_error_lbl.setText("Phone must a number");
    		flag=false;
    	}
    	else if (telephone_lbl.getText().length()!=10) {
    		phone_error_lbl.setText("Number must be 10 digits");
    		flag=false;
    	}
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
    /**
     * Function to validate correct email address (ending with shtrudol and .com and so on)*/
  	private boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
}

  	private boolean isNumeric(String strNum) {
  	    if (strNum == null) {
  	        return false; 
  	    }
  	    return pattern.matcher(strNum).matches();
  	}
  	private boolean isValidName(String name) {
  		 if (name == null) {
   	        return false; 
   	    }
   	    return namePattern.matcher(name).matches();
  	}
 

}

