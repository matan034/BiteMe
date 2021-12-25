package order;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import entity.Order;
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
	    private Button take_away_btn;

	    @FXML
	    private Button delivery_btn;
	    
	    
	    @FXML
	    public void initialize() {
	    	StartClient.order.accept("Load_suppliers~"+OrderClient.user.getHomeBranch());
	    	choose_branch_combo_box.setItems(Globals.suppliers);
	    	take_away_btn.setDisable(true);
	    	delivery_btn.setDisable(true);
	    	choose_branch_combo_box.getSelectionModel().selectedItemProperty().addListener((obs,oldValue, newValue)-> {
	    	    if(newValue!=null)
	    	    {
	    	    	take_away_btn.setDisable(false);
	    	    	delivery_btn.setDisable(false);
	    	    }
	    	});
	    	
	    	Globals.index_controller.getComboBoxBranch().getSelectionModel().selectedItemProperty().addListener((obs,oldValue, newValue)-> {
	    	    if(newValue!=null)
	    	    {
	    	    	StartClient.order.accept("Load_suppliers~"+newValue.getBranchID());
	    	    	choose_branch_combo_box.getSelectionModel().clearSelection();// need to make prompt text appear!!
	    	    	choose_branch_combo_box.setItems(Globals.suppliers);
	    	    	
	    	    }
	    	    
	    	});
	    }

	    @FXML
	    void back(ActionEvent event)
	    {
	    	Globals.loadInsideFXML( Globals.W4CLoginFXML);
	    }
	    
	    @FXML
	    void delivery(ActionEvent event) {
	    	Globals.newOrder=new Order("Delivery",OrderClient.w4c_card,OrderClient.customer);
	    	Globals.newOrder.setSupplier(choose_branch_combo_box.getSelectionModel().getSelectedItem());
	    	StartClient.order.accept("Load_dishes~"+choose_branch_combo_box.getSelectionModel().getSelectedItem());
	    	
	    	Globals.loadInsideFXML(Globals.branch_menuFXML);
	    }

	    @FXML
	    void takeAway(ActionEvent event) {
	    	Globals.newOrder=new Order("Take-Away",OrderClient.w4c_card,OrderClient.customer);//sent cutomer before
	    	Globals.newOrder.setSupplier(choose_branch_combo_box.getSelectionModel().getSelectedItem());
	    	StartClient.order.accept("Load_dishes~"+choose_branch_combo_box.getSelectionModel().getSelectedItem());
	    	
	    	Globals.loadInsideFXML(Globals.branch_menuFXML);

	    }

	}


