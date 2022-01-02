package managment;

import clients.OrderClient;
import clients.StartClient;
import entity.Order;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class NewOrderScreenController {
	
	
    @FXML
    private Accordion neworders_accordion;

    @FXML
    private Accordion historyaccordion;
    
    @FXML
    private Label no_history_lbl;
    
    @FXML
    private Label no_orders_lbl;


    @FXML
    private Accordion orders_to_deliver_accordion;

    @FXML
    private Label No_delivery_lbl;
    
    public void initialize() {
    	StartClient.order.accept("Load_orders~"+OrderClient.user.getID());
    	if(OrderClient.OrdersInBranch.size()!=0) {
    	
	    	for(Order o:OrderClient.OrdersInBranch.values())
	    	{
	    		StartClient.order.accept("Check_approved~"+o.getOrder_num());
	    		VBox vbox = new VBox(new Label(o.getRecieving_name()+" Ordered:"));
	    		for(String dish:o.getDishesInOrder()) {
	    			vbox.getChildren().add(new Label(dish));
	    		}
	    		TitledPane panenew = new TitledPane("Order #"+""+o.getOrder_num(),vbox);
	    		panenew.getStyleClass().add("titled-pane");
	    		vbox.getChildren().add(new Label("Time: "+o.getOrder_time()));
	    		vbox.getChildren().add(new Label("By: "+o.getOrder_type()));
	    		if(OrderClient.IsOrderApproved.get(o.getOrder_num()).get(0)==0) { //ORDER Not Approved
	    			Button button = defineTitledPane("Approve", o, neworders_accordion, vbox,panenew);
	    			vbox.getChildren().add(button);
	    			neworders_accordion.getPanes().add(panenew);
			
	
	    		}
	    		else if(OrderClient.IsOrderApproved.get(o.getOrder_num()).get(1)==1) { // ORDER HAS BEEN DELIVERED
	    			TitledPane pane1 = new TitledPane("Order #"+""+o.getOrder_num(),vbox);
	    			historyaccordion.getPanes().add(pane1);
	    		}
	    		else {//APPROVED BUT NOT DELIVERED YET
	    			Button button = defineTitledPane("Deliver", o, orders_to_deliver_accordion, vbox,panenew);
	    			vbox.getChildren().add(button);
	    			orders_to_deliver_accordion.getPanes().add(panenew);

	    		}
	    		
	    		
	    		
	    	}
    	}
    	else {
    		No_delivery_lbl.setVisible(true);
    		no_orders_lbl.setVisible(true);
    		no_history_lbl.setVisible(true);
    	}
    		
    }
    
	public Button defineTitledPane(String str,Order o,Accordion accordion,VBox vbox,TitledPane panenew)
	{
		
		Button button = new Button(str);
		button.getStyleClass().add("ViewBtn");

		button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	switch(str)
            	{
            	 	case "Approve":	StartClient.order.accept("Approve_order~"+o.getOrder_num());
            	 					TitledPane pane1 = new TitledPane("Order #"+""+o.getOrder_num(),vbox);
            	 					orders_to_deliver_accordion.getPanes().add(pane1);
            	 					vbox.getChildren().remove(button);
            	 					neworders_accordion.getPanes().remove(panenew); 
            	 					
            	 					 break;
            	 	case "Deliver":	StartClient.order.accept("Deliver_order~"+o.getOrder_num());
            	 					button.setVisible(false);
            	 					TitledPane pane2 = new TitledPane("Order #"+""+o.getOrder_num(),vbox);
            	 					historyaccordion.getPanes().add(pane2);
            	 					orders_to_deliver_accordion.getPanes().remove(panenew);break;
            	}

            }});
		return button;
	}


}