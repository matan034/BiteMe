package controllers;



import common.Globals;
import entity.DishInOrder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

public class DishSelectionController {

    @FXML
    private GridPane options_grid;
    @FXML
    private Button back_btn;

    @FXML
    private Button add_btn;

    ToggleGroup sizes=new ToggleGroup(),cooklevels=new ToggleGroup();
    TextField extra_input=new TextField();
    String currentSize,currentLvl,extras;
    int i=0;
    public void initialize()
    {
    	//options_grid.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
    	if(Globals.selected_dish.getChooseSize()==1)
    	{
    		Label size=new Label("Choose Size:");
    		RadioButton small=new RadioButton("Small");
    		RadioButton medium=new RadioButton("Medium");
    		RadioButton large=new RadioButton("Large");
    		small.setToggleGroup(sizes);
    		small.setSelected(true);
    		small.setUserData("Small");
    		medium.setToggleGroup(sizes);
    		medium.setUserData("Medium");
    		large.setToggleGroup(sizes);
    		large.setUserData("Large");
    		
    		options_grid.add(size,0,i);
    		options_grid.add(small, 1, i);
    		options_grid.add(medium, 2, i);
    		options_grid.add(large, 3, i);
    		i++;
    	}
    	if(Globals.selected_dish.getChooseCookingLvl()==1)
    	{
    		Label cook_level=new Label("Choose Coooking Level:");
    		RadioButton r=new RadioButton("Rare");
    		RadioButton mr=new RadioButton("Medium-Rare");
    		RadioButton m=new RadioButton("Medium");
    		RadioButton mw=new RadioButton("Medium-Well");
    		RadioButton wd=new RadioButton("Well-Done");
    		r.setUserData("Rare");
    		mr.setUserData("Medium-Rare");
    		m.setUserData("Medium");
    		mw.setUserData("Medium-Well");
    		wd.setUserData("Well-Done");
    		r.setToggleGroup(cooklevels);
    		r.setSelected(true);
    		mr.setToggleGroup(cooklevels);
    		m.setToggleGroup(cooklevels);
    		mw.setToggleGroup(cooklevels);
    		wd.setToggleGroup(cooklevels);
    		
    		options_grid.add(cook_level,0,i);
    		options_grid.add(r, 1, i);
    		options_grid.add(mr, 2, i);
    		options_grid.add(m, 3, i);
    		options_grid.add(mw,4,i);
    		options_grid.add(wd, 5, i);
    		i++;
    	}
    	if(Globals.selected_dish.getChooseExtras()==1)
    	{
    		Label extras=new Label("Extras:");
    		extra_input = new TextField();
    	
    		options_grid.add(extras, 0, i);
    		options_grid.add(extra_input, 1, i);
    	}
    	

         
    }
    @FXML
    void addToOrder(ActionEvent event) {
    	//need to get order number
    	DishInOrder dish;
    	if(i!=0)
    	{
    		if(Globals.selected_dish.getChooseSize()==1)
    			currentSize=sizes.getSelectedToggle().getUserData().toString();
    		if(Globals.selected_dish.getChooseCookingLvl()==1)
    			currentLvl=cooklevels.getSelectedToggle().getUserData().toString();
    		if(Globals.selected_dish.getChooseExtras()==1)
    			extras=extra_input.getText();
    	 dish=new DishInOrder(currentSize, currentLvl,extras ,Globals.selected_dish.getName(), Globals.selected_dish.getDishID(), 0,Globals.selected_dish.getPrice());
    	}
    	else
    		dish = new DishInOrder(null, null, null, Globals.selected_dish.getName(), Globals.selected_dish.getDishID(), 0, Globals.selected_dish.getPrice());
    	Globals.newOrder.addDish(dish);
    	Globals.loadFXML(null, Globals.branch_menuFXML, event);
    }

    @FXML
    void backToMenu(ActionEvent event) {
    	Globals.loadFXML(null, Globals.branch_menuFXML, event);
    }
}
