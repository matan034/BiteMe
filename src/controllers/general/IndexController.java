package general;

import clients.StartClient;
import clients.OrderClient;
import common.Globals;
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
import utility.UserImportUtility;

public class IndexController
{
	  @FXML
	    private Button create_order_btn;

	    @FXML
	    private Tooltip insert_tooltip;

	    @FXML
	    private Button change_user_status_btn;

	    @FXML
	    private Tooltip update_tooltip;

	    @FXML
	    private Button register_new_employer_btn;

	    @FXML
	    private Tooltip search_tooltip;

	    @FXML
	    private Button login_btn;

	    @FXML
	    private Button register_new_account_btn;

	    @FXML
	    private Label title_lbl;

	    @FXML
	    private Label ip_label;

	    @FXML
	    private Label host_name;

	    @FXML
	    private Label connection_status;
	    
	    @FXML
	    private Button import_users_btn;

	    @FXML
	    private Button Exit_btn;
	    private String status;
	    public static ObservableList<String> delivery_options;
	   
	    /**
	    This func initalizes our index screen to display connection info we get from server aswell as initalize our combo box options*/
	   /* @FXML
	    public void initialize() {
	       StartClient.order.accept("Get_connection");
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
	    }*/
	    
	    @FXML
	    void importUsers(ActionEvent event) {
	    	UserImportUtility user_import_utility = new UserImportUtility();
	    	user_import_utility.ImportUsers();
	    }
	    
	    @FXML
	    void changeUserStatus(ActionEvent event) {
	    	Globals.loadFXML(null,Globals.changeuserstatusFXML, event);
	    }


		/**
		* this func enters the insert screen,displaying its UI and closes the index screen
		*@param event recognises insert button is pressed and prompts func to activate
		*/
	    @FXML
	    void createOrder(ActionEvent event) {
	    	Globals.loadFXML(null, Globals.W4CLoginFXML, event);
	    }

	    @FXML
	    void login(ActionEvent event) {
	    	Globals.loadFXML(null,Globals.userloginFXML, event);
	    }

	    @FXML
	    void registerNewAccount(ActionEvent event) {
	    	Globals.loadFXML(null,Globals.regnewaccountp1FXML, event);
	    }

	    @FXML
	    void registerNewEmployer(ActionEvent event) {
	    	Globals.loadFXML(null,Globals.regnewemployerFXML, event);
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




}
