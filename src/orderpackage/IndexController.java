package orderpackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
    private Button Exit_btn;

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
		//InsertFormController insertFormController = loader.getController();		
		//insertFormController.loadStudent(ChatClient.s1);
	
		Scene scene = new Scene(root);			
		//scene.getStylesheets().add(getClass().getResource("/orderpackage/InsertCSS.css").toExternalForm());
		primaryStage.setTitle("Search Order");

		primaryStage.setScene(scene);		
		primaryStage.show();
    	}
    	catch (Exception e) {System.out.println("search load exception");}
    }

    @FXML
    void updateOrder(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader();
    	try {
    	((Node) event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/gui/UpdateScreen.fxml").openStream());
		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/gui/updateScreenCss.css").toExternalForm());
		primaryStage.setTitle("Update Order");

		primaryStage.setScene(scene);		
		primaryStage.show();
    	}
    	catch (Exception e) {System.out.println("update load exception");}
    }

}
