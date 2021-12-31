package order;

import java.time.LocalDate;
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
    public void initialize()
    {
    	
    	if(Globals.newOrder.getpAccount()==null) shared_btn.setDisable(true);
    	
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
    @FXML
    void back(ActionEvent event) {
    	Globals.loadInsideFXML( Globals.branch_menuFXML);
    }

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
	
	private boolean verifyDate()
	{
		LocalDate today = LocalDate.now();
    	if(date_input.getEditor().getText().equals(""))
    	{
    		date_tooltip.setText("Please Choose Supply Date");
    		return false;
    	}
    	else if( date_input.getValue().compareTo(today) < 0 )
    	{
    		date_tooltip.setText("Cant select past date");
    		return false;
    	}
    	date_tooltip.setText("");
    	return true;
	}
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
	private boolean verifyCompany()
	{
		if(!company_input.getText().isEmpty())
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
		company_tooltip.setText("");
		return true;
	}
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
	private boolean isToday()
	{
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH)+1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		return day==date_input.getValue().getDayOfMonth()&&month==date_input.getValue().getMonthValue()&&year==date_input.getValue().getYear();
	}
	private boolean isEarlyFromNow()
	{
		Calendar c = Calendar.getInstance();
		String []request=hour_input.getText().split(":");
		 
		return c.get(Calendar.HOUR)>Integer.parseInt(request[0])||
				(c.get(Calendar.HOUR)==Integer.parseInt(request[0])&&c.get(Calendar.MINUTE)>Integer.parseInt(request[1]));
		
	}
    
}