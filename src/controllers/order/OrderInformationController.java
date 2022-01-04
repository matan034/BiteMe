package order;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.regex.Pattern;

import common.Globals;
import general.VerifyListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
/**
 * This class is used to order information inputs for the user
 * if the selected order is for delivery screen will require extra delivery fields
 * input for time and date for all order types

 * @param hour_input = hour input from user, must be in HH:mm format
 * @param hour_tooltip = displayes hour input errors
 * @param date_input = date picker, can choose date from table or input manually need to be in dd.MM.yyyy format
 * @param date_tooltip = displayes date input errors
 * @param first_tooltip = displayes first name input errors
 * @param last_tooltip = displayes last name input errors
 * @param phone_tooltip = displayes phone input errors
 * @param company_tooltip = displayes company input errors
 * @param street_tooltip = displayes street input errors
 * @param city_tooltip = displayes city input errors
 * @param zip_tooltip =  displayes zip input errors
 * @param order_summary = lbl to set the total price of items in cart
 * @param delivery_pane = vbox showing order summary
 * @param continue_btn = button for continue to payment, only after validation
 * @param back_btn = back to menu screen
 * @param delivery_details = showing delivery price 
 * @param first_name_input = first name input from user
 * @param last_name_input = last name input from user
 * @param phone_input = phone input from user
 * @param company_input = company input from user,optional
 * @param street_input = street input from user
 * @param city_input = city input from user
 * @param zip_input = zip input from user
 * @param private_btn = radio button, selecting private delivery
 * @param delivery_type = toggle group for all delivery options
 * @param shared_btn =  radio button, selecting shared delivery
 * @param robot_btn =  radio button, selecting robot delivery - TBD
 * @param people_cnt =  counter for people in shared delivery
 * @param plus_btn =  add people to shared delivery
 * @param minus_btn = remove people from shred delivery
 * @param shared_options = hbox for all shared delivery option, for enable and disable
 * @param time = pattern to match hour input
 * @param namePattern = pattern to match name input
 * @param phone = pattern to match phone input
 * @param address = pattern to match street input
 * @param zip = pattern to match zip input
 * @param timeFormat = string for pattern DATE_PATTERN
 * @param DATE_PATTERN = pattern to match date input
 * 
 * 
 * @author      Matan Weisberg
 * @version     1.0               
 * @since       01.01.2022        
 */
public class OrderInformationController {

    @FXML
    private TextField hour_input;
    @FXML
    private Tooltip hour_tooltip;
    @FXML
    private DatePicker date_input;
    @FXML
    private Tooltip date_tooltip;
    @FXML
    private Tooltip first_tooltip;
    @FXML
    private Tooltip last_tooltip;
    @FXML
    private Tooltip phone_tooltip;
    @FXML
    private Tooltip company_tooltip;
    @FXML
    private Tooltip street_tooltip;
    @FXML
    private Tooltip city_tooltip;
    @FXML
    private Tooltip zip_tooltip;
    @FXML
    private VBox order_summary;
    @FXML
    private Pane delivery_pane;
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
    
    private Pattern time=Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
    private Pattern namePattern=Pattern.compile("^(?=.{2,40}$)[a-zA-Z]+(?:[-'\\s][a-zA-Z]+)*$");
    private Pattern phone=Pattern.compile("^\\d{10}$");
    private Pattern address=Pattern.compile("^.{2,40}$");
    private Pattern zip=Pattern.compile("^\\d{7}$");
    private String timeFormat= "^([1-9]|1[0-9]|2[0-9]|3[0-1]).([1-9]|1[0-2]).(19|20)\\d{2}$";

