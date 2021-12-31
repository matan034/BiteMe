package general;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import entity.Branch;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    



	public void initialize()
    {

        StartClient.order.accept("Load_branches");
        String typeUser=OrderClient.user.getType();
        hello_label.setText("Hello "+OrderClient.user.getFirstName());
        comboBoxBranch.setItems(Globals.branches);
        Branch homeBranch= new Branch(OrderClient.user.getHomeBranch(),OrderClient.user.getStringHomeBranch());
        comboBoxBranch.getSelectionModel().select(homeBranch);
        
        
        
        switch(typeUser) {
        
        case "CEO":
            
  
            Button CEOOption1= new Button ("View Reports");
            Button CEOOption2= new Button ("View Employers");
            Button CEOOption3= new Button ("View Users");
            Button CEOOption4= new Button ("View Menu ");
            Button CEOOption5= new Button ("Register Employer");
            Button CEOOption6= new Button ("Approve Account");
            Button CEOOption7= new Button ("Log out");
            

            CEOOption1.getStyleClass().add("ViewBtn");
            CEOOption1.setMaxWidth(Double.MAX_VALUE);
            CEOOption2.getStyleClass().add("ViewBtn");
            CEOOption2.setMaxWidth(Double.MAX_VALUE);
            CEOOption3.getStyleClass().add("ViewBtn");
            CEOOption3.setMaxWidth(Double.MAX_VALUE);
            CEOOption4.getStyleClass().add("ViewBtn");
            CEOOption4.setMaxWidth(Double.MAX_VALUE);
            CEOOption5.getStyleClass().add("ViewBtn");
            CEOOption5.setMaxWidth(Double.MAX_VALUE);
            CEOOption6.getStyleClass().add("ViewBtn");
            CEOOption6.setMaxWidth(Double.MAX_VALUE);
            CEOOption7.getStyleClass().add("ViewBtn");
            CEOOption7.setMaxWidth(Double.MAX_VALUE);
            
            
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
                
                ///insert 2,4 option /////////////////////////////////////////
                ///2--> we need to crate screen 
                
                CEOOption3.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        Globals.loadInsideFXML(Globals.changeuserstatusFXML);
                    }});
        
          /////4->>>update menu Muhamad
              CEOOption4.setOnAction(new EventHandler<ActionEvent>() {
                  @Override
                  public void handle(ActionEvent e) {
                      Globals.loadInsideFXML(Globals.viewMenu);
                  }});
              
                
                CEOOption5.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        Globals.loadInsideFXML(Globals.regnewemployerFXML);
                    }});
                
                
          //6-->approve account Daniel
