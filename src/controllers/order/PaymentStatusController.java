package order;

import general.MyListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * This class showing user is payment to approve
 * user can approve payment thru listener
 * 
 * 
 * @author      Matan Weisberg
 * @version     1.0               
 * @since       01.01.2022        
 */
public class PaymentStatusController {

	/**
	 * display message with payment to approve*/
    @FXML
    private Label status_txt;

    /**
     * button for confirm payment*/
    @FXML
    private Button confirm_btn;

    /**
     *  button for cancel payment*/
    @FXML
    private Button cancel_btn;
    
    /**listener used to update the approve status in PaymentController*/
    private MyListener approveListener;

    /**
     *This func sets the approve flag to false and close window
     *@param event - action event for pressing cancel button
     **/
    @FXML
    void cancel(ActionEvent event) {
    	approveListener.onClickListener(false);
    	((Node) event.getSource()).getScene().getWindow().hide();
    }
    /**
     *This func sets the approve flag to true and close window
     *@param event - action event for pressing confirm button
     **/
    @FXML
    void confirm(ActionEvent event) {
    	approveListener.onClickListener(true);
    	((Node) event.getSource()).getScene().getWindow().hide();
    }
    
    /**
     *This func sets message and saves the approve listener
     *@param str the message for user
     *@param approveListener the listener for getting confirmation status
     **/
    public void setData(String str,MyListener approveListener)
    {
    	this.approveListener=approveListener;
    	status_txt.setText(str);
    }

}
