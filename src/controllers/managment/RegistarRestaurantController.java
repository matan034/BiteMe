package managment;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * Class for registering a supplier, BM registers the restaurant by inputting it's address,city and type 
 * You can only register a supplier that is in the users table from import users by outer function
 * @param restarant_cmb combo box to choose available  suppliers to register
 * @param address_lbl textfield for inputting address
 * @param city_lbl textfiled for inputting city
 * @param type_cmb combobox for choosing restaurants type
 * @param approve_cmb button for saving inputted data
 * @param cnt used to disable button if not all the information that is not optional has been inputted, enable the button if all iput is valid
 * @author      daniel aibinder
 * @version     1.0               
 * @since       01.01.2022        
 */

public class RegistarRestaurantController {

    @FXML
    private ComboBox<String> restaurant_cmb;

    @FXML
    private TextField address_lbl;

    @FXML
    private TextField city_lbl;


    @FXML
    private ComboBox<String> type_cmb;

    @FXML
    private Button approve_cmb;
    
    private int cnt=0;
    
    /**
     * this func initalizes our controller by first getting all restaurants in the users branch
     * it also sets events listeners to count that all mandatory fields have been filled by the user
     * once they are all filled the apprve button is enabled */
    public void initialize() {
    	StartClient.order.accept("Get_restaurants~"+OrderClient.user.getHomeBranch());
    	approve_cmb.setDisable(true);
    	type_cmb.getSelectionModel().selectedItemProperty().addListener((obs,oldValue, newValue)-> {
    	    if(newValue!=null)
    	    {
    	    	cnt++;
    	    	if(cnt>=2) approve_cmb.setDisable(false);
    	    }
    	});
    	restaurant_cmb.getSelectionModel().selectedItemProperty().addListener((obs,oldValue, newValue)-> {
    	    if(newValue!=null)
    	    {
    	    	cnt++;
    	    	if(cnt>=2) approve_cmb.setDisable(false);
    	    }
    	});
    	if(OrderClient.myRestaurants.size()!=0)
    		restaurant_cmb.setItems(OrderClient.myRestaurants);
    	else
    		restaurant_cmb.setItems(FXCollections.observableArrayList("No Restaurants"));
		type_cmb.setItems(FXCollections.observableArrayList("American","Italian","Mexican","Israeli","Meat","Vegan"));
    }
    
    /**
     * this func is for approving restaurant button event, we send to server the user input and register the restuarant
     * @param event =ActionEvent javafx class for event information*/
    @FXML
    void approve_restaurant(ActionEvent event) {
    	
    	String res=OrderClient.user.getHomeBranch()+"~"+restaurant_cmb.getSelectionModel().getSelectedItem()+"~"+address_lbl.getText()+"~"+city_lbl.getText()+"~"+type_cmb.getSelectionModel().getSelectedItem();
    	StartClient.order.accept("Approve_restaurant~"+res);
    	Globals.loadInsideFXML(Globals.homeScreen);
    }

}
