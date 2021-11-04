package orderpackage;


	import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
	import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

	public class OrderController {



	    @FXML
	    private Label return_from_db;

	    @FXML
	    private TextField field_to_update;

	    @FXML
	    private TextField val_to_update;

	    @FXML
	    private Button update_btn;

	    @FXML
	    private Button insert_btn;

	    @FXML
	    private TextField rest_info;

	    @FXML
	    private TextField order_num_info;

	    @FXML
	    private TextField order_time_info;

	    @FXML
	    private TextField phone_info;

	    @FXML
	    private TextField type_order_info;

	    @FXML
	    private TextField address_info;

	    @FXML
	    private Label ip_host;
	    
	    @FXML
	    private Button search_btn;
	    @FXML
	    private TextField search_box;
	    
	    private Connection my_conn = mysqlConnection.connectToDB();
	    
	    @FXML
	    protected void initialize() throws SQLException
	    {
	    	
	    	//ip_host.setText(my_conn.getClientInfo().getProperty(null));
	    }
	    
	    
	    @FXML
	    void insertOrder(ActionEvent event) {
	    	mysqlConnection.insertOrder(my_conn, rest_info.getText(), Integer.parseInt(order_num_info.getText()), order_time_info.getText(), phone_info.getText(),
	    			type_order_info.getText(), address_info.getText());
	    }
	    

	    @FXML
	    void searchOrder(ActionEvent event) {
	    	ResultSet r = mysqlConnection.getOrder(my_conn, Integer.parseInt(search_box.getText()));
	    	try {
	    	return_from_db.setText(r.getString(1)+r.getString(2)+r.getString(3)+r.getString(4)+r.getString(5)+r.getString(6));
	    	}
	    	catch (SQLException e) { }
	    	catch (NullPointerException e) {return_from_db.setText("Order is not found");}
	    }
	    
	    @FXML
	    void update(ActionEvent event) {
	    	mysqlConnection.update(my_conn, field_to_update.getText(),val_to_update.getText() , 1);
	    }

	}


