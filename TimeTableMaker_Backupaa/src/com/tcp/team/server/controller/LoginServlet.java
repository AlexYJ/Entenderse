package com.tcp.team.server.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcp.team.logger.LogManager;
import com.tcp.team.server.LeopardObject;
import com.tcp.team.server.bean.LectureInfoBean;
import com.tcp.team.server.bean.UserInfoBean;
import com.tcp.team.server.dao.LectureDao;
import com.tcp.team.server.dao.UserInfoDao;
import com.tcp.team.util.JsonMaker;

/**
 * @author Leopard
 * @설명 Login 기능을 하는 서블릿!
 *
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	/**
	 * @설명 LoginServlet에서 이 doGet()으로 호출된 경우 잘못된 접근! Login은 보안과 관련되므로 doPost로만
	 *     호출하게 만든다
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		LogManager.getInstance().logging("클라이언트로 부터 잘못된 접근이 입력되었습니다");

		// 인코딩을 한국사람이니 한국어로 함
		response.setCharacterEncoding("EUC-KR");
		// 잘못된 접근!
		PrintWriter writer = response.getWriter();
		writer.println("wrong url.. please, check your url");
	}

	/**
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userID = request.getParameter("userID");
		String userPasswd = request.getParameter("userPasswd");

		UserInfoBean bean = new UserInfoBean();
		bean.setUserID(userID);
		bean.setUserPasswd(userPasswd);

		UserInfoDao dao = UserInfoDao.getInstance();

		UserInfoBean isLogin = dao.login(bean);
		// 로그인 실패한 경우
		if (isLogin == null) {
			response.setCharacterEncoding("EUC-KR");
			response.getWriter()
					.print("<html><body> <h1 align='center'> 로그인에 실패하였습니다 </h1></body></html>");
		}

		// 로그인에 성공하게 되면, 해당 사용자가 가지는 전공과목 정보를 select하여 돌려준다!
		LectureInfoBean lecture = new LectureInfoBean();
		lecture.setMajor(isLogin.getUserMajor());
		ArrayList<LeopardObject> lectureList = new ArrayList<LeopardObject>();
		lectureList = LectureDao.getInstance().selectLectureInfo(lecture);
		
		String lectureJsonList = JsonMaker.getInstance()
				.toJsonList(lectureList);

		response.setCharacterEncoding("EUC-KR");
		PrintWriter writer = response.getWriter();
		writer.println(lectureJsonList);
	}

}
