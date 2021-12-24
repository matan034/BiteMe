package order;

import java.time.LocalDate;
import java.util.Date;
import java.util.regex.Pattern;

import common.Globals;
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
    
    @FXML
    private Label time_error;
    @FXML
    private Label phone_error;

    @FXML
    private Label company_error;

    @FXML
    private Label street_error;

    @FXML
    private Label last_name_error;

    @FXML
    private Label first_name_error;

    @FXML
    private Label city_error;

    @FXML
    private Label zip_error;
    
    private Pattern time=Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
    private Pattern namePattern=Pattern.compile("^(?=.{2,40}$)[a-zA-Z]+(?:[-'\\s][a-zA-Z]+)*$");
    private Pattern phone=Pattern.compile("^\\d{10}$");
    private Pattern address=Pattern.compile("{2,40}$");
    private Pattern zip=Pattern.compile("^\\d{7}$");
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
    	    	
    	    	check_input_not_empty();
    	    }
    	});
    	
    	createListener(hour_input,time_error);
    	createListener(first_name_input,first_name_error);
    	createListener(last_name_input,last_name_error);
    	createListener(phone_input,phone_error);
    	createListener(company_input,company_error);
    	createListener(street_input,street_error);
    	createListener(city_input,city_error);
    	createListener(zip_input,zip_error);
    	
//    	hour_input.textProperty().addListener((obs,oldValue, newValue)-> {
//    	    if(newValue!=null)
//    	    {
//    	    	time_error.setText("");
//    	    	check_input_not_empty();
//    	    }
//    	});
//    	first_name_input.textProperty().addListener((obs,oldValue, newValue)-> {
//    	    if(newValue!=null)
//    	    {
//    	    	first_name_error.setText("");
//    	    	check_input_not_empty();
//    	    }
//    	});
    	
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

    private void createListener(TextField textfield,Label label)
    {

    	textfield.textProperty().addListener((obs,oldValue, newValue)-> {
    	    if(newValue!=null)
    	    {
    	    	if(label!=null)
    	    		label.setText("");
    	    	check_input_not_empty();
    	    }
    	});
    }
    
    private void check_input_not_empty()
    {
    	int i=0;
    	if(!hour_input.getText().equals("")) i++;
    	if(date_input.getValue()!=null) i++;
    	if(Globals.newOrder.getOrder_type().equals("Delivery"))
    	{
    		if(!first_name_input.getText().equals("")) i++;
    		if(!last_name_input.getText().equals("")) i++;
    		if(!phone_input.getText().equals("")) i++;
    		if(!company_input.getText().equals("")) i++;
    		if(!street_input.getText().equals("")) i++;
    		if(!city_input.getText().equals("")) i++;
    		if(!zip_input.getText().equals("")) i++;
        	if(i==9) continue_btn.setDisable(false);
        	else if(i==8) {
        		if(company_input.getText().equals("")) {
        			continue_btn.setDisable(false);
        		}
        	}else continue_btn.setDisable(true);
        		
    	}
    	else
    	{
        	if(i==2) continue_btn.setDisable(false);
        	else continue_btn.setDisable(true);
    	}
    	
    	
    	
    	
    }
    public boolean validate_input()
    {
    	int flag=0;
    	if(!time.matcher(hour_input.getText()).matches())
    	{
    		time_error.setText("Hour must be in hh:mm format");
    		flag++;
    	}
    	if(first_name_input.getText().length()<2)
    	{
    		if(!namePattern.matcher(first_name_input.getText()).matches())
    		{
    			first_name_error.setText("First name must contain only abc chars");
    			flag++;
    		}
    	}
    	else {
    		first_name_error.setText("First name must be at least 2 chars long");
    		flag++;
    	}
    	if(last_name_input.getText().length()<2)
    	{
    		if(!namePattern.matcher(last_name_input.getText()).matches())
    		{
    			last_name_error.setText("Last name must contain only abc chars");
    			flag++;
    		}
    	}
    	else {
    		last_name_error.setText("Last name must be at least 2 chars long");
    		flag++;
    	}
  
    	if(!phone.matcher(phone_input.getText()).matches())
    	{
    		last_name_error.setText("Phone must contain only 10 numbers");
    		flag++;
    	}
    	
    	if(!company_input.getText().isEmpty())
    	{
    		if(company_input.getText().length()<2)
    		{
	    		if(!namePattern.matcher(company_input.getText()).matches())
	    		{
	    			company_error.setText("Company name must contain only abc chars");
	    			flag++;
	    		}
    		}
    		else {
    			company_error.setText("Company name must be at least 2 chars long");
    			flag++;
        	}
    	}
    	if(!address.matcher(street_input.getText()).matches())
		{
			street_error.setText("Street name must contain at least 2 chars and less than 40");
			flag++;
		}
    	if(!address.matcher(city_input.getText()).matches())
		{
			city_error.setText("City name must contain at least 2 chars and less than 40");
			flag++;
		}
    	if(!zip.matcher(zip_input.getText()).matches())
		{
			zip_error.setText("Zip must contain 7 numbers");
			flag++;
		}
    	if(flag==0)	return true;
    	return false;
    }
    @FXML
    void continueCheckout(ActionEvent event) {//validation!!!!!
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
	
    
}