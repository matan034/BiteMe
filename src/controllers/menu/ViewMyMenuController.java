package menu;



import clients.StartClient;
import common.Globals;
import entity.Branch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ViewMyMenuController {
	@FXML
	    private ComboBox<String> choose_menu_type_combo_box;
	    @FXML
	    private Button view_branch_menu_btn;
	    @FXML
	    private TextField My_Branch_id;
	    @FXML
	    public void initialize() {
	    	StartClient.order.accept("Load_branches");
	    	choose_menu_type_combo_box.setItems(Globals.dishesTypes);
	    }
	    @FXML
	    void viewMenu(ActionEvent event) {
	    	Globals.dishType= choose_menu_type_combo_box.getSelectionModel().getSelectedItem();
	    	StartClient.order.accept("Load_menu~"+choose_menu_type_combo_box.getSelectionModel().getSelectedItem()+"~"+My_Branch_id.getText());
	    	Globals.loadFXML(null, Globals.menuFXML, event,null);
	    }
	    
	    

}
