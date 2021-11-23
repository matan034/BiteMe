/**
*Controller class for Search the class handles events from UI and information that returns from server DB
*@param order_number_label lbl for order number
*@param order_number_input textfield for input order number
*@param search_order_btn button used to search address
*@praam back_btn button used to go back to index
*/


package controllers;

import clients.LoginUI;
import clients.OrderClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SearchOrderController {

    @FXML
    private Label order_number_label;

    @FXML
    private TextField order_number_input;

    @FXML
    private Button serach_order_btn;

    @FXML
    private Button back_btn;

    @FXML
    private Label search_res;
    public void initialize()
    {

    	order_number_input.textProperty().addListener((obs,oldValue, newValue)-> {
	    	    if(newValue!=null)
	    	    {
	    	    	validate_search();
	    	    }
	    	});
    }
    public void validate_search()
    {
    	if( order_number_input.getText()!=""&&order_number_input.getText().chars().allMatch( Character::isDigit ))  serach_order_btn.setDisable(false);
    	else serach_order_btn.setDisable(true);
    }
    /**
    *back_to_index func activates on button back press event and loads index screen while hiding search screen
    *@param event. event button back pressed event 
    */
    @FXML
    void back_to_index(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader();
    	try {
    	((Node) event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		Pane root ;
		loader.setLocation(getClass().getResource("/ui/IndexScreen.fxml"));
	    root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/ui/GeneralStyleSheet.css").toExternalForm());
		//scene.getStylesheets().add(getClass().getResource("/gui/IndexScreenCSS.css").toExternalForm());
		primaryStage.setTitle("Insert Order");
		primaryStage.setScene(scene);		
		primaryStage.show();
    	}
    	catch (Exception e) {System.out.println("insert load exception");}
    }
	 /**
    *searchOrder func activates on search button press event and loads the order if it is found else displays order not found
    *@param event. event button back pressed event 
    */
    @FXML
    void searchOrder(ActionEvent event) {
    	if(order_number_input.getText()!=null)
    	{
    		String str="Search_order~"+order_number_input.getText();
    		LoginUI.order.accept(str);//send to server request for order
    		if(OrderClient.found_order.getOrder_num()!=Integer.parseInt(order_number_input.getText()))//order not found
    			search_res.setText("Order not found");
    		else //if we found an order with the same number as the one requessted
    			search_res.setText(OrderClient.found_order.getRestuarant()+'\n'+OrderClient.found_order.getOrder_time()+'\n'+OrderClient.found_order.getPhone()+'\n'+OrderClient.found_order.getOrder_type()+'\n'+OrderClient.found_order.getAddress());
    	}
    	else {
    		
    	}
    	
    }

}
