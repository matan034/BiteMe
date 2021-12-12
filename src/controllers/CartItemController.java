package controllers;


import common.Globals;
import entity.DishInOrder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
    MyListener cartItemListener;
    
    @FXML
    void removeItem(ActionEvent event) {
    	cartItemListener.onClickListener(item);
    }

    public void removeButton()
    {
    	remove_btn.setVisible(false);
    }
    
    public void setData(DishInOrder d, MyListener cartItemListener)
    {
    	this.item=d;
    	this.cartItemListener=cartItemListener;
    	dish_name_lbl.setText(d.getDish_name());
    	dish_price_label.setText(d.getPrice()+Globals.currency);
    	if(!d.getCooking_lvl().equals(" "))
    	{
    		Label cook_level=new Label("Chosen Coooking Level:");
    		Label chosen_cook_level=new Label(d.getCooking_lvl());
    		HBox cookingLevels=new HBox();
    		cookingLevels.getChildren().addAll(cook_level,chosen_cook_level);
    		chosen_dish_options_vbox.getChildren().add(cookingLevels);
    	}
    	if(!d.getSize().equals(" "))
    	{
    		Label size_lbl=new Label("Chosen Size:");
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
