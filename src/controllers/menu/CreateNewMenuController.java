package menu;


import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import entity.Dish;
import entity.DishInRestaurant;
import entity.clientData;
import general.MyListener;
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

import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;


public class CreateNewMenuController {
	  @FXML
	    private Button saveEdit_btn;

	 @FXML
	 private ListView<String> DishesList;



	 @FXML
	 private ComboBox<String> ChooseMenuTypeComboBox;

	 @FXML
	 private Label chooseDishMessage;

	 @FXML
	    private Button edit_btn;

	 @FXML
	 private TextField dishPrice;

	    @FXML
	    private Label price_lbl;

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
	 private ListView<String> dishesToAdd;

	 @FXML
	 private Label dishesToAddMessage;
	 
	 @FXML
	 private Label fileChooserMsg;
	 

	    @FXML
	    private ImageView selected_image;

	  @FXML
	    private ImageView rightarrw_img;

	    @FXML
	    private ImageView leftarrow_img;

	    @FXML
	    private ImageView plus_img;

	    @FXML
	    private Label error_lbl;


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
    
    ArrayList<DishInRestaurant> toUpdate=new ArrayList<DishInRestaurant>();
    ArrayList<DishInRestaurant> toDelete=new ArrayList<DishInRestaurant>();
    private MyListener getAddedDish;
    public void initialize() {
    	ChooseMenuTypeComboBox.setItems(Globals.dishesTypes1);
    	setToolTips();
    	dishesToAdd.setPlaceholder(new Label("No Dishes At This Category"));
    	DishesList.setPlaceholder(new Label("No Available Dishes To Add"));
    	selected_image.setStyle(" -fx-border-color: black;\r\n"
    			+ "    -fx-border-style: solid;\r\n"
    			+ "    -fx-border-width: 5;");
    	setListViewFieldsVisibility(false);
    	setDishFieldsVisibility(false);
    	getAddedDish=new MyListener() {
			
			@Override
			public void onClickListener(Object object) {
				String [] temp=((String)object).split("~");
				dishToChoose.add(temp[0]);
				Dishes_to_choose.add(new Dish(Integer.parseInt(temp[2]),temp[0],temp[1]));
			}
		};
    	
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
			addDishController.setListener(getAddedDish);
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
    	edit_btn.setVisible(false);
    	saveEdit_btn.setVisible(false);
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
    	dishesToAddMessage.setText("Restaurant Menu");
    	chooseDishMessage.setText("Potential Dishes");
    	DishesList.setItems(dishToChoose);
    	dishesToAdd.setItems(dishToAdd);
		
    }
    
    @FXML
    void setDishFields(MouseEvent event) {
    	if(DishesList.getSelectionModel().isEmpty()) return;
    	if(DishesList.getSelectionModel().getSelectedItem().isEmpty())
    		return;
    	edit_btn.setVisible(false);
    	saveEdit_btn.setVisible(false);
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
    	if(dishesToAdd.getSelectionModel().isEmpty()) return;
    	if(dishesToAdd.getSelectionModel().getSelectedItem().isEmpty())
    		return;
    	error_lbl.setText("");
    	setDishFieldsVisibility(false);
    	edit_btn.setVisible(true);
    	saveEdit_btn.setVisible(false);
    	resetDishFields();
    	String selected=dishesToAdd.getSelectionModel().getSelectedItem();
    	for(DishInRestaurant DIN:Dishes_in_menu)
    	{
    		if(DIN.getName().equals(selected))
    		{
    			currentDishToAdd=DIN;
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
				Image image = new Image(file.getAbsolutePath());
				
				//Setting image to the image view
		         selected_image.setImage(image);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	else {error_lbl.setText("File is not valid choose another one");}

    }
    @FXML
    void saveDish(MouseEvent event) {
    	DishInRestaurant DIR;
    	int dishId,chooseSize=0,chooseCookingLvl=0,chooseExtras=0;
    	double price;
    	
    	String imageName,dishName;

    	if(!dishPrice.getText().equals("") && !ImageName.getText().equals(""))
    	{
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
	    				chooseExtras,imageName,currentDishToAdd.getMyImagebytearray(),1,restaurentNumber);
	    		Dishes_in_menu.remove(currentDishToAdd);
	    		Dishes_in_menu.add(DIR);
	    		currentDishToAdd=DIR;
	    		dishes_to_add.put(dishType, Dishes_in_menu);
	    		toUpdate.add(DIR);
	    		error_lbl.setText("");
	    		save();
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
        		dishToChoose.remove(dishName);
        		dishesToAdd.setItems(dishToAdd);
        		
        		toUpdate.add(DIR);
        		error_lbl.setText("");
        		save();

    	}

    	}
    	
    	else error_lbl.setText("Please fill price and choose image");
    }

