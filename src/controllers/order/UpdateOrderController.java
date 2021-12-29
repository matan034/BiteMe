package order;



import clients.StartClient;
import clients.OrderClient;
import common.Globals;
import entity.Order;
import general.IndexController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
	    @FXML
	    private Tooltip update_order_tooltip;

	    private ObservableList<Order> all_orders;
	    private final int max_line_size = 45;
	
    @FXML
    private Button back_btn;

    @FXML
    void back_to_index(ActionEvent event) {

    	//Globals.loadFXML(null, Globals.indexFXML, event);
    }
    
    
    public void initialize()
    {
    	StartClient.order.accept("Load_orders");
    	updated_type.setItems(IndexController.delivery_options);
    	display_table();
    	update_order_tooltip.setText("Please choose an order from table");
    	OrderTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null)
              try {
            	validate_update();
                selected_order();
              } catch (Exception e) {
                e.printStackTrace();
              }  
          });
    	updated_address.textProperty().addListener((obs,oldValue, newValue)-> {
	    	    if(newValue!=null)
	    	    {
	    	    	validate_update();
	    	    }
	    	});
    	updated_type.getSelectionModel().selectedItemProperty().addListener((obs,oldValue, newValue)-> {
    	    if(newValue!=null)
    	    {
    	    	validate_update();
    	    }
    	});

    }
    
    void validate_update()
    {
    	int cnt=0;
    	if(OrderTable.getSelectionModel().getSelectedItem()!=null) cnt++;
    	else {
    		update_order_tooltip.setText("Please choose an order from table");
    	}
    	if(updated_type.getSelectionModel().selectedItemProperty().get()==null)  update_order_tooltip.setText("You must choose delivery type");
    	else{
    		cnt++;
    		if(updated_type.getSelectionModel().selectedItemProperty().get().equals("Delivery")) 
        	{
    			updated_address.setDisable(false);
        		if(!updated_address.getText().trim().isEmpty()&&updated_address.getText().length()<max_line_size) cnt++;
        		else {
        			update_order_tooltip.setText("You must enter an address for delivery");
        		}
        	}
    		else {
    			updated_address.setDisable(true);
    			updated_address.clear();
    			cnt++;
    			
    		}
    	}
    	
    	if(cnt==3)
    	{
    		update_btn.setDisable(false);
    		update_order_tooltip.setText("Update chosen delivery");
    	}
    	else update_btn.setDisable(true);
    }
    
    void display_table(){  
    	//all_orders = FXCollections.observableArrayList(OrderClient.all_orders);
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
    	//updated_address.setText(OrderTable.getSelectionModel().getSelectedItem().getAddress());
    	updated_type.getSelectionModel().select(OrderTable.getSelectionModel().getSelectedItem().getOrder_type());
    }
    
    @FXML
    void updateOrder(ActionEvent event) {
    	
    	
    	if(OrderTable.getSelectionModel().getSelectedItem()==null) {}
    	else {
    		String suffix="-";
        	if(!updated_address.getText().trim().isEmpty())
        	{
        		suffix=updated_address.getText();
        	}
    		String msg = "Update_order~"+"OrderAddress~"+suffix+"~TypeOfOrder~"+updated_type.getSelectionModel().getSelectedItem()+"~"+OrderTable.getSelectionModel().getSelectedItem().getOrder_num();
    		StartClient.order.accept(msg);

    		for(Order o: all_orders)
    		{
    			if(o.getOrder_num()==OrderTable.getSelectionModel().getSelectedItem().getOrder_num())
    			{
    				//o.setAddress(updated_address.getText());
    				o.setOrder_type(updated_type.getSelectionModel().getSelectedItem());
    			}
    		}
    		OrderTable.refresh();
    		updated_address.clear();
    		selected_order.setText(OrderClient.update_msg);
    	}
    }
}
