package entity;

import java.io.Serializable;

import common.Globals;


/**Entity inTakeOrder how much the supplier gets at the end of the month after bite me takes a commision for using it's service
 * @param order_num= number of orders the supplier got
 * @param price = total price the orders cost
 * @param payment= the suppliers final bill
  * @author      daniel aibinder
 * @version     1.0                 
 * @since       01.01.2022  */


public class IntakeOrder implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private int order_num;
private Double price,commission,payment;


public IntakeOrder(int order_num, Double price) {
	super();
	this.order_num = order_num;
	this.price = price;
	this.commission=price*Globals.companyCommission;
	this.payment=this.price-this.commission;
}
public int getOrder_num() {
	return order_num;
}
public void setOrder_num(int order_num) {
	this.order_num = order_num;
}
public Double getPrice() {
	return price;
}
public void setPrice(Double price) {
	this.price = price;
}
public Double getCommission() {
	return commission;
}
public void setCommission(Double commission) {
	this.commission = commission;
}
public Double getPayment() {
	return payment;
}
public void setPayment(Double payment) {
	this.payment = payment;
}


}
