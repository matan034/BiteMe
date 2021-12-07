package entity;

public class PrivateAccount extends Account {

	private String CreditCardNumber;

	public PrivateAccount(Account account) {
		super(account);
	}
	
	public String getCreditCardNumber() {
		return CreditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		CreditCardNumber = creditCardNumber;
	}

	@Override
	public String toString() {
		return "Private_account~"+super.toString()+"~"+CreditCardNumber;
	}
}
