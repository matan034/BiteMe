package entity;



/**Entity for a business account inherits account details from account
 * @param EmployerName= business accounts employer the account is registered to
 * @param budget= the accounts budget
 * @param isApproved= flag to check if the business account is approved
 * @param employerNum= employers num according to DB
 * @author      Daniel Aibinder
 * @version     1.0                 
 * @since       01.01.2022  */



public class BusinessAccount extends Account{

	private String EmployerName;
	private double budget;
	private int isApproved,employerNum;
	
	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public BusinessAccount(Account account) {
		super(account);
	}
	public BusinessAccount(int accountnum,String firstName,String lastName,String ID,String phone,String mail,int employerNum,
			double monthlyLimit,int isApproved,String EmployerName) {
		super(firstName,lastName,ID,phone,mail,accountnum);
		this.budget=monthlyLimit;
		this.employerNum=employerNum;
		this.isApproved=isApproved;
		this.EmployerName=EmployerName;
	}
	
	
	public int getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(int isApproved) {
		this.isApproved = isApproved;
	}

	public int getEmployerNum() {
		return employerNum;
	}

	public void setEmployerNum(int employerNum) {
		this.employerNum = employerNum;
	}

	public String getEmployerName() {
		return EmployerName;
	}
	public void setEmployerName(String employerName) {
		EmployerName = employerName;
	}
	@Override
	public String toString() {
		return "Business_account~"+super.toString()+"~"+EmployerName+"~"+budget;
	}
	
	
}
