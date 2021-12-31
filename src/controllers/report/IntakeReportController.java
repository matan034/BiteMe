package report;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import entity.IntakeOrder;
import entity.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class IntakeReportController {

    @FXML
    private ComboBox<String> year_cmb;

    @FXML
    private ComboBox<String> month_cmb;

    @FXML
    private Button view_btn;

    @FXML
    private TableView<IntakeOrder> payment_tbl;
    @FXML
    private TableColumn<IntakeOrder,Integer> orderCol;

    @FXML
    private TableColumn<IntakeOrder,Double> priceCol;

    @FXML
    private TableColumn<IntakeOrder,Double> commissionCol;

    @FXML
    private TableColumn<IntakeOrder,Double> paymentCol;

    @FXML
    private Label total_payment;

    @FXML
    private Label total_commission;

    private ObservableList<String> month= FXCollections.observableArrayList("1", "2","3","4","5","6","7","8","9","10","11","12");
    private ObservableList<String> years= FXCollections.observableArrayList("2021");
    public void initialize()
    {
    
    	year_cmb.setItems(years);
    	month_cmb.setItems(month);
    }
    @FXML
    void viewBill(ActionEvent event) {
    	StartClient.order.accept("Load_myIntake~"+OrderClient.supplier.getSupplierNum()+"~"+year_cmb.getSelectionModel().getSelectedItem()+"~"+month_cmb.getSelectionModel().getSelectedItem());
    	showTable();
    	int total_payment = 0 ,total_commission=0;
    	
    	for (IntakeOrder row : payment_tbl.getItems()) {
    		total_payment += row.getPayment();
    		total_commission += row.getCommission();
    	}
    	this.total_commission.setText(Integer.toString(total_commission)+Globals.currency);
    	this.total_payment.setText(Integer.toString(total_payment)+Globals.currency);
    
    }
    private void showTable()
    {
    	orderCol.setCellValueFactory(new PropertyValueFactory<IntakeOrder,Integer>("order_num"));
    	priceCol.setCellValueFactory(new PropertyValueFactory<IntakeOrder,Double>("price"));
    	commissionCol.setCellValueFactory(new PropertyValueFactory<IntakeOrder,Double>("commission"));
    	paymentCol.setCellValueFactory(new PropertyValueFactory<IntakeOrder,Double>("payment"));
    	payment_tbl.setItems(OrderClient.monthIntake);
    
    }

}
