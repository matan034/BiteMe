package controllers;

import common.Globals;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PerformanceReportController {

    @FXML
    private TableView<?> orderIDcol;

    @FXML
    private TableColumn<?, ?> expectedCol;

    @FXML
    private TableColumn<?, ?> arrivalCol;

    @FXML
    private Button backButton;

    @FXML
    private Label percentageOfWellServed;

    @FXML
    private Label percentageOfNotServedWell;

    @FXML
    private Label whichBranch;

    @FXML
    private Label whichMonth;

    @FXML
    void goBack(ActionEvent event) {
    	Globals.loadInsideFXML(Globals.view_reportsFXML);
    }
    
    public void initialize() {
    	whichBranch.setText(ViewReportsController.branchName);
    	whichMonth.setText(ViewReportsController.monthNumber);

}
}
