package com.client.bean;

import java.util.ArrayList;
import java.util.HashMap;


public class TimeTable implements Cloneable {
	
	// 월:0, 화:1, 수:2 ... 와 같은 정보를 저장하는 해시
	private HashMap<Character, Integer> dayMap = null;
	// 행 x 열 시간표
	// 시간표에서 row는 과목 시간 [ ex) 0,1,2,3,4,5,6,7,8,9 ] col은 월, 화, 수, 목, .. 과 같은 정보가 들어간다
	private boolean[][] timeTable = null;
	// 현재 해당 TimeTable 클래스에 포함된 수업의 ID코드, lectureInfoHash를 조회하는데 사용된다
	private ArrayList<String> lectureIDList = null;
	// 만들까 말까 가장 고민한 자료구조, 현재 TimeTable에 포함되어 있는 과목의 이름이 포함된다
	// 검색의 속도를 생각하여 Hash로 만든다
	// 현재 과목을 가지고 있다면 true를 리턴한다
	private HashMap<String, Boolean> lectureNameHash = null;
	// 현재 과목의 정보를 가지는 테이블
	private HashMap<String, ArrayList<LectureInfoBean>> lectureInfoMap = null;
	// 현재 만들어지는 과목의 총 학점이 몇 학점인지 알려준다
	private int curCredit = 0;
	
	public int getCredit() {
		return this.curCredit;
	}
	
	public void setLectureInfoMap(HashMap<String, ArrayList<LectureInfoBean>> map) {
		
		this.lectureInfoMap = map;
	}
	
	@SuppressWarnings("unchecked")
	public TimeTable clone() throws CloneNotSupportedException {
		
		TimeTable tableBean = (TimeTable) super.clone();
		tableBean.dayMap = (HashMap<Character, Integer>) dayMap.clone();
		tableBean.lectureIDList = (ArrayList<String>) lectureIDList.clone();
		tableBean.lectureNameHash = (HashMap<String, Boolean>) lectureNameHash.clone();
		
		tableBean.timeTable = new boolean[7][10];
		for(int i=0,iLen=timeTable.length; i<iLen; i++) {
			for(int j=0,jLen=timeTable[i].length; j<jLen; j++) {
				tableBean.timeTable[i][j] = timeTable[i][j];
			}
		}
		return tableBean;
	}
	
	/**
	 * @param hash			-	lectureInfoHash에 저장될 과목정보 <String,LectureInfoBean>
	 * @param dayMapInfo	-	월:0, 화:1, .. 과 같은 정보를 저장하는 해시. 즉, 요일을 인덱스로 바꿔주는 해시이다
	 */
	public TimeTable(HashMap<String, ArrayList<LectureInfoBean>> hash) {
		
		lectureInfoMap = hash;
		
		// 행 x 열의 시간표 맵을 만든다
		timeTable = new boolean[7][10];
		lectureIDList = new ArrayList<String>();
		lectureNameHash = new HashMap<String, Boolean>();
		// 월:0, 화:1 과 같은 정보를 가지는 해시, 위와 마찬가지로 모든 클래스가 참조하게 만든다
		dayMap = new HashMap<Character, Integer>();
		dayMap.put('월', 0);
		dayMap.put('화', 1);
		dayMap.put('수', 2);
		dayMap.put('목', 3);
		dayMap.put('금', 4);
		dayMap.put('토', 5);
		dayMap.put('일', 6);
	}
	
	/**
	 * @param chDay			-	공강으로 설정한 날짜 ex) 월,화,수,목,금,토,일의 문자가 들어온다
	 * @param beginTime		-	공강으로 설정할 시작 시간을 설정한다
	 * @param endTime		-	공강으로 설정한 마지막 시간을 설정한다
	 */
	public void setBreakTime(Character chDay,int beginTime,int endTime) {
		
		int day = dayMap.get(chDay);
		
		for(int j=beginTime; j<=endTime; j++) {
			timeTable[day][j] = true;
		}
		
	}
	
