package controllers;
import clients.OrderClient;
import common.Globals;
import entity.Dish;
import entity.DishInOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
public class CartController {



	    @FXML
	    private TableView<DishInOrder> added_dish_table;

	    @FXML
	    private TableColumn<DishInOrder, String> dish_col;

	    @FXML
	    private TableColumn<DishInOrder, Double> price_col;
	    
	    @FXML
	    private TableColumn<DishInOrder, String> cook_lvl_col;

	    @FXML
	    private TableColumn<DishInOrder, String> size_col;


	    @FXML
	    private TableColumn<DishInOrder, String> extras_col;
	    
	    @FXML
	    private Button back_btn;

	    @FXML
	    private Button checkout_btn;

	    @FXML
	    private Label total_label;
	    
	    private ObservableList<DishInOrder> all_dishes;
	    Double total=0.0;
	    public void initialize()
	    {
	    	display_table();
	    	added_dish_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	            if (newSelection != null)
	              try {
	                //selected_dish();
	              } catch (Exception e) {
	                e.printStackTrace();
	              }  
	          });
	    	for (DishInOrder item : added_dish_table.getItems()) {
	    	    total = total + item.getPrice();
	    	}
	    	total_label.setText(total_label.getText()+total);
	    }
	    @FXML
	    void back(ActionEvent event) {
	    	Globals.loadFXML(null, Globals.branch_menuFXML, event);
	    }
	    @FXML
	    void checkout(ActionEvent event) {
	    	
	    	Globals.loadFXML(null, Globals.order_informationFXML, event);
	    }
	    void display_table(){  
	    	all_dishes = FXCollections.observableArrayList(Globals.newOrder.getDishes());
			dish_col.setCellValueFactory(new PropertyValueFactory<DishInOrder,String>("dish_name"));
			price_col.setCellValueFactory(new PropertyValueFactory<DishInOrder,Double>("price"));
			cook_lvl_col.setCellValueFactory(new PropertyValueFactory<DishInOrder,String>("cooking_lvl"));
			size_col.setCellValueFactory(new PropertyValueFactory<DishInOrder,String>("size"));
			extras_col.setCellValueFactory(new PropertyValueFactory<DishInOrder,String>("extras"));
			added_dish_table.setItems(all_dishes);
	    }

}
