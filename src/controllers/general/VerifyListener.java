package general;

/**
 * interface for VerifyListener we use this when we want to verify that data is correct by any means (regex, manual checking, etc...)
 * must have a verify function where we return false for verify failed and true for verify passed
 * @author      matan weisberg
 * @version     1.0                
 * @since       01.01.2022         
 */

public interface VerifyListener {
	public boolean verify();
}
