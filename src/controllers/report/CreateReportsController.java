package report;

import common.Globals;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class CreateReportsController {

	ObservableList<String> branchList = FXCollections.observableArrayList("North", "Center", "South");
	ObservableList<String> quarterList = FXCollections.observableArrayList("1-3", "4-6", "7-9", "10-12");
	ObservableList<String> typeList = FXCollections.observableArrayList("Quarterly order report",
			"Quarterly income report", "Quarterly delays supply report");

	public static String branch;
	public static String quarter;

	@FXML
	private Button backButton;

	@FXML
	private Button create;

	@FXML
	private ComboBox<String> branchCombox;

	@FXML
	private ComboBox<String> quarterCombox;

	@FXML
	private ComboBox<String> typeCombox;

	@FXML
	void goToSpecificQuarterReport(ActionEvent event) {
		branch = branchCombox.getSelectionModel().getSelectedItem();
		quarter = quarterCombox.getSelectionModel().getSelectedItem();

		// if (typeCombox.getSelectionModel().getSelectedItem().equals("Quarterly order
		// report"))
		// Globals.loadFXML(null, Globals., null);

		 if (typeCombox.getSelectionModel().getSelectedItem().equals("Quarterly income report"))
		 Globals.loadInsideFXML( Globals.quarter_income_reportFXML);

		if (typeCombox.getSelectionModel().getSelectedItem().equals("Quarterly delays supply report"))
			Globals.loadInsideFXML( Globals.quarter_delay_reportFXML);
	}

	@FXML
	void backToReportScreen(ActionEvent event) {
		Globals.loadInsideFXML( Globals.reports_screenFXML);
	}

	@FXML
	private void initialize() {
		branchCombox.setItems(branchList);
		quarterCombox.setItems(quarterList);
		typeCombox.setItems(typeList);

	}

}
