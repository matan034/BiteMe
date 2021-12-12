package controllers;

import java.util.regex.Pattern;

import clients.OrderClient;
import clients.StartClient;
import entity.Employer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegNewEmployerController {

    @FXML
    private TextField name_lbl;

    @FXML
    private TextField address_lbl;

    @FXML
    private TextField number_lbl;
    
    @FXML
    private Label res_lbl;
    
    @FXML
    private Label name_error_lbl;

    @FXML
    private Label number_error_lbl;
    
    public static Employer employer;

    private Pattern namePattern=Pattern.compile("^(?=.{2,40}$)[a-zA-Z]+(?:[-'\\s][a-zA-Z]+)*$");
    private Pattern phonePattern = Pattern.compile("\\d+");
    @FXML
    void RegisterEmployer(ActionEvent event) {
    		
    	employer=new Employer(name_lbl.getText(),address_lbl.getText(),number_lbl.getText());
    	if(CheckInput()) {
    		StartClient.order.accept("Reg_employer~"+employer.toString());
        	res_lbl.setText(OrderClient.employer_reg_msg);
    	}
    }
    
    private boolean CheckInput() {
    	number_error_lbl.setText("");
    	name_error_lbl.setText("");
    	boolean flag=true;
    	if(name_lbl.getText().trim().isEmpty()){
    		name_error_lbl.setText("Must input Name");
    		flag=false;
    	}
    	else if (!isValidName(name_lbl.getText())) {
    		name_error_lbl.setText("Name must be with valid characters");
    		flag=false;
    	}
    	if(!isNumeric(number_lbl.getText())) {
    		number_error_lbl.setText("Phone must be a number");
    		flag=false;
    	}
    	else if (number_lbl.getText().length()!=10) {
    		number_error_lbl.setText("Number must be 10 digits");
    		flag=false;
    	}
    	if(flag==true) {
    		StartClient.order.accept("Check_employer~"+employer.toString());
 			   if(!OrderClient.employer_reg_errors.get("NameError")) { name_error_lbl.setText("Employer already exists"); return false;}
 				   
 		   }
    	return flag;
    	}
    
	private boolean isValidName(String name) {
 		 if (name == null) {
  	        return false; 
  	    }
  	    return namePattern.matcher(name).matches();
 	}
	private boolean isNumeric(String strNum) {
  	    if (strNum == null) {
  	        return false; 
  	    }
  	    return phonePattern.matcher(strNum).matches();
  	}
}
