package controllers;

import clients.LoginUI;
import clients.OrderClient;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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
    private Label ip_label;

    @FXML
    private Label host_name;

    @FXML
    private Label connection_status;
    @FXML
    private Tooltip insert_tooltip;

    @FXML
    private Tooltip update_tooltip;

    @FXML
    private Tooltip search_tooltip;
    
    @FXML
    private Button Exit_btn;
    
    private String status;
    public static ObservableList<String> delivery_options;
   
    /**
    This func initalizes our index screen to display connection info we get from server aswell as initalize our combo box options*/
    @FXML
    public void initialize() {
       LoginUI.order.accept("Get_connection");
       ip_label.setText(OrderClient.connection_info.get(0));
	   host_name.setText(OrderClient.connection_info.get(1));
	   connection_status.setText(OrderClient.connection_info.get(2));
       OrderClient.connection_info.addListener(new ListChangeListener<String>() {
    	 @Override
    	 public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {
    	 System.out.println("Changed on " + c);
    	
    	 if(c.next()){
    		 status = OrderClient.connection_info.get(c.getFrom());
    	 }
    	 Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				connection_status.setText(status);
			}	 
    	 });
    	 }
  });     
       delivery_options=FXCollections.observableArrayList("Take-Away","Order-In","Delivery");
    }

    
   /** 
   *this func exits our program, meaning we activate our quit function in client and close the window displaying UI
   * @param event Event recognises the exit button is pressed and prompts the func to activate
   */
    @FXML
    void exit_program(ActionEvent event) {
    	OrderClientController.order_client.quit();
    	Stage stage = (Stage) Exit_btn.getScene().getWindow();
        stage.close();
    }
	/**
	* this func enters the insert screen,displaying its UI and closes the index screen
	*@param event recognises insert button is pressed and prompts func to activate
	*/
    @FXML
    void insertOrder(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader();
    	try {
    	((Node) event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		Pane root ;
		loader.setLocation(getClass().getResource("/ui/InsertScreen.fxml"));
	    root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/ui/GeneralStyleSheet.css").toExternalForm());
		scene.getStylesheets().add(getClass().getResource("/ui/InsertCSS.css").toExternalForm());
		primaryStage.setTitle("Insert Order");
		primaryStage.setScene(scene);		
		primaryStage.show();
    	}
    	catch (Exception e) {System.out.println("insert load exception");}
    }
	/**
	* this func enters the search screen,displaying its UI and closes the index screen
	*@param event recognises search button is pressed and prompts func to activate
	*/
    @FXML
    void searchOrder(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader();
    	try {
    	((Node) event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		Pane root;
		loader.setLocation(getClass().getResource("/ui/SearchScreen.fxml"));
	    root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/ui/GeneralStyleSheet.css").toExternalForm());
		scene.getStylesheets().add(getClass().getResource("/ui/SearchScreenCSS.css").toExternalForm());
		primaryStage.setTitle("Search Order");

		primaryStage.setScene(scene);		
		primaryStage.show();
    	}
    	catch (Exception e) {System.out.println(e+"search load exception");}
    }
	/**
	* this func enters the update screen,displaying its UI and closes the index screen
	*@param event recognises update button is pressed and prompts func to activate
	*/
    @FXML
    void updateOrder(ActionEvent event) {
    	
    	try {
    	((Node) event.getSource()).getScene().getWindow().hide(); //hiding primary window
    	FXMLLoader loader = new FXMLLoader();
    	Stage primaryStage = new Stage();
    	Pane root;
    	loader.setLocation(getClass().getResource("/ui/UpdateScreen.fxml"));
	    root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/ui/GeneralStyleSheet.css").toExternalForm());
		scene.getStylesheets().add(getClass().getResource("/ui/updateScreenCss.css").toExternalForm());
		primaryStage.setTitle("Update Order");
		primaryStage.setScene(scene);
		primaryStage.show();	

    	}
    	catch (Exception e) {System.out.println("update load exception");}
    }

}
