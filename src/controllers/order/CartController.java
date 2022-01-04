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

/**
 * This class is used to show our cart screen
 *loads a list of all dishes in order
 * user can remove items from cart
 * 
 * @author      Matan Weisberg
 * @version     1.0               
 * @since       01.01.2022        
 */
public class CartController {


	/**
	 *cart_items_vbox = main vbox used to load each dish in order */
	    @FXML
	    private VBox cart_items_vbox;
	    /**
	     *total_price_label = lbl sets the price of all items */
	    @FXML
	    private Label total_price_label;

	    /**
	     * checkout_btn = button for proceeding to checkout*/
	    @FXML
	    private Button checkout_btn;
	    /**
	     * cartItemListener = used for remove item option*/
	    private MyListener cartItemListener;
	    
	    
	    /**
	     *This func initializes our controller, sets the cart items
	     *each cart items is an appearance of CartItemScreen
	     *sets a listener for removing dish from cart
	     **/
	    public void initialize()
	    {
			cartItemListener = new MyListener() {
	    		   @Override
	                public void onClickListener(Object dish) {
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

	    /**
	     *This func used for proceed to next screen (Order Information)
	     *@param event action event for pressing checkout button
	     **/
	    @FXML
	    void checkout(ActionEvent event) {
	    	
	    	Globals.loadInsideFXML(Globals.order_informationFXML);
	    }
	    
	    /**
	     *This func used for setting the cart list loads cart item screen for each dish
	     *
	     **/ 
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
