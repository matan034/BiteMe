package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController {
	public static String ip;
    @FXML
    private TextField ip_address;

    @FXML
    private Button login_btn;

    @FXML
    void LoginToServer(ActionEvent event) {
    	ip=ip_address.getText();
    	
    	FXMLLoader loader = new FXMLLoader();
    	try {
    	((Node) event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		Pane root ;
		loader.setLocation(getClass().getResource("/ui/IndexScreen.fxml"));
	    root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/ui/GeneralStyleSheet.css").toExternalForm());
		//scene.getStylesheets().add(getClass().getResource("/gui/IndexScreen.css").toExternalForm());
		primaryStage.setTitle("Index");
		primaryStage.setScene(scene);		
		primaryStage.show();
    	}
    	catch (Exception e) {System.out.println("Index load exception");}
    	
    }
}
