package com.tcp.team.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import com.tcp.team.logger.LogManager;
import com.tcp.team.server.LeopardObject;
import com.tcp.team.server.bean.LectureInfoBean;
import com.tcp.team.server.dao.LectureDao;

public class CSVParser {
	
	private static volatile CSVParser instance = null;
	private static String splitOrder = ",";
	
	public static CSVParser getInstance() {
		
		if(instance == null) {
			synchronized (CSVParser.class) {
				if(instance == null) {
					instance = new CSVParser();
				}
			}
		}
		
		return instance;
	}

	public ArrayList<LectureInfoBean> parseCSV(String path) {

		ArrayList<LectureInfoBean> lectureList = new ArrayList<LectureInfoBean>();
		String[] columName = null;
		int nIndex = 0;

		try {
			BufferedReader bfReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(path), "EUC-KR"));
			columName = bfReader.readLine().split(splitOrder);
			String nextLine = null;
			while ((nextLine = bfReader.readLine()) != null) {
				LectureInfoBean lectureBean = new LectureInfoBean();
				String[] lectureInfo = nextLine.split(splitOrder);

				// 과목코드
				lectureBean.setLectureCode(lectureInfo[0].trim());
				// 과목이름
				lectureBean.setLectureName(lectureInfo[1].trim());
				
				// 전필, 전선등 이수정보
				lectureBean.setAbeek(lectureInfo[2].trim());
				// 학점
				lectureBean.setCredit(lectureInfo[3].trim());
				
				// 개설학과
				lectureBean.setMajor(lectureInfo[5].trim());
				// 교수명
				lectureBean.setProfName(lectureInfo[6].trim());
				// 강의실
				lectureBean.setLectureRoom(lectureInfo[7].trim());
				
				// 강의시간
				String str1 = lectureInfo[4].replace("(","/").replace(")", "/").replace("/", "");
				System.out.println(str1);
				String str2[] = str1.split("-");
				int str2Len = str2.length;
				
				// 원래 지정되어 있단 lectureCode + profName은 PK가 되지 못했다.
				// 따라서 이렇게 임시방편으로 그 문제를 해결한다
				String lectureID = lectureBean.getLectureCode() + Integer.toString(nIndex++);
				lectureBean.setLectureID(lectureID);
				
				// 현재 가지고 있는 cvs 파일에는 학년 정보가 없다
				// 따라서 추후에 무조건 꼭 학년 정보가 필요 하므로 테스트 용으로 학년 정보를 박아둔다
				Random random = new Random();
				Integer grade = random.nextInt(4)+1;
				lectureBean.setGrade(Integer.toString(grade));
				
				if(str2Len > 1) {
					for(int i=0; i<str2Len; i++) {
						
						LeopardObject leopardObj = lectureBean.clone();
						LectureInfoBean bean = (LectureInfoBean)leopardObj;
						
						bean.setDay(Character.toString(str2[i].charAt(0)));
						bean.setStartTime(Character.toString(str2[i].charAt(1)));
						int len = str2[i].length();
						bean.setFinishTime(Character.toString(str2[i].charAt(len-1)));
						
						bean.setLectureID(lectureID);
						
						// 땜빵식으로 코딩을 해도 되려나..
						if( i != 0)
							bean.setCredit("0");
						
						lectureList.add(bean);
					}
				}
				else {
					lectureBean.setDay(Character.toString(str1.charAt(0)));
					lectureBean.setStartTime(Character.toString(str1.charAt(1)));
					lectureBean.setFinishTime(Character.toString(str1.charAt(str1.length()-1)));
					
					System.out.println(lectureBean.getGrade());
					
					lectureList.add(lectureBean);
				}
			}
			bfReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return lectureList;
	}
	
	public static void main(String[] args) {
		ArrayList<LectureInfoBean> list = CSVParser.getInstance().parseCSV("D:/Anh/OneDrive/문서/2015_TimeTable.csv");
		QueryStorage.getInstance().loadQueryFromXML("D:/Anh/OneDrive/문서/query.txt");
		
//		String test = "132efsdfsd";
//		String tt[] = test.split("-");
		
		for(int i=0,len=list.size(); i<len; i++) {
			LectureDao.getInstance().insertLectureInfo(list.get(i));
		}
	}
}
