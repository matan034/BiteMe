package report;

import java.io.File;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import entity.Branch;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.web.WebView;

public class ViewPdfController {

    @FXML
    private ComboBox<Branch> branch_cmb;

    @FXML
    private ComboBox<String> quater_cmb;

    @FXML
    private ComboBox<String> year_cmb;

    @FXML
    private Button open_btn;

    @FXML
    private WebView webviewPdf;
    
    
    private HostServices hostServices ;

    public HostServices getHostServices() {
        return hostServices ;
    }

    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices ;
    }

    
    private ObservableList<String> quarters= FXCollections.observableArrayList("Q1", "Q2","Q3","Q4");
    private ObservableList<String> years= FXCollections.observableArrayList("2021");
    public void initialize() {
    	quater_cmb.setItems(quarters);
    	year_cmb.setItems(years);
    	branch_cmb.setItems(Globals.branches);
    }

    @FXML
    void openPdf(ActionEvent event) {
    	StartClient.order.accept("Open_pdf~"+quater_cmb.getSelectionModel().getSelectedItem()+"~"+year_cmb.getSelectionModel().getSelectedItem()+"~"+branch_cmb.getSelectionModel().getSelectedItem().getBranchID());
    	
    	File file = new File("."+OrderClient.pdfpath);
    	HostServices hostServices = getHostServices();
    	hostServices.showDocument(file.getAbsolutePath());
    	
    }

}
