package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import entity.Branch;
import entity.Dish;
import entity.DishInOrder;
import entity.DishInRestaurant;
import ocsf.server.ConnectionToClient;

/**
 * This class is used for all functions relating orders, IE: creating a new order from a restaurant, delivery details, loading of menus to display to users and so on 
 * Used in src/gui/customer with the controllers in src/controllers/order 
 * @author      matan weisberg 
 * @version     1.0                 
 * @since       01.01.2022        
 */

public class DBOrderController {
	
	  /*
	   * This method insert a new order to DB
	   *
	   * @param res[0] used to start function, rest of res for details we need for queries  order information 
	   * @param res[0]=Insert_order, 
	   * @param res[1]=branchId,
	   * @param res[2]=customernum,
	   * @param res[3]=supply_wait,
	   * @param res[4]=price,
	   * @param res[5]=isearlyorder,
	   * @param res[6]=requestordertime
	   * @param client The connection from which the message originated.
	   * @param myCon the connection to mySql DB
	   * @param db the main database controller used in order to send message back to client
	   */
	  protected void insertOrder(String[]res,ConnectionToClient client,Connection myCon,DBController db) throws SQLException
	  {
		  Statement stmt;
		  ResultSet rs;
		  stmt = myCon.createStatement();
		  int orderId=-1,branch_id,customer_num,isEarlyOrder;
		  double price;
		  String order,supply_way,Delivery_type,requested_order_time,delivery_query,takeAway_query,recieverName,recieverPhone,businessName,street,city,zip;
		  PreparedStatement preparedStmt;
		  order="INSERT INTO biteme.order (ResturantNumber, CustomerNumber, SupplyWay,Price,IsEarlyOrder,RequestOrderTime)  VALUES (?,?,?, ?, ?,?);";
		  try {
		  branch_id=Integer.parseInt(res[1]);
		  customer_num=Integer.parseInt(res[2]);
		  supply_way=res[3];
		  price=Double.parseDouble(res[4]);
		  isEarlyOrder=Integer.parseInt(res[5]);
		  requested_order_time=res[6];
		  // create the mysql insert new order	  
	      preparedStmt = myCon.prepareStatement(order);
	      preparedStmt.setInt (1,branch_id);
	      preparedStmt.setInt (2,customer_num);
	      preparedStmt.setString (3,supply_way);
	      preparedStmt.setDouble(4,price);
	      preparedStmt.setInt (5,isEarlyOrder);
	      preparedStmt.setString (6,requested_order_time);
	      preparedStmt.execute();	
	      rs=stmt.executeQuery("SELECT last_insert_id()");//get new order id
			if(rs.next()) 
				orderId=rs.getInt(1);	
		  if(supply_way.equals("Delivery"))
		  {
			  String insertDelivery="INSERT INTO biteme.delivery_details (OrderID, DeliveryType, RecieverName,BusinessName,RecieverPhone,DeliveryStreet,DeliveryCity,DeliveryZip)"
			  		+ "  VALUES (?,?, ?, ?,?,?,?,?);";
			  Delivery_type=res[7];
			  recieverName=res[8];  
			  businessName=res[9];
			  recieverPhone=res[10];
			  street=res[11];
			  city=res[12];
			  zip=res[13];
			  // create the mysql insert deliveryDetails	  
		      preparedStmt = myCon.prepareStatement(insertDelivery);
		      preparedStmt.setInt (1,orderId);
		      preparedStmt.setString (2,Delivery_type);
		      preparedStmt.setString (3,recieverName);
		      preparedStmt.setString (4,businessName);
		      preparedStmt.setString (5,recieverPhone);
		      preparedStmt.setString (6,street);
		      preparedStmt.setString (7,city);
		      preparedStmt.setString (8,zip);
		      preparedStmt.execute();		
		  }
		  if(orderId!=-1)
			db.sendToClient("Insert~"+orderId, client);
		  else
			 db.sendToClient("Insert~fail insert order", client);
		  }
		  catch (Exception e) {
			  System.out.println(e);
			  }
		  stmt.close();
	  }

