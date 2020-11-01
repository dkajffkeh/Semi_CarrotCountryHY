package com.javachip.carrotcountry.adminBoard.model.dao;

import static com.javachip.carrotcountry.common.JDBCtemplate.close;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import com.javachip.carrotcountry.adminBoard.model.vo.AdminBoard;
import com.javachip.carrotcountry.adminBoard.model.vo.AdminPageInfo;

public class AdminBoardDao {

	private Properties prop = new Properties();
	
	public AdminBoardDao() {
		
		String fileName = AdminBoardDao.class.getResource("/sql/adminBoard/adminBoard-mapper.xml").getPath();
		
		try {
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public int selectListCount(Connection conn) {

		int listCount = 0;
		
		Statement stmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectListCount");
		
		try {
			stmt = conn.createStatement();
			
			rset = stmt.executeQuery(sql);
			
			if (rset.next()) {
				listCount = rset.getInt("listCount");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}
		
		return listCount;
	}

	public ArrayList<AdminBoard> postSelectAll(Connection conn, AdminPageInfo pi) {

		ArrayList<AdminBoard> list = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = prop.getProperty("postSelectAll");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int startRow = (pi.getCurrentPage() -1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				AdminBoard ab = new AdminBoard();

				ab.setPostNo(rs.getInt("post_no"));
				ab.setCategoryName(rs.getString("category_name"));
				ab.setPostName(rs.getString("post_name"));
				ab.setMemNo(rs.getString("mem_userid"));
				ab.setPostEnrollDate(rs.getDate("post_enroll_date"));
				ab.setPostViews(rs.getInt("post_views"));
				ab.setBlindCheck(rs.getString("blind_check"));
				ab.setUsedPostCheck(rs.getInt("used_post_check"));
				ab.setGpPostCheck(rs.getInt("gp_post_check"));

				list.add(ab);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return list;
	}

	public int blindListEnroll(Connection conn, int postNo, String bCheck) {

		int result = 0;
		
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("blindListEnroll");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, bCheck);
			pstmt.setInt(2, postNo);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public ArrayList<AdminBoard> blindListSelectAll(Connection conn, AdminPageInfo pi) {
		
		ArrayList<AdminBoard> list = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("blindListSelectAll");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int startRow = (pi.getCurrentPage() -1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				AdminBoard ab = new AdminBoard();
				
				ab.setPostNo(rset.getInt("post_no"));
				ab.setUsedPostCheck(rset.getInt("used_post_check"));
				ab.setGpPostCheck(rset.getInt("gp_post_check"));
				ab.setCategoryName(rset.getString("category_name"));
				ab.setPostName(rset.getString("post_name"));
				ab.setPostViews(rset.getInt("post_views"));
				ab.setMemNo(rset.getString("mem_userid"));
				ab.setPostEnrollDate(rset.getDate("post_enroll_date"));
				ab.setReportCount(rset.getInt("report_count"));
				
				list.add(ab);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}
	
}
