package com.tcp.team.server.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;

import com.mysql.jdbc.PreparedStatement;
import com.tcp.team.logger.LogManager;
import com.tcp.team.util.DBConnPoolMng;
import com.tcp.team.util.QueryStorage;

public class DBManager {
	
	public void makeTable(){
		
		Connection connection = DBConnPoolMng.getInstance().getConnection();
		ArrayList<TableInfo> tableList = new TableList().getTableList();
		PreparedStatement pstmt = null;
		
		ArrayList<ColumInfo> colList = null;
		
		LogManager.getInstance().logging("데이터베이스 테이블 생성 시작");
		for(int tabIdx=0,tabLen=tableList.size(); tabIdx<tabLen; tabIdx++){
			colList = tableList.get(tabIdx).getColumList();
			
			StringBuilder sql = new StringBuilder();
			sql.append("CREATE TABLE ");
			sql.append(tableList.get(tabIdx).getTableName());
			sql.append("(");
			
			ColumInfo colInfo = null;
			for(int colIdx=0,colLen=colList.size(); colIdx<colLen; colIdx++){
				colInfo = colList.get(colIdx);
				sql.append(colInfo.getColumName());
				sql.append(" ");
				sql.append(colInfo.getColumValue());
				sql.append(",");
			}
			
			// 마지막 쉼표 제거
			sql.deleteCharAt(sql.toString().length()-1);
			sql.append(");");
			
			try{
				pstmt = (PreparedStatement) connection.prepareStatement(sql.toString());
				pstmt.execute();
			}
			catch(SQLException exp){
				LogManager.getInstance().logging("데이터베이스 테이블 생성 실패 "+exp.getMessage());
			}
		}
	}
	
	public static void main(String[] args) {
		QueryStorage.getInstance().loadQueryFromXML("D:/Anh/OneDrive/문서/test.xml");
		new DBManager().makeTable();
	}
}