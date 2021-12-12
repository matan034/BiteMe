package controllers;

import java.io.IOException;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import entity.DishInOrder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
    
    @FXML
    private VBox order_items;

    @FXML
    private VBox extra_fees_vbox;
    @FXML
    private Label total_price_label;


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
    	
    	for(DishInOrder d : Globals.newOrder.getDishes())
    	{
    		 try {
    		    	FXMLLoader fxmlLoader = new FXMLLoader();
    	            fxmlLoader.setLocation(getClass().getResource("/order/CartItem.fxml"));
    	            AnchorPane anchorPane;
    	            anchorPane = fxmlLoader.load();
    	            CartItemController cartItemController = fxmlLoader.getController();
    	            cartItemController.setData(d,null);
    	            cartItemController.removeButton();
    	            order_items.getChildren().add(anchorPane);
    	            //total_price_label.setText(Double.toString(Globals.newOrder.getPrice()));
    		    	 } 
    		    	 catch (IOException e) 
    		    	 {
    					e.printStackTrace();
    		    	 }
    	}
    	if(Globals.newOrder.getOrder_type().equals("Delivery"))
    	{
    		Separator sep= new Separator();
    		Label delivery=new Label("Delivery");
    		Label delivery_type=new Label(Globals.newOrder.getDelivery_method());
    		Label delivery_fee=new Label(Globals.delivery_fee.get(Globals.newOrder.getDelivery_method())+Globals.currency);
    		HBox hbox=new HBox();
    		hbox.getChildren().addAll(delivery_type,delivery_fee);
    		extra_fees_vbox.getChildren().addAll(sep,delivery,hbox);
    	}
    	if(Globals.newOrder.getIs_early_order()==1)
    	{
    		Separator sep= new Separator();
    		Label discount=new Label("Discount");
    		Label early_order=new Label("Early Order 10%");
    		extra_fees_vbox.getChildren().addAll(sep,discount,early_order);
    	}
    	
    	total_price_label.setText(Globals.newOrder.getPrice()+Globals.currency);
    }
    
    
    @FXML
    void back(ActionEvent event) {
    	Globals.loadInsideFXML( Globals.order_informationFXML);
    }

    @FXML
    void payAndCompleteOrder(ActionEvent event) {

    	String createOrder="Insert_order~"+Globals.newOrder.toString();
    	StartClient.order.accept(createOrder);
    	for(DishInOrder o:Globals.newOrder.getDishes()) {
    		o.setOrderNum(Globals.newOrder.getOrder_num());//give the order number to each dish in our current order
    		StartClient.order.accept("Add_dishInOrder~"+o.toString());
    	}
    	
    	
    	Globals.loadInsideFXML(Globals.order_confirmedFXML);
    }

}
