package com.client.controller;

import javax.swing.JOptionPane;

import com.client.model.BeanBucket;
/*
 *  LoginControl
 *  사용자가 입력한 학번과 학년정보를 서버에게 전달한다.
 */
public class LoginControl {
	private String id;
	private String passwd;
	public LoginControl(String id, String passwd){
		setId(id);
		setPasswd(passwd);


	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public boolean connectServer(){
		HttpConnection hcn = HttpConnection.getInstance();
		try{
			String json = hcn.request("http://localhost:9989/TimeTableMaker_Backup/getLectureInfo.do" );
//			JOptionPane.showMessageDialog(null, json);
			BeanBucket beanbucket = BeanBucket.getInstance(json);
//			JOptionPane.showMessageDialog(null, beanbucket.getBeanList().get(0).getGrade());
	
			
			
			
		}catch(Exception e){

		JOptionPane.showMessageDialog(null, "연결에 실패하였습니다");
		return false;
		}

		return true;

	}
	/*
	 *  서버에게 학번과 학년정보를 넘겨준다
	 *  성공시 true를 실패시 false를 반환한다.
	 */




}
