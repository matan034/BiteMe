package order;

import common.Globals;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * This class is for showing order confirmation screen 
 * showing order summary info

 * @param order_number_output = outputs the order number
 * @param supply_method_output = outputs the supply method
 * @param supply_time_output = outputs the supply time
 * @param home_btn = button for getting back to home screen
 * @param total_payment_label = outputs the total payment
 * 
 * 
 * @author      Matan Weisberg
 * @version     1.0               
 * @since       01.01.2022        
 */
public class OrderConfirmedController {

    @FXML
    private Label order_number_output;

    @FXML
    private Label supply_method_output;

    @FXML
    private Label supply_time_output;

    @FXML
    private Button home_btn;

    @FXML
    private Label total_payment_label;

    /**
     *This func initializes our controller
     *loads all order data to screen

     **/
    @FXML
    void initialize()
    {
    	order_number_output.setText(order_number_output.getText()+Globals.newOrder.getOrder_num());
    	supply_method_output.setText(Globals.newOrder.getOrder_type());
    	supply_time_output.setText(Globals.newOrder.getOrder_time());
    	total_payment_label.setText(total_payment_label.getText()+Globals.newOrder.getPrice());
    }
    /**
     *This func is for getting back to home screen
     *@param evetn action event for pressing home button
     *set a listner for suppliers comobobox for enabling take away and delivery buttons
     **/
    @FXML
    void goHome(ActionEvent event) {
    	Globals.loadInsideFXML(Globals.homePageCustomer);
    }

}
