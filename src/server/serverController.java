package server;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class serverController {

    @FXML
    private Label connect_status;
    @FXML
    private TextField ip_input ;

    @FXML
    private TextField port_input;

    @FXML
    private TextField db_name_input;

    @FXML
    private TextField db_user_input;

    @FXML
    private PasswordField db_pass_input;

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

	
	private String getport() {
		return port_input.getText();			
	}
	

	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/gui/serverFXML.fxml"));		
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/gui/ServerPort.css").toExternalForm());
		primaryStage.setTitle("Server");
		primaryStage.setScene(scene);
		primaryStage.show();		
	}
    @FXML
    void close_window(ActionEvent event) {
    	Stage stage = (Stage) close_btn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void connectToDB(ActionEvent event) {
    	String p;
		p=getport();
		if(p.trim().isEmpty()) {
			System.out.println("You must enter a port number");
					
		}
		else
		{
			//((Node) event.getSource()).getScene().getWindow().hide(); //hiding primary window
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			ServerStart.runServer(p);
		}
    	String res;
    	res=BiteMeServer.connectToDB(ip_input.getText(), port_input.getText(), db_name_input.getText(), db_user_input.getText(), db_pass_input.getText());
    	System.out.println(res);
    	connect_status.setText(res);
    }

    @FXML
    void disconnectFromDB(ActionEvent event) {
    	ServerStart.closeServer();
    	connect_status.setText(BiteMeServer.disconnectDB());
    }

}
