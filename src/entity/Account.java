package entity;

public class Account {
	
	private String FirstName,LastName,ID,Telephone,Email,w4cNum;
	private int Balance=0;

	public Account(String FirstName,String LastName,String ID,String Telephone,String Email) {
		this.FirstName=FirstName;
		this.LastName=LastName;
		this.ID=ID;
		this.Telephone=Telephone;
		this.Email=Email;
	}
	public Account(Account account) {
		this.FirstName=account.getFirstName();
		this.LastName=account.getLastName();
		this.ID=account.getID();
		this.Telephone=account.getTelephone();
		this.Email=account.getEmail();
	}

	
	public String getW4cNum() {
		return w4cNum;
	}
	public void setW4cNum(String w4cNum) {
		this.w4cNum = w4cNum;
	}
	public int getBalance() {
		return Balance;
	}

	public void setBalance(int balance) {
		Balance = balance;
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

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getTelephone() {
		return Telephone;
	}

	public void setTelephone(String telephone) {
		Telephone = telephone;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}
	@Override
	public String toString() {
		return FirstName+ "~" +LastName + "~" + ID + "~" + Telephone
				+ "~" + Email;

	}
}
