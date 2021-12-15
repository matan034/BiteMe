package controllers;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import common.Globals;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    
    @FXML
    private Label people_cnt;

    @FXML
    private Button plus_btn;

    @FXML
    private Button minus_btn;

    @FXML
    private HBox shared_options;

    public void initialize()
    {
    	
    	continue_btn.setDisable(true);
    	class peopleCounter implements EventHandler<ActionEvent>{

    		private int mul;
    	
			public peopleCounter(int mul) {
				super();
				this.mul = mul;
			}
			@Override
			public void handle(ActionEvent arg0) {
				
				int temp=Integer.parseInt(people_cnt.getText());
				if((mul==-1&& temp>1)|| mul==1)
					temp+=1*mul;
				people_cnt.setText(Integer.toString(temp));
				
			}
    		
    	}
    	
    	plus_btn.setOnAction(new peopleCounter(1));
    	minus_btn.setOnAction(new peopleCounter(-1));
    	if(Globals.newOrder.getOrder_type().equals("Delivery"))
    	{
    		delivery_details.setVisible(true);
    	}
    	robot_btn.setDisable(true);
    	private_btn.setUserData("Private");
    	shared_btn.setUserData("Shared");
    	robot_btn.setUserData("Robot");
    	date_input.valueProperty().addListener((obs,oldValue, newValue)-> {
    	    if(newValue!=null)
    	    {
    	    	validate_input();
    	    }
    	});
    	hour_input.textProperty().addListener((obs,oldValue, newValue)-> {
    	    if(newValue!=null)
    	    {
    	    	validate_input();
    	    }
    	});
    	
    	delivery_type.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
    	    public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

    	         if (delivery_type.getSelectedToggle().equals(shared_btn)) {
    	        	 shared_options.setDisable(false);
    	         }
    	         if (delivery_type.getSelectedToggle().equals(private_btn)) {
    	        	 shared_options.setDisable(true);
    	         }
    	     } 
    	});
    }
    @FXML
    void back(ActionEvent event) {
    	Globals.loadInsideFXML( Globals.branch_menuFXML);
    }

    private void validate_input()
    {
    	int i=0;
    	if(!hour_input.getText().equals("")) i++;
    	if(date_input.getValue()!=null) i++;
    	if(i==2) continue_btn.setDisable(false);
    	else continue_btn.setDisable(true);
    	
    }
    @FXML
    void continueCheckout(ActionEvent event) {//validation!!!!!
    	
    	if(Globals.newOrder.getOrder_type().equals("Delivery"))
    	{
    		String delivery_method=delivery_type.getSelectedToggle().getUserData().toString();	
    		Globals.newOrder.setRecieving_name(first_name_input.getText()+" "+last_name_input.getText());
    		Globals.newOrder.setDelivery_method(delivery_method);
    		Globals.newOrder.setCity(city_input.getText());
    		Globals.newOrder.setStreet(street_input.getText());
    		Globals.newOrder.setPhone(phone_input.getText());
    		Globals.newOrder.setZip(zip_input.getText());
        	Globals.newOrder.setBuisness_name(company_input.getText());
        	if(delivery_method.equals("Shared"))
        	{
        		Globals.newOrder.setPeople_in_delivery(Integer.parseInt(people_cnt.getText()));
        	}
    		
    	}
    	String time=hour_input.getText();
    	LocalDate date = date_input.getValue();
    	Globals.newOrder.setOrder_time(time+" "+date);
    	checkIfEarlyOrder(time,date);
    	Globals.loadInsideFXML( Globals.paymentFXML);
    
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
	
    
}