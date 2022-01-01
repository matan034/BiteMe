package menu;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import entity.Dish;
import entity.DishInRestaurant;
import entity.clientData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import order.QRSimulationController;

public class CreateNewMenuController {
	 @FXML
	 private Button saveMenu;

	 @FXML
	 private ListView<String> DishesList;

	 @FXML
	 private Label createMessageLabel;

	 @FXML
	 private ComboBox<String> ChooseMenuTypeComboBox;

	 @FXML
	 private Label chooseDishMessage;



	 @FXML
	 private TextField dishPrice;


	 @FXML
	 private CheckBox hasMultipleSizes;

	 @FXML
	 private CheckBox hasMultipleCockingLvls;

	 @FXML
	 private CheckBox canAddExtras;


	 @FXML
	 private TextField ImageName;

	 @FXML
	 private Button ChooseImege;

	 @FXML
	 private Label errorMessage;

	 @FXML
	 private ListView<String> dishesToAdd;

	 @FXML
	 private Label dishesToAddMessage;
	 
	 @FXML
	 private Label fileChooserMsg;
	 


	  @FXML
	    private ImageView rightarrw_img;

	    @FXML
	    private ImageView leftarrow_img;

	    @FXML
	    private ImageView plus_img;

	

	int restaurentNumber=OrderClient.supplier.getSupplierNum(); 
    byte[]Imagebytearray=null;
    String dishType;
    Dish currentDish;
    DishInRestaurant currentDishToAdd;
    ObservableList<String> dishToChoose;
    ObservableList<String> dishToAdd;
    ArrayList<Dish> Dishes_to_choose=new ArrayList<Dish>();
    ArrayList<DishInRestaurant> Dishes_in_menu=new ArrayList<DishInRestaurant>();
    Map<String, ArrayList<DishInRestaurant>> dishes_to_add=new HashMap<String, ArrayList<DishInRestaurant>>();

    public void initialize() {
    	ChooseMenuTypeComboBox.setItems(Globals.dishesTypes1);
    	setListViewFieldsVisibility(false);
    	setDishFieldsVisibility(false);
    	saveMenu.setDisable(true);

    	
    	dishes_to_add.put("Appetizer",new ArrayList<DishInRestaurant>());
    	dishes_to_add.put("Salad",new ArrayList<DishInRestaurant>());
    	dishes_to_add.put("Main",new ArrayList<DishInRestaurant>());
    	dishes_to_add.put("Dessert",new ArrayList<DishInRestaurant>());
    	dishes_to_add.put("Drink",new ArrayList<DishInRestaurant>());
    	
    	StartClient.order.accept("load_all_dishes~");
    	StartClient.order.accept("load_dishes_in_menu~"+restaurentNumber);
       	
    	dishes_to_add.put("Appetizer",OrderClient.dishes_in_menu.get("Appetizer"));
    	dishes_to_add.put("Salad",OrderClient.dishes_in_menu.get("Salad"));
    	dishes_to_add.put("Main",OrderClient.dishes_in_menu.get("Main"));
    	dishes_to_add.put("Dessert",OrderClient.dishes_in_menu.get("Dessert"));
    	dishes_to_add.put("Drink",OrderClient.dishes_in_menu.get("Drink"));
    }

    @FXML
    void AddDish(MouseEvent event) {
    	FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(Globals.addDish));
        VBox anchorPane;
        try {
			anchorPane = fxmlLoader.load();
			AddDishController addDishController=fxmlLoader.getController();
			addDishController.setType(ChooseMenuTypeComboBox.getSelectionModel().getSelectedItem());
			final Stage dialog = new Stage();
	        dialog.initModality(Modality.APPLICATION_MODAL);    
	        Scene dialogScene = new Scene(anchorPane);
	        dialog.setScene(dialogScene);

	        dialog.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
    }
    
