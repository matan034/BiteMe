package server;

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
import ocsf.server.ConnectionToClient;

public class DBOrderController {
	
	  /*
	   * This method insert a new order to DB
	   *
	   * @param res  order information res[0]=Insert_order
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
	   * @param res  restaurant number res[0]=Load_orders
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
		  		+ "		  		SELECT *\r\n"
		  		+ "		  		FROM(\r\n"
		  		+ "		  		SELECT account.FirstName, account.LastName,CustomerOrderJoin.SupplyWay, CustomerOrderJoin.RequestOrderTime,CustomerOrderJoin.OrderID\r\n"
		  		+ "		  		FROM biteme.account\r\n"
		  		+ "		  		INNER JOIN (SELECT biteme.order.SupplyWay, biteme.order.RequestOrderTime, customers.PrivateAccount,customers.BusinessAccount,biteme.order.OrderID\r\n"
		  		+ "		  		FROM biteme.order\r\n"
		  		+ "		  		INNER JOIN biteme.customers\r\n"
		  		+ "		  		ON biteme.order.CustomerNumber=customers.CustomerNumber)AS CustomerOrderJoin\r\n"
		  		+ "		  		ON account.AccountNum=CustomerOrderJoin.PrivateAccount OR account.AccountNum=CustomerOrderJoin.businessaccount)AS y;");
		  
		  rs=stmt.executeQuery("SELECT DishesInOrders.DishName, CustomersInOrders.FirstName,CustomersInOrders.LastName,CustomersInOrders.SupplyWay,CustomersInOrders.RequestOrderTime,CustomersInOrders.OrderID\r\n"
		  		+ "FROM biteme.DishesInOrders\r\n"
		  		+ "INNER JOIN biteme.CustomersInOrders\r\n"
		  		+ "ON CustomersInOrders.OrderID=DishesInOrders.OrderNumber;\r\n"
		  		+ "");
		  orders.add("Order");
		  while(rs.next())
		  {
			  
			  String new_order=rs.getString(1)+"~"+rs.getString(2)+"~"+rs.getString(3)+"~"+rs.getString(4)+"~"+rs.getString(5)+"~"+rs.getString(6);
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
		   * @param res  customer number res[0]=Load_Myorders
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
	 					+ "From (SELECT OrderID,ResturantNumber, SupplyWay,RequestOrderTime,IsApproved,IsArrived FROM biteme.order WHERE CustomerNumber="+ res[1]+") as x\r\n"
	 					+ "Inner Join restaurant \r\n"
	 					+ "On restaurant.Number=x.ResturantNumber");
	 			ArrayList<String> myOrders = new ArrayList<>();
	 			myOrders.add("load my orders");
	 			while (rs.next()) {
	 				int orderNum = rs.getInt(1);
	 				String orderType = rs.getString(3);
	 				String orderTime = rs.getString(4);
	 				int isApproved = rs.getInt(5);
	 				int isArrived=rs.getInt(6);
	 				String name=rs.getString(7);
	 				String temp = orderNum + "~" + orderType + "~" + orderTime + "~" + isApproved + "~" + isArrived + "~"+ name;
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
		   * @param res  w4c card number res[0]=W4C_verify
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
		   * @param res  res[0]=W4C_load_list res[1]=PrivateAccount/BusinessAccount res[2]=accountNum
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
		   * @param res  res[0]=Load_dishes res[1]= restaurant number
		   * @param client The connection from which the message originated.
		   * @param myCon the connection to mySql DB
		   * @param db the main database controller used in order to send message back to client
		   */  
	 	protected void loadDishes(String[] res, ConnectionToClient client,Connection myCon,DBController db) {

			Statement stmt;
			ResultSet rs;
			ArrayList<Dish> dishes = new ArrayList<>();
			try {
				stmt = myCon.createStatement();
				rs = stmt.executeQuery("SELECT *\r\n" + "FROM biteme.dishes\r\n" + "INNER JOIN \r\n" + "(\r\n"
						+ "SELECT DishID FROM biteme.dishinmenu WHERE MenuID=\r\n"
						+ "(SELECT MenuID FROM biteme.menu WHERE RestaurantNum=\r\n"
						+ "(SELECT Number FROM biteme.restaurant WHERE Name=\"" + res[1] + "\"))\r\n" + ") as x\r\n"
						+ " ON biteme.dishes.dishID=x.DishID");

				while (rs.next()) {

					int dishID = rs.getInt(1);
					String DishName = rs.getString(2);
					String DishType = rs.getString(3);
					Double price = rs.getDouble(4);
					int chooseSize = rs.getInt(5);
					int chooseCookLvl = rs.getInt(6);
					int chooseExtras = rs.getInt(7);
					String imgSrc = rs.getString(8);
					Dish newDish = new Dish(dishID, chooseSize, chooseCookLvl, chooseExtras, price, DishName, DishType,
							imgSrc);
					dishes.add(newDish);
				}

				db.sendToClient(dishes, client);
				stmt.close();
				rs.close();

			} catch (Exception e) {
				db.sendToClient("Cant Load Menu " + e, client);
			}
		}

