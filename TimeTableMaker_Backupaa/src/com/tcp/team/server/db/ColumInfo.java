package com.tcp.team.server.db;

public class ColumInfo {
	private String columName = null;
	private String columValue = null;
	
	public ColumInfo(String colName,String colVal){
		this.columName = colName;
		this.columValue = colVal;
	}
	
	public String getColumName() {
		return columName;
	}
	public void setColumName(String columName) {
		this.columName = columName;
	}
	public String getColumValue() {
		return columValue;
	}
	public void setColumValue(String columValue) {
		this.columValue = columValue;
	}
}
