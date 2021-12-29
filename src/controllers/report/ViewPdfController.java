package report;

import java.io.File;

import com.mysql.cj.x.protobuf.MysqlxCrud.Order;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import entity.Branch;
import entity.MyFile;
import general.IndexControllerD;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;


public class ViewPdfController {

    @FXML
    private ComboBox<Branch> branch_cmb;

    @FXML
    private ComboBox<String> quater_cmb;

    @FXML
    private ComboBox<String> year_cmb;

    @FXML
    private Button open_btn;

    
    
    

    
    private ObservableList<String> quarters= FXCollections.observableArrayList("Q1", "Q2","Q3","Q4");
    private ObservableList<String> years= FXCollections.observableArrayList("2021");
    public void initialize() {
    	quater_cmb.setItems(quarters);
    	year_cmb.setItems(years);
    	branch_cmb.setItems(Globals.branches);
    	open_btn.setDisable(true);
    	quater_cmb.getSelectionModel().selectedItemProperty().addListener((ChangeListener<String>)defineListener(1));
    	year_cmb.getSelectionModel().selectedItemProperty().addListener((ChangeListener<String>)defineListener(1));
    	branch_cmb.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Branch>)defineListener(0));
    	
    	
    }
    private ChangeListener<?> defineListener(int flag)
    {
    	if(flag==1)
    	return new ChangeListener<String>() {   	 
			@Override
			public void changed(ObservableValue arg0, String arg1, String arg2) {
				 if(arg2!=null)
    	    	    {
    	    	    	validate_input();
    	    	    }
			}
    	    };  
    	  else return new ChangeListener<Branch>() {   	 
  			@Override
  			public void changed(ObservableValue arg0, Branch arg1, Branch arg2) {
  				 if(arg2!=null)
      	    	    {
      	    	    	validate_input();
      	    	    }
  			}
      	    };  
    }

    private void validate_input()
    {
   	 int i=0;
   	 
   	 if(quater_cmb.getSelectionModel().getSelectedItem()!=null) i++;
   	 if(year_cmb.getSelectionModel().getSelectedItem()!=null) i++;
   	 if(branch_cmb.getSelectionModel().getSelectedItem()!=null) i++;
   	 if(i==3) open_btn.setDisable(false);
    }
    @FXML
    void openPdf(ActionEvent event) {
    	StartClient.order.accept("Open_pdf~"+quater_cmb.getSelectionModel().getSelectedItem().substring(1)+"~"+year_cmb.getSelectionModel().getSelectedItem()+"~"+branch_cmb.getSelectionModel().getSelectedItem().getBranchID());

    	Globals.host_service.showDocument(OrderClient.loaded_file.getAbsolutePath());
    	
    }

}
