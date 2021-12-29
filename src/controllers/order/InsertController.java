/**
*This class is our controller class for insert it handles UI requests and DB returns
*@param insert_pane main pane for our screen
*@param vbox vbox where grid is set
*@param rest_info textfield for resturant
*@param phone_info textfield for phone num
*@param address_info text field for address
*@param insert_btn btn used to insert our new order
*@param type_order_info combo box options 
*@param back_btn btn used to go back to index
*@param insert_result lbl to display result from insert success or fail
*/


package order;

import javax.swing.event.ChangeListener;

import clients.StartClient;
import clients.OrderClient;
import common.Globals;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
	    @FXML
	    private Label insert_result;
	   private final int max_line_size = 20;
	   /**
	   *on InsertController creation create event listeners to count if all input fields have been inserted, if yes we unlock insert button if no insert button remains locked*/
	    @FXML
	    public void initialize() {
	    	//type_order_info.setItems(IndexController.delivery_options);
//	    	rest_info.textProperty().addListener((obs,oldValue, newValue)-> {
//	    	    if(newValue!=null)
//	    	    {
//	    	    	validate_insert();
//	    	    }
//	    	});
//	    	phone_info.textProperty().addListener((obs,oldValue, newValue)-> {
//	    	    if(newValue!=null)
//	    	    {
//	    	    	validate_insert();
//	    	    }
//	    	});
//	    	address_info.textProperty().addListener((obs,oldValue, newValue)-> {
//	    	    if(newValue!=null)
//	    	    {
//	    	    	validate_insert();
//	    	    }
//	    	});
//	    	type_order_info.getSelectionModel().selectedItemProperty().addListener((obs,oldValue, newValue)-> {
//	    	    if(newValue!=null)
//	    	    {
//	    	    	set_address_bar();
//	    	    	validate_insert();
//	    	    }
//	    	});
	    }
	    /**
	    *back_to_index func used to go back to index screen while hiding insertScreen
	    *@param event event for button press back
	    */
	    @FXML
	    void back_to_index(ActionEvent event) {

	    	//Globals.loadFXML(null, Globals.indexFXML, event);
	    }

	    void set_address_bar()
	    {
	    	if(type_order_info.getSelectionModel().selectedItemProperty().get()==null) {}
	    	else
	    	{
	    		if(!type_order_info.getSelectionModel().selectedItemProperty().get().equals("Delivery"))
				{
					address_info.clear();
					address_info.setDisable(true); 
				}
				else
				{
					address_info.setDisable(false);
				}
	    	}
	    }
	    	
	/**
	*validate_insert function used to check if user has inputted text if our cnt reaches 4(all details we need) we can enable the button, if not we disable the button
	*/
	    void validate_insert() {
	    	int cnt=0;
	    	if(type_order_info.getSelectionModel().selectedItemProperty().get()==null) {}
	    	else {
	    			if(!rest_info.getText().trim().isEmpty() && rest_info.getText().length()<max_line_size)
	    			{
	    				if(!phone_info.getText().trim().isEmpty()&& phone_info.getText().length()<max_line_size) 
	    				{
	    					if(!type_order_info.getSelectionModel().selectedItemProperty().get().equals("Delivery"))
	    					{

	    						insert_btn.setDisable(false); 
	    					}
	    					else
	    					{
	    						if(!address_info.getText().trim().isEmpty()&& address_info.getText().length()<max_line_size)
	    						{
	    							insert_btn.setDisable(false); 
	    						}
	    						else
	    						{
	    							insert_btn.setDisable(true); 
	    						}
	    					}
	    					
	    				}
	    				else {
	    					insert_btn.setDisable(true); 
	    				}
	    			}
	    			else {
	    				insert_btn.setDisable(true); 
	    			}
	    		}    	
	    }
	/**
	*insertOrder function used to send the insert info to server, server inserts order to DB if successfull returns success msg if not returns fail msg 
	*/
    @FXML
    void insertOrder(ActionEvent event) {
    	String suffix="-";
    	if(!address_info.getText().trim().isEmpty())
    	{
    		suffix=address_info.getText();
    	}
    		String str="Insert_order~"+rest_info.getText()+"~"+phone_info.getText()+"~"+type_order_info.getSelectionModel().selectedItemProperty().get()+"~"+suffix;
    		StartClient.order.accept(str);
    		insert_result.setText(OrderClient.insert_msg);
    }

}
