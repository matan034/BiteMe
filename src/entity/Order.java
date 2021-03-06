package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Order implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String order_type,delivery_method,phone,recieving_name,buisness_name,street,city,zip,order_time,dish_name;

	
	private W4C w4c;
	

	private ArrayList<String> dishesInOrder= new ArrayList<>();
	public ArrayList<String> getDishesInOrder() {
		return dishesInOrder;
	}
	public void setDishesInOrder(String dish) {
		dishesInOrder.add(dish);
	}

	private Supplier supplier;
	private Branch branch;
	private PrivateAccount pAccount;
	private BusinessAccount bAccount;
	private Customer customer;
	private int order_num,is_early_order=0,people_in_delivery=1,is_approved,is_arrived;
	private Double price=0.0;
	private String supplierName;
	private Map<String,Integer> items_by_type=new HashMap<String,Integer>()
	{{
		 put("Appetizer",0);
		 put("Main",0);
		 put("Dessert",0);
		 put("Drink",0);
		 put("Salad",0);
	}};
	private ObservableList<DishInOrder> dishes=FXCollections.observableArrayList();
	
	public Order( String order_type,W4C w4c,Customer customer,PrivateAccount paccount,BusinessAccount baccount) {
		this.order_type = order_type;
		this.w4c=w4c;
		this.customer=customer;
		this.bAccount=baccount;
		this.pAccount=paccount;
	}

	public Order(int order_num,String order_type,String order_time,int isApproved,int is_arrived,String supplierName)
	{
		this.order_num = order_num;
		this.order_type = order_type;
		this.order_time = order_time;
		this.is_arrived=is_arrived;
		this.is_approved=isApproved;
		this.supplierName=supplierName;
		
	}
	public Order() {}
	
	

	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	@Override
	public String toString() {//check for customer
		if(supplier!=null)
		return supplier.getSupplierNum()+"~"+customer.getCustomerNumber()+"~"+order_type+"~"+price+"~"+is_early_order+"~"+order_time+"~"+
				delivery_method+"~"+recieving_name+"~"+buisness_name+"~"+phone+"~"+street+"~"+city+"~"+zip;
		else
			return order_num+"~"+order_type+"~"+order_time+"~"+is_arrived;
	}
	
	
	public PrivateAccount getpAccount() {
		return pAccount;
	}

	public void setpAccount(PrivateAccount pAccount) {
		this.pAccount = pAccount;
	}

	public BusinessAccount getbAccount() {
		return bAccount;
	}

	public void setbAccount(BusinessAccount bAccount) {
		this.bAccount = bAccount;
	}

	public W4C getW4c() {
		return w4c;
	}

	public void setW4c(W4C w4c) {
		this.w4c = w4c;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public String getDish_name() {
		return dish_name;
	}
	public void setDish_name(String dish_name) {
		this.dish_name = dish_name;
	}
	public int getIs_arrived() {
		return is_arrived;
	}
	public void setIs_arrived(int is_arrived) {
		this.is_arrived = is_arrived;
	}
	public int getIs_approved() {
		return is_approved;
	}
	public void setIs_approved(int is_approved) {
		this.is_approved = is_approved;
	}
	public int getPeople_in_delivery() {
		return people_in_delivery;
	}

	public void setPeople_in_delivery(int people_in_delivery) {
		this.people_in_delivery = people_in_delivery;
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
		if(delivery_method.equals("")) this.delivery_method=" ";
		else this.delivery_method = delivery_method;
	}


	public String getRecieving_name() {
		return recieving_name;
	}


	public void setRecieving_name(String recieving_name) {
		if(recieving_name.equals("")) this.recieving_name=" ";
		else this.recieving_name = recieving_name;
	}


	public String getBuisness_name() {
		return buisness_name;
	}


	public void setBuisness_name(String buisness_name) {
		if(buisness_name.equals("")) this.buisness_name=" ";
		else this.buisness_name = buisness_name;
	}


	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		if(street.equals("")) this.street=" ";
		else this.street = street;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		if(city.equals("")) this.city=" ";
		else this.city = city;
	}


	public String getZip() {
		return zip;
	}


	public void setZip(String zip) {
		if(zip.equals("")) this.zip=" ";
		else this.zip = zip;
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
	public ObservableList<DishInOrder> getDishes()
	{
		
		return dishes;
	}
	public boolean removeDish(DishInOrder dish)
	{
		price-=dish.getPrice();
		if(price<0)price=0.0;
		return dishes.remove(dish);
	}
	
	public Map<String, Integer> getItems_by_type() {
		return items_by_type;
	}
	public void setItems_by_type(Map<String, Integer> items_by_type) {
		this.items_by_type = items_by_type;
	}
	public void addQuantity(String type)
	{
		items_by_type.put(type, items_by_type.get(type)+1);
	}
	public void removeQuantity(String type)
	{
		items_by_type.put(type, items_by_type.get(type)-1);
	}
	
	public boolean removeAllDishes()
	{
		int size=dishes.size();
		for(int i=0;i<size;i++) 
		{
			if(!removeDish(dishes.get(0))) return false;//try to remove each dish, if failes return false
		}
		return true;
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
		if(phone.equals("")) this.phone=" ";
		else this.phone = phone;
	}


	public Supplier getSupplier() {
		return supplier;
	}


	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
	
}
