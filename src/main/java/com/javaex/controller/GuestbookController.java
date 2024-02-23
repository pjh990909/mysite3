package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.MemberVo;

@WebServlet("/gbc")
public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		System.out.println(action);

		if ("addList".equals(action)) {
			System.out.println("addlist:등록폼");

			GuestDao guestDao = new GuestDao();

			List<MemberVo> memberList = guestDao.memberSelect();

			request.setAttribute("memberList", memberList);

			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");

		} else if ("insert".equals(action)) {
			System.out.println("insert:등록");

			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");

			/* vo로 묶기 */
			MemberVo memberVo = new MemberVo(name, password, content);
			System.out.println(memberVo.toString());

			// db관련 업무
			GuestDao guestDao = new GuestDao();
			// db저장
			guestDao.memberInsert(memberVo);

			WebUtil.redirect(request, response, "/mysite3/gbc?action=addList");
		} else if ("delete".equals(action)) {
			System.out.println("delete:삭제");
			
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");

			/* vo로 묶기 */
			MemberVo memberVo = new MemberVo(no,password);
			System.out.println(memberVo.toString());

			request.setAttribute("no", no);
			// db관련 업무
			GuestDao guestDao = new GuestDao();
			// db저장
			guestDao.memberDelete(no, password);

			WebUtil.redirect(request, response, "/mysite3/gbc?action=addList");
		}else if("dform".equals(action)) {
			System.out.println("dform:삭제폼");
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			request.setAttribute("no", no);
			
			GuestDao guestDao = new GuestDao();

			List<MemberVo> memberList = guestDao.memberSelect();

			request.setAttribute("memberList", memberList);

			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
			
			
			
			
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
