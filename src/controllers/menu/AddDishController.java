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

public class AddDishController {

    @FXML
    private TextField dish_name_input;


    @FXML
    private Button add_btn;
    
    @FXML
    private Label result_lbl;

    private String type;

    private MyListener dishName;
    public void setListener(MyListener dishName)
    {
    	this.dishName=dishName;
    }
    public void setType(String type)
    {
    	this.type=type;
    }
    
    public void initialize()
    {
    	dish_name_input.setOnKeyPressed( event -> {
  		  if( event.getCode() == KeyCode.ENTER ) {
  		   addDish(event);
  		  }
  		} );
    }
    @FXML
    void addDish(Event event) {
    	StartClient.order.accept("Add_Dish~"+dish_name_input.getText()+"~"+type);
    	if(!OrderClient.addDish.equals("Dish Already Exist"))
    		dishName.onClickListener(dish_name_input.getText()+"~"+type+"~"+OrderClient.dishId);
    	dish_name_input.clear();
    	result_lbl.setText(OrderClient.addDish);
    	
    }

}
