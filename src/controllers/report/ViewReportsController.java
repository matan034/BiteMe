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


/**
 * Controller for sending a user to the report that that he wishes, User can choose which branch and which month he wants to see along with if he wants to view a report
 * of amount of orders by components or a report for the branches performance
 * 
 * @version     1.0               
 * @since       01.01.2022  */



public class ViewReportsController {
	/**
	 * branchList list of branches*/
	ObservableList<String> branchList = FXCollections.observableArrayList("North", "Center", "South");
	/**
	 * monthList list of months*/
	ObservableList<String> monthList = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
	/**
	 * typeList list of types or available orders*/
	ObservableList<String> typeList = FXCollections.observableArrayList("Order report by components", "Performance report",
			"Income Report by restaurants");

	/**
	 * branchName name of branch*/
	public static String branchName;
	/**
	 *  MonthNumber month selected*/
	public static String monthNumber;
	/**
	 * YearNumber year selected*/
	public static String YearNumber;
	
	/**
	 * banchCombox combo box for selecting a branch*/
	@FXML
	private ComboBox<String> branchCombox = new ComboBox<String>();

	/**
	 * monthCombobox combo box for selecting a month*/
	@FXML
	private ComboBox<String> monthCombox;

	/**
	 * typeCombox combo box for selecting a type*/
	@FXML
	private ComboBox<String> typeCombox;

	/**
	 * yearCombobox combo box for selecting a year*/
	@FXML
	private ComboBox<String> YearComboBox;
	/**
	 * viewBtn button for entering a report*/
	@FXML
	private Button viewBtn;
	
	/**
	 * backButton button to go back a screen*/
	 @FXML
	    private Button backButton;
	 
	 /**
	  * initialzes our comboboxes with our lists and sets listeners for comboboxes to validate input so that we make sure all combo boxes have been selected*/
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
		
		/**
		 * function to set listeners we call this on comboboxes to set an event listener that validates input whenever a change has been detected
		 * @return ChangeListener*/
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
	  
	    /**
	     * function to validate input we count if all combo boxes have been selected if they have been we enable the view buttn to go to the chosen report*/
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
	 
	 /**
	  * function to go to report chosen activates on view button press, 
	  * we set branch name , year number , month number in class static variable so the reports can access it 
	  * and we open the report matching all the combo boxes choices 
	  * @param event Actionevent for event details*/
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


	 /**
	  * activates on pressing the back button sends us back to ReportsScreen.fxml
	  * @param event actionevent for event details*/
	   @FXML
	    void goToReportScreen(ActionEvent event) {
		   Globals.loadInsideFXML(Globals.reports_screenFXML);
	    }

}
