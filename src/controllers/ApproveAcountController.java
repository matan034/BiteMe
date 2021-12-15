package controllers;

import clients.StartClient;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;

public class ApproveAcountController {

    @FXML
    private Accordion accordion;
    
    public void initialize() {
    	StartClient.order.accept("Load_business_account");
    	
    	
    }

}