	  /*
	   * This method loads all orders of specified restaurant
	   *
	   * @param res[0] used to start function, rest of res for details we need for queries  restaurant number 
	   * @param res[0]=Load_orders
	   * @param client The connection from which the message originated.
	   * @param myCon the connection to mySql DB
	   * @param db the main database controller used in order to send message back to client
	   */
	 	  protected void loadOrders(String []res,ConnectionToClient client,Connection myCon,DBController db) throws SQLException{
		  Statement stmt;
		  stmt = myCon.createStatement();
		  String result="Load Orders~";
		  ArrayList <String> orders=new ArrayList<>();
		  int flag,restuarantNum=0;
		  ResultSet rs;
		  rs = stmt.executeQuery(String.format("SELECT Number FROM biteme.restaurant WHERE Manager='%s'",res[1]));
		  if(rs.next()) {
			  restuarantNum=rs.getInt(1);
		  }
		  flag=stmt.executeUpdate(String.format("CREATE TEMPORARY TABLE biteme.DishesInOrders\r\n"
		  		+ "		  		SELECT * \r\n"
		  		+ "		  		FROM (\r\n"
		  		+ "		  		SELECT biteme.dishes.DishName,DishesByOrderNumber.DishID,DishesByOrderNumber.OrderNumber\r\n"
		  		+ "		  		FROM biteme.dishes\r\n"
		  		+ "		  			INNER JOIN (SELECT dishinorder.DishID,dishinorder.OrderNumber\r\n"
		  		+ "		  		FROM biteme.dishinorder\r\n"
		  		+ "		  		INNER JOIN (SELECT biteme.order.OrderID, biteme.order.CustomerNumber\r\n"
		  		+ "		  		FROM biteme.order\r\n"
		  		+ "		  		INNER JOIN biteme.restaurant\r\n"
		  		+ "		  		ON restaurant.Number=biteme.order.ResturantNumber WHERE ResturantNumber='%d')AS OrderByRestaurant\r\n"
		  		+ "		  		ON OrderNumber=OrderID)AS DishesByOrderNumber\r\n"
		  		+ "		  		WHERE dishes.DishID=DishesByOrderNumber.DishID)As x;\r\n"
		  		+ "                ",restuarantNum));
		  flag=stmt.executeUpdate("CREATE TEMPORARY TABLE biteme.CustomersInOrders\r\n"
		  		+ "		  		SELECT DISTINCT *\r\n"
		  		+ "		  		FROM(\r\n"
		  		+ "		  		SELECT account.FirstName, account.LastName,account.Telephone,account.Email,CustomerOrderJoin.SupplyWay, CustomerOrderJoin.RequestOrderTime,CustomerOrderJoin.OrderID\r\n"
		  		+ "		  		FROM biteme.account\r\n"
		  		+ "		  		INNER JOIN (SELECT biteme.order.SupplyWay, biteme.order.RequestOrderTime, customers.PrivateAccount,customers.BusinessAccount,biteme.order.OrderID\r\n"
		  		+ "		  		FROM biteme.order\r\n"
		  		+ "		  		INNER JOIN biteme.customers\r\n"
		  		+ "		  		ON biteme.order.CustomerNumber=customers.CustomerNumber)AS CustomerOrderJoin\r\n"
		  		+ "		  		ON account.AccountNum=CustomerOrderJoin.PrivateAccount OR account.AccountNum=CustomerOrderJoin.businessaccount)AS y;");
		  
		  rs=stmt.executeQuery("SELECT DishesInOrders.DishName, CustomersInOrders.FirstName,CustomersInOrders.LastName,CustomersInOrders.SupplyWay,CustomersInOrders.RequestOrderTime,CustomersInOrders.OrderID,CustomersInOrders.Email,CustomersInOrders.Telephone\r\n"
		  		+ "FROM biteme.DishesInOrders\r\n"
		  		+ "INNER JOIN biteme.CustomersInOrders\r\n"
		  		+ "ON CustomersInOrders.OrderID=DishesInOrders.OrderNumber;\r\n"
		  		+ "");
		  orders.add("Order");
		  while(rs.next())
		  {
			  
			  String new_order=rs.getString(1)+"~"+rs.getString(2)+"~"+rs.getString(3)+"~"+rs.getString(4)+"~"+rs.getString(5)+"~"+rs.getString(6)+"~"+rs.getString(7)+"~"+rs.getString(8);
			  orders.add(new_order);
		  }
		  flag=stmt.executeUpdate("DROP TABLE biteme.CustomersInOrders;");
		  flag=stmt.executeUpdate("DROP TABLE biteme.DishesInOrders;");
		  
		  db.sendToClient(orders,client);
		  rs.close();
		  stmt.close();
		  
		 
	  }


