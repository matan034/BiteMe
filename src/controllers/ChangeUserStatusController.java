package controllers;

import clients.OrderClient;
import clients.StartClient;
import entity.Order;
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
    private TableView<User> table;
    

    @FXML
    private TableColumn<User, String> name_col;

    @FXML
    private TableColumn<User, String> id_col;

    @FXML
    private TableColumn<User, String> type_col;

    @FXML
    private TableColumn<User, String> status_col;

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
    
    private ObservableList<User> all_users;
    public void initialize()
    {
    	StartClient.order.accept("Load_users");
    	display_table();
    	status_cmb.setItems(FXCollections.observableArrayList("Frozen","Active"));
    	table.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection)->{
    		if(newSelection!=null) {
    			try {
    				selected_lbl.setText("User selected: "+table.getSelectionModel().getSelectedItem().getFullName());
    				status_cmb.getSelectionModel().select(table.getSelectionModel().getSelectedItem().getStatus());
    			}catch(Exception e) {e.printStackTrace();}
    		}
    	});

    }
    
    void display_table(){  
    	all_users = FXCollections.observableArrayList(OrderClient.all_users);
		name_col.setCellValueFactory(new PropertyValueFactory<User,String>("FullName"));
		id_col.setCellValueFactory(new PropertyValueFactory<User,String>("ID"));
		type_col.setCellValueFactory(new PropertyValueFactory<User,String>("Type"));
		status_col.setCellValueFactory(new PropertyValueFactory<User,String>("Status"));
		table.setItems(all_users);

    }
    @FXML
    void update(ActionEvent event) {
    	StartClient.order.accept("Update_user~"+status_cmb.getSelectionModel().getSelectedItem()+"~"+table.getSelectionModel().getSelectedItem().getID());
    	selected_lbl.setText(OrderClient.update_msg);
    	for(User u: all_users)
		{
			if(u.getID()==table.getSelectionModel().getSelectedItem().getID())
			{
				u.setStatus(status_cmb.getSelectionModel().getSelectedItem());
			}
		}
    	table.refresh();
    }

}
