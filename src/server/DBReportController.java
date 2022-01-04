package server;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import clients.OrderClient;
import common.Globals;
import entity.IntakeOrder;
import entity.MyFile;
import ocsf.server.ConnectionToClient;

/**
 * This class is used for all functions relating to reports, creating a report, loading an existing report, sending a pdf on so on
 * Used in src/gui/report  with the controllers in src/controllers/report 
 * @author      Yeela Malka 
 * @version     1.0                 
 * @since       01.01.2022         
 */

public class DBReportController {
	
	
	
	
	
 	/**
   * This method loads data on a restaurants amount of items ordered in a specific restaurant in a specific branch,month,year
   *
   * @param res  res[0] used to start function, rest of res for details we need for queries 
   * @param res[0]=Load_components
   * @param res[1]=branchID
   * @param res[2]=month
   * @param res[3]=year
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
	
	/**
	   *
	  *This method check insert into DB for reports a new row when new month
	   * @param RestaurantNum 
	   * @param myCon the connection to mySql DB
	   * @param Type flag to check which table to get

	   */

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
	
	
	
	
 	/**
   * This method updates data for report, each time a dish is ordered we update the counters we later pull to display for amount ordered by type
   *
   * @param res res[0] used to start function, rest of res for details we need for queries 
   * @param res[0]=Insert_quantity
   * @param res[1]=ordertype
   * @param res[2]=amount starters ordered,
   * @param res[3]=amount main ordered,
   * @param res[4]=amount salad ordered,
   * @param res[5]=amount desert ordered,
   * @param res[6]=amount drinks ordered
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
	
 	/**
   * This method loads monthly perforamance or income report for a specific restaurant in a specific branch,month and year
   *
   * @param res  res[0] used to start function, rest of res for details we need for queries,
   * @param res[0]=Load_monthly_performance,
   * @param res[1]=string to choose if we want performance or income report,
   * @param res[2]=branchNum,
   * @param res[3]=month,
   * @param res[4]=year
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
	
	/**
	   * This method updates restaurant data for income, amount of orders
	   *
	   * @param res  res[0] used to start function, rest of res for details we need for queries 
	   * @param res[0]=updateRestaurantData 
	   * @param res[1]=income,@param 
	   * res[2]=restaurantnum
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
	
	/**
	   * This method is used to update amount of late orders delivered
	   *
	   * @param res[0] used to start function, rest of res for details we need for queries 
	   * @param res[0]=Update Supplier Late Cnt 
	   * @param res[1]=restaurantnum   
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
		  
	/**
	   * This method is used to get quarterly data for reports
	   *
	   * @param res[0] used to start function, rest of res for details we need for queries 
	   * @param res[0]= Load_quarter_data 
	   * @param res[1]=quarter start, 
	   * @param res[2]=quarter end ,
	   * @param res[3]=year,
	   * @param res[4]=restaurantNum   
	   * @param client The connection from which the message originated.
	   * @param myCon the connection to mySql DB
	   * @param db the main database controller used in order to send message back to client
	   */
		protected void getSupplierQuarterData(String[]res,ConnectionToClient  client,Connection myCon,DBController db)
		{
			  Statement stmt;
			  int flag;
			  ResultSet rs;
			  try {
			  stmt = myCon.createStatement();
			  rs = stmt.executeQuery(String.format(
						"SELECT Name,x.* FROM biteme.restaurant \r\n"
						+ "INNER JOIN (SELECT SUM(OrderCount),SUM(Income),RestaurantNum FROM biteme.data WHERE  Month>='%d' AND Month<='%d' AND Year='%d' AND RestaurantNum='%d') as x\r\n"
						+ "ON x.RestaurantNum=Restaurant.Number",
						Integer.parseInt(res[1]),Integer.parseInt(res[2]),Integer.parseInt(res[3]),Integer.parseInt(res[4])));
			  if(rs.next())
				  db.sendToClient("Supplier Quarter Data~"+rs.getString(2)+"~"+rs.getString(3),client);
			  else db.sendToClient("Supplier Quarter Data~0~0", client);
				stmt.close();
				rs.close();
			  }
			  catch (Exception e) {
				
			}
		}
		/**
		   * This method is used to upload a report branch manager sends to CEO
		   *
		   * @param msg our report using Entity MyFile 
		   * @param client The connection from which the message originated.
		   * @param myCon the connection to mySql DB
		   * @param db the main database controller used in order to send message back to client
		   */
			  
