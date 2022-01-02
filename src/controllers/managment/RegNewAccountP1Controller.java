package managment;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import entity.Account;
import general.MyListener;
import general.VerifyListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import order.QRSimulationController;

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
    
    @FXML
    private Hyperlink UserHasAccount_lbl;
    
    public static Account new_account;
    
    
    private Pattern namePattern=Pattern.compile("^(?=.{2,40}$)[a-zA-Z]+(?:[-'\\s][a-zA-Z]+)*$");
    private Pattern VALID_EMAIL_ADDRESS_REGEX = 
    	    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
  
    private Pattern pattern = Pattern.compile("\\d+");
    
    
   public void initialize() {
	   
	   
	 if(Globals.AccountInfoArr!=null) {
		 first_name_lbl.setText(Globals.AccountInfoArr[0]);
		 last_name_lbl.setText(Globals.AccountInfoArr[1]);
		 id_lbl.setText(Globals.AccountInfoArr[2]);
		 telephone_lbl.setText(Globals.AccountInfoArr[3]);
		 email_lbl.setText(Globals.AccountInfoArr[4]);
		 confirm_email_lbl.setText(Globals.AccountInfoArr[5]);
	 }
		   
	   Globals.VerifyInputListener(first_name_lbl, new VerifyListener() {

		@Override
		public boolean verify() {
			
			if(first_name_lbl.getText().trim().isEmpty()){
	    		first_error_lbl.setText("Must input a first name");
	    		return false;}
			if(isValidName(first_name_lbl.getText())) {
				first_error_lbl.setText("");
				return true;
			}
			else {
				first_error_lbl.setText("Must have valid chars, above 2 letters");
				return false;
			}
				
		}
		  
	   });
	   
	   Globals.VerifyInputListener(last_name_lbl, new VerifyListener() {

			@Override
			public boolean verify() {
				if(last_name_lbl.getText().trim().isEmpty()){
		    		last_error_lbl.setText("Must input a last name");
		    		return false;
		    	}
				if(isValidName(last_name_lbl.getText())) {
					last_error_lbl.setText("");
					return true;
				}
				else {
					last_error_lbl.setText("Must have valid chars, above 2 letters");
					return false;
				}
			}
			  
		   });
	   
	   Globals.VerifyInputListener(email_lbl, new VerifyListener() {

		@Override
		public boolean verify() {
			
			
			if(email_lbl.getText().isEmpty()) {
				email_error_lbl.setText("Must input email");
	    		return false;
			}
			
			if(!validateEmail(email_lbl.getText())) {
	    		email_error_lbl.setText("Email address must be valid");
	    		return false;
	    	}
			else
			{
				 StartClient.order.accept("Check_account_info~Email~"+email_lbl.getText());
		 			if(OrderClient.AccountInfo == true) {
		 				email_error_lbl.setText("Account with this email already exists");
		 				return false;
		 			}
		 			else {
		 				email_error_lbl.setText("");
		 				return true;
		 			}
			}
			
		}
	   });
	   
	   Globals.VerifyInputListener(confirm_email_lbl, new VerifyListener() {

		@Override
		public boolean verify() {
			
			if(confirm_email_lbl.getText().isEmpty()) {
				email_error_lbl.setText("Must input email confirmation");
	    		return false;
			}
			
			if(!email_lbl.getText().equals(confirm_email_lbl.getText())) {
	    		confirm_email_error_lbl.setText("Emails must match");
	    		return false;
	    	}
			else {
				email_error_lbl.setText("");
				return true;
			}
		}
		   
		   
		   
	   });
	   
	   Globals.VerifyInputListener(telephone_lbl, new VerifyListener() {

		@Override
		public boolean verify() {
			int flag=0;
		
			 if(!telephone_lbl.getText().isEmpty() && !telephone_lbl.getText().equals("0")) { 
				 if(!isNumeric(telephone_lbl.getText())) {
				   		phone_error_lbl.setText("Phone must be a number");
				   		return false;
				   	}
				 else
					 flag++;
					
				  if (telephone_lbl.getText().length()!=10) {
				   		phone_error_lbl.setText("Number must be 10 digits");
				   		return false;
				   	}
				  else 
					  flag++;
				  if(flag==2) {
					  StartClient.order.accept("Check_account_info~Telephone~"+telephone_lbl.getText());
			 			if(OrderClient.AccountInfo == true) {
			 				phone_error_lbl.setText("Account with this number already exists");
			 				return false;
			 			}
			 			else {
			 				phone_error_lbl.setText("");
			 				return true;
			 			}
				  }
			 }
			 
				 phone_error_lbl.setText("");
	 				return true;
			
				 
		}
				    
	   });
	   
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
	    	}
			else {
				
				StartClient.order.accept("Check_account_info~ID~"+id_lbl.getText());
	 			if(OrderClient.AccountInfo == true) {
	 				id_error_lbl.setText("Account with this ID already exists");
	 				return false;
	 			}
	 			StartClient.order.accept("Check_user~"+id_lbl.getText());
	 			if(OrderClient.AccountInfo==false) {
	 				id_error_lbl.setText("No valid user with this ID");
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
     * Function to go to next page, account details in this page are saved to new_account*/
    @FXML
    void NextPage(ActionEvent event) {
    	String phone="0";
    	if(!telephone_lbl.getText().isEmpty()) phone=telephone_lbl.getText();
    	new_account= new Account(first_name_lbl.getText(),last_name_lbl.getText(),id_lbl.getText(),phone,email_lbl.getText());
    	if(CheckInput()){//if input is valid for this page we can move to the next page
    		Globals.AccountInfoArr=new String[] {first_name_lbl.getText(),last_name_lbl.getText(),id_lbl.getText(),telephone_lbl.getText(),email_lbl.getText(),confirm_email_lbl.getText()};
    		Globals.loadInsideFXML( Globals.regnewaccountp2FXML);
    	}
    		

    }
    
    /**
     * Private function CheckInput checks for valid input in Account registration form
     * returns True for valid input, False for invalid input */
    private boolean CheckInput() {
    	boolean flag=true;
    	email_error_lbl.setText("");
    	confirm_email_error_lbl.setText("");
    	id_error_lbl.setText("");
    	phone_error_lbl.setText("");	
    	first_error_lbl.setText("");
    	last_error_lbl.setText("");
    	
    	if(id_lbl.getText().trim().isEmpty()){
    		id_error_lbl.setText("Must input ID");
    		flag=false;
    	}
    	if(first_name_lbl.getText().trim().isEmpty()){
    		first_error_lbl.setText("Must input a first name");
    		flag=false;
    	}
    	else if(!isValidName(first_name_lbl.getText())){
    		first_error_lbl.setText("valid characters only");
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
    	if(!telephone_lbl.getText().isEmpty()) {
	    	if(!isNumeric(telephone_lbl.getText())) {
	    		phone_error_lbl.setText("Phone must be a  number");
	    		flag=false;
	    	}
	    	else if (telephone_lbl.getText().length()!=10) {
	    		phone_error_lbl.setText("Number must be 10 digits");
	    		flag=false;
	    	}
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
    		

    	if(flag==true) {
           	StartClient.order.accept("Check_account_input~"+new_account.toString());
           	if(OrderClient.account_reg_errors.get("Errors")==false) {//no errors found in input
           		return flag;
           	}
           	else
           	{
           		flag=false;
           		if(OrderClient.account_reg_errors.get("ID")==true) {
           			id_error_lbl.setText("Account with this ID already exists");
           		}
           		if(OrderClient.account_reg_errors.get("Telephone")==true && !telephone_lbl.getText().isEmpty())
           			phone_error_lbl.setText("Account with this Telephone already exists");
           		if(OrderClient.account_reg_errors.get("Email")==true)
           			email_error_lbl.setText("Account with this Email already exists");
           		if(OrderClient.account_reg_errors.get("UserID")==true)
           			id_error_lbl.setText("No User with this ID ");
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
  	
  	
    @FXML
    void openReg2(ActionEvent event) {
    	try {

        	FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/branch_manager/UserAlreadyHasAccountScreen.fxml"));
            VBox anchorPane;
            anchorPane = fxmlLoader.load();
            //UserAlreadyHasAccountController uController = fxmlLoader.getController();
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);    
            Scene dialogScene = new Scene(anchorPane);
            dialog.setScene(dialogScene);

            dialog.show();
            
        	
        	}
        	catch (Exception e) {
    			// TODO: handle exception
    		}

    }
  	
  }


