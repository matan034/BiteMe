package entity;

import java.io.Serializable;


/**Entity clientData wrapper for entitys to send to server where the object is the entity we wrapped
 * @param operation = key word for server to know which operation to activate
 * @param data= object thats been wrapped by the clientData
  * @author      Muhamad abu assad
 * @version     1.0                 
 * @since       01.01.2022  */


public class clientData implements Serializable {
	private static final long serialVersionUID = 1L;
	private String operation;
	private Object data;
	 
	public clientData(Object data,String operation) {
		super();
		this.data=data;
		this.operation=operation;
	}
	
	public clientData(String operation) {
		super();
		this.data=null;
		this.operation=operation;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	

}
