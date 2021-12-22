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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class W4CLoginController {

    @FXML
    private Button qr_simulation_btn;

    @FXML
    private TextField qr_alternative_input;

    @FXML
    private Button login_btn;
    private MyListener qrListener;
    public void initialize()
    {
    	 qrListener=new MyListener() {
			
			@Override
			public void onClickListener(Object qr) {
				// TODO Auto-generated method stub
				qr_alternative_input.setText((String)qr);
			}
		};
    }
    @FXML
    void verifyW4C(ActionEvent event) {
    	String msg="W4C_verify~"+qr_alternative_input.getText();
    	StartClient.order.accept(msg);
    	
    	if(OrderClient.w4c_card.getCardNum()==Integer.parseInt(qr_alternative_input.getText())) {
    	
    		Globals.loadInsideFXML( Globals.ChooseSupplierFXML);
    	}
    }
    public void setW4CInput(String w4c_code)
    {
    	qr_alternative_input.setText(w4c_code);
    }
    
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
        Scene dialogScene = new Scene(anchorPane, 300, 200);
        dialog.setScene(dialogScene);

        dialog.show();
        
    	
    	}
    	catch (Exception e) {
			// TODO: handle exception
		}

    	
    }

}