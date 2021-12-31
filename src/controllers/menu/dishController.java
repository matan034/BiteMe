package menu;


import clients.OrderClient;
import common.Globals;
import entity.Dish;
import entity.DishInRestaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class dishController {
	 @FXML
	    private TableView<DishInRestaurant> menu_table;

	    @FXML
	    private TableColumn<DishInRestaurant, String> dish_name;

	    @FXML
	    private TableColumn<DishInRestaurant, Double> price;
	    
	    @FXML
	    private TableColumn<DishInRestaurant, Integer> Dish_ID ;

	    @FXML
	    private TableColumn<DishInRestaurant, String> Dish_Type ;
	    
	    @FXML
	    private TableColumn<DishInRestaurant, Integer> Choose_Size;

	    @FXML
	    private TableColumn<DishInRestaurant, Integer> Choose_CookingLevel;
	    
	    @FXML
	    private TableColumn<DishInRestaurant, Integer> Choose_Extras;

	    @FXML
	    private TableColumn<DishInRestaurant, Void> buttons;
	    @FXML
	    private Button save_btn;
	    @FXML
	    private Button edit_btn;

	    @FXML
	    private Label selected_item_label;
	    
	    private ObservableList<Dish> all_dishes;
	    @FXML
	    private Button cart_btn;


	    @FXML
	    void goToCart(ActionEvent event) {
	    	Globals.loadFXML(null, Globals.cartFXML, event,null);
	    }
	    
	    
	    public void initialize()
	    {
	    	display_table();
	    	/*menu_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	            if (newSelection != null)
	              try {
	                selected_dish();
	              } catch (Exception e) {
	                e.printStackTrace();
	              }  
	          });*/
       
	    }
	    private void selected_dish()
	    {
	    	selected_item_label.setText("You chose dish: "+menu_table.getSelectionModel().getSelectedItem().getName());
	    }

	    @FXML
	    void addToOrder(ActionEvent event) {
	    	Globals.selected_dish=menu_table.getSelectionModel().getSelectedItem();
	    	Globals.loadFXML(null, Globals.dish_selectionFXML, event,null);
	    }

	    @FXML
	    void edit(ActionEvent event) {
	    	menu_table.setEditable(true);
	    	dish_name.setCellFactory(TextFieldTableCell.forTableColumn());
	    	price.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
	    	Dish_ID.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
	    	Dish_Type.setCellFactory(TextFieldTableCell.forTableColumn());
	    	Choose_Size.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
	    	Choose_CookingLevel.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
	    	Choose_Extras.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
	    	
	    	dish_name.setOnEditCommit(new EventHandler<CellEditEvent<DishInRestaurant, String>>(){
				@Override
				public void handle(CellEditEvent<DishInRestaurant, String> event) {
				
					DishInRestaurant dish= event.getRowValue();
					if(event.getNewValue()==null)
						dish.setName(event.getOldValue());
					else
						dish.setName(event.getNewValue());
					
					
				}
	    	});
	    	
	    	Dish_Type.setOnEditCommit(new EventHandler<CellEditEvent<DishInRestaurant, String>>(){
				@Override
				public void handle(CellEditEvent<DishInRestaurant, String> event) {
				
					DishInRestaurant dish= event.getRowValue();
					if(event.getNewValue()==null)
						dish.setType(event.getNewValue());
					else
						dish.setType(event.getNewValue());
					
					
				}
	    	});
	    	
	    	price.setOnEditCommit(new EventHandler<CellEditEvent<DishInRestaurant, Double>>(){
				@Override
				public void handle(CellEditEvent<DishInRestaurant, Double> event) {
				
					DishInRestaurant dish= event.getRowValue();
					if(event.getNewValue()==null)
						dish.setPrice(event.getNewValue());
					else
						dish.setPrice(event.getNewValue());
					
				}
	    	});
	    	
	    	Dish_ID.setOnEditCommit(new EventHandler<CellEditEvent<DishInRestaurant, Integer>>(){
				@Override
				public void handle(CellEditEvent<DishInRestaurant, Integer> event) {
				
					DishInRestaurant dish= event.getRowValue();
					if(event.getNewValue()==null)
						dish.setDishID(event.getOldValue());
					else
						dish.setDishID(event.getNewValue());
					
					
				}
	    	});
	    	
	    	Choose_Size.setOnEditCommit(new EventHandler<CellEditEvent<DishInRestaurant, Integer>>(){
				@Override
				public void handle(CellEditEvent<DishInRestaurant, Integer> event) {
				
					DishInRestaurant dish= event.getRowValue();
					if(event.getNewValue()==null)
						dish.setChooseSize(event.getOldValue());
					else
						dish.setChooseSize(event.getNewValue());
					
					
				}
	    	});
	    	
	    	Choose_CookingLevel.setOnEditCommit(new EventHandler<CellEditEvent<DishInRestaurant, Integer>>(){
				@Override
				public void handle(CellEditEvent<DishInRestaurant, Integer> event) {
				
					DishInRestaurant dish= event.getRowValue();
					if(event.getNewValue()==null)
						dish.setChooseCookingLvl(event.getOldValue());
					else
						dish.setChooseCookingLvl(event.getNewValue());
					
				}
	    	});
	    	
	    	Choose_Extras.setOnEditCommit(new EventHandler<CellEditEvent<DishInRestaurant, Integer>>(){
				@Override
				public void handle(CellEditEvent<DishInRestaurant, Integer> event) {
				
					DishInRestaurant dish= event.getRowValue();
					if(event.getNewValue()==null)
						dish.setChooseExtras(event.getOldValue());
					else
						dish.setChooseExtras(event.getNewValue());
					
				}
	    	});
	    	
	    	
	    }

	    void display_table(){  
	    	all_dishes = FXCollections.observableArrayList(OrderClient.dishes_by_type_list);
			dish_name.setCellValueFactory(new PropertyValueFactory<DishInRestaurant,String>("name"));
			price.setCellValueFactory(new PropertyValueFactory<DishInRestaurant,Double>("price"));
			Dish_ID.setCellValueFactory(new PropertyValueFactory<DishInRestaurant,Integer>("dishID"));
			Dish_Type.setCellValueFactory(new PropertyValueFactory<DishInRestaurant,String>("type"));
			Choose_Size.setCellValueFactory(new PropertyValueFactory<DishInRestaurant,Integer>("chooseSize"));
			Choose_CookingLevel.setCellValueFactory(new PropertyValueFactory<DishInRestaurant,Integer>("chooseCookingLvl"));
			Choose_Extras.setCellValueFactory(new PropertyValueFactory<DishInRestaurant,Integer>("chooseExtras"));
			//buttons.setCellValueFactory(new PropertyValueFactory<Dish,Void>("Action"));
		    //buttons.setSortable(false);
	    }
	    @FXML
	    void save(ActionEvent event) {
	    	menu_table.setEditable(false);
	    	all_dishes= FXCollections.observableArrayList(OrderClient.dishes_by_type_list);
	    	Dish[] Dishes=(Dish[])all_dishes.toArray();
	    	
	    }

}
