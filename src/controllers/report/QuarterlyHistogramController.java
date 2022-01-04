package report;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import entity.Branch;
import entity.Supplier;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;


/**
 * Controller for quarterlyHistogramScreen.fxml this controller handles viewing amount of orders or amount of income in a specific year,branch,quarter
 * The ceo is able to view this quartely report and also compare the data to another year,branch,quarter
 * To handle this the class uses XYChart.Series to display data in a BarChart we get data using keyword Load_quarter_data 
 * 
 *  @author      Yeela Malka
 * @version     1.0               
 * @since       01.01.2022  
 **/





public class QuarterlyHistogramController {
	
	
	/**branch_histogram BarChart that holds our data in a histogram fashion*/
    @FXML
    private BarChart<String, Number> branch_histogram;
    /**
     * branch_selected_combo combo box for selecting a branch*/
    @FXML
    private ComboBox<Branch> branch_select_combo;
    /**
     * quarter_comboBox comboBox for selecting a quarter*/
    @FXML
    private ComboBox<String> quarter_comboBox;
    /**
     * year_combobox comboBox for selecting a year*/
    @FXML
    private ComboBox<String> year_comboBox1;
    /**
     * compare_ceckbox checkbox used if user wants to ompare between reports*/
    @FXML
    private CheckBox compare_ceckbox;

    /**
     * compare_lbl lbl next to checkbox*/
    @FXML
    private Label compareTo_lbl;

    /**
     * type_selected_combo combobox for selecting type of report*/
    @FXML
    private ComboBox<String> type_select_combo;

    /**
     *grid_options grid holding options to select the second report shown once ceckbox is marked */
    @FXML
    private GridPane grid_options;
    /**
     *branch_select_combo1 combobox for selecting branch on second report */
    @FXML
    private ComboBox<Branch> branch_select_combo1;
    /**
     *quarter_combobox1 combo box for choosing quarter in second report */
    @FXML
    private ComboBox<String> quarter_comboBox1;
    /**
     * year_comboBox11 combobox for choosing year in second report*/
    @FXML
    private ComboBox<String> year_comboBox11;
    /**
     * xAxis histograms x axis*/
    @FXML
    private CategoryAxis xAxis;
    /**
     * yAxis histograms yAxis*/
    @FXML
    private NumberAxis yAxis;
    /**
     * back_btn button to go back to previous page*/
    @FXML
    private Button back_btn;
    /**
     *show_branch_btn button to show the report data */
    @FXML
    private Button show_branch_btn;
    /**
     *  observable list to display available quarters*/
    private ObservableList<String> quarters= FXCollections.observableArrayList("Q1", "Q2","Q3","Q4");
    /**
     *types observable list to display types of reports */
    private ObservableList<String> types= FXCollections.observableArrayList("Income","Order Amount");
    
    
    
