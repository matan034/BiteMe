package entity;


/**Entity for components rating here we save counters for how many each of each component was ordered

  * @author      Yeela malka
 * @version     1.0                 
 * @since       01.01.2022  */


public class ComponentsRating {
	private String restaurant, starters, drinks, desserts, salads, mains;

	public ComponentsRating(String restaurant, String starters, String drinks, String desserts, String salads,
			String mains) {
		this.restaurant = restaurant;
		this.starters = starters;
		this.drinks = drinks;
		this.desserts = desserts;
		this.salads = salads;
		this.mains = mains;
	}

	public String getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}

	public String getStarters() {
		return starters;
	}

	public void setStarters(String starters) {
		this.starters = starters;
	}

	public String getDrinks() {
		return drinks;
	}

	public void setDrinks(String drinks) {
		this.drinks = drinks;
	}

	public String getDesserts() {
		return desserts;
	}

	public void setDesserts(String desserts) {
		this.desserts = desserts;
	}

	public String getSalads() {
		return salads;
	}

	public void setSalads(String salads) {
		this.salads = salads;
	}

	public String getMains() {
		return mains;
	}

	public void setMains(String mains) {
		this.mains = mains;
	}
}
