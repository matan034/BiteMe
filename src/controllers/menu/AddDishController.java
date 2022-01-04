package menu;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import general.MyListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;


/**
 * Controller for adding a new dish, Used in CreateNewMenuScreen when a user pressed the PLUS icon opens AddDishScreen where user can 
 * add a new dish not directly to the menu but to the potential dishes that can be added(left side list view)
 * @param dish_name_input = text are for user to input a dish name
 * @param add_btn = button for confirming inputted name
 * @param result_lbl= label to display messages to user
 * @param type = dishes type (main , side ,etc)
 * @param dishName= myListener class to pass data about the dishname user inputted back to createnewmenu
 *  @author      matan weisberg
 * @version     1.0               
 * @since       01.01.2022  
 **/
public class AddDishController {

    @FXML
    private TextField dish_name_input;


    @FXML
    private Button add_btn;
    
    @FXML
    private Label result_lbl;

    private String type;

    private MyListener dishName;
    
    /**
     * setter for Listener that Create new menu passes 
     * @param dishName a function that has the onClickListener defined that we activate*/
    public void setListener(MyListener dishName)
    {
    	this.dishName=dishName;
    }
    /**
     * setter for dish type that Create new menu passes 
     * @param type a string that contains dish type (main,side)*/
    public void setType(String type)
    {
    	this.type=type;
    }
    /**
     * initializes our controller so that enter key also works for inputting a dish name*/
    public void initialize()
    {
    	dish_name_input.setOnKeyPressed( event -> {
  		  if( event.getCode() == KeyCode.ENTER ) {
  		   addDish(event);
  		  }
  		} );
    }
    /**
     * addDish function activates on button press add_btn 
     * we send the new dish to our server if the dish doesnt already exist we pass on that dish name and type back to create new menu 
     * so it can be added to potential dishes(left list view)*/
    @FXML
    void addDish(Event event) {
    	StartClient.order.accept("Add_Dish~"+dish_name_input.getText()+"~"+type);
    	if(!OrderClient.addDish.equals("Dish Already Exist"))
    		dishName.onClickListener(dish_name_input.getText()+"~"+type+"~"+OrderClient.dishId);
    	dish_name_input.clear();
    	result_lbl.setText(OrderClient.addDish);
    	
    }

}
