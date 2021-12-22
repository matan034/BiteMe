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
	    private TableColumn<MonthlyPerformance, Integer> amtOfOrdersCol;

	    @FXML
	    private TableColumn<MonthlyPerformance, Integer> delaySupplyCol;

	    @FXML
	    private TableColumn<MonthlyPerformance, Integer> servedOnTimeCol;

	@FXML
	private Button backButton;

	@FXML
	private Label whichBranch;

	@FXML
	private Label whichMonth;

	@FXML
	void goBack(ActionEvent event) {
		OrderClient.monthlyPerformance.clear();
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
			
			restaurantCol.setCellValueFactory(new PropertyValueFactory<MonthlyPerformance, String>("restaurant"));
			amtOfOrdersCol.setCellValueFactory(new PropertyValueFactory<MonthlyPerformance, Integer>("amount_of_orders"));
			delaySupplyCol.setCellValueFactory(new PropertyValueFactory<MonthlyPerformance, Integer>("delay_supply"));
			servedOnTimeCol.setCellValueFactory(new PropertyValueFactory<MonthlyPerformance, Integer>("served_on_time"));
			performanceTable.setItems(OrderClient.monthlyPerformance);
		}
	
}
