package com.client.bean;

import java.util.HashMap;


/**
 * @author 	Nightmare
 * @설명	인자로 넘어온 Json을 Bean으로 담을 객체
 * 			Beanable을 인자로 전달받아 Json을 만든다	
 *
 */
public class LectureInfoBean implements Beanable {
	
	private String nIndex = null;
	private String lectureCode = null;
	private String lectureName = null;
	private String credit = null;
	private String day = null;
	private String startTime = null;
	private String finishTime = null;
	private String abeek = null;
	private String profName = null;
	private String major = null;
	private String lectureRoom = null;
	private String lectureID = null;
	private String grade = null;
	
	
	public String getGrade() {
		return grade;
	}
	
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public String getnIndex() {
		return nIndex;
	}
	public void setnIndex(String nIndex) {
		this.nIndex = nIndex;
	}
	public String getLectureCode() {
		return lectureCode;
	}
	public void setLectureCode(String lectureCode) {
		this.lectureCode = lectureCode;
	}
	public String getLectureName() {
		return lectureName;
	}
	public void setLectureName(String lectureName) {
		this.lectureName = lectureName;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	public String getAbeek() {
		return abeek;
	}
	public void setAbeek(String abeek) {
		this.abeek = abeek;
	}
	public String getProfName() {
		return profName;
	}
	public void setProfName(String profName) {
		this.profName = profName;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getLectureRoom() {
		return lectureRoom;
	}
	public void setLectureRoom(String lectureRoom) {
		this.lectureRoom = lectureRoom;
	}
	public String getLectureID() {
		return lectureID;
	}
	public void setLectureID(String lectureID) {
		this.lectureID = lectureID;
	}
	
	@Override
	public Beanable toBean(HashMap<String, String> jsonBlock) {
		
		LectureInfoBean bean = new LectureInfoBean();
		
		bean.setAbeek(jsonBlock.get("abeek"));
		bean.setCredit(jsonBlock.get("credit"));
		bean.setDay(jsonBlock.get("day"));
		bean.setFinishTime(jsonBlock.get("finishTime"));
		bean.setLectureCode(jsonBlock.get("lectureCode"));
		bean.setLectureID(jsonBlock.get("lectureID"));
		bean.setLectureName(jsonBlock.get("lectureName"));
		bean.setLectureRoom(jsonBlock.get("lectureRoom"));
		bean.setMajor(jsonBlock.get("major"));
		bean.setnIndex(jsonBlock.get("nIndex"));
		bean.setProfName(jsonBlock.get("profName"));
		bean.setStartTime(jsonBlock.get("startTime"));
		bean.setGrade(jsonBlock.get("grade"));
		
		return bean;
	}
}
