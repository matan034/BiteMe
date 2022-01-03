package managment;

import clients.StartClient;
import entity.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SMSSimulationController {

    @FXML
    private Label deliver_msg;
    
    
    public void setMessage(String str)
    {
    	deliver_msg.setText(str);
    	
    }

}
