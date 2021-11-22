package orderpackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import server.ServerStart;
import server.serverController;

public class IndexController
{

    @FXML
    private Pane index_pane;

    @FXML
    private Button insert_btn;

    @FXML
    private Button update_btn;

    @FXML
    private Button search_btn;

    @FXML
    private Label title_lbl;

    @FXML
    private Label status_label;
    
    
    @FXML
    private Button Exit_btn;
    
    public static ObservableList<String> delivery_options;
    @FXML
    public void initialize() {
       IndexOrderUI.index.accept("Get_connection");
       status_label.setText(IndexOrderUI.index.index_client.connection_info);
       delivery_options=FXCollections.observableArrayList("Take-Away","Order-In","Delivery");
    }
    
   
    @FXML
    void exit_program(ActionEvent event) {
    	Stage stage = (Stage) Exit_btn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void insertOrder(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader();
    	try {
    	((Node) event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		Pane root ;
		loader.setLocation(getClass().getResource("/gui/InsertScreen.fxml"));
	    root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/GeneralStyleSheet.css").toExternalForm());
		scene.getStylesheets().add(getClass().getResource("/gui/InsertCSS.css").toExternalForm());
		primaryStage.setTitle("Insert Order");
		primaryStage.setScene(scene);		
		primaryStage.show();
    	}
    	catch (Exception e) {System.out.println("insert load exception");}
    }

    @FXML
    void searchOrder(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader();
    	try {
    	((Node) event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/gui/SearchScreen.fxml").openStream());
	
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/GeneralStyleSheet.css").toExternalForm());
		scene.getStylesheets().add(getClass().getResource("/gui/SearchScreenCSS.css").toExternalForm());
		primaryStage.setTitle("Search Order");

		primaryStage.setScene(scene);		
		primaryStage.show();
    	}
    	catch (Exception e) {System.out.println(e+"search load exception");}
    }

    @FXML
    void updateOrder(ActionEvent event) {
    	
    	try {
    	((Node) event.getSource()).getScene().getWindow().hide(); //hiding primary window
        UpdateOrderController aFrame = new UpdateOrderController(); // create 
    	aFrame.start();

    	}
    	catch (Exception e) {System.out.println("update load exception");}
    }

}
