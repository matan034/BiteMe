package server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ocsf.server.ConnectionToClient;

public class DBReportController {
	
	
	
	
	
 	/*
   * This method 
   *
   * @param res   
   * @param client The connection from which the message originated.
   * @param myCon the connection to mySql DB
   * @param db the main database controller used in order to send message back to client
   */
	protected void loadComponentsOfOrder(String[] res, ConnectionToClient client,Connection myCon,DBController db) {

		Statement stmt;
		ResultSet rs;
		ArrayList<String> restaurants = new ArrayList<>();
		try {
			stmt = myCon.createStatement();
			restaurants.add("Components_load");
			rs = stmt.executeQuery(String.format(
					"SELECT restaurant.Name, x.Starter,x.Drink,x.Dessert,x.Salad,x.Main\r\n"
					+ " FROM (SELECT * \r\n"
					+ "		FROM biteme.orderbytype WHERE BranchID = '%d' AND Month='%d' AND Year='%d') as x \r\n"
					+ "INNER JOIN restaurant ON x.Restaurant=restaurant.Number;",Integer.parseInt(res[1]),Integer.parseInt(res[2]),Integer.parseInt(res[3])));
			while (rs.next()) {

				restaurants.add(rs.getString(1) + "~" + rs.getInt(2) + "~" + rs.getInt(3) + "~" + rs.getInt(4) + "~"
						+ rs.getInt(5) + "~" + rs.getInt(6));

			}

			db.sendToClient(restaurants, client);

			rs.close();
			stmt.close();
		} catch (Exception e) {

		}
	}
	
	

	protected void CheckMonthYear(int RestaurantNum,Connection myCon,String Type) throws SQLException {
		Statement stmt;
		  int flag=0;
		  int CreateFlag=1;
		  stmt = myCon.createStatement();
		  Calendar c = Calendar.getInstance();
		  int year = c.get(Calendar.YEAR);
		  int month = c.get(Calendar.MONTH)+1;
		  ResultSet rs;
		  if(Type.equals("orderbytype")) {
			  rs = stmt.executeQuery(String.format("SELECT Year,Month FROM biteme.orderbytype WHERE Restaurant='%d';",RestaurantNum));
			  while(rs.next()) {
				  if((rs.getInt(1)==year && rs.getInt(2)==month))
				  		CreateFlag=0;  
			  }
			  if(CreateFlag==1) {
				  flag = stmt.executeUpdate(String.format(
							"INSERT INTO biteme.orderbytype (BranchID, Restaurant,Month,Year) VALUES ((SELECT BranchNum FROM biteme.restaurant WHERE Number='%d'),'%d','%d','%d');",
							RestaurantNum, RestaurantNum,month,year));
			  }
		  }
		  else
		  {
			  rs = stmt.executeQuery(String.format("SELECT Year,Month FROM biteme.data WHERE RestaurantNum='%d';",RestaurantNum));
			  while(rs.next()) {
				  if((rs.getInt(1)==year && rs.getInt(2)==month))
				  		CreateFlag=0;  
			  }
			  if(CreateFlag==1) {
				  flag = stmt.executeUpdate(String.format(
							"INSERT INTO biteme.data (RestaurantNum,Month,Year) VALUES('%d','%d','%d');",
							RestaurantNum,month,year));
			  }
		  }
	}
	
	
	
	
 	/*
   * This method 
   *
   * @param res   
   * @param client The connection from which the message originated.
   * @param myCon the connection to mySql DB
   * @param db the main database controller used in order to send message back to client
   */
	protected void insertItemsAmount(String[] res, ConnectionToClient client,Connection myCon,DBController db) {

		  Statement stmt;
		  int flag;
		  try {
		  stmt = myCon.createStatement();
		  CheckMonthYear(Integer.parseInt(res[1]),myCon,"orderbytype");
		  Calendar c = Calendar.getInstance();
		  int year = c.get(Calendar.YEAR);
		  int month = c.get(Calendar.MONTH)+1;
		  flag =stmt.executeUpdate(String.format("UPDATE biteme.orderbytype\r\n"
		  		+ "SET Starter =Starter+ '%d', Main =Main+ '%d', Salad =Salad+ '%d',Dessert = Dessert+'%d',Drink =Drink+ '%d'\r\n"
		  		+ "WHERE Restaurant = '%d' AND Month='%d' AND Year='%d'",Integer.parseInt(res[2]) ,Integer.parseInt(res[3]),Integer.parseInt(res[4]),Integer.parseInt(res[5]),Integer.parseInt(res[6]),Integer.parseInt(res[1]),month,year));
		  db.sendToClient("update amount~Updated Successfully",client);
			stmt.close();
		  }
		  catch (Exception e) {
			
		}
	}
	
