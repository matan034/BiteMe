package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;



import entity.Dish;
import entity.DishInRestaurant;
import entity.clientData;
import ocsf.server.ConnectionToClient;

public class DBMenuController {
	protected void loadAllDishInRestaurant(String []res,ConnectionToClient client,Connection myCon,DBController db)
	{
		Statement stmt;
		ResultSet myDishes,checkIfDishInMenu;
		int restaurantID=Integer.parseInt(res[1]);
		String dishInRestaurant="SELECT dishes.*,Price,ChooseSize,"
				+ "ChooseCookingLevel,ChooseExtras,DishImage,ImageName,ImageSize\r\n"
		  		+ "FROM biteme.dishes,biteme.dishinrestaurant\r\n"
		  		+ "WHERE biteme.dishes.dishID=biteme.dishinrestaurant.dishID"
		  		+ "AND biteme.dishinrestaurant.restaurantNumber=\""+restaurantID+"\\r\n";
		try 
		{
			stmt = myCon.createStatement();
			myDishes=stmt.executeQuery(dishInRestaurant);
			ArrayList<DishInRestaurant> dishes=new ArrayList<DishInRestaurant>();
			while(myDishes.next())
			{
				int dishID=myDishes.getInt(1);
				  String DishName=myDishes.getString(2);
				  String DishType=myDishes.getString(3);
				  double price=myDishes.getDouble(4);
				  int chooseSize=myDishes.getInt(5);
				  int chooseCookLvl=myDishes.getInt(6);
				  int chooseExtras=myDishes.getInt(7);
				  String DishImage=myDishes.getString(9);
				  InputStream imageData=myDishes.getBinaryStream(8);
				  int size=myDishes.getInt(10);
				  BufferedInputStream bis = new BufferedInputStream(imageData);
				  byte[]myByteArray=new byte[size];
				  bis.read(myByteArray,0,size);
				  DishInRestaurant d=new DishInRestaurant(dishID,DishName,DishType,price,chooseSize,
						  chooseCookLvl,chooseExtras,DishImage,myByteArray,
						  0,restaurantID);
				  checkIfDishInMenu=stmt.executeQuery(String.format("SELECT MenuID FROM biteme.dishinmenu "
					  		+ "WHERE DishID='%d';",d.getDishID()));
				  if(checkIfDishInMenu.next())
					  d.setInMenu(1);
				  dishes.add(d);
			}
			clientData data=new clientData(dishes,"load_all_dish_in_menu");
			db.sendToClient(data,client);
		}catch(Exception e) 
		{
			db.sendToClient("load_all_dishes_msg~Error happened while loading your menu",client);
		}
	}
	protected void loadAllDishsInMenu(String []res,ConnectionToClient client,Connection myCon,DBController db)
	{
		Statement stmt;
		ResultSet menu,dishesInMenu;
		int restaurantID=Integer.parseInt(res[1]);
		int MenuID=0;
		
		String dishInMenu="Select dishes_without_name.*,dishes.DishName,dishes.DishType\r\n"
				+ "From\r\n"
				+ "(Select dishinrestaurant.*\r\n"
				+ "From\r\n"
				+ "(Select DishID\r\n"
				+ "From dishinmenu\r\n"
				+ "Where MenuID=(SELECT MenuID FROM biteme.menu WHERE RestaurantNum="+res[1]+" ) ) as x\r\n"
				+ "Inner join dishinrestaurant\r\n"
				+ "on dishinrestaurant.DishId=x.DishID\r\n"
				+ "Where restaurantNumber="+res[1]+")  as dishes_without_name\r\n"
				+ "Inner Join dishes\r\n"
				+ "On dishes.DishID=dishes_without_name.DishID";
		
		
		try 
		{
			stmt = myCon.createStatement();
			
			ArrayList<DishInRestaurant> dishes=new ArrayList<DishInRestaurant>();
			dishesInMenu=stmt.executeQuery(dishInMenu);
			while(dishesInMenu.next())
			{
				 int dishID=dishesInMenu.getInt(2);
				 String DishName=dishesInMenu.getString(9);
				 String DishType=dishesInMenu.getString(10);
					  double price=dishesInMenu.getDouble(3);
					  int chooseSize=dishesInMenu.getInt(4);
					  int chooseCookLvl=dishesInMenu.getInt(5);
					  int chooseExtras=dishesInMenu.getInt(6);
					  String DishImage=dishesInMenu.getString(8);
					  InputStream imageData=dishesInMenu.getBinaryStream(7);
					  BufferedInputStream bis = new BufferedInputStream(imageData);
					  byte[]myByteArray=new byte[imageData.available()];
					  int size=myByteArray.length;
					  bis.read(myByteArray,0,size);
					  dishes.add(new DishInRestaurant(dishID,DishName,DishType,price,chooseSize,
							  chooseCookLvl,chooseExtras,DishImage,myByteArray,
							  1,restaurantID));
				}
			
			clientData data=new clientData(dishes,"load_all_dish_in_menu");
			db.sendToClient(data,client);
		}catch(Exception e) 
		{
			db.sendToClient("load_all_dishes_msg~Error happened while loading your menu",client);
		}
	}
	protected void loadAllDishs(String []res,ConnectionToClient client,Connection myCon,DBController db) 
	{
		Statement stmt;
		ResultSet AllDishes;

		String allDishes=String.format("SELECT * FROM biteme.dishes;");
		try 
		{
			stmt = myCon.createStatement();
			String dishName,dishType;
			int dishID;
			AllDishes=stmt.executeQuery(allDishes);
			ArrayList<Dish> dishes=new ArrayList<Dish>();
			while(AllDishes.next())
			{
				dishID=AllDishes.getInt(1);
				dishName=AllDishes.getString(2);
				dishType=AllDishes.getString(3);
				dishes.add(new Dish(dishID,dishName,dishType));
			}
			clientData data=new clientData(dishes,"laod_all_dishes");
			db.sendToClient(data,client);
		}catch(Exception e) 
		{
			db.sendToClient("load_all_dishes_msg~Error happened while loading your menu",client);
		}
	}
	protected void insertDishesToRestaurant(ArrayList<DishInRestaurant> dishes,ConnectionToClient client,Connection myCon,DBController db)
	{
		Statement stmt;
		ResultSet menu,menuID;
		int restaurantID=dishes.get(0).getRestaurantNumber();
		int MenuID=0,temp=0;
		String myMenu=String.format("SELECT MenuID FROM biteme.menu WHERE RestaurantNum=%d;",restaurantID);
		try 
		{
			stmt = myCon.createStatement();
			menu=stmt.executeQuery(myMenu);
			if(!menu.isBeforeFirst())
			{
				MenuID=createNewEmptyMenu(restaurantID,myCon);
				if(MenuID==-1)
				{
					db.sendToClient("insert_dishes_to_restaurant_msg~Error happened while saving your menu",client);
					return;
				}
			}
			else { 
				menu.next();
				MenuID=menu.getInt(1);
			}
			boolean result;
			for(DishInRestaurant DIR:dishes)
			{
				  menuID =stmt.executeQuery(String.format("SELECT MenuID FROM biteme.dishinmenu \r\n"
				  		+ "WHERE DishID='%d' AND MenuID='%d';",DIR.getDishID(),MenuID));
				  if(menuID.next())
				  {
					  result=editDish(DIR,myCon,MenuID);
					  if(!result)
					  {
						  db.sendToClient("insert_dishes_to_restaurant_msg~Error happened while insert dishes",client); 
						  return;
					  }
				  }
				  else 
				  {
					  result=insertDish(DIR,1,MenuID,myCon);
					  if(!result)
					  {
						  
						  db.sendToClient("insert_dishes_to_restaurant_msg~Error happened while insert dishes",client); 
						  return;
					  }

				  }
			}
			db.sendToClient("insert_dishes_to_restaurant_msg~The dishes has been added successfully to your menu.",client); 
			
		}catch(Exception e) 
		{
			db.sendToClient("insert_dishes_to_restaurant_msg~Error happened while insert dishes",client); 
		}
	}
	
