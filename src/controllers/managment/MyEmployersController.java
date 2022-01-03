package managment;

import clients.OrderClient;
import clients.StartClient;

import entity.Employer;

import general.MyListener;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;

import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.VBox;

/**
 * This class is used for approving employers by BM, here he can view all employers that he has approved along with employer that still need to be approved
 * @param waiting_for_approv_vbox used to hold our waiting for approval accordion
 * @param waiting_for_approval_accordion accordion holding titled panes with the employer to approve
 * @param approved_vbox holds our approved_accordion
 * @param approved_accordion all employers that have been approved are moved here 
 * @author      matan weisberg
 * @version     1.0               
 * @since       01.01.2022        
 */


public class MyEmployersController {

    @FXML
    private VBox waiting_for_approval_vbox;

    @FXML
    private Accordion waiting_for_approval_accordion;

    @FXML
    private VBox approved_vbox;

    @FXML
    private Accordion approved_accordion;
    
    private MyListener approveEmployer;
    
    
    /**
     * initialize func for loading all employers in currect users branch
     * 
     * we set a MyListener from MyListener interface to have an onclickListener where we approve an employer via the database
     * this interface will help us transfer data to EmployerRecord controller to handle moving user between catogories(waiting for approval and approved)*/
    public void initialize()
    {
    	int branchnumber=OrderClient.user.getHomeBranch();
    	String str="Load_myEmployers~"+branchnumber;
    	StartClient.order.accept(str);
    	approveEmployer=new MyListener() {
			
			@Override
			public void onClickListener(Object object) {
				Employer emp=(Employer)object;
				emp.setIs_approved(1);
				String emp_approved="Employer_approved~"+"1~"+emp.getEmployerNum();
				StartClient.order.accept(emp_approved);
				
				
			}
		};
    	for(Employer emp:OrderClient.myEmployers)
    	{
    		try {
	    		FXMLLoader fxmlLoader = new FXMLLoader();
	            fxmlLoader.setLocation(getClass().getResource("/branch_manager/EmployerRecordScreen.fxml"));
	            VBox anchorPane;
	            anchorPane = fxmlLoader.load();       
	            EmployerRecordController employerRecordController = fxmlLoader.getController();
	            employerRecordController.setData(emp,approveEmployer);
	    		TitledPane pane = new TitledPane(emp.getName(), anchorPane);
	    		if(emp.getIs_approved()==1)
	    		{
	    			approved_accordion.getPanes().add(pane);
	    		}
	    		else
	    		{
	    			employerRecordController.setApproval(waiting_for_approval_accordion,approved_accordion,pane);
	    			waiting_for_approval_accordion.getPanes().add(pane);
	    			
	    		}
    		}catch (Exception e) {
				// TODO: handle exception
			}
    	}
    }
    
    
}
