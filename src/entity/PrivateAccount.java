package entity;
/**Entity for private account Extends Account

 *  * @author      Daniel Aibinder
 * @version     1.0                 
 * @since       01.01.2022  */
public class PrivateAccount extends Account {

	private String CreditCardNumber;

	public PrivateAccount(Account account) {
		super(account);
	}
	public PrivateAccount(String FirstName,String LastName,String ID,String Telephone,String Email,int accountNum,String creditCard)
	{
		super(FirstName,LastName,ID,Telephone,Email,accountNum);
		this.CreditCardNumber=creditCard;
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
