package com.client.util;

import java.util.ArrayList;
import java.util.HashMap;

import com.client.bean.LectureInfoBean;


public class LectureListMap {
	
	// lectureID를 key로 과목정보가 들어 있다
	// <lectureID, ArrayList<LectureInfoBean>>인 이유는 하나의 과목이 여러 날 로 분할될 수 있기 때문이다
	private HashMap<String, ArrayList<LectureInfoBean>> lectureListMap = null;
	private ArrayList<String> lectureIDList = null;
	
	public LectureListMap() {
		lectureListMap = new HashMap<String, ArrayList<LectureInfoBean>>();
		lectureIDList = new ArrayList<String>();
	}
	
	/**
	 * @param 	lectureID	-	과목 고유 번호
	 * @return	ArrayList<LectureInfoBean>	- 값을 가지고 있다면 
	 * 			null						- 값을 가지고 있지 않다면
	 */
	public ArrayList<LectureInfoBean> getLecture(String lectureID) {
		
		if( lectureListMap.containsKey(lectureID) ) 
			return lectureListMap.get(lectureID);
		
		return null;
	}
	
	public HashMap<String, ArrayList<LectureInfoBean>> getLectureInfo() {
		
		return lectureListMap;
	}
	
	public ArrayList<String> getLectureIDList() {
		
		return lectureIDList;
	}
	
	/**
	 * @param 	lectureList
	 * @설명	쓰이기 전에 가장 먼저 호출되어야 할 함수이며, 과목정보를 완성한다
	 */
	public void makeLectureList(ArrayList<LectureInfoBean> lectureList) {
		
		String lectureID = null;
		for(int i=0,len=lectureList.size(); i<len; i++) {
			
			// 저장해야할 인스턴스 이므로 new를 사용하여 만든다. 나중에 왜 new를 사용했는지 까먹을거 같아서..
			LectureInfoBean lectureBean = (LectureInfoBean) lectureList.get(i);
			lectureID = lectureBean.getLectureID();
			
			// 만약 해당 과목을 포함하고 있다면,
			if(lectureListMap.containsKey(lectureID)) {
				ArrayList<LectureInfoBean> list = lectureListMap.get(lectureID);
				list.add(lectureBean);
			}
			// 만약 해당 과목을 포함하고 있지 않다면 
			else {
				ArrayList<LectureInfoBean> list = new ArrayList<LectureInfoBean>();
				list.add(lectureBean);
				
				// lectureIDList에 저장되는 lecture의 ID를 저장한다
				// 이 부분에 저장하는 이유는 lectureID의 저장은 한 번만 이루져야 하기 때문이다
				lectureIDList.add(lectureID);
				
				// 해시에 lectureID를 저장한다
				lectureListMap.put(lectureID, list);
			}
		}
	}
	
	public static void main(String[] args) {
		
		HttpConnection mng = HttpConnection.getInstance();
		String json = mng.request("http://localhost:8080/TimeTableMaker_Backup/getLectureInfo.do");
		
		Converter converter = Converter.getInstance();
		
		// 받아온 데이터를 Bean 형식으로 만든다. 솔직히 좀 느리긴 하지만 클라이언트 이고 나중에 성능을 감안하여 수정하자
		// 느린 이유, 읽은 데이터를 목적에 따라 중복적으로 읽기 때문.
		// 충분히 나중에 한번만 읽도록 수정할 수 있을거 같다 2015-05-21 by Anh
		ArrayList<LectureInfoBean> list = converter.convertLectureInfo(converter.convertBean(json, new LectureInfoBean()));
		LectureListMap listMap = new LectureListMap();
		listMap.makeLectureList(list);
		
		ArrayList<String> lll = listMap.getLectureIDList();
		for(int i=0,len=lll.size(); i<len; i++) {
			ArrayList<LectureInfoBean> beanList = listMap.getLecture(lll.get(i));
			for(int j=0,jLen=beanList.size(); j<jLen; j++) {
				System.out.print(beanList.get(j).getLectureName()+" ");
			}
		}
	}
}
