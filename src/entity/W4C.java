package entity;
/**Entity for a W4C Card details

 *  * @author      Daniel Aibinder
 * @version     1.0                 
 * @since       01.01.2022  */

public class W4C {
	private int cardNum,privateAccount,businessAccount;

	public W4C(int cardNum, int privateAccount, int businessAccount) {
		super();
		this.cardNum = cardNum;
		this.privateAccount = privateAccount;
		this.businessAccount = businessAccount;
	}

	public int getCardNum() {
		return cardNum;
	}

	public void setCardNum(int cardNum) {
		this.cardNum = cardNum;
	}

	public int getPrivateAccount() {
		return privateAccount;
	}

	public void setPrivateAccount(int privateAccount) {
		this.privateAccount = privateAccount;
	}

	public int getBusinessAccount() {
		return businessAccount;
	}

	public void setBusinessAccount(int buisinessAccount) {
		this.businessAccount = buisinessAccount;
	}
	
	
}
