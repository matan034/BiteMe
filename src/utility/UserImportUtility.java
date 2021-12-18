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
		User U1=new User("318239639","Danny","Aibinder","dannyaibinder@gmail.com","0546535156","Branch Manager","danny","123",0, "Active",1,"North Branch");
		User U2=new User("313603367","Dorin","Bacher","Dorinbenhur20@gmail.com","0546545155","CEO","dorin","321",0, "Active",2, "Center Branch");
		User U3=new User("207727744","Yeela","Malka","Yeela1231@gmail.com","0546535154","HR","Yeela1231","1231",0, "Active",1, "North Branch");
		User U4=new User("312165913","Matan","Weisberg","Matan034@gmail.com","0546535153","Certified Employee","matan","Aa123456",0, "Frozen",2, "Center Branch");
		User U5=new User("316449495","Muhamad","Abu-asad","M7mad144@gmail.com","05465351522","Supplier","m7","1",0, "Active",3, "South Branch");
		User U6=new User("303245142","Freddy","Kruger","Scray@gmail.com","0546535151","Customer","fred","2",0, "Frozen",3, "South Branch");
		all_users.add(U1);
		all_users.add(U2);
		all_users.add(U3);
		all_users.add(U4);
		all_users.add(U5);
	}
	
	private void Import() {
		for(User u: all_users) {
			StartClient.order.accept("Import_users~"+u.toString());
		}
		
	}
}
