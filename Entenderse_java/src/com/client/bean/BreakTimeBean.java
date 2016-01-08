package com.client.bean;

import java.util.HashMap;

/**
 * @author 	Anh
 * @설명		사용자로 부터 입력받은 공강정보
 * 			요일, 시작시간, 끝시간이 들어간다
 *
 */
public class BreakTimeBean {
	
	private HashMap<Character, Integer> dayInfo = null;
	private int beginTime = -1;
	private int endTime = -1;
	
	public int getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(int beginTime) {
		this.beginTime = beginTime;
	}
	public int getEndTime() {
		return endTime;
	}
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
}
