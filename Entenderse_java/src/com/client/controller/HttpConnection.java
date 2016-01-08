package com.client.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.client.HttpClient;







public class HttpConnection   {
	private static volatile HttpConnection instance = null;

	private HttpConnection() { }

	public static HttpConnection getInstance() {

		if(instance == null) {
			synchronized (HttpConnection.class) {
				if(instance == null) {
					instance = new HttpConnection();
				}
			}
		}
		return instance;
	}

	/**
	 * @param url
	 * @return 	json의 문법을 만족하는 문자열을 리턴. 하지만 유효성 검사는 하지 않는다
	 * 			리턴되는 문자열을 json으로 적절히 파싱하여 bean에 넣어 사용할 것
	 */
	public String request(String url) {

		StringBuilder jsonString = new StringBuilder();
		BufferedReader reader = null;
		HttpURLConnection urlConn = null;

		try {
			URL requestURL = new URL(url);
			urlConn = (HttpURLConnection)requestURL.openConnection();

			if( urlConn == null ) {
				// 연결에 실패한 경우..
				System.out.println("연결에 실패하였습니다");

				return null;
			}

			// URL 연결 타임아웃 시간을 설정한다
			urlConn.setConnectTimeout(100000);
			// URL 연결요청 메소드 방법을 설정한다
			urlConn.setRequestMethod("GET");
			// 객체 입력이 가능하도록 설정한다
			urlConn.setDoInput(true);
			// 객체 출력이 가능하도록 설정한다
			urlConn.setDoOutput(true);

			int requestResult = urlConn.getResponseCode();

			// 서버와 통신이 성공한 경우에 
			if( requestResult == HttpURLConnection.HTTP_OK ) {

				reader = new BufferedReader(
						new InputStreamReader(urlConn.getInputStream()));


				String line = null;
				do {

					line = reader.readLine();

					if (line != null) {
						jsonString.append(line);
					}
				} while(line != null);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				urlConn.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return jsonString.toString();
	}

	//	public static void main(String[] args) {
	//		
	//		HttpConnection conMng = HttpConnection.getInstance();
	//		String json = conMng.request("http://localhost:8080/TimeTableMaker_Backup/getLectureInfo.do");
	//		
	//		System.out.println(json);
	//	}






}
