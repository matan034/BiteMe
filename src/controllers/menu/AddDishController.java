package menu;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddDishController {

    @FXML
    private TextField dish_name_input;


    @FXML
    private Button add_btn;
    
    @FXML
    private Label result_lbl;

    private String type;

    
    public void setType(String type)
    {
    	this.type=type;
    }
    @FXML
    void addDish(ActionEvent event) {
    	StartClient.order.accept("Add_Dish~"+dish_name_input.getText()+"~"+type);
    	result_lbl.setText(OrderClient.addDish);
    	
    }

}
