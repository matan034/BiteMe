package report;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import entity.OrdersByComponents;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;


/**
 * this controller is for ordercomponentrating.fxml it displays each branches restaurants and the summary for how many orders a customer made from each dish type(main,side,salds,etc...)
 * The controller does this by populating a table with information from database
 * @param branchName_lbl label to display a branches name
 * @param monthNum_lbl label to display selected month
 * @param ratingTable table that holds our data
 * @param restaurantName column for restaurants name
 * @param startersColumn column for amount of starts ordered
 * @param mainsColumn column for amount of mains ordered
 * @param salads column for amount of salads ordered
 * @param desert column for amount of deserts ordered
 * @param drinks column for amount of drinks ordered
 * @param backToViewReports button to go back to view reports
 * 
 *      
 *  @author      Yeela Malka
 * @version     1.0               
 * @since       01.01.2022  
 **/


public class OrdersByComponentsController {

	@FXML
	private Label branchName_lbl;

	@FXML
	private Label monthNum_lbl;

	@FXML
	private TableView<OrdersByComponents> ratingTable;

	@FXML
	private TableColumn<OrdersByComponents, String> restaurantName;

	@FXML
	private TableColumn<OrdersByComponents, String> startersColumn;

	@FXML
	private TableColumn<OrdersByComponents, String> mainsColumn;

	@FXML
	private TableColumn<OrdersByComponents, String> saladsColumn;

	@FXML
	private TableColumn<OrdersByComponents, String> dessertsColumn;

	@FXML
	private TableColumn<OrdersByComponents, String> drinksColumn;

	@FXML
	private Button backToViewReports;

	
	
	/**
	 * initalizes our controller by calling database to get information about a branch the restaurants in it and the amount of orderes for each type, we wcall the displatable function in order to populate our table*/
	public void initialize() {
		int BranchID;
		if(OrderClient.user.equals("CEO")) {
			branchName_lbl.setText(ViewReportsController.branchName);
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
			branchName_lbl.setText(OrderClient.user.getStringHomeBranch());
			}
		monthNum_lbl.setText(ViewReportsController.monthNumber);

		
		String str = "Load_components~" + BranchID+"~"+ViewReportsController.monthNumber+"~"+ViewReportsController.YearNumber;
		StartClient.order.accept(str);
		display_table();
	}

	
	/**
	 * function to populate table we set each column a cell value and populate the table using componentsofDishes from OrderClient after the server has made a db call*/
	void display_table() {

		restaurantName.setCellValueFactory(new PropertyValueFactory<OrdersByComponents, String>("restaurant"));
		startersColumn.setCellValueFactory(new PropertyValueFactory<OrdersByComponents, String>("starters"));
		mainsColumn.setCellValueFactory(new PropertyValueFactory<OrdersByComponents, String>("mains"));
		saladsColumn.setCellValueFactory(new PropertyValueFactory<OrdersByComponents, String>("salads"));
		dessertsColumn.setCellValueFactory(new PropertyValueFactory<OrdersByComponents, String>("desserts"));
		drinksColumn.setCellValueFactory(new PropertyValueFactory<OrdersByComponents, String>("drinks"));

		ratingTable.setItems(OrderClient.componentsOfDishes);
	}

	
	/**
	 * activates on button back press to go back to view reports menu 
	 * @param event= action event containing event details*/
	@FXML
	void back_to_view_reports(ActionEvent event) {
		OrderClient.componentsOfDishes.clear();
        Globals.loadInsideFXML(Globals.view_reportsFXML);
	}

}
