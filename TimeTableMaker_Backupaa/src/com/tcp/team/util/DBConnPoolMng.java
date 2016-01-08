package com.tcp.team.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import com.tcp.team.logger.LogManager;


/*
 * ConnectionPool을 정의한 소스 by Anh
 */
public class DBConnPoolMng {
	private LinkedList<Connection> freeConnPool = null;

	//jdbc:mysql://localhost:3306/leopard
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/test";
	private String user = "root";
	private String password = "1234";

	// 현재 여기서 수를 고정시켜 두었지만, 조금 더 가변적으로 만들 필요가 있지 않을까 싶다.
	private int openConnection = 10;

	private static volatile DBConnPoolMng inst = null;

	private void init() {
		try{
			Class.forName(driver);
			// XML 파일로 부터 값을 Database 연결 정보를 읽어온다
			QueryStorage storage = QueryStorage.getInstance();
			url = storage.getQuery("urlInfo").trim();
			user = storage.getQuery("userInfo").trim();
			password = storage.getQuery("passwordInfo").trim();
			openConnection = Integer.parseInt(storage.getQuery("openConnection").trim());

			// Database 연결 정보를 만들어 사용하게 한다
			freeConnPool = new LinkedList<Connection>();

			for(int i=0,len=openConnection; i<len; i++){
				freeConnPool.add(createNewConnection());
			}
		}catch(ClassNotFoundException e){
			LogManager.getInstance().logging(e.getMessage());
		}
	}

	/**
	 * 생성자에 초기화 소스를 만들지 마라 
	 */
	private DBConnPoolMng(){
		init();
	}

	/*
	 * Name		:: createNewConnection
	 * Func		:: Connection을 연결하여 해당 객체를 리턴해준다.
	 */
	private Connection createNewConnection() {
		Connection con = null;

		try {
			con = DriverManager.getConnection(url,user,password);
		} catch (SQLException e) {
			LogManager.getInstance().logging("DB Connect 에러 :"+e.getMessage() + " : "+  e.getStackTrace());
		}

		return con;
	}

	/*
	 * Name		:: getInstance
	 * Func		:: 싱글톤 패턴을 이용하였으머, 중간에 동기화를 걸어 안정성을 높였다. 
	 * Return	:: DBConnPoolMng 객체를 리턴해 준다.
	 */
	public static DBConnPoolMng getInstance(){

		if(inst == null){
			synchronized (DBConnPoolMng.class) {
				if(inst == null){
					inst = new DBConnPoolMng();
				}
			}
		}

		return inst;
	}

	public synchronized Connection getConnection(){
		Connection con = null;

		// ConnectionPool에 사용가능한 Connection이 있다면,
		if(freeConnPool.size() > 0){

			// 해당 Connection을 가져오고, 리스트에서 삭제한다.
			con = (Connection)freeConnPool.get(0);
			freeConnPool.remove(0);

			try {
				// 만약 DBMS에 의해서 컨넥션이 닫힌경우, 다시 새로 만든다.
				if(con.isClosed()){
					con = createNewConnection();
				}
			} catch (SQLException e) {
				e.getStackTrace();
				LogManager.getInstance().logging(e.getMessage());
			}

			return con;
		}
		// 현재 사용가능한 Connection이 없으므로 null을 리턴한다.
		return null;
	}

	/*
	 * Name		:: freeConnection
	 * Func		:: 가지고 있던 Conenction을 반환시킨다.
	 * 동기화 이유	:: 반환 중, 해당 ArrayList의 인덱스가 중복될 가능성이 있다.
	 * 			ArrayList는 삽입 삭제가 느리다 - LinkedList는 삽입 삭제가 빠르다
	 */
	public synchronized void freeConnection(Connection con){
		// Connection을 해당 ConnectionPool에 반환한다.
		freeConnPool.add(con);
	}

	public static void main(String[] args) throws SQLException{

		QueryStorage.getInstance().loadQueryFromXML("D:/Anh/OneDrive/문서/query.txt");
		DBConnPoolMng inst = DBConnPoolMng.getInstance();
		Connection con = inst.getConnection();

		for(int i=0; i<50; i++){
			System.out.println(i+" ---- Connection ----- ");
			con = inst.getConnection();
			if(con == null){
				System.out.println("There is no available connection");
			}
			else{

				java.sql.Statement st = null;
				ResultSet rs = null;
				st = con.createStatement();
				rs = st.executeQuery("SHOW TABLES");

				if (st.execute("SHOW TABLES")) {
					rs = st.getResultSet();
				}

				while (rs.next()) {
					String str = rs.getNString(1);
					System.out.println(str);
				}

				inst.freeConnection(con);
				System.out.println(i+" ---- Connection  ----- ");
			}
		}
	}
}