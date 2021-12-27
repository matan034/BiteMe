
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

	@FXML
	void goBack(ActionEvent event) {
		OrderClient.totalIncomesOfRestaurant.clear();
		
		Globals.loadInsideFXML( Globals.view_reportsFXML);
	}

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
		
		
	
	
	
	void display_table() {
		System.out.println(OrderClient.totalIncomesOfRestaurant.toString());
		restaurantCol.setCellValueFactory(new PropertyValueFactory<TotalIncomesOfRestaurants, String>("restaurant"));
		sumOfIncomeCol.setCellValueFactory(new PropertyValueFactory<TotalIncomesOfRestaurants, String>("total_income"));
		IncomeTable.setItems(OrderClient.totalIncomesOfRestaurant);
	}

}




