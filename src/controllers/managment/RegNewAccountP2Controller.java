package managment;


import java.util.regex.Pattern;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import entity.Account;
import entity.BusinessAccount;
import entity.PrivateAccount;
import general.VerifyListener;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 *Class for the second part of user registration here the user entersr private account details or business account details, once user presses create the account is created
 *data for account is taken from P1 Controller. user has an option between private account,business account or both.

 * @author      daniel aibinder
 * @version     1.0               
 * @since       01.01.2022        
 */



public class RegNewAccountP2Controller {

	/**text field for creditcard*/
	 @FXML
	    private TextField cc_lbl;
	 /** employer name text field */
	    @FXML
	    private TextField employer_name_lbl;
	    /**back button to return to P1 controller*/
	    @FXML
	    private Button back_btn;
	    /**button to register the account*/
	    @FXML
	    private Button reg_btn;
	    /**result label to display messages to user*/
	    @FXML
	    private Label res_lbl;
	    /**error label for creditcarad input errors*/
	    @FXML
	    private Label cc_error_lbl;
	    /**error label for employer name errors*/
	    @FXML
	    private Label employer_name_error_lbl;
	    /**text area for entering budget*/
	    @FXML
	    private TextField budget_lbl;
	    /**error label for budget errors*/
	    @FXML
	    private Label budget_error_lbl;
	    /**checkbox to select create a business account*/
	    @FXML
	    private CheckBox BusinessCheckBox;
	    /**checkbox to create private account*/
	    @FXML
	    private CheckBox PrivateCheckBox;
	    /**pattern to confirm an integer*/
	    @FXML
	    private ImageView green_v_img;
	    /**patern for validate string as integer*/
	    private Pattern pattern = Pattern.compile("\\d+");
	    
	    
	    /**
	     * initialzes our controller, by setting text areas to be disabled unless the correct check box is ticked 
	     * also sets listeners to verify input
	     * checks if user already has an account in that case we update the account data
	     * if user doesnt have an account we create a new account using our private or business entitys which we send to server using 
	     * the entitys to string*/
	    public void initialize() {
	    	cc_lbl.setText("");
	    	employer_name_lbl.setText("");
	    	budget_lbl.setText("");
	    	if(UserAlreadyHasAccountController.account_id!=null) {
	    		StartClient.order.accept("Load_customer~"+UserAlreadyHasAccountController.account_id);
	    		if(OrderClient.customer.getpAccount()!=0) {
	    			StartClient.order.accept("Load private Account~"+OrderClient.customer.getpAccount());
	    			cc_lbl.setText(OrderClient.paccount.getCreditCardNumber());
	    			}
	    		if(OrderClient.customer.getbAccount()!=0) {
	    			StartClient.order.accept("Load business Account~"+OrderClient.customer.getbAccount());
	    			employer_name_lbl.setText(OrderClient.baccount.getEmployerName());
	    			budget_lbl.setText(OrderClient.baccount.getBudget()+"");
	    		}
	    	}
	    	cc_lbl.setDisable(true);
	    	employer_name_lbl.setDisable(true);
	    	budget_lbl.setDisable(true);
	    	PrivateCheckBox.selectedProperty().addListener((obs, oldSelection, newSelection) -> {
	    		if(newSelection!=null) {
	    			try {
	    				PrivateCheckOnChange();
	    			}catch(Exception e) {e.printStackTrace();}
	    			
	    		}
	    	});
	    	BusinessCheckBox.selectedProperty().addListener((obs, oldSelection, newSelection) -> {
	    		if(newSelection!=null) {
	    			try {
	    				BusinessCheckOnChange();
	    			}catch(Exception e) {e.printStackTrace();}
	    			
	    		}
	    	});
	    	
	    	
	    	 Globals.VerifyInputListener(cc_lbl, new VerifyListener() {

				@Override
				public boolean verify() {
					
					if(!isNumeric(cc_lbl.getText()))
					{
						cc_error_lbl.setText("Valid characters only");
						return false;
					}
					
					if(cc_lbl.getText().trim().isEmpty()) {
						cc_error_lbl.setText("Must input CC");
						return false;
					}
					else if(cc_lbl.getText().length()!=16) {
			    			cc_error_lbl.setText("Must input 16 digits");
			    			return false;
					}
					else {
						cc_error_lbl.setText("");
						return true;
					}
						
					
						
						
				}
	    		 
	    	 });
	    	 
	    	 
	    	 Globals.VerifyInputListener(employer_name_lbl, new VerifyListener() {

				@Override
				public boolean verify() {
					if(employer_name_lbl.getText().isEmpty()) {
						employer_name_error_lbl.setText("Must input Employer Name");
						return false;
					}
					
					else if(!CheckEmployer(employer_name_lbl.getText()))
						return false;
					else
						return true;
						
				}
	    		 
	    	 });
	    	 
	    	 Globals.VerifyInputListener(budget_lbl, new VerifyListener() {

				@Override
				public boolean verify() {
					return checkBudget();
					
				} 
	    		 
	    	 });
	    	 
	    	 
	    	 
	    	 
	    	 
	    }
	    /**
	     * disable and enable credit card text area when user clicks the private account checkbox */
	    private void PrivateCheckOnChange() {
	    	if(cc_lbl.isDisable()) {
	    		cc_lbl.setDisable(false);
	    	}
	    	else {
	    		cc_lbl.setDisable(true);
	    		cc_lbl.clear();
	    	}
	    		
	    }
	    /**
	     * disable and enable employer name text area and budget according to when user clicks business account checkbox */
	    private void BusinessCheckOnChange() {
	    	if(employer_name_lbl.isDisable() && budget_lbl.isDisable()) {
	    		employer_name_lbl.setDisable(false);
	    		budget_lbl.setDisable(false);
	    	}
	    	else {
	    		employer_name_lbl.clear();
	    		budget_lbl.clear();
	    		employer_name_lbl.setDisable(true);
	    		budget_lbl.setDisable(true);
	    	}
	    }
	    