    @FXML
    void chooseType(ActionEvent event) {
    	dishType=ChooseMenuTypeComboBox.getSelectionModel().getSelectedItem();
    	setListViewFieldsVisibility(true);
    	setDishFieldsVisibility(false);
    	ArrayList<String> chooseFromList=new ArrayList<String>();
    	ArrayList<String> listToAdd=new ArrayList<String>();
    	Dishes_to_choose=OrderClient.all_dishes.get(dishType);
    	Dishes_in_menu=dishes_to_add.get(dishType);
    	for(DishInRestaurant DIN:Dishes_in_menu)
    	{
    		listToAdd.add(DIN.getName());
    	}
    	for(Dish d:Dishes_to_choose)
    	{
    		if(!listToAdd.contains(d.getName()))
    			chooseFromList.add(d.getName());
    	}
    	dishToChoose=FXCollections.observableList(chooseFromList);
    	
    	dishToAdd=FXCollections.observableList(listToAdd);
    	dishesToAddMessage.setText("Dishes in menu:");
    	chooseDishMessage.setText("Choose dish:");
    	DishesList.setItems(dishToChoose);
    	dishesToAdd.setItems(dishToAdd);

    	if(dishToChoose.isEmpty())
    	{
    		errorMessage.setText("There is no availiable dishes!");
    	}
    		
    }
    
