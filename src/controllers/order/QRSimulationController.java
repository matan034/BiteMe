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

public class QRSimulationController {

    @FXML
    private ComboBox<String> w4c_cards;

    @FXML
    private Button insert_btn;
    private MyListener listener;
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
    public void setListener(MyListener l)
    {
    	this.listener=l;
    }
    @FXML
    void insert(ActionEvent event) {
    	listener.onClickListener(w4c_cards.getSelectionModel().getSelectedItem());
    	((Node) event.getSource()).getScene().getWindow().hide();
    }

}
