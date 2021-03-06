package entity;

import java.io.Serializable;

public class DishInOrder implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String size,cooking_lvl,extras,dish_name;
	private int dishID,orderNum,dishInOrderNum;
	private double price;
	public DishInOrder(String size, String cooking_lvl, String extras,String dish_name, int dishID, int orderNum,double price,int dishInOrderNum) {
		super();
		this.size = size;
		this.cooking_lvl = cooking_lvl;
		this.extras = extras;
		this.dishID = dishID;
		this.orderNum = orderNum;
		this.dish_name=dish_name;
		this.price=price;
		this.dishInOrderNum=dishInOrderNum;
	}
	@Override
	public String toString() {
	
		if(size==null) size=" ";
		if(cooking_lvl==null) cooking_lvl=" ";
		if(extras==null||extras.equals("")) extras=" ";
		return orderNum+"~"+dishInOrderNum+"~"+dishID+"~"+size+"~"+cooking_lvl+"~"+extras;
	}
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getDishInOrderNum() {
		return dishInOrderNum;
	}
	public void setDishInOrderNum(int dishInOrderNum) {
		this.dishInOrderNum = dishInOrderNum;
	}
	public String getDish_name() {
		return dish_name;
	}

	public void setDish_name(String dish_name) {
		this.dish_name = dish_name;
	}

	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getCooking_lvl() {
		return cooking_lvl;
	}
	public void setCooking_lvl(String cooking_lvl) {
		this.cooking_lvl = cooking_lvl;
	}
	public String getExtras() {
		return extras;
	}
	public void setExtras(String extras) {
		this.extras = extras;
	}
	public int getDishID() {
		return dishID;
	}
	public void setDishID(int dishID) {
		this.dishID = dishID;
	}
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	
}