    @FXML
    void setDishFields(MouseEvent event) {
    	if(DishesList.getSelectionModel().getSelectedItem().isEmpty())
    		return;
    	setDishFieldsVisibility(true);
    	resetDishFields();
    	String selected=DishesList.getSelectionModel().getSelectedItem();
    	for(DishInRestaurant DIN:Dishes_in_menu)
    	{
    		if(DIN.getName().equals(selected))
    		{
    			currentDishToAdd=DIN;
    			if(DIN.getChooseSize()==1)
    				hasMultipleSizes.setSelected(true);
    			if(DIN.getChooseCookingLvl()==1)
    				hasMultipleCockingLvls.setSelected(true);
    			if(DIN.getChooseExtras()==1)
    				canAddExtras.setSelected(false);
    	    	dishPrice.setText(DIN.getPrice()+"");
    	    	ImageName.setText(DIN.getImageName());
    		}
    	}
    	if(currentDishToAdd==null)
    		for(Dish d:Dishes_to_choose)
        	{
        		if(d.getName().equals(selected))
        		{
        			currentDish=new Dish(d.getDishID(),d.getName(),dishType);
        		}
        	}
    	
    }
    @FXML
    void displayDish(MouseEvent event) {
    	if(dishesToAdd.getSelectionModel().getSelectedItem().isEmpty())
    		return;
    	setDishFieldsVisibility(true);
 
    	resetDishFields();
    	String selected=dishesToAdd.getSelectionModel().getSelectedItem();
    	for(DishInRestaurant DIN:Dishes_in_menu)
    	{
    		if(DIN.getName().equals(selected))
    		{
    			currentDishToAdd=DIN;
    			if(DIN.getChooseSize()==1)
    				hasMultipleSizes.setSelected(true);
    			if(DIN.getChooseCookingLvl()==1)
    				hasMultipleCockingLvls.setSelected(true);
    			if(DIN.getChooseExtras()==1)
    				canAddExtras.setSelected(false);
    	    	dishPrice.setText(DIN.getPrice()+"");
    	    	ImageName.setText(DIN.getImageName());
    		}
    	}
    }
    @FXML
    void ChooseImege(ActionEvent event) {
    	
    	FileChooser fc=new FileChooser();
    	File file;
    	fc.getExtensionFilters().addAll(new ExtensionFilter("JPG Files","*.jpg")
    			,new ExtensionFilter("PNG Files","*.png"));
    	File selectedFile=fc.showOpenDialog(null);
    	if(selectedFile!=null)
    	{
    		ImageName.setText(selectedFile.getName());
    		file=new File (selectedFile.getAbsolutePath());
			try {
				FileInputStream fis = new FileInputStream(file);
				BufferedInputStream bis = new BufferedInputStream(fis);
				Imagebytearray=new byte[(int)file.length()];
				bis.read(Imagebytearray,0,Imagebytearray.length);	
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	else {fileChooserMsg.setText("File is not valid choose another one");}

    }
    @FXML
    void saveDish(MouseEvent event) {
    	DishInRestaurant DIR;
    	int dishId,chooseSize=0,chooseCookingLvl=0,chooseExtras=0;
    	double price;
    	String imageName,dishName;
    	if(dishPrice.getText().isEmpty()||ImageName.getText().isEmpty())
    	{
    		createMessageLabel.setText("You must enter price and image for the dish!");
    		return;
    	}
    	price=Double.parseDouble(dishPrice.getText());
    	imageName=ImageName.getText();
    	if(hasMultipleSizes.isSelected())
    		chooseSize=1;
    	if(hasMultipleCockingLvls.isSelected())
    		chooseCookingLvl=1;
    	if(canAddExtras.isSelected())
    		chooseExtras=1;
    	if(currentDish==null)
    	{
    		dishName=currentDishToAdd.getName();
    		dishId=currentDishToAdd.getDishID();

    		DIR=new DishInRestaurant(dishId,dishName,dishType,price,chooseSize,chooseCookingLvl,
    				chooseExtras,imageName,Imagebytearray,1,restaurentNumber);
    		Dishes_in_menu.remove(currentDishToAdd);
    		Dishes_in_menu.add(DIR);
    		currentDishToAdd=DIR;
    		dishes_to_add.put(dishType, Dishes_in_menu);
    		
    	}
    	else if(currentDishToAdd==null)
    	{
    		dishName=currentDish.getName();
    		dishId=currentDish.getDishID();
    		DIR=new DishInRestaurant(dishId,dishName,dishType,price,chooseSize,chooseCookingLvl,
    				chooseExtras,imageName,Imagebytearray,1,restaurentNumber);
    		Dishes_in_menu.add(DIR);
    		currentDishToAdd=DIR;
    		currentDish=null;
    		dishes_to_add.put(dishType, Dishes_in_menu);
    		dishToAdd.add(dishName);
    		dishesToAdd.setItems(dishToAdd);
    	}
    	
    }

    @FXML
    void deleteDishFromMenu(MouseEvent event) {
    	if(dishesToAdd.getSelectionModel().getSelectedItem()==null)
    		return;
    	Dishes_in_menu.remove(currentDishToAdd);
    	dishes_to_add.put(dishType, Dishes_in_menu);
    	setDishFieldsVisibility(true);

    	dishesToAdd.refresh();
    	DishesList.refresh();
    	resetDishFields();
    }
    @FXML
    void saveMenu(ActionEvent event) {
    	ArrayList<DishInRestaurant> sendToServer=new ArrayList<DishInRestaurant>();
    	sendToServer.addAll(dishes_to_add.get("Appetizer"));
    	sendToServer.addAll(dishes_to_add.get("Salad"));
    	sendToServer.addAll(dishes_to_add.get("Main"));
    	sendToServer.addAll(dishes_to_add.get("Dessert"));
    	sendToServer.addAll(dishes_to_add.get("Drink"));
    	if(sendToServer.isEmpty())
    	{
    		createMessageLabel.setText("Edit or add dish to menu first!!");
    		return;
    	}
    	for(DishInRestaurant DIR:sendToServer)
    	{
    		if(OrderClient.dishes_in_menu.get(DIR.getType()).contains(DIR))
    		{
    			sendToServer.remove(DIR);
    		}
    	}
    	if(sendToServer.isEmpty())
    	{
    		createMessageLabel.setText("Edit or add dish to menu first!!");
    		return;
    	}
    	clientData data=new clientData(sendToServer,"add_to_restaurant");
    	StartClient.order.accept(data);	
    	OrderClient.dishes_in_menu.putAll(dishes_to_add);
    	setListViewFieldsVisibility(false);
    	setDishFieldsVisibility(false);
    	saveMenu.setDisable(true);
    	
    	ChooseMenuTypeComboBox.getSelectionModel().select(0);
    	createMessageLabel.setText(OrderClient.insert_dishes_to_restaurant_msg);
    }
    void setDishFieldsVisibility(boolean setting) 
    {
    	
    	ChooseImege.setVisible(setting);
    	canAddExtras.setVisible(setting);
    	hasMultipleCockingLvls.setVisible(setting);
    	hasMultipleSizes.setVisible(setting);
    	dishPrice.setVisible(setting);
    	ImageName.setVisible(setting);
    	
    }
    
    void setListViewFieldsVisibility(boolean setting) 
    {
    	DishesList.setVisible(setting);
    	dishesToAdd.setVisible(setting);
    	chooseDishMessage.setVisible(setting);
    	dishesToAddMessage.setVisible(setting);
    	rightarrw_img.setVisible(setting);
    	leftarrow_img.setVisible(setting);
    	plus_img.setVisible(setting);
  	 
    	
    }
    
    void resetDishFields()
    {
    	Imagebytearray=null;
     	currentDishToAdd=null;
    	currentDish=null;
    	canAddExtras.setSelected(false);
    	hasMultipleCockingLvls.setSelected(false);
    	hasMultipleSizes.setSelected(false);
    	dishPrice.setText("");
    	ImageName.setText("");
    	fileChooserMsg.setText("");
    }



}
