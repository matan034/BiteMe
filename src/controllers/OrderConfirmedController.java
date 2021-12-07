package controllers;

import common.Globals;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
    void initialize()
    {
    	order_number_output.setText(order_number_output.getText()+Globals.newOrder.getOrder_num());
    	supply_method_output.setText(Globals.newOrder.getOrder_type());
    	supply_time_output.setText(Globals.newOrder.getOrder_time());
    }
    @FXML
    void goHome(ActionEvent event) {
    	Globals.loadFXML(null, Globals.indexFXML, event);
    }

}
