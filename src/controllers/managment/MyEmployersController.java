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

    public void initialize()
    {
    	int branchnumber=1;
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
	    			employerRecordController.setApproval();
	    			waiting_for_approval_accordion.getPanes().add(pane);
	    		}
    		}catch (Exception e) {
				// TODO: handle exception
			}
    	}
    }
    
    
}
