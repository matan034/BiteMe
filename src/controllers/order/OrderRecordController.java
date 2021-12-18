package order;

import clients.StartClient;
import common.Globals;
import entity.DishInOrder;
import entity.Order;
import general.MyListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OrderRecordController {


    @FXML
    private HBox order_details_hbox;

    @FXML
    private Label order_date_lbl;

    @FXML
    private Label order_type_lbl;
    

    @FXML
    private VBox dishes_vbox;

    
    private Order order;

    private MyListener approveOrder;
    public void setData(Order order,MyListener approveOrder)
    {
    	StartClient.order.accept("Load_orderDishes~"+order.getOrder_num());
    	
    	for(DishInOrder dish: Globals.order_dishes)
    	{
    		Label dish_details=new Label(dish.getDish_name()+" "+dish.getSize()+" "+dish.getCooking_lvl()+" "+dish.getExtras()+" "+dish.getPrice()+Globals.currency);
    		dishes_vbox.getChildren().add(dish_details);
    	}
    	this.approveOrder=approveOrder;
    	this.order=order;
    	order_date_lbl.setText(order.getOrder_time());
    	order_type_lbl.setText(order.getOrder_type());
    }
    public void setApproval()
    {
    	Button approve = new Button("Approve");
    	Label approved = new Label("Approved");
    	approve.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	approveOrder.onClickListener(order);
		    	order_details_hbox.getChildren().remove(approve);
		    	order_details_hbox.getChildren().add(approved);	
		    }});
    	
    	order_details_hbox.getChildren().add(approve);	
    	
    			
    }
}