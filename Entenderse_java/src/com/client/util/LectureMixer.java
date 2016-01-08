package com.client.util;

import java.util.ArrayList;
import java.util.HashMap;

import com.client.bean.LectureInfoBean;
import com.client.bean.TimeTable;


/**
 * @author		Nightmare
 * @설명		Json으로 입력된 과목을 조합해 주는 클래스이다
 * 				과목의 조합은 재귀호출로 구현 하였다
 * 				재귀 호출의 방법은 포함/안포함 
 */
public class LectureMixer {
	private int maxCredit = 100;
	private int minCredit = 0;
	private LectureListMap listMap = null;
	private ArrayList<String> lectureIDList = null;
	private ArrayList<TimeTable> tableList = null;
	private HashMap<Character, Integer> dayMap = null;
	// 조합의 기본이 되는 시간표이다. 
	// 테이블을 조합할 때, 이 테이블을 사용하게 된다
	private TimeTable baseTimeTable = null;
	private HashMap<String, ArrayList<LectureInfoBean>> lectureInfoMap = null;
	
	public LectureMixer(ArrayList<LectureInfoBean> list) {
		
		tableList = new ArrayList<TimeTable>();
		listMap = new LectureListMap();
		
		// 과목 정보를 모두 만든다
		listMap.makeLectureList(list);
		lectureIDList = listMap.getLectureIDList();
		
		lectureInfoMap = listMap.getLectureInfo();
	}
	
	public int getMaxCredit() {
		return maxCredit;
	}

	public void setMaxCredit(int maxCredit) {
		this.maxCredit = maxCredit;
	}

	public int getMinCredit() {
		return minCredit;
	}

	public void setMinCredit(int minCredit) {
		this.minCredit = minCredit;
	}

	public void setBaseTable(TimeTable baseTable) {
		
		this.baseTimeTable = baseTable;
	}
	
	public ArrayList<TimeTable> mixLecture() throws CloneNotSupportedException {
		
		if(baseTimeTable != null)
			mixLectureRecur(baseTimeTable.clone(), 0, lectureIDList.size());
		else
			mixLectureRecur(new TimeTable(lectureInfoMap), 0, lectureIDList.size());
		
		// 조합의 기본이 되는 테이블의 인스턴스의 클론을 넘겨준다
		// 클론을 넘겨주는 이유는 레퍼런스 형이기 때문에 기본 테이블의 정보를 수정하지 못하게 하기 위해서이다
		
//		
		
		return tableList;
	}
	
	/**
	 * @param 	curIndex
	 * @param 	untilIndex
	 * @throws CloneNotSupportedException 
	 * @설명	재귀적으로 포함/미포함으로 시간표의 모든 경우의 수를 만든다
	 * 			현재 만들어 지는데 제한이 존재하지 않는다
	 * 			잎으로 공강이라던지, 픽스 되어야 하는 과목이라던지에 대한 처리를 추가한다
	 */
	private void mixLectureRecur(TimeTable tableInfo,int curIndex, int untilIndex) throws CloneNotSupportedException {
		
		if( curIndex >= untilIndex ) {
			if(tableInfo.getCredit() < minCredit || tableInfo.getCredit() > maxCredit) {
				return;
			}
			if (tableInfo != null) {
				tableList.add(tableInfo);
			}
			// 중복되는 데이터가 있는 경우의 처리를 여기다가 해준다.. 그런데 어떻게 중복이 발생하게 되는거지? 신기하네..
			return;
		}
		if( tableInfo == null )
			return;
		
		// 이전 시간표에 과목을 추가하는 경우
		String lectureID = lectureIDList.get(curIndex);
		ArrayList<LectureInfoBean> lectureInfo = listMap.getLecture(lectureID);
		tableInfo.setIfNotHasLecture(lectureInfo);
		mixLectureRecur(tableInfo, curIndex+1, untilIndex);
		
		// 현재 과목부터 과목 정보를 추가해 나가는 경우
		if(baseTimeTable != null)
			mixLectureRecur(baseTimeTable.clone(), curIndex+1, untilIndex);
		else
			mixLectureRecur(new TimeTable(lectureInfoMap), curIndex+1, untilIndex);
	}
	
	public static void main(String[] args) throws CloneNotSupportedException {
		
		HttpConnection mng = HttpConnection.getInstance();
		String json = mng.request("http://localhost:8080/TimeTableMaker_Backup/getLectureInfo.do");
		
		Converter converter = Converter.getInstance();
		
		// 받아온 데이터를 Bean 형식으로 만든다. 솔직히 좀 느리긴 하지만 클라이언트 이고 나중에 성능을 감안하여 수정하자
		// 느린 이유, 읽은 데이터를 목적에 따라 중복적으로 읽기 때문.
		// 충분히 나중에 한번만 읽도록 수정할 수 있을거 같다 2015-05-21 by Anh
		ArrayList<LectureInfoBean> list = converter.convertLectureInfo(
				converter.convertBean(json, new LectureInfoBean()));
		
		LectureMixer mixer = new LectureMixer(list);
		ArrayList<TimeTable> timeTableList = mixer.mixLecture();
		
		// mixLecture()를 통해 만들어진 조합을 확인하는 작업을 진행한다
		for(int i=timeTableList.size()-1,len=0; i>=len; i--) {
			System.out.println("-----------------LectureInfo-----------------");
			timeTableList.get(i).printAllLectureNameAndTime();
		}
	}
}
