package orderpackage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class serverController {

    @FXML
    private Label connect_status;
    @FXML
    private TextField ip_input;

    @FXML
    private TextField port_input;

    @FXML
    private TextField db_name_input;

    @FXML
    private TextField db_user_input;

    @FXML
    private TextField db_pass_input;

    @FXML
    private Label IP_label;

    @FXML
    private Label Port_label;

    @FXML
    private Label db_name_label;

    @FXML
    private Label db_user_label;

    @FXML
    private Label db_password_label;

    @FXML
    private Button connect_btn;

    @FXML
    private Button disconnect_btn;

    @FXML
    private Button close_btn;

    @FXML
    void close_window(ActionEvent event) {
    	Stage stage = (Stage) close_btn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void connectToDB(ActionEvent event) {
    	String res;
    	res=BiteMeServer.connectToDB(ip_input.getText(), port_input.getText(), db_name_input.getText(), db_user_input.getText(), db_pass_input.getText());
    	System.out.println(res);
    	connect_status.setText(res);
    }

    @FXML
    void disconnectFromDB(ActionEvent event) {
    	connect_status.setText(BiteMeServer.disconnectDB());
    }

}
