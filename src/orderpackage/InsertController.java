package orderpackage;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InsertController {

	  @FXML
	    private Pane insert_pane;

	    @FXML
	    private VBox Vbox;

	    @FXML
	    private TextField rest_info;

	    @FXML
	    private TextField phone_info;

	    @FXML
	    private TextField address_info;

	    @FXML
	    private Button insert_btn;

	    @FXML
	    private ComboBox<String> type_order_info;
	    @FXML
	    private Button back_btn;

	    InsertOrderClient insert_client;
	    @FXML
	    public void initialize() {
	    //not working!!!!! ad matai	
	    	type_order_info = new ComboBox<String>();
	    	type_order_info.setItems(FXCollections.observableArrayList("Order-In","Take-Away","Delievery"));
	    }
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
    
    @FXML
    void insertOrder(ActionEvent event) {
    	String str="Insert_order "+rest_info.getText()+" "+type_order_info.getPromptText()+" "+phone_info.getText()+" "+address_info.getText();
    	IndexOrderUI.insert.accept(str);
    	
    	
    }

}
