package entity;



/**Entity employer used to save details about an employer 
 * @param name employer name
 * @param adress= employer adress
 * @param phone= employer phone number
 * @param employerNum employers number from DB
 * @param is_approved = is the employer approved flag 1 for approved 0 for not approved
  * @author      Muhamad abu assad
 * @version     1.0                 
 * @since       01.01.2022  */


public class Employer {

	private String name,address="-",phone;
	private int employerNum,is_approved=0;

	
	public Employer( int employerNum,String name, String address, String phone, int is_approved) {
		super();
		this.employerNum=employerNum;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.is_approved = is_approved;
	}

	public Employer(String name, String address, String phone) {
		this.name = name;
		this.address = address;
		this.phone = phone;
	}

	public int getEmployerNum() {
		return employerNum;
	}

	public void setEmployerNum(int employerNum) {
		this.employerNum = employerNum;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return  name + "~"+address +"~" + phone;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIs_approved() {
		return is_approved;
	}

	public void setIs_approved(int is_approved) {
		this.is_approved = is_approved;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
