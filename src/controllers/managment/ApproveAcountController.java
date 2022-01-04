package managment;

import clients.OrderClient;
import clients.StartClient;
import entity.BusinessAccount;
import entity.Order;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

/**
 * This class is used to Approve accounts by BM manager 
 * The controller loads all business account sto approve 
 * if theres users to approve he adds them to an accordion 
 * once a user has been approved he is removed from that accordion
 * if there are no users to approve the message no business accounts to approve will be displayed
 *
 * @author      daniel aibinder
 * @version     1.0               
 * @since       01.01.2022        
 */


public class ApproveAcountController {

	/**user_accordion =accordion where we set titled panes to display accounts to approve */
    @FXML
    private Accordion user_accordion;
    /**msglbl = lbl to set if theres no users to approve*/
    @FXML
    private Label msglbl;
    
    /**
     * this fucn initializes our our class by querying business accounts to approve from server which pulls from db
     * for each user we receive we set a title pane to hold the account along with a button to approve the account*/
    public void initialize() {
    	StartClient.order.accept("Load_business_account~"+OrderClient.user.getID());
    	
    	if(OrderClient.usersToApprove.size()==0)
    	{
    		msglbl.setText("No Business accounts to approve");
    	}
    	    	
    	for(BusinessAccount account:OrderClient.usersToApprove)
    	{
    		VBox vbox = new VBox();
    		vbox.getChildren().add(new Label("Name: "+account.getFirstName()+" "+account.getLastName()));
    		vbox.getChildren().add(new Label("Employer: "+account.getEmployerName()));
    		Button button = new Button("Approve");
    		
    		vbox.getChildren().add(button);
			TitledPane pane1 = new TitledPane("ID #"+""+account.getID(),vbox);
			pane1.getStyleClass().add("titled-pane");
			user_accordion.getPanes().add(pane1);
    		button.setOnAction((new EventHandler<ActionEvent>() {
    			@Override
    		public void handle(ActionEvent e) {
    			StartClient.order.accept("Approve_account~"+account.getAccountNum());
    			button.setVisible(false);
    			user_accordion.getPanes().remove(pane1);		
    				}
    			}));
    			

    	}
    		
    		
    	}
 
 
    }

