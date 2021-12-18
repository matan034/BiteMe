package order;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import entity.Supplier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
public class ChooseSupplierController {


	    @FXML
	    private ComboBox<Supplier> choose_branch_combo_box;
	    @FXML
	    private Button back_btn;
	    @FXML
	    private Button view_menu_btn;
	    @FXML
	    public void initialize() {
	    	StartClient.order.accept("Load_suppliers~"+OrderClient.user.getHomeBranch());
	    	choose_branch_combo_box.setItems(Globals.suppliers);
	    	view_menu_btn.setDisable(true);
	    	choose_branch_combo_box.getSelectionModel().selectedItemProperty().addListener((obs,oldValue, newValue)-> {
	    	    if(newValue!=null)
	    	    {
	    	    	view_menu_btn.setDisable(false);
	    	    }
	    	});
	    }
	    @FXML
	    void viewMenu(ActionEvent event) {
	    	Globals.newOrder.setSupplier(choose_branch_combo_box.getSelectionModel().getSelectedItem());
	    	StartClient.order.accept("Load_dishes~"+choose_branch_combo_box.getSelectionModel().getSelectedItem());
	    	Globals.loadInsideFXML(Globals.branch_menuFXML);
	    }
	    @FXML
	    void back(ActionEvent event)
	    {
	    	Globals.loadInsideFXML( Globals.supply_wayFXML);
	    }

	}


