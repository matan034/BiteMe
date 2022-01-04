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

/**
 * controller for IntakeReportScreen.fxml here the customer can view the amount he has earned after all orders and after the tax bite me collects
 * Uses a table to display the data 
 * <p>
 * Class gets table data DB using keyword Load_myIntake
 * 
 * @author      Yeela Malka
 * @version     1.0               
 * @since       01.01.2022  */


public class IntakeReportController {

	/**
	 * combo box for year selection*/
    @FXML
    private ComboBox<String> year_cmb;
    /**
     * combo box for month selection*/
    @FXML
    private ComboBox<String> month_cmb;
    /**
     * button for viewing the report*/
    @FXML
    private Button view_btn;
    /**
     * table where we display our data*/
    @FXML
    private TableView<IntakeOrder> payment_tbl;
    /**
     * column for number of orders*/
    @FXML
    private TableColumn<IntakeOrder,Integer> orderCol;

    /**
     * column for price*/
    @FXML
    private TableColumn<IntakeOrder,Double> priceCol;

    /**
     * column for amount of commision*/
    @FXML
    private TableColumn<IntakeOrder,Double> commissionCol;

    /**
     * column for amount to pay*/
    @FXML
    private TableColumn<IntakeOrder,Double> paymentCol;

    /**
     * label displaying total payment amount*/
    @FXML
    private Label total_payment;

    /**
     * label displaying total comission price*/
    @FXML
    private Label total_commission;

    private ObservableList<String> month= FXCollections.observableArrayList("1", "2","3","4","5","6","7","8","9","10","11","12");
   
    /**shows error messages*/
    @FXML
    private Label error_lbl;
    /**
     * Func initializes our combo boxes*/
    public void initialize()
    {
    
    	year_cmb.setItems(Globals.years);
    	month_cmb.setItems(month);
    }
    /**
     * Loads intake data from DB and calls show table to insert the data into the table
     * it also goes over the data to get a sum amount to display back to user
     * @param event Action event for event details*/
    @FXML
    void viewBill(ActionEvent event) {
    	StartClient.order.accept("Load_myIntake~"+OrderClient.supplier.getSupplierNum()+"~"+year_cmb.getSelectionModel().getSelectedItem()+"~"+month_cmb.getSelectionModel().getSelectedItem());
    	if(OrderClient.monthIntake== null)
    	{
    		error_lbl.setText("No Data For Requested Month");
    	}
    	else
    	{
    		error_lbl.setText("");
    		showTable();
        	int total_payment = 0 ,total_commission=0;
        	
        	for (IntakeOrder row : payment_tbl.getItems()) {
        		total_payment += row.getPayment();
        		total_commission += row.getCommission();
        	}
        	this.total_commission.setText(Integer.toString(total_commission)+Globals.currency);
        	this.total_payment.setText(Integer.toString(total_payment)+Globals.currency);
    	}
    	
    
    }
    /**
     * Func to initalize columns with cellvalue and set items to talbe with OrderClient.monthIntake*/
    private void showTable()
    {
    	orderCol.setCellValueFactory(new PropertyValueFactory<IntakeOrder,Integer>("order_num"));
    	priceCol.setCellValueFactory(new PropertyValueFactory<IntakeOrder,Double>("price"));
    	commissionCol.setCellValueFactory(new PropertyValueFactory<IntakeOrder,Double>("commission"));
    	paymentCol.setCellValueFactory(new PropertyValueFactory<IntakeOrder,Double>("payment"));
    	payment_tbl.setItems(OrderClient.monthIntake);
    
    }

}
