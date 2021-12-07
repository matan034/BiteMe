package controllers;
import clients.OrderClient;
import common.Globals;
import entity.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
	    	Image takeaway=new Image("takeaway.png");
	    	take_away_btn.setPrefSize(100,100);	
	    	take_away_btn.setContentDisplay(ContentDisplay.CENTER);
	    	take_away_btn.setMaxHeight(Double.MAX_VALUE);
	    	take_away_btn.setMaxWidth(Double.MAX_VALUE);
	    	delivery_btn.setPrefSize(100,100);	
	    	delivery_btn.setContentDisplay(ContentDisplay.CENTER);
	    	delivery_btn.setMaxHeight(Double.MAX_VALUE);
	    	delivery_btn.setMaxWidth(Double.MAX_VALUE);
			take_away_btn.setBackground(Globals.bTakeaway);
	    	delivery_btn.setBackground(Globals.bDelivery);
	    }
	    @FXML
	    void backFunc(ActionEvent event) {
	    	Globals.loadFXML(null, Globals.indexFXML, event);
	    }

	    @FXML
	    void delivery(ActionEvent event) {
	    	Globals.newOrder=new Order("Delivery");
	    	Globals.loadFXML(null, Globals.ChooseBranchFXML, event);
	    }

	    @FXML
	    void takeAway(ActionEvent event) {
	    	Globals.newOrder=new Order("Take-Away");
	    	Globals.loadFXML(null, Globals.ChooseBranchFXML, event);

	    }
	    
}
