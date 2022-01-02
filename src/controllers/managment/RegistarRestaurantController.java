package managment;

import clients.OrderClient;
import clients.StartClient;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
    

    @FXML
    void approve_restaurant(ActionEvent event) {
    	
    	String res=OrderClient.user.getHomeBranch()+"~"+restaurant_cmb.getSelectionModel().getSelectedItem()+"~"+address_lbl.getText()+"~"+city_lbl.getText()+"~"+type_cmb.getSelectionModel().getSelectedItem();
    	StartClient.order.accept("Approve_restaurant~"+res);
    }

}
