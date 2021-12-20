package order;

import java.io.IOException;
import java.util.Map;

import clients.StartClient;
import common.Globals;
import entity.DishInOrder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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

    private int delivery_pay;
    private double early_discount;
    public void initialize()
    {
    	StartClient.order.accept("Load private Account~"+Globals.newOrder.getW4c().getPrivateAccount());
    	if(Globals.newOrder.getW4c().getBusinessAccount()!=0) StartClient.order.accept("Load business Account~"+Globals.newOrder.getW4c().getBusinessAccount());
    	payment_type.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
    		
    	    public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

    	         if (payment_type.getSelectedToggle().equals(buisness_btn)) {
    	        	 employer_name_input.setDisable(false);
    	        	 employer_w4c_input.setDisable(false);
    	        	 if(Globals.newOrder.getbAccount().getIsApproved()==1)
    	        	 {
    	        		 employer_name_input.setText(Globals.newOrder.getbAccount().getEmployerName());
        	        	// employer_w4c_input.setText();
    	        	 }
    	         }
    	         if (payment_type.getSelectedToggle().equals(private_btn)) {
    	        	 employer_name_input.setDisable(true);
    	        	 employer_w4c_input.setDisable(true);
    	        	 employer_name_input.clear();
     	        	 employer_w4c_input.clear();
    	         }
    	     } 
    	});
    	

    	for(DishInOrder d : Globals.newOrder.getDishes())
    	{
    		 try {
    		    	FXMLLoader fxmlLoader = new FXMLLoader();
    	            fxmlLoader.setLocation(getClass().getResource("/order/CartItem.fxml"));
    	            VBox anchorPane;
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
    		String delivery_method=Globals.newOrder.getDelivery_method();
    		if(delivery_method.equals("Shared"))
        	{
    	    	private_btn.setDisable(true);
    	    	buisness_btn.setDisable(false);
    	    	buisness_btn.setSelected(true);
    			getSharedDeliveryPrice();
        	}
    		addDeliveryPrice(delivery_method);
    		Separator sep= new Separator();
    		Label delivery=new Label("Delivery");
    		Label delivery_type=new Label(delivery_method);
    		Label delivery_fee=new Label(Globals.delivery_fee.get(delivery_method)+Globals.currency+" X"+Globals.newOrder.getPeople_in_delivery());
    		
    		HBox hbox=new HBox();
    		delivery_type.setPadding(new Insets(10));
    		delivery_fee.setPadding(new Insets(10));
    		hbox.getChildren().addAll(delivery_type,delivery_fee);
    		extra_fees_vbox.getChildren().addAll(sep,delivery,hbox);
    	}
    	if(Globals.newOrder.getIs_early_order()==1)
    	{
    		early_discount=Globals.newOrder.getPrice()*0.1;
    		Globals.newOrder.setPrice(Globals.newOrder.getPrice()*0.9);
    		Separator sep= new Separator();
    		Label discount=new Label("Discount");
    		Label early_order=new Label("Early Order 10%");
    		extra_fees_vbox.getChildren().addAll(sep,discount,early_order);
    	}
    	
    	total_price_label.setText(Globals.newOrder.getPrice()+Globals.currency);
    }
    
    
    @FXML
    void back(ActionEvent event) {
    	double temp=Globals.newOrder.getPrice();
    	if(Globals.newOrder.getIs_early_order()==1)
    	{
    		temp += early_discount;
    	}
    	temp-= delivery_pay;
    	Globals.newOrder.setPrice(temp);
    	Globals.loadInsideFXML( Globals.order_informationFXML);
    }

    @FXML
    void payAndCompleteOrder(ActionEvent event) {

    	String createOrder="Insert_order~"+Globals.newOrder.toString();
    	StartClient.order.accept(createOrder);
    	String insertAmount="Insert_quantity~"+Globals.newOrder.getSupplier().getSupplierNum();
    	Map <String,Integer> items_by_types=Globals.newOrder.getItems_by_type();
    	for (Map.Entry<String, Integer> pair : items_by_types.entrySet()) {
    	    insertAmount+="~"+pair.getValue();
    	}
    	
    	StartClient.order.accept(insertAmount);
    	
    	String updateRestaurantData="updateRestaurantData~"+Globals.newOrder.getPrice()+"~"+Globals.newOrder.getSupplier().getSupplierNum();
    	StartClient.order.accept(updateRestaurantData);
    	for(DishInOrder o:Globals.newOrder.getDishes()) {
    		o.setOrderNum(Globals.newOrder.getOrder_num());//give the order number to each dish in our current order
    		
    		StartClient.order.accept("Add_dishInOrder~"+o.toString());
    	}
    	
    	
    	Globals.loadInsideFXML(Globals.order_confirmedFXML);
    }
    private void addDeliveryPrice(String delivery_method)
	{
    	
		double current_price=Globals.newOrder.getPrice();
		delivery_pay=Globals.delivery_fee.get(delivery_method);
		
		switch(delivery_method)
		{
			case "Private": Globals.newOrder.setPrice(current_price+delivery_pay); break;
			case "Shared": int people_count=Globals.newOrder.getPeople_in_delivery();
							delivery_pay*=people_count;
							Globals.newOrder.setPrice(current_price+delivery_pay);
							break;
			case "Robot":Globals.newOrder.setPrice(current_price+delivery_pay); break;
		}
	}
	private void getSharedDeliveryPrice()
	{
		int price= Globals.regular_delivery_fee;
		int people_count=Globals.newOrder.getPeople_in_delivery();
		if(people_count>=3) price =15;
		else price -= (people_count-1)*5;
		Globals.delivery_fee.put(Globals.newOrder.getDelivery_method(), price);
	}


}
