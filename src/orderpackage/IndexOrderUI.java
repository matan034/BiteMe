package orderpackage;


	import java.io.IOException;
	import javafx.application.Application;
	import javafx.fxml.FXMLLoader;
	import javafx.scene.Scene;
import javafx.scene.layout.Pane;
	import javafx.stage.Stage;

	public class IndexOrderUI extends Application {
		//public static OrderClientController order; //only one instance

	
		
		public static void main(String[] args) {
				launch(args);
		}
				
			@Override
			public void start(Stage stage) {
				Pane pane;
				//order= new OrderClientController(LoginController.ip, 5555);

				try {
				    FXMLLoader loader = new FXMLLoader();
				    loader.setLocation(getClass().getResource("/gui/IndexScreen.fxml"));
				    pane = loader.load();
					Scene scene=new Scene(pane);
					scene.getStylesheets().add(getClass().getResource("/gui/GeneralStyleSheet.css").toExternalForm());
					scene.getStylesheets().add(getClass().getResource("/gui/IndexScreenCSS.css").toExternalForm());
					stage.setTitle("Order");
					stage.setScene(scene);		
					stage.show();
				} catch (IOException e) {
				    e.printStackTrace();
				    return;
				}
				
	
			}
		
}
