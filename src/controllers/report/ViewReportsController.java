package report;

import common.Globals;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class ViewReportsController {
	ObservableList<String> branchList = FXCollections.observableArrayList("North", "Center", "South");
	ObservableList<String> monthList = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
	ObservableList<String> typeList = FXCollections.observableArrayList("Order report by components", "Performance report",
			"Income Report by restaurants");

	public static String branchName;
	public static String monthNumber;

	@FXML
	private ComboBox<String> branchCombox = new ComboBox<String>();

	@FXML
	private ComboBox<String> monthCombox;

	@FXML
	private ComboBox<String> typeCombox;

	@FXML
	private Button viewBtn;
	
	 @FXML
	    private Button backButton;

	    
	@FXML
	void goToRep(ActionEvent event) {
		branchName = branchCombox.getSelectionModel().getSelectedItem();
		monthNumber = monthCombox.getSelectionModel().getSelectedItem();

		if (typeCombox.getSelectionModel().getSelectedItem().equals("Order report by components"))
			Globals.loadInsideFXML( Globals.order_components_rating_reportFXML);

		if (typeCombox.getSelectionModel().getSelectedItem().equals("Performance report"))
			Globals.loadInsideFXML(Globals.performance_reportFXML);
		
		if (typeCombox.getSelectionModel().getSelectedItem().equals("Income Report by restaurants"))
			Globals.loadInsideFXML(Globals.income_reportFXML);

	}

	@FXML
	private void initialize() {
		branchCombox.setItems(branchList);
		monthCombox.setItems(monthList);
		typeCombox.setItems(typeList);

	}
	
	   @FXML
	    void goToReportScreen(ActionEvent event) {
		   Globals.loadInsideFXML(Globals.reports_screenFXML);
	    }

}
