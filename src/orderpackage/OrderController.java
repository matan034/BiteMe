package orderpackage;


	import java.sql.Connection;

import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
	import javafx.scene.control.Button;
	import javafx.scene.control.TextField;

	public class OrderController {

	    @FXML
	    private Button search_btn;

	    @FXML
	    private TextField search_box;

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
	    private Connection my_conn = mysqlConnection.connectToDB();
	    @FXML
	    void insertOrder(ActionEvent event) {
	    	mysqlConnection.insertOrder(my_conn, rest_info.getText(), Integer.parseInt(order_num_info.getText()), order_time_info.getText(), phone_info.getText(),
	    			type_order_info.getText(), address_info.getText());
	    }
	    

	    @FXML
	    void searchOrder(ActionEvent event) {

	    }

	}


