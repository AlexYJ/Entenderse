package com.tcp.team.util;

import java.util.ArrayList;

import com.tcp.team.exception.JsonCreateException;
import com.tcp.team.logger.LogManager;
import com.tcp.team.server.LeopardObject;


/*
 * LeopardObject를 상속받는 클래스만 json으로 만들것이냐..
 * 아니면 모든 bean들을 json으로 만들 것이냐.. 
 * 
 * 
 */
public class JsonMaker {
	private static volatile JsonMaker instance = null;
	
	private JsonMaker() { }
	
	public static JsonMaker getInstance() {
		
		if(instance == null) {
			synchronized (JsonMaker.class) {
				if(instance == null) {
					instance = new JsonMaker();
				}
			}
		}
		
		return instance;
	}
	
	/**
	 * @param beanList - LeopardObject를 상속받는 bean의 ArrayListt
	 * @return 			- [ {id:'2', major:'컴공' .. }, {id:'3', major:'컴공' .. } ] 의 json 리스트
	 */
	public String toJsonList(ArrayList<LeopardObject> beanList) {
		
		StringBuilder json = new StringBuilder();
		
		try {
	
			json.append("[");

			for (int i = 0, len = beanList.size(); i < len; i++) {
				LeopardObject obj = beanList.get(i);
				json.append(obj.toJson());
				json.append(",");
			}

			json.deleteCharAt(json.length() - 1);
			json.append("]");
			
			if(json.length() < 3) {
				throw new JsonCreateException("json이 올바르지 않거나 데이터가 없습니다");
			}
		} catch (JsonCreateException exp) {
			LogManager.getInstance().logging(exp.toString());
			
			return null;
		}
		
		return json.toString();
	}
}
