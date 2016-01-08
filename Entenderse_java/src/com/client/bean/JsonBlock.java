package com.client.bean;

import java.util.HashMap;

public class JsonBlock {
	
	private HashMap<String, String> jsonHash = null;
	
	public JsonBlock(HashMap<String, String> jsonBlock) {
		
		this.jsonHash = jsonBlock;
	}
	
	public JsonBlock() {
		
	}
	
	public String getData(String key) {
		
		if(jsonHash.containsKey(key)) {
			return jsonHash.get(key);
		}
		else {
			return null;
		}
	}
	
	public HashMap<String, String> getHashMap() {
		
		return jsonHash;
	}
	
	public void setHashMap(HashMap<String, String> hash) {
		
		jsonHash = hash;
	}
}