	protected int createNewEmptyMenu(int restaurantNum,Connection myCon) 
	{
		Statement stmt;
		ResultSet rs;
		PreparedStatement preparedStmt;
		int menuId;
		String AddEmptyMenu="INSERT INTO biteme.menu (RestaurantNum) VALUES (?);";
		 try {
			 preparedStmt = myCon.prepareStatement(AddEmptyMenu);
			 preparedStmt.setInt (1,restaurantNum);
			 preparedStmt.execute();
			 stmt = myCon.createStatement();
			 rs=stmt.executeQuery("SELECT last_insert_id();");//get new menu id
			if(rs.next()) 
			{
				menuId=rs.getInt(1);
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
	protected boolean editDish(DishInRestaurant Dish,Connection myCon,int menuId)
	{
		String editDish;
		PreparedStatement preparedStmt;
		try {
			  editDish="UPDATE biteme.dishinrestaurant "
				  		+ "SET Price=? , ChooseSize=?, ChooseCookingLevel=?,ChooseExtras=?,"
				  		+ "DishImage=? , ImageName=?  "
				  		+ "WHERE DishID=?;";
			  preparedStmt = myCon.prepareStatement(editDish);
			  preparedStmt.setDouble (1,Dish.getPrice());
			  preparedStmt.setInt(2,Dish.getChooseSize());
			  preparedStmt.setInt(3,Dish.getChooseCookingLvl());
			  preparedStmt.setInt(4,Dish.getChooseExtras());
			  preparedStmt.setString(6,Dish.getImageName());
			  InputStream targetStream = new ByteArrayInputStream(Dish.getMyImagebytearray());
			  preparedStmt.setBinaryStream(5, targetStream);
			  preparedStmt.setInt(7,Dish.getDishID());
			  preparedStmt.execute();
			  preparedStmt.close();
		}catch(Exception e) {
			  return false;
		}
		return true;
	}
	protected boolean insertDish(DishInRestaurant dish,int addToMenu,int menuId,Connection myCon)
	{
		  Statement stmt;
		  PreparedStatement preparedStmt;
		  boolean result;
		  try {
			  stmt = myCon.createStatement();
			  String AddDishToRestaurant="INSERT INTO biteme.dishinrestaurant (restaurantNumber, DishID, Price, ChooseSize, ChooseCookingLevel, ChooseExtras, DishImage,ImageName) VALUES (?,?,?,?,?,?,?,?);";
			  preparedStmt = myCon.prepareStatement(AddDishToRestaurant);
			  preparedStmt.setInt (1,dish.getRestaurantNumber());
			  preparedStmt.setInt(2,dish.getDishID());
			  preparedStmt.setDouble(3,dish.getPrice());
			  preparedStmt.setInt(4,dish.getChooseSize());
			  preparedStmt.setInt(5,dish.getChooseCookingLvl());
			  preparedStmt.setInt(6,dish.getChooseExtras());
			  InputStream targetStream = new ByteArrayInputStream(dish.getMyImagebytearray());
			  preparedStmt.setBinaryStream(7, targetStream);
			  preparedStmt.setString(8,dish.getImageName());
			  preparedStmt.execute();
			  stmt.close();
			  preparedStmt.close();
			  if(addToMenu==1)
			  {
				  result=addDishToMenu(dish.getRestaurantNumber(),dish.getDishID(),myCon);
				  if(!result)
					  return false;
			  }
		  }catch(Exception e) {
			  return false;
		  }
		  return true;
	}
	protected boolean addDishToMenu(int restNum,int dishID,Connection myCon)
	{
		  PreparedStatement preparedStmt;
		  try {
			  String AddDish="INSERT INTO biteme.dishinmenu (MenuID,DishID) VALUES ((SELECT MenuID FROM biteme.menu Where RestaurantNum=?),?);";
			  preparedStmt = myCon.prepareStatement(AddDish);
			  preparedStmt.setInt (1,restNum);
			  preparedStmt.setInt(2,dishID);
			  preparedStmt.execute();		
			  preparedStmt.close();
		  }catch(Exception e) {
			  return false;
		  }
		  return true;
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

	protected void addDish(String []res,ConnectionToClient client,Connection myCon,DBController db)
	{
		  Statement stmt;
		  ResultSet rs;
		  PreparedStatement preparedStmt;
		  int id=-1;
		  try {
			  stmt = myCon.createStatement();
			  String AddDish="INSERT INTO biteme.dishes (DishName,DishType) VALUES (?,?);";
			  preparedStmt = myCon.prepareStatement(AddDish);
			  rs =stmt.executeQuery(String.format("SELECT DishID FROM biteme.dishes WHERE DishName='%s' AND DishType='%s'",res[1],res[2]));
			  if(rs.next())
			  {
				  db.sendToClient("Add_dish~Dish Already Exist", client);
			  }
			  else
			  {
					preparedStmt.setString (1,res[1]);
					preparedStmt.setString(2,res[2]);
					preparedStmt.execute();
					rs=stmt.executeQuery("SELECT last_insert_id()");//get new menu id
					if(rs.next()) 
					{
						id=rs.getInt(1);
					}
					db.sendToClient("Add_dish~Dish was added successfuly~"+id, client);		
			  }
			  stmt.close();
			  rs.close();
			  preparedStmt.close();
		  }catch(Exception e) {
			  db.sendToClient("Add_dish~"+e,client);
		  }
	}
	
	protected void insertDishToRestaurant(DishInRestaurant dish,ConnectionToClient client,Connection myCon,DBController db)
	{
		  Statement stmt;
		  ResultSet rs;
		  PreparedStatement preparedStmt;
		  try {
			  stmt = myCon.createStatement();
			  String AddDishToRestaurant="INSERT INTO biteme.dishinrestaurant (restaurantNumber, DishID, Price, ChooseSize, ChooseCookingLevel, ChooseExtras, DishImage, ImageName) VALUES (?,?,?,?,?,?,?,?);";
			  rs =stmt.executeQuery(String.format("SELECT DishID FROM biteme.dishinrestaurant WHERE DishID='%d' AND restaurantNumber= '%d' ",dish.getDishID(),dish.getRestaurantNumber()));
			  preparedStmt = myCon.prepareStatement(AddDishToRestaurant);
			  if(rs.next())
				 db.sendToClient("Add_dish_to_rest~Dish already in restaurant", client);		
			 else
			 {
			  preparedStmt.setInt (1,dish.getRestaurantNumber());
			  preparedStmt.setInt(2,dish.getDishID());
			  preparedStmt.setDouble(3,dish.getPrice());
			  preparedStmt.setInt(4,dish.getChooseSize());
			  preparedStmt.setInt(5,dish.getChooseCookingLvl());
			  preparedStmt.setInt(6,dish.getChooseExtras());
			  InputStream targetStream = new ByteArrayInputStream(dish.getMyImagebytearray());
			  preparedStmt.setBinaryStream(7, targetStream);
			  preparedStmt.setString(8,dish.getImageName());  
			  preparedStmt.execute();
			  db.sendToClient("Add_dish_to_rest~Dish was added successfuly", client);		
			 }
			  stmt.close();
			  rs.close();
			  preparedStmt.close();
		  }catch(Exception e) {
			  db.sendToClient("Add_dish_to_rest~"+e,client);
		  }
	}
	
	
	protected void addDishToRestMenu(String []res,ConnectionToClient client,Connection myCon,DBController db)
	{
		//maybe check if its already in menu?
		  PreparedStatement preparedStmt;
		  try {
			
			  String AddDish="INSERT INTO biteme.dishinmenu (MenuID,DishID) VALUES ((SELECT MenuID FROM biteme.menu Where RestaurantNum=?),?);";
			  preparedStmt = myCon.prepareStatement(AddDish);
			  preparedStmt.setInt (1,Integer.parseInt(res[2]));
			  preparedStmt.setInt(2,Integer.parseInt(res[1]));
			  preparedStmt.execute();
			  db.sendToClient("Add_dish_to_menu~Dish was added successfuly", client);		
			  preparedStmt.close();
		  }catch(Exception e) {
			  db.sendToClient("Add_dish_to_menu~"+e,client);
		  }
	}
	protected void removeDishes(ArrayList<DishInRestaurant> dishes,ConnectionToClient client,Connection myCon,DBController db)
	{
		 PreparedStatement stmt;
		  ResultSet rs;
		  String deleteDish="Delete From biteme.dishinrestaurant Where restaurantNumber=? AND DishID=?";
		  try {
		  for(DishInRestaurant dish:dishes)
		  {
			 
			  stmt = myCon.prepareStatement(deleteDish);
			  stmt.setInt (1,dish.getRestaurantNumber());
			  stmt.setInt(2,dish.getDishID());
			  stmt.execute();
			  
		  }
		  db.sendToClient("remove dishes~Dishes were removed successfuly", client);	
		  }
		  catch (Exception e) {
			  db.sendToClient("remove dishes~"+e, client);	
		}
	}
}
