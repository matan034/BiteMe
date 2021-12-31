package menu;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.filechooser.FileNameExtensionFilter;

import clients.OrderClient;
import clients.StartClient;
import entity.Dish;
import entity.DishInRestaurant;
import entity.MyFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AddDishToRestaurantController {

    @FXML
    private TextField priceText;

    @FXML
    private TextField ImegeName;

    @FXML
    private Button ChooseImage;

    @FXML
    private CheckBox addDirectllyToMenu;

    @FXML
    private CheckBox hasMultipleSizes;

    @FXML
    private CheckBox hasMultipleCockingLvls;

    @FXML
    private CheckBox canAddExtras;

    @FXML
    private Label fileChooserMessage;

    @FXML
    private Button add_btn;

    @FXML
    private Button addAnotherDishBttn;

    @FXML
    private Label message;

    @FXML
    private ComboBox<Dish> dish_cmb;

    private DishInRestaurant dish;
    public void initialize()
    {
    	StartClient.order.accept("Load_all_dishes");
    	dish_cmb.setItems(OrderClient.allDishes);
    }
    @FXML
    void AddAnotherDish(ActionEvent event) {

    }

    @FXML
    void ChooseImage(ActionEvent event) {//currently up to 4mb
    	dish=new DishInRestaurant(OrderClient.supplier.getSupplierNum(), 0, 0, 0, 0, 0, 0, null, null);
    	Stage stage=(Stage)((Node) event.getSource()).getScene().getWindow();
    	FileChooser fileChooser = new FileChooser();
    	//fileChooser.getExtensionFilters().addAll(new FileNameExtensionFilter(null, null)) //make it only available to choose photos
    	fileChooser.setTitle("Select Image");
    	File file = fileChooser.showOpenDialog(stage);
    	if (file != null) {
    		  try{
    			  ImegeName.setText(file.getAbsolutePath());
    			  dish.setImageName(file.getName());
    			  byte [] mybytearray  = new byte [(int)file.length()];
    			  FileInputStream fis = new FileInputStream(file);
    			  BufferedInputStream bis = new BufferedInputStream(fis);			  
    			  dish.setImageSize(mybytearray.length); 
    			  bis.read(mybytearray,0,mybytearray.length);
    			  dish.setMyImegebytearray(mybytearray);
    		  }
    			catch (Exception e) {
    				System.out.println(e);
    		}
    }
    }

    @FXML
    void addDishToRest(ActionEvent event) {
    	if(canAddExtras.isSelected()) dish.setChooseExtras(1);
    	if(hasMultipleCockingLvls.isSelected()) dish.setChooseCookingLvl(1);
    	if(hasMultipleSizes.isSelected()) dish.setChooseSize(1);
    	dish.setPrice(Double.parseDouble(priceText.getText()));
    	dish.setDishID(dish_cmb.getSelectionModel().getSelectedItem().getDishID());
    	Dish selected=dish_cmb.getSelectionModel().getSelectedItem();
    	dish.setDishID(selected.getDishID());
    	dish.setName(selected.getName());
    	dish.setType(selected.getType());
    	StartClient.order.accept(dish);
    	if(addDirectllyToMenu.isSelected())
    	{
    		StartClient.order.accept("Add_to_rest_menu~"+dish.getDishID()+"~"+dish.getRestaurantNumber());
    	}
    	message.setText(OrderClient.addDish);
    }

}
