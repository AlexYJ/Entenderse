package com.tcp.team.util;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author Anh
 *
 * @param <KEY_T>
 * @param <VALUE_T>
 * @설명	내부변수 HashMap<KEY_T,ArrayList<VALUE_T>>를 이용하여 HashMap에 같은  키에 여러 문자열이 입력된 경우,
 * 		ArrayList에 그 값을 담아두어 모두 저장하게 만들었다. XMLParser에서 이 값을 사용하게 된다.
 */
public class LeopardHash<KEY_T,VALUE_T> {
	private HashMap<KEY_T,ArrayList<VALUE_T>> map = null;
	private ArrayList<KEY_T> keys = null;
	
	public LeopardHash(){
		map = new HashMap<KEY_T, ArrayList<VALUE_T>>();
		keys = new ArrayList<KEY_T>();
	}
	
	public void put(KEY_T name,VALUE_T val){
		if(map.containsKey(name)){
			map.get(name).add(val);
		}
		else{
			ArrayList<VALUE_T> list = new ArrayList<VALUE_T>();
			list.add(val);
			map.put(name,list);
			keys.add(name);
		}
	}
	
	public boolean containsKey(KEY_T key){
		if(map.containsKey(key)){
			return true;
		}
		return false;
	}
	
	public ArrayList<VALUE_T> get(KEY_T key){
		return map.get(key);
	}
	
	public ArrayList<KEY_T> getKeysList(){
		return this.keys;
	}
}
