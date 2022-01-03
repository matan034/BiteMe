package report;

import clients.OrderClient;
import common.Globals;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
	public static String YearNumber;

	@FXML
	private ComboBox<String> branchCombox = new ComboBox<String>();

	@FXML
	private ComboBox<String> monthCombox;

	@FXML
	private ComboBox<String> typeCombox;

	@FXML
	private ComboBox<String> YearComboBox;
	@FXML
	private Button viewBtn;
	
	 @FXML
	    private Button backButton;
	 
		@FXML
		private void initialize() {
			if(OrderClient.user.getType().equals("Branch Manager"))
				branchCombox.setVisible(false);
			branchCombox.setItems(branchList);
			monthCombox.setItems(monthList);
			typeCombox.setItems(typeList);
			YearComboBox.setItems(Globals.years);

			viewBtn.setDisable(true);
			YearComboBox.getSelectionModel().selectedItemProperty().addListener(defineListener());
			typeCombox.getSelectionModel().selectedItemProperty().addListener(defineListener());
			monthCombox.getSelectionModel().selectedItemProperty().addListener(defineListener());
			branchCombox.getSelectionModel().selectedItemProperty().addListener(defineListener());
		}
	    private ChangeListener<String> defineListener()
	    {
	    	return new ChangeListener() {   	 
				@Override
				public void changed(ObservableValue arg0, Object arg1, Object arg2) {
					 if(arg2!=null)
	    	    	    {
	    	    	    	validate_input();
	    	    	    }
				}
	    	    };  
	    }
	    
	 private void validate_input()
	 {
		 int i=0;
		 
		 if(YearComboBox.getSelectionModel().getSelectedItem()!=null) i++;
		 if(monthCombox.getSelectionModel().getSelectedItem()!=null) i++;
		 if(typeCombox.getSelectionModel().getSelectedItem()!=null) i++;
		 if(branchCombox.getSelectionModel().getSelectedItem()!=null) i++;
		  
		 if(OrderClient.user.getType().equals("Branch Manager"))
		 {
			 if(i==3) viewBtn.setDisable(false);
		 }
		 else if(i==4) viewBtn.setDisable(false);
	 }
	@FXML
	void goToRep(ActionEvent event) {
		if(OrderClient.user.getType().equals("CEO")) {
			branchName = branchCombox.getSelectionModel().getSelectedItem();
		}
		YearNumber= YearComboBox.getSelectionModel().getSelectedItem();
		monthNumber = monthCombox.getSelectionModel().getSelectedItem();
		

		if (typeCombox.getSelectionModel().getSelectedItem().equals("Order report by components"))
			Globals.loadInsideFXML( Globals.order_components_rating_reportFXML);

		if (typeCombox.getSelectionModel().getSelectedItem().equals("Performance report"))
			Globals.loadInsideFXML(Globals.performance_reportFXML);
		
		if (typeCombox.getSelectionModel().getSelectedItem().equals("Income Report by restaurants"))
			Globals.loadInsideFXML(Globals.income_reportFXML);

	}


	
	   @FXML
	    void goToReportScreen(ActionEvent event) {
		   Globals.loadInsideFXML(Globals.reports_screenFXML);
	    }

}