	    /**
	     * func to go back to P1 controller page on back button click
	     * @param event ActionEvent for event details */
	    @FXML
	    void BackPage(ActionEvent event) {
	    	UserAlreadyHasAccountController.account_id=null;
	    	Globals.loadInsideFXML(Globals.regnewaccountp1FXML);
	    }
	
	
	    /**
	     * function where we check which checkboxes the user has selcted and make an account with our entitys accordingly 
	     * that account we create we send to another func that transfers the account to server to be added to database
	     * @param event Actionevent for event details  */
	   @FXML
	    void RegisterAccount(ActionEvent event) {
		   budget_error_lbl.setText("");
		   res_lbl.setText("");
		   employer_name_error_lbl.setText("");
	    	PrivateAccount pAccount=null;
	    	BusinessAccount bAccount=null;
	    	if(PrivateCheckBox.isSelected()) {
		    	if(!cc_lbl.getText().trim().isEmpty()) {//check cc text field is not empty
		    		if(cc_lbl.getText().length()!=16)//
		    			cc_error_lbl.setText("Must input 16 digits");
		    		else {//if its valid create a private account entity
		    			if(UserAlreadyHasAccountController.account_id==null) //does not have an account create a new account
				    		pAccount=new PrivateAccount(RegNewAccountP1Controller.new_account);
		    			else if(OrderClient.customer.getpAccount()!=0)//THERE IS A p account
		    				pAccount=OrderClient.paccount;
		    			else {
		    				pAccount=new PrivateAccount(OrderClient.baccount.getFirstName(),OrderClient.baccount.getLastName(),OrderClient.baccount.getID(),OrderClient.baccount.getTelephone(),OrderClient.baccount.getEmail(),0,"");
		    			}
		    			if(pAccount!=null)
		    				pAccount.setCreditCardNumber(cc_lbl.getText());
		    		}	
		    			
		    	}
	    	}
	    	if(BusinessCheckBox.isSelected()) {
		    	if(!employer_name_lbl.getText().trim().isEmpty() && !budget_lbl.getText().trim().isEmpty()) {
		    		if(CheckEmployer(employer_name_lbl.getText()) && checkBudget()) {
		    			if(UserAlreadyHasAccountController.account_id==null)
		    				bAccount=new BusinessAccount(RegNewAccountP1Controller.new_account);
		    			else if(OrderClient.customer.getbAccount()!=0)
		    				bAccount=OrderClient.baccount;
		    			else {
		    				bAccount=new BusinessAccount(new Account(OrderClient.paccount.getFirstName(),OrderClient.paccount.getLastName(),OrderClient.paccount.getID(),OrderClient.paccount.getTelephone(),OrderClient.paccount.getEmail(),OrderClient.paccount.getAccountNum()));
		    			}
		    				
			    		bAccount.setEmployerName(employer_name_lbl.getText());
			    		bAccount.setBudget(Integer.parseInt(budget_lbl.getText()));
		    		}
		    	
		    	}
	    	}
	    	if(PrivateCheckBox.isSelected() && (!BusinessCheckBox.isSelected())) {
		    	createPaccount(pAccount);
		    	if(pAccount!=null) {
		    		UserAlreadyHasAccountController.account_id=null;
		    		Globals.loadInsideFXML(Globals.homeScreen);
		    	}
		    	
	    	}
	    	if(!PrivateCheckBox.isSelected() && BusinessCheckBox.isSelected()) {
	    		createBaccount(bAccount);
	    		if(bAccount!=null) {
	    			UserAlreadyHasAccountController.account_id=null;
	    			Globals.loadInsideFXML(Globals.homeScreen);
	    		}
	    	}
	    	if(PrivateCheckBox.isSelected() && BusinessCheckBox.isSelected()) {
	    		createPaccount(pAccount);
	    		createBaccount(bAccount);
	    		if(bAccount!=null && pAccount!=null) {
	    			UserAlreadyHasAccountController.account_id=null;
	    			Globals.loadInsideFXML(Globals.homeScreen);
	    			}
	    	}
	    	
	    	if(!PrivateCheckBox.isSelected() && !BusinessCheckBox.isSelected()) res_lbl.setText("Please selected an account option");
	    	
	    	if(BusinessCheckBox.isSelected()) {
	    		if(employer_name_lbl.getText().isEmpty()) employer_name_error_lbl.setText("Must Input employer name");
	    		if(budget_lbl.getText().isEmpty())budget_error_lbl.setText("Must input budget");
	    	}
	    	
	    	if(employer_name_lbl.getText().trim().isEmpty() && cc_lbl.getText().trim().isEmpty())//user didn't input anything in textfields
	    	{
	    		res_lbl.setText("Must input credit card or employer name");
	    	}
	    	
	    	
	    }
	   
