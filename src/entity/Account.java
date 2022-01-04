package entity;


/**Entity for an Account used for private account and business account to inherit from
 * @param FirstName = Accounts first name
 * @param LastName= Accounts last name
 * @param Telephone= Accounts telephone
 * @param Email = Accounts Email
 * @param Balace = Accounts Balance
 * @param w4cNum = accounts w4C 
 * @param acccountNum = accounts Num according to database*/


public class Account {
	
	private String FirstName,LastName,ID,Telephone,Email;
	private Double Balance=0.0;
	int w4cNum,accountNum;
	
	public Account(String FirstName,String LastName,String ID,String Telephone,String Email,int accountNum) {
		this.FirstName=FirstName;
		this.LastName=LastName;
		this.ID=ID;
		this.Telephone=Telephone;
		this.Email=Email;
		this.accountNum=accountNum;
	}
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

	public Account(String FirstName,String LastName,String ID) {
		this.FirstName=FirstName;
		this.LastName=LastName;
		this.ID=ID;
	}
	
	public int getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(int accountNum) {
		this.accountNum = accountNum;
	}
	public int getW4cNum() {
		return w4cNum;
	}
	public void setW4cNum(int w4cNum) {
		this.w4cNum = w4cNum;
	}
	public double getBalance() {
		return Balance;
	}

	public void setBalance(double balance) {
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
