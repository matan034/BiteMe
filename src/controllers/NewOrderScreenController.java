package controllers;

import clients.OrderClient;
import clients.StartClient;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class NewOrderScreenController {

    @FXML
    private VBox vbox;
    
    public void initialize() {
    	StartClient.order.accept("Load_orders");
    	System.out.println("YAY");
    	//if(OrderClient.ordersInBranch.size()==0)
    		
    }

}
