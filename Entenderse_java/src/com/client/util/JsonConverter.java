package com.client.util;

/**
 * @author	Nightmare
 * @설명	Json 문법 형식으로 입력된 문자열을 파싱하여 해당 Bean으로 만든다.
 * 			최종 목표는 Hash<lectureID,LectureInfoBean> 으로 사용하는 것
 */
public class JsonConverter {
	
	private static volatile JsonConverter instance = null;
	
	private JsonConverter() { }
	
	public static JsonConverter getInstance() {
		
		if(instance == null) {
			synchronized (JsonConverter.class) {
				if(instance == null) {
					instance = new JsonConverter();
				}
			}
		}
		return instance;
	}
}
