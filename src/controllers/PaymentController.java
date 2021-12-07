package controllers;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class PaymentController {

    @FXML
    private RadioButton buisness_btn;

    @FXML
    private ToggleGroup payment_type;

    @FXML
    private RadioButton private_btn;

    @FXML
    private TextField employer_name_input;

    @FXML
    private TextField employer_w4c_input;

    @FXML
    private Button back_btn;

    @FXML
    private Button pay_btn;

    public void initialize()
    {
    	payment_type.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
    		
    	    public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

    	         if (payment_type.getSelectedToggle().equals(buisness_btn)) {
    	        	 employer_name_input.setDisable(false);
    	        	 employer_w4c_input.setDisable(false);
    	         }
    	         if (payment_type.getSelectedToggle().equals(private_btn)) {
    	        	 employer_name_input.setDisable(true);
    	        	 employer_w4c_input.setDisable(true);
    	         }
    	     } 
    	});
    }
    
    
    @FXML
    void back(ActionEvent event) {
    	Globals.loadFXML(null, Globals.order_informationFXML, event);
    }

    @FXML
    void payAndCompleteOrder(ActionEvent event) {
    	String supply_method="";
    	if(Globals.newOrder.getOrder_type().equals("Delivery"))
    	{
    		supply_method=Globals.newOrder.getOrder_type()+"~"+Globals.newOrder.getDelivery_method();
    	}
    	else supply_method=Globals.newOrder.getOrder_type();
    	String createOrder="Insert_order~"+Globals.newOrder.getBranch().getBranchID()+"~"+OrderClient.customer.getCustomerNumber()+"~"+Globals.newOrder.getIs_early_order()+
    			"~"+Globals.newOrder.getOrder_time()+"~"+supply_method;
    	StartClient.order.accept(createOrder);
    	
    	Globals.loadFXML(null, Globals.order_confirmedFXML, event);
    }

}
