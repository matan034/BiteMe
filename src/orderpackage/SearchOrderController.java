package orderpackage;

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
    private TextField order_time_result;

    @FXML
    private TextField phone_result;

    @FXML
    private TextField order_type_result;

    @FXML
    private TextField order_address_result;

    @FXML
    private TextField restuarant_result;

    @FXML
    private Button serach_order_btn;

    @FXML
    private Button back_btn;

    @FXML
    void back_to_index(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader();
    	try {
    	((Node) event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		Pane root ;
		loader.setLocation(getClass().getResource("/gui/IndexScreen.fxml"));
	    root = loader.load();
		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/gui/IndexScreenCSS.css").toExternalForm());
		primaryStage.setTitle("Insert Order");
		primaryStage.setScene(scene);		
		primaryStage.show();
    	}
    	catch (Exception e) {System.out.println("insert load exception");}
    }

    @FXML
    void searchOrder(ActionEvent event) {
    	String str="Search_order~"+order_number_input.getText();
    	IndexOrderUI.search.accept(str);
//    	restuarant_result.clear();
//    	order_time_result.clear();
//    	phone_result.clear();   	need to make it work
//    	order_type_result.clear();
//    	order_address_result.clear();
    	restuarant_result.setText(SearchOrderClient.o1.getRestuarant());
    	order_time_result.setText(SearchOrderClient.o1.getOrder_time());
    	phone_result.setText(SearchOrderClient.o1.getPhone());
    	order_type_result.setText(SearchOrderClient.o1.getOrder_type());
    	order_address_result.setText(SearchOrderClient.o1.getAddress());
    }

}