 	/*
   * This method 
   *
   * @param res   
   * @param client The connection from which the message originated.
   * @param myCon the connection to mySql DB
   * @param db the main database controller used in order to send message back to client
   */
	protected void loadMonthlyPerformance(String[] res, ConnectionToClient client,Connection myCon,DBController db) {
		Statement stmt;
		ResultSet rs;
		ArrayList<String> restaurants = new ArrayList<>();
		try {
			stmt = myCon.createStatement();
			if(res[1].equals("performance"))
				restaurants.add("Monthly_performance_load");
			else
				restaurants.add("Incomes_load");
			rs = stmt.executeQuery(String.format(
					"SELECT RestaurantInBranch.Name,data.*\r\n"
					+ "FROM data\r\n"
					+ "INNER JOIN (SELECT restaurant.Number,restaurant.Name\r\n"
					+ "FROM restaurant\r\n"
					+ "WHERE BranchNum='%d')AS RestaurantInBranch\r\n"
					+ "ON RestaurantInBranch.Number=data.RestaurantNum\r\n"
					+ "WHERE Month='%d' AND Year='%d'",
					Integer.parseInt(res[2]),Integer.parseInt(res[3]),Integer.parseInt(res[4])));

			while (rs.next()) {

				restaurants.add(rs.getString(1) + "~" + rs.getString(2) + "~" + rs.getDouble(3) + "~" + rs.getInt(4)+"~"+rs.getInt(5));
				System.out.println(restaurants);
			}
			
			
			db.sendToClient(restaurants, client);

			rs.close();
			stmt.close();
		} catch (Exception e) {

		}

	}
	
	/*
	   * This method 
	   *
	   * @param res   
	   * @param client The connection from which the message originated.
	   * @param myCon the connection to mySql DB
	   * @param db the main database controller used in order to send message back to client
	   */
	protected void updateRestaurantData(String[] res, ConnectionToClient client,Connection myCon,DBController db) {

		  Statement stmt;
		  int flag;
		  Calendar c = Calendar.getInstance();
		  int year = c.get(Calendar.YEAR);
		  int month = c.get(Calendar.MONTH)+1;
		  try {
			CheckMonthYear(Integer.parseInt(res[2]), myCon, "data");
		  stmt = myCon.createStatement();
		  flag =stmt.executeUpdate(String.format("UPDATE biteme.data\r\n"
		  		+ "SET Income =Income+ '%f',  OrderCount =OrderCount+ 1\r\n"
		  		+ "WHERE RestaurantNum = '%d' AND Month='%d' AND Year='%d'",Double.parseDouble(res[1]) ,Integer.parseInt(res[2]),month,year));
		  db.sendToClient("update data~Updated Successfully",client);
			stmt.close();
		  }
		  catch (Exception e) {
			
		}
	}
	
	/*
	   * This method 
	   *
	   * @param res   
	   * @param client The connection from which the message originated.
	   * @param myCon the connection to mySql DB
	   * @param db the main database controller used in order to send message back to client
	   */
	protected void updateSupplierLateCnt(String[]res,ConnectionToClient  client,Connection myCon,DBController db)
	{
		  Statement stmt;
		  int flag;
		  try {
		  stmt = myCon.createStatement();
		  flag =stmt.executeUpdate("UPDATE biteme.data SET CounterIsLate =CounterIsLate + 1 WHERE RestaurantNum = "+res[1] );
		  db.sendToClient("Supplier Late Cnt~Updated Successfully",client);
			stmt.close();
		  }
		  catch (Exception e) {
			
		}
	
}
}
