package managment;


import entity.Employer;
import general.MyListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;

/**
 *This class recives data from MyEmployersController, its purpose is to set each employer to it's correct accordion(users that need to be approved to waiting_for_approval_accordion)
 *users that have been approved to the approved_accordion we do this by setting the employers address and telephone and loading this MyEmployersRecord FXML file into My EmployerScreen FXML
 * @author      matan weisberg
 * @version     1.0               
 * @since       01.01.2022   
 
 */



public class EmployerRecordController {
	/** hbox where we hold employer details*/
    @FXML
    private HBox employer_details_hbox;
    /**label  for employers address*/
    @FXML
    private Label address_lbl;
    /**label for employers telephone*/
    @FXML
    private Label telephone_lbl;
    /**Our employee that we recieve*/
    private Employer emp;
    /**Listener we recieve when setting data from MyEmployersController*/
    private MyListener approveEmployer;
    
    /**
     * setter function to set emp, approveEmployer , the classes labels 
     * @param emp an employee from MyEmployersContoler we use the employe to set label details
     * @param approveEmployer a listener from EmployerController that is set to approves the employer in database*/
    public void setData(Employer emp,MyListener approveEmployer)
    {

    	this.approveEmployer=approveEmployer;
    	this.emp=emp;
    	address_lbl.setText(emp.getAddress());
    	telephone_lbl.setText(emp.getPhone());
    }
    
    /**
     * function used to approve the Employer, we move the employer to the correct accordion accordion to if it's been approved.
     * @param waiting_for_approval_accordion accordion we remove the employer from
     * @param approved_accordion acoordion we move the used to once he is approved
     * @param pane Pane we remove from waiting_for_approval and move to approved_accordion
     * */
    public void setApproval(Accordion waiting_for_approval_accordion,Accordion approved_accordion,TitledPane pane )
    {
    	Button approve = new Button("Approve");
    	Label approved = new Label("Approved");
    	approve.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	approveEmployer.onClickListener(emp);
		    	employer_details_hbox.getChildren().remove(approve);
		    	employer_details_hbox.getChildren().add(approved);	
		    	waiting_for_approval_accordion.getPanes().remove(pane);
    			approved_accordion.getPanes().add(pane);
		    }});
    	
    	employer_details_hbox.getChildren().add(approve);	
    	
    			
    }

}
