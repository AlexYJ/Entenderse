package com.tcp.team.server.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcp.team.server.LeopardObject;
import com.tcp.team.server.bean.LectureInfoBean;
import com.tcp.team.server.dao.LectureDao;
import com.tcp.team.util.JsonMaker;

/**
 * @author Leopard
 * @설명	모든 get요청이 DispatcherServlet으로 들어온다.
 * 			그리고 DispatcherServlet은 들어온 요청을 분석해서 해당 Servlet으로 Dispatcher한다
 */
@WebServlet("/AdminServlet")
public class getLectureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getLectureServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		LectureInfoBean bean = new LectureInfoBean();
		bean.setMajor("컴공");
//		bean.setGrade("2");
		ArrayList<LeopardObject> list = LectureDao.getInstance().selectLectureInfo(bean);
//		ArrayList<LeopardObject> list = LectureDao.getInstance().selectLectureInfoByGradeMajor(bean);
		
		String json = JsonMaker.getInstance().toJsonList(list);
		
		// response의 인코딩을 EUC-KR로 바꾼다
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(json);
	}

	/**
	 * @설명 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	}
}