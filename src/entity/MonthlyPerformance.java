package entity;

public class MonthlyPerformance {
	private String restaurant;
	private Integer amount_of_orders, delay_supply, served_on_time;

	public MonthlyPerformance(String restaurant, Integer amount_of_orders, Integer delay_supply,
			Integer served_on_time) {
		super();
		this.restaurant = restaurant;
		this.amount_of_orders = amount_of_orders;
		this.delay_supply = delay_supply;
		this.served_on_time = served_on_time;
	}

	public String getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}

	public Integer getAmount_of_orders() {
		return amount_of_orders;
	}

	public void setAmount_of_orders(Integer amount_of_orders) {
		this.amount_of_orders = amount_of_orders;
	}

	public Integer getDelay_supply() {
		return delay_supply;
	}

	public void setDelay_supply(Integer delay_supply) {
		this.delay_supply = delay_supply;
	}

	public Integer getServed_on_time() {
		return served_on_time;
	}

	public void setServed_on_time(Integer served_on_time) {
		this.served_on_time = served_on_time;
	}

}
