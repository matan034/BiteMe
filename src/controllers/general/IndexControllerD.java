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


/**
 * This class is used as our index giving users access to their respective actions in our system. 
 * Each action (ordering food for example) gets loaded inside a pane in the index controller screen 
 * This screen is different for each user according to their types in our DB
 * @author      dorin bahar
 * @version     1.0               
 * @since       01.01.2022        
 */


public class IndexControllerD {

    @FXML
    private AnchorPane formIndex;

    @FXML
    private AnchorPane paneOfGrid;
    /**grid that holds our buttons in the sidebar menu */
    @FXML
    private GridPane options_grid1;
    /**label that greets user according to his name*/
    @FXML
    private Label hello_label;
    /**label to says welcome to BiteMe we change this label according to the screen we are currently in*/
    @FXML
    private Label welcome_label;
    /**this is the pane where we load all screens into for instance creating a new account screen will be loaded here */
    @FXML
    private VBox pane_in_vbox;
    /**this is the pane where we load all screens into for instance creating a new account screen will be loaded here */
    @FXML
    private ComboBox <Branch> comboBoxBranch;
    /**home page icon that user can click to go back to his home page*/
    @FXML
    private Label msg_label;
    /***/
    @FXML
    private ImageView home_page;
    /** holds the current users type*/
    static String tempTypeUser; 
    
    

    
    /*
	   * This method initializes our index, it loads all branches in DB, recognised which user is logged in and the users type to correctly display the functions the user can perform
	   * for instance a customer can order food but cannot create accounts 
	   * We use a switch case on tempTypeUser to map each user to his correct functions 
	   *
	   */
    
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
        	
        	tempTypeUser="CEO";
        	
            Button CEOOption1= new Button ("View Reports");
            
            
            /*
            Button CEOOption2= new Button ("View Employers");
            Button CEOOption3= new Button ("View Users");
            Button CEOOption4= new Button ("Edit Menu ");
            Button CEOOption5= new Button ("Register Employer");
            Button CEOOption6= new Button ("Approve Account");
            */
            
            Button CEOOption7= new Button ("Log out");
            
            setDeginButton (CEOOption1);
            setDeginButton (CEOOption7);            
            
            options_grid1.add(CEOOption1,0,0);
            options_grid1.add(CEOOption7,0,8);
            
               
                CEOOption1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                    	   Globals.loadInsideFXML(Globals.reportFXML);
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
            Button BranchManagerOption2= new Button ("View Customers");
            Button BranchManagerOption3= new Button ("View Employers");
            Button BranchManagerOption4= new Button ("Register Account");
            Button BranchManagerOption5= new Button ("Approve Suppliers");
            Button BranchManagerOption6= new Button ("Log out");
            
            setDeginButton(BranchManagerOption1);
            setDeginButton(BranchManagerOption2);
            setDeginButton(BranchManagerOption3);
            setDeginButton(BranchManagerOption4);
            setDeginButton(BranchManagerOption5);
            setDeginButton(BranchManagerOption6);
  
            options_grid1.add(BranchManagerOption1,0,0);
            options_grid1.add(BranchManagerOption2,0,1);
            options_grid1.add(BranchManagerOption3,0,2);
            options_grid1.add(BranchManagerOption4,0,3);
            options_grid1.add(BranchManagerOption5,0,4);
            options_grid1.add(BranchManagerOption6,0,8);
           

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
                    Globals.loadInsideFXML(Globals.view_employersFXML);
                }});
            
         
            BranchManagerOption4.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Globals.loadInsideFXML(Globals.regnewaccountp1FXML);
                }});
            
           
            BranchManagerOption5.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Globals.loadInsideFXML(Globals.regRestaurant);
                }});
            
            BranchManagerOption6.setOnAction(new EventHandler<ActionEvent>() {
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
    	   StartClient.order.accept("Get_my_supplier~"+OrderClient.user.getID());
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
    	   tempTypeUser="Supplier";
    	   
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
	/**
	 * getter function for returning pane_in_vbox*/
	public VBox getPane_in_vbox() {
		return pane_in_vbox;
	}
	/**
	 * setter function for setting pane_in_vbox*/
	public void setPane_in_vbox(VBox pane_in_vbox) {
		this.pane_in_vbox = pane_in_vbox;
	}

	/**
	 * getter function for returning welcome_lbl*/
	public Label getWelcome_label() {
		return welcome_label;
	}

	/**
	 * setter function for setting welcome label*/
	public void setWelcome_label(String str) {
		this.welcome_label.setText(str);
	}

	/**
	 * getter function for returning comboBoxBranch*/
	public ComboBox<Branch> getComboBoxBranch() {
		return comboBoxBranch;
	}

	/**
	 * setter function for setting  comboBoxBranch*/
	public void setComboBoxBranch(ComboBox<Branch> comboBoxBranch) {
		this.comboBoxBranch = comboBoxBranch;
	}

	/** function to set home page according to each user type*/
	public void setHomePage(){
		
		if(tempTypeUser=="Customer")
			Globals.loadInsideFXML(Globals.homePageCustomer);
		if(tempTypeUser=="CEO")
			 Globals.loadInsideFXML(Globals.homePageCeo);
	     if(tempTypeUser=="Supplier")
			 Globals.loadInsideFXML(Globals.homePageSupplier);
	     if(tempTypeUser=="Base User")
	    	 Globals.loadInsideFXML(Globals.homePageBaseUser); 
}
	/** function to set a class and styling to a button*/
	public void setDeginButton(Button b) {
		
		b.getStyleClass().add("ViewBtn");
        b.getStyleClass().add("lbl");
        b.setMaxWidth(Double.MAX_VALUE);
		
	}

}