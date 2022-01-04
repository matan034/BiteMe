package managment;

import clients.OrderClient;
import clients.StartClient;
import entity.Customer;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * this class is used to display a table where we can change a users status from active to frozen to deleted and so on 

 * @author      daniel aibinder
 * @version     1.0               
 * @since       01.01.2022        
 */


public class ChangeUserStatusController {

	/**table main table that holds data*/
    @FXML
    private TableView<Customer> table;
    
    /**name_col in table for names*/
    @FXML
    private TableColumn<Customer, String> name_col;
    /**id_col in table for ids*/
    @FXML
    private TableColumn<Customer, String> id_col;
    /**pAccount_col col for displaying private account number*/
    @FXML
    private TableColumn<Customer, String> pAccount_col;
    /**bAccount_col col for displaying private account number */
    @FXML
    private TableColumn<Customer, String> bAccount_col;
    /**status_col col for displaying accounts status(frozen,active)*/
    @FXML
    private TableColumn<Customer, String> status_col;

    /**status_cmb combo box for selecting status we want to change to*/
    @FXML
    private ComboBox<String> status_cmb= new ComboBox<String>();;
    /**selected_lbl shows which user is selcted in the table*/
    @FXML
    private Label selected_lbl;
    /**update_btn button user pressed to update the account*/
    @FXML
    private Button update_btn;
    /**back_btn back button to return to previous screen*/
    @FXML
    private Button back_btn;
    /**branch_customer observable list of customers the table usees to update correctly*/
    private ObservableList<Customer> branch_customer;
    
    /**
     * func to initalize our table , we load all customers in the branch the manager is logged into 
     * we initalize our table with data 
     * we initialize our comboboxes
     * and we set an event listener to recognise selected useres from table */
    public void initialize()
    {
    	StartClient.order.accept("Load_branch_customers~"+OrderClient.user.getHomeBranch());
    	display_table();
    	status_cmb.setItems(FXCollections.observableArrayList("Frozen","Active","Delete"));
    	table.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection)->{
    		if(newSelection!=null) {
    			try {
    				selected_lbl.setText("User selected: "+table.getSelectionModel().getSelectedItem().getName());
    				status_cmb.getSelectionModel().select(table.getSelectionModel().getSelectedItem().getStatus());
    			}catch(Exception e) {e.printStackTrace();}
    		}
    	});

    }
    
    /**
     * func to initalize table data we do so by initalizing each col with a new cell factory with it's entity 
     * and we populate each col data by branch_customers (in OrderClient) we loaded in the initalize phase of the controller
     * finally we set all collumns to the talbe  */
    void display_table(){  
    	branch_customer = FXCollections.observableArrayList(OrderClient.branch_customers);
		name_col.setCellValueFactory(new PropertyValueFactory<Customer,String>("name"));
		id_col.setCellValueFactory(new PropertyValueFactory<Customer,String>("id"));
		pAccount_col.setCellValueFactory(new PropertyValueFactory<Customer,String>("stringPaccount"));
		bAccount_col.setCellValueFactory(new PropertyValueFactory<Customer,String>("stringBaccount"));
		status_col.setCellValueFactory(new PropertyValueFactory<Customer,String>("status"));
		table.setItems(branch_customer);

    }
    
    /**
    *update func gets activates on pressing the update button 
    *we check the combo box for what we want to change if we want to delete we send delete_account to server
    *if we want to change status we send update_customer to server
    *either way find update the correct table in DB and find the customer we just updated in the table to correctly display it's new status
    *or if it's been deleted to remove it from the observable list we use to populate our table */
    @FXML
    void update(ActionEvent event) {
    	if(!status_cmb.getSelectionModel().getSelectedItem().equals("Delete")) {
    		StartClient.order.accept("Update_customer~"+status_cmb.getSelectionModel().getSelectedItem()+"~"+table.getSelectionModel().getSelectedItem().getId());
	    	selected_lbl.setText(OrderClient.update_msg);
	    	for(Customer c: branch_customer)
			{
				if(c.getId()==table.getSelectionModel().getSelectedItem().getId())
				{
					c.setStatus(status_cmb.getSelectionModel().getSelectedItem());
				}
			}
    	}
    	else {//delete account
    		StartClient.order.accept("Delete_account~"+table.getSelectionModel().getSelectedItem().getId());
    		selected_lbl.setText("Account deleted");
    		Customer toRemove=null;
    		for(Customer c: branch_customer)
			{
    			if(c.getId()==table.getSelectionModel().getSelectedItem().getId()) {
    				toRemove=c;
    				OrderClient.branch_customers.remove(c);
    			}
			}
    		if(toRemove!=null)
    			branch_customer.remove(toRemove);

    	}
    	OrderClient.customer=null;
    	table.refresh();
    }

}
