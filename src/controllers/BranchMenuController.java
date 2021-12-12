package controllers;




import java.io.IOException;


import clients.OrderClient;
import common.Globals;
import entity.Dish;
import entity.DishInOrder;

import javafx.collections.ListChangeListener;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.RadioButton;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

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
	    private AnchorPane cart_pane;
	    
	    private MyListener menuListener;
	    private String currentSize,currentLvl,extras;
	    private ToggleGroup sizes=new ToggleGroup(),cooklevels=new ToggleGroup();
	    private TextField extra_input=new TextField();
	    private Dish selected_dish;
	    private int i=0;
	    private Boolean firstClick=true;
	    @FXML
	    void goCart(MouseEvent event) {
	    	AnchorPane newLoadedPane;
	    	if(Integer.parseInt(cart_count.getText())!=0)
	    	{
		    	if(firstClick)
		    	{
		    		try {
		    		firstClick=false;
					newLoadedPane = FXMLLoader.load(getClass().getResource("/order/CartScreen.fxml"));
					cart_pane.getChildren().add(newLoadedPane); 
		    		} catch (IOException e) {
					// TODO Auto-generated catch block
		    			e.printStackTrace();
		    		}
		    	}
		    	else
		    	{
		    		firstClick=true;
		    		cart_pane.getChildren().clear();
		    	}
	    	}
	    	
	    }
	    
	    public void initialize()
	    {
	    	add_btn.setDisable(true);
	    	menuListener = new MyListener() {
	    		   @Override
	                public void onClickListener(Object dish) {
	                    setChosenDish((Dish)dish);
	                }  
			};

	    	display_table();
	    	Globals.newOrder.getDishes().addListener(new ListChangeListener<DishInOrder>() { 
	    		@Override
		    	 public void onChanged(ListChangeListener.Change<? extends DishInOrder> c) {
	    			if(c.next())
	    			{
	    				int item_cnt=Globals.newOrder.getDishes().size();
	    				if(item_cnt==0) cart_pane.getChildren().clear();
	    				cart_count.setText(Integer.toString(item_cnt));
	    			}
	    				
	             }
	         });

	    }
	    private void setChosenDish(Dish dish) {
	    	add_btn.setDisable(false);
	    	selected_dish=dish;
	        selected_dish_name.setText(dish.getName());
	        selected_dish_price.setText(dish.getPrice()+Globals.currency);
	        Image image = new Image(getClass().getResourceAsStream(dish.getImgSrc()));
	        selected_dish_img.setImage(image);
	        dish_options_vbox.getChildren().clear();
	        if(dish.getChooseSize()==1)
	    	{
	        	i++;
	    		Label size_lbl=new Label("Choose Size:");
	    		RadioButton small=new RadioButton("Small");
	    		RadioButton medium=new RadioButton("Medium");
	    		RadioButton large=new RadioButton("Large");
	    		small.setToggleGroup(sizes);
	    		small.setSelected(true);
	    		small.setUserData("Small");
	    		medium.setToggleGroup(sizes);
	    		medium.setUserData("Medium");
	    		large.setToggleGroup(sizes);
	    		large.setUserData("Large");
	    		HBox size=new HBox();
	    		size.getChildren().addAll(size_lbl,small,medium,large);
	    		dish_options_vbox.getChildren().add(size);
	    		
	    	}
	    	if(dish.getChooseCookingLvl()==1)
	    	{
	    		i++;
	    		Label cook_level=new Label("Choose Coooking Level:");
	    		RadioButton r=new RadioButton("Rare");
	    		RadioButton mr=new RadioButton("Medium-Rare");
	    		RadioButton m=new RadioButton("Medium");
	    		RadioButton mw=new RadioButton("Medium-Well");
	    		RadioButton wd=new RadioButton("Well-Done");
	    		r.setUserData("Rare");
	    		mr.setUserData("Medium-Rare");
	    		m.setUserData("Medium");
	    		mw.setUserData("Medium-Well");
	    		wd.setUserData("Well-Done");
	    		r.setToggleGroup(cooklevels);
	    		r.setSelected(true);
	    		mr.setToggleGroup(cooklevels);
	    		m.setToggleGroup(cooklevels);
	    		mw.setToggleGroup(cooklevels);
	    		wd.setToggleGroup(cooklevels);
	    		HBox cookingLevels=new HBox();
	    		VBox vbox=new VBox();
	    		cookingLevels.getChildren().addAll(r,mr,m,mw,wd);
	    		vbox.getChildren().addAll(cook_level, cookingLevels);
	    		dish_options_vbox.getChildren().add(vbox);

	    	}
	    	if(dish.getChooseExtras()==1)
	    	{
	    		i++;
	    		Label extras_lbl=new Label("Extras:");
	    		HBox extras=new HBox();
	    		extras.getChildren().addAll(extras_lbl,extra_input);
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
	    		currentLvl=cooklevels.getSelectedToggle().getUserData().toString();
	    	if(selected_dish.getChooseExtras()==1)
	    		extras=extra_input.getText();
	    	dish=new DishInOrder(currentSize, currentLvl,extras ,selected_dish.getName(), selected_dish.getDishID(), 0,selected_dish.getPrice());    	
	    	Globals.newOrder.addDish(dish);
	    }

	   
	    @FXML
	    void back_to_branch_selection(ActionEvent event) {
	    	Globals.newOrder.removeAllDishes();
	    	Globals.loadInsideFXML( Globals.ChooseBranchFXML);
	    }

	    void display_table(){  
	    	setChosenDish(OrderClient.branch_menu.get(0));
	    	int row=1,col=0;
	    	for(int i=0;i<OrderClient.branch_menu.size();i++)
	    	{
	    		try {
	    		if(col==3) {
	    			row+=1;
	    			col=0;
	    		}
	    		Dish current=OrderClient.branch_menu.get(i);
	    		FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/order/Dish.fxml"));
                AnchorPane anchorPane;
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
	        

	                   


}

