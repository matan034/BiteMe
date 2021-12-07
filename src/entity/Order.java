package entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String order_type,delivery_method,phone,recieving_name,buisness_name,street,city,zip,order_time;
	private Branch branch;
	private int order_num,is_early_order=0;
	private Double price=0.0;
	
	private ArrayList<DishInOrder> dishes=new ArrayList<DishInOrder>();
	
	public Order( String order_type) {
		this.order_type = order_type;
	}

	
	public int getIs_early_order() {
		return is_early_order;
	}


	public void setIs_early_order(int is_early_order) {
		this.is_early_order = is_early_order;
	}


	public String getDelivery_method() {
		return delivery_method;
	}


	public void setDelivery_method(String delivery_method) {
		this.delivery_method = delivery_method;
	}


	public String getRecieving_name() {
		return recieving_name;
	}


	public void setRecieving_name(String recieving_name) {
		this.recieving_name = recieving_name;
	}


	public String getBuisness_name() {
		return buisness_name;
	}


	public void setBuisness_name(String buisness_name) {
		this.buisness_name = buisness_name;
	}


	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getZip() {
		return zip;
	}


	public void setZip(String zip) {
		this.zip = zip;
	}


	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public boolean addDish(DishInOrder dish)
	{
		price+=dish.getPrice();
		return dishes.add(dish);
	}
	public ArrayList<DishInOrder> getDishes()
	{
		return dishes;
	}
	public boolean removeDish(DishInOrder dish)
	{
		price-=dish.getPrice();
		return dishes.remove(dish);
	}
	

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}



	public Branch getBranch() {
		return branch;
	}


	public void setBranch(Branch branch) {
		this.branch = branch;
	}


	public String getOrder_time() {
		return order_time;
	}

	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}

	public int getOrder_num() {
		return order_num;
	}

	public void setOrder_num(int order_num) {
		this.order_num = order_num;
	}
	
	
}
