package order;

import clients.StartClient;
import common.Globals;
import entity.DishInOrder;
import entity.Order;
import general.MyListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 * This class is for creating order titled pane details in MyOrdersController
 * sets an approve button only for orders that been accepted by supplier and not been recieved by customer
 * 
 * @author      Matan Weisberg
 * @version     1.0               
 * @since       01.01.2022        
 */
public class OrderRecordController {


	/**
	 * hbox that will be used to place approve button*/
    @FXML
    private HBox order_details_hbox;

    /**
     * lbl set the order date*/
    @FXML
    private Label order_date_lbl;

    /**lbl sets the order type*/
    @FXML
    private Label order_type_lbl;
    

    /**
     * vbox used for containing the order dishes*/
    @FXML
    private VBox dishes_vbox;

    /**
     * saves the matching order*/
    private Order order;
    /**
     *  saves the titled pane this order pane will be in*/
    private TitledPane pane;
    /**
     * saves listener for changing accordions after approving arrival*/
    private MyListener approveOrder,changeAccordion;
    
    /**
     *This func initializes our controller
     *saves all the info for order and set it up
     *@param order the current order
     *@param approveOrder the listener that will be used to approve order in MyOrdersController
     *@param changeAccordion the listener that will be used to change accordion in MyOrdersController
     *@param pane the pane that contains current order deatails
     **/
    public void setData(Order order,MyListener approveOrder,TitledPane pane,MyListener changeAccordion)
    {
    	StartClient.order.accept("Load_orderDishes~"+order.getOrder_num());
    	this.pane=pane;
    	this.changeAccordion=changeAccordion;
    	for(DishInOrder dish: Globals.order_dishes)
    	{
    		Label dish_details=new Label(dish.getDishInOrderNum()+" "+dish.getDish_name()+" "+dish.getSize()+" "+dish.getCooking_lvl()+" "+dish.getExtras()+" "+dish.getPrice()+Globals.currency);
    		dishes_vbox.getChildren().add(dish_details);
    	}
    	this.approveOrder=approveOrder;
    	this.order=order;
    	order_date_lbl.setText(order.getOrder_time());
    	order_type_lbl.setText(order.getOrder_type());
    }
    
    /**
     *This func is for setting approval button in the relevant orders
     **/
    public void setApproval()
    {
    	Button approve = new Button("Approve");
    	Label approved = new Label("Approved");
    	approve.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	approveOrder.onClickListener(order);
		    	changeAccordion.onClickListener(pane);
		    	order_details_hbox.getChildren().remove(approve);
		    	order_details_hbox.getChildren().add(approved);	
		    }});
    	
    	order_details_hbox.getChildren().add(approve);		
    }
    /**
     *This func is getting the order TitledPane
     *@return TitledPane the order titledpane
     **/
    public TitledPane getPane()
    {
    	return pane;
    }
}