package utility;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import clients.StartClient;
import entity.User;
import server.ServerStart;


/**
 * Class to use for importing users utility this class simulates an outer system importing users to our DB users table
 * @author      daniel aibinder
 * @version     1.0               
 * @since       01.01.2022        
 */

public class UserImportUtility {
	
	/** a list of all users that we wish to import*/
	private List<User> all_users=new ArrayList<>();
	
	/**
	 * func that activates CreateUsers and Import we use this func outside the class for an easy 1 func actiavtion of import utility*/
	public void ImportUsers() {
		CreateUsers();
		Import();
	}
	
	/**
	 * this func creates new users and adds them to all_users list*/
	private void CreateUsers() {
		//ceo
		all_users.add(new User("313603367","Dorin","Bacher","Dorinbenhur20@gmail.com","0546545155","CEO","dorin","321",0, "Active",2, "Center Branch"));
		
		//branch Manager
		all_users.add(new User("318239639","Danny","Aibinder","dannyaibinder@gmail.com","0546535156","Branch Manager","danny","123",0, "Active",1,"North Branch"));
		all_users.add(new User("316449495","Muhamad","Abu-asad","M7mad144@gmail.com","05465351522","Branch Manager","m7","1",0, "Active",3, "South Branch"));
		all_users.add(new User("312121232","Avi","Cohen","avi@gmail.com","0542587413","Branch Manager","avi","123",0, "Active",2, "Center Branch"));
		
		//certified employee
		all_users.add(new User("257846593","Shai","Rozen","shai@gmail.com","0542635241","Certified Employee","shai","123",0, "Active",1, "North Branch","222222222"));
		all_users.add(new User("312165913","Matan","Weisberg","Matan034@gmail.com","0546535153","Certified Employee","matan","Aa123456",0, "Active",2, "Center Branch","333333333"));
		all_users.add(new User("325412856","Dana","Gantz","dana@gmail.com","0546524123","Certified Employee","dana","123",0, "Active",3, "South Branch","444444444"));
		all_users.add(new User("252624251","Alon","Katz","alon@gmail.com","0585645241","Certified Employee","alon","123",0, "Active",3, "South Branch","555555555"));
		
		//customer
		all_users.add(new User("303245142","Freddy","Kruger","Scray@gmail.com","0546535151","Base User","fred","2",0, "Active",3, "South Branch"));
		all_users.add(new User("369852741","Adir","Kadosh","adir@gmail.com","0525485216","Base User","adir","123",0, "Active",3, "South Branch"));
		all_users.add(new User("325472653","Kobi","Maor","kobi@gmail.com","0542536521","Base User","kobi","123",0, "Active",2, "Center Branch"));
		all_users.add(new User("369147258","Idan","Amado","idan@gmail.com","0515235241","Base User","idan","123",0, "Active",2, "Center Branch"));
		all_users.add(new User("258147369","Gabriel","Lopa","gabi@gmail.com","0525623698","Base User","gabi","123",0, "Active",1, "North Branch"));
		all_users.add(new User("369258147","Anat","Moalem","anat@gmail.com","0565241523","Base User","anat","123",0, "Active",1, "North Branch"));
		all_users.add(new User("258147369","Sapir","Cohen","sapir@gmail.com","0535625418","Base User","sapir","123",0, "Active",1, "North Branch"));
		
		
		//hr
		all_users.add(new User("207727744","Yeela","Malka","Yeela1231@gmail.com","0546535154","HR","yeela","123",0, "Active",1, "North Branch"));
		all_users.add(new User("285456123","Ronit","Yafa","ronit@gmail.com","0586523241","HR","ronit","123",0, "Active",1, "North Branch"));
		all_users.add(new User("356231425","Omer","Katz","omer@gmail.com","0546802541","HR","omer","123",0, "Active",1, "North Branch"));
		all_users.add(new User("201526321","Reut","Melamed","reut@gmail.com","0585214236","HR","reut","123",0, "Active",2, "Center Branch"));
		all_users.add(new User("254653284","Anna","Weis","anna@gmail.com","0545266884","HR","anna","123",0, "Active",2, "Center Branch"));
		all_users.add(new User("254256254","Kochava","Yarkoni","kochava@gmail.com","058421523","HR","kochava","123",0, "Active",3, "South Branch"));
		all_users.add(new User("312528454","Moshe","Pereg","moshe@gmail.com","0502653285","HR","moshe","123",0, "Active",3, "South Branch"));
		
		//restaurants
		all_users.add(new User("111111111","McDonalds","Company","McDonalds@gmail.com","0546535151","Base User","mc","123",0, "Active",3, "South Branch"));
		all_users.add(new User("222222222","KFC","Company","KFC@gmail.com","0542576532","Base User","kfc","123",0, "Active",1, "North Branch"));
		all_users.add(new User("666666666","BP","Company","bp@gmail.com","054254853","Base User","bp","123",0, "Active",1, "North Branch"));
		all_users.add(new User("333333333","Dominos","Company","dominos@gmail.com","0542542635","Base User","dominos","123",0, "Active",2, "Center Branch"));
		all_users.add(new User("777777777","Gordos","Company","gordos@gmail.com","0542123252","Base User","gordos","123",0, "Active",2, "Center Branch"));
		all_users.add(new User("444444444","BBB","Company","bbb@gmail.com","0585214529","Base User","bbb","123",0, "Active",3, "South Branch"));
		all_users.add(new User("555555555","Nafis","Company","Nafis@gmail.com","0541234567","Base User","nafis","123",0, "Active",3, "South Branch"));
		
		
		

	}
	/**
	 * func to send users to import to database, it also maps certified employes to the restuarant they work in */
	private void Import() {
		ArrayList<User> certified_employee=new ArrayList<User>();
		for(User u: all_users) {	
			String []res= u.toString2().split("~");
			if(u.getType().equals("Certified Employee")) certified_employee.add(u);
			try {
				ServerStart.sv.importUsers(res);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		for(User u: certified_employee) {	
			
			String[]res= {u.getID(),u.getSupplier()};
				ServerStart.sv.createCertifiedEmployee(res); 
		}
		
	}
}
