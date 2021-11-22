package clients;

import java.io.IOException;

import controllers.LoginController;
import controllers.OrderClientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginUI extends Application{
	
	public static OrderClientController order; //only one instance
	
	public static void main(String[] args) {
		launch(args);
	}
	public void start(Stage stage) {
		Pane pane;
		order= new OrderClientController(LoginController.ip, 5555);
		try {
		    FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(getClass().getResource("/ui/LoginScreen.fxml"));
		    pane = loader.load();
			Scene scene=new Scene(pane);
			stage.setTitle("Login");
			stage.setScene(scene);		
			stage.show();
		} catch (IOException e) {
		    e.printStackTrace();
		    return;
		}
	}
}
