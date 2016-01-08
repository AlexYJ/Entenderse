package com.client.bean;

import java.util.HashMap;

/**
 * @author 	Anh
 * @설명		입력으로 들어온 Json을 Bean으로 만들고 싶은 객체는
 * 			해당 인터페이스를 상속하여 toBean() 메소드를 오버라이딩 하여야 한다
 *
 */
public interface Beanable {
	
	public Beanable toBean(HashMap<String,String> jsonBlock);
}
