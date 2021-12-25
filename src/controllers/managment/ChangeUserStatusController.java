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

public class ChangeUserStatusController {

    @FXML
    private TableView<Customer> table;
    

    @FXML
    private TableColumn<Customer, String> name_col;

    @FXML
    private TableColumn<Customer, String> id_col;

    @FXML
    private TableColumn<Customer, String> pAccount_col;
    @FXML
    private TableColumn<Customer, String> bAccount_col;

    @FXML
    private TableColumn<Customer, String> status_col;

    @FXML
    private TextField search_txt;

    @FXML
    private Button search_btn;
    
    @FXML
    private ComboBox<String> status_cmb= new ComboBox<String>();;

    @FXML
    private Label selected_lbl;

    @FXML
    private Button update_btn;

    @FXML
    private Button back_btn;
    
    private ObservableList<Customer> branch_customer;
    public void initialize()
    {
    	StartClient.order.accept("Load_branch_customers~"+OrderClient.user.getHomeBranch());
    	display_table();
    	status_cmb.setItems(FXCollections.observableArrayList("Frozen","Active"));
    	table.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection)->{
    		if(newSelection!=null) {
    			try {
    				selected_lbl.setText("User selected: "+table.getSelectionModel().getSelectedItem().getName());
    				status_cmb.getSelectionModel().select(table.getSelectionModel().getSelectedItem().getStatus());
    			}catch(Exception e) {e.printStackTrace();}
    		}
    	});

    }
    
    void display_table(){  
    	branch_customer = FXCollections.observableArrayList(OrderClient.branch_customers);
		name_col.setCellValueFactory(new PropertyValueFactory<Customer,String>("name"));
		id_col.setCellValueFactory(new PropertyValueFactory<Customer,String>("id"));
		pAccount_col.setCellValueFactory(new PropertyValueFactory<Customer,String>("stringPaccount"));
		bAccount_col.setCellValueFactory(new PropertyValueFactory<Customer,String>("stringBaccount"));
		status_col.setCellValueFactory(new PropertyValueFactory<Customer,String>("status"));
		table.setItems(branch_customer);

    }
    @FXML
    void update(ActionEvent event) {
    	StartClient.order.accept("Update_customer~"+status_cmb.getSelectionModel().getSelectedItem()+"~"+table.getSelectionModel().getSelectedItem().getId());
    	selected_lbl.setText(OrderClient.update_msg);
    	for(Customer c: branch_customer)
		{
			if(c.getId()==table.getSelectionModel().getSelectedItem().getId())
			{
				c.setStatus(status_cmb.getSelectionModel().getSelectedItem());
			}
		}
    	table.refresh();
    }

}
