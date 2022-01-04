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
import javafx.scene.control.Label;


/**
 * Controller for viewing a pdf used by ViewPdfScreen.fxml used by CEO to view pdf report sent by Branch manager
 * We do this by pulling a blob from database to display the report back to CEO
 * 
 * @param branch_cmb combo box for choosing a branch
 * @param quarter_cmb combo box for choosing a quarter
 * @param year_cmb bombo box for choosing a year
 * @param open_btn button for opening the pdf report
 * @param backBtn button for going back to previous screen
 * @param error_lbl label to display error to user
 * @param quarters observable list with quarters to choose from
 *  @author     Dorin bahar
 * @version     1.0               
 * @since       01.01.2022  */


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
    private Button backBtn;


    @FXML
    private Label error_lbl;
    
    /**
     * activates on pressing back button to send user back to reportsScreen.fxml*/
    @FXML
    void back(ActionEvent event) {
    	Globals.loadInsideFXML(Globals.reportFXML);
    }
    
    

    
    private ObservableList<String> quarters= FXCollections.observableArrayList("Q1", "Q2","Q3","Q4");
    
    
    /**
     * initalizes our combo boxes and adds event listeners  to validate input by calling validate_input function*/
    public void initialize() {
    	quater_cmb.setItems(quarters);
    	year_cmb.setItems(Globals.years);
    	branch_cmb.setItems(Globals.branches);
    	open_btn.setDisable(true);
    	quater_cmb.getSelectionModel().selectedItemProperty().addListener((ChangeListener<String>)defineListener(1));
    	year_cmb.getSelectionModel().selectedItemProperty().addListener((ChangeListener<String>)defineListener(1));
    	branch_cmb.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Branch>)defineListener(0));
    	
    	
    }
    
    /**
     * this func returns a change listener according to the flag we send
     * if flag=1 we create a listener for combo box that uses string
     * if flag!= creates a listener for combo box that uses branches 
     * @return ChangeListener<?>*/
    private ChangeListener<?> defineListener(int flag)
    {
    	if(flag==1)
    	return new ChangeListener<String>() {   	 
			@Override
			public void changed(ObservableValue arg0, String arg1, String arg2) {
				 if(arg2!=null)
    	    	    {
					 error_lbl.setText("");
    	    	    	validate_input();
    	    	    }
			}
    	    };  
    	  else return new ChangeListener<Branch>() {   	 
  			@Override
  			public void changed(ObservableValue arg0, Branch arg1, Branch arg2) {
  				 if(arg2!=null)
      	    	    {
  					 error_lbl.setText("");
      	    	    	validate_input();
      	    	    }
  			}
      	    };  
    }

    /**
     * validates that all comboboxes have been choosen if everything has been chosen we enable the open pdf button*/
    private void validate_input()
    {
   	 int i=0;
   	 
   	 if(quater_cmb.getSelectionModel().getSelectedItem()!=null) i++;
   	 if(year_cmb.getSelectionModel().getSelectedItem()!=null) i++;
   	 if(branch_cmb.getSelectionModel().getSelectedItem()!=null) i++;
   	 if(i==3) open_btn.setDisable(false);
    }
    
    /**
     * on pressing open pdf file we quary the database for the pdf and get it's path which we save in OrderClient.loaded_file and we display that file to the user
     * @param event Action event containing event details*/
    @FXML
    void openPdf(ActionEvent event) {
    	StartClient.order.accept("Open_pdf~"+quater_cmb.getSelectionModel().getSelectedItem().substring(1)+"~"+year_cmb.getSelectionModel().getSelectedItem()+"~"+branch_cmb.getSelectionModel().getSelectedItem().getBranchID());
    	if(OrderClient.loaded_file!=null)
    		Globals.host_service.showDocument(OrderClient.loaded_file.getAbsolutePath());
    	else error_lbl.setText("No Matching Report");
    	
    }

}
