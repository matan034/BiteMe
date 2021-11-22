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

    @FXML
    void searchOrder(ActionEvent event) {
    	if(order_number_input.getText()!=null)
    	{
    		String str="Search_order~"+order_number_input.getText();
    		LoginUI.order.accept(str);
    		if(OrderClient.found_order.getOrder_num()!=Integer.parseInt(order_number_input.getText()))  	
    			search_res.setText("Order not found");
    		else 
    			search_res.setText(OrderClient.found_order.getRestuarant()+'\n'+OrderClient.found_order.getOrder_time()+'\n'+OrderClient.found_order.getPhone()+'\n'+OrderClient.found_order.getOrder_type()+'\n'+OrderClient.found_order.getAddress());
    	}
    	else {
    		
    	}
    	
    }

}
