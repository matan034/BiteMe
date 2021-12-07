package controllers;




import clients.OrderClient;
import common.Globals;
import entity.Dish;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class BranchMenuController {

	    @FXML
	    private TableView<Dish> menu_table;

	    @FXML
	    private TableColumn<Dish, String> dish_name;

	    @FXML
	    private TableColumn<Dish, Double> price;

	    @FXML
	    private TableColumn<Dish, Void> buttons;
	    @FXML
	    private Button back_btn;
	    @FXML
	    private Button add_btn;

	    @FXML
	    private Label selected_item_label;
	    
	    private ObservableList<Dish> all_dishes;
	    @FXML
	    private Button cart_btn;


	    @FXML
	    void goToCart(ActionEvent event) {
	    	Globals.loadFXML(null, Globals.cartFXML, event);
	    }
	    
	    
	    public void initialize()
	    {
	    	display_table();
	    	menu_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	            if (newSelection != null)
	              try {
	                selected_dish();
	              } catch (Exception e) {
	                e.printStackTrace();
	              }  
	          });
             
	    }
	    private void selected_dish()
	    {
	    	selected_item_label.setText("You chose dish: "+menu_table.getSelectionModel().getSelectedItem().getName());
	    }

	    @FXML
	    void addToOrder(ActionEvent event) {
	    	Globals.selected_dish=menu_table.getSelectionModel().getSelectedItem();
	    	Globals.loadFXML(null, Globals.dish_selectionFXML, event);
	    }

	    @FXML
	    void back_to_branch_selection(ActionEvent event) {
	    	Globals.loadFXML(null, Globals.ChooseBranchFXML, event);
	    }

	    void display_table(){  
	    	all_dishes = FXCollections.observableArrayList(OrderClient.branch_menu);
			dish_name.setCellValueFactory(new PropertyValueFactory<Dish,String>("name"));
			price.setCellValueFactory(new PropertyValueFactory<Dish,Double>("price"));
			//buttons.setCellValueFactory(new PropertyValueFactory<Dish,Void>("Action"));
		    //buttons.setSortable(false);
			menu_table.setItems(all_dishes);
	    }
	        

	                   


}

