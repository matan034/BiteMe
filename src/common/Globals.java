package common;

import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import clients.StartClient;
import controllers.RegNewAccountP1Controller;
import controllers.ClientStartController;
import controllers.IndexControllerD;
import controllers.MyListener;
import controllers.OrderClientController;
import entity.Branch;
import entity.Dish;
import entity.DishInOrder;
import entity.Order;
import entity.Supplier;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import server.ServerStart;

public class Globals {
	
	
	public static IndexControllerD index_controller;
	
	public static String currency="₪";
	public static String insertFXML="/order/InsertScreen.fxml",
			searchFXML="/order/SearchScreen.fxml",
			updateFxml="/order/UpdateScreen.fxml",
			indexFXML="/general/index2Screen.fxml",
			serverFXML="/server/serverFXML.fxml",
			userloginFXML="/general/UserLoginScreen.fxml",
			regnewaccountp1FXML="/general/RegNewAccountP1Screen.fxml",
			regnewaccountp2FXML="/general/RegNewAccountP2Screen.fxml",
			regnewemployerFXML="/general/RegNewEmployerScreen.fxml",
			changeuserstatusFXML="/general/ChangeUserStatusScreen.fxml",
			clientStartFXML="/general/ClientStartScreen.fxml",
			W4CLoginFXML="/order/W4CLoginScreen.fxml",
			ChooseBranchFXML="/order/ChooseBranchScreen.fxml",
			branch_menuFXML="/order/BranchMenuScreen.fxml",
			dish_selectionFXML="/order/DishSelectionScreen.fxml",
			supply_wayFXML="/order/chooseSupplyWayScreen.fxml",
			cartFXML="/order/CartScreen.fxml",
			delivery_detailsFXML="/order/DeliveryDetails.fxml",
			order_informationFXML="/order/OrderInformationScreen.fxml",
			paymentFXML="/order/PaymentScreen.fxml",
			order_confirmedFXML="/order/OrderConfirmedScreen.fxml",
			myOrdersFXML="/customer/MyOrdersScreen.fxml",
			reports_screenFXML = "/report/ReportsScreen.fxml",
			create_reportsFXML = "/report/CreateReport.fxml", view_reportsFXML = "/report/ViewReports.fxml",
			order_components_rating_reportFXML = "/report/OrderComponentsRating.fxml",
			performance_reportFXML = "/report/PerformanceReport.fxml",
			purchase_reportFXML = "/report/PurchaseReport.fxml",
			quarter_delay_reportFXML = "/report/QuarterlyDelaySupplyReport.fxml",
			quarter_income_reportFXML = "/report/QuarterlyIncomeReport.fxml",
			view_employersFXML="/branch_manager/MyEmployersScreen.fxml",
			reportFXML="/report/reportsScreen.fxml",
			NewOrdersFXML="/general/NewOrdersScreen.fxml";

			
	public static String regularDelivery="Private",sharedDelivery="Shared",robotDelivery="Robot - TBD";
	 public static ObservableList<String> supply_options=FXCollections.observableArrayList("Take-Away","Order-In","Delivery");
	 public static ObservableList<String> delivery_options=FXCollections.observableArrayList(regularDelivery,sharedDelivery,robotDelivery);
	 
	 public static ObservableList<Branch> branches;
	 public static ObservableList<Supplier> suppliers;
	 public static ArrayList<DishInOrder> order_dishes;
	 public static Dish selected_dish;
	 public static Order newOrder;
	 private static Image takeaway=new Image("takeaway.png");
	 private static Image delivery=new Image("delivery.jpg");
	 protected static BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
		
	 protected static BackgroundImage scaledImageTakeaway = new BackgroundImage(takeaway, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);	
	 protected static BackgroundImage scaledImageDelivery = new BackgroundImage(delivery, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);	

		
	 public static Background bTakeaway = new Background(scaledImageTakeaway);
	 public static Background bDelivery =new Background(scaledImageDelivery);
	 
	 public static int regular_delivery_fee=25,robot_delivery_fee=0;
	 public static Map<String,Integer> delivery_fee=new HashMap<>() {{
		 put(regularDelivery,regular_delivery_fee);
		 put(robotDelivery,robot_delivery_fee);
	 }};
	 
	 
	 public static void loadInsideFXML(String fxml_name)
	 {
		 try {
			 Globals.index_controller.getPaneInPane().getChildren().clear();
			 	FXMLLoader loader = new FXMLLoader();
			    loader.setLocation(Globals.class.getResource(fxml_name));
	    		AnchorPane newLoadedPane =loader.load();
	    		newLoadedPane.autosize();
	  
			   Globals.index_controller.getPaneInPane().getChildren().add(newLoadedPane);
			    
	            }
	    	catch(Exception e1) {}
	 }

	public static void loadFXML(Stage stage,String fxml_name,Event event)
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
		    }
			Scene scene=new Scene(pane);
			stage.setTitle(window_name);
			
			stage.setScene(scene);	
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		     public void handle(WindowEvent we) {
		    	 try {
		    	 if(fxml_name.equals(serverFXML)) ServerStart.closeServer();
		    	 else OrderClientController.order_client.quit();
		    	 
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
	
	
	
	public void setMainController(IndexControllerD controller)
	{
		this.index_controller=controller;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}