//              CEOOption6.setOnAction(new EventHandler<ActionEvent>() {
//                  @Override
//                  public void handle(ActionEvent e) {
//                      Globals.loadInsideFXML(Globals.);
//                  }});
        
                
               
              CEOOption7.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                    	Globals.loadFXML(null, Globals.userloginFXML, e,null);
                     
                    }});
          
            break;
            
       case "Branch Manager":
           
           
            Button BranchManagerOption1= new Button ("View Reports");
            Button BranchManagerOption2= new Button ("View Users");
            Button BranchManagerOption3= new Button ("View And Update Menu");
            Button BranchManagerOption4= new Button ("send Report");
            Button BranchManagerOption5= new Button ("View Employers");
            Button BranchManagerOption6= new Button ("Register Account");
            Button BranchManagerOption7= new Button ("Approve suppliers");
            Button BranchManagerOption8= new Button ("Log out");
            
            BranchManagerOption1.getStyleClass().add("ViewBtn");
            BranchManagerOption1.setMaxWidth(Double.MAX_VALUE);
            BranchManagerOption2.getStyleClass().add("ViewBtn");
            BranchManagerOption2.setMaxWidth(Double.MAX_VALUE);
            BranchManagerOption3.getStyleClass().add("ViewBtn");
            BranchManagerOption3.setMaxWidth(Double.MAX_VALUE);
            BranchManagerOption4.getStyleClass().add("ViewBtn");
            BranchManagerOption4.setMaxWidth(Double.MAX_VALUE);
            BranchManagerOption5.getStyleClass().add("ViewBtn");
            BranchManagerOption5.setMaxWidth(Double.MAX_VALUE);
            BranchManagerOption6.getStyleClass().add("ViewBtn");
            BranchManagerOption6.setMaxWidth(Double.MAX_VALUE);
            BranchManagerOption7.getStyleClass().add("ViewBtn");
            BranchManagerOption7.setMaxWidth(Double.MAX_VALUE);
            
            
  
            options_grid1.add(BranchManagerOption1,0,0);
            options_grid1.add(BranchManagerOption2,0,1);
            options_grid1.add(BranchManagerOption3,0,2);
            options_grid1.add(BranchManagerOption4,0,3);
            options_grid1.add(BranchManagerOption5,0,4);
            options_grid1.add(BranchManagerOption6,0,6);
            options_grid1.add(BranchManagerOption7,0,7);
            options_grid1.add(BranchManagerOption8,0,8);
            
            
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
            
            BranchManagerOption5.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Globals.loadInsideFXML(Globals.view_employersFXML);
                }});
            ///insert 3,4,5 option /////////////////////////////////////////
            ///3--> update menu Muhamad
            ///4--> send reports Yeela
            //5--> we need to crate screen 
           
            BranchManagerOption6.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Globals.loadInsideFXML(Globals.regnewaccountp1FXML);
                }});
            
            
            ///7-->we need to crate screen  
            BranchManagerOption7.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Globals.loadInsideFXML(Globals.regRestaurant);
                }});
            
            BranchManagerOption8.setOnAction(new EventHandler<ActionEvent>() {
            	  @Override
                  public void handle(ActionEvent e) {
            		StartClient.order.accept("Logout~"+OrderClient.user.getID()); 
                  	Globals.loadFXML(null, Globals.userloginFXML, e,null);
                   
                  }});
          
            
            break;  
            
       case "Customer":
    	   StartClient.order.accept("Load_customer~"+OrderClient.user.getID());
          
           Button CustomerOption1= new Button ("New Order");
           Button CustomerOption2= new Button ("My Orders");
           Button CustomerOption3= new Button ("Log out");
           
           CustomerOption1.getStyleClass().add("ViewBtn");
           CustomerOption1.setMaxWidth(Double.MAX_VALUE);
           CustomerOption2.getStyleClass().add("ViewBtn");
           CustomerOption2.setMaxWidth(Double.MAX_VALUE);
           CustomerOption3.getStyleClass().add("ViewBtn");
           CustomerOption3.setMaxWidth(Double.MAX_VALUE);
        
           
           options_grid1.add(CustomerOption1,0,0);
           options_grid1.add(CustomerOption2,0,1);
           options_grid1.add(CustomerOption3,0,8);
        
           if(OrderClient.customer.getStatus().equals("Frozen"))
           {
        	   CustomerOption1.setDisable(true);
        	   msg_label.setText("Customer frozen");
           }
           CustomerOption1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Globals.loadInsideFXML(Globals.W4CLoginFXML);
                }});
           
          
           // (Matan)
         CustomerOption2.setOnAction(new EventHandler<ActionEvent>() {
              @Override
             public void handle(ActionEvent e) {
           Globals.loadInsideFXML(Globals.myOrdersFXML);
              }});
           
           CustomerOption3.setOnAction(new EventHandler<ActionEvent>() {
        	   @Override
               public void handle(ActionEvent e) {
               	Globals.loadFXML(null, Globals.userloginFXML, e,null);
                
               }});
           
       
            break;      
            
       case "HR":
           
           Button HROption1= new Button ("Register Employer");
           Button HROption2= new Button ("Approve Account");
           Button HROption3= new Button ("Log out");
            
           HROption1.getStyleClass().add("ViewBtn");
           HROption1.setMaxWidth(Double.MAX_VALUE);
           HROption2.getStyleClass().add("ViewBtn");
           HROption2.setMaxWidth(Double.MAX_VALUE);
           HROption3.getStyleClass().add("ViewBtn");
           HROption3.setMaxWidth(Double.MAX_VALUE);
           
           options_grid1.add(HROption1,0,0);
           options_grid1.add(HROption2,0,1);
           options_grid1.add(HROption3,0,8);
           
           
           HROption1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Globals.loadInsideFXML(Globals.regnewemployerFXML);
                }});
           
           
         //2-->approve account Daniel
         HROption2.setOnAction(new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent e) {
                  Globals.loadInsideFXML(Globals.approveUserFXML);
              }});
           
           HROption3.setOnAction(new EventHandler<ActionEvent>() {
        	   @Override
               public void handle(ActionEvent e) {
               	Globals.loadFXML(null, Globals.userloginFXML, e,null);
                
               }});  
           
                break;

       case "Certified Employee":
            
           Button CEOption1= new Button ("View and Update Menu");
           Button CEOption2= new Button ("Log out");
           
           CEOption1.getStyleClass().add("ViewBtn");
           CEOption1.setMaxWidth(Double.MAX_VALUE);
           CEOption2.getStyleClass().add("ViewBtn");
           CEOption2.setMaxWidth(Double.MAX_VALUE);
           
           options_grid1.add(CEOption1,0,0);
           options_grid1.add(CEOption2,0,8);
           
         ///1--> update menu Muhamad
           
           CEOption2.setOnAction(new EventHandler<ActionEvent>() {
        	   @Override
               public void handle(ActionEvent e) {
               	Globals.loadFXML(null, Globals.userloginFXML, e,null);
                
               }});  
           
            
            break;
            
       case "Supplier":
    	   StartClient.order.accept("Load_supplier~"+OrderClient.user.getID());
           
           Button SupplierOption1= new Button ("Orders");
           Button SupplierOption2= new Button ("view and Update Menu");
           Button SupplierOption3= new Button ("Monthly Intake");
           Button SupplierOption4= new Button ("Add Dish");
           Button SupplierOption5= new Button ("Add Dish to rest");
           Button SupplierOption8= new Button ("Log out");
           
           SupplierOption1.getStyleClass().add("ViewBtn");
           SupplierOption1.setMaxWidth(Double.MAX_VALUE);
           SupplierOption2.getStyleClass().add("ViewBtn");
           SupplierOption2.setMaxWidth(Double.MAX_VALUE);
           SupplierOption3.getStyleClass().add("ViewBtn");
           SupplierOption3.setMaxWidth(Double.MAX_VALUE);
        
           
           options_grid1.add(SupplierOption1,0,0);
           options_grid1.add(SupplierOption2,0,1);
           options_grid1.add(SupplierOption3,0,2);
           options_grid1.add(SupplierOption4,0,3);
           options_grid1.add(SupplierOption5,0,4);
           options_grid1.add(SupplierOption8,0,8);
           
           //1->>view orders Daniel
           SupplierOption1.setOnAction(new EventHandler<ActionEvent>() {
        	   @Override
               public void handle(ActionEvent e) {
               	Globals.loadInsideFXML(Globals.NewOrdersFXML);
                
               }});
           //2-->update menu Muhamad
           SupplierOption2.setOnAction(new EventHandler<ActionEvent>() {
        	   @Override
               public void handle(ActionEvent e) {
               	Globals.loadInsideFXML(Globals.menuFXML);
                
               }});
           SupplierOption3.setOnAction(new EventHandler<ActionEvent>() {
        	   @Override
               public void handle(ActionEvent e) {
               	Globals.loadInsideFXML(Globals.intakeReport);
                
               }});
           SupplierOption4.setOnAction(new EventHandler<ActionEvent>() {
        	   @Override
               public void handle(ActionEvent e) {
               	Globals.loadInsideFXML(Globals.addDish);
                
               }});
           SupplierOption5.setOnAction(new EventHandler<ActionEvent>() {
        	   @Override
               public void handle(ActionEvent e) {
               	Globals.loadInsideFXML(Globals.addDishToRest);
                
               }});
           SupplierOption8.setOnAction(new EventHandler<ActionEvent>() {
        	   @Override
               public void handle(ActionEvent e) {
               	Globals.loadFXML(null, Globals.userloginFXML, e,null);
                
               }});
            
            break;  
            
        default:System.out.println("this default");
                
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




    
   
}