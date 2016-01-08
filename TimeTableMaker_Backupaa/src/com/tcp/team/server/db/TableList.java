package com.tcp.team.server.db;

import java.util.ArrayList;

public class TableList {
	
	public ArrayList<TableInfo> getTableList() {
		
		ArrayList<TableInfo> tableList = new ArrayList<TableInfo>();
	
		tableList.add(getLectureTable());
		tableList.add(getUserInfoTable());
		
		return tableList;
	}
	
	private TableInfo getLectureTable() {
		ArrayList<ColumInfo> colums = new ArrayList<ColumInfo>();
		
		colums.add(new ColumInfo("nIndex", "INT NOT NULL PRIMARY KEY AUTO_INCREMENT"));
		colums.add(new ColumInfo("lectureCode", "NVARCHAR(20) NOT NULL"));
		colums.add(new ColumInfo("lectureName", "NVARCHAR(100) NOT NULL"));
		colums.add(new ColumInfo("credit", "INT NOT NULL"));
		colums.add(new ColumInfo("day", "NVARCHAR(10) NOT NULL"));
		colums.add(new ColumInfo("startTime", "INT NOT NULL"));
		colums.add(new ColumInfo("finishTime", "INT NOT NULL"));
		colums.add(new ColumInfo("abeek", "NVARCHAR(15) NOT NULL"));
		colums.add(new ColumInfo("profName","NVARCHAR(10) NOT NULL"));
		colums.add(new ColumInfo("major", "NVARCHAR(30) NOT NULL"));
		colums.add(new ColumInfo("lectureRoom", "NVARCHAR(50) NOT NULL"));
		colums.add(new ColumInfo("lectureID", "NVARCHAR(50) NOT NULL"));
		colums.add(new ColumInfo("grade", "INT NOT NULL"));
		
		return new TableInfo("lectureInfo", colums);
	}
	
	private TableInfo getUserInfoTable() {
		
		ArrayList<ColumInfo> colums = new ArrayList<ColumInfo>();
		
		colums.add(new ColumInfo("nIndex", "INT NOT NULL AUTO_INCREMENT PRIMARY KEY"));
		colums.add(new ColumInfo("userID", "NVARCHAR(20) NOT NULL"));
		colums.add(new ColumInfo("userPasswd", "NVARCHAR(20) NOT NULL"));
		colums.add(new ColumInfo("selectLecture", "TEXT"));
		colums.add(new ColumInfo("exceptLecture", "TEXT"));
		colums.add(new ColumInfo("userMajor", "NVARCHAR(20) NOT NULL"));
		
		return new TableInfo("userInfo", colums);
	}
}