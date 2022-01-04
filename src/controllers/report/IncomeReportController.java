
package report;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import entity.TotalIncomesOfRestaurants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 * controller for IncomeReport.fxml here we see the restaurant in branch and it's total income for a specific month 
 * Uses a table that uses TotalIncomesOfRestaurants entity to display it's data
 * 
 * @param whichBranch label for showing what branch the restaurant is in
 * @param whichMonth label for showing what month the report is for
 * @param IncomeTable table that shows us our data
 * @param restaurantCol column in table for restaurant name
 * @param sumOfIncomeCol column in table for restaurants income
 * @param backbtn button to return to previous page
 *  @author      Yeela Malka
 * @version     1.0               
 * @since       01.01.2022  */



public class IncomeReportController {

	@FXML
	private Label whichBranch;

	@FXML
	private Label whichMonth;
	

	@FXML
	private TableView<TotalIncomesOfRestaurants> IncomeTable;

	@FXML
	private TableColumn<TotalIncomesOfRestaurants, String> restaurantCol;

	@FXML
	private TableColumn<TotalIncomesOfRestaurants, String> sumOfIncomeCol;

	@FXML
	private Button backBtn;

	
	/**event activated by pressing backBtn goes back to previous screen
	 * @param event Action event for event details*/
	@FXML
	void goBack(ActionEvent event) {
		OrderClient.totalIncomesOfRestaurant.clear();
		
		Globals.loadInsideFXML( Globals.view_reportsFXML);
	}

	
	/**initializes our controller by calling database to pull relevant data on the branch getting all its restaurants and it's income calls display table to populate our table*/
	public void initialize() {

		int BranchID;
		if(OrderClient.user.equals("CEO")) {
			whichBranch.setText(ViewReportsController.branchName);
			// the parameter that will be passed to server for access the relevant data in
			// the DB
			 BranchID = 0;
			if (ViewReportsController.branchName.equals("North"))
				BranchID = 1;
			else if (ViewReportsController.branchName.equals("Center"))
				BranchID = 2;
			else
				BranchID = 3;
			
		}
		else {
			BranchID=OrderClient.user.getHomeBranch();
			whichBranch.setText(OrderClient.user.getStringHomeBranch());
			}
		whichMonth.setText(ViewReportsController.monthNumber);
		
		String str = "Load_monthly_performance~income~" + BranchID+"~"+ViewReportsController.monthNumber+"~"+ViewReportsController.YearNumber;
		StartClient.order.accept(str);
		display_table();
	}
		
		
	
	
	/**initialzes our table setting each column a newcell factory and also setting the tables setItems using totalIncomesOfRestaurants variable from OrderClient*/
	void display_table() {
		System.out.println(OrderClient.totalIncomesOfRestaurant.toString());
		restaurantCol.setCellValueFactory(new PropertyValueFactory<TotalIncomesOfRestaurants, String>("restaurant"));
		sumOfIncomeCol.setCellValueFactory(new PropertyValueFactory<TotalIncomesOfRestaurants, String>("total_income"));
		IncomeTable.setItems(OrderClient.totalIncomesOfRestaurant);
	}

}




