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

public class ApproveAcountController {

    @FXML
    private Accordion user_accordion;
    
    @FXML
    private Label msglbl;
    
    public void initialize() {
    	StartClient.order.accept("Load_business_account");
    	
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
    		button.setOnAction((new EventHandler<ActionEvent>() {
    			@Override
    		public void handle(ActionEvent e) {
    			StartClient.order.accept("Approve_account~"+account.getAccountNum());
    			button.setVisible(false);
    					
    				}
    			}));
    			vbox.getChildren().add(button);
    			TitledPane pane1 = new TitledPane("ID #"+""+account.getID(),vbox);
    			pane1.getStyleClass().add("titled-pane");
    			user_accordion.getPanes().add(pane1);

    	}
    		
    		
    	}
 
 
    }

