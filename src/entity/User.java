package entity;

import java.io.Serializable;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ID,FirstName,LastName,Email,Phone,Type,UserName,Password,FullName,Status;
	private int IsLoggedIn=0;
	public User(String iD, String firstName, String lastName, String email, String phone, String type, String userName,
			String password,String status) {
		ID = iD;
		FirstName = firstName;
		LastName = lastName;
		Email = email;
		Phone = phone;
		Type = type;
		UserName = userName;
		Password = password;
		FullName=FirstName+" "+LastName;
		Status=status;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getFullName() {
		return FullName;
	}
	public void setFullName(String fullName) {
		FullName = fullName;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public int getIsLoggedIn() {
		return IsLoggedIn;
	}
	public void setIsLoggedIn(int isLoggedIn) {
		IsLoggedIn = isLoggedIn;
	}
	
}
