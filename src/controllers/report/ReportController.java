package report;

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
    void goToCreateReports(ActionEvent event) {
    	Globals.loadInsideFXML( Globals.create_reportsFXML);
    }

    @FXML
    void goToViewReports(ActionEvent event) {
    	Globals.loadInsideFXML(Globals.view_reportsFXML);
    }

}
