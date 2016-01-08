package com.tcp.team.server.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.tcp.team.logger.LogManager;
import com.tcp.team.server.LeopardObject;
import com.tcp.team.server.dao.UserInfoDao;
import com.tcp.team.util.QueryStorage;

/**
 * @author Leopard
 *
 */
public class UserInfoBean extends LeopardObject {
	
	private DataWrapper nIndex = null;
	private DataWrapper userID = null;
	private DataWrapper userPasswd = null;
	private DataWrapper selectLecture = null;
	private DataWrapper exceptLecture = null;
	private DataWrapper userMajor = null;
	
	public UserInfoBean() {
		
		nIndex = new DataWrapper();
		userID = new DataWrapper();
		userPasswd = new DataWrapper();
		selectLecture = new DataWrapper();
		exceptLecture = new DataWrapper();
		userMajor = new DataWrapper();
		
		localValueList = new ArrayList<DataWrapper>();
		
		localValueList.add(nIndex);
		localValueList.add(userID);
		localValueList.add(userPasswd);
		localValueList.add(selectLecture);
		localValueList.add(exceptLecture);
		localValueList.add(userMajor);
	}
	
	public DataWrapper getUserID() {
		return userID;
	}
	public void setUserID(Object userID) {
		this.userID.setData(userID);
	}
	
	public DataWrapper getUserPasswd() {
		return userPasswd;
	}
	public void setUserPasswd(Object userPasswd) {
		this.userPasswd.setData(userPasswd);
	}
	public DataWrapper getSelectLecture() {
		return selectLecture;
	}
	public void setSelectLecture(Object selectLecture) {
		this.selectLecture.setData(selectLecture);
	}
	public DataWrapper getExceptLecture() {
		return exceptLecture;
	}
	public void setExceptLecture(Object exceptLecture) {
		this.exceptLecture.setData(exceptLecture);
	}
	
	public DataWrapper getnIndex() {
		return nIndex;
	}

	public void setnIndex(Object nIndex) {
		this.nIndex.setData(nIndex);
	}
	
	public Object getUserMajor() {
		return userMajor.getData();
	}

	public void setUserMajor(Object userMajor) {
		this.userMajor.setData(userMajor);
	}
	
	@Override
	public ArrayList<DataWrapper> getLocalValList() {
		return localValueList;
	}
	@Override
	public void setResultSet(ResultSet set) {
		
		try {
			setnIndex(set.getObject("nIndex"));
			setUserID(set.getObject("userID"));
			setUserPasswd(set.getObject("userPasswd"));
			setSelectLecture(set.getObject("selectLecture"));
			setExceptLecture(set.getObject("exceptLecture"));
			setUserMajor(set.getObject("userMajor"));
		} catch(SQLException e) {
			
			LogManager.getInstance().logging(e.getMessage());
		}
	}

	public String toJson() {
		
		String json = null;
		try {
			json = super.toJson(this);
		} catch (IllegalArgumentException e) {
			LogManager.getInstance().logging(e.getStackTrace().toString());
		}
		
		return json;
	}
	
	public static void main(String[] args) {
		
		QueryStorage.getInstance().loadQueryFromXML("D:/Anh/OneDrive/문서/test.xml");
		
		UserInfoBean bean = new UserInfoBean();
		bean.setUserID("illsky");
		bean.setUserPasswd("1234");
		
		UserInfoDao.getInstance().insertUserInfo(bean);
	}
}
