package com.client.model;




import java.util.ArrayList;

import com.client.bean.Beanable;
import com.client.bean.LectureInfoBean;
import com.client.controller.HttpConnection;
import com.client.util.Converter;




public class BeanBucket  {
	private static final String String = null;

	private static volatile BeanBucket instance = null;
	
	private ArrayList<Beanable> list;
	private ArrayList<LectureInfoBean> beanList;
	
	public BeanBucket(String json){
		Converter con = Converter.getInstance();
		list = con.convertBean(json, new LectureInfoBean());
		beanList = con.convertLectureInfo(list);
	}
	public static BeanBucket getInstance(String json){
		if(instance == null){

					instance = new BeanBucket(json);

				
		
	
		
		}
		return instance;
	}
	public static BeanBucket getInstance(){
		return instance;
	}
	public ArrayList<LectureInfoBean> getBeanList(){
		return beanList;
	}



}
