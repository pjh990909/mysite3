package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;

@WebServlet("/bdc")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String action = request.getParameter("action");
		System.out.println(action);
		
		if("list".equals(action)) {
			System.out.println("list:리스트");
			
			BoardDao boardDao = new BoardDao();
			
			List<BoardVo> boardList =  boardDao.boardSelect();
			
			request.setAttribute("boardList", boardList);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		}else if("read".equals(action)){
			System.out.println("read:읽기");
			
			int no = Integer.parseInt(request.getParameter("no"));
			System.out.println("boardList");
			
			
			BoardDao boardDao = new BoardDao();
			
			System.out.println("boardList");
			
			List<BoardVo> boardList=boardDao.boardSelect01(no);
			
			
			request.setAttribute("boardList", boardList);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");	
		}
		
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
