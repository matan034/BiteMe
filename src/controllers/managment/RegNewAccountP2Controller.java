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
	    private Label budget_error_lbl;
	    
	    @FXML
	    private CheckBox BusinessCheckBox;

	    @FXML
	    private CheckBox PrivateCheckBox;
	    
	    @FXML
	    private ImageView green_v_img;
	    
	    private Pattern pattern = Pattern.compile("\\d+");
	    
	    public void initialize() {
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
		    			if(UserAlreadyHasAccountController.account_id==null) 
				    		pAccount=new PrivateAccount(RegNewAccountP1Controller.new_account);
		    			else
		    				pAccount=OrderClient.paccount;
		    			
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
		    	if(pAccount!=null)
		    		Globals.loadInsideFXML(Globals.homeScreen);
		    	
	    	}
	    	if(!PrivateCheckBox.isSelected() && BusinessCheckBox.isSelected()) {
	    		createBaccount(bAccount);
	    		if(bAccount!=null)
	    			Globals.loadInsideFXML(Globals.homeScreen);
	    	}
	    	if(PrivateCheckBox.isSelected() && BusinessCheckBox.isSelected()) {
	    		createPaccount(pAccount);
	    		createBaccount(bAccount);
	    		if(bAccount!=null && pAccount!=null)
	    			Globals.loadInsideFXML(Globals.homeScreen);
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
	   private boolean isNumeric(String strNum) {
	  	    if (strNum == null) {
	  	        return false; 
	  	    }
	  	    return pattern.matcher(strNum).matches();
	  	}
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
	   private void createPaccount(PrivateAccount pAccount) {
		   if(pAccount!=null) {
	    		if(OrderClient.customer.getpAccount()==0)
	    			StartClient.order.accept(pAccount.toString());
	    		else
	    			StartClient.order.accept("Update_private_account~"+pAccount.getCreditCardNumber()+"~"+pAccount.getAccountNum());
	    		green_v_img.setVisible(true);
		    	/*PauseTransition pause = new PauseTransition(Duration.seconds(1));
		    	pause.setOnFinished(e -> green_v_img.setVisible(false));
		    	pause.play();*/
		    	
	    	}
	   }
	   private void createBaccount(BusinessAccount bAccount) {
		   if(bAccount!=null) {
	    		if(OrderClient.customer.getbAccount()==0)
	    			StartClient.order.accept(bAccount.toString());
	    		else
	    			StartClient.order.accept("Update_business_account~"+bAccount.getEmployerName()+"~"+bAccount.getBudget()+"~"+bAccount.getAccountNum());
	    		/*PauseTransition pause = new PauseTransition(Duration.seconds(1));
		    	pause.setOnFinished(e -> green_v_img.setVisible(false));
		    	pause.play();*/
	    	}
	   }
}
