package order;

import clients.OrderClient;
import clients.StartClient;
import entity.Order;
import order.OrderRecordController;
import general.MyListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * This class is for showing all orders of the logged in customer
 * divides the orders to 3 accordions 

 * @param new_orders_vbox = vbox containing new orders information
 * @param history_vbox = vbox containing history orders information
 * @param new_orders_accordion = Accordion containing new orders that supplier has approved them
 * @param history_accordion = Accordion containing history orders
 * @param wating_supplier_approval_accordion = Accordion containing new orders that supplier hasn't approved them
 * @param changeAccordion = listener interface for moving order between panes
 * @param approveArrival = listener interface for approving order has arrived to customer
 * 
 * @author      Matan Weisberg
 * @version     1.0               
 * @since       01.01.2022        
 */
public class MyOrdersController {

    @FXML
    private VBox new_orders_vbox;

    @FXML
    private VBox history_vbox;

    @FXML
    private Accordion new_orders_accordion;

    @FXML
    private Accordion history_accordion;

    @FXML
    private Accordion wating_supplier_approval_accordion;
    
    private MyListener approveArrival,changeAccordion;
    
    /**
     *This func initializes our controller
     *loads all customer orders from DB
     *add each order to correct accordion according to its flags
     *set a listner for changing accordions
     *set a listener for approving order arrived
     **/
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
				if(OrderClient.orderLateFlag==1)
				{
					StartClient.order.accept("Refund Account~"+order.getCustomerNum()+"~"+order.getPrice()*0.5+"~"+order.getSupplierNum());
					StartClient.order.accept("Update Supplier Late Cnt~"+order.getSupplierNum());
				}
				
				
			}
		};
		changeAccordion = new MyListener() {
			
			@Override
			public void onClickListener(Object object) {
				TitledPane pane=(TitledPane)object;
				new_orders_accordion.getPanes().remove(pane);
				history_accordion.getPanes().add(pane);
				
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
	           
	            TitledPane pane = new TitledPane("Order #"+o.getOrder_num()+" From "+o.getSupplierName(), anchorPane);
	            orderRecordController.setData(o,approveArrival,pane,changeAccordion);
	            pane.getStyleClass().add("accordion_panel");
	           
	    		if(o.getIs_arrived()==1)
	    		{
	    			history_accordion.getPanes().add(pane);
	    		}
	    		else if(o.getOutForDeliver()==1)
	    			{
	    				orderRecordController.setApproval();
	    				new_orders_accordion.getPanes().add(pane);
	    			}
	    			else
	    			{
	    				wating_supplier_approval_accordion.getPanes().add(pane);
	    			}
    		}
    		catch (Exception e) {
				// TODO: handle exception
			}
    	}
    	
    	
    }
}