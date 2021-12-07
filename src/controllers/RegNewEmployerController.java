package controllers;

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
    void RegisterEmployer(ActionEvent event) {
    		
    	
    	Employer employer=new Employer(name_lbl.getText(),address_lbl.getText(),number_lbl.getText());
    	StartClient.order.accept("Reg_employer~"+employer.toString());
    	res_lbl.setText(OrderClient.employer_reg_msg);
    }

}
