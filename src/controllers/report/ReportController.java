package report;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import entity.MyFile;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;



/**
 * Controller for choosing report the user wants to view or if the BM is connected to upload a PDF report
 * Controls reportsScreen.fxml 
 * Ceo has options monthly report , quarter report,Branch manger quarterly reports
 * BM has options MonthlyReports Upload Reports
 *    
 *  @author      Daniel Aibinder
 * @version     1.0               
 * @since       01.01.2022  
 **/



public class ReportController {
	
	/**
	 * viewReportButton button to go to monthly reports*/
    @FXML
    private Button viewReportButton;
    /**
     * uploadReportsBtn button to upload a pdf report*/
    @FXML
    private Button UploadReportsBtn;
    
    /**
     * viewQuarterIncome button to go to quarterlyIncome*/
    @FXML
    private Button viewQuarterIncome;
    /**
     * viewBranchPdfReport button to view the pdf report branch manager sent*/
    @FXML
    private Button viewBranchPdfReport;
    
    /**Image for green V for user feedback*/
    @FXML
    private ImageView green_v;
    
    /**
     * initializes our controller to hide and show buttons according to user type CEO doesnt see view uploads
     * BM only sees UploadReports and View reports button*/
    public void initialize()
    {
    	OrderClient.upload_pdf_flag=false;
    	green_v.setVisible(false);
    	if(!OrderClient.user.getType().equals("CEO"))
    	{
    		viewQuarterIncome.setVisible(false);
    		UploadReportsBtn.setVisible(true);
    		viewBranchPdfReport.setVisible(false);
    	}
    	else {//CEO
    		viewQuarterIncome.setVisible(true);
    		UploadReportsBtn.setVisible(false);
    		viewBranchPdfReport.setVisible(true);
    	}
    		
    	
    }
    
    /**
     * loads ViewPdfScreen on pressing view quarterly BM  reports button
     * loads the pdf the BM has uploaded
     * @param event actionevent containing event details*/
    @FXML
    void ViewPdf(ActionEvent event) {
    	Globals.loadInsideFXML( Globals.viewPdf);
    }
    
    
    
    
    /**
     * loads QuarterlyHistogramScreen.fxml on pressing view quarterly report button 
     * @param event actionevent containing event details*/
    @FXML
    void goToChooseQuarterAndBranch(ActionEvent event) {
    	Globals.loadInsideFXML( Globals.quarterlyHistogram);
    }
    
    /**
     * Activates on pressing upload report button opens a file chooser for user where he can select a pdf to upload 
     * @param event action event containing event details*/
    @FXML
    void goToCreateReports(ActionEvent event) {
    	Stage stage=(Stage)((Node) event.getSource()).getScene().getWindow();
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Select File");
    	File file = fileChooser.showOpenDialog(stage);
  
    	

    	if (file != null) {
    		  try{
    			  MyFile filePath= new MyFile(file.getPath());
    		    	filePath.setFile(file);
    			      byte [] mybytearray  = new byte [(int)file.length()];
    			      FileInputStream fis = new FileInputStream(file);
    			      BufferedInputStream bis = new BufferedInputStream(fis);			  
    			      
    			      filePath.initArray(mybytearray.length);
    			      filePath.setSize(mybytearray.length);
    			      
    			      bis.read(filePath.getMybytearray(),0,mybytearray.length);
    			      filePath.setBranchID(OrderClient.user.getHomeBranch());
    			      StartClient.order.accept(filePath);	
    			      fis.close();
    			      bis.close();
    			      if(OrderClient.upload_pdf_flag==true) {
    			    	  green_v.setVisible(true);
    			    	  PauseTransition pause = new PauseTransition(Duration.seconds(2));
    				    	pause.setOnFinished(e -> green_v.setVisible(false));
    				    	pause.play();
    			      }
    			    }
    			catch (Exception e) {
    				System.out.println("Error send (Files)msg) to Server");}
    		}
    }

    /**
     * Activates on pressing  monthly reports button opens the ViewReports.fxml page
     * @param event action event containing event details*/
    @FXML
    void goToViewReports(ActionEvent event) {
    	Globals.loadInsideFXML(Globals.view_reportsFXML);
    }

}
