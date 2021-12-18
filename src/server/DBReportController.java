package server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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
			rs = stmt.executeQuery(
					"SELECT restaurant.Name, x.Starter,x.Drink,x.Dessert,x.Salad,x.Main\r\n" + "FROM (SELECT * \r\n"
							+ "		FROM biteme.orderbytype\r\n" + "		WHERE BranchID=" + Integer.parseInt(res[1])
							+ ") as x\r\n" + "INNER JOIN restaurant ON x.Restaurant=restaurant.Number;");
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
		  flag =stmt.executeUpdate(String.format("UPDATE biteme.orderbytype\r\n"
		  		+ "SET Starter =Starter+ '%d', Main =Main+ '%d', Salad =Salad+ '%d',Dessert = Dessert+'%d',Drink =Drink+ '%d'\r\n"
		  		+ "WHERE Restaurant = '%d'",Integer.parseInt(res[1]) ,Integer.parseInt(res[2]),Integer.parseInt(res[3]),Integer.parseInt(res[4]),Integer.parseInt(res[5]),Integer.parseInt(res[6])));
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
			restaurants.add("Monthly_performance_load");
			//need to change
			rs = stmt.executeQuery(String.format(
					"SELECT x.Name, biteme.order.RequestOrderTime, biteme.order.CustomerReciveTime,biteme.order.IsEarlyOrder\r\n"
							+ "							FROM (SELECT * FROM biteme.restaurant		WHERE restaurant.BranchNum='%d'\r\n"
							+ "							) as x\r\n"
							+ "							INNER JOIN biteme.order ON x.Number=biteme.order.resturantNumber;",
					Integer.parseInt(res[1])));

			while (rs.next()) {

				restaurants.add(rs.getString(1) + "~" + rs.getString(2) + "~" + rs.getString(3) + "~" + rs.getInt(4));
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
		  try {
		  stmt = myCon.createStatement();
		  flag =stmt.executeUpdate(String.format("UPDATE biteme.data\r\n"
		  		+ "SET Income =Income+ '%f',  OrderCount =OrderCount+ 1\r\n"
		  		+ "WHERE RestaurantNum = '%d'",Double.parseDouble(res[1]) ,Integer.parseInt(res[2])));
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
