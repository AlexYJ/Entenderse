package com.tcp.team.server.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.tcp.team.logger.LogManager;
import com.tcp.team.server.LeopardObject;
import com.tcp.team.server.dao.LectureDao;
import com.tcp.team.util.JsonMaker;
import com.tcp.team.util.QueryStorage;

/**
 * @author Leopard by Anh
 * @설명	   DB및 데이터를 주고 받기 위해 쓰이는 DTO
 * 		   Json 변환을 위해 상위클래스를 상속받는다
 *
 */
public class LectureInfoBean extends LeopardObject {
	
	private DataWrapper nIndex = null;
	// 과목코드
	private DataWrapper lectureCode = null;
	// 과목이름
	private DataWrapper lectureName = null;
	// 학점
	private DataWrapper credit = null;
	// 강의날짜
	private DataWrapper day = null;
	// 강의시간
	private DataWrapper startTime = null;
	// 강의 마치는 시간
	private DataWrapper finishTime = null;
	// 공학인증
	private DataWrapper abeek = null;
	// 교수님 이름
	private DataWrapper profName = null;
	// 전공정보
	private DataWrapper major = null;
	// 강의실 정보
	private DataWrapper lectureRoom = null;
	// Bean의 PK로 과목코드 + 교수명 + nIndex 으로 한다
	private DataWrapper lectureID = null;
	// 과목 학년 정보
	private DataWrapper grade = null;
	
	public LectureInfoBean() {
		
		nIndex = new DataWrapper();
		lectureCode = new DataWrapper();
		lectureName = new DataWrapper();
		credit = new DataWrapper();
		day = new DataWrapper();
		startTime = new DataWrapper();
		finishTime = new DataWrapper();
		abeek = new DataWrapper();
		profName = new DataWrapper();
		major = new DataWrapper();
		lectureRoom = new DataWrapper();
		lectureID = new DataWrapper();
		grade = new DataWrapper();
		
		// 필드에 적힌 순서대로 들어갈 필요가 있다..
		
		localValueList = new ArrayList<DataWrapper>();
		
		localValueList.add(nIndex);
		localValueList.add(lectureCode);
		localValueList.add(lectureName);
		localValueList.add(credit);
		localValueList.add(day);
		localValueList.add(startTime);
		localValueList.add(finishTime);
		localValueList.add(abeek);
		localValueList.add(profName);
		localValueList.add(major);
		localValueList.add(lectureRoom);
		localValueList.add(lectureID);
		localValueList.add(grade);
	}

	public Object getGrade() {
		return grade.getData();
	}
	public void setGrade(Object grade) {
		this.grade.setData(grade);
	}
	
	public Object getnIndex() {
		return nIndex.getData();
	}

	public void setnIndex(Object nIndex) {
		this.nIndex.setData(nIndex);
	}

	public Object getLectureCode() {
		return lectureCode.getData();
	}

	public void setLectureCode(Object lectureCode) {
		this.lectureCode.setData(lectureCode);
	}

	public Object getLectureName() {
		return lectureName.getData();
	}

	public void setLectureName(Object lectureName) {
		this.lectureName.setData(lectureName);
	}

	public Object getCredit() {
		return credit.getData();
	}

	public void setCredit(Object credit) {
		this.credit.setData(credit);
	}

	public Object getDay() {
		return day.getData();
	}

	public void setDay(Object day) {
		this.day.setData(day);
	}

	public Object getStartTime() {
		return startTime.getData();
	}

	public void setStartTime(Object startTime) {
		this.startTime.setData(startTime);
	}

	public Object getFinishTime() {
		return finishTime.getData();
	}

	public void setFinishTime(Object finishTime) {
		this.finishTime.setData(finishTime);
	}

	public Object getAbeek() {
		return abeek.getData();
	}

	public void setAbeek(Object abeek) {
		this.abeek.setData(abeek);
	}

	public Object getProfName() {
		return profName.getData();
	}

	public void setProfName(Object profName) {
		this.profName.setData(profName);
	}
	
	public void setMajor(Object major) {
		this.major.setData(major);
	}
	
	public Object getMajor() {
		return major.getData();
	}
	
	public Object getLectureRoom() {
		return lectureRoom.getData();
	}
	
