package entity;

import java.io.Serializable;

public class Branch implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int branchID;
	private String branch_name;
	public Branch(int branchID, String branch_name) {
		super();
		this.branchID = branchID;
		this.branch_name = branch_name;
	}
	public int getBranchID() {
		return branchID;
	}
	public void setBranchID(int branchID) {
		this.branchID = branchID;
	}
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return branch_name;
	}
	
}
