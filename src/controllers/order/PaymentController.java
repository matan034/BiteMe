package order;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import entity.DishInOrder;
import general.MyListener;
import general.VerifyListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    
    @FXML
    private ImageView back_screen;


    private int delivery_pay;
    private double early_discount;
    private MyListener approveListener;
    private boolean approve;
    public void initialize()
    {
    	if(Globals.newOrder.getbAccount()==null) buisness_btn.setDisable(true);	
    	else
    	{
    		if(Globals.newOrder.getbAccount().getIsApproved()==0)
    			buisness_btn.setDisable(true);
    		else buisness_btn.setDisable(false);
    	}
    	
    	if(Globals.newOrder.getpAccount()==null) private_btn.setDisable(true);
    	else private_btn.setDisable(false);
    	
    	payment_type.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
    		
    	    public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

    	         if (payment_type.getSelectedToggle().equals(buisness_btn)) {
    	        	 employer_name_input.setDisable(false);
    	        	 employer_w4c_input.setDisable(false);
    	        	 if(Globals.newOrder.getbAccount().getIsApproved()==1)
    	        	 {
    	        		 employer_name_input.setText(Globals.newOrder.getbAccount().getEmployerName());	
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
    	
    	
    	approveListener=new MyListener(){
			@Override
			public void onClickListener(Object object) {			
				approve=(boolean)object;	
			}		
    	};
    	
    	Globals.VerifyInputListener(employer_w4c_input, new VerifyListener() {
			
			@Override
			public boolean verify() {
				StartClient.order.accept("Check_employer_w4c~"+Globals.newOrder.getW4c().getCardNum()+"~"+employer_w4c_input.getText());
				if(OrderClient.employerW4cVerify)
				{
					pay_btn.setDisable(false);
					return true;
				}
				else {
					pay_btn.setDisable(true);
					return false;
				}
			
			}
		});
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
    	verifyPayment(event);
    	
    			if(approve)
	    		{

    					double price=Globals.newOrder.getPrice(),refund=OrderClient.refund;
    					
    					 if (payment_type.getSelectedToggle().equals(buisness_btn)) {
    						 double monthlyLimit=Globals.newOrder.getbAccount().getBalance(),lefttoPay=0;
    						 String chargeBAccount="Update_BaccountBalance~"+Globals.newOrder.getbAccount().getAccountNum()+"~";
    						 if(refund>0)
    						 {
    							 if(price>refund) 
    							 {
    								 lefttoPay=(price-refund);
    								 if(monthlyLimit>lefttoPay) chargeBAccount+=(monthlyLimit-lefttoPay);
    								 else chargeBAccount+=monthlyLimit;	
    							 }
    						 }
    						 else
    						 {
    							 if(monthlyLimit>price) chargeBAccount+=price;
								 else chargeBAccount+=monthlyLimit;	
    						 }  						 
    						StartClient.order.accept(chargeBAccount);
    						
    					 }
    			if(refund>0) {
    				String updateRefund="Update_refund~"+Globals.newOrder.getCustomer().getCustomerNumber()+"~"+Globals.newOrder.getSupplier().getSupplierNum()+"~";
    				if(price>refund) updateRefund+=refund;
    				else updateRefund+=(refund-price);
    				StartClient.order.accept(updateRefund);//implement
    			}
    				
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
	}	
		
    		
    			   	


	private void verifyPayment(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
	        fxmlLoader.setLocation(getClass().getResource(Globals.paymentStatusFXML));
	        VBox popUp;
	        popUp = fxmlLoader.load();
	        PaymentStatusController cont = fxmlLoader.getController();
	        double orderPrice=Globals.newOrder.getPrice();
	        double toPay;
	        StartClient.order.accept("Check_refund~"+Globals.newOrder.getCustomer().getCustomerNumber()+"~"+Globals.newOrder.getSupplier().getSupplierNum());
			double refund=OrderClient.refund;
			if(refund<orderPrice) {
				toPay=orderPrice-refund;
			}
			else toPay=0;
	        if (payment_type.getSelectedToggle().equals(private_btn))
			 {
				 String number =Globals.newOrder.getpAccount().getCreditCardNumber();
				 String mask = number.replaceAll("\\w(?=\\w{4})", "*");
				 if(refund>0)
				 {
					 
					 if(toPay>0)
						 cont.setData("You got credit in store by: "+refund+"\nYour credit card Number: "+mask+"\n Will be Charged by "+toPay+Globals.currency,approveListener); 
					 else 
						 cont.setData("You got credit in store by: "+refund+"\nYou Will not be charged. ",approveListener);
				 }
				 else cont.setData("Your credit card Number: "+mask+"\n Will be Charged by "+orderPrice+Globals.currency,approveListener);
				 
				 
				
			 }
			 if (payment_type.getSelectedToggle().equals(buisness_btn))
			 {
				 double account_balance=Globals.newOrder.getbAccount().getBudget();
				 if(refund>0)
				 {
					 if(toPay>0)
						 cont.setData("You got credit in store by: "+refund+"\nYour business account will charged by: "+toPay+Globals.currency,approveListener); 
					 else 
						 cont.setData("You got credit in store by: "+refund+"\nYou Will not be charged. ",approveListener);
				 }
				 else
				 {
					if(orderPrice<=account_balance)	 
					 {
						 Globals.newOrder.getbAccount().setBalance(account_balance-orderPrice);
						 //need to sent to DB
						 cont.setData("Your business account will charged by: "+orderPrice+Globals.currency+"\n You updated balance will be: "+(account_balance-orderPrice)+Globals.currency,approveListener);
					
					 }
					 else {
						 cont.setData("You dont have sufficient balance.\n Your balance: " +account_balance+Globals.currency+
								 "\n You can proceed with your order by charging your remainder with your private account by:  "+(orderPrice-account_balance)+Globals.currency,approveListener);
					 }
				 }
			 }
			
	        
	        final Stage dialog = new Stage();
	        dialog.initOwner((Stage)((Node) event.getSource()).getScene().getWindow());
	        dialog.initModality(Modality.APPLICATION_MODAL);    
	        Scene dialogScene = new Scene(popUp);
	        dialog.setScene(dialogScene);
	        dialog.showAndWait();
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
		
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