		  /*
		   * This method loads all orders of a specified customer
		   *
		   * @param res[0] used to start function, rest of res for details we need for queries  customer number 
		   * @param res[0]=Load_Myorders
		   * @param client The connection from which the message originated.
		   * @param myCon the connection to mySql DB
		   * @param db the main database controller used in order to send message back to client
		   */  
	 	 protected void loadMyOrders(String[] res, ConnectionToClient client,Connection myCon,DBController db) {
	 		Statement stmt;
	 		ResultSet rs;

	 		try {
	 			stmt = myCon.createStatement();
	 			rs = stmt.executeQuery(
	 					"Select x.*,restaurant.Name\r\n"
	 					+ "From (SELECT OrderID,ResturantNumber,CustomerNumber ,SupplyWay,Price,RequestOrderTime,IsApproved,IsArrived,OutForDelivery FROM biteme.order WHERE CustomerNumber="+ res[1]+") as x\r\n"
	 					+ "Inner Join restaurant \r\n"
	 					+ "On restaurant.Number=x.ResturantNumber");
	 			ArrayList<String> myOrders = new ArrayList<>();
	 			myOrders.add("load my orders");
	 			while (rs.next()) {
	 				int orderNum = rs.getInt(1);
	 				int restaurantNum= rs.getInt(2);
	 				int customerNum= rs.getInt(3);
	 				String orderType = rs.getString(4);
	 				Double Price=rs.getDouble(5);
	 				String orderTime = rs.getString(6);
	 				int isApproved = rs.getInt(7);
	 				int isArrived=rs.getInt(8);
	 				int outForDeliver=rs.getInt(9);
	 				String name=rs.getString(10);
	 				String temp = orderNum + "~" + orderType + "~" + orderTime + "~" + isApproved + "~" + isArrived + "~" + outForDeliver + "~"+ name + "~" + restaurantNum + "~" + customerNum + "~" + Price;
	 				myOrders.add(temp);
	 			}

	 			db.sendToClient(myOrders, client);
	 			rs.close();
	 			stmt.close();
	 		} catch (Exception e) {
	 			System.out.println(e);
	 		}
	 	}
	 	 
