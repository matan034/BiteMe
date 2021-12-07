package entity;

public class DishInMenu {
private int dishID,menuID;

public DishInMenu(int dishID, int menuID) {
	super();
	this.dishID = dishID;
	this.menuID = menuID;
}

public int getDishID() {
	return dishID;
}

public void setDishID(int dishID) {
	this.dishID = dishID;
}

public int getMenuID() {
	return menuID;
}

public void setMenuID(int menuID) {
	this.menuID = menuID;
}

}
