package entity;

public class MonthlyPerformance {
	private String restaurant,amount_of_orders, delay_supply;;

	public MonthlyPerformance(String restaurant, String amount_of_orders, String delay_supply) {
		super();
		this.restaurant = restaurant;
		this.amount_of_orders = amount_of_orders;
		this.delay_supply = delay_supply;
	}

	public String getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}

	public String getAmount_of_orders() {
		return amount_of_orders;
	}

	public void setAmount_of_orders(String amount_of_orders) {
		this.amount_of_orders = amount_of_orders;
	}

	public String getDelay_supply() {
		return delay_supply;
	}

	public void setDelay_supply(String delay_supply) {
		this.delay_supply = delay_supply;
	}

	
}
