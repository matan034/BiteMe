package controllers;


import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import entity.BusinessAccount;
import entity.PrivateAccount;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegNewAccountP2Controller {

	 @FXML
	    private TextField cc_lbl;

	    @FXML
	    private TextField employer_name_lbl;

	    @FXML
	    private Button back_btn;

	    @FXML
	    private Button reg_btn;
	    
	    @FXML
	    private Label res_lbl;
	    
	    @FXML
	    private Label cc_error_lbl;

	    @FXML
	    private Label employer_name_error_lbl;
	    
	    @FXML
	    private TextField budget_lbl;

	    @FXML
	    void BackPage(ActionEvent event) {
	    	Globals.loadFXML(null, Globals.regnewaccountp1FXML, event);
	    }
	
	
	
	   @FXML
	    void RegisterAccount(ActionEvent event) {
		   res_lbl.setText("");
		   employer_name_error_lbl.setText("");
	    	PrivateAccount pAccount=null;
	    	BusinessAccount bAccount=null;
	    	if(!cc_lbl.getText().trim().isEmpty()) {//check cc text field is not empty
	    		if(Integer.parseInt(cc_lbl.getText())<16)//atleast 16 digits in credit card
	    			cc_error_lbl.setText("Must input atleast 16 digits");
	    		else {//if its valid create a private account entity
		    		pAccount=new PrivateAccount(RegNewAccountP1Controller.new_account);
		    		pAccount.setCreditCardNumber(cc_lbl.getText());
	    		}	
	    			
	    	}
	    	if(!employer_name_lbl.getText().trim().isEmpty() && !budget_lbl.getText().trim().isEmpty()) {
	    		if(CheckEmployer(employer_name_lbl.getText())) {
	    			bAccount=new BusinessAccount(RegNewAccountP1Controller.new_account);
		    		bAccount.setEmployerName(employer_name_lbl.getText());
		    		bAccount.setBudget(budget_lbl.getText());
	    		}
	    	
	    	}
	    	if(pAccount!=null) {
	    		StartClient.order.accept(pAccount.toString());
	    	}
	    	if(bAccount!=null) {
	    		StartClient.order.accept(bAccount.toString());
	    	}
	    	if(employer_name_lbl.getText().trim().isEmpty() && cc_lbl.getText().trim().isEmpty())//user didn't input anything in textfields
	    	{
	    		res_lbl.setText("Must input credit card or employer name");
	    	}
	    	
	    }
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
}
