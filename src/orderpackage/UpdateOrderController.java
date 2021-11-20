package orderpackage;

import java.util.ArrayList;

import javax.security.auth.callback.Callback;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UpdateOrderController {

	  @FXML
	    private TableView<Order> OrderTable;

	    @FXML
	    private TableColumn<Order, String> order_col;

	    @FXML
	    private TableColumn<Order, String> resturant_col;

	    @FXML
	    private TableColumn<Order,String> order_type_col;

	    @FXML
	    private TableColumn<Order, String> phone_col;

	    @FXML
	    private TableColumn<Order, String> time_col;

	    @FXML
	    private TableColumn<Order,String> address_col;
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
		primaryStage.setTitle("Order");
		primaryStage.setScene(scene);		
		primaryStage.show();
		
    	}
    	catch (Exception e) {System.out.println("insert load exception");}
    }
    
    void display_table(ArrayList<Order> orders){
        
    	
    	ObservableList<Order> all_orders = FXCollections.observableArrayList(orders);
    	/*TableView tableView = new TableView();

        TableColumn<Order, String> column1 = new TableColumn<>("Order");
        column1.setCellValueFactory(new PropertyValueFactory<>("Resturant"));


        TableColumn<Order, String> column2 = new TableColumn<>("Resturant");
        column2.setCellValueFactory(new PropertyValueFactory<>("Resturant"));*/

    	OrderTable = new TableView<Order>();
    	order_col= new TableColumn<>("Order");
		order_col.setCellValueFactory(new PropertyValueFactory<>("Order"));
		resturant_col = new TableColumn<>("Restuarant");
		resturant_col.setCellValueFactory(new PropertyValueFactory<>("Resturant"));
		order_type_col = new TableColumn<>("Order Type");
		order_type_col.setCellValueFactory(new PropertyValueFactory<>("Order Type"));
		phone_col = new TableColumn<>("Phone Number");
		phone_col.setCellValueFactory(new PropertyValueFactory<>("Phone-Number"));
		time_col = new TableColumn<>("Time of Order");
		time_col.setCellValueFactory(new PropertyValueFactory<>("Time of Order"));
		address_col = new TableColumn<>("Order Address");
		address_col.setCellValueFactory(new PropertyValueFactory<>("Order Address"));     
 		OrderTable.setItems(all_orders);
 		OrderTable.getColumns().addAll(order_col, resturant_col, order_type_col, phone_col, time_col, address_col);
    }
}
