package entity;

public class Customer {

	private int customerNumber,account_num;
	private String id,status;
	public Customer(int customerNumber, int account_num,String id,  String status) {
		super();
		this.customerNumber = customerNumber;
		this.id = id;
		this.account_num = account_num;
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
	public int getAccount_num() {
		return account_num;
	}
	public void setAccount_num(int account_num) {
		this.account_num = account_num;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
