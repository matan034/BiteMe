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

/**
 * This class is for showing available supplier for the user according to his home brnach
 * user can choose in upper left screen a different branch to get the supplier from that branch

 * @param choose_branch_combo_box = suppliers combobox
 * @param back_btn = back button to get back to last screen(qr w4c)
 * @param take_away_btn = button available to presses after selecting supplier, for take away order
 * @param delivery_btn = button available to presses after selecting supplier, for delivery order
 * 
 * 
 * @author      Matan Weisberg
 * @version     1.0               
 * @since       01.01.2022        
 */
public class ChooseSupplierController {

	    @FXML
	    private ComboBox<Supplier> choose_branch_combo_box;
	    @FXML
	    private Button back_btn;

	    
	    @FXML
	    private Button take_away_btn;

	    @FXML
	    private Button delivery_btn;
	    
	    /**
	     *This func initializes our controller
	     *loads all available suppliers for user home branch
	     *set a listener for the branch selection combobox, when change loads relevant suppliers
	     *set a listner for suppliers comobobox for enabling take away and delivery buttons
	     **/
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
	    	    	choose_branch_combo_box.getSelectionModel().clearSelection();
	    	    	if(choose_branch_combo_box.getItems()!=null)
	    	    		choose_branch_combo_box.getItems().clear();
	    	    	choose_branch_combo_box.setItems(Globals.suppliers);	
	    	    }	    
	    	});
	    }
	    /**
	     *This func is back to last screen(W4C recognize)
	     *@param event - action event for pressing back button
	     **/
	    @FXML
	    void back(ActionEvent event)
	    {
	    	Globals.loadInsideFXML( Globals.W4CLoginFXML);
	    }
	    
	    /**
	     *This func creates a new order with delivery method
	     *and proceeds to show supplier menu
	     *@param event - action event for pressing delivey button
	     **/
	    @FXML
	    void delivery(ActionEvent event) {
	    	Globals.newOrder=new Order("Delivery",OrderClient.w4c_card,OrderClient.customer,OrderClient.paccount,OrderClient.baccount);
	    	Globals.newOrder.setSupplier(choose_branch_combo_box.getSelectionModel().getSelectedItem());
	    	
	    	Globals.loadInsideFXML(Globals.branch_menuFXML);
	    }
	    /**
	     *This func creates a new order with take away method
	     *and proceeds to show supplier menu
	     *@param event - action event for pressing take away button
	     **/
	    @FXML
	    void takeAway(ActionEvent event) {
	    	Globals.newOrder=new Order("Take-Away",OrderClient.w4c_card,OrderClient.customer,OrderClient.paccount,OrderClient.baccount);
	    	Globals.newOrder.setSupplier(choose_branch_combo_box.getSelectionModel().getSelectedItem());
	    	Globals.loadInsideFXML(Globals.branch_menuFXML);

	    }

	}


