package entity;

import java.io.Serializable;

public class DishInRestaurant extends Dish implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int restaurantNumber,dishID,chooseSize,chooseCookingLvl,chooseExtras,imageSize=0;
	private double price;
	private String imageName;
	public  byte[] myImagebytearray;
	
	
	
	
	public DishInRestaurant(int restaurantNumber, int dishID, int chooseSize, int chooseCookingLvl, int chooseExtras,
			int imageSize, double price, String imageName, byte[] myImagebytearray) {
		super(dishID,"","");
		this.restaurantNumber = restaurantNumber;
		this.dishID = dishID;
		this.chooseSize = chooseSize;
		this.chooseCookingLvl = chooseCookingLvl;
		this.chooseExtras = chooseExtras;
		this.imageSize = imageSize;
		this.price = price;
		this.imageName = imageName;
		this.myImagebytearray = myImagebytearray;
	}
	public int getRestaurantNumber() {
		return restaurantNumber;
	}
	public void setRestaurantNumber(int restaurantNumber) {
		this.restaurantNumber = restaurantNumber;
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
	public int getImageSize() {
		return imageSize;
	}
	public void setImageSize(int imageSize) {
		this.imageSize = imageSize;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public byte[] getMyImagebytearray() {
		return myImagebytearray;
	}
	public void setMyImegebytearray(byte[] myImagebytearray) {
		this.myImagebytearray = myImagebytearray;
	}
	
	
}
