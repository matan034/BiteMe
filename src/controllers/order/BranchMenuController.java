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
/**
 * This class is used to show menu of a supplier to the customer
 * The controller loads dishes of the supplier from the DB
 * Divides the dishes by type 
 * user can choose dish from menu, than select the dish details and add it to the cart
 * when there are dishes in the cart user can view the cart and remove dishes or continue to checkout
 * 
 * @param back_btn = button to return to last screen (choose supplier)
 * @param add_btn = add a dish to cart after fill dish details
 * @param selected_item_label = lbl to set the selected dish
 * @param cart_img = cart img using as a button to open the cart,only available when cart has items
 * @param cart_count = lbl to set the amount of items in cart
 * @param items_grid = grid pain containing all dishes in the category
 * @param selected_dish_name = lbl to set selected dish name
 * @param selected_dish_price = lbl to set selected dish price
 * @param selected_dish_img = image view showing current dish
 * @param dish_options_vbox = vbox loading each dish options
 * @param cart_items_vbox = vbox loading all dishes in cart
 * @param total_price_label = lbl to set the total price of items in cart
 * @param green_v_img = img of a green v, appears when a dish is added to the cart
 * @param cart_vbox = vbox containing all the cart details, appears when clickin cart img
 * @param back_image = back image to get back to last screen
 * @param borderPane = the pane of all the menu, when loading the screen used to disable the menu until load is complete
 * @param menu_categories = uses to get all the button for menu categories
 * @param pi = shows progress when loading screen
 * @param menuListener = listen to what dish was chosen and set it 
 * @param currentSize = saves the selected size
 * @param currentLvl = saves the selected cookin lvl
 * @param extras = saves the selected extras
 * @param sizes = toggle group for size options
 * @param extra_input = textfield to input dish extras
 * @param selected_dish = save the selected dishInMenu
 * @param appetizer_btn = button for showing appetizer dishes
 * @param salad_btn =  button for showing salad dishes
 * @param main_dish_btn =  button for showing main dishes
 * @param dessert_btn =  button for showing dessert dishes
 * @param drinks_btn =  button for showing drinks dishes
 * @param r = combobox for selecting cookinglvl
 * @param i = used to count the selectes dish options
 * @param firstClick = usesd for showing cart screen
 * 
 * 
 * @author      Matan Weisberg
 * @version     1.0               
 * @since       01.01.2022        
 */
public class BranchMenuController {

	/** @param back_btn = button to return to last screen (choose supplier)*/
	    @FXML
	    private Button back_btn;
	 /** @param add_btn = add a dish to cart after fill dish details*/
	    @FXML
	    private Button add_btn;
	 /**selected_item_label = lbl to set the selected dish*/
	    @FXML
	    private Label selected_item_label;
	 /**cart_img = cart img using as a button to open the cart,only available when cart has items*/
	    @FXML
	    private ImageView cart_img;
	    /**cart_count = lbl to set the amount of items in cart*/
	    @FXML
	    private Label cart_count;
	    /**items_grid = grid pain containing all dishes in the category*/
	    @FXML
	    private GridPane items_grid;
	    /**selected_dish_name = lbl to set selected dish name*/
	    @FXML
	    private Label selected_dish_name;
	    /** selected_dish_price = lbl to set selected dish pric*/
	    @FXML
	    private Label selected_dish_price;
	    /** selected_dish_img = image view showing current dish*/
	    @FXML
	    private ImageView selected_dish_img;
	    /** dish_options_vbox = vbox loading each dish options*/
	    @FXML
	    private VBox dish_options_vbox;
	    /**m cart_items_vbox = vbox loading all dishes in cart*/
	    @FXML
	    private VBox cart_items_vbox;
	    /**lbl to set the total price of items in car*/
	    @FXML
	    private Label total_price_label;
	 
