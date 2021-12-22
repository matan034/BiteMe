package server;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;



import entity.Dish;
import ocsf.server.ConnectionToClient;

public class DBMenuController {

	
	protected void insertNewDish(Dish newDish,ConnectionToClient client,Connection myCon,DBController db)
	{
		Statement stmt;
		ResultSet dishesID,restaurantID,rs;
		String addDish,addDishToMenu,addToRestaurant;
		PreparedStatement preparedStmt;
		
		  try {
			  stmt = myCon.createStatement();
			  dishesID =stmt.executeQuery(String.format("SELECT DishID FROM biteme.dishes WHERE DishName='%s'",newDish.getName()));
			  while(dishesID.next())
			  {
				  restaurantID=stmt.executeQuery(String.format("SELECT restaurantNumber FROM biteme.dishinrestaurant WHERE DishID='%d'",dishesID.getInt(1)));
				  while(restaurantID.next())
					  if(restaurantID.getInt(1)==newDish.getRestaurantNumber())
					  {
						  db.sendToClient("insert_New_Dish_msg~The dish "+newDish.getName()+" has been already added to your restaurant.",client);
						  return;
					  }
			
			  }
			  addDish="INSERT INTO biteme.dishes "
			  		+ "(DishName,DishType,Price,ChooseSize,ChooseCookingLevel,ChooseExtras,DishImage) VALUES"
			  		+ "(?,?,?,?,?,?,?);";
			  addDishToMenu="INSERT INTO biteme.dishinmenu (DishID,MenuID) VALUES (?,?);";
			  addToRestaurant="INSERT INTO biteme.dishinrestaurant (restaurantNumber,DishID) VALUES (?,?);";
			  String imegeLink="C:\\Users\\m7mad\\OneDrive\\Documents\\mohammad\\shetot\\server\\"+newDish.getImageName();
			  File dishImege=new File(imegeLink);
			  FileOutputStream fos = new FileOutputStream(dishImege);
			  BufferedOutputStream bos = new BufferedOutputStream(fos);
			  bos.write(newDish.myImegebytearray, 0,newDish.getImageSize());
			  bos.close();
			  preparedStmt = myCon.prepareStatement(addDish);
			  preparedStmt.setString(1,newDish.getName());
			  preparedStmt.setString(2,newDish.getType());
			  preparedStmt.setDouble(3,newDish.getPrice());
			  preparedStmt.setInt(4,newDish.getChooseSize());
			  preparedStmt.setInt(5,newDish.getChooseCookingLvl());
			  preparedStmt.setInt(6,newDish.getChooseExtras());
			  preparedStmt.setString(7,imegeLink);
			  preparedStmt.execute();
			  rs=stmt.executeQuery("SELECT last_insert_id()");
			  if(rs.next()) 
				{
					newDish.setDishID(Integer.parseInt(rs.getString(1))); 
					 preparedStmt = myCon.prepareStatement(addToRestaurant);
					 preparedStmt.setInt(1,newDish.getRestaurantNumber());
					 preparedStmt.setInt(2,newDish.getDishID());
					 preparedStmt.execute();
					 if(newDish.getAddDirectllyToMenu()==1)
					 {
						 rs =stmt.executeQuery(String.format("SELECT MenuID FROM biteme.menu "
						 		+ "WHERE MenuType='%s' AND RestaurantNum='%d'",
						 		newDish.getType(),newDish.getRestaurantNumber()));
						 if(rs.next())
						 {
							 preparedStmt= myCon.prepareStatement(addDishToMenu);
							 preparedStmt.setInt(1,newDish.getDishID());
							 preparedStmt.setInt(2,rs.getInt(1));
							 preparedStmt.execute();
						 }
						 else {
							 int menuId=createNewEmptyMenu(newDish.getRestaurantNumber(),newDish.getType(),myCon);
							 if(menuId==-1)
							 {
									db.sendToClient("insert_New_Dish_msg~Error happened while adding your dish",
											client);
									return;
							 }
							 preparedStmt= myCon.prepareStatement(addDishToMenu);
							 preparedStmt.setInt(1,newDish.getDishID());
							 preparedStmt.setInt(2,menuId);
							 preparedStmt.execute();
						 }
						 
					 }
					 db.sendToClient("insert_New_Dish_msg~The dish: "+newDish.getName()+" has been added successflly",
							 client);
				}
			  else db.sendToClient("insert_New_Dish_msg~Error happened while adding your dish",client);
		  }catch(Exception e) {
			  db.sendToClient("insert_New_Dish_msg~Error happened while adding your dish",client);
			  return;
		}
	}
	protected void createNewMenu(String []res,ConnectionToClient client,Connection myCon,DBController db) 
	{
		Statement stmt;
		ResultSet rs;
		int i,restaurantNum=Integer.parseInt(res[3]);
		String menuType=res[2];
		int menuId=createNewEmptyMenu(restaurantNum,menuType,myCon);
		if(menuId==-1)
		{
			db.sendToClient("Insert_Menu~Error happened while adding your menu", client);
			return;
		}
		int amountOfDishes=Integer.parseInt(res[1]);
		if(amountOfDishes==0)
			db.sendToClient("Insert_Menu~Your menu has been added successfully", client);
		PreparedStatement preparedStmt;
		String AddNewMenu="INSERT INTO biteme.dishinmenu (DishID,MenuID) VALUES (?,?);";
		 try {
			 preparedStmt = myCon.prepareStatement(AddNewMenu);
			 preparedStmt.setInt(2,menuId);
			 
			 for(i=0;i<amountOfDishes;i++) {
				 preparedStmt.setInt (1,Integer.parseInt(res[4+i]));
				 preparedStmt.execute();
			 }
			 stmt = myCon.createStatement();
			 rs=stmt.executeQuery(String.format("SELECT DishID FROM biteme.dishinmenu WHERE MenuID='%d'",menuId));
			 for(i=0;i<amountOfDishes;i++)
				 if(!rs.next())
				 { 
					 db.sendToClient("Insert_Menu~Error happened while adding your menu", client);
					 return; 
				 }
		    db.sendToClient("Insert_Menu~Your menu has been added successfully", client);
			preparedStmt.close();
		  }catch(Exception e) {
			  db.sendToClient("Insert_Menu~Error happened while adding your menu", client);
			  return;
		}
		
	}
	protected int createNewEmptyMenu(int restaurantNum,String menuType,Connection myCon) 
	{
		Statement stmt;
		ResultSet rs;
		PreparedStatement preparedStmt;
		int menuId;
		String AddEmptyMenu="INSERT INTO biteme.menu (RestaurantNum,MenuType) VALUES (?,?);";
		 try {
			 preparedStmt = myCon.prepareStatement(AddEmptyMenu);
			 preparedStmt.setInt (1,restaurantNum);
			 preparedStmt.setString(2,menuType);
			 preparedStmt.execute();
			 stmt = myCon.createStatement();
			 rs=stmt.executeQuery("SELECT last_insert_id()");//get new menu id
			if(rs.next()) 
			{
				menuId=Integer.parseInt(rs.getString(1));
				stmt.close();
				preparedStmt.close();
				return menuId; 
				//sendToClient("Insert_Empty_Menu~"+"Your menu has been add successfully.Menu ID:"+rs.getString(1), client);
			}	
			else {
				stmt.close();
				preparedStmt.close();
				return -1;
			} 
		  }catch(Exception e) {
			  return -1;
		}
	}
	protected void laodDishesByType(String []res,ConnectionToClient client,Connection myCon,DBController db)
	{
		  
		  Statement stmt;
		  ResultSet rs;
		  try {
			  stmt = myCon.createStatement();
			  rs =stmt.executeQuery(String.format("SELECT MenuID FROM biteme.menu WHERE MenuType='%s' AND RestaurantNum='%d'",res[1],Integer.parseInt(res[2])));
		  }catch(Exception e) {
			  db.sendToClient("load_Dishes_msg~Error happened while loading the dishes",client);
			  return;
			 // sendToClient("You already have a "+res[1]+"menu"+e,client);
		}    
		  ArrayList<Dish>dishes=new ArrayList<>();
		  try 
		  {
			  if(rs.isBeforeFirst())//if the rs isn't empty so we already created a menu with this type of dishes so do not create another one
			  {
				  db.sendToClient("load_Dishes_msg~Unavailable dish type choose another one",client);
				  stmt.close();
				  return;
			  }	
			  stmt = myCon.createStatement();
			  rs =stmt.executeQuery("SELECT *\r\n"
		  		+ "FROM biteme.dishes\r\n"
		  		+ "INNER JOIN \r\n"
		  		+ "(\r\n"
		  		+ "SELECT DishID FROM biteme.dishinrestaurant WHERE restaurantNumber=\""+Integer.parseInt(res[2])+"\\r\n"
		  		+ ") as x\r\n"
		  		+ " ON biteme.dishes.dishID=x.DishID AND biteme.dishes.DishType=\""+res[1]+"\\r\n");

		  	  while(rs.next())
		  	  {			  
					  int dishID=rs.getInt(1);
					  String DishName=rs.getString(2);
					  String DishType=rs.getString(3);
					  int price=rs.getInt(4);
					  int chooseSize=rs.getInt(5);
					  int chooseCookLvl=rs.getInt(6);
					  int chooseExtras=rs.getInt(7);
					  dishes.add(new Dish(dishID,DishName,DishType,price,chooseSize,chooseCookLvl,chooseExtras,null,null,0,-1,Integer.parseInt(res[2])));		 	
		  	  }
		  	db.sendToClient(dishes,client);
			stmt.close();
			rs.close();
		  }
		  catch (Exception e) {
			  db.sendToClient("load_Dishes_msg~Error happened while loading the dishes",client);
		}
	}
}
