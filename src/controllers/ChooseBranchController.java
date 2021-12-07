package controllers;
import clients.StartClient;
import common.Globals;
import entity.Branch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
public class ChooseBranchController {


	    @FXML
	    private ComboBox<Branch> choose_branch_combo_box;
	    @FXML
	    private Button back_btn;
	    @FXML
	    private Button view_menu_btn;
	    @FXML
	    public void initialize() {
	    	StartClient.order.accept("Load_branches");
	    	choose_branch_combo_box.setItems(Globals.branches);
	    }
	    @FXML
	    void viewMenu(ActionEvent event) {
	    	Globals.newOrder.setBranch(choose_branch_combo_box.getSelectionModel().getSelectedItem());
	    	StartClient.order.accept("Load_dishes~"+choose_branch_combo_box.getSelectionModel().getSelectedItem());
	    	Globals.loadFXML(null, Globals.branch_menuFXML, event);
	    }
	    @FXML
	    void back(ActionEvent event)
	    {
	    	Globals.loadFXML(null, Globals.supply_wayFXML, event);
	    }

	}


