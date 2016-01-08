package com.tcp.team.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.tcp.team.logger.LogManager;
import com.tcp.team.server.LeopardObject;
import com.tcp.team.server.bean.LectureInfoBean;
import com.tcp.team.util.QueryStorage;


/**
 * @author Leopard by Anh
 * @설명	   Lecture와 관련된 삽입, 조회, 삭제, 업데이트 기능을 관리한다
 *
 */
public class LectureDao {
	private static volatile LectureDao instance = null;
	
	private LectureDao() { }
	
	public static LectureDao getInstance() {
		
		if(instance == null) {
			synchronized (LectureDao.class) {
				if(instance == null) {
					instance = new LectureDao();
				}
			}
		}
		
		return instance;
	}
	
	/**
	 * @param bean		- LectureInfoBean 클래스로 DB에 해당 튜플을 삽입한다
	 */
	public void insertLectureInfo(LectureInfoBean bean) {
		
		bean.setLectureName(((String)bean.getLectureName()).trim());
		System.out.println(bean.getLectureName());
		String query = QueryStorage.getInstance().getQuery("InsertLectureInfo").trim();
		LeopardTemplate tmplt = LeopardTemplate.getInstance();
		
		try {
			tmplt.excuteSql(query, bean);
		} catch (SQLException e) {
			LogManager.getInstance().logging("[LectureDao] - Insert Error ");
		}
	}
	
	public ArrayList<LeopardObject> selectLectureInfo(LectureInfoBean bean) {
		
		String sql = QueryStorage.getInstance().getQuery("selectLectureInfoByMajor").trim();
		LeopardTemplate tmplt = LeopardTemplate.getInstance();
		ArrayList<LeopardObject> list = null;
		
		try {
			list = tmplt.excuteQuery(sql, bean);
		} catch (SQLException e){
			LogManager.getInstance().logging(e.toString());
			list = null;
		}
		return list;
	}
	
	public ArrayList<LeopardObject> selectLectureInfoByGradeMajor(LectureInfoBean bean) {
		
		String sql = QueryStorage.getInstance().getQuery("selectLectureInfoByGradeMajor").trim();
		LeopardTemplate tmplt = LeopardTemplate.getInstance();
		ArrayList<LeopardObject> list = null;
		
		try {
			list = tmplt.excuteQuery(sql, bean);
		} catch (SQLException e) {
			LogManager.getInstance().logging(e.toString());
			list = null;
		}
		return list;
	}
}
