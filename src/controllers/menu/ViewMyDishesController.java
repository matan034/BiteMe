package menu;

import java.awt.TextField;

import clients.StartClient;
import common.Globals;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class ViewMyDishesController {
	@FXML
    private ComboBox<String> choose_Dish_type_combo_box;
    @FXML
    private Button view_branch_dishes_btn;
    @FXML
    private TextField My_Branch_id;
    @FXML
    public void initialize() {
    	StartClient.order.accept("Load_branches");
    	choose_Dish_type_combo_box.setItems(Globals.dishesTypes);
    }
    @FXML
    void viewDishes(ActionEvent event) {
    	Globals.dishType= choose_Dish_type_combo_box.getSelectionModel().getSelectedItem();
    	StartClient.order.accept("Load_menu~"+choose_Dish_type_combo_box.getSelectionModel().getSelectedItem()+"~"+My_Branch_id.getText());
    	Globals.loadFXML(null, Globals.dishFXML, event);
    }
}
