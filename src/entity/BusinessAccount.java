package entity;

public class BusinessAccount extends Account{

	private String EmployerName;
	private String budget;

	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public BusinessAccount(Account account) {
		super(account);
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