	   /**
	     * func for checking if an employer name is valid we do so by checking in the server that the employer is registered and approved
	     * @param employer_name name of employer that we check */
	   private boolean CheckEmployer(String employer_name) {
		   employer_name_error_lbl.setText("");
		   StartClient.order.accept("Check_employer~"+employer_name);
		   
		   if(OrderClient.employer_reg_errors.get("Errors")==false) {//no errors found in input
          		return true;
          	}
		   else {
			   if(OrderClient.employer_reg_errors.get("NameError")) { employer_name_error_lbl.setText("No such employer name"); return false;}
			   else if(OrderClient.employer_reg_errors.get("ApprovedError")) { employer_name_error_lbl.setText("Employer not approved yet"); return false;}
				   
		   }
		   return true;
	   }
	   
	   /**
	     * this func checks if a string is numeric
	     * @param strNum string that we check */
	   private boolean isNumeric(String strNum) {
	  	    if (strNum == null) {
	  	        return false; 
	  	    }
	  	    return pattern.matcher(strNum).matches();
	  	}
	   
	   /**
	     * function to check if budget has been inputted correctly using regex */
	   private boolean checkBudget() {
		   if(budget_lbl.getText().isEmpty()) {
				budget_error_lbl.setText("Must input a budget");
				return false;
			}
			
			if(!isNumeric(budget_lbl.getText())) {
				budget_error_lbl.setText("Must input numbers");
				return false;
			}
			else {
				budget_error_lbl.setText("");
				return true;
			}
	   }
	   
	   /**
	     * this function sends a private account to the server to be created or updated
	     * @param pAccount PrivateAccount entity the account that we create or update  */
	   private void createPaccount(PrivateAccount pAccount) {
		   if(pAccount!=null) {
			   if(UserAlreadyHasAccountController.account_id!=null) {//USER ALREADY HAS AN ACCOUNT
		    		if(OrderClient.customer.getpAccount()==0) {
		    			StartClient.order.accept(pAccount.toString());}
		    		else
		    			StartClient.order.accept("Update_private_account~"+pAccount.getCreditCardNumber()+"~"+pAccount.getAccountNum());
		    	}
			   else
				   StartClient.order.accept(pAccount.toString());
	    		
	    		Globals.AccountInfoArr=null;
	
		    	
	    	}
	   }
	   
	   
	   /**
	     * this function sends a business account to the server to be created or updated
	     * @param bAccount businessAccount entity the account that we create or update */
	   private void createBaccount(BusinessAccount bAccount) {
		   if(bAccount!=null) {
			   if(UserAlreadyHasAccountController.account_id!=null) { 
		    		if(OrderClient.customer.getbAccount()==0)
		    			StartClient.order.accept(bAccount.toString());
		    		else
		    			StartClient.order.accept("Update_business_account~"+bAccount.getEmployerName()+"~"+bAccount.getBudget()+"~"+bAccount.getAccountNum());
			   }
			   else
				   StartClient.order.accept(bAccount.toString());
	    		Globals.AccountInfoArr=null;

	    	}
	   }
}
