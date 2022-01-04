package order;

import common.Globals;
import entity.DishInRestaurant;
import general.MyListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
/**
 * This class is for representing a dish in the branch menu controller
 * each dish load its image and set label according to dish details
 * 
 * @param dish = the pane for single dish
 * @param dish_name_label = lbl to set dish name
 * @param dish_price_label = lbl to set price 
 * @param d = saves the given dish
 * @param myListener = listener for recognize click on the dish
 * 
 * 
 * @author      Matan Weisberg
 * @version     1.0               
 * @since       01.01.2022        
 */
public class DishController {

    @FXML
    private AnchorPane dish;

    @FXML
    private Label dish_name_label;

    @FXML
    private Label dish_price_label;
    
    @FXML
    private ImageView img;
   
    private DishInRestaurant d;
    private MyListener myListener;

    /**
     *This func is for activate listener when pressing the image pane
     *set the current dish in branchMenuController
     *@param event - mouse event for pressing dish pane
     **/
    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(d);
    }

    /**
     *This func is for setting the dish details
     *@param d the given dish, used for gettin info 
     *@param menuListener used for recognize press on dish
     **/
    public void setData(DishInRestaurant d,MyListener menuListener)
    {
    	this.d=d;
    	this.myListener=menuListener;
    	dish_name_label.setText(d.getName());
    	dish_price_label.setText(d.getPrice()+Globals.currency);
    	Image image;
    	try {
    	 image = new Image(getClass().getResourceAsStream("/dishPics/"+d.getImageName()));
    	 this.img.setImage(image);    
    	} catch (Exception e) {
			// TODO: handle exception
		}
		
		       
    	
    }
}