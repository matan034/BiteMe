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


/**
 * Controller for updating a menu or creating a new menu here the user can view the restaurants menu and add dishes to it along with it's price its photo and the dishes options
 * if the menu is empty the user views the menu as empty and can add to it creating a new menu for it's restaurant 
 * each dish is also filtered by types (main,side etc)
 * presseing Right arrow adds a dish to menu
 * Pressing left arrow removes a dish from menu
 * to edit an existing dish user presses on dish from menu and presses edit to open the dishes settings 
 * @param save_edit_btn button to save edits to a dish
 * @param DishesList List view for dishes (left)
 * @param ChooseMenuTypeComboBox combo box for the menu type(main,side,etc...)
 * @param chooseDishMessage label above dishesList list view
 * @param edit_btn when clicking an existing dish open the edit button to enable editing the dish
 * @param dishPrice text area to input dish price
 * @param price_lbl label next to price
 * @param hasMultipleSizes checkbox for selecting multiple sizes
 * @param hasMultipleCoockingLvls checkbox for choosing if a dish has cooking levels
 * @param canAddExtras check box for if user can add extras to the dish
 * @param ImageName textfield for user to enter an image
 * @param ChooseImege button that opens file explorer to choose an image
 * @param dishesToAdd list of dishes in menu (Right side)
 * @param dishesToAddMessage label above dishesToAdd 
 * @param fileChooserMsg msg for errors in choosing a file
 * @param selected_image shows image of selected dish or image uploaded by user
 * @param rightarrw_img user clicks this to move dish between left list to right list
 * @param leftarrow_img user clicks this to move dish between right to left list
 * @param plus_img opens a popup to AddDishScreen that uses addDishController here user adds new dishes 
 * @param error_lbl label for displaying errors
 * @param add_lbl label above right arrow saying add
 * @param remove_lbl label aboe left arrow saying remove
 * @param restaurentNumber save our restaurant number by the user thats logged in
 * @param Imagebytearray byte array for images
 * @param dishType saves the dishes type (main,side)
 * @param currentDish the current this the user selected
 * @param currentDishToAdd dish that we wish to add to menu(in right side)
 * @param dishToChoose observableList that we use to update our left list 
 * @param dishToAdd observablelist we use to update our right list
 * @param dishes_to_choose an array list of dishes from leftlist
 * @param dishes_in_menu = an array list of dishes from right list
 * @param toUpdate = an array list of all dishes we wish to update
 * @param toDelete= an arraylist of all dishes we wish to delete
 * @param getAddedDish an event listener we define to communicate with adddishcontroller when user inputs a new dish
 * 

 *  @author     muhamad abu assad
 * @version     1.0               
 * @since       01.01.2022  
 **/




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
	    
	    @FXML
	    private Label add_lbl;

	    @FXML
	    private Label remove_lbl;


	    int restaurentNumber;
	
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
    
    
    /**
     * initialize our controller hidinhg fields we dont want the user to see yet, we also get the restuarants number and check that the restuarant is indeed approved
     * we also load all the dishes the restuarant currently has in menu and any dishes that are in the restaurant but not in the menu so the user can add them
     * we also define MyListener func to add new dish to observable list once a user has inputted a  new name*/
    public void initialize() {
    	
    	setListViewFieldsVisibility(false);
    	setDishFieldsVisibility(false);
    	if(OrderClient.supplier==null) error_lbl.setText("Your Restaurant Is Not Approved Yet");
    	else {
    	restaurentNumber=OrderClient.supplier.getSupplierNum(); 
    	
    	ChooseMenuTypeComboBox.setItems(Globals.dishesTypes1);
    	setToolTips();
    	dishesToAdd.setPlaceholder(new Label("No Dishes At This Category"));
    	DishesList.setPlaceholder(new Label("No Available Dishes To Add"));
    	selected_image.setStyle(" -fx-border-color: black;\r\n"
    			+ "    -fx-border-style: solid;\r\n"
    			+ "    -fx-border-width: 5;");
    	
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
    }

    /**
     * Function for opening addDishScreen as a popup, opens on the PLUS image click in this screen user can input a new dish name we recieve data back using MyListener
     * @param event A mouse event containing details about the event*/
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
    /**
     * This function is an event for choosing a dish type in the combo box 
     * once a dish type has been chosen we open visibility for the list and the labels 
     * we also update set our lists to contain the items in our observable list
     * @param event actionevent containings details about the event*/
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
    
    
    /**
     *function for initialzing a chosen dishes fields, event happens on clicking an item in left list 
     *the function goes over the dish if it has been in the menu before it remembers the that the dish has the ability to choose sides
     *cooking levels and so on this shows the user the dishes price and image aswell  if the dish is a new one the fields remain empty
     *@param event holds details about the mouse event*/
    @FXML
    void setDishFields(MouseEvent event) {
    	if(DishesList.getSelectionModel().isEmpty()) return;
    	if(DishesList.getSelectionModel().getSelectedItem().isEmpty())
    		return;
    	edit_btn.setVisible(false);
    	saveEdit_btn.setVisible(false);
    	selected_image.setImage(null);
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
    
    /**
     *function for displaying a dish that has been selected form the right list opens the edit button to the user so he can select the dish to be edited
     *@param event moust event for event details*/
    @FXML
    void displayDish(MouseEvent event) {
    	if(dishesToAdd.getSelectionModel().isEmpty()) return;
    	if(dishesToAdd.getSelectionModel().getSelectedItem().isEmpty())
    		return;
    	error_lbl.setText("");
    	selected_image.setImage(null);
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
    /**
     *function for choosing an image once an image has been chosen we convert it into byte array we also display the image chosen back to the user 
     *@param event action event details about the event
     */
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
    /**
     *Function for saving a dish activated on pressing the right arrow user visually sees the dish move from left list to right list thus showing the item has been added to the menu
     * we take all the users input about the dish and save the dish to the restaurants menu 
     * @param event MouseEvent for event details
     */
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
	    	if(currentDish==null)//update dish(from right)
	    	{
	    		dishName=currentDishToAdd.getName();
	    		dishId=currentDishToAdd.getDishID();
	    		if(Imagebytearray!=null)
	    			currentDishToAdd.setMyImegebytearray(Imagebytearray);
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
    	else if(currentDishToAdd==null)//when adding new dish (from left)
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

    /**
     *Function for deleting a dish from menu activated on left arrow press, removes an item from right list and adds it to left list
     *@param event Mouseevent containing event details
     */
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

    
    /**
     *function to save data into database
     *we take all dishes that we want to update 
     *and all dishes we wish to delete and sends them to their respective function in server where it updates in DB
     */
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

    /**
     *function to set visibility on fields relating to user input for a dish
     *@param setting = boolean value true for enabling false for disable
     */
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
    /**
     *function to set visibility on fields relating to lists
     *@param setting = boolean value true for enabling false for disable
     */
    void setListViewFieldsVisibility(boolean setting) 
    {
    	DishesList.setVisible(setting);
    	dishesToAdd.setVisible(setting);
    	chooseDishMessage.setVisible(setting);
    	dishesToAddMessage.setVisible(setting);
    	rightarrw_img.setVisible(setting);
    	leftarrow_img.setVisible(setting);
    	plus_img.setVisible(setting);

 	    add_lbl.setVisible(setting);

 	  remove_lbl.setVisible(setting);
    	
    }
    
    /**
     *function to set visibility on fields relating to lists
     *@param setting = boolean value true for enabling false for disable
     */
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
    
    /**
     *function to save edits to a dish we activate saveDish function here, saveDish knows to update the dish
     *@param event action event containing event details
     */
    @FXML
    void saveEdit(ActionEvent event) {    	 
    	saveDish(null);
    	saveEdit_btn.setVisible(false);   	  	
    }
    
    
    /**
     *event for when user presses the edit button, it gets the currenct selected dish details and sets the fields shown to user accordingly(if it has multiple cooking levels the dishes price and name and so on)
     *it also displays the dishes image to the viewer and hides and shows relevant fields
     *@param event Action event containing event details
     */
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
    /**
     *sets tooltip to plus image, rightarrow_img, and leftarrow_img 
     */
    private void setToolTips()
    {
    	  setToolTip("Add new dish to the category",plus_img);
    	  setToolTip("Add Dish to your menu, Need to select a dish from left view and fill details",rightarrw_img);
    	  setToolTip("Remove selected dish from your menu",leftarrow_img);

    }
    /**
     *creates a new tooltip with a custom message
     */
    private void setToolTip(String msg,ImageView img)
    {
    	Tooltip tooltip = new Tooltip(msg);
    	tooltip.setStyle("-fx-font-size: 14");
    	img.setPickOnBounds(true);
   	  	Tooltip.install(img, tooltip);
    }
}
