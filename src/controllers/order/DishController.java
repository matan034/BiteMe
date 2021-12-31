package order;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import common.Globals;
import entity.Dish;
import entity.DishInRestaurant;
import general.MyListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class DishController {

    @FXML
    private AnchorPane dish;

    @FXML
    private Label dish_name_label;

    @FXML
    private Label dish_price_label;
    
    @FXML
    private ImageView img;
    
    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(d);
    }

    private DishInRestaurant d;
    private MyListener myListener;

    public void setData(DishInRestaurant d,MyListener menuListener)
    {
    	this.d=d;
    	this.myListener=menuListener;
    	dish_name_label.setText(d.getName());
    	dish_price_label.setText(d.getPrice()+Globals.currency);
    	Image image = new Image(getClass().getResourceAsStream("/dishPics/"+d.getImageName()));
		this.img.setImage(image);    
		       
    	
    }
}