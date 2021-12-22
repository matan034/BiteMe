package entity;

import java.io.Serializable;

public class Dish implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int dishID,chooseSize,chooseCookingLvl,chooseExtras,imageSize=0,addDirectllyToMenu,restaurantNumber;
	private double price;
	private String name,type,imgSrc,imageName;
	public  byte[] myImegebytearray;
	
	public Dish(int dishID, String name,String type, double price, int chooseSize, int chooseCookingLvl, int chooseExtras,
			String imegeName,byte[] myImegebytearray,int imegeSize,int addDirectllyToMenu,int restaurantNumber) {
		super();
		this.imageName=imegeName;
		this.dishID = dishID;
		this.chooseSize = chooseSize;
		this.chooseCookingLvl = chooseCookingLvl;
		this.chooseExtras = chooseExtras;
		this.price = price;
		this.name = name;
		this.type = type;
		this.myImegebytearray = new byte [(int)myImegebytearray.length];
		this.myImegebytearray=myImegebytearray;
		this.imageSize=imegeSize;
		this.addDirectllyToMenu=addDirectllyToMenu;
		this.restaurantNumber=restaurantNumber;
	}
	public Dish(int dishID, int chooseSize, int chooseCookingLvl, int chooseExtras, double price, String name,String type,String imgSrc) {
		super();
		this.dishID = dishID;
		this.chooseSize = chooseSize;
		this.chooseCookingLvl = chooseCookingLvl;
		this.chooseExtras = chooseExtras;
		this.price = price;
		this.name = name;
		this.type = type;
		this.imgSrc=imgSrc;
	}
	@Override
	public String toString() {
		//String dish="";
		//dish+=this.getDishID();
		//dish= this.getDishID()+" "+this.getName()+" "+this.getPrice()+" "+this.getType()+" "+this.getChooseSize()+" "+this.getChooseCookingLvl()+" "+this.getChooseExtras();
		//return dish;
		return name;
	}
	public int getAddDirectllyToMenu() {
		return addDirectllyToMenu;
	}
	public void setAddDirectllyToMenu(int addDirectllyToMenu) {
		this.addDirectllyToMenu = addDirectllyToMenu;
	}
	public int getRestaurantNumber() {
		return restaurantNumber;
	}
	public void setRestaurantNumber(int restaurantNumber) {
		this.restaurantNumber = restaurantNumber;
	}
	public int getImageSize() {
		return imageSize;
	}

	public void setImegeSize(int imageSize) {
		this.imageSize = imageSize;
	}
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
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
	public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

}
