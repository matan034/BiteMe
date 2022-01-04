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



/**
 * This class is a Service Class used for loading all dishes images from their byte arrays
 * uses for the progress indicator in BranchMenuController
 * 
 * 
 * @author      Matan Weisberg
 * @version     1.0               
 * @since       01.01.2022        
 */
public class GetDishesService extends Service<DishInRestaurant> {

    /**
     * Create and return the task for fetching the data. Note that this method
     * is called on the background thread (all other code in this application is
     * on the JavaFX Application Thread!).
     * @return Task the progress of loading images
     */
    @Override
    protected Task createTask() {
    	 return new Task<Void>() {
             @Override
             protected Void call() throws Exception {
            	 int amount=0;
            	 for (Map.Entry<String,ArrayList<DishInRestaurant>> entry : OrderClient.branch_menu.entrySet())
    			 {
            		 if(!entry.getValue().isEmpty())
            			 for(DishInRestaurant dish:entry.getValue())
            			 {
            				 amount++;					
            			 }
    			 }
            	 int i=0;
               if(amount!=0)// if there are dishes
               {
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
        							bis.close();
        							bi.flush();
        							
        						 } catch (Exception e) {
        							System.out.println(e);
        						}					
        				 }
        			 }
               }
               return null;
        
             }
         };
    } 
    
    

}
