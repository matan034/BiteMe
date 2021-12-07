package entity;

import java.io.Serializable;

public class Dish implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int dishID,chooseSize,chooseCookingLvl,chooseExtras;
	private double price;
	private String name,type;
	

	public Dish(int dishID, int chooseSize, int chooseCookingLvl, int chooseExtras, double price, String name,
			String type) {
		super();
		this.dishID = dishID;
		this.chooseSize = chooseSize;
		this.chooseCookingLvl = chooseCookingLvl;
		this.chooseExtras = chooseExtras;
		this.price = price;
		this.name = name;
		this.type = type;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}
	public int getDishID() {
		return dishID;
	}
	public void setDishID(int dishID) {
		this.dishID = dishID;
	}
	public int getChooseSize() {
		return chooseSize;
	}
	public void setChooseSize(int chooseSize) {
		this.chooseSize = chooseSize;
	}
	public int getChooseCookingLvl() {
		return chooseCookingLvl;
	}
	public void setChooseCookingLvl(int chooseCookingLvl) {
		this.chooseCookingLvl = chooseCookingLvl;
	}
	public int getChooseExtras() {
		return chooseExtras;
	}
	public void setChooseExtras(int chooseExtras) {
		this.chooseExtras = chooseExtras;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
