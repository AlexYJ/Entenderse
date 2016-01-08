package com.tcp.team.server.bean;

/**
 * @author Anh
 * @설명	쓸모가 없어진줄 알았는데, 알고보니 이 클래스가 없으면 Bean클래스에서 값을 잡고있지 못한다.
 * 		의심해서 미안
 */

// 2015-05-11 extends LeopardObject를 지움.. 혹시 문제가 생기면 다시 이 글을 참조하길
// 왜 extends 받고 있는지 이유가 현재 명확하지 않아서 지우게 되었음. 
public class DataWrapper implements Cloneable {
	
	private Object data = null;
	
	public Object getData() {
		return this.data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public DataWrapper clone() {
		DataWrapper retVal = null;
		try {
			retVal =  (DataWrapper) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return retVal;
	}
}
