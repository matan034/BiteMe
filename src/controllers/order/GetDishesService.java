package order;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import javax.imageio.ImageIO;

import clients.OrderClient;
import entity.DishInRestaurant;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class GetDishesService extends Service<DishInRestaurant> {

    /**
     * Create and return the task for fetching the data. Note that this method
     * is called on the background thread (all other code in this application is
     * on the JavaFX Application Thread!).
     */
    @Override
    protected Task createTask() {
    	 return new Task<Void>() {
             @Override
             protected Void call() throws Exception {
            	 int amount=0;
            	 for (Map.Entry<String,ArrayList<DishInRestaurant>> entry : OrderClient.branch_menu.entrySet())
    			 {
    				 for(DishInRestaurant dish:entry.getValue())
    				 {
    					 amount++;					
    				 }
    			 }
            	 int i=0;
 
               
            		 for (Map.Entry<String,ArrayList<DishInRestaurant>> entry : OrderClient.branch_menu.entrySet())
        			 {
        				 for(DishInRestaurant dish:entry.getValue())
        				 {
        					 updateProgress(i, amount);
        					 i++;
        					 try {
        						 ByteArrayInputStream bis = new ByteArrayInputStream(dish.getMyImagebytearray());
        							BufferedImage bi = ImageIO.read(bis);
        							File out=new File("..\\BiteMe\\src\\gui\\dishPics\\"+dish.getImageName());
        							String suffix=dish.getImageName().split("\\.")[1];
        							ImageIO.write(bi, suffix, out);   
        						 } catch (Exception e) {
        							
        						}					
        				 }
        			 }
					return null;
            

        
             }
         };
    } 
    
    

}
