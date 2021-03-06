package common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import clients.OrderClient;
import clients.StartClient;
import general.IndexControllerD;
import general.OrderClientController;
import general.VerifyListener;
import entity.Branch;
import entity.Dish;
import entity.DishInOrder;
import entity.Order;
import entity.Supplier;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import server.ServerStart;

public class Globals {
	
	
	public static IndexControllerD index_controller;
	public static double companyCommission=0.12;
	public static String currency="₪";
	public static String insertFXML="/order/InsertScreen.fxml",
			searchFXML="/order/SearchScreen.fxml",
			updateFxml="/order/UpdateScreen.fxml",
			indexFXML="/general/index2Screen.fxml",
			serverFXML="/server/serverFXML.fxml",
			userloginFXML="/general/UserLoginScreen.fxml",
			regnewaccountp1FXML="/branch_manager/RegNewAccountP1Screen.fxml",
			regnewaccountp2FXML="/branch_manager/RegNewAccountP2Screen.fxml",
			regnewemployerFXML="/hr/RegNewEmployerScreen.fxml",
			changeuserstatusFXML="/branch_manager/ChangeUserStatusScreen.fxml",
			clientStartFXML="/general/ClientStartScreen.fxml",
			W4CLoginFXML="/order/W4CLoginScreen.fxml",
			ChooseSupplierFXML="/order/ChooseSupplierScreen.fxml",
			branch_menuFXML="/order/BranchMenuScreen.fxml",
			dish_selectionFXML="/order/DishSelectionScreen.fxml",
			cartFXML="/order/CartScreen.fxml",
			delivery_detailsFXML="/order/DeliveryDetails.fxml",
			order_informationFXML="/order/OrderInformationScreen.fxml",
			paymentFXML="/order/PaymentScreen.fxml",
			order_confirmedFXML="/order/OrderConfirmedScreen.fxml",
			myOrdersFXML="/customer/MyOrdersScreen.fxml",
			reports_screenFXML = "/report/ReportsScreen.fxml",
			create_reportsFXML = "/report/CreateReport.fxml", 
			view_reportsFXML = "/report/ViewReports.fxml",
			order_components_rating_reportFXML = "/report/OrderComponentsRating.fxml",
			performance_reportFXML = "/report/PerformanceReport.fxml",
			quarter_delay_reportFXML = "/report/QuarterlyDelaySupplyReport.fxml",
			quarter_income_reportFXML = "/report/QuarterlyIncomeReport.fxml",
			view_employersFXML="/branch_manager/MyEmployersScreen.fxml",
			reportFXML="/report/reportsScreen.fxml",
			NewOrdersFXML="/resturant/NewOrdersScreen.fxml",
			approveUserFXML="/hr/ApproveUserScreen.fxml",
			paymentStatusFXML="/order/PaymentStatusScreen.fxml",
			ceo_chooses_quarter_and_monthFXML="/report/CeoChooses.fxml",
			ceo_view_the_report_she_chose="/report/ceoQuarterReportOfOrdersNumAndIncomes.fxml",
		    income_reportFXML = "/report/IncomeReport.fxml",
			dishFXML="/menu/Dishes.fxml",
			createMenu="/menu/createMenu.fxml",
			viewMenu="/menu/ViewMyMenus.fxml",
			viewDishes="/menu/ViewMyDishes.fxml",
			regRestaurant="/branch_manager/RegisterRestaurant.fxml",
			quarterlyHistogram="/report/QuarterlyHistogramScreen.fxml",
			viewPdf="/report/ViewPdfScreen.fxml",
			intakeReport="/report/IntakeReportScreen.fxml",
			addDish="/menu/AddDishScreen.fxml",
			addDishToRest="/menu/AddDishToRestaurant.fxml",
			homeScreen="/general/HomeScreen.fxml",


			MenuFXML="/menu/createMenu.fxml",


			MenuFXML="/menu/createMenu.fxml",

	        homePageCustomer="/general/homePageCustomer.fxml",
	        homePageCeo="/general/homePageCeo.fxml";

	

	private static String[] types={"Salad","Appetizer","Main Dish","Dessert","Drink"};
	private static String[] types1={"Salad","Appetizer","Main","Dessert","Drink"};
	public static String dishType;
	public static ObservableList<String> dishesTypes=FXCollections.observableArrayList(types);
	public static ObservableList<String> dishesTypes1=FXCollections.observableArrayList(types1);
	public static String regularDelivery="Private",sharedDelivery="Shared",robotDelivery="Robot - TBD";
	 public static ObservableList<String> supply_options=FXCollections.observableArrayList("Take-Away","Order-In","Delivery");
	 public static ObservableList<String> delivery_options=FXCollections.observableArrayList(regularDelivery,sharedDelivery,robotDelivery);
	 public static String AccountInfoArr[];
	 
	 public static ObservableList<Branch> branches;
	 public static ObservableList<Supplier> suppliers;
	 public static ArrayList<DishInOrder> order_dishes;
	 public static Dish selected_dish;
	 public static Order newOrder;

