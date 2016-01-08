package com.tcp.team.server.dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;
import com.tcp.team.logger.LogManager;
import com.tcp.team.server.LeopardObject;
import com.tcp.team.server.bean.DataWrapper;
import com.tcp.team.server.bean.LectureInfoBean;
import com.tcp.team.util.DBConnPoolMng;

/**
 * @author Anh
 * LeopardTemplate는 DB접근에 쓰이는 쿼리문을 처리해 주는 클래스로서,
 * 싱글톤으로 구현되어있다.
 */
public class LeopardTemplate {
	private static volatile LeopardTemplate inst = null;
	
	public static LeopardTemplate getInstance(){
		if(inst == null){
			synchronized (LeopardTemplate.class) {
				if(inst == null){
					inst = new LeopardTemplate();
				}
			}
		}
		return inst;
	}
	
	/**
	 * @param sql
	 * @param obj
	 * @return true - 쿼리가 성공한 경우 false - 쿼리가 실패한 경우
	 * @throws SQLException
	 * @설명		주로 ResultSet이 반환되지 않는 insert나 delete 쿼리에 사용된다
	 */
	public boolean excuteSql(String sql,LeopardObject obj) throws SQLException{
		PreparedStatement pstmt = null;
		Connection conn = null;
		ArrayList<DataWrapper> list = obj.getLocalValList();
		
		try {
			conn = DBConnPoolMng.getInstance().getConnection();
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			completeStatement(pstmt, list);
			pstmt.execute();
		} catch (SQLException e) {
			LogManager.getInstance().logging(e.getMessage());
			
			return false;
		} finally {
			if(pstmt != null)
				pstmt.close();
			if(conn != null)
				DBConnPoolMng.getInstance().freeConnection(conn);
		}
		return true;
	}
	
	/**
	 * @param sql
	 * @param obj
	 * @return ArrayList<LeopardObject> - 쿼리에 성공하여 그 결과를 반환 / null - 쿼리에 실패한 경우 / 리턴된 값은 다운캐스팅하여 사용
	 * @throws SQLException
	 * @설명		주로 ResultSet이 반환되는 Select 쿼리에 이용되며,
	 * 			쿼리의 매개변수로 넘겨줄 값을 bean에 넣어서 넘겨주면 된다. (인자로 넘겨주지 않는 값은 빈에 넣지 않는다)
	 */
	public ArrayList<LeopardObject> excuteQuery(String sql,LeopardObject obj) throws SQLException {
		PreparedStatement pstmt = null;
		Connection conn = null; 
		ArrayList<DataWrapper> dataList = obj.getLocalValList();
		ArrayList<LeopardObject> objList = null; 
		ResultSet rs = null;
		
		try {
			conn = DBConnPoolMng.getInstance().getConnection();
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			completeStatement(pstmt, dataList);
			rs = pstmt.executeQuery();
			objList = getQueryResult(rs, obj);
		} catch (SQLException e){
			LogManager.getInstance().logging(e.getMessage());
			
			return null;
		} finally {
			if(pstmt != null)
				pstmt.close();
			if(conn != null)
				DBConnPoolMng.getInstance().freeConnection(conn);
		}
		return objList;
	}
	
	/**
	 * @param rs
	 * @param bean
	 * @return 
	 * @설명		쿼리 결과 ResultSet이 필요한 경우 이 메소드를 호출한다
	 */
	private ArrayList<LeopardObject> getQueryResult(ResultSet rs,LeopardObject bean) {
		ArrayList<LeopardObject> list = new ArrayList<LeopardObject>();
		
		try {
			while(rs.next()){
				LeopardObject obj = newInstance(bean);
				obj.setResultSet(rs);
				list.add(obj);
			}
		} catch (SQLException e) {
			LogManager.getInstance().logging(e.getMessage());
		}
		return list;
	}
	
	private LeopardObject newInstance(LeopardObject bean) {
		
		LeopardObject retVal = null;
		
			// bean 클래스를 동적으로 로딩한다
		try {
			Class<?> classInfo = Class.forName(bean.getClass().getName());
			Constructor<?> constructor = classInfo.getDeclaredConstructor();
			retVal = (LeopardObject) constructor.newInstance();				
		} catch (Exception e) {
			LogManager.getInstance().logging(e.getMessage());
		}
			
		return retVal;
	}
	
	private PreparedStatement completeStatement(PreparedStatement pstmt,ArrayList<DataWrapper> list) throws SQLException{
		int count = 1;
		for(int i=0,len=list.size(); i<len; i++){
			DataWrapper data = list.get(i);
			
			if(data.getData() != null) // null값이 아닌 경우에만 데이터를 넣도록 수정. 2015-03-24 안덕환
				pstmt.setObject(count++, data.getData());
		}
		return pstmt;
	}
}