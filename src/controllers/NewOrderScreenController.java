package controllers;

import clients.OrderClient;
import clients.StartClient;
import entity.Order;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class NewOrderScreenController {
	
	
    @FXML
    private Accordion accordion;

    
    public void initialize() {
    	StartClient.order.accept("Load_orders");
    	System.out.println("YAY");
    	//if(OrderClient.ordersInBranch.size()==0)
    	
    	
    	for(Order o:OrderClient.ordersInBranch)
    	{
    		StartClient.order.accept("Check_approved~"+o.getOrder_num());
    		HBox hbox = new HBox(new Label(o.getDish_name()+" By: "+ o.getRecieving_name()+" "+o.getOrder_time()+" "+o.getOrder_type()));
    		if(OrderClient.IsOrderApproved.get(o.getOrder_num())==0) {
    			Button button = new Button("Approve");
    			button.setOnAction((new EventHandler<ActionEvent>() {
    				@Override
    				 public void handle(ActionEvent e) {
    					StartClient.order.accept("Approve_order~"+o.getOrder_num());
    					button.setText("Approved");
    				}
    			}));
    			hbox.getChildren().add(button);
    			

    		}
    		TitledPane pane1 = new TitledPane("Order #"+""+o.getOrder_num(),hbox);
    		accordion.getPanes().add(pane1);
    		
    	}
    		
    }

}
