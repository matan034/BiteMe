package common;

import java.io.IOException;


import controllers.RegNewAccountP1Controller;

import controllers.OrderClientController;
import entity.Branch;
import entity.Dish;
import entity.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Globals {
	public static String insertFXML="/order/InsertScreen.fxml",
			searchFXML="/order/SearchScreen.fxml",
			updateFxml="/order/UpdateScreen.fxml",
			indexFXML="/order/IndexScreen.fxml",
			serverFXML="/server/serverFXML.fxml",
			//loginFXML="/general/LoginScreen.fxml",
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
			order_confirmedFXML="/order/OrderConfirmedScreen.fxml";

	
	 public static ObservableList<String> supply_options=FXCollections.observableArrayList("Take-Away","Order-In","Delivery");
	 public static ObservableList<String> delivery_options=FXCollections.observableArrayList("Private","Shared","Robot - TBD");
	 
	 public static ObservableList<Branch> branches;
	 public static Dish selected_dish;
	 public static Order newOrder;
	 
	 private static Image takeaway=new Image("takeaway.png");
	 private static Image delivery=new Image("delivery.jpg");
	 protected static BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
		
	 protected static BackgroundImage scaledImageTakeaway = new BackgroundImage(takeaway, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);	
	 protected static BackgroundImage scaledImageDelivery = new BackgroundImage(delivery, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);	

		
	 public static Background bTakeaway = new Background(scaledImageTakeaway);
	 public static Background bDelivery =new Background(scaledImageDelivery);

	public static void loadFXML(Stage stage,String fxml_name,ActionEvent event)
	{
		String window_name=fxml_name.split("/")[2].split("\\.")[0];
		Pane pane;
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
			Scene scene=new Scene(pane);
			stage.setTitle(window_name);
			stage.setScene(scene);	
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		     public void handle(WindowEvent we) {
		    	 try {OrderClientController.order_client.quit();
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

}


