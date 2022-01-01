package entity;

import java.io.Serializable;

public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer customerNumber,pAccount=0,bAccount=0;
	private String id,status,name,stringPaccount,stringBaccount;
	public Customer(int customerNumber, int pAccount,int bAccount,String id,  String status) {
		super();
		this.customerNumber = customerNumber;
		this.id = id;
		this.pAccount = pAccount;
		this.bAccount = bAccount;
		this.status = status;
	}
	
	
	public Customer(int customerNumber,String id, String status,int pAccount, int bAccount, String name) {
		super();
		this.customerNumber = customerNumber;
		this.pAccount = pAccount;
		this.bAccount = bAccount;
		this.id = id;
		this.status = status;
		this.name = name;
		
	}
	public Customer(int customerNumber,String id, String status,String stringPaccount,String stringBaccount, String name) {
		super();
		this.customerNumber = customerNumber;
		this.stringPaccount = stringPaccount;
		this.stringBaccount = stringBaccount;
		this.id = id;
		this.status = status;
		this.name = name;
		
	}


	public String getStringPaccount() {
		return stringPaccount;
	}


	public void setStringPaccount(String stringPaccount) {
		this.stringPaccount = stringPaccount;
	}


	public String getStringBaccount() {
		return stringBaccount;
	}


	public void setStringBaccount(String stringBaccount) {
		this.stringBaccount = stringBaccount;
	}


	public void setpAccount(Integer pAccount) {
		this.pAccount = pAccount;
	}


	public void setbAccount(Integer bAccount) {
		this.bAccount = bAccount;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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
