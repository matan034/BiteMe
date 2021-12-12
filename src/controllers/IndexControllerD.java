package controllers;

import common.Globals;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import javafx.event.EventHandler;

public class IndexControllerD {

    @FXML
    private AnchorPane formIndex;

    @FXML
    private Label welcom_label;

    @FXML
    private Label hello_label;

    @FXML
    private AnchorPane paneInPane;

    @FXML
    private GridPane options_grid1;
    
    @FXML
    private Pane image;
    
	
	public void initialize()
	{

		
		String typeUser="Customer";
		
		switch(typeUser) {
		
		case "CEO":
			
			hello_label.setText("Hello CEO");
			
			Button CEOOption1= new Button ("View Reports");
			Button CEOOption2= new Button ("View Employers");
			Button CEOOption3= new Button ("View Users");
			Button CEOOption4= new Button ("View Menu");
			Button CEOOption5= new Button ("Register Employer");
			Button CEOOption6= new Button ("Register Suppliers");
			Button CEOOption7= new Button ("Approve Account");
			Button CEOOption8= new Button ("New Order");
			Button CEOOption9= new Button ("Log out");
			
			options_grid1.add(CEOOption1,0,0);
			options_grid1.add(CEOOption2,0,1);
			options_grid1.add(CEOOption3,0,2);
			options_grid1.add(CEOOption4,0,3);
			options_grid1.add(CEOOption5,0,4);
			options_grid1.add(CEOOption6,0,5);
			options_grid1.add(CEOOption7,0,6);
			options_grid1.add(CEOOption8,0,7);
			options_grid1.add(CEOOption9,0,8);

			
		     	CEOOption1.setOnAction(new EventHandler<ActionEvent>() {
				    @Override
				    public void handle(ActionEvent e) {
				    	Globals.loadInsideFXML(Globals.reportFXML);
				    }});
		
			break;
			
       case "BrenchManager":
    	   
    	   hello_label.setText("Hello Brench Manager");
    	   
    		Button BranchManagerOption1= new Button ("View Reports");
			Button BranchManagerOption2= new Button ("View Users");
			Button BranchManagerOption3= new Button ("View And Update Menu");
			Button BranchManagerOption4= new Button ("send Report");
			Button BranchManagerOption5= new Button ("Confirm Employers");
			Button BranchManagerOption6= new Button ("Register Suppliers");
			Button BranchManagerOption7= new Button ("Register Account");
			Button BranchManagerOption8= new Button ("Log out");
			
			options_grid1.add(BranchManagerOption1,0,0);
			options_grid1.add(BranchManagerOption2,0,1);
			options_grid1.add(BranchManagerOption3,0,2);
			options_grid1.add(BranchManagerOption4,0,3);
			options_grid1.add(BranchManagerOption5,0,4);
			options_grid1.add(BranchManagerOption6,0,5);
			options_grid1.add(BranchManagerOption7,0,6);
			options_grid1.add(BranchManagerOption8,0,8);
			
			
			break;	
			
       case "Customer":
    	   
    	   hello_label.setText("Hello Customer");
    	   
    	   Button CustomerOption1= new Button ("New Order");
		   Button CustomerOption2= new Button ("My Orders");
		   Button CustomerOption3= new Button ("Log out");
		   
		   
		   CustomerOption1.setOnAction(new EventHandler<ActionEvent>() {
			    @Override
			    public void handle(ActionEvent e) {
			    	Globals.loadInsideFXML( Globals.W4CLoginFXML);
			    }});
		   
		   options_grid1.add(CustomerOption1,0,0);
		   options_grid1.add(CustomerOption2,0,1);
		   options_grid1.add(CustomerOption3,0,8);
			break;		
			
       case "HR":
    	   hello_label.setText("Hello HR");
    	   
    	   Button HROption1= new Button ("Register Employer");
		   Button HROption2= new Button ("Approve Account");
		   Button HROption3= new Button ("Log out");
			
		   
		   options_grid1.add(HROption1,0,0);
		   options_grid1.add(HROption2,0,1);
		   options_grid1.add(HROption3,0,8);
			
    			break;

       case "CertifiedEmployee":
    	   hello_label.setText("Hello Certified Employee");
			
    	   Button CEOption1= new Button ("Update Menu");
		   Button CEOption2= new Button ("Log out");
			
		   
		   options_grid1.add(CEOption1,0,0);
		   options_grid1.add(CEOption2,0,8);
			
			break;
			
       case "Supplier":
    	   
    	   hello_label.setText("Hello Supplier");
    	   
    	   Button SupplierOption1= new Button ("Orders");
		   Button SupplierOption2= new Button ("Update Menu");
		   Button SupplierOption3= new Button ("Log out");
		   
		   options_grid1.add(SupplierOption1,0,0);
		   options_grid1.add(SupplierOption2,0,1);
		   options_grid1.add(SupplierOption3,0,8);
    	   
			
			break;	
			
		default:System.out.println("this default");
			
		
		}
	}

	public AnchorPane getPaneInPane() {
		return paneInPane;
	}

	public void setPaneInPane(AnchorPane paneInPane) {
		this.paneInPane = paneInPane;
	}
	
	
}