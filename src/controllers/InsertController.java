package controllers;

import clients.LoginUI;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InsertController {

	  @FXML
	    private Pane insert_pane;

	    @FXML
	    private VBox Vbox;

	    @FXML
	    private TextField rest_info;

	    @FXML
	    private TextField phone_info;

	    @FXML
	    private TextField address_info;

	    @FXML
	    private Button insert_btn;

	    @FXML
	    private ComboBox<String> type_order_info;
	    @FXML
	    private Button back_btn;

	    @FXML
	    public void initialize() {
	    	type_order_info.setItems(IndexController.delivery_options);
	    }
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
			primaryStage.setTitle("Order");
			primaryStage.setScene(scene);		
			primaryStage.show();
	    	}
	    	catch (Exception e) {System.out.println("insert load exception");}
	    }
    
    @FXML
    void insertOrder(ActionEvent event) {
    	if(rest_info.getText()==null) {}
    	if(phone_info.getText()==null) {}
    	if(type_order_info.getPromptText()==null) {}
    	if(address_info.getText()==null) {}
    	else {
    		String str="Insert_order~"+rest_info.getText()+"~"+phone_info.getText()+"~"+type_order_info.getPromptText()+"~"+address_info.getText();
    		LoginUI.order.accept(str);
    	}
    }

}
