package controllers;

import clients.OrderClient;
import clients.StartClient;
import entity.Dish;
import entity.Order;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MyOrdersController {

    @FXML
    private VBox new_orders_vbox;

    @FXML
    private VBox history_vbox;

    @FXML
    private Accordion new_orders_accordion;

    @FXML
    private Accordion history_accordion;
    
    private MyListener approveArrival;

    public void initialize()
    {
    	
    	int customerid=OrderClient.customer.getCustomerNumber();
    	String load_orders="Load_Myorders~"+customerid;
    	StartClient.order.accept(load_orders);
    	approveArrival=new MyListener() {
			
			@Override
			public void onClickListener(Object object) {
				Order order=(Order)object;
				order.setIs_arrived(1);
				String orderArrived="Order_arrived~"+1+"~"+order.getOrder_num();
				StartClient.order.accept(orderArrived);
				String update_recieve_time="Update_recieve_time~"+order.getOrder_num();
				StartClient.order.accept(update_recieve_time);
				
			}
		};
    	for(Order o:OrderClient.myOrders)
    	{
    		try {
	    		FXMLLoader fxmlLoader = new FXMLLoader();
	            fxmlLoader.setLocation(getClass().getResource("/customer/OrderRecordScreen.fxml"));
	            AnchorPane anchorPane;
	            anchorPane = fxmlLoader.load();       
	            OrderRecordController orderRecordController = fxmlLoader.getController();
	            orderRecordController.setData(o,approveArrival);
	            TitledPane pane = new TitledPane("Order #"+o.getOrder_num(), anchorPane);

	           
	    		if(o.getIs_arrived()==1)
	    		{
	    			history_accordion.getPanes().add(pane);
		    		//history_vbox.getChildren().add(anchorPane);
	    		}
	    		else
	    		{
	    			orderRecordController.setApproval();
	    			new_orders_accordion.getPanes().add(pane);
	    			//new_orders_vbox.getChildren().add(anchorPane);    
	    		}
    		}
    		catch (Exception e) {
				// TODO: handle exception
			}
    	}
    	
    	
    }
}