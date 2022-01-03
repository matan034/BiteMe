package utility;

import java.util.ArrayList;
import java.util.List;

import clients.StartClient;
import entity.User;

public class UserImportUtility {
	
	private List<User> all_users=new ArrayList<>();
	
	public void ImportUsers() {
		CreateUsers();
		Import();
	}
	
	
	private void CreateUsers() {
		//ceo
		all_users.add(new User("313603367","Dorin","Bacher","Dorinbenhur20@gmail.com","0546545155","CEO","dorin","321",0, "Active",2, "Center Branch"));
		
		//branch Manager
		all_users.add(new User("318239639","Danny","Aibinder","dannyaibinder@gmail.com","0546535156","Branch Manager","danny","123",0, "Active",1,"North Branch"));
		all_users.add(new User("316449495","Muhamad","Abu-asad","M7mad144@gmail.com","05465351522","Branch Manager","m7","1",0, "Active",3, "South Branch"));
		all_users.add(new User("312121232","Avi","Cohen","avi@gmail.com","0542587413","Branch Manager","avi","123",0, "Active",2, "Center Branch"));
		
		//certified employee
		all_users.add(new User("312165913","Matan","Weisberg","Matan034@gmail.com","0546535153","Certified Employee","matan","Aa123456",0, "Active",2, "Center Branch","333333333"));
		all_users.add(new User("325412856","Dana","Gantz","dana@gmail.com","0546524123","Certified Employee","dana","123",0, "Active",3, "South Branch","444444444"));
		
		//customer
		all_users.add(new User("303245142","Freddy","Kruger","Scray@gmail.com","0546535151","Base User","fred","2",0, "Active",3, "South Branch"));
		all_users.add(new User("325472653","Kobi","Maor","kobi@gmail.com","0542536521","Base User","kobi","123",0, "Active",2, "Center Branch"));
		
		
		//hr
		all_users.add(new User("207727744","Yeela","Malka","Yeela1231@gmail.com","0546535154","HR","yeela","123",0, "Active",1, "North Branch"));
		all_users.add(new User("201526321","Reut","Melamed","reut@gmail.com","0585214236","HR","reut","123",0, "Active",2, "Center Branch"));
		all_users.add(new User("254256254","Kochava","Yarkoni","kochava@gmail.com","058421523","HR","kochava","123",0, "Active",3, "South Branch"));
		
		//restaurants
		all_users.add(new User("111111111","McDonalds","Company","McDonalds@gmail.com","0546535151","Base User","mc","123",0, "Active",3, "South Branch"));
		all_users.add(new User("222222222","KFC","Company","KFC@gmail.com","0542576532","Base User","kfc","123",0, "Active",1, "North Branch"));
		all_users.add(new User("666666666","BP","Company","bp@gmail.com","054254853","Base User","bp","123",0, "Active",1, "North Branch"));
		all_users.add(new User("333333333","Dominos","Company","dominos@gmail.com","0542542635","Base User","dominos","123",0, "Active",2, "Center Branch"));
		all_users.add(new User("777777777","Gordos","Company","gordos@gmail.com","0542123252","Base User","gordos","123",0, "Active",2, "Center Branch"));
		all_users.add(new User("444444444","BBB","Company","bbb@gmail.com","0585214529","Base User","bbb","123",0, "Active",3, "South Branch"));
		all_users.add(new User("555555555","Nafis","Company","Nafis@gmail.com","0541234567","Base User","nafis","123",0, "Active",3, "South Branch"));
		
		
		

	}
	
	private void Import() {
		for(User u: all_users) {
			StartClient.order.accept("Import_users~"+u.toString2());
			
		}
		for(User u: all_users) {		
			if(u.getType().equals("Certified Employee"))StartClient.order.accept("Create_certifies_employee~"+u.getID()+"~"+u.getSupplier()); 
		}
		
	}
}
