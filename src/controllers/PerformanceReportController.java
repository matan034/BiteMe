package controllers;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PerformanceReportController {

	@FXML
	private TableView<?> performanceTable;

	@FXML
	private TableColumn<?, ?> restaurantCol;

	@FXML
	private TableColumn<?, ?> servedWellCol;

	@FXML
	private TableColumn<?, ?> delaySupplyCol;

	@FXML
	private Button backButton;

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
		int BranchID = 0;
		if (ViewReportsController.branchName.equals("North"))
			BranchID = 1;
		else if (ViewReportsController.branchName.equals("Center"))
			BranchID = 2;
		else
			BranchID = 3;
		String str = "Load_monthly_performance~" + BranchID;
		StartClient.order.accept(str);
		display_table();

	}

	void display_table() {

//		restaurantName.setCellValueFactory(new PropertyValueFactory<String, String>("restaurant"));
//		startersColumn.setCellValueFactory(new PropertyValueFactory<String, String>("starters"));
//		mainsColumn.setCellValueFactory(new PropertyValueFactory<String, String>("mains"));
//		saladsColumn.setCellValueFactory(new PropertyValueFactory<String, String>("salads"));
//		dessertsColumn.setCellValueFactory(new PropertyValueFactory<String, String>("desserts"));
//		drinksColumn.setCellValueFactory(new PropertyValueFactory<String, String>("drinks"));
//
//		ratingTable.setItems(OrderClient.componentsOfDishes);
	}
}
