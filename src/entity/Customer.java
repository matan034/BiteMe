package entity;

public class Customer {

	private int customerNumber,pAccount,bAccount;
	private String id,status;
	public Customer(int customerNumber, int pAccount,int bAccount,String id,  String status) {
		super();
		this.customerNumber = customerNumber;
		this.id = id;
		this.pAccount = pAccount;
		this.bAccount = bAccount;
		
		this.status = status;
	}
	public int getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(int customerNumber) {
		this.customerNumber = customerNumber;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getpAccount() {
		return pAccount;
	}
	public void setpAccount(int pAccount) {
		this.pAccount = pAccount;
	}
	public int getbAccount() {
		return bAccount;
	}
	public void setbAccount(int bAccount) {
		this.bAccount = bAccount;
	}
	
	
}
