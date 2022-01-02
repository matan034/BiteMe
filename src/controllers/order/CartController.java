package order;
import java.io.IOException;

import common.Globals;

import entity.DishInOrder;
import general.MyListener;
import javafx.collections.ListChangeListener;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
public class CartController {


	    @FXML
	    private VBox cart_items_vbox;

	    @FXML
	    private Label total_price_label;

	    @FXML
	    private Button checkout_btn;


	    
	  

	    private MyListener cartItemListener;
	    public void initialize()
	    {
			cartItemListener = new MyListener() {
	    		   @Override
	                public void onClickListener(Object dish) {
	    			  // Globals.newOrder.removeQuantity();
	                    Globals.newOrder.removeDish((DishInOrder)dish);
	                   
	                }  
			};
	    	setCartItems();
	    
	    	Globals.newOrder.getDishes().addListener(new ListChangeListener<DishInOrder>() { 
	    		@Override
		    	 public void onChanged(javafx.collections.ListChangeListener.Change<? extends DishInOrder> c) {
		 	          setCartItems();
	             }
	         });
	    }

	    
	    @FXML
	    void checkout(ActionEvent event) {
	    	
	    	Globals.loadInsideFXML(Globals.order_informationFXML);
	    }
	    
	    private void setCartItems()
	    {
	    	if( cart_items_vbox.getChildren()!=null)  cart_items_vbox.getChildren().clear();
	    	for(DishInOrder d : Globals.newOrder.getDishes())
	    	{
	    		 try {
	    		    	FXMLLoader fxmlLoader = new FXMLLoader();
	    	            fxmlLoader.setLocation(getClass().getResource("/order/CartItem.fxml"));
	    	            VBox anchorPane;
	    	            anchorPane = fxmlLoader.load();
	    	            CartItemController cartItemController = fxmlLoader.getController();
	    	            cartItemController.setData(d,cartItemListener);
	    	            cart_items_vbox.getChildren().add(anchorPane);
	    	            String sum=String.format("%.2f %s",Globals.newOrder.getPrice(),Globals.currency);
	    	            total_price_label.setText(sum);
	    		    	 } 
	    		    	 catch (IOException e) 
	    		    	 {
	    					e.printStackTrace();
	    		    	 }
	    	}
	    }


}
