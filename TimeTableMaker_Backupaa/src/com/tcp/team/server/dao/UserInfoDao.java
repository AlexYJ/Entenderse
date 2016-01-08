package com.tcp.team.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.tcp.team.logger.LogManager;
import com.tcp.team.server.LeopardObject;
import com.tcp.team.server.bean.UserInfoBean;
import com.tcp.team.util.QueryStorage;

public class UserInfoDao {
	private static volatile UserInfoDao instance = null;
	
	public static UserInfoDao getInstance() {
		
		if(instance == null) {
			synchronized (UserInfoDao.class) {
				if(instance == null) {
					instance = new UserInfoDao();
				}
			}
		}
		return instance;
	}
	
	public UserInfoBean login(UserInfoBean bean) {
		
		String sql = QueryStorage.getInstance().getQuery("login").trim();
		LeopardTemplate tmplt = LeopardTemplate.getInstance();
		
		UserInfoBean retValue = null;
		
		try {
			ArrayList<LeopardObject> loginMember = tmplt.excuteQuery(sql, bean);
			
			if(loginMember == null) {
				
				retValue = null;
			}
			
			retValue = (UserInfoBean) loginMember.get(0);
		} catch(SQLException exp) {
			LogManager.getInstance().logging(exp.toString());
		} catch (NullPointerException exp) {
			LogManager.getInstance().logging(exp.toString());
		}
		
		return retValue;
	}
	
	public void insertUserInfo(UserInfoBean bean) {
		
		String query = QueryStorage.getInstance().getQuery("insertUserInfo").trim();
		LeopardTemplate tmplt = LeopardTemplate.getInstance();
		
		try {
			tmplt.excuteSql(query, bean);
		} catch (SQLException e) {
			LogManager.getInstance().logging(e.toString());
		}
	}
	
	public UserInfoBean searchMember(UserInfoBean userBean) {
		
		String query = QueryStorage.getInstance().getQuery("searchMemberById").trim();
		LeopardTemplate tmplt = LeopardTemplate.getInstance();
		
		UserInfoBean retBean = null;
		try {
			ArrayList<LeopardObject> list = tmplt.excuteQuery(query, userBean);
			retBean = (UserInfoBean) list.get(0);
		} catch(SQLException exp) {
			LogManager.getInstance().logging(exp.toString());
		}
		
		return retBean;
	}
}
