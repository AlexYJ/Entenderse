package com.client.util;

import java.util.ArrayList;
import java.util.HashMap;

import com.client.bean.Beanable;
import com.client.bean.JsonBlock;
import com.client.bean.LectureInfoBean;



/**
 * @author 	Nightmare
 * @설명	Json 문법을 만족하는 문자열을 파싱하여 bean으로 만든다
 * 			이때 주의사항, 현재 프로젝트에서만 사용 가능한 Parser로 모든 json 문법에 적용되지 않는다
 * 			나중에 유틸로 하나 뽑으면 괜찮을거 같다 
 * 			2015-05-21 Anh
 */
public class Converter {
	
	private static volatile Converter instance = null;
	
	public static Converter getInstance() {
		
		if(instance == null) {
			synchronized (Converter.class) {
				if(instance == null) {
					instance = new Converter();
				}
			}
		}
		return instance;
	}
	
	/**
	 * @param 	jsonLine	들어오는 데이터 형식 - "key:value","key":"value" ... 
	 * @return	JsonBlock	Json에서 하나의 블럭에 있는 데이터를 hash로 만들어 돌려준다
	 * @설명	이 부분이 성능저하의 중요한 역할을 한다. json을 { } 로 한 번 파싱, split으로 다시 한번 파싱, 파싱.. 
	 * 			같은 문자열을 읽는 구간이 굉장히 많아지기 때문에 성능저하의 원인이 된다
	 */
	private JsonBlock convertJsonBlock(String jsonLine) {
		
		// 아래 과정을 거치게 되면 "key":"value" 상태가 된다
		String[] keyValue = jsonLine.split(",");
		HashMap<String, String> jsonHash = new HashMap<String, String>();
		
		for(int i=0,len=keyValue.length; i<len; i++) {
			
			// 0에는 key, 1에는 value가 들어가게 된다
			// 이때 jsonData[0] 에는 "key", jsonData[1] 에느 "value"가 들어가게 된다
			String[] jsonData = keyValue[i].split(":");
			String key = jsonData[0].split("\"")[1].toString();
			String value = jsonData[1].split("\"")[1].toString();
			
			jsonHash.put(key, value);
		}
		
		return new JsonBlock(jsonHash);
	}
	
	private boolean isJsonLineComplete(int endIndex) {
		
		if(endIndex == -1)
			return false;
		else
			return true;
	}
	
	private ArrayList<String> getJsonBlock(String json) {
		
		ArrayList<String> jsonBlockList = new ArrayList<String>();
		int nBracket = 0;
		int splitStartIndex = -1;
		int splitEndIndex = -1;
		
		for(int i=0,jsonLen=json.length(); i<jsonLen; i++) {
			
			// json 문자열에서 하나의 노드블럭을 가지고 왔다면 json을 빠져 나간다.
			if( isJsonLineComplete(splitEndIndex)) {
				jsonBlockList.add(json.substring(splitStartIndex+1,splitEndIndex));
				
				nBracket = 0;
				splitStartIndex = -1;
				splitEndIndex = -1;
			}
			
			switch (json.charAt(i)) {
			case '{':
				
				if(nBracket == 0) 
					splitStartIndex = i;
				
				nBracket++;
				
				break;
				
			case '}':
				nBracket--;
				if(nBracket == 0)
					splitEndIndex = i;
				
				break;

			default:
				break;
			}
		}
		
		// Json 문법을 만족하지 않는 경우에 null을 리턴한다
		if(splitEndIndex != splitStartIndex) {
			
			return null;
		}
		
		return jsonBlockList;
	}
	
	public Beanable convertBean(HashMap<String, String> jsonBlock,Beanable bean) {
		
		return bean.toBean(jsonBlock);
	}
	
	public ArrayList<LectureInfoBean> convertLectureInfo(ArrayList<Beanable> list) {
		
		ArrayList<LectureInfoBean> lectureList = new ArrayList<LectureInfoBean>();
		
		for(int i=0,len=list.size(); i<len; i++) 
			lectureList.add((LectureInfoBean) list.get(i));
	
		return lectureList;
	}
	
	public ArrayList<Beanable> convertBean(String json, Beanable bean) {
		
		ArrayList<String> blockJson = getJsonBlock(json);
		ArrayList<Beanable> beanList = new ArrayList<Beanable>();
		
		for(int i=0,len=blockJson.size(); i<len; i++) {
			
			JsonBlock block = convertJsonBlock(blockJson.get(i));
			if(block != null) {
				beanList.add(convertBean(block.getHashMap(), bean));
			}
		}
		
		return beanList;
	}
	
	public static void main(String[] args) {
		
		
		// 문자열 split 파싱 테스트
		StringBuilder str = new StringBuilder();
		
		str.append("\"").append("key").append("\"");
		str.append(":");
		str.append("\"").append("value").append("\"");
		
		System.out.println(str.toString());
		String[] keyValue = str.toString().split(":");
		
		System.out.println(keyValue[0].split("\"")[1].toString());
		System.out.println(keyValue[1].split("\"")[1].toString());
		
		HttpConnection conntMng = HttpConnection.getInstance();
		// Json 데이터를 서버로부터 받아와서 
		String json = conntMng.request("http://localhost:8080/TimeTableMaker_Backup/getLectureInfo.do");
		
		Converter converter = Converter.getInstance();
		
		// 받아온 데이터를 Bean 형식으로 만든다. 솔직히 좀 느리긴 하지만 클라이언트 이고 나중에 성능을 감안하여 수정하자
		// 느린 이유, 읽은 데이터를 목적에 따라 중복적으로 읽기 때문.
		// 충분히 나중에 한번만 읽도록 수정할 수 있을거 같다 2015-05-21 by Anh
		ArrayList<Beanable> list = converter.convertBean(json, new LectureInfoBean());
		
		System.out.println("헤헷 성공");
	}
}
