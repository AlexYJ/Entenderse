package com.tcp.team.server;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.lang.reflect.*;

import com.tcp.team.logger.LogManager;
import com.tcp.team.server.bean.DataWrapper;


public abstract class LeopardObject implements Cloneable {
	
	
	// 해당 객체는 LeopardObject를 상속받는 클래스에서 사용된다.
	protected ArrayList<DataWrapper> localValueList = null;
	
	public abstract ArrayList<DataWrapper> getLocalValList();
	
	/*
	public ArrayList<DataWrapper> getLocalValList() {
		
		try {
			Class<?> classInfo = Class.forName(this.getClass().getName());
			Field[] fieldInfos = classInfo.getDeclaredFields();
			
			for(int i=0,len=fieldInfos.length; i<len; i++) {
				localValueList.add();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	*/
	
	public LeopardObject clone() {
		LeopardObject retVal = null;
		try {
			retVal = (LeopardObject) super.clone();
		} catch (CloneNotSupportedException e) {
			LogManager.getInstance().logging("[LeopardObject] Clone Error");
		}
		
		return retVal;
	}
	
	/**
	 * @param set
	 * @설명		LeopardTemplate를 사용하기 위해서 모든 Bean들은 아래 
	 * 			메소드를 오버라이딩 해야 한다.
	 * 			자신의 멤버변수를 순서에 맞게 ResultSet에 set한다
	 * 			현재 모든 기능이 완성된 다음 만들도록 한다 2015-05-12 by Anh
	 */
	/*
	public void setResultSet(ResultSet set){
		try {
			Class<?> classInfo = Class.forName(this.getClass().getName());
			Field[] fieldList = classInfo.getDeclaredFields();
			Method[] methodList = classInfo.getDeclaredMethods();
			
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append("set");
			for(int fieldIndex=0,fieldLen=fieldList.length; fieldIndex<fieldLen; fieldIndex++) {
				StringBuilder setMethod = new StringBuilder();
				setMethod.append("set");
				setMethod.append(fieldList[fieldIndex]);
				
				// debug !!
				LogManager.getInstance().logging(setMethod.toString());
				
				// 대소문자에 관계없이 비슷한 이름의 메소드를 가지고 온다.
				String targetMethodName = null;
				for(int methodIndex=0,methodLen=methodList.length; methodIndex<methodLen; methodIndex++) {
					String getMethodName = setMethod.toString();
					if(getMethodName.equalsIgnoreCase(methodList[methodIndex].getName())){
						targetMethodName = methodList[methodIndex].getName();
						break;
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	*/
	
	public abstract void setResultSet(ResultSet set);
	
	/**
	 * @return
	 * 			LeopardObject를 상속하는 객체의 멤버변수 - 멤버변수 값의 쌍으로 
	 * 			Json을 만들어 주는 메소드
	 * 			ArrayList 또는 배열에 대하여도 Json이 적용되도록 만들자
	 * 			reflection과 String에 대소문자에 관계없이 값을 비교할 수 있는 메소드가 있어서 다행!
	 * 			이라고는 하지만 만드는데 시간이 너무 오래 걸렸다 ㅜㅜ
	 * 			그리고 웬만한 상속은 (인터페이스) -> (추상 클래스) -> (클래스) 로 하자
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	protected String toJson(LeopardObject obj) {
		
		StringBuilder strBuilder = null;
		
		try {
			Class<?> classInfo = Class.forName(this.getClass().getName());
			// 해당 클래스에 선언된 필드의 이름을 모두 가지고 온다. - 변수 '값'이 아닌 '이름' 이라는 점에 유의한다
			Field[] fieldList = classInfo.getDeclaredFields();
			// 해당 클래스에 선언된 메소드의 이름을 모두 가지고 온다
			Method[] methodList = classInfo.getDeclaredMethods();
			
			strBuilder = new StringBuilder();
			
			strBuilder.append("{");
			for(int fieldIndex=0,fieldLen=fieldList.length; fieldIndex<fieldLen; fieldIndex++) {
				
				StringBuilder getMethod = new StringBuilder();
				getMethod.append("get");
				
				String fieldName = null;
				fieldName = fieldList[fieldIndex].getName();
				
				getMethod.append(fieldName);
				
				// 대소문자에 관계없이 비슷한 이름의 메소드를 가지고 온다.
				String targetMethodName = null;
				for(int methodIndex=0,methodLen=methodList.length; methodIndex<methodLen; methodIndex++) {
					String getMethodName = getMethod.toString();
					if(getMethodName.equalsIgnoreCase(methodList[methodIndex].getName())){
						System.out.println(methodList[methodIndex].getName());
						targetMethodName = methodList[methodIndex].getName();
						break;
					}
				}
				
				// Beans의 getter함수의 값이 저장될 변수
				Object retVal = null;
				try {
					try {
						// targetMethodName의 메소드를 가지고 온다
						Method method = classInfo.getMethod(targetMethodName);
						try {
							// 매개변수 obj의 메소드 method를 호출하여 그 값을 retVal에 저장한다
							retVal = method.invoke(obj);
						} catch (InvocationTargetException e) {
							LogManager.getInstance().logging(e.getMessage());
						} catch (IllegalAccessException e) {
							LogManager.getInstance().logging(e.getMessage());
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
				} catch (SecurityException e) {
					LogManager.getInstance().logging(e.getStackTrace().toString());
				}
				
				// 필드 이름 + 값 의 조합으로 Json을 만든다
				strBuilder.append("\""+fieldName+"\"");
				strBuilder.append(":");
				strBuilder.append("\"");

				strBuilder.append( retVal.toString() );
				strBuilder.append("\",");
			}
			// 마지막 ','를 지운다 -- Json 문법을 만족하기 위해
			strBuilder.deleteCharAt(strBuilder.length()-1);
			strBuilder.append("}");
			
		} catch (ClassNotFoundException e) {
			LogManager.getInstance().logging(this.getClass().toString()+" "+e.getMessage());
		}
		return strBuilder.toString();
	}
	
	public String toJson() {
		
		return this.toJson(this);
	}
}