package orderpackage;

import java.io.Serializable;

public class Order implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String restuarant,order_type,phone,address,order_time;
	private int order_num;

	public Order(String restuarant, String order_type, String phone, String address) {
		this.restuarant = restuarant;
		this.order_type = order_type;
		this.phone = phone;
		this.address = address;
	}
	public Order(String restuarant,int order_num,String order_time,String phone, String order_type,  String address ) {
		this.restuarant = restuarant;
		this.order_type = order_type;
		this.phone = phone;
		this.address = address;
		this.order_time = order_time;
		this.order_num=order_num;
	}

	public String getRestuarant() {
		return restuarant;
	}

	public void setRestuarant(String restuarant) {
		this.restuarant = restuarant;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