	 	/*
		   * This method loads all company branches
		   *
		   * @param res  res[0]=Load_branches
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
		   * @param res  res[0]=Add_dishInOrder, res is the dish details and its order number
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
		   * @param res  res[0]=Order_arrived, res[1]= 1 ,res[2] = order id
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
		   * @param res  res[0]=Load_orderDishes, res[1]= order id
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
						"SELECT x.OrderNumber,x.DishInOrder,x.DishID,x.Size,x.CookingLevel,x.Extras,dishes.DishName,dishes.Price\r\n"
						+ "FROM (SELECT OrderNumber,DishInOrder,DishID,Size,CookingLevel,Extras FROM biteme.dishinorder WHERE OrderNumber="+res[1]+") as x\r\n"
						+ "INNER JOIN dishes ON x.DishID=dishes.DishID;");
				ArrayList<DishInOrder> myOrders = new ArrayList<>();

				while (rs.next()) {
					int orderNum = rs.getInt(1);
					int dishinorder=rs.getInt(2);
					int dishNum = rs.getInt(3);
					
					String size = rs.getString(4);
					String cookinglvl = rs.getString(5);
					String extras = rs.getString(6);
					String dishName = rs.getString(7);
					double dishPrice = rs.getDouble(8);
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
		   * @param res  res[0]=Update_recieve_time, res[1]= order id
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
	 			  rs = stmt.executeQuery("SELECT IsEarlyOrder,RecivedOrderTime,CustomerReciveTime FROM biteme.order WHERE OrderID="+res[1]);
	 			  if(rs.next())
	 			  {
	 				  int isEarlyOrder,getCredit=0;
	 				  String supplierRecieve,CustomerRecievd;
	 				  isEarlyOrder=rs.getInt(1);
	 				  supplierRecieve=rs.getString(2);
	 				  CustomerRecievd=rs.getString(3);
	 				  Date CustomerRecievdTime=new Date(CustomerRecievd);
	 				  Date supplierRecieveTime=new Date(supplierRecieve);
	 				  long diff =supplierRecieveTime.getTime()-CustomerRecievdTime.getTime();
	 			 
	 				  if(isEarlyOrder==1)
	 				  {
	 					     long twenty_minutes=1200000;
	 					     if(diff>twenty_minutes) getCredit=1;
	 				  }
	 				  else
	 				  {
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
		   * @param res  res[0]=Approve_order, res[1]= order id
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
		   * @param res  res[0]=Refund Account, res[1]=refund amount , res[2] account
		   * @param client The connection from which the message originated.
		   * @param myCon the connection to mySql DB
		   * @param db the main database controller used in order to send message back to client
		   */
	 	protected void refundAccount(String[]res,ConnectionToClient  client,Connection myCon,DBController db)
	 	{
	 		  Statement stmt;
	 		  int flag;
	 		  try {
	 		  stmt = myCon.createStatement();
	 		  flag =stmt.executeUpdate(String.format("UPDATE biteme.account SET Balance =Balnce + '%f' WHERE AccountNum = '%d';",Double.parseDouble(res[1]) ,Integer.parseInt(res[2])));
	 		  db.sendToClient("refund updated~Updated Successfully",client);
	 			stmt.close();
	 		  }
	 		  catch (Exception e) {
	 			
	 		}
	 		  
	 	}
	 	
	 	
	 	
}