	public void setLectureRoom(Object lectureRoom) {
		this.lectureRoom.setData(lectureRoom);
	}
	

	public Object getLectureID() {
		return lectureID.getData();
	}
	
	public void setLectureID(Object lectureID) {
		this.lectureID.setData(lectureID);
	}
	public String toJson() {
		
		String json = null;
		try {
			json = super.toJson(this);
		} catch (IllegalArgumentException e) {
			LogManager.getInstance().logging(e.getStackTrace().toString());
		}
		
		return json;
	}
	
	@Override
	public void setResultSet(ResultSet set) {
		try {
			setnIndex(set.getObject("nIndex"));
			setLectureCode(set.getObject("lectureCode"));
			setLectureName(set.getObject("lectureName"));
			setCredit(set.getObject("credit"));
			setDay(set.getObject("day"));
			setStartTime(set.getObject("startTime"));
			setFinishTime(set.getObject("finishTime"));
			setAbeek(set.getObject("abeek"));
			setProfName(set.getObject("profName"));
			setMajor(set.getObject("major"));
			setLectureRoom(set.getObject("lectureRoom"));
			setLectureID(set.getObject("lectureID"));
			setGrade(set.getObject("grade"));
			
		} catch (SQLException e) {
			LogManager.getInstance().logging(e.toString());
		}
	}

	@Override
	public ArrayList<DataWrapper> getLocalValList() {
		return localValueList;
	}
	
	public LeopardObject clone() {
		LectureInfoBean cloneBean = (LectureInfoBean) super.clone();
		cloneBean.nIndex = (DataWrapper) nIndex.clone();
		cloneBean.lectureCode = (DataWrapper) lectureCode.clone();
		cloneBean.lectureName = (DataWrapper) lectureName.clone();
		cloneBean.credit = (DataWrapper) credit.clone();
		cloneBean.day = (DataWrapper) day.clone();
		cloneBean.startTime = (DataWrapper) startTime.clone();
		cloneBean.finishTime = (DataWrapper) finishTime.clone();
		cloneBean.abeek = (DataWrapper) abeek.clone();
		cloneBean.profName = (DataWrapper) profName.clone();
		cloneBean.major = (DataWrapper) major.clone();
		cloneBean.lectureRoom = (DataWrapper) lectureRoom.clone();
		cloneBean.lectureID = (DataWrapper) lectureID.clone();
		cloneBean.grade = (DataWrapper) grade.clone();
		
		cloneBean.localValueList = new ArrayList<DataWrapper>();
		
		cloneBean.localValueList.add(cloneBean.nIndex);
		cloneBean.localValueList.add(cloneBean.lectureCode);
		cloneBean.localValueList.add(cloneBean.lectureName);
		cloneBean.localValueList.add(cloneBean.credit);
		cloneBean.localValueList.add(cloneBean.day);
		cloneBean.localValueList.add(cloneBean.startTime);
		cloneBean.localValueList.add(cloneBean.finishTime);
		cloneBean.localValueList.add(cloneBean.abeek);
		cloneBean.localValueList.add(cloneBean.profName);
		cloneBean.localValueList.add(cloneBean.major);
		cloneBean.localValueList.add(cloneBean.lectureRoom);
		cloneBean.localValueList.add(cloneBean.lectureID);
		cloneBean.localValueList.add(cloneBean.grade);
		
		return cloneBean;
	}
	
	public static void main(String[] args) {
		LectureInfoBean bean2 = new LectureInfoBean();
		
		bean2.setMajor("산공");
		
		// C:/Users/Anh/OneDrive/Tcp/leopard/query.txt
		// Dao에서 사용될 쿼리를 미리 로드함
		QueryStorage.getInstance().loadQueryFromXML("D:/Anh/OneDrive/문서/test.xml");
		
		ArrayList<LeopardObject> list = LectureDao.getInstance().selectLectureInfo(bean2);
		
		System.out.println(JsonMaker.getInstance().toJsonList(list));
		
		/*
		ArrayList<LeopardObject> list = new ArrayList<LeopardObject>();
		list.add(bean);
		list.add(bean2);
		
		System.out.println(JsonMaker.getInstance().toJsonList(list));
		*/
	}

}
