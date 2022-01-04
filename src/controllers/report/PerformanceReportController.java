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



/**
 * controller class for PerformanceReport.fxml this class gets all restaurants in a branch and displays the amount of total orders from each restaurants and how many of those orders where late
 * The controller does this by populating a table with information from database
 * @param performanceTable table holding all the data
 * @param restaurantCol column for restaurant name
 * @param amtofOrderCol column of amount of orders
 * @param delaySupplyCol column for amount of late orders
 * @param backButton button to go back to previous screen
 * @param whichBranch label to show which branch you are in 
 * @param whichMonth label to show which month you are in
 * @param whichYear label to show which year you are in
 *      
 *  @author      Yeela Malka
 * @version     1.0               
 * @since       01.01.2022  
 **/

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

	/**
	 * on backButton press activates to go back to view reports screen
	 * @param event an Actionevent containing event details*/
	@FXML
	void goBack(ActionEvent event) {
		OrderClient.monthlyPerformance.clear();
		Globals.loadInsideFXML(Globals.view_reportsFXML);
	}

	/**
	 * Initializes our controller by calling the server with Load_monthly_performance we recive data about the specific month and branches amount of orders and alte orders
	 * we call display_talbe to populate that data into our table*/
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

	/**
	 * initalizes our table setting each colum a cellvalue finally setting the table data from OrderClient.MonthlyPerformance*/
		void display_table() {
			
			restaurantCol.setCellValueFactory(new PropertyValueFactory<MonthlyPerformance, String>("restaurant"));
			amtOfOrdersCol.setCellValueFactory(new PropertyValueFactory<MonthlyPerformance, String>("amount_of_orders"));
			delaySupplyCol.setCellValueFactory(new PropertyValueFactory<MonthlyPerformance, String>("delay_supply"));
			performanceTable.setItems(OrderClient.monthlyPerformance);
		}
	
}
