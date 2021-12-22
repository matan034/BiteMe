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

	public void initialize() {
		branchName_lbl.setText(ViewReportsController.branchName);
		monthNum_lbl.setText(ViewReportsController.monthNumber);

		// the parameter that will be passed to server for access the relevant data in
		// the DB
		int BranchID = 0;
		if (ViewReportsController.branchName.equals("North"))
			BranchID = 1;
		else if (ViewReportsController.branchName.equals("Center"))
			BranchID = 2;
		else
			BranchID = 3;
		String str = "Load_components~" + BranchID;
		StartClient.order.accept(str);
		display_table();
	}

	void display_table() {

		restaurantName.setCellValueFactory(new PropertyValueFactory<OrdersByComponents, String>("restaurant"));
		startersColumn.setCellValueFactory(new PropertyValueFactory<OrdersByComponents, String>("starters"));
		mainsColumn.setCellValueFactory(new PropertyValueFactory<OrdersByComponents, String>("mains"));
		saladsColumn.setCellValueFactory(new PropertyValueFactory<OrdersByComponents, String>("salads"));
		dessertsColumn.setCellValueFactory(new PropertyValueFactory<OrdersByComponents, String>("desserts"));
		drinksColumn.setCellValueFactory(new PropertyValueFactory<OrdersByComponents, String>("drinks"));

		ratingTable.setItems(OrderClient.componentsOfDishes);
	}

	@FXML
	void back_to_view_reports(ActionEvent event) {
		OrderClient.componentsOfDishes.clear();
        Globals.loadInsideFXML(Globals.view_reportsFXML);
	}

}
