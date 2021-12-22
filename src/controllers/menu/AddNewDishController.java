package menu;

import java.awt.Button;
import java.awt.Label;
import java.awt.TextField;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import clients.OrderClient;
import clients.StartClient;
import common.Globals;
import entity.Dish;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class AddNewDishController {

    @FXML
    private TextField dishNameText;

    @FXML
    private TextField priceText;

    @FXML
    private CheckBox hasMultipleSizes;

    @FXML
    private CheckBox hasMultipleCockingLvls;

    @FXML
    private Button saveBttn;

    @FXML
    private Button addAnotherDishBttn;

    @FXML
    private Label message;

    @FXML
    private ComboBox<String> chooseDishTypeComboBox;

    @FXML
    private CheckBox canAddExtras;

    @FXML
    private TextField myRestaurantID;

    @FXML
    private Button ChooseImege;

    @FXML
    private TextField ImegeName;
    
    @FXML
    private CheckBox addDirectllyToMenu;
    
    @FXML
    private Label fileChooserMessage;
    
    private byte[] myImegebytearray;
    
    private Dish newDish;
    
    private File SelectedFile;
    
   
    public void initialize() {
    	
    	chooseDishTypeComboBox.setItems(Globals.dishesTypes);
    }

    @FXML
    void AddNewDish(ActionEvent event) {
    	String dishName=dishNameText.getText();
    	String dishPrice=priceText.getText();
    	String dishType=chooseDishTypeComboBox.getSelectionModel().getSelectedItem();
    	String imageName=ImegeName.getText();
    	if(dishName==null || dishPrice==null||dishType==null||imageName==null)
    		message.setText("The fields with * you need to fill inorder to add a new dish");
    	else {
    		int multipleSizes=0,multipleCockingLvls=0,addExtras=0,AddDirectllyToMenu=0;
    		if(addDirectllyToMenu.isSelected())
    			AddDirectllyToMenu=1;
    		if(hasMultipleSizes.isSelected())
    			multipleSizes=1;
    		if(hasMultipleCockingLvls.isSelected())
    			multipleCockingLvls=1;
    		if(canAddExtras.isSelected())
    			addExtras=1;
    		newDish=new Dish(-1,dishName,dishType,Double.parseDouble(dishPrice),multipleSizes,multipleCockingLvls,
    				addExtras,imageName,myImegebytearray,(int)myImegebytearray.length,AddDirectllyToMenu,
    				Integer.parseInt(myRestaurantID.getText()));
    		StartClient.order.accept(newDish);
    		message.setText(OrderClient.insert_New_Dish_msg);
    	}
    }
    @FXML
    void AddAnotherDish(ActionEvent event) {
    	dishNameText.setText(null);
    	priceText.setText(null);
    	chooseDishTypeComboBox.getSelectionModel().select(0);
    	ImegeName.setText(null);
    	fileChooserMessage.setText(null);
    	message.setText(null);
    	addDirectllyToMenu.setSelected(false);
    	hasMultipleSizes.setSelected(false);
    	hasMultipleCockingLvls.setSelected(false);
    	canAddExtras.setSelected(false);
    	message.setText(null);
    }
    @FXML
    void ChooseImege(ActionEvent event)  {
    	FileChooser fc=new FileChooser();
    	fc.getExtensionFilters().addAll(new ExtensionFilter("JPG Files","*.jpg"));
    	File selectedFile=fc.showOpenDialog(null);
    	if(selectedFile!=null)
    	{
    		ImegeName.setText(selectedFile.getName());
    		SelectedFile=new File (selectedFile.getAbsolutePath());
			try {
				FileInputStream fis = new FileInputStream(SelectedFile);
				BufferedInputStream bis = new BufferedInputStream(fis);
				myImegebytearray=new byte[(int)SelectedFile.length()];
				bis.read(myImegebytearray,0,myImegebytearray.length);	
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	else {fileChooserMessage.setText("File is not valid choose another one");}

    }

}
