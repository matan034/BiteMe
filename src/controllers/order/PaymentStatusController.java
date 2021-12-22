package order;

import general.MyListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class PaymentStatusController {

    @FXML
    private Label status_txt;

    @FXML
    private Button confirm_btn;

    @FXML
    private Button cancel_btn;
    
    private MyListener approveListener;

    @FXML
    void cancel(ActionEvent event) {
    	approveListener.onClickListener(false);
    	((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    void confirm(ActionEvent event) {
    	approveListener.onClickListener(true);
    	((Node) event.getSource()).getScene().getWindow().hide();
    }
    
    public void setData(String str,MyListener approveListener)
    {
    	this.approveListener=approveListener;
    	status_txt.setText(str);
    }

}
