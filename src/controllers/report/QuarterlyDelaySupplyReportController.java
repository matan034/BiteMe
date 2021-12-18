package report;

import common.Globals;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;

public class QuarterlyDelaySupplyReportController {

	@FXML
	private Label whichBranch;

	@FXML
	private Label whichQuarter;

	@FXML
	private TableColumn<?, ?> restaurantNameCol;

	@FXML
	private TableColumn<?, ?> amountOdDelayCol;

	@FXML
	private Button backBtn;

	@FXML
	void goBack(ActionEvent event) {
		Globals.loadInsideFXML( Globals.create_reportsFXML);
	}

	public void initialize() {
		whichBranch.setText(CreateReportsController.branch);
		whichQuarter.setText(CreateReportsController.quarter);

	}

}
