package com.tcp.team.util;

import com.tcp.team.logger.LogManager;

public class QueryStorage {
	
	private static volatile QueryStorage instance = null;
	private LeopardHash<String, String> map = null;
	private String order = "//*";
	
	public static QueryStorage getInstance() {
		if(instance == null) {
			synchronized (QueryStorage.class) {
				if(instance == null) {
					instance = new QueryStorage();
				}
			}
		}
		
		return instance;
	}
	
	/**
	 * @param path
	 * @return true - load가 정상적으로 수행 false - load를 수행하던 중 예외가 발생한 경우.
	 */
	public boolean loadQueryFromXML(String path) {
		LogManager.getInstance().logging("XML 문서로 부터 각종 설정 파일을 로드합니다");
		try{
			map = XMLParser.getInstance().parseFromFile(path, order, new LeopardHash<String, String>());
		}
		catch(Exception exp){
			LogManager.getInstance().logging("Fail to Load Query "+exp.getMessage());;
			
			return false;
		}
		return true;
	}
	
	/**
	 * @param queryKey
	 * @return String - queryKey가 올바른 경우 정상적으로 쿼리가 리턴됨. null - 해당하는 키의 값이 없는 경우.
	 */
	public String getQuery(String queryKey){
		if(map != null && map.containsKey(queryKey)){
			return map.get(queryKey).get(0).toString();
		}
		LogManager.getInstance().logging("key :"+queryKey+" 의 값을 찾을 수 없습니다");
		return null;
	}
}
