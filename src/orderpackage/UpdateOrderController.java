package orderpackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UpdateOrderController {

    @FXML
    private TableView<?> OrderTable;
    @FXML
    private Button back_btn;

    @FXML
    void back_to_index(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader();
    	try {
    	((Node) event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		Pane root ;
		loader.setLocation(getClass().getResource("/gui/IndexScreen.fxml"));
	    root = loader.load();
		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/gui/IndexScreenCSS.css").toExternalForm());
		primaryStage.setTitle("Order");
		primaryStage.setScene(scene);		
		primaryStage.show();
    	}
    	catch (Exception e) {System.out.println("insert load exception");}
    }
}
