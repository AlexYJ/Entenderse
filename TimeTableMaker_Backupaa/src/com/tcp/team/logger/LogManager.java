package com.tcp.team.logger;

import org.apache.log4j.Logger;

public class LogManager {
	private volatile static LogManager instance = null;
	private static Logger logger = null;
	
	public static LogManager getInstance() {
		if(instance == null) {
			synchronized (LogManager.class) {
				if(instance == null) {
					instance = new LogManager();
					logger = Logger.getLogger(LogManager.class);
				}
			}
		}
		
		return instance;
	}
	
	public void logging(String msg) {
		
		try {
			logger.info(msg);
		} catch(Exception exp) {
			logger.error(exp);
		}
	}
	
	/**
	 * @param args
	 * @설명	  로그를 찍는 방법. 출력 경로는 콘솔과 파일로 파일은 해당
	 * 		  프로젝트의 appLog에 기록되게 된다
	 */
	public static void main(String[] args){
		LogManager logger = LogManager.getInstance();
		logger.logging("Hello World!");
	}
}