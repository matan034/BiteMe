package entity;


/**Entity DishInMenu connects a certain dish to a certain menu
 * @param dishId = dish id from DB
 * @param menuID = the menus ID from DB
  * @author      Muhamad abu assad
 * @version     1.0                 
 * @since       01.01.2022  */


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
