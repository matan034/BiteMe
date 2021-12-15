package controllers;

import common.Globals;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PurchaseReportController {

	@FXML
	private Label whichBranch;

	@FXML
	private Label whichMonth;

	@FXML
	private TableView<?> purchaseTable;

	@FXML
	private TableColumn<?, ?> restaurantCol;

	@FXML
	private TableColumn<?, ?> sumOfPurchasesCol;

	@FXML
	private Button backBtn;

	@FXML
	void goBack(ActionEvent event) {
		Globals.loadInsideFXML( Globals.view_reportsFXML);
	}

	public void initialize() {
		whichBranch.setText(ViewReportsController.branchName);
		whichMonth.setText(ViewReportsController.monthNumber);
	}

}
