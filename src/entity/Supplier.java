package entity;

import java.io.Serializable;

public class Supplier implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int supplierNum,branchNum,isApproved;
	private String name,address,city,type,manager;
	public Supplier(int supplierNum, int branchNum, int isApproved, String name, String address, String city,
			String type, String manager) {
		super();
		this.supplierNum = supplierNum;
		this.branchNum = branchNum;
		this.isApproved = isApproved;
		this.name = name;
		this.address = address;
		this.city = city;
		this.type = type;
		this.manager = manager;
	}
	@Override
	public String toString() {
		
		return name;
	}
	public int getSupplierNum() {
		return supplierNum;
	}
	public void setSupplierNum(int supplierNum) {
		this.supplierNum = supplierNum;
	}
	public int getBranchNum() {
		return branchNum;
	}
	public void setBranchNum(int branchNum) {
		this.branchNum = branchNum;
	}
	public int getIsApproved() {
		return isApproved;
	}
	public void setIsApproved(int isApproved) {
		this.isApproved = isApproved;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	
}
