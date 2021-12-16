package controllers;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
    private AnchorPane paneInPane;
    
    
    public void initialize()
    {

        
        String typeUser=OrderClient.user.getType();
        hello_label.setText("Hello "+OrderClient.user.getFirstName());
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
                    	welcome_label.setVisible(false);
       
                        Globals.loadInsideFXML(Globals.reportFXML);
                    }});
                
                ///insert 2,4 option /////////////////////////////////////////
                ///2--> we need to crate screen 
                
                CEOOption3.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        Globals.loadInsideFXML(Globals.changeuserstatusFXML);
                    }});
        
//          /////4->>>update menu Muhamad
//              CEOOption4.setOnAction(new EventHandler<ActionEvent>() {
//                  @Override
//                  public void handle(ActionEvent e) {
//                      Globals.loadInsideFXML(Globals.);
//                  }});
              
                
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
                        Globals.loadInsideFXML(Globals.userloginFXML);
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
            
            ///insert 3,4,5 option /////////////////////////////////////////
            ///3--> update menu Muhamad
            ///4--> send reports Yeela
            //5--> we need to crate screen 
            BranchManagerOption5.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Globals.loadInsideFXML(Globals.view_employersFXML);
                }});
            
            
            BranchManagerOption6.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Globals.loadInsideFXML(Globals.regnewaccountp1FXML);
                }});
            
            
            ///7-->we need to crate screen  
            
            BranchManagerOption8.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        Globals.loadInsideFXML(Globals.userloginFXML);
                    }});
          
            
            break;  
            
       case "Customer":
    	   StartClient.order.accept("Load_customer~"+OrderClient.user.getID());
    	   
           
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
                    Globals.loadInsideFXML(Globals.userloginFXML);
                }});
           
       
            break;      
            
       case "HR":
       
           Button HROption1= new Button ("Register Employer");
           Button HROption2= new Button ("Approve Account");
           Button HROption3= new Button ("Log out");
            
           
           options_grid1.add(HROption1,0,0);
           options_grid1.add(HROption2,0,1);
           options_grid1.add(HROption3,0,8);
           
           
           HROption1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Globals.loadInsideFXML(Globals.regnewemployerFXML);
                }});
           
           
         //2-->approve account Daniel
//         HROption2.setOnAction(new EventHandler<ActionEvent>() {
//              @Override
//              public void handle(ActionEvent e) {
//                  Globals.loadInsideFXML(Globals.);
//              }});
           
           HROption3.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Globals.loadInsideFXML(Globals.userloginFXML);
                }});   
           
                break;

       case "Certified Employee":
         
           Button CEOption1= new Button ("View and Update Menu");
           Button CEOption2= new Button ("Log out");
           
           options_grid1.add(CEOption1,0,0);
           options_grid1.add(CEOption2,0,8);
           
         ///1--> update menu Muhamad
           
           CEOption2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Globals.loadInsideFXML(Globals.userloginFXML);
                }});   
           
            
            break;
            
       case "Supplier":
           
         
           
           Button SupplierOption1= new Button ("Orders");
           Button SupplierOption2= new Button ("view and Update Menu");
           Button SupplierOption3= new Button ("Log out");
           
           options_grid1.add(SupplierOption1,0,0);
           options_grid1.add(SupplierOption2,0,1);
           options_grid1.add(SupplierOption3,0,8);
           
           //1->>view orders Daniel
           //2-->update menu Muhamad
           
           SupplierOption3.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Globals.loadInsideFXML(Globals.userloginFXML);
                }});
            
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


