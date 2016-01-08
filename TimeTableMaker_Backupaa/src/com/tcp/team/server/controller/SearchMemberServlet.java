package com.tcp.team.server.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcp.team.server.LeopardObject;
import com.tcp.team.server.bean.UserInfoBean;
import com.tcp.team.server.dao.UserInfoDao;
import com.tcp.team.util.JsonMaker;

/**
 * @author Leopard
 * @설명	멤버의 아이디를 통해 멤버를 조회한다. 이때 조회되는 멤버는 비밀번호 정보는 감추게 된다
 *
 */
@WebServlet("/SearchMemberServlet")
public class SearchMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SearchMemberServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userID = request.getParameter("userID");
		UserInfoBean userBean = new UserInfoBean();
		
		userBean.setUserID(userID);
		
		UserInfoDao userDao = UserInfoDao.getInstance();
		UserInfoBean member = userDao.searchMember(userBean);
		
		// 보안 문제로 패스워드는 감춘다
		member.setUserPasswd(null);
		
		// bean을 json으로 바꾼다
		String json = member.toJson();
		
		response.setCharacterEncoding("EUC-KR");
		PrintWriter writer = response.getWriter();
		writer.println(json);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("EUC-KR");
		PrintWriter writer = response.getWriter();
		
		writer.println("잘못된 접근 입니다.");
	}
}
