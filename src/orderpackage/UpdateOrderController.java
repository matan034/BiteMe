package orderpackage;

import java.util.ArrayList;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import orderpackage.Order;

public class UpdateOrderController {

		@FXML
		private Pane update_pane;
	   @FXML
	    private TableView<Order> OrderTable=new TableView<Order>();

	    @FXML
	    private TableColumn<Order, Integer> order_col;

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
	    private TextField updated_address;

	    @FXML
	    private Button update_btn;
	    @FXML
	    private Label selected_order;

	    @FXML
	    private ComboBox<String> updated_type = new ComboBox<String>(); 
	    private Scene scene;
	    private ObservableList<Order> all_orders;

	
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
		scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/gui/GeneralStyleSheet.css").toExternalForm());
		primaryStage.setTitle("Order");
		primaryStage.setScene(scene);		
		primaryStage.show();
		
    	}
    	catch (Exception e) {System.out.println("insert load exception");}
    }
    
    
    public void initialize()
    {
    	LoginUI.order.accept("Load_orders");
    	updated_type.setItems(IndexController.delivery_options);
    	display_table();
    	OrderTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null)
              try {
                selected_order();
              } catch (Exception e) {
                e.printStackTrace();
              }  
          });

    }

    
    void display_table(){
        
    	all_orders = FXCollections.observableArrayList(UpdateOrderClient.all_orders);
		order_col.setCellValueFactory(new PropertyValueFactory<Order,Integer>("order_num"));
		resturant_col.setCellValueFactory(new PropertyValueFactory<Order,String>("restuarant"));
		order_type_col.setCellValueFactory(new PropertyValueFactory<Order,String>("order_type"));
		phone_col.setCellValueFactory(new PropertyValueFactory<Order,String>("phone"));
		time_col.setCellValueFactory(new PropertyValueFactory<Order,String>("order_time"));
		address_col.setCellValueFactory(new PropertyValueFactory<Order,String>("address")); 
 		OrderTable.setItems(all_orders);
    }
    
    void selected_order()
    {
    	selected_order.setText("You chose order number: "+OrderTable.getSelectionModel().getSelectedItem().getOrder_num());
    	updated_address.setText(OrderTable.getSelectionModel().getSelectedItem().getAddress());
    	updated_type.getSelectionModel().select(OrderTable.getSelectionModel().getSelectedItem().getOrder_type());
    }
    
    @FXML
    void updateOrder(ActionEvent event) {
    	String msg = "Update_order~"+"OrderAddress~"+updated_address.getText()+"~TypeOfOrder~"+updated_type.getSelectionModel().getSelectedItem()+"~"+OrderTable.getSelectionModel().getSelectedItem().getOrder_num();
    	LoginUI.order.accept(msg);

    	for(Order o: all_orders)
    	{
    		if(o.getOrder_num()==OrderTable.getSelectionModel().getSelectedItem().getOrder_num())
    			o.setAddress(updated_address.getText());
    			o.setOrder_type(updated_type.getSelectionModel().getSelectedItem());
    	}
    	OrderTable.refresh();
    	updated_address.clear();
    	selected_order.setText(UpdateOrderClient.res);
    }
}