	 public static int regular_delivery_fee=25,robot_delivery_fee=0;
	 public static Map<String,Integer> delivery_fee=new HashMap<String,Integer>() {{
		 put(regularDelivery,regular_delivery_fee);
		 put(robotDelivery,robot_delivery_fee);
	 }};
	 public static Map<String,String> titles=new HashMap<String,String>() {{
		 put(W4CLoginFXML,"W4C Login");
		 put(ChooseSupplierFXML,"Restaurant");
		 put(branch_menuFXML,"Menu");
		 put(order_informationFXML,"Order Details");
		 put(paymentFXML,"Payment Method");
		 put(order_confirmedFXML,"Order Confirmation");
		 put(myOrdersFXML,"My Orders");
		 put(changeuserstatusFXML,"Users");
		 put(regnewaccountp1FXML,"Register New Account");
		 put(regnewaccountp2FXML,"Register New Account");
		 put(quarterlyHistogram,"Quarter Report");
		 put(reportFXML,"Reports");
		 put(view_reportsFXML,"Monthly Report");
		 put(income_reportFXML,"Income Report");
		 put(performance_reportFXML,"Performance Report");
		 put(order_components_rating_reportFXML,"Dishes Type Report");
		 put(viewPdf,"Inclusive Quarter Report");
		 put(view_employersFXML,"My Employers");
		 put(homePageCustomer,"Welcome to BiteME");
		 put(homePageCeo,"Welcome to BiteME");
		 put(MenuFXML,"Edit menu");
		 put(regnewemployerFXML,"Register new employer");
	 }};
	 
	 public static HostServices host_service;
	 
	 
	    /*@param Func for load Vbox inside Vbox  
		/*@param  input: name of the fxml file we want to load into the main fxml file*/
	 
	 public static void loadInsideFXML(String fxml_name)
	 {
		 try {
			 Globals.index_controller.getPane_in_vbox().getChildren().clear();
			 	FXMLLoader loader = new FXMLLoader();
			 	VBox newVbox;
			    loader.setLocation(Globals.class.getResource(fxml_name));
			    newVbox=loader.load();  
			    VBox.setVgrow( newVbox, Priority.ALWAYS);
			    index_controller.getPane_in_vbox().getChildren().add(newVbox);
			    index_controller.setWelcome_label(titles.get(fxml_name));
			   

	            }
	    	catch(Exception e1) {}
	 }

	 
	public static void loadFXML(Stage stage,String fxml_name,Event event,HostServices hostServices)
	{
		String window_name=fxml_name.split("/")[2].split("\\.")[0];
		VBox pane;
		try {
		((Node) event.getSource()).getScene().getWindow().hide(); //hiding primary window
		}
		catch(Exception e) {}
		if(stage==null) 
			 stage= new Stage();
		try {
		    FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(Globals.class.getResource(fxml_name));
		   
		    pane = loader.load();
		    if(fxml_name.equals(Globals.indexFXML)) {
		    	index_controller=loader.getController();
		    	
		    	stage.getIcons().add(new Image("/img/app_logo.png"));
		    }
		    if(fxml_name.equals(Globals.clientStartFXML)) {
		    	host_service= hostServices;
		    }
			Scene scene=new Scene(pane);
			stage.setTitle(window_name);
			stage.setScene(scene);	
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		     public void handle(WindowEvent we) {
		    	 try {
		    	
		    	 if(fxml_name.equals(serverFXML)) ServerStart.closeServer();
		    	 else {
		    		 StartClient.order.accept("Logout~"+OrderClient.user.getID());
		    		 OrderClientController.order_client.quit();
		    	 }
		    	 
		    	 System.out.println("Stage is closing");}
		    	 catch(Exception e) {}
		    	 	
		          }
		      });        
			stage.show();
		} catch (IOException e) {
		    e.printStackTrace();
		    return;
		}
	}
	
	
	public static void VerifyInputListener(TextField textfield,VerifyListener verify)
    {
    	textfield.focusedProperty().addListener((obs,oldValue, newValue)-> {
    		if(newValue)//here we get focus in textfield
    		{
    			if ( textfield.getStyleClass().contains("error")) {
	    			textfield.getStyleClass().removeAll(Collections.singleton("error"));
	    		}
    			if (  textfield.getStyleClass().contains("success")) {
	    			textfield.getStyleClass().removeAll(Collections.singleton("success"));
	    		}
    		}
    	    if(!newValue)//here we focused out
    	    {
    	    	if(verify.verify())
    	    	{
    	    		if ( textfield.getStyleClass().contains("error")) {
    	    			textfield.getStyleClass().removeAll(Collections.singleton("error"));
    	    		}
    	    		 if (!  textfield.getStyleClass().contains("success")) {
    	    			 textfield.getStyleClass().add("success");
    	    		 }
    	    	}
    	    	else
    	    	{
    	    		if (  textfield.getStyleClass().contains("success")) {
    	    			textfield.getStyleClass().removeAll(Collections.singleton("success"));
    	    		}
    	    		if (!  textfield.getStyleClass().contains("error")) {
    	    			textfield.getStyleClass().add("error");
    	    		} 	
    	    	}
    	    }
    	});
    }
	
	
	
	
	
	
	
	
	
	
	
	
	

}