	 	 /*
		   * This method finds a w4c number in the database
		   *
		   * @param res[0] used to start function, rest of res for details we need for queries  w4c card number 
		   * @param res[0]=W4C_verify,
		   * @param res[1]=cardnum,
		   * @param res[2]=privateaccoutnum,
		   * @param res[3]=businessaccoutnnum
		   * @param client The connection from which the message originated.
		   * @param myCon the connection to mySql DB
		   * @param db the main database controller used in order to send message back to client
		   */  
	 	protected void w4cVerify(String[] res, ConnectionToClient client,Connection myCon,DBController db) {
			String result;
			Statement stmt;
			ResultSet rs;
			try {
				stmt = myCon.createStatement();

				rs = stmt.executeQuery("SELECT * FROM biteme.w4c_cards WHERE CardNum=" + res[1]+" AND (PrivateAccount="+res[2]+" OR BuisinessAccount="+res[3]+")");
				if (rs.next()) {
					System.out.println("card found");
					result = "W4C verify~" + rs.getString(1);
					if(rs.getString(2)!=null)//private
						result+= "~" + rs.getString(2);
					else
						result+="~0";
					if(rs.getString(3)!=null)//business
						result+= "~" + rs.getString(3);
					else
						result+="~0";
					db.sendToClient(result, client);
				} else
					db.sendToClient("W4C verify~W4C Wasnt found", client);
				rs.close();
				stmt.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	 	
	 	/*
		   * This method loads w4c card number for a given customer for the qr simulation
		   *
		   * @param res[0] used to start function, rest of res for details we need for queries  
		   * @param res[0]=W4C_load_list 
		   * @param res[1]=PrivateAccount/BusinessAccount
		   * @param res[2]=accountNum
		   * @param client The connection from which the message originated.
		   * @param myCon the connection to mySql DB
		   * @param db the main database controller used in order to send message back to client
		   */  
	 	protected void w4cLoadList(String[] res, ConnectionToClient client,Connection myCon,DBController db) {
			String result = "W4C_load_list";
			Statement stmt;
			ResultSet rs;
			try {
				stmt = myCon.createStatement();
				if(res[1].equals("PrivateAccount"))
				{
					rs = stmt.executeQuery("SELECT CardNum FROM biteme.w4c_cards WHERE PrivateAccount="+res[2]);
				}
				else {
					rs = stmt.executeQuery("SELECT CardNum FROM biteme.w4c_cards WHERE BuisinessAccount="+res[2]);
				}
				
				if (rs.next()) {
					result += "~" + rs.getString(1);

				}
				db.sendToClient(result, client);
				rs.close();
				stmt.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	 	  
	 	/*
		   * This method loads all dishes in a selected restaurant
		   *
		   * @param res[0] used to start function, rest of res for details we need for queries 
		   * @param res[0]=Load_dishes 
		   * @param res[1]= restaurant number
		   * @param client The connection from which the message originated.
		   * @param myCon the connection to mySql DB
		   * @param db the main database controller used in order to send message back to client
		   */  
	 	protected void loadDishes(String[] res, ConnectionToClient client,Connection myCon,DBController db) {

			Statement stmt;
			ResultSet rs;
			ArrayList<DishInRestaurant> dishes = new ArrayList<>();
			try {
				stmt = myCon.createStatement();
				rs = stmt.executeQuery("Select dishes_without_name.*,dishes.DishName,dishes.DishType\r\n"
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
						+ "On dishes.DishID=dishes_without_name.DishID");

				while (rs.next()) {
					int restNum=rs.getInt(1);
					int dishID = rs.getInt(2);
					Double price = rs.getDouble(3);
					int chooseSize = rs.getInt(4);
					int chooseCookLvl = rs.getInt(5);
					int chooseExtras = rs.getInt(6);
					 InputStream input;  
					input=rs.getBinaryStream(7);
					  if(input==null)
					  {
						  File image=new File("..\\BiteMe\\src\\gui\\img\\imageNotFound.jpg");
						  input = new FileInputStream(image);	 
					  }

					byte[] targetArray = new byte[input.available()];
					input.read(targetArray);
					String imageName=rs.getString(8);
					String DishName = rs.getString(9);
					String DishType = rs.getString(10);	
					DishInRestaurant newDish = new DishInRestaurant(restNum,dishID,chooseSize,chooseCookLvl,chooseExtras,targetArray.length,price,imageName,targetArray);
					newDish.setName(DishName);
					newDish.setType(DishType);
					dishes.add(newDish);
					input.close();
				}

				db.sendToClient(dishes, client);
				
				stmt.close();
				rs.close();

			} catch (Exception e) {
				db.sendToClient("Cant Load Menu " + e, client);
			}
		}
	 	
	 	
	 	/*
		   * This method loads all dishes in database
		   *
		   * @param res[0] used to start function, rest of res for details we need for queries  
		   * @param res[0]=load_all_dishes
		   * @param client The connection from which the message originated.
		   * @param myCon the connection to mySql DB
		   * @param db the main database controller used in order to send message back to client
		   */  
	 	protected void loadAllDishes(String[] res, ConnectionToClient client,Connection myCon,DBController db) {
	 		Statement stmt;
			ResultSet rs;
			ArrayList<String> dishes = new ArrayList<>();
			dishes.add("load all dishes");
			try {
				stmt = myCon.createStatement();
				rs = stmt.executeQuery("Select * From biteme.dishes");
				while(rs.next())
				{
					dishes.add(rs.getString(1)+"~"+rs.getString(2)+"~"+rs.getString(3));
				}
				db.sendToClient(dishes, client);						
			}catch (Exception e) {
				db.sendToClient("Cant Load Dishes " + e, client);
			}
	 	}
	 	/*
		   * This method loads all company branches
		   *
		   * @param res[0] used to start function, rest of res for details we need for queries 
		   * @param res[0]=Load_branches
		   * @param client The connection from which the message originated.
		   * @param myCon the connection to mySql DB
		   * @param db the main database controller used in order to send message back to client
		   */
	 	protected void loadBranches(String[] res, ConnectionToClient client,Connection myCon,DBController db) {
			Statement stmt;
			ResultSet rs;

			try {
				stmt = myCon.createStatement();
				rs = stmt.executeQuery("SELECT * FROM biteme.branch");
				ArrayList<Branch> branches = new ArrayList<>();
				while (rs.next()) {
					int branchID = rs.getInt(1);
					String branchName = rs.getString(2);
					branches.add(new Branch(branchID, branchName));
				}

				db.sendToClient(branches, client);
				rs.close();
				stmt.close();
			} catch (Exception e) {

			}
		}
	 	
	 	/*
		   * This method adds a dish in order to dishinorder table in DB
		   *
		   * @param res[0] used to start function, rest of res for details we need for queries 
		   * @param  res[0]=Add_dishInOrder,
		   * @param res[1]=OrderNumber
		   * @param res[2]=DishInOrder
		   * @param res[3]=DishID
		   * @param res[4]=Size
		   * @param res[5]=CookingLevel
		   * @param res[6]=CookingLevel
		   * @param res[7]=Extras
		   * @param client The connection from which the message originated.
		   * @param myCon the connection to mySql DB
		   * @param db the main database controller used in order to send message back to client
		   */
	 	protected void addDishInOrder(String[] res, ConnectionToClient client,Connection myCon,DBController db) {
			Statement stmt;
			int flag;
			try {
				stmt = myCon.createStatement();
				flag = stmt.executeUpdate(String.format(
						"INSERT INTO biteme.dishinorder (OrderNumber,DishInOrder,DishID,Size, CookingLevel,Extras)  VALUES ('%d','%d','%d', '%s', '%s','%s')",
						Integer.parseInt(res[1]), Integer.parseInt(res[2]),Integer.parseInt(res[3]), res[4], res[5], res[6]));
				if (flag == 1)
					db.sendToClient("Insert dishinorder~Inserted successfuly", client);
				else
					db.sendToClient("Insert dishinorder~Insert failed", client);
				stmt.close();
			} catch (Exception e) {

			}
		}
	 	
	 	/*
		   * This method update that a orders has arrived to customer
		   *
		   * @param res[0] used to start function, rest of res for details we need for queries 
		   * @param res[0]=Order_arrived, 
		   * @param res[1]= 1 ,
		   * @param res[2] = order id
		   * @param client The connection from which the message originated.
		   * @param myCon the connection to mySql DB
		   * @param db the main database controller used in order to send message back to client
		   */
	 	protected void orderArrived(String[] res, ConnectionToClient client,Connection myCon,DBController db) {
			Statement stmt;
			int flag;
			try {
				stmt = myCon.createStatement();
				flag = stmt.executeUpdate(String.format("UPDATE biteme.order SET IsArrived = %d WHERE OrderID = %d;",
						Integer.parseInt(res[1]), Integer.parseInt(res[2])));
				db.sendToClient("Order_arrived~Updatet Successfully", client);
				stmt.close();
			} catch (Exception e) {

			}
		}

	 	/*
		   * This method load all the dishes of a selected order
		   *
		   * @param res[0] used to start function, rest of res for details we need for queries  
		   * @param res[0]=Load_orderDishes, 
		   * @param res[1]= order id
		   * @param client The connection from which the message originated.
		   * @param myCon the connection to mySql DB
		   * @param db the main database controller used in order to send message back to client
		   */
	 	protected void loadOrderDishes(String[] res, ConnectionToClient client,Connection myCon,DBController db) {
			Statement stmt;
			ResultSet rs;

			try {
				stmt = myCon.createStatement();
				rs = stmt.executeQuery(
						"Select *\r\n"
						+ "From\r\n"
						+ "(Select x.*,dishinrestaurant.Price\r\n"
						+ "From\r\n"
						+ "(SELECT OrderNumber,DishInOrder,DishID,Size,CookingLevel,Extras FROM biteme.dishinorder WHERE OrderNumber="+res[1]+") as x\r\n"
						+ "inner join dishinrestaurant\r\n"
						+ "On dishinrestaurant.DishID=x.DishID) as dishes_without_name\r\n"
						+ "Inner Join dishes\r\n"
						+ "On dishes.DishID=dishes_without_name.DishID");
				ArrayList<DishInOrder> myOrders = new ArrayList<>();

				while (rs.next()) {
					int orderNum = rs.getInt(1);
					int dishinorder=rs.getInt(2);
					int dishNum = rs.getInt(3);
					
					String size = rs.getString(4);
					String cookinglvl = rs.getString(5);
					String extras = rs.getString(6);
					String dishName = rs.getString(9);
					double dishPrice = rs.getDouble(7);
					DishInOrder temp = new DishInOrder(size, cookinglvl, extras, dishName, dishNum, orderNum, dishPrice,dishinorder);
					myOrders.add(temp);
				}

				db.sendToClient(myOrders, client);
				rs.close();
				stmt.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	 

	 	/*
		   * This method updates customer recieve time and check if it was late
		   *
		   * @param res[0] used to start function, rest of res for details we need for queries  
		   * @param res[0]=Update_recieve_time, 
		   * @param res[1]= order id
		   * @param client The connection from which the message originated.
		   * @param myCon the connection to mySql DB
		   * @param db the main database controller used in order to send message back to client
		   */
	 	protected void updateRecieveTime(String[]res,ConnectionToClient  client,Connection myCon,DBController db)
	 	{
	 		
	 		  Statement stmt;
	 		  int flag;
	 		  ResultSet rs;
	 		  try {
	 		  Date date1 = new Date();
	 		  SimpleDateFormat format = new SimpleDateFormat("HH:mm yyyy-MM-dd");
	 		  String curr_time=format.format(date1);
	 		  stmt = myCon.createStatement();
	 		  flag =stmt.executeUpdate(String.format("UPDATE biteme.order SET CustomerReciveTime = '%s' WHERE OrderID = '%d';", curr_time ,Integer.parseInt(res[1])));
	 		  if(flag==1)
	 		  {
	 			  rs = stmt.executeQuery("SELECT IsEarlyOrder,RequestOrderTime,RecivedOrderTime,CustomerReciveTime FROM biteme.order WHERE OrderID="+res[1]);
	 			  if(rs.next())
	 			  {
	 				  int isEarlyOrder,getCredit=0;
	 				  String supplierRecieve,CustomerRecievd,request;
	 				  isEarlyOrder=rs.getInt(1);
	 				  request=rs.getString(2);
	 				  supplierRecieve=rs.getString(3);
	 				 CustomerRecievd=rs.getString(4);
	 				 Date CustomerRecievdTime=new SimpleDateFormat("HH:mm yyyy-MM-dd").parse(CustomerRecievd);  
	 				 Date requsetTime=new SimpleDateFormat("HH:mm yyyy-MM-dd").parse(request);  
	 				 Date supplierRecieveTime=new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(supplierRecieve);  
	 				  
	 			 
	 				  if(isEarlyOrder==1)
	 				  {
	 					  	long diff =CustomerRecievdTime.getTime()-requsetTime.getTime();
	 					     long twenty_minutes=1200000;
	 					     if(diff>twenty_minutes) getCredit=1;
	 				  }
	 				  else
	 				  {
	 					 long diff =CustomerRecievdTime.getTime()-supplierRecieveTime.getTime();
	 					  long one_hour=3600000;
	 					  if(diff>one_hour)  getCredit=1;
	 				  }
	 				  if(getCredit==1) stmt.executeUpdate("UPDATE biteme.order SET IsLate = 1 WHERE OrderID = "+res[1]);
	 				 db.sendToClient("Order_customer recieved time~"+getCredit,client);
	 			  }
	 			  else db.sendToClient("Order_customer recieved time~cant get order times",client);
	 		  }
	 		  else {
	 			  db.sendToClient("Order_customer recieved time ~failed to update",client);
	 		  }
	 		  
	 			stmt.close();
	 		  }
	 		  catch (Exception e) {
	 			
	 		}
	 		  	  
	 	}
	 	
	 	
	 	

	 	/*
		   * This method updates that the supplier has recieved the order
		   *
		   * @param res[0] used to start function, rest of res for details we need for queries 
		   * @param res[0]=Approve_order, 
		   * @param res[1]= order id
		   * @param client The connection from which the message originated.
		   * @param myCon the connection to mySql DB
		   * @param db the main database controller used in order to send message back to client
		   */
	 	 protected void ApproveOrder(String []res,ConnectionToClient client,Connection myCon,DBController db) {
			  Statement stmt;
			  int flag;
				try {
					stmt = myCon.createStatement();
					
					flag=stmt.executeUpdate(String.format("UPDATE biteme.order SET IsApproved = '1', RecivedOrderTime=CURRENT_TIMESTAMP WHERE OrderID = %d;",Integer.parseInt(res[1])));
					
					if(flag>0)	db.sendToClient("OrderApproved~Updated Successfuly", client);
					else db.sendToClient("OrderApproved~Failed to update", client);
					stmt.close();	
				} catch (SQLException e) {	e.printStackTrace();}
				
		  }
	 	 
		 	/*
		   * This method refund customer at a specified restaurant for being late
		   *
		   * @param res[0] used to start function, rest of res for details we need for queries 
		   * @param res[0]=Refund Account, 
		   * @param res[1]=customer num ,
		   * @param res[2] =refund amount
		   * @param res[3]=restaurant num
		   * @param client The connection from which the message originated.
		   * @param myCon the connection to mySql DB
		   * @param db the main database controller used in order to send message back to client
		   */
	 	protected void refundAccount(String[]res,ConnectionToClient  client,Connection myCon,DBController db)
	 	{
	 		  Statement stmt;
	 		  ResultSet rs;
	 		  int flag=0;
	 		  
	 		  try {
	 			  
	 		  stmt = myCon.createStatement();
	 		 rs = stmt.executeQuery(String.format("SELECT * FROM biteme.refund Where CustomerID='%d' AND RestaurantNum='%d' ;",Integer.parseInt(res[1]),Integer.parseInt(res[3])));
	 		  if(rs.isBeforeFirst())
	 		  {
	 			 flag=stmt.executeUpdate(String.format("UPDATE biteme.refund SET Refund = Refund + '%f'  WHERE CustomerID='%d' AND RestaurantNum='%d';",
	 					 Double.parseDouble(res[2]),Integer.parseInt(res[1]),Integer.parseInt(res[3])));
	 		  }
	 		  else
	 		  {
	 			 flag=stmt.executeUpdate(String.format("INSERT INTO biteme.refund (CustomerID, RestaurantNum, Refund)   VALUES ('%d','%d','%f');",
	 					Integer.parseInt(res[1]),Integer.parseInt(res[3]),Double.parseDouble(res[2])));
	 		  }
	 		  if(flag>0) db.sendToClient("refund updated~Updated Successfully",client);
	 		  else db.sendToClient("refund updated~refund error",client);
	 			stmt.close();
	 		  }
	 		  catch (Exception e) {
	 			 db.sendToClient("refund updated~"+e,client);
	 		}
	 		  
	 	}
	 	/*
		   * This method sets an order to be out for delivery 
		   *
		   * @param  res[0] used to start function, rest of res for details we need for queries 
		   * @param res[0]=Deliver_order,
		   * @param res[1]=orderID 
		   * @param client The connection from which the message originated.
		   * @param myCon the connection to mySql DB
		   * @param db the main database controller used in order to send message back to client
		   */
	 	protected void DeliverOrder(String[]res,ConnectionToClient  client,Connection myCon,DBController db) {
	 		Statement stmt;
	 		  int flag;
	 		  try {
	 		  stmt = myCon.createStatement();
	 		  flag =stmt.executeUpdate(String.format("UPDATE biteme.order SET OutForDelivery =1 WHERE OrderID = '%d';",Integer.parseInt(res[1])));
	 		  db.sendToClient(" updated to deliver~Updated Successfully",client);
	 			stmt.close();
	 		  }
	 		  catch (Exception e) {
	 			
	 		}
	 	}

	 	
	 	/*
		   * This method checks if customer got refund in a specific restaurant
		   *
		   * @param  res[0] used to start function, rest of res for details we need for queries 
		   * @param res[0]=Check_refund,
		   * @param res[1]=customer number
		   * @param res[2]=supplier number 
		   * @param client The connection from which the message originated.
		   * @param myCon the connection to mySql DB
		   * @param db the main database controller used in order to send message back to client
		   */
		public void checkRefund(String[] res, ConnectionToClient client, Connection myCon, DBController db) {
			  Statement stmt;
	 		  ResultSet rs;

	 		  try {
	 			  stmt = myCon.createStatement();
	 			  rs = stmt.executeQuery(String.format("SELECT Refund FROM biteme.refund Where CustomerID='%d' AND RestaurantNum='%d' ;",Integer.parseInt(res[1]),Integer.parseInt(res[2])));
	 			  if(rs.next())
	 			  {
	 				 db.sendToClient("Check_refund~"+rs.getDouble(1),client);
	 			  }
	 			  else
	 				  db.sendToClient("Check_refund~0",client);
	 		  }
	 		  catch (Exception e) {
	 			 db.sendToClient("Check_refund_sql error"+e,client);
			}
	 		  
	 		  
	 	}

		/*
		   * This method updates balance of business account
		   *
		   * @param  res[0] used to start function, rest of res for details we need for queries 
		   * @param res[0]=Update_BaccountBalance,
		   * @param res[1]=business account
		   * @param res[2]=ammount to sub
		   * @param client The connection from which the message originated.
		   * @param myCon the connection to mySql DB
		   * @param db the main database controller used in order to send message back to client
		   */
		public void updateBusinessAccountBalnce(String[] res, ConnectionToClient client, Connection myCon,
				DBController db) {	
		 		Statement stmt;
		 		  int flag;
		 		  try {
		 		  stmt = myCon.createStatement();
		 		  flag =stmt.executeUpdate(String.format("UPDATE biteme.businessaccount SET MonthlyLimit =MonthlyLimit-'%f' WHERE AccountNum = '%d';",Double.parseDouble(res[2]),Integer.parseInt(res[1])));
		 		  db.sendToClient("updated business account balance~Updated Successfully",client);
		 			stmt.close();
		 		  }
		 		  catch (Exception e) {
		 			 db.sendToClient("updated business account balance~"+e,client);
		 		}
		 	}
		/*
		   * This method updates balance of refund for customer
		   *
		   * @param  res[0] used to start function, rest of res for details we need for queries 
		   * @param res[0]=Update_BaccountBalance,
		   * @param res[1]=customer number
		   * @param res[2]=supplier number
		   * @param res[3]=amount to sub
		   * @param client The connection from which the message originated.
		   * @param myCon the connection to mySql DB
		   * @param db the main database controller used in order to send message back to client
		   */
		public void updateRefund(String[] res, ConnectionToClient client, Connection myCon, DBController db) {
			Statement stmt;
	 		  int flag;
	 		  try {
	 		  stmt = myCon.createStatement();
	 		  flag =stmt.executeUpdate(String.format("UPDATE biteme.refund SET Refund = Refund -'%f' WHERE CustomerID = '%d' AND RestaurantNum = '%d';",
	 				  Double.parseDouble(res[3]),Integer.parseInt(res[1]),Integer.parseInt(res[2])));
	 		  db.sendToClient("updated refund~Updated Successfully",client);
	 			stmt.close();
	 		  }
	 		  catch (Exception e) {
	 			 db.sendToClient("updated refund~"+e,client);
	 		}
			
		}

		/*
		   * This method checks if customer inserted correct employer w4c
		   *
		   * @param  res[0] used to start function, rest of res for details we need for queries 
		   * @param res[0]=Check_employer_w4c,
		   * @param res[1]=w4c_num
		   * @param res[2]=user input w4c employer code
		   * @param client The connection from which the message originated.
		   * @param myCon the connection to mySql DB
		   * @param db the main database controller used in order to send message back to client
		   */
		public void checkEmployerW4C(String[] res, ConnectionToClient client, Connection myCon,
				DBController db) {
			  Statement stmt;
	 		  ResultSet rs;
	 		  try {
	 			  stmt = myCon.createStatement();
	 			  rs = stmt.executeQuery(String.format("SELECT EmployerCode FROM biteme.w4c_cards Where CardNum='%d' ;",Integer.parseInt(res[1])));
	 			  if(rs.next())
	 			  {
	 				  int employerCode=rs.getInt(1);
	 				  if(employerCode==Integer.parseInt(res[2]))
	 					  db.sendToClient("Check_employer_w4c_code~true",client);
	 				  else
	 					 db.sendToClient("Check_employer_w4c_code~false",client);
	 			  }
	 			  else
	 				  db.sendToClient("Check_employer_w4c_code~w4c not found",client);
	 		  }
	 		  catch (Exception e) {
	 			 db.sendToClient("Check_employer_w4c_code"+e,client);
			}
			
		}
			
}
	 	
	 	
	 	
	 	