	/**
	 * @param	lectureInfo
	 * @return	boolean			만약 같은 lectureName과 lectureCode의 강의가 있다면 true, 없다면 false
	 * 							하지만 lectureCode는 lectureName을 함축하므로 lectureName으로만 검색한다
	 * 							경우의 수를 줄이는 한 단계이므로 나름 중요하다. 예외가 있을지 검색해 본다 
	 */
	private boolean hasSameLecture(String lectureName) {
		
		boolean flag = false;
		
		// 만약, 현재 입력으로 들어온 과목을 이미 수강하고 있다면,
		if( lectureNameHash.containsKey(lectureName) ) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * @param 	beanList	-	ArrayList<LectureInfo>인 이유는, 하나의 수업이 당일에만 끝나는게 아니라 
	 * 							다른 날까지 연장될 수 있기 때문이다.
	 * 							예를들어, 컴퓨터 아키텍쳐 수업은 월,화에 나누어서 하게된다  
	 * @return				-	과목을 등록하는 시간에 중복되는 수업이 없다면 true 그렇지 않다면 false
	 */
	private boolean hasSameTimeLecture(ArrayList<LectureInfoBean> beanList) {
		
		for(int i=0,len=beanList.size(); i<len; i++) {
			
			LectureInfoBean bean = beanList.get(i);
			
			int beginTime = Integer.parseInt(bean.getStartTime());
			int endTime = Integer.parseInt(bean.getFinishTime());
			int day = dayMap.get(bean.getDay().charAt(0));
			
			// 같은 시간에 수강중인 과목이 있다면,
			for(int j=beginTime; j<=endTime; j++) {
				
				if( timeTable[day][j] == true ) {
					// false를 리턴한다
					return true;
				}
			}
		}
		// 코드가 이쪽까지 왔다는 의미는 입력된 과목과 같은 시간에 중복되는 과목이 없다는 의미
		return false;
	}
	
	private boolean hasSameTimeLecture(LectureInfoBean bean) {

		if (bean != null) {

			int beginTime = Integer.parseInt(bean.getStartTime());
			int endTime = Integer.parseInt(bean.getFinishTime());
			int day = dayMap.get(bean.getDay().charAt(0));

			// 같은 시간에 수강중인 과목이 있다면,
			for (int j = beginTime; j <= endTime; j++) {

				if (timeTable[day][j] == true) {
					// false를 리턴한다
					return true;
				}
			}
		}
		// 코드가 이쪽까지 왔다는 의미는 입력된 과목과 같은 시간에 중복되는 과목이 없다는 의미
		return false;
	}
	
	/**
	 * @param	bean
	 * @설명	모든 조건이 충족되어 이 메소드를 호출하게 되면 시간표에 입력으러 들어온 과목이 등록되게 된다
	 * 		초반에 데이터가 잘 못 들어오게 되면 이상한 값이 호출될 수 있으므로 예외처리를 추가한다. - 2015.05.23 Anh	
	 */
	private void registLecture(ArrayList<LectureInfoBean> lectureList) {
		
		LectureInfoBean bean = null;
		for(int i=0,len=lectureList.size(); i<len; i++) {
			
			bean = lectureList.get(i);
			
			int beginTime = Integer.parseInt(bean.getStartTime());
			int endTime = Integer.parseInt(bean.getFinishTime());
			int day = dayMap.get(bean.getDay().charAt(0));
			
			// 시간표에 과목 정보를 입력한다
			for(int j=beginTime; j<=endTime; j++) 
				timeTable[day][j] = true;
			// 현재 시간표의 총 학점을 갱신한다
			curCredit += Integer.parseInt(bean.getCredit());
			// 현재 시간표가 어떤 과목을 추가했는지를 저장한다
			lectureNameHash.put(bean.getLectureName(), true);
			// 과목의 정보를 조회할 과목 식별값을 저장한다
			lectureIDList.add(bean.getLectureID());
		}
	}
	
	private void registLecture(LectureInfoBean bean) {

		int beginTime = Integer.parseInt(bean.getStartTime());
		int endTime = Integer.parseInt(bean.getFinishTime());
		int day = dayMap.get(bean.getDay().charAt(0));
		
		// 시간표에 과목 정보를 입력한다
		for(int j=beginTime; j<=endTime; j++) 
			timeTable[day][j] = true;
		// 현재 시간표의 총 학점을 갱신한다
		curCredit += Integer.parseInt(bean.getCredit());
		// 현재 시간표가 어떤 과목을 추가했는지를 저장한다
		lectureNameHash.put(bean.getLectureName(), true);
		// 과목의 정보를 조회할 과목 식별값을 저장한다
		lectureIDList.add(bean.getLectureID());
	}
	
	/**
	 * @param 	bean
	 * @설명	개발자는 모든 과목에 대하여 이 함수만 호출하면 된다. 지가 알아서 만든다
	 * 			해당 테이블에 중복되는 과목이 있는 경우,
	 * 			그리고 동일 시간에 중복되는 과목이 있는 경우에는 과목 정보를 포함하지 않고
	 * 			중복되는 과목이 완전히 없는 경우에만 과목 테이블에 추가한다
	 */
	public boolean setIfNotHasLecture(ArrayList<LectureInfoBean> lectureList) {
		
		boolean flag = false;
		if (lectureList != null) {
			
			LectureInfoBean bean = null;
			for(int i=0,len=lectureList.size(); i<len; i++) {

				bean = lectureList.get(i);
				
				if (setIfNotHasLecture(bean)) { 
					registLecture(bean);
					flag = true;
				}
			}
		}
		
		return flag;
	}
	
	public boolean setIfNotHasLecture(LectureInfoBean bean) {
		
		if(bean != null) {
			
			if (hasSameLecture(bean.getLectureName()))
				return false;
			
			if (hasSameTimeLecture(bean))
				return false;
			
			return true;
		}
		return false;
	}
	
	/**
	 * 테스트용
	 */
	public void printAllLecture() {
		
		for(int i=0,len=lectureIDList.size(); i<len; i++) {
			System.out.println(" /"+lectureIDList.get(i));
		}
	}
	
	public void printTimeTable() {
		
		for(int i=0,iLen=timeTable.length; i<iLen; i++) {
			for(int j=0,jLen=timeTable[i].length; j<jLen; j++) {
				System.out.print("[ "+timeTable[i][j]+" ]");
			}
			System.out.println();
		}
	}
	
	/**
	 * 테스트 용도
	 */
	public void printAllLectureNameAndTime() {
		
		String lectureID = null;
		
		System.out.println("총 학점 :"+getCredit());
		for(int i=0,len=lectureIDList.size(); i<len; i++) {
			lectureID = lectureIDList.get(i);
			ArrayList<LectureInfoBean> list = lectureInfoMap.get(lectureID);
			
			for(int j=0,size=list.size(); j<size; j++) {
				System.out.println("---------------------------------");
				System.out.println("lecture ID		:"+list.get(j).getLectureID());
				System.out.println("lecture Name 		:"+list.get(j).getLectureName());
				System.out.println("lecture Day  		:"+list.get(j).getDay());
				System.out.println("lecture Start Time 	:"+list.get(j).getStartTime());
				System.out.println("lecture End Time 	:"+list.get(j).getFinishTime());
				System.out.println("lecture Cridet		:"+list.get(j).getCredit());
			}
		}
	}
}
