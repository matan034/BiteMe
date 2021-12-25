package entity;

import java.io.Serializable;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ID,FirstName,LastName,Email,Phone,Type,UserName,Password,FullName,Status,StringHomeBranch;
	private int IsLoggedIn=0,homeBranch;
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
	public User(String id,String firstName, String lastName,String mail, String phone,String type,String userName,
			String password,int IsLoggedIn,String status,int homeBranch,String HomeBranch) {
		this.ID=id;
		this.FirstName = firstName;
		this.LastName = lastName;
		this.Email=mail;
		this.Phone=phone;
		this.Type = type;
		this.IsLoggedIn=IsLoggedIn;
		this.Status=status;
		this.homeBranch=homeBranch;
		this.StringHomeBranch=HomeBranch;
		this.UserName=userName;
		this.Password=password;
		
	}
	
	public String getStringHomeBranch() {
		return StringHomeBranch;
	}
	public void setStringHomeBranch(String homeBranch) {
		StringHomeBranch = homeBranch;
	}
	
	public int getHomeBranch() {
		return homeBranch;
	}
	public void setHomeBranch(int homeBranch) {
		this.homeBranch = homeBranch;
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
	@Override
	public String toString() {
		return ID + "~" + FirstName + "~" + LastName + "~" + Email
				+ "~" + Phone + "~" + Type + "~" + UserName + "~" + Password
				+ "~"+ IsLoggedIn+"~"+ Status;
	}
	
	public String toString2() {
		return ID + "~" + FirstName + "~" + LastName + "~" + Email
				+ "~" + Phone + "~" + Type + "~" + UserName + "~" + Password
				+ "~"+ IsLoggedIn+"~"+ Status + "~" + homeBranch ;
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