		protected void UploadReport(Object msg,ConnectionToClient  client,Connection myCon,DBController db)
		{ 
			MyFile msgfile =((MyFile)msg); 
			  try {
			  Calendar c = Calendar.getInstance();
			  int year = c.get(Calendar.YEAR);
			  int month = c.get(Calendar.MONTH)+1;
			  int quater=0;
			  if(month>=1 && month<=3)quater=1;
			  if(month>=4 && month<=6)quater=2;
			  if(month>=7 && month<=9)quater=3;
			  if(month>=10 && month<=12)quater=4;
			  MyFile in = (MyFile)msg;		
				 PreparedStatement preparedStmt;
				 String query="INSERT INTO biteme.reports (Quarter,Year,BranchID,ReportBlob) VALUES(?,?,?,?);";
			     preparedStmt = myCon.prepareStatement(query);
			     preparedStmt.setInt (1,quater);
			     preparedStmt.setInt (2,year);
			     preparedStmt.setInt (3,msgfile.getBranchID());
			     FileInputStream input = new FileInputStream(in.getFile());
			     preparedStmt.setBinaryStream(4,input ) ;
			      preparedStmt.execute();	
				db.sendToClient("Uploaded sucescully~", client);
				preparedStmt.close();
			  }catch(Exception e) {db.sendToClient(e, client);}
			  
		}  
		
		
		/**
		   * This method is used to get our report pdf and display it to CEO
		   *
		   * @param res[0] used to start function, rest of res for details we need for queries 
		   * @param res[0]= Open_pdf
		   * @param res[1]=quarter, 
		   * @param res[2]=year ,
		   * @param res[3]=branchID 
		   * @param client The connection from which the message originated.
		   * @param myCon the connection to mySql DB
		   * @param db the main database controller used in order to send message back to client
		   */
		protected void getBlob(String[] res,ConnectionToClient  client,Connection myCon,DBController db) {
			  ResultSet rs;
			  PreparedStatement preparedStmt;
			  try {
				 String query="SELECT ReportBlob FROM biteme.reports WHERE Quarter=? AND Year=? AND BranchID=?";
			     preparedStmt = myCon.prepareStatement(query);
			     preparedStmt.setInt (1,Integer.parseInt(res[1]));
			     preparedStmt.setInt (2,Integer.parseInt(res[2]));
			     preparedStmt.setInt (3,Integer.parseInt(res[3]));
			     rs= preparedStmt.executeQuery();	
			  if(rs.next())
			  {
				  
				String name="..\\BiteMe\\src\\gui\\quartelyreports\\Branch"+res[3]+"_Quarter"+res[1]+"_Year"+res[2]+".pdf";
				  InputStream input = rs.getBinaryStream(1);
				  MyFile out=new MyFile(name);
				  File temp=new File(out.getFileName());
					 FileOutputStream output = new FileOutputStream(temp);
					 byte[] buffer = new byte[1024];
					 while (input.read(buffer) > 0) {
					        output.write(buffer);		        
					  }
					 out.setFile(temp);
					 output.close();
					 input.close();
				 db.sendToClient(out,client);
				 output.close();
				 input.close();

			  }
		
				  
			  else db.sendToClient("PdfPath~0", client);
				preparedStmt.close();
				rs.close();
			  }
			  catch (Exception e) {
				  db.sendToClient("PdfPath~"+e, client);
			}
			  }
		/**
		   * This method is used to get how much a supplier made during a specific month and year 
		   *
		   * @param res[0] used to start function, rest of res for details we need for queries 
		   * @param res[0]= Load_myIntake 
		   * @param res[1]=restaurantnum,
		   * @param res[2]= year from user ,
		   * @param res[3]=month from user
		   * @param client The connection from which the message originated.
		   * @param myCon the connection to mySql DB
		   * @param db the main database controller used in order to send message back to client
		   */
		protected void getSupplierIntake(String[] res,ConnectionToClient  client,Connection myCon,DBController db) {
			 Statement stmt;
			  ArrayList<IntakeOrder> orders=new ArrayList<IntakeOrder>();
			  ResultSet rs;
			  try {
				  stmt = myCon.createStatement();
				  rs = stmt.executeQuery("SELECT OrderID,Price,RequestOrderTime FROM biteme.order Where ResturantNumber="+res[1]);
				  while(rs.next())
				  {
					  String [] temp=rs.getString(3).split("-");
					  //temp[1]=month from db,res[3]=month from user
					  //temp[0].split(" ")[1]=year from db, res[2]= year from user
					  if(temp[1].equals(res[3])&&temp[0].split(" ")[1].equals(res[2]))
					  {
						  orders.add(new IntakeOrder(rs.getInt(1), rs.getDouble(2)));
					  }	
				  }
				  db.sendToClient(orders, client);
			  }
			  catch (Exception e) {
				  db.sendToClient(e, client);
			}
		}
			
		
}
		 	 






