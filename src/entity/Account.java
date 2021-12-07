package entity;



public class Account {
	private int accountNum,balance,w4cNum;
	private String type,empName;
	public Account(int accountNum, int balance, int w4cNum, String type, String empName) {
		super();
		this.accountNum = accountNum;
		this.balance = balance;
		this.w4cNum = w4cNum;
		this.type = type;
		this.empName = empName;
	}
	public int getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(int accountNum) {
		this.accountNum = accountNum;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public int getW4cNum() {
		return w4cNum;
	}
	public void setW4cNum(int w4cNum) {
		this.w4cNum = w4cNum;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
}
