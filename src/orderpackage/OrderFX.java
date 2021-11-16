package orderpackage;


	import java.io.IOException;
	import javafx.application.Application;
	import javafx.fxml.FXMLLoader;
	import javafx.scene.Scene;
	import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
	import javafx.stage.Stage;

	public class OrderFX extends Application {
			
		public static void main(String[] args) {
				launch(args);
		}
				
			@Override
			public void start(Stage stage) {
				Pane pane;
				try {
				    FXMLLoader loader = new FXMLLoader();
				    loader.setLocation(getClass().getResource("serverFXML.fxml"));
				    pane = loader.load();
				} catch (IOException e) {
				    e.printStackTrace();
				    return;
				}
				
				Scene scene=new Scene(pane);
			
				stage.setTitle("Server");
				stage.setScene(scene);		
				stage.show();	
			}
		
}
