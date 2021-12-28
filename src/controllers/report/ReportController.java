package report;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import entity.MyFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ReportController {

    @FXML
    private Button viewReportButton;

    @FXML
    private Button UploadReportsBtn;
    
    @FXML
    private Button viewQuarterIncome;

    @FXML
    private Button viewBranchPdfReport;
    
    public void initialize()
    {
    	if(!OrderClient.user.getType().equals("CEO"))
    	{
    		viewQuarterIncome.setVisible(false);
    		UploadReportsBtn.setVisible(true);
    	}
    	else {//CEO
    		viewQuarterIncome.setVisible(true);
    		UploadReportsBtn.setVisible(false);
    	}
    		
    	
    }
    
    
    @FXML
    void ViewPdf(ActionEvent event) {
    	Globals.loadInsideFXML( Globals.viewPdf);
    }
    
    
    
    
    
    @FXML
    void goToChooseQuarterAndBranch(ActionEvent event) {
    	Globals.loadInsideFXML( Globals.quarterlyHistogram);
    }

    @FXML
    void goToCreateReports(ActionEvent event) {
    	Stage stage=(Stage)((Node) event.getSource()).getScene().getWindow();
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Resource File");
    	File file = fileChooser.showSaveDialog(stage);
  
    	MyFile filePath= new MyFile(file.getPath());

    	if (file != null) {
    		  try{
    			      byte [] mybytearray  = new byte [(int)file.length()];
    			      FileInputStream fis = new FileInputStream(file);
    			      BufferedInputStream bis = new BufferedInputStream(fis);			  
    			      
    			      filePath.initArray(mybytearray.length);
    			      filePath.setSize(mybytearray.length);
    			      
    			      bis.read(filePath.getMybytearray(),0,mybytearray.length);
    			      filePath.setBranchID(OrderClient.user.getHomeBranch());
    			      StartClient.order.accept(filePath);		      
    			    }
    			catch (Exception e) {
    				System.out.println("Error send (Files)msg) to Server");}
    		}
    }

    @FXML
    void goToViewReports(ActionEvent event) {
    	Globals.loadInsideFXML(Globals.view_reportsFXML);
    }

}
