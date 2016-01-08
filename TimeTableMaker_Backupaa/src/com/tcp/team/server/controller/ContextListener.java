package com.tcp.team.server.controller;

import java.util.ArrayList;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.tcp.team.logger.LogManager;
import com.tcp.team.server.LeopardObject;
import com.tcp.team.server.bean.LectureInfoBean;
import com.tcp.team.server.dao.LectureDao;
import com.tcp.team.server.db.DBManager;
import com.tcp.team.util.CSVParser;
import com.tcp.team.util.DBConnPoolMng;
import com.tcp.team.util.QueryStorage;


/**
 * @author	Leopard
 * @설명		DB 초기화, XML 파싱, CSV 파싱 등의 각종 초기화 작업을 진행한다
 *
 */	
public class ContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		LogManager.getInstance().logging("서버 종료");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			LogManager.getInstance().logging("서버 초기화 시작");

			LogManager.getInstance().logging("QueryStorage 초기화 시작");
			QueryStorage.getInstance().loadQueryFromXML("C:/Users/bonos2/Desktop/doc/test.xml");

			LogManager.getInstance().logging("DBConnectionPool 초기화 시작");
			DBConnPoolMng.getInstance();

			LogManager.getInstance().logging("데이터베이스 테이블 생성");
			// 어차피 서버 초기화에 한번 쓰이고 끝이므로 싱글톤으로 만들지 않음
			new DBManager().makeTable();

			LogManager.getInstance().logging("CSV 파일로부터 데이터를 읽어 DB에 삽입 시작");
			ArrayList<LectureInfoBean> list = CSVParser.getInstance().parseCSV(
					QueryStorage.getInstance().getQuery("csvPath").trim());
			
			for(int i=0,listLen=list.size(); i<listLen; i++) {
				LectureDao.getInstance().insertLectureInfo(list.get(i));
			}

			LogManager.getInstance().logging("서버 초기화 작업 끝");
		} catch (Exception e) {
			LogManager.getInstance().logging("서버 초기화 실패");
			LogManager.getInstance().logging(e.getStackTrace().toString());
		}
	}
}
