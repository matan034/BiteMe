package managment;

import java.util.regex.Pattern;

import clients.OrderClient;
import clients.StartClient;
import entity.Employer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


/**
 *Class for registering a new employer in this class BM inputs an employer to register and approves that employer 
 *@param name_lbl = employer name text area
 *@param address_lbl = employer address text area
 *@param number_lbl = employer phone number text area
 *@param res_lbl = label to display messages to user
 *@param name_error_lbl = error label for name input
 *@param number_error_lbl = error label for phone input
 *@param employer = new employer that we create and send to server
 *@param namePattern = regex pattern to verify a valid name
 *@param phonePattern = regex pattern to verify a valid phone number
 * @author      matan weisberg
 * @version     1.0               
 * @since       01.01.2022        
 */


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
    
    /**
     * func for registering the employeer activated on register button press. this takes text areas fields and creates an employer and sends it to server to be registered
     * @param event Action event for event details*/
    @FXML
    void RegisterEmployer(ActionEvent event) {
    		
    	employer=new Employer(name_lbl.getText(),address_lbl.getText(),number_lbl.getText());
    	if(CheckInput()) {
    		StartClient.order.accept("Reg_employer~"+employer.toString()+"~"+OrderClient.user.getHomeBranch()+"~"+OrderClient.user.getID());
        	res_lbl.setText(OrderClient.employer_reg_msg);
    	}
    }
    /**
     * function for checking correct input using our regexes and other checks like valid length and so on 
     * also check in data base if the employer already exists
     * returns true if all input is ok 
     * returns false if theres an error*/
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
    
    /**
     *checks for valid name via regex pattern
     *@param name = name that we check
     *return true if valid
     *return valse if invalid*/
	private boolean isValidName(String name) {
 		 if (name == null) {
  	        return false; 
  	    }
  	    return namePattern.matcher(name).matches();
 	}
	
	 /**
     *func to check if string is numeric using regex
     *@param strNum = string we check
     *return true if strNum is a number
     *returns false if not a number */
	private boolean isNumeric(String strNum) {
  	    if (strNum == null) {
  	        return false; 
  	    }
  	    return phonePattern.matcher(strNum).matches();
  	}
}
