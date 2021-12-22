package entity;

public class TotalIncomesOfRestaurants {
	private String restaurant;
	private Double total_income;
	public String getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}
	public Double getTotal_income() {
		return total_income;
	}
	public void setTotal_income(Double total_income) {
		this.total_income = total_income;
	}
	public TotalIncomesOfRestaurants(String restaurant, Double total_income) {
		super();
		this.restaurant = restaurant;
		this.total_income = total_income;
	}
	
	
	
}
