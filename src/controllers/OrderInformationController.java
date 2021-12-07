package controllers;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import common.Globals;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class OrderInformationController {

    @FXML
    private VBox order_summary;

    @FXML
    private Pane delivery_pane;

    @FXML
    private DatePicker date_input;

    @FXML
    private TextField hour_input;

    @FXML
    private Button continue_btn;

    @FXML
    private Button back_btn;

    @FXML
    private GridPane delivery_details;

    @FXML
    private TextField first_name_input;

    @FXML
    private TextField last_name_input;

    @FXML
    private TextField phone_input;

    @FXML
    private TextField company_input;

    @FXML
    private TextField street_input;

    @FXML
    private TextField city_input;

    @FXML
    private TextField zip_input;

    @FXML
    private RadioButton private_btn;

    @FXML
    private ToggleGroup delivery_type;

    @FXML
    private RadioButton shared_btn;

    @FXML
    private RadioButton robot_btn;

    public void initialize()
    {
    	if(Globals.newOrder.getOrder_type().equals("Delivery"))
    	{
    		delivery_details.setVisible(true);
    	}
    	robot_btn.setDisable(true);
    	private_btn.setUserData("Private");
    	shared_btn.setUserData("Shared");
    	robot_btn.setUserData("Robot");
    }
    @FXML
    void back(ActionEvent event) {
    	Globals.loadFXML(null, Globals.cartFXML, event);
    }

    @FXML
    void continueCheckout(ActionEvent event) {//validation!!!!!
    	
    	if(Globals.newOrder.getOrder_type().equals("Delivery"))
    	{
    		addDeliveryPrice(delivery_type.getSelectedToggle().getUserData().toString());
    		Globals.newOrder.setRecieving_name(first_name_input.getText()+" "+last_name_input.getText());
    		Globals.newOrder.setDelivery_method(delivery_type.getSelectedToggle().getUserData().toString());
    		Globals.newOrder.setCity(city_input.getText());
    		Globals.newOrder.setStreet(street_input.getText());
    		Globals.newOrder.setPhone(phone_input.getText());
    		Globals.newOrder.setZip(zip_input.getText());
        	Globals.newOrder.setBuisness_name(company_input.getText());	
    	}
    	String time=hour_input.getText();
    	LocalDate date = date_input.getValue();
    	Globals.newOrder.setOrder_time(time+" "+date);
    	if(checkIfEarlyOrder(time,date))
    	{
    		Globals.newOrder.setPrice(Globals.newOrder.getPrice()*0.9);
    	}
    	Globals.loadFXML(null, Globals.paymentFXML, event);
    
    }
    
  
	@SuppressWarnings("deprecation")
	private boolean checkIfEarlyOrder(String time,LocalDate date)
    {
    	int requestedH,requestedMin,requestedD,requestedMon,requestedY;
    	Date date1 = new Date();
    	String[] requestedTime=time.split(":");//[0]=hour , [1]=minutes
    	requestedH=Integer.parseInt(requestedTime[0]);
    	requestedMin=Integer.parseInt(requestedTime[1]);
    	requestedD=date.getDayOfMonth();
    	requestedMon=date.getMonthValue();
    	requestedY=date.getYear();
    	Date requested=new Date(requestedY-1900, requestedMon-1, requestedD, requestedH, requestedMin);
    		
    	requested.setSeconds(0);
    	date1.setSeconds(0);
    	
    	long diff =requested.getTime()-date1.getTime();
    	long two_hours=7200000;
    	if(diff>two_hours) {
    		Globals.newOrder.setIs_early_order(1);
    		return true;
    	}
    	else 
    	{
    		Globals.newOrder.setIs_early_order(0);
    		return false;
    	}
	     
    }
	private void addDeliveryPrice(String delivery_method)
	{
		switch(delivery_method)
		{
			case "Private": Globals.newOrder.setPrice(Globals.newOrder.getPrice()+25); break;
			case "Shared":
			case "Robot": break;
		}
	}
    
}