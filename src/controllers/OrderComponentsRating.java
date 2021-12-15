package controllers;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import entity.Order;
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

public class OrderComponentsRating {

	@FXML
	private Label branchName_lbl;

	@FXML
	private Label monthNum_lbl;

	@FXML
	private TableView<Integer> ratingTable;

	ObservableList<Integer> components;

	@FXML
	private TableColumn<String, String> restaurantName;

	@FXML
	private TableColumn<Integer, Integer> startersColumn;

	@FXML
	private TableColumn<Integer, Integer> mainsColumn;

	@FXML
	private TableColumn<Integer, Integer> saladsColumn;

	@FXML
	private TableColumn<Integer, Integer> dessertsColumn;

	@FXML
	private TableColumn<Integer, Integer> drinksColumn;

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

		//components = FXCollections.observableArrayList(OrderClient.componentsOfDishes);
		startersColumn.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("starters"));
		mainsColumn.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("mains"));
		saladsColumn.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("salads"));
		dessertsColumn.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("desserts"));
		drinksColumn.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("drinks"));

		ratingTable.setItems(components);
	}

	@FXML
	void back_to_view_reports(ActionEvent event) {

		Globals.loadInsideFXML( Globals.view_reportsFXML);
	}

}
