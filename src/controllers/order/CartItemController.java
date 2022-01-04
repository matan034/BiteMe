package order;


import common.Globals;
import entity.DishInOrder;
import general.MyListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/**
 * This class is used to show a single dish item in cart screen

 * @param dish_name_lbl = lbl to set dish name
 * @param dish_price_label = lbl to set dish price
 * @param chosen_dish_options_vbox = vbox used for dispalying relevant dish options
 * @param remove_btn = button for removing dish from cart thru listener
 * @param item = saves what dish this class will represant 
 * @param cartItemListener = listener for removing item from cart in cart controller
 * 
 * 
 * @author      Matan Weisberg
 * @version     1.0               
 * @since       01.01.2022        
 */
public class CartItemController {

    @FXML
    private Label dish_name_lbl;

    @FXML
    private Label dish_price_label;

    @FXML
    private VBox chosen_dish_options_vbox;
    @FXML
    private Button remove_btn;


    private DishInOrder item;
    private MyListener cartItemListener;
    
    /**
     *This func is for removing item from cart thru listener in cart screen
     *@param event - action event for pressing remove button
     **/
    @FXML
    void removeItem(ActionEvent event) {
    	cartItemListener.onClickListener(item);
    }

    /**
     *This func set visiblty of remove button to false
     **/
    public void removeButton()
    {
    	remove_btn.setVisible(false);
    }
    
    /**
     *This func is setting the controller with the current dish and listener uses to remove dish
     *@param d the current dish to be used in this class
     *@param cartItemLisener listener to use to remove item in cart screen
     **/
    public void setData(DishInOrder d, MyListener cartItemListener)
    {
    	this.item=d;
    	this.cartItemListener=cartItemListener;
    	dish_name_lbl.setText(d.getDish_name());
    	dish_price_label.setText(d.getPrice()+Globals.currency);
    	if(!d.getCooking_lvl().equals(" "))
    	{
    		Label cook_level=new Label("Coooking Level:");
    		Label chosen_cook_level=new Label(d.getCooking_lvl());
    		HBox cookingLevels=new HBox();
    		cookingLevels.getChildren().addAll(cook_level,chosen_cook_level);
    		chosen_dish_options_vbox.getChildren().add(cookingLevels);
    	}
    	if(!d.getSize().equals(" "))
    	{
    		Label size_lbl=new Label("Size:");
    		Label chosen_size=new Label(d.getSize());
    		HBox size=new HBox();
    		size.getChildren().addAll(size_lbl,chosen_size);
    		chosen_dish_options_vbox.getChildren().add(size);
    	}
    	if(!d.getExtras().equals(" ")&&!d.getExtras().equals(""))
    	{
    		Label extras_lbl=new Label("Extras: "+d.getExtras());
    		HBox extras=new HBox();
    		extras.getChildren().addAll(extras_lbl);
    		chosen_dish_options_vbox.getChildren().add(extras);
    	}
    }
}