	    /**img of a green v, appears when a dish is added to the cart*/
	    @FXML
	    private ImageView green_v_img;
	    /** vbox containing all the cart details, appears when clickin cart img*/
	    @FXML
	    private VBox cart_vbox;
	    /**back image to get back to last screen*/
	    @FXML
	    private ImageView back_image;
	    /**the pane of all the menu, when loading the screen used to disable the menu until load is complete*/
	    @FXML
	    private BorderPane borderPane;
	    /**uses to get all the button for menu categories*/
	    @FXML
	    private HBox menu_categories;
	    /**shows progress when loading screen*/
	    @FXML
	    private ProgressIndicator pi;
	    /**listen to what dish was chosen and set it */
	    private MyListener menuListener;
	    /**saves the selected size, saves the selected cookin lvl ,saves the selected extras */
	    private String currentSize,currentLvl,extras;
	    /**toggle group for size options*/
	    private ToggleGroup sizes=new ToggleGroup();
	    /**textfield to input dish extras*/
	    private TextField extra_input=new TextField();
	    /**save the selected dishInMenu*/
	    private DishInRestaurant selected_dish;
	    /**group of buttons in hbox each button takes you to the menu type*/
	    private Button appetizer_btn,salad_btn,main_dish_btn,dessert_btn,drinks_btn;
	    /**combobox for selecting cookinglvl*/
	    private ComboBox<String> r=new ComboBox<>();
	    /**used to count the selectes dish options*/
	    private int i=0;
	    /**used for showing cart screem*/
	    private Boolean firstClick=true;
	    
	    
	    
	    
	    /**
	     *This func open cart screen when cart img is pressed
	     *@param event MouseEvent for event details
	     **/
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
	    
	    /**
	     *This func initializes our controller, disables the main screen and showing progrees indicator while loading all dishes
	     *sets a listener for the selected dish
	     *sets categeories button according to supplier dishes
	     **/
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
	    
	    
	    
	    /**
	     *this func sets the current dish display according to dish options
	     *@param dish - the selected dish
	     *
	     **/
	    private void setChosenDish(DishInRestaurant dish) {
	    	add_btn.setDisable(false);
	    	selected_dish=dish;
	        selected_dish_name.setText(dish.getName());
	        selected_dish_price.setText(dish.getPrice()+Globals.currency);
	        Image image;  
	        try {
	        	image = new Image(getClass().getResourceAsStream("/dishPics/"+selected_dish.getImageName()));
	        }
	        catch (NullPointerException e) {
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
	    }
	    
	    
	    /**
	     *This func runs when pressing add to order button,
	     * add the dish to current order cart, displays a green v for user for 1 second
	     * @param event - action event for pressing button event
	     **/
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

	    /**
	     *This func is for back button, goes back to supllier selection screen
	     *@param event-  action event for pressing button event
	     **/
	    @FXML
	    void back_to_branch_selection(ActionEvent event) {
	    	Globals.newOrder.removeAllDishes();
	    	Globals.loadInsideFXML( Globals.ChooseSupplierFXML);
	    }

	    /**
	     *This method is for setting up all the menu for a chosen type
	     *@param type - selected type to show
	     **/
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
	        
	    /**
	     *This func is for showing appetizer dishes in menu
	     *@param event - for the presses button event
	     **/
	    @FXML
	    void showAppetizers(ActionEvent event) {
	    	display_table("Appetizer");
	    }
	    /**
	     *This func is for showing dessert dishes in menu
	     *@param event - for the presses button event
	     **/
	    @FXML
	    void showDessert(ActionEvent event) {
	    	display_table("Dessert");
	    }
	    /**
	     *This func is for showing drinks  in menu
	     *@param event - for the presses button event
	     **/
	    @FXML
	    void showDrinks(ActionEvent event) {
	    	display_table("Drink");
	    }
	    /**
	     *This func is for showing mains dishes in menu
	     *@param event - for the presses button event
	     **/
	    @FXML
	    void showMains(ActionEvent event) {
	    	display_table("Main");
	    }
	    /**
	     *This func is for showing salads dishes in menu
	     *@param event - for the presses button event
	     **/
	    @FXML
	    void showSalads(ActionEvent event) {
	    	display_table("Salad");
	    }
	           
	    /**
	     *This func checks the loades dishes from DB and sets the menu categories buttons according to it
	     **/
		private void checkDishes()
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
		
	    /**
	     *This func sets up a buttons giving each button an action event according to it's type 
	     *for example appetizer button will show appetizer and so on
	     *@param str - the category name to be displayed and action is according to
	     *@return button the button we defined in the class 
	     **/
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