    private  Pattern DATE_PATTERN = Pattern.compile( timeFormat);
   
    
    /**
     *This func initializes our controller, sets the fields according to supply way
     *delivery - all deliver info
     *all types- hour and date
     *if user have no Business account only private delivery is optional
     *set listeners to all inputs
     *sets categeories button according to supplier dishes
     **/
    public void initialize()
    {
    	
    	if(Globals.newOrder.getbAccount()==null) shared_btn.setDisable(true);
    	
    	people_cnt.setText("1");
    	Globals.newOrder.setPeople_in_delivery(1);
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
    	if(Globals.newOrder.getOrder_time()!=null)
    	{
    		String[] split_date=Globals.newOrder.getOrder_time().split(" ");
    		hour_input.setText(split_date[0]);
    		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    		  //convert String to LocalDate
    		LocalDate localDate = LocalDate.parse(split_date[1], formatter);
    		date_input.setValue(localDate);
    		
    	}
    	if(Globals.newOrder.getOrder_type().equals("Delivery"))
    	{
    		delivery_details.setVisible(true);
    		if(Globals.newOrder.getRecieving_name()!=null)
    		{
    			String[] split_name=Globals.newOrder.getRecieving_name().split(" ");
    			first_name_input.setText(split_name[0]);
    			last_name_input.setText(split_name[1]);
    		}
    		if(Globals.newOrder.getPhone()!=null)
    		{
    			phone_input.setText(Globals.newOrder.getPhone());
    		}
    		if(Globals.newOrder.getBuisness_name()!=null)
    		{
    			company_input.setText(Globals.newOrder.getBuisness_name());
    		}
    		if(Globals.newOrder.getStreet()!=null)
    		{
    			street_input.setText(Globals.newOrder.getStreet());
    		}
    		if(Globals.newOrder.getCity()!=null)
    		{
    			city_input.setText(Globals.newOrder.getCity());
    		}
    		if(Globals.newOrder.getZip()!=null)
    		{
    			zip_input.setText(Globals.newOrder.getZip());
    		}
    	}
    	robot_btn.setDisable(true);
    	private_btn.setUserData("Private");
    	shared_btn.setUserData("Shared");
    	robot_btn.setUserData("Robot");

    	createListener(date_input.getEditor(), new VerifyListener() {		
			@Override
			public boolean verify() {
				return verifyDate();
			}
		});
   	
    	createListener(hour_input,new VerifyListener() {
			@Override
			public boolean verify() {
				return verifyHour();
			}
		});
    	createListener(first_name_input,new VerifyListener() {
			@Override
			public boolean verify() {
				return verifyFirstName();
			}
		});
    	createListener(last_name_input,new VerifyListener() {	
			@Override
			public boolean verify() {
				return verifyLastName();
			}
		});
    	createListener(phone_input,new VerifyListener() {	
			@Override
			public boolean verify() {
				return verifyPhone();
			}
		});
    	createListener(company_input,new VerifyListener() {		
			@Override
			public boolean verify() {
				return verifyCompany();
			}
		});
    	createListener(street_input,new VerifyListener() {		
			@Override
			public boolean verify() {
				return verifyStreet();
			}
		});
    	createListener(city_input,new VerifyListener() {		
			@Override
			public boolean verify() {
				return verifyCity();
			}
		});
    	createListener(zip_input,new VerifyListener() {		
			@Override
			public boolean verify() {
				return verifyZip();
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
    
    
    /**
     *This func is for get back to previous screen(branch menu)
     *@param event - action event for pressing back button
     **/
    @FXML
    void back(ActionEvent event) {
    	Globals.loadInsideFXML( Globals.branch_menuFXML);
    }

    /**
     *This func initializes a listener for a text field, if valid color it in green else, in red
     *@param textfield - the textfield to add the listener to 
     *@param verify - the method used to verify field, using a listener interface
     **/
    private void createListener(TextField textfield,VerifyListener verify)
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
    
    
    /**
     *This func validates all relevant input fields
     *@return true if valid, else - false
     **/
    public boolean validate_input()
    {
    	int flag=0;

    	if(!verifyDate()) flag++;
    	if(isToday() && isEarlyFromNow()) flag++;
    	if(!verifyHour()) flag++;
    	if(Globals.newOrder.getOrder_type().equals("Delivery"))
    	{
    		if(!verifyFirstName()) flag++;
        	if(!verifyLastName()) flag++;
        	if(!verifyPhone()) flag++;
        	if(!verifyCompany()) flag++;
        	if(!verifyStreet()) flag++;
        	if(!verifyCity()) flag++;
        	if(!verifyZip()) flag++;	
    	}
    	if(flag==0)	return true;
    	return false;
    }
    
    /**
     *This func is for continue checkout button, check if the input is valid
     *if it is valid calculate if order is Early order and go to next screen
     *
     *@param event - action event for pressing continue checkout button
     **/
    @FXML
    void continueCheckout(ActionEvent event) {
    	if(validate_input())
    	{
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
    }
  
    /**
     *This func checks if the user request time is early order(2 hours earlier from now)
     *@param time the user hour request
     *@param date the user date request
     *@return true if early order, else false
     **/
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
	
	 /**
     *This func checks if the given date from user is valid according to our pattern
     *if not valid set tooltip with relevant error
     *@return true if valid, else false
     **/
	private boolean verifyDate()
	{
		LocalDate today = LocalDate.now();
    	if(date_input.getEditor().getText().equals(""))
    	{
    		date_tooltip.setText("Please Choose Supply Date");
    		return false;
    	}
    	else
    	{
    		if(!DATE_PATTERN.matcher(date_input.getEditor().getText()).matches())
    		{
    			date_tooltip.setText("Date must be in dd.MM.yyyy format");
    			return false;
    		} 
    		date_input.setValue(date_input.getConverter()
    			    .fromString(date_input.getEditor().getText()));
    		if( date_input.getValue().compareTo(today) < 0 )
        	{
        		date_tooltip.setText("Cant select past date");
        		return false;
        	}
        	if(!hour_input.getText().equals("")) verifyHour();
        	date_tooltip.setText("");
        	return true;
    	}
    		
	}
	/**
     *This func checks if the given hour from user is valid according to our pattern
     *if not valid set tooltip with relevant error
     *@return true if valid, else false
     **/
	private boolean verifyHour()
	{
		
		if(hour_input.getText().equals("")) 
    	{
    		hour_tooltip.setText("Please input supply time");
    		return false;
    	}
    	else 
    	{
    		if(!time.matcher(hour_input.getText()).matches())
    		{
    			hour_tooltip.setText("Hour must be in hh:mm format");
    			return false;
    		} 
    		else 
    		{
    			if(date_input.getEditor().getText().equals(""))
    			{
    				hour_tooltip.setText("Please select date");
        			return false;
    			}
    			if(isToday() && isEarlyFromNow()) {
    				hour_tooltip.setText("Hour can't be in the past");    
    				return false;
    			}
    				
    			}
    			
    		}
    		hour_tooltip.setText("");
    		return true;
    	}
	/**
     *This func checks if the given first name from user is valid according to our pattern
     *if not valid set tooltip with relevant error
     *@return true if valid, else false
     **/
	private boolean verifyFirstName()
	{
		if(first_name_input.getText().equals("")) {
			first_tooltip.setText("Please enter first name");
    		return false;
		}
		else if(!namePattern.matcher(first_name_input.getText()).matches())
    	{
			first_tooltip.setText("First name must contain only abc chars");
    		return false;
    	}
		first_tooltip.setText("");
		return true;
	}
	/**
     *This func checks if the given last name from user is valid according to our pattern
     *if not valid set tooltip with relevant error
     *@return true if valid, else false
     **/
	private boolean verifyLastName()
	{
		if(last_name_input.getText().equals("")) {
			last_tooltip.setText("Please enter last name");
    		return false;
		}
		else if(!namePattern.matcher(last_name_input.getText()).matches())
    	{
			last_tooltip.setText("Last name must contain only abc chars");
    		return false;
    	}
		last_tooltip.setText("");
		return true;
	}
	/**
     *This func checks if the given phone from user is valid according to our pattern
     *if not valid set tooltip with relevant error
     *@return true if valid, else false
     **/
	private boolean verifyPhone()
	{
		if(phone_input.getText().equals("")) {
			phone_tooltip.setText("Please enter phone number");
    		return false;
		}
		else if(!phone.matcher(phone_input.getText()).matches())
    	{
			phone_tooltip.setText("Phone must contain only 10 numbers");
	    	return false;
    	}
		phone_tooltip.setText("");
		return true;
	}
	/**
     *This func checks if the given company from user is valid according to our pattern
     *if not valid set tooltip with relevant error
     *@return true if valid, else false
     **/
	private boolean verifyCompany()
	{
		if(!company_input.getText().equals(""))
		{
			if(!company_input.getText().equals(" "))
    		{
    			if(company_input.getText().length()>=2)
    			{
    				if(!namePattern.matcher(company_input.getText()).matches())
    				{
    					company_tooltip.setText("Company name must contain only abc chars");
    					return false;
    				}
    			}
    			else {
    				company_tooltip.setText("Company name must be at least 2 chars long");
    				return false;
    			}
    		}
		}
		company_tooltip.setText("");
		return true;
		}
				
	/**
     *This func checks if the given street from user is valid according to our pattern
     *if not valid set tooltip with relevant error
     *@return true if valid, else false
     **/
	private boolean verifyStreet()
	{
		if(street_input.getText().equals("")) 
    	{
    		street_tooltip.setText("Please enter street for delivery");
			return false;
    	}
    	else if(!address.matcher(street_input.getText()).matches())
		{
    		street_tooltip.setText("Street name must contain at least 2 chars and less than 40");
			return false;
		}
		street_tooltip.setText("");
		return true;
	}
	/**
     *This func checks if the given city from user is valid according to our pattern
     *if not valid set tooltip with relevant error
     *@return true if valid, else false
     **/
	private boolean verifyCity()
	{
		if(city_input.getText().equals("")) {
    		city_tooltip.setText("Please enter city for delivery");
			return false;
    	}
    	else if(!address.matcher(city_input.getText()).matches())
		{
    		city_tooltip.setText("City name must contain at least 2 chars and less than 40");
			return false;
		}
		city_tooltip.setText("");
		return true;
	}
	/**
     *This func checks if the given zip from user is valid according to our pattern
     *if not valid set tooltip with relevant error
     *@return true if valid, else false
     **/
	private boolean verifyZip()
	{
		if(zip_input.getText().equals("")) {
    		zip_tooltip.setText("Please enter zip for delivery");
			return false;
    	}
    	else if(!zip.matcher(zip_input.getText()).matches())
		{
    		zip_tooltip.setText("Zip must contain 7 numbers");
			return false;
		}
		zip_tooltip.setText("");
		return true;
	}
	
	/**
     *This func checks the given date from user is today
     *@return true if today, else false
     **/
	private boolean isToday()
	{
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH)+1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		return day==date_input.getValue().getDayOfMonth()&&month==date_input.getValue().getMonthValue()&&year==date_input.getValue().getYear();
	}
	/**
     *This func is called when the given date from user is today 
     *checks if the hour is early from the current hour
     *@return true if early, else false
     **/
	private boolean isEarlyFromNow()
	{
		Calendar c = Calendar.getInstance();
		String []request=hour_input.getText().split(":");
		
		return c.get( Calendar.HOUR_OF_DAY)>Integer.parseInt(request[0])||
				(c.get( Calendar.HOUR_OF_DAY)==Integer.parseInt(request[0])&&c.get(Calendar.MINUTE)>Integer.parseInt(request[1]));
		
	}
    
}