package entity;
/**Entity for restaurant income data from DB

 *  * @author      Daniel Aibinder
 * @version     1.0                 
 * @since       01.01.2022  */

public class TotalIncomesOfRestaurants {
	private String restaurant,total_income;

	public String getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}

	public String getTotal_income() {
		return total_income;
	}

	public void setTotal_income(String total_income) {
		this.total_income = total_income;
	}

	public TotalIncomesOfRestaurants(String restaurant, String total_income) {
		super();
		this.restaurant = restaurant;
		this.total_income = total_income;
	}

	
	
}
