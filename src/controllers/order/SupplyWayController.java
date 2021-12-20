package order;
import clients.OrderClient;
import common.Globals;
import entity.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class SupplyWayController {
	


	    @FXML
	    private Pane insert_pane;

	    @FXML
	    private Label result;

	    @FXML
	    private GridPane form_grid;

	    @FXML
	    private Label insert_result;



	    @FXML
	    private Button back_btn;

	    @FXML
	    private Button take_away_btn;

	    @FXML
	    private Button delivery_btn;
	    @FXML
	    public void initialize() {
//	    	Image takeaway=new Image("takeaway.png");
//	    	take_away_btn.setPrefSize(100,100);	
//	    	take_away_btn.setContentDisplay(ContentDisplay.CENTER);
//	    	take_away_btn.setMaxHeight(Double.MAX_VALUE);
//	    	take_away_btn.setMaxWidth(Double.MAX_VALUE);
//	    	delivery_btn.setPrefSize(100,100);	
//	    	delivery_btn.setContentDisplay(ContentDisplay.CENTER);
//	    	delivery_btn.setMaxHeight(Double.MAX_VALUE);
//	    	delivery_btn.setMaxWidth(Double.MAX_VALUE);
//			take_away_btn.setBackground(Globals.bTakeaway);
//	    	delivery_btn.setBackground(Globals.bDelivery);
	    }
	    @FXML
	    void backFunc(ActionEvent event) {
	    	Globals.loadInsideFXML(Globals.W4CLoginFXML);
	    }

	    @FXML
	    void delivery(ActionEvent event) {
	    	Globals.newOrder=new Order("Delivery",OrderClient.w4c_card);
	    	Globals.loadInsideFXML(Globals.ChooseSupplierFXML);
	    }

	    @FXML
	    void takeAway(ActionEvent event) {
	    	Globals.newOrder=new Order("Take-Away",OrderClient.w4c_card);//sent cutomer before
	    	Globals.loadInsideFXML(Globals.ChooseSupplierFXML);

	    }
	    
}
