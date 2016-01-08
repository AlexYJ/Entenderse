package com.tcp.team.server.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcp.team.logger.LogManager;
import com.tcp.team.server.bean.UserInfoBean;
import com.tcp.team.server.dao.UserInfoDao;

/**
 * @설명	회원가입하는 서블릿!
 */
@WebServlet("/JoinMemberServlet")
public class JoinMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public JoinMemberServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 회원가입에서 get방식은 역시나 잘못된 접근.. 체크해 준다
		PrintWriter writer = response.getWriter();
		writer.println("check your url.. ");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("EUC-KR");
		
		String userID = request.getParameter("userID");
		String userPasswd = request.getParameter("userPasswd");
		String userMajor = request.getParameter("userMajor");
		
		LogManager.getInstance().logging(userMajor);
		
		UserInfoBean userBean = new UserInfoBean();
		userBean.setUserID(userID);
		userBean.setUserPasswd(userPasswd);
		userBean.setUserMajor(userMajor);
		
		UserInfoDao userDao = UserInfoDao.getInstance();
		userDao.insertUserInfo(userBean);
		
		// response를 얻기 전에 미리 인코딩을 해야 출력값이 인코딩된 값으로 결정된다
		response.setCharacterEncoding("EUC-KR");
		PrintWriter writer = response.getWriter();
		
		writer.println("회원가입 완료");
	}
}
