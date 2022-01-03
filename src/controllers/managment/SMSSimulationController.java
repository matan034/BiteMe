package managment;

import clients.StartClient;
import entity.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Class for simulating sms We simply set the label in our SMS screen to be the message we send 
 * from other conotrollers 
 * @param deliver msg= label that displays message*/
public class SMSSimulationController {

    @FXML
    private Label deliver_msg;
    
    /**
     * chane deliver_msg content to display the messsage a client has send
     * @param str = text to display*/
    public void setMessage(String str)
    {
    	deliver_msg.setText(str);
    	
    }

}
