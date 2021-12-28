package report;

import clients.OrderClient;
import common.Globals;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ReportController {

    @FXML
    private Button viewReportButton;

    @FXML
    private Button createReportButton;
    
    @FXML
    private Button viewQuarterIncome;

    public void initialize()
    {
    	if(!OrderClient.user.getType().equals("CEO"))
    	{
    		viewQuarterIncome.setVisible(false);
    	}
    }
    
    @FXML
    void goToChooseQuarterAndBranch(ActionEvent event) {
    	Globals.loadInsideFXML( Globals.quarterlyHistogram);
    }

    @FXML
    void goToCreateReports(ActionEvent event) {
    	Globals.loadInsideFXML( Globals.create_reportsFXML);
    }

    @FXML
    void goToViewReports(ActionEvent event) {
    	Globals.loadInsideFXML(Globals.view_reportsFXML);
    }

}
