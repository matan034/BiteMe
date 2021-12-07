package controllers;
import clients.LoginUI;
import clients.OrderClient;
import entity.Order;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    
    private ObservableList<User> all_users;
    public void initialize()
    {
    	LoginUI.order.accept("Load_users");
    	display_table();

    }
    
    void display_table(){  
    	all_users = FXCollections.observableArrayList(OrderClient.all_users);
		name_col.setCellValueFactory(new PropertyValueFactory<User,String>("FullName"));
		id_col.setCellValueFactory(new PropertyValueFactory<User,String>("ID"));
		type_col.setCellValueFactory(new PropertyValueFactory<User,String>("Type"));
		status_col.setCellValueFactory(new PropertyValueFactory<User,String>("Status"));
		table.setItems(all_users);

    }

}
