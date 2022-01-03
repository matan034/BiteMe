package general;

/**
 * interface for MyListener we use MyListener when we want to move data between controllers 
 * every MyListener must have an onClickListener that defines what to do when an action occurs
 * @author      matan weisberg
 * @version     1.0                
 * @since       01.01.2022         
 */

public interface MyListener {
	public void onClickListener(Object object);
}
