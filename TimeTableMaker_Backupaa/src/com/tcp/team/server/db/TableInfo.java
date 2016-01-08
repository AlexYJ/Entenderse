package com.tcp.team.server.db;

import java.util.ArrayList;

public class TableInfo {

	private String tableName = null;
	private ArrayList<ColumInfo> columList = null;
	
	public TableInfo(String tblName,ArrayList<ColumInfo> colList){
		this.tableName = tblName;
		this.columList = colList;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public ArrayList<ColumInfo> getColumList() {
		return columList;
	}

	public void setColumList(ArrayList<ColumInfo> columList) {
		this.columList = columList;
	}
}
