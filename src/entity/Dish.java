package entity;

import java.io.Serializable;

public class Dish implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int dishID;
	private String name,type;
	

	public Dish(int dishID,String name,String type)
	{
		this.dishID = dishID;
		this.name = name;
		this.type = type;
	}
	
	@Override
	public String toString() {
		
		return name;
	}
	
	public int getDishID() {
		return dishID;
	}
	public void setDishID(int dishID) {
		this.dishID = dishID;
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