    @FXML
    void deleteDishFromMenu(MouseEvent event) {
    	if(dishesToAdd.getSelectionModel().getSelectedItem()==null)
    		return;
    	Dishes_in_menu.remove(currentDishToAdd);
    	
    	dishToChoose.add(currentDishToAdd.getName());
    	
    	setDishFieldsVisibility(true);
    	edit_btn.setVisible(false);
    	saveEdit_btn.setVisible(false);
    	dishToAdd.remove(currentDishToAdd.getName()); 
    	
    	toDelete.add(currentDishToAdd);
    	resetDishFields();
    	save();
    }

    
    
    private void save() {
   
    	clientData data;
    	if(!toUpdate.isEmpty())
    	{
    		data=new clientData(toUpdate,"add_to_restaurant");
        	StartClient.order.accept(data);	
    	}
    	if(!toDelete.isEmpty())
    	{
    		data=new clientData(toDelete,"remove");
        	StartClient.order.accept(data);	
    	}
    	OrderClient.dishes_in_menu.putAll(dishes_to_add);
    	setDishFieldsVisibility(false);
  
    	toDelete.clear();
    	toUpdate.clear();
    	
    }

    void setDishFieldsVisibility(boolean setting) 
    {
    	
    	ChooseImege.setVisible(setting);
    	canAddExtras.setVisible(setting);
    	hasMultipleCockingLvls.setVisible(setting);
    	hasMultipleSizes.setVisible(setting);
    	dishPrice.setVisible(setting);
    	ImageName.setVisible(setting);
    	selected_image.setVisible(setting);
    	price_lbl.setVisible(setting);
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

    @FXML
    void saveEdit(ActionEvent event) {    	 
    	saveDish(null);
    	saveEdit_btn.setVisible(false);   	  	
    }
    @FXML
    void edit(ActionEvent event) {
    	if(currentDishToAdd.getChooseSize()==1)
			hasMultipleSizes.setSelected(true);
		if(currentDishToAdd.getChooseCookingLvl()==1)
			hasMultipleCockingLvls.setSelected(true);
		if(currentDishToAdd.getChooseExtras()==1)
			canAddExtras.setSelected(true);
    	dishPrice.setText(currentDishToAdd.getPrice()+"");
    	ImageName.setText(currentDishToAdd.getImageName());
    	try {
    	 ByteArrayInputStream bis = new ByteArrayInputStream(currentDishToAdd.getMyImagebytearray());
			BufferedImage bi = ImageIO.read(bis);
			File out=new File("..\\BiteMe\\src\\gui\\dishPics\\"+currentDishToAdd.getImageName());
			String suffix=currentDishToAdd.getImageName().split("\\.")[1];
			ImageIO.write(bi, suffix, out);   
			Image image = new Image(getClass().getResourceAsStream("/dishPics/"+currentDishToAdd.getImageName()));
			 selected_image.setImage(image);
    	}catch (Exception e) {
			System.out.println("image failed");
		}
    	setDishFieldsVisibility(true);
    	saveEdit_btn.setVisible(true);   
    	
    }
    private void setToolTips()
    {
    	  setToolTip("Add new dish to the category",plus_img);
    	  setToolTip("Add Dish to your menu, Need to select a dish from left view and fill details",rightarrw_img);
    	  setToolTip("Remove selected dish from your menu",leftarrow_img);

    }
    private void setToolTip(String msg,ImageView img)
    {
    	Tooltip tooltip = new Tooltip(msg);
    	tooltip.setStyle("-fx-font-size: 14");
    	img.setPickOnBounds(true);
   	  	Tooltip.install(img, tooltip);
    }
}
