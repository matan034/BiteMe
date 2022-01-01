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
	    		vbox.getChildren().add(new Label("Time: "+o.getOrder_time()));
	    		vbox.getChildren().add(new Label("By: "+o.getOrder_type()));
	    		if(OrderClient.IsOrderApproved.get(o.getOrder_num())==0) {
	    			Button button = new Button("Approve");
	    			vbox.getChildren().add(button);
	    			TitledPane panenew = new TitledPane("Order #"+""+o.getOrder_num(),vbox);
	    			panenew.getStyleClass().add("titled-pane");
	    			neworders_accordion.getPanes().add(panenew);
	    			button.setOnAction((new EventHandler<ActionEvent>() {
	    				@Override
	    				 public void handle(ActionEvent e) {
	    					StartClient.order.accept("Approve_order~"+o.getOrder_num());
	    					button.setVisible(false);
	    					TitledPane pane1 = new TitledPane("Order #"+""+o.getOrder_num(),vbox);
	    	    			historyaccordion.getPanes().add(pane1);
	    	    			neworders_accordion.getPanes().remove(panenew);
	    	    			
	    					
	    				}
	    			}));
	    			
	
	    		}
	    		else {
	    			TitledPane pane1 = new TitledPane("Order #"+""+o.getOrder_num(),vbox);
	    			historyaccordion.getPanes().add(pane1);
	    		}
	    		
	    		
	    		
	    	}
    	}
    	else {
    		no_orders_lbl.setVisible(true);
    		no_history_lbl.setVisible(true);
    	}
    		
    }


}
