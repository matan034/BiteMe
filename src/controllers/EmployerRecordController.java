package controllers;

import clients.StartClient;
import common.Globals;
import entity.DishInOrder;
import entity.Employer;
import entity.Order;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class EmployerRecordController {

    @FXML
    private HBox employer_details_hbox;

    @FXML
    private Label address_lbl;

    @FXML
    private Label telephone_lbl;
    private Employer emp;
    private MyListener approveEmployer;
    public void setData(Employer emp,MyListener approveEmployer)
    {

    	this.approveEmployer=approveEmployer;
    	this.emp=emp;
    	address_lbl.setText(emp.getAddress());
    	telephone_lbl.setText(emp.getPhone());
    }
    public void setApproval()
    {
    	Button approve = new Button("Approve");
    	Label approved = new Label("Approved");
    	approve.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	approveEmployer.onClickListener(emp);
		    	employer_details_hbox.getChildren().remove(approve);
		    	employer_details_hbox.getChildren().add(approved);	
		    }});
    	
    	employer_details_hbox.getChildren().add(approve);	
    	
    			
    }

}
