package general;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import entity.Branch;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;

public class IndexControllerD {

    @FXML
    private AnchorPane formIndex;

    @FXML
    private AnchorPane paneOfGrid;

    @FXML
    private GridPane options_grid1;

    @FXML
    private Label hello_label;

    @FXML
    private Label welcome_label;

    @FXML
    private VBox pane_in_vbox;
    
    @FXML
    private ComboBox <Branch> comboBoxBranch;
        
    @FXML
    private Label msg_label;
    
    @FXML
    private ImageView home_page;

    static String tempTypeUser; 

	public void initialize()
    {

        StartClient.order.accept("Load_branches");
        hello_label.setText("Hello "+OrderClient.user.getFirstName());
        comboBoxBranch.setItems(Globals.branches);
        String typeUser = OrderClient.user.getType();
        Branch homeBranch= new Branch(OrderClient.user.getHomeBranch(),OrderClient.user.getStringHomeBranch());
        comboBoxBranch.getSelectionModel().select(homeBranch);
        
        switch(typeUser) {
        
        case "CEO":
        	
        	Globals.loadInsideFXML(Globals.homePageCeo);
        	tempTypeUser="CEO";
        	
            Button CEOOption1= new Button ("View Reports");
            Button CEOOption2= new Button ("View Employers");
            Button CEOOption3= new Button ("View Users");
            Button CEOOption4= new Button ("Edit Menu ");
            Button CEOOption5= new Button ("Register Employer");
            Button CEOOption6= new Button ("Approve Account");
            Button CEOOption7= new Button ("Log out");
            
            
            setDeginButton (CEOOption1);
            setDeginButton (CEOOption2);
            setDeginButton (CEOOption3);
            setDeginButton (CEOOption4);
            setDeginButton (CEOOption5);
            setDeginButton (CEOOption6);
            setDeginButton (CEOOption7);            
            
            options_grid1.add(CEOOption1,0,0);
            options_grid1.add(CEOOption2,0,1);
            options_grid1.add(CEOOption3,0,2);
            options_grid1.add(CEOOption4,0,3);
            options_grid1.add(CEOOption5,0,4);
            options_grid1.add(CEOOption6,0,5);
            options_grid1.add(CEOOption7,0,8);
            
               
                CEOOption1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                    	   Globals.loadInsideFXML(Globals.reportFXML);
                    }});
                
                CEOOption2.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        Globals.loadInsideFXML(Globals.view_employersFXML);
                    }});
                
                CEOOption3.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        Globals.loadInsideFXML(Globals.changeuserstatusFXML);
                    }});
        
 
              CEOOption4.setOnAction(new EventHandler<ActionEvent>() {
                  @Override
                  public void handle(ActionEvent e) {
                      Globals.loadInsideFXML(Globals.MenuFXML);
                  }});
              
                
                CEOOption5.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        Globals.loadInsideFXML(Globals.regnewemployerFXML);
                    }});

                
              CEOOption6.setOnAction(new EventHandler<ActionEvent>() {
                  @Override
                  public void handle(ActionEvent e) {
                      Globals.loadInsideFXML(Globals.approveUserFXML);
                  }});

              CEOOption7.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                    	StartClient.order.accept("Logout~"+OrderClient.user.getID()); 
                    	Globals.loadFXML(null, Globals.userloginFXML, e,null);
                    }});
              
          	    

            break;
            
       case "Branch Manager":
           
    	   comboBoxBranch.setVisible(false);
    	   
            Button BranchManagerOption1= new Button ("View Reports");
            Button BranchManagerOption2= new Button ("View Users");
            Button BranchManagerOption3= new Button ("Edit Menu");
            Button BranchManagerOption4= new Button ("View Employers");
            Button BranchManagerOption5= new Button ("Register Account");
            Button BranchManagerOption6= new Button ("Approve suppliers");
            Button BranchManagerOption7= new Button ("Log out");
            
            setDeginButton(BranchManagerOption1);
            setDeginButton(BranchManagerOption2);
            setDeginButton(BranchManagerOption3);
            setDeginButton(BranchManagerOption4);
            setDeginButton(BranchManagerOption5);
            setDeginButton(BranchManagerOption6);
            setDeginButton(BranchManagerOption7);
  
            options_grid1.add(BranchManagerOption1,0,0);
            options_grid1.add(BranchManagerOption2,0,1);
            options_grid1.add(BranchManagerOption3,0,2);
            options_grid1.add(BranchManagerOption4,0,3);
            options_grid1.add(BranchManagerOption5,0,4);
            options_grid1.add(BranchManagerOption6,0,5);
            options_grid1.add(BranchManagerOption7,0,8);
  
            
            
            BranchManagerOption1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Globals.loadInsideFXML(Globals.reportFXML);
                }});
            
            BranchManagerOption2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Globals.loadInsideFXML(Globals.changeuserstatusFXML);
                }});
            
            BranchManagerOption3.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Globals.loadInsideFXML(Globals.MenuFXML);
                }});
            
            BranchManagerOption4.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Globals.loadInsideFXML(Globals.view_employersFXML);
                }});
            
         
            BranchManagerOption5.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Globals.loadInsideFXML(Globals.regnewaccountp1FXML);
                }});
            
           
            BranchManagerOption6.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Globals.loadInsideFXML(Globals.regRestaurant);
                }});
            
            BranchManagerOption7.setOnAction(new EventHandler<ActionEvent>() {
            	  @Override
                  public void handle(ActionEvent e) {
            		StartClient.order.accept("Logout~"+OrderClient.user.getID()); 
                  	Globals.loadFXML(null, Globals.userloginFXML, e,null);
                   
                  }});
          
            
            break;  
            
       case "Customer":
    	   
    	   tempTypeUser="Customer";
    	   StartClient.order.accept("Load_customer~"+OrderClient.user.getID());

           Button CustomerOption1= new Button ("New Order");
           Button CustomerOption2= new Button ("My Orders");
           Button CustomerOption3= new Button ("Log out");
           
           setDeginButton(CustomerOption1);
           setDeginButton(CustomerOption2);
           setDeginButton(CustomerOption3);
        
           options_grid1.add(CustomerOption1,0,0);
           options_grid1.add(CustomerOption2,0,1);
           options_grid1.add(CustomerOption3,0,8);
           
        
           if(OrderClient.customer.getStatus().equals("Frozen"))
           {
        	   CustomerOption1.setDisable(true);
        	   msg_label.setText("Customer frozen");
           }
           
        
           Globals.loadInsideFXML(Globals.homePageCustomer);
         
           CustomerOption1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Globals.loadInsideFXML(Globals.W4CLoginFXML);
                }});

         CustomerOption2.setOnAction(new EventHandler<ActionEvent>() {
              @Override
             public void handle(ActionEvent e) {
           Globals.loadInsideFXML(Globals.myOrdersFXML);
              }});
           
           CustomerOption3.setOnAction(new EventHandler<ActionEvent>() {
        	   @Override
               public void handle(ActionEvent e) {
        		   StartClient.order.accept("Logout~"+OrderClient.user.getID()); 
                	Globals.loadFXML(null, Globals.userloginFXML, e,null);
                
               }});
           
       
            break;      
           
       case "HR":
    	
    	   comboBoxBranch.setVisible(false);
    	   
           Button HROption1= new Button ("Register Employer");
           Button HROption2= new Button ("Approve Account");
           Button HROption3= new Button ("Log out");
            
           setDeginButton(HROption1);
           setDeginButton(HROption2);
           setDeginButton(HROption3);
           
           options_grid1.add(HROption1,0,0);
           options_grid1.add(HROption2,0,1);
           options_grid1.add(HROption3,0,8);
           
           
           HROption1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Globals.loadInsideFXML(Globals.regnewemployerFXML);
                }});
           
          
         HROption2.setOnAction(new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent e) {
                  Globals.loadInsideFXML(Globals.approveUserFXML);
              }});
           
           HROption3.setOnAction(new EventHandler<ActionEvent>() {
        	   @Override
               public void handle(ActionEvent e) {
        		   StartClient.order.accept("Logout~"+OrderClient.user.getID()); 
               	   Globals.loadFXML(null, Globals.userloginFXML, e,null);
                
               }});  
           
                break;

       case "Certified Employee":
    	   
    	   comboBoxBranch.setVisible(false);
            
           Button CEOption1= new Button ("Edit Menu");
           Button CEOption2= new Button ("Log out");
           
           setDeginButton(CEOption1);
           setDeginButton(CEOption2);
           
           options_grid1.add(CEOption1,0,0);
           options_grid1.add(CEOption2,0,8);
           
         
           CEOption1.setOnAction(new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent e) {
                   Globals.loadInsideFXML(Globals.MenuFXML);
               }});
           
           CEOption2.setOnAction(new EventHandler<ActionEvent>() {
        	   @Override
               public void handle(ActionEvent e) {
        		   StartClient.order.accept("Logout~"+OrderClient.user.getID()); 
               	   Globals.loadFXML(null, Globals.userloginFXML, e,null);
               }});  
           
            
            break;
            
       case "Supplier":
    	   
    	   comboBoxBranch.setVisible(false);
    	   
    	   StartClient.order.accept("Load_supplier~"+OrderClient.user.getID());
           
           Button SupplierOption1= new Button ("Orders");
           Button SupplierOption2= new Button ("Edit Menu");
           Button SupplierOption3= new Button ("Monthly Intake");
           Button SupplierOption4= new Button ("Log out");
           
           setDeginButton(SupplierOption1);
           setDeginButton(SupplierOption2);
           setDeginButton(SupplierOption3);
           setDeginButton(SupplierOption4);
        
           
           options_grid1.add(SupplierOption1,0,0);
           options_grid1.add(SupplierOption2,0,1);
           options_grid1.add(SupplierOption3,0,2);
           options_grid1.add(SupplierOption4,0,8);
           
     
           SupplierOption1.setOnAction(new EventHandler<ActionEvent>() {
        	   @Override
               public void handle(ActionEvent e) {
               	Globals.loadInsideFXML(Globals.NewOrdersFXML);
                
               }});
           //2-->update menu Muhamad
           SupplierOption2.setOnAction(new EventHandler<ActionEvent>() {
        	   @Override
               public void handle(ActionEvent e) {
               	Globals.loadInsideFXML(Globals.MenuFXML);
                
               }});
           
           SupplierOption3.setOnAction(new EventHandler<ActionEvent>() {
        	   @Override
               public void handle(ActionEvent e) {
               	Globals.loadInsideFXML(Globals.intakeReport);
               
               }});
           
           SupplierOption4.setOnAction(new EventHandler<ActionEvent>() {
        	   @Override
               public void handle(ActionEvent e) {
        		   StartClient.order.accept("Logout~"+OrderClient.user.getID()); 
                 	Globals.loadFXML(null, Globals.userloginFXML, e,null);
                
               }});
            
            break;  

       case "Base User":
    	   
    	   comboBoxBranch.setVisible(false);
    	   
    	   Button baseUseription1= new Button ("Log out");
    	   
           setDeginButton(baseUseription1); 
    	   options_grid1.add(baseUseription1,0,8);
    
    	   baseUseription1.setOnAction(new EventHandler<ActionEvent>() {
    		   @Override
    		   public void handle(ActionEvent e) {
    			   StartClient.order.accept("Logout~"+OrderClient.user.getID()); 
    			   Globals.loadFXML(null, Globals.userloginFXML, e,null);
    		   }});
    	   
    	   break; 
        }
    }

	public VBox getPane_in_vbox() {
		return pane_in_vbox;
	}
	
	public void setPane_in_vbox(VBox pane_in_vbox) {
		this.pane_in_vbox = pane_in_vbox;
	}

	public Label getWelcome_label() {
		return welcome_label;
	}

	public void setWelcome_label(String str) {
		this.welcome_label.setText(str);
	}

	
	public ComboBox<Branch> getComboBoxBranch() {
		return comboBoxBranch;
	}

	public void setComboBoxBranch(ComboBox<Branch> comboBoxBranch) {
		this.comboBoxBranch = comboBoxBranch;
	}

 ////////check why not recognize ???
	public void setHomePage(){
		
		if(tempTypeUser=="Customer")
			Globals.loadInsideFXML(Globals.homePageCustomer);
		else
			if(tempTypeUser=="CEO")
				 Globals.loadInsideFXML(Globals.homePageCeo);		 
}
	
	public void setDeginButton(Button b) {
		
		b.getStyleClass().add("ViewBtn");
        b.getStyleClass().add("lbl");
        b.setMaxWidth(Double.MAX_VALUE);
		
	}

}