package report;

	import common.Globals;
	import javafx.collections.FXCollections;
	import javafx.collections.ObservableList;
	import javafx.event.ActionEvent;
	import javafx.fxml.FXML;
	import javafx.scene.control.Button;
	import javafx.scene.control.ComboBox;

	public class CEOchooseBranchAndQuarterController {
		ObservableList<String> branchList = FXCollections.observableArrayList("North", "Center", "South");
		ObservableList<String> quarterList = FXCollections.observableArrayList("1-3", "4-6", "7-9", "10-12");
		
		public static String branch1;
		public static String quarter1;
		public static String branch2;
		public static String quarter2;

	    @FXML
	    private ComboBox<String> firstBranchCombox;

	    @FXML
	    private ComboBox<String> quarterOfFirstCombox;

	    @FXML
	    private ComboBox<String> secondBranchCombox;

	    @FXML
	    private ComboBox<String> quarterForSecondCombox;

	    @FXML
	    private Button viewBtn;

	    @FXML
	    private Button backBtn;
	    @FXML
		private void initialize() {
	    	firstBranchCombox.setItems(branchList);
	    	quarterOfFirstCombox.setItems(quarterList);
	    	secondBranchCombox.setItems(branchList);
	    	quarterForSecondCombox.setItems(quarterList);
	    }
	    

	    @FXML
	    void goBackToReportScreen(ActionEvent event) {
	    	Globals.loadInsideFXML( Globals.reports_screenFXML);
	    }

	    @FXML
	    void viewQuarterIncomeReport(ActionEvent event) {
	    	 
	    		 Globals.loadInsideFXML( Globals.ceo_view_the_report_she_chose);

	    		

	    }

	}


