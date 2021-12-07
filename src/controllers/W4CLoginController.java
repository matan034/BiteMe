package controllers;
import clients.StartClient;
import clients.OrderClient;
import common.Globals;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class W4CLoginController {

    @FXML
    private Button qr_simulation_btn;

    @FXML
    private TextField qr_alternative_input;

    @FXML
    private Button login_btn;

    @FXML
    void verifyW4C(ActionEvent event) {
    	String msg="W4C_verify~"+qr_alternative_input.getText();
    	StartClient.order.accept(msg);
    	
    	if(OrderClient.account.getW4cNum()==Integer.parseInt(qr_alternative_input.getText()))
    	{
    		msg="Load_customer~"+OrderClient.account.getAccountNum();
    		StartClient.order.accept(msg);
    		Globals.loadFXML(null, Globals.supply_wayFXML, event);
    	}
    }

}