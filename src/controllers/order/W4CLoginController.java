package order;
import clients.StartClient;
import clients.OrderClient;
import common.Globals;
import general.MyListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class is for login with W4C card 
 * user can input card number manually or getting the number from QR

 * @param error_msg = lbl to show error message
 * @param qr_alternative_input = input code manually
 * @param login_btn = button for logging with w4c code
 * @param qrListener = sets the listener for getting card number back from QR
 * 
 * 
 * @author      Matan Weisberg
 * @version     1.0               
 * @since       01.01.2022        
 */
public class W4CLoginController {

    @FXML
    private Label error_msg;

    @FXML
    private TextField qr_alternative_input;

    @FXML
    private Button login_btn;
 
    
    private MyListener qrListener;
    
    /**
     *This func initializes our controller
     *set login button to disable and set a listener for textinput 
     *when user enters number button will be enable
     *
     **/
    public void initialize()
    {
    	
    	login_btn.setDisable(true);
    	 qrListener=new MyListener() {
			
			@Override
			public void onClickListener(Object qr) {
				
				qr_alternative_input.setText((String)qr);
			}
		};
		
		qr_alternative_input.textProperty().addListener((obs,oldValue, newValue)-> {
    	    if(!newValue.equals(""))
    	    {
    	    	login_btn.setDisable(false);
    	    	
    	    }
    	    else login_btn.setDisable(true);
    	    error_msg.setText("");
    	});
    }
    /**
     *This func verifies the enterd number with the DB
     *if The number is NOT correct a error message will be set
     *if card number is correct but user have a not approved business account and no private account an error message will present
     *@param event - action event for pressing login button
     **/
    @FXML
    void verifyW4C(ActionEvent event) {
    
    	String msg="W4C_verify~"+qr_alternative_input.getText()+"~"+OrderClient.customer.getpAccount()+"~"+OrderClient.customer.getbAccount();
    	StartClient.order.accept(msg);
    	if(OrderClient.w4c_card!=null)
    	{
    		if(OrderClient.customer.getpAccount()!=0)
        		StartClient.order.accept("Load private Account~"+OrderClient.customer.getpAccount());
        	if(OrderClient.customer.getbAccount()!=0) 
        		StartClient.order.accept("Load business Account~"+OrderClient.customer.getbAccount());
        	if(OrderClient.customer.getpAccount()==0 && OrderClient.baccount.getIsApproved()==0) 
        		error_msg.setText("Please arrange your payment method\nNo Private Account And Your Business Account Not Yet Approved");
        	else
        		Globals.loadInsideFXML( Globals.ChooseSupplierFXML);
    	}
    	else error_msg.setText(OrderClient.w4c_msg);
    }
    
    /**
     *This func is for setting the w4c input number from user
     *@param w4c_code - user W4c input
     **/
    public void setW4CInput(String w4c_code)
    {
    	qr_alternative_input.setText(w4c_code);
    }
    
    /**
     *This func is for loading pop up for the QR simulation
     *@param event - mouse event for pressing qr image
     **/
    @FXML
    void qrScanSimulation(MouseEvent event) {
    	try {

    	FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/order/QRSimulationScreen.fxml"));
        VBox anchorPane;
        anchorPane = fxmlLoader.load();
        QRSimulationController qrController = fxmlLoader.getController();
        qrController.setListener(qrListener);
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);    
        Scene dialogScene = new Scene(anchorPane);
        dialog.setScene(dialogScene);

        dialog.show();
        
    	
    	}
    	catch (Exception e) {
			// TODO: handle exception
		}

    	
    }

}