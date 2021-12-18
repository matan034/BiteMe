package order;

import common.Globals;
import entity.Dish;
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

    private Dish d;
    private MyListener myListener;

    public void setData(Dish d,MyListener menuListener)
    {
    	this.d=d;
    	this.myListener=menuListener;
    	dish_name_label.setText(d.getName());
    	dish_price_label.setText(d.getPrice()+Globals.currency);
    	Image image = new Image(getClass().getResourceAsStream(d.getImgSrc()));
        img.setImage(image);
    }
}