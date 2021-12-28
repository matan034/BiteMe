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

public class QuarterlyHistogramController {

    @FXML
    private BarChart<String, Number> branch_histogram;

    @FXML
    private ComboBox<Branch> branch_select_combo;
    @FXML
    private ComboBox<String> quarter_comboBox;
    
    @FXML
    private ComboBox<String> year_comboBox1;
    
    @FXML
    private CheckBox compare_ceckbox;

    @FXML
    private Label compareTo_lbl;

    @FXML
    private ComboBox<String> type_select_combo;


    @FXML
    private GridPane grid_options;

    @FXML
    private ComboBox<Branch> branch_select_combo1;

    @FXML
    private ComboBox<String> quarter_comboBox1;

    @FXML
    private ComboBox<String> year_comboBox11;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private Button show_branch_btn;

    private ObservableList<String> quarters= FXCollections.observableArrayList("Q1", "Q2","Q3","Q4");
    private ObservableList<String> years= FXCollections.observableArrayList("2021");
    private ObservableList<String> types= FXCollections.observableArrayList("Income","Order Amount");
    public void initialize()
    {
    	show_branch_btn.setDisable(true);
    	branch_select_combo.setItems(Globals.branches);
    	quarter_comboBox.setItems(quarters);
    	year_comboBox1.setItems(years);
    	type_select_combo.setItems(types);
    	branch_select_combo1.setItems(Globals.branches);
    	quarter_comboBox1.setItems(quarters);
    	year_comboBox11.setItems(years);
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
    	
    
    
    private XYChart.Series updateSeries( ComboBox <String>quarter,ComboBox <String>year,ComboBox<String> type,ComboBox<Branch>branch)
    {
    
    		StartClient.order.accept("Load_suppliers~"+branch.getSelectionModel().getSelectedItem().getBranchID());   	
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
