package order;


import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import general.MyListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 * This class is for showing a simulation for QR W4C code
 * loads the customer w4c card from DB
 * 
 * 
 * 
 * @author      Matan Weisberg
 * @version     1.0               
 * @since       01.01.2022        
 */
public class QRSimulationController {

	/**
	 * w4c card from DB*/
    @FXML
    private ComboBox<String> w4c_cards;

    /**
     *  button for insert the w4c number thru listener to W4CLoginController*/
    @FXML
    private Button insert_btn;
    /**
     *  saves the listener used to get the card number*/
    private MyListener listener;
    
    /**
     *This func initiailize our controller 
     *loads from DB the W4C card for the customer
     *
     **/
    public void initialize()
    {
    	String msg="W4C_load_list~";
    	if(OrderClient.customer.getpAccount()!=0) msg+="PrivateAccount~"+OrderClient.customer.getpAccount();
    	else msg+="BusinessAccount~"+OrderClient.customer.getbAccount();
    	StartClient.order.accept(msg);
    	w4c_cards.setItems(OrderClient.w4cList);
    	insert_btn.setDisable(true);
    	w4c_cards.getSelectionModel().selectedItemProperty().addListener((obs,oldValue, newValue)-> {
    	    if(newValue!=null)
    	    {
    	    	insert_btn.setDisable(false);
    	    }
    	});
    }
    
    /**
     *This func is for set the listener
     *@param l - the listener for getting the card number
     **/
    public void setListener(MyListener l)
    {
    	this.listener=l;
    }
    /**
     *This func uses the listener to get the card number to W4CController
     *and close window
     *@param event - action event for pressing insert button
     **/
    @FXML
    void insert(ActionEvent event) {
    	listener.onClickListener(w4c_cards.getSelectionModel().getSelectedItem());
    	((Node) event.getSource()).getScene().getWindow().hide();
    }

}
