package order;



import java.io.IOException;
import javafx.concurrent.WorkerStateEvent;
import java.util.ArrayList;
import java.util.Map;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import entity.DishInOrder;
import entity.DishInRestaurant;
import general.MyListener;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class BranchMenuController {

	    @FXML
	    private Button back_btn;
	    @FXML
	    private Button add_btn;

	    @FXML
	    private Label selected_item_label;
	    
	    @FXML
	    private ImageView cart_img;
	    
	    @FXML
	    private Label cart_count;

	    @FXML
	    private GridPane items_grid;
	    
	    @FXML
	    private Label selected_dish_name;

	    @FXML
	    private Label selected_dish_price;

	    @FXML
	    private ImageView selected_dish_img;

	    @FXML
	    private VBox dish_options_vbox;
	    @FXML
	    private VBox cart_items_vbox;
	    @FXML
	    private Label total_price_label;
	 

	    @FXML
	    private ImageView green_v_img;
	    @FXML
	    private VBox cart_vbox;

	    @FXML
	    private ImageView back_image;

	    @FXML
	    private BorderPane borderPane;
	    @FXML
	    private HBox menu_categories;
	    @FXML
	    private ProgressIndicator pi;
	    private MyListener menuListener;
	    private String currentSize,currentLvl,extras;
	    private ToggleGroup sizes=new ToggleGroup();
	    private TextField extra_input=new TextField();
	    private DishInRestaurant selected_dish;
	    private Button appetizer_btn,salad_btn,main_dish_btn,dessert_btn,drinks_btn;

	    ComboBox<String> r=new ComboBox<>();
	    private int i=0;
	    private Boolean firstClick=true;
	    @FXML
	    void goCart(MouseEvent event) {
	    	VBox newLoadedPane;
	    	if(Integer.parseInt(cart_count.getText())!=0)
	    	{
	    		
		    	if(firstClick)
		    	{
		    		try {
		    		firstClick=false;
		    		cart_vbox.setVisible(true);
					newLoadedPane = FXMLLoader.load(getClass().getResource("/order/CartScreen.fxml"));
					cart_vbox.getChildren().add(newLoadedPane); 
		    		} catch (IOException e) {
					// TODO Auto-generated catch block
		    			e.printStackTrace();
		    		}
		    	}
		    	else
		    	{
		    		firstClick=true;
		    		cart_vbox.setVisible(false);
		    		cart_vbox.getChildren().clear();
		    	}
	    	}
	    	
	    }
	    
	    public void initialize()
	    {
	    	borderPane.setDisable(true);
	    	StartClient.order.accept("Load_dishes~"+Globals.newOrder.getSupplier().getSupplierNum());
	    	final GetDishesService serviceExample = new GetDishesService();

	        //Here you tell your progress indicator is visible only when the service is runing
	        pi.visibleProperty().bind(serviceExample.runningProperty());
	        pi.progressProperty().bind(serviceExample.progressProperty());
	        borderPane.setStyle( "-fx-background-color: rgba(0, 0, 0, 0.4)");
	        serviceExample.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
	            @Override
	            public void handle(WorkerStateEvent workerStateEvent) {
	            	borderPane.setDisable(false);
	            	 borderPane.setStyle( "-fx-background-color: #fff;");
	    	    	appetizer_btn=defineButton("Appetizer");
	    	    	salad_btn=defineButton("Salad");
	    	    	main_dish_btn=defineButton("Main");
	    	    	dessert_btn=defineButton("Dessert");
	    	    	drinks_btn=defineButton("Drink");
	    	    	menuListener = new MyListener() {
	    	    		   @Override
	    	                public void onClickListener(Object dish) {
	    	                    setChosenDish((DishInRestaurant)dish);
	    	                }  
	    			};
	    			checkDishes();
	    			
	    			Globals.newOrder.getDishes().addListener(new ListChangeListener<DishInOrder>() { 
	    	    		@Override
	    		    	 public void onChanged(ListChangeListener.Change<? extends DishInOrder> c) {
	    	    		
	    	    				int item_cnt=Globals.newOrder.getDishes().size();
	    	    				if(item_cnt==0) 
	    	    				{
	    	    					cart_vbox.getChildren().clear();
	    	    					
	    	    				}
	    	    				
	    	    				cart_count.setText(Integer.toString(item_cnt));
	    	    				
	    	             }
	    	         });
	    			
	    			cart_count.setText(Integer.toString(Globals.newOrder.getDishes().size()));
	            }
	        });

	        serviceExample.setOnFailed(new EventHandler<WorkerStateEvent>() {
	            @Override
	            public void handle(WorkerStateEvent workerStateEvent) {
	                //DO stuff on failed
	            }
	        });
	        serviceExample.restart(); //here you start your service
	        
	    }
	    private void setChosenDish(DishInRestaurant dish) {
	    	add_btn.setDisable(false);
	    	selected_dish=dish;
	        selected_dish_name.setText(dish.getName());
	        selected_dish_price.setText(dish.getPrice()+Globals.currency);
	        Image image;    
	        image = new Image(getClass().getResourceAsStream("/dishPics/"+selected_dish.getImageName()));
	        if(image==null)
	        {
	        	image = new Image(getClass().getResourceAsStream("/img/imageNotFound.jpg"));
	        }
	        selected_dish_img.setImage(image);
	        dish_options_vbox.getChildren().clear();
	        if(dish.getChooseSize()==1)
	    	{
	        	i++;
	    		RadioButton small=new RadioButton("Small ");
	    		RadioButton medium=new RadioButton("Medium ");
	    		RadioButton large=new RadioButton("Large");
	    		small.setToggleGroup(sizes);
	    		small.setSelected(true);
	    		small.setUserData("Small");
	    		medium.setToggleGroup(sizes);
	    		medium.setUserData("Medium");
	    		large.setToggleGroup(sizes);
	    		large.setUserData("Large");
	    		HBox size=new HBox();
	    		HBox.setMargin(size, new Insets(10));
	    		size.setAlignment(Pos.CENTER);
	    		size.getChildren().addAll(small,medium,large);
	    		dish_options_vbox.getChildren().add(size);
	    		
	    	}
	    	if(dish.getChooseCookingLvl()==1)
	    	{
	    		add_btn.setDisable(true);
	    		i++;
	    		ObservableList<String> options=FXCollections.observableArrayList("Rare","Medium-Rare","Medium","Medium-Well","Well-Done");
	    		
	    		r.setItems(options);
	    		r.setPromptText("Cooking level");
	    		r.getStyleClass().add("comboBox");
	    		HBox cookingLevels=new HBox();
	    		HBox.setMargin(cookingLevels, new Insets(10));
	    		cookingLevels.setAlignment(Pos.CENTER);
	    		cookingLevels.getChildren().add(r);
	    		dish_options_vbox.getChildren().add(cookingLevels);
	    		r.getSelectionModel().selectedItemProperty().addListener((obs,oldValue, newValue)-> {
	        	    if(newValue!=null)
	        	    {
	        	    	add_btn.setDisable(false);
	        	    }
	        	});
	    		
	    	}
	    	if(dish.getChooseExtras()==1)
	    	{
	    		i++;
	    		HBox extras=new HBox();
	    		HBox.setMargin(extras, new Insets(10));
	    		extra_input.setPromptText("Extras.Ex: No onions");
	    		extra_input.getStyleClass().add("text-box");
	    		extras.getChildren().addAll(extra_input);
	    		extras.setAlignment(Pos.CENTER);
	    		dish_options_vbox.getChildren().add(extras);
	
	    	}
	    	
	
	    	
	        //chosenFruitCard.setStyle("-fx-background-color: #" + fruit.getColor() + ";\n" +
	            //    "    -fx-background-radius: 30;");
	    	
	    }
	    
	    @FXML
	    void addToOrder(ActionEvent event) {
	    	DishInOrder dish;
	    	currentLvl=" ";
	    	currentSize=" ";
	    	extras=" ";
		    if(selected_dish.getChooseSize()==1)
	    		currentSize=sizes.getSelectedToggle().getUserData().toString();
	    	if(selected_dish.getChooseCookingLvl()==1)
	    		currentLvl=r.getSelectionModel().selectedItemProperty().get();
	    	if(selected_dish.getChooseExtras()==1)
	    		extras=extra_input.getText();
	    	int dishInOrderNumber=Globals.newOrder.getDishes().size()+1;
	    	dish=new DishInOrder(currentSize, currentLvl,extras ,selected_dish.getName(), selected_dish.getDishID(), 0,selected_dish.getPrice(),dishInOrderNumber);    	
	    	Globals.newOrder.addQuantity(selected_dish.getType());
	    	Globals.newOrder.addDish(dish);
	    	green_v_img.setVisible(true);
	    	
	    	PauseTransition pause = new PauseTransition(Duration.seconds(1));
	    	pause.setOnFinished(e -> green_v_img.setVisible(false));
	    	pause.play();
	    	
	    }

	   
	    @FXML
	    void back_to_branch_selection(ActionEvent event) {
	    	Globals.newOrder.removeAllDishes();
	    	Globals.loadInsideFXML( Globals.ChooseSupplierFXML);
	    }

	    void display_table(String type){  
	    	items_grid.getChildren().clear();
	    	setChosenDish(OrderClient.branch_menu.get(type).get(0));
	    	int row=1,col=0;
	    	for(int i=0;i<OrderClient.branch_menu.get(type).size();i++)
	    	{
	    		try {
	    		if(col==3) {
	    			row+=1;
	    			col=0;
	    		}
	    		DishInRestaurant current=OrderClient.branch_menu.get(type).get(i);
	    		FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/order/Dish.fxml"));
                VBox anchorPane;
                anchorPane = fxmlLoader.load();

                DishController DishController = fxmlLoader.getController();
                DishController.setData(current,menuListener);

	    		items_grid.add(anchorPane, col++, row);
	    		  //set grid width
	    		items_grid.setMinWidth(Region.USE_COMPUTED_SIZE);
	    		items_grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
	    		items_grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
	    		items_grid.setMinHeight(Region.USE_COMPUTED_SIZE);
	    		items_grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
	    		items_grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
	    		} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    }
	        
	    @FXML
	    void showAppetizers(ActionEvent event) {
	    	display_table("Appetizer");
	    }

	    @FXML
	    void showDessert(ActionEvent event) {
	    	display_table("Dessert");
	    }

	    @FXML
	    void showDrinks(ActionEvent event) {
	    	display_table("Drink");
	    }

	    @FXML
	    void showMains(ActionEvent event) {
	    	display_table("Main");
	    }

	    @FXML
	    void showSalads(ActionEvent event) {
	    	display_table("Salad");
	    }
	                   
		void checkDishes()
		{
			String first="";
			 for (Map.Entry<String,ArrayList<DishInRestaurant>> entry : OrderClient.branch_menu.entrySet())
			 {
		           if(!entry.getValue().isEmpty())
		           {
		        	   if(first.isEmpty()) first=entry.getKey();
		        	   switch(entry.getKey())
		        	   {
		        	   case "Appetizer":  menu_categories.getChildren().add(appetizer_btn); break;
		        	   case "Salad":menu_categories.getChildren().add(salad_btn);break;
		        	   case "Main":menu_categories.getChildren().add(main_dish_btn);break;
		        	   case "Dessert":menu_categories.getChildren().add(dessert_btn);break;
		        	   case "Drink":menu_categories.getChildren().add(drinks_btn);break;
		        	   }
		           }
			 }
			 menu_categories.setSpacing(5);
			 display_table(first);
			 
		}
		
		public Button defineButton(String str)
		{
			Button temp=new Button(str);
			temp.getStyleClass().add("ViewBtnOrange");
			temp.setMaxWidth(Double.MAX_VALUE);
			temp.getStyleClass().add("lbl");
			
			temp.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                	switch(str)
                	{
                	 	case "Appetizer":showAppetizers(e); break;
                	 	case "Salad":showSalads(e);break;
                	 	case "Main":showMains(e);break;
                	 	case "Dessert":showDessert(e); break;
                	 	case "Drink":showDrinks(e);break;
                	}

                }});
			return temp;
		}

}