    /**
     * initalizes our controller by setting all the combobox, hiding fields not relevant till user checks compare checkbox,
     * also disables show_branch_btn until the user has inputted all data nessecary we validate using an event listener that calls validate_input function */
    public void initialize()
    {
    	show_branch_btn.setDisable(true);
    	branch_select_combo.setItems(Globals.branches);
    	quarter_comboBox.setItems(quarters);
    	year_comboBox1.setItems(Globals.years);
    	type_select_combo.setItems(types);
    	branch_select_combo1.setItems(Globals.branches);
    	quarter_comboBox1.setItems(quarters);
    	year_comboBox11.setItems(Globals.years);
    	compare_ceckbox.selectedProperty().addListener((obs, oldSelection, newSelection) -> {
    		if(newSelection!=null) {
    			try {
    				
    				grid_options.setVisible(compare_ceckbox.isSelected());
    				//show_branch_btn.setDisable(compare_ceckbox.isSelected());
    				validate_input();
    			}catch(Exception e) {e.printStackTrace();}
    			
    		}
    	});
    	
    	branch_select_combo.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Branch>)defineListener(0));
    	quarter_comboBox.getSelectionModel().selectedItemProperty().addListener((ChangeListener<String>)defineListener(1));
    	year_comboBox1.getSelectionModel().selectedItemProperty().addListener((ChangeListener<String>)defineListener(1));
    	type_select_combo.getSelectionModel().selectedItemProperty().addListener((ChangeListener<String>)defineListener(1));
    	branch_select_combo1.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Branch>)defineListener(0));
    	quarter_comboBox1.getSelectionModel().selectedItemProperty().addListener((ChangeListener<String>)defineListener(1));
    	year_comboBox11.getSelectionModel().selectedItemProperty().addListener((ChangeListener<String>)defineListener(1));
    	
    	
    }
    /**
     * Method creates a Listener to validate input using the validate_input method we use this to set listeners for validation on our comboboxes
     * @param flag flag 1 for a listener on strings
     * 			   flag 2 for listener on branch(entity)
     * 
     * @return ChangeListener
     * @see #validate_input()*/
    private ChangeListener<?> defineListener(int flag)
    {
    	if(flag==1)
    	return new ChangeListener<String>() {   	 
			@Override
			public void changed(ObservableValue arg0, String arg1, String arg2) {
				 if(arg2!=null)
    	    	    {
    	    	    	validate_input();
    	    	    }
			}
    	    };  
    	  else return new ChangeListener<Branch>() {   	 
  			@Override
  			public void changed(ObservableValue arg0, Branch arg1, Branch arg2) {
  				 if(arg2!=null)
      	    	    {
      	    	    	validate_input();
      	    	    }
  			}
      	    };  
    }

   /**
    * validates user input counts if all the nesaccery combo boxes have been selected, if user compares reports he must input 7 combo boxes else he must input 4 comboboxes*/
 private void validate_input()
 {
	 int i=0;
	 
	 if(branch_select_combo.getSelectionModel().getSelectedItem()!=null) i++;
	 if(quarter_comboBox.getSelectionModel().getSelectedItem()!=null) i++;
	 if(year_comboBox1.getSelectionModel().getSelectedItem()!=null) i++;
	 if(type_select_combo.getSelectionModel().getSelectedItem()!=null) i++;
	 if(compare_ceckbox.isSelected())
	 {
		 if(branch_select_combo1.getSelectionModel().getSelectedItem()!=null) i++;
		 if(quarter_comboBox1.getSelectionModel().getSelectedItem()!=null) i++;
		 if(year_comboBox11.getSelectionModel().getSelectedItem()!=null) i++;
		 if(i==7) show_branch_btn.setDisable(false);
	 }

	 else if(i==4) show_branch_btn.setDisable(false);
 }
    /**
     * function for showing histogram to user activates on show_branch_btn press 
     * here here we define a series using the data from users combobox inputs
     * we call updateSeries to get data from DB from each users selection
     * @param event Action event for action details*/
    @FXML
    void showBranch(ActionEvent event) {
    	branch_histogram.setTitle("Orders Summary");
    	branch_histogram.getData().clear();
    	xAxis.setAnimated(false);
    	yAxis.setAnimated(false);

    	XYChart.Series series1=updateSeries(quarter_comboBox, year_comboBox1, type_select_combo, branch_select_combo);
	 
    	if(compare_ceckbox.isSelected())
    	{
    		
    		XYChart.Series series2 = updateSeries(quarter_comboBox1, year_comboBox11, type_select_combo, branch_select_combo1);
    		 branch_histogram.getData().addAll(series1,series2);
    	}
    	else  branch_histogram.getData().addAll(series1);
    	
    	}
    	
    
    /**
     * Function for getting data according to users comboBoxes we do this by calling server with Load_suppliers to first check if the restaurant exists
     * if it doesnt we return an empty series
     * if it does we get the rest of the combo box inputs calling for each supplier the server with Load_quater_data along this gets information from DB
     * and inputs it into OrderClient.income if it's income report or OrderClient.orderAmount of it's an amount of order report
     * once we have the data we set it to a series and we return that series
     * @param quarter ComboBox for choosing quarter
     * @param year ComboBox for choosing year
     * @param type ComboBox for choosing type or report(income, amount of orders)
     * @param branch ComboBox for choosing a branch
     * @return XYChar.series*/
    private XYChart.Series updateSeries( ComboBox <String>quarter,ComboBox <String>year,ComboBox<String> type,ComboBox<Branch>branch)
    {
    
    		StartClient.order.accept("Load_suppliers~"+branch.getSelectionModel().getSelectedItem().getBranchID());  
    		if(Globals.suppliers==null) {
    			xAxis.setLabel("No Restaurants");
    			return  new XYChart.Series<>();
    		}
    		else {
    			xAxis.setLabel("Restaurants");
    			int start=0,end=0;
            	switch(quarter.getSelectionModel().getSelectedItem())
            	{
            	case "Q1": start=1;end=3;break;
            	case "Q2":start=4;end=6;break;
            	case "Q3":start=7;end=9;break;
            	case "Q4":start=10;end=12;break;
            		
            	}

        	
        	XYChart.Series series1 = new XYChart.Series<>();
        	series1.setName(year.getSelectionModel().getSelectedItem()+" "+ quarter.getSelectionModel().getSelectedItem()+" "+branch.getSelectionModel().getSelectedItem());
       
       	 for(Supplier s:Globals.suppliers)
       	 {
       		  
       		 StartClient.order.accept("Load_quarter_data~"+start+"~"+end+"~"+year.getSelectionModel().getSelectedItem()+"~"+s.getSupplierNum()); 
       		 if(type.getSelectionModel().getSelectedItem().equals("Income"))
       		 {
       			 yAxis.setLabel("Income("+Globals.currency+")");
       			 series1.getData().add(new XYChart.Data(s.getName(), Double.parseDouble(OrderClient.income)));
       		 }
       		 if(type.getSelectionModel().getSelectedItem().equals("Order Amount"))
       		 {
       			 yAxis.setLabel("Orders Amount");
       			  series1.getData().add(new XYChart.Data(s.getName(), Integer.parseInt(OrderClient.orderAmount)));
       		 }		 
       		 
       		
       	 }
       	return series1;
    		}
        
   	 
    }
    
    
    
    /**
     *activates on back back button press to return to previous report screen
     *@param event Action event for event details*/
    @FXML
    void back(ActionEvent event) {
    	Globals.loadInsideFXML(Globals.reportFXML);
    }
   
}
