package report;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import entity.TotalIncomesOfRestaurants;
import entity.MonthlyPerformance;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PerformanceReportController {

	  @FXML
	    private TableView<MonthlyPerformance> performanceTable;

	    @FXML
	    private TableColumn<MonthlyPerformance, String> restaurantCol;

	    @FXML
	    private TableColumn<MonthlyPerformance, String> amtOfOrdersCol;

	    @FXML
	    private TableColumn<MonthlyPerformance, String> delaySupplyCol;


	@FXML
	private Button backButton;

	@FXML
	private Label whichBranch;

	@FXML
	private Label whichMonth;
	
	@FXML
	private Label whichYear;

	@FXML
	void goBack(ActionEvent event) {
		OrderClient.monthlyPerformance.clear();
		Globals.loadInsideFXML(Globals.view_reportsFXML);
	}

	public void initialize() {
		int BranchID;
		if(OrderClient.user.getType().equals("CEO")) {
			whichBranch.setText(ViewReportsController.branchName);
			 BranchID = 0;
			if (ViewReportsController.branchName.equals("North"))
				BranchID = 1;
			else if (ViewReportsController.branchName.equals("Center"))
				BranchID = 2;
			else
				BranchID = 3;
		}
		else {
			whichBranch.setText(OrderClient.user.getStringHomeBranch());
			BranchID=OrderClient.user.getHomeBranch();
			}
		whichMonth.setText(ViewReportsController.monthNumber);
		
		String str = "Load_monthly_performance~performance~" + BranchID +"~"+ViewReportsController.monthNumber+"~"+ViewReportsController.YearNumber;
		StartClient.order.accept(str);
		display_table();

	}


		void display_table() {
			
			restaurantCol.setCellValueFactory(new PropertyValueFactory<MonthlyPerformance, String>("restaurant"));
			amtOfOrdersCol.setCellValueFactory(new PropertyValueFactory<MonthlyPerformance, String>("amount_of_orders"));
			delaySupplyCol.setCellValueFactory(new PropertyValueFactory<MonthlyPerformance, String>("delay_supply"));
			performanceTable.setItems(OrderClient.monthlyPerformance);
		}
	
}
