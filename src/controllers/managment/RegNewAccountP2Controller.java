package managment;


import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import entity.BusinessAccount;
import entity.PrivateAccount;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
	    private CheckBox BusinessCheckBox;

	    @FXML
	    private CheckBox PrivateCheckBox;
	    
	    public void initialize() {
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
	    }

	    private void PrivateCheckOnChange() {
	    	if(cc_lbl.isDisable()) {
	    		cc_lbl.setDisable(false);
	    	}
	    	else {
	    		cc_lbl.setDisable(true);
	    		cc_lbl.clear();
	    	}
	    		
	    }
	    
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
	    @FXML
	    void BackPage(ActionEvent event) {
	    	Globals.loadInsideFXML(Globals.regnewaccountp1FXML);
	    }
	
	
	
	   @FXML
	    void RegisterAccount(ActionEvent event) {
		   res_lbl.setText("");
		   employer_name_error_lbl.setText("");
	    	PrivateAccount pAccount=null;
	    	BusinessAccount bAccount=null;
	    	if(!cc_lbl.getText().trim().isEmpty()) {//check cc text field is not empty
	    		if(cc_lbl.getText().length()<16)//atleast 16 digits in credit card
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
		    		bAccount.setBudget(Integer.parseInt(budget_lbl.getText()));
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
