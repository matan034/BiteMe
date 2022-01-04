package managment;

import java.io.IOException;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import entity.Order;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import order.QRSimulationController;


/**
 * Class for viewing a restuarants new orders that have been recived, or orders that are in the making , or orders that have already been completed
 * we do this with 3 accordions we move between to display to the user the orders status (new,ready to deliver,history)

 * @author      dorin bahar
 * @version     1.0               
 * @since       01.01.2022        
 */

public class NewOrderScreenController {
	
	/**accordion for holding new orders*/
    @FXML
    private Accordion neworders_accordion;
    /**accordion to hold orders already completed*/
    @FXML
    private Accordion historyaccordion;
    
    /**label hidden by default and shown when there is no history*/
    @FXML
    private Label no_history_lbl;
    /**label hidden by default and shown when here is no orders*/
    @FXML
    private Label no_orders_lbl;

    /**accordion to hold orders that are waiting to get delivered*/
    @FXML
    private Accordion orders_to_deliver_accordion;
    /**label hidden by default and shown when here is no orders to deliver*/
    @FXML
    private Label No_delivery_lbl;
    
    
    /**
     * func to initalize our controller, here we load all orders by a specific customer
     * when regonize the orders status and set it to the correct accordion */
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
	    			Button button = defineButton("Approve",o,panenew,vbox);
	    			vbox.getChildren().add(button);
	    			neworders_accordion.getPanes().add(panenew);
			
	
	    		}
	    		else if(OrderClient.IsOrderApproved.get(o.getOrder_num()).get(1)==1) { // ORDER HAS BEEN DELIVERED
	    			TitledPane pane1 = new TitledPane("Order #"+""+o.getOrder_num(),vbox);
	    			historyaccordion.getPanes().add(pane1);
	    		}
	    		else {//APPROVED BUT NOT DELIVERED YET
	    			Button button = defineButton("Deliver",o,panenew,vbox);
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
    /**
     * define our button to have the correct action accordion to t he pane it is in 
     * if the order is in new orders we set the button to approve the order, send an sms to user and move the order to waiting for delivery
     * if the order is waiting for delivery the button will set the order to be out for delivery and move the order to history accordion
     * @param str value of button 
     * @param Order O current order the button is mapped to
     * @param pane the pane we remove and add once we move between accordions
     * @param vbox = vbox we remove the button from*/
	private Button defineButton(String str,Order o,TitledPane pane,VBox vbox)
	{
		Button temp=new Button(str);
		temp.getStyleClass().add("ViewBtnOrange");
		temp.setMaxWidth(Double.MAX_VALUE);
		temp.getStyleClass().add("lbl");
		
		temp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	String msg;
            	switch(temp.getText())
            	{	
            	 	case "Approve":	StartClient.order.accept("Approve_order~"+o.getOrder_num()); 
            	 					msg="Your order has been accepted.\nThis message has been sent to ";
            	 					if(Integer.parseInt(o.getPhone())!=0) msg+="\nPhone Number:"+o.getPhone();
        	 						msg+="\nEmail: "+o.getMail();
        	 						createPopUp(msg);          	 
            	 					neworders_accordion.getPanes().remove(pane);
            	 					orders_to_deliver_accordion.getPanes().add(pane);
            	 					temp.setText("Deliver");
            	 					break;
            	 	case "Deliver":	StartClient.order.accept("Deliver_order~"+o.getOrder_num());
            	 					if(o.getOrder_type().equals("Delivery"))
            	 					{
            	 						msg="Your order is out for delivery.\nThis message has been sent to ";
            	 						if(Integer.parseInt(o.getPhone())!=0) msg+="\nPhone Number:"+o.getPhone();
            	 						msg+="\nEmail: "+o.getMail();
            	 						createPopUp(msg);
            	 					}
            	 					else
            	 					{
            	 						msg="Your order is ready, Please collect your order at the front desk.\nThis message has been sent to ";
            	 						if(Integer.parseInt(o.getPhone())!=0) msg+="\nPhone Number:"+o.getPhone();
            	 						msg+="\nEmail: "+o.getMail();
            	 						createPopUp(msg);
            	 					}
            	 					
            	 					vbox.getChildren().remove(temp);
            	 					orders_to_deliver_accordion.getPanes().remove(pane);
            	 					historyaccordion.getPanes().add(pane);
            	 					break;
            	}

            }});
		return temp;
	}

	/**
	 * this func simulates an sms message to the using by loading a popup that shows in the users screen once an order has been confirmed
	 * @param msg the msg we set in the popup label*/
	private void createPopUp(String msg)
	{
		FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(Globals.smsSimulationFXML));
        VBox anchorPane;
        try {
			anchorPane = fxmlLoader.load();
			 SMSSimulationController smsController = fxmlLoader.getController();
		        smsController.setMessage(msg);
		        final Stage dialog = new Stage();
		        dialog.initModality(Modality.APPLICATION_MODAL);    
		        Scene dialogScene = new Scene(anchorPane);
		        dialog.setScene(dialogScene);

		        dialog.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
       
	}
}