package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {

	// 필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/web_db";
	private String id = "web";
	private String pw = "web";

	// 메소드- 일반

	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver); // 위에 생성자로 올려주고 변수명으로 넣어줌

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

	} // getConnection()끝

	private void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	} // getConnection()끝

	public List<BoardVo> boardSelect() {
		
		List<BoardVo> boardList = new ArrayList<BoardVo>();

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " select b.no, ";
			query += " 		  b.title, ";
			query += " 		  u.name, ";
			query += " 		  b.hit, ";
			query += " 		  b.reg_date ";
			query += " from board b,users u ";
			query += " where b.user_no = u.no ";
			

			pstmt = conn.prepareStatement(query);
			
			rs = pstmt.executeQuery();
			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("b.no");
				String title = rs.getString("b.title");
				String name = rs.getString("u.name");
				int hit = rs.getInt("b.hit");
				String reg_date = rs.getString("b.reg_date");
				
				BoardVo boardVo = new BoardVo(no, title, name, hit,reg_date);
				boardList.add(boardVo);


			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		return boardList;
	}
	
public List<BoardVo> boardSelect01(int no) {
		
		List<BoardVo> boardList = new ArrayList<BoardVo>();

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " select u.name, ";
			query += " 		  b.hit, ";
			query += " 		  b.reg_date, ";
			query += " 		  b.title, ";
			query += " 		  b.content ";
			query += " from board b ";
			query += " left join users u on b.user_no=u.no ";
			query += " where b.no = ? " ;

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,no);
			
			
			rs = pstmt.executeQuery();
			// 4.결과처리
			while (rs.next()) {
				String name = rs.getString("u.name");
				int hit = rs.getInt("b.hit");
				String reg_date = rs.getString("b.reg_date");
				String title = rs.getString("b.title");
				String content = rs.getString("b.content");
				
				BoardVo boardVo = new BoardVo(no,title, content, hit, reg_date,name);
				boardList.add(boardVo);


			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		return boardList;
	}
}	
