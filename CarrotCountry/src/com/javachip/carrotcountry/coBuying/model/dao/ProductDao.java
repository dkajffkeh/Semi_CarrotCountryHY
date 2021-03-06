package com.javachip.carrotcountry.coBuying.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;

import com.javachip.carrotcountry.coBuying.model.vo.Account;
import com.javachip.carrotcountry.coBuying.model.vo.Option;
import com.javachip.carrotcountry.coBuying.model.vo.PageInfo;
import com.javachip.carrotcountry.coBuying.model.vo.PostBoardJY;
import com.javachip.carrotcountry.coBuying.model.vo.Product;
import com.javachip.carrotcountry.coBuying.model.vo.QnA;
import com.javachip.carrotcountry.shMarketBoard.mainPage.model.vo.Photo;
import com.javachip.carrotcountry.shMarketBoard.mainPage.model.vo.PostBoard;
import com.javachip.carrotcountry.shMarketBoard.townMarket.model.vo.Location;
import com.javachip.carrotcountry.shMarketBoard.townMarket.model.vo.ShmarketPageInfo;

import static com.javachip.carrotcountry.common.JDBCtemplate.*;

public class ProductDao {

private Properties prop = new Properties();
	
	public ProductDao() {
		
		try {
			prop.loadFromXML(new FileInputStream(QnADao.class.getResource("/sql/coBuying/coBuying.xml").getPath()));
		}  catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	public int increaseCount(Connection conn, int bno) {
		
				int result = 0;
				
				PreparedStatement pstmt = null;
				
				String sql = prop.getProperty("increaseCount");
				
				try {
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setInt(1, bno);
					
					result = pstmt.executeUpdate();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					close(pstmt);
				}
				
				System.out.println(result);
				return result;
				}
	

	
	//
	public int selectProductListCount(Connection conn) {
		// select문 => int (총 갯수)
		
		int listCount = 0;
		
		Statement stmt = null;
		ResultSet rs = null;
		
		String sql = prop.getProperty("selectProductListCount");
		
		try {
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {	
				listCount = rs.getInt("LISTCOUNT");
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(stmt);
		}
		
		return listCount;
	}
	

	
	//
	

	
	
	public PostBoard selectPostBoard(Connection conn, int bno) {
		// select문 => 한 행 
		PostBoard pb = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = prop.getProperty("selectPostBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, bno);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				pb = new PostBoard(rs.getInt("post_no"),
							  rs.getString("post_name"),
							  rs.getString("post_comment"),
							  rs.getString("thumbnail_path"),
							  rs.getString("thumbnail_filename"),
							  rs.getString("thumbnail_loadpath"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
			
		}
		
		return pb;
		
	}
	
	
	
	public Product selectProduct(Connection conn, int bno) {
		// select문 => 한 행 
		Product pd = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = prop.getProperty("selectProduct");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, bno);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				pd = new Product(rs.getInt("post_no"),
							  rs.getDate("POST_ENROLL_DATE"),
							  rs.getString("gp_deadline"),
							  rs.getInt("gp_price"),
							  rs.getInt("GP_DRATE"),
							  rs.getInt("gp_minpeople"),
							  rs.getInt("gp_people"),
							  rs.getInt("gp_dprice"),
							  rs.getString("gp_refund"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
			
		}
		
		return pd;
		
	}
	
	
	
	public ArrayList<Option> selectOption(Connection conn, int bno){
			// select문 => 여러행
			ArrayList<Option> oList = new ArrayList<>();
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String sql = prop.getProperty("selectOption");
			
			try {
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, bno);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					Option o = new Option();
					o.setOptionNo(rs.getInt("option_no"));
					o.setOptionName(rs.getString("option_name"));
					oList.add(o);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}
			
			return oList;
			
			
		}
		

	
	public ArrayList<Account> selectAccount(Connection conn, int bno){
		// select문 => 여러행
		ArrayList<Account> aList = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = prop.getProperty("selectAccount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, bno);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Account a = new Account();
				a.setAccount(rs.getString("bank"));
				aList.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return aList;
		
		
	}
	
	
	public ArrayList<Photo> selectPhoto(Connection conn, int bno){
		// select문 => 여러행
		ArrayList<Photo> ptList = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = prop.getProperty("selectPhoto");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, bno);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Photo pt = new Photo();
				pt.setPostNo(rs.getInt("post_no"));
				pt.setPhotoPath(rs.getString("photo_path"));
				pt.setPhotoFileName(rs.getString("photo_filename"));
				pt.setPhotoLoadPath(rs.getString("photo_loadpath"));
				
				ptList.add(pt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return ptList;
		
		
	}
	
	
	public ArrayList<Product> selectMainProductList(Connection conn, PageInfo pi){
		// select문 => 여러행 조회
		ArrayList<Product> pList = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = prop.getProperty("selectMainProductList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				pList.add(new Product(rs.getInt("post_no"),
										rs.getString("thumbnail_path"),
										rs.getString("thumbnail_filename"),
										rs.getString("thumbnail_loadpath"),
										rs.getString("post_name"),
										rs.getInt("gp_people"),
										rs.getInt("post_likes"),
										rs.getInt("gp_price"),
										rs.getInt("gp_drate"),
										rs.getInt("gp_dprice")));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
 		
		return pList;
		
	}
	
	
	

	// insert
	

	// 1. post
	public int insertProductPost(Connection conn, PostBoard pb, Location lo) {
		// insert문 => 처리된 행 수
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("insertProductPost");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pb.getCategoryNo());
			pstmt.setInt(2, pb.getMemNo());
			pstmt.setString(3, lo.getLocal_si());
			pstmt.setString(4, lo.getLocal_gu());
			pstmt.setString(5, lo.getLocal_dong());
			pstmt.setString(6, pb.getMemNickname());
			pstmt.setString(7, pb.getPostName());
			pstmt.setString(8, pb.getPostContent());
			pstmt.setString(9, pb.getCategoryNo());
			pstmt.setString(10, pb.getThumbnailPath());
			pstmt.setString(11, pb.getThumbnailFilename());
			pstmt.setString(12, pb.getThumbnailLoadPath());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	

	// 2. group_purchase
	public int insertProductGroupPurchase(Connection conn, Product pd) {
		// insert문 => 처리된 행 수
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("insertProductGroupPurchase");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pd.getGpDeadline());
			pstmt.setInt(2, pd.getGpMinPeople());
			pstmt.setInt(3, pd.getGpPrice());
			pstmt.setInt(4, pd.getGpDRate());
			pstmt.setInt(5, pd.getGpDPrice());
			pstmt.setString(6, pd.getGpRefund());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	
	/*
		// 3. location
		public int insertProductLocation(Connection conn, Location lo) {
			// insert문 => 처리된 행 수
			int result = 0;
			
			PreparedStatement pstmt = null;
			
			String sql = prop.getProperty("insertProductLocation");
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, lo.getLocal_si());
				pstmt.setString(2, lo.getLocal_gu());
				pstmt.setString(3, lo.getLocal_dong());
				
				result = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(pstmt);
			}
			return result;
		}
	
		*/
		// 4. option
		public int insertProductOption(Connection conn, String[] option) {
			// 여러번의 insert문 
			int result = 0;
			
			PreparedStatement pstmt = null;
			
			String sql = prop.getProperty("insertProductOption");
			
			try {
				
				for(int i=0; i<option.length; i++) {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, option[i]);
				result = pstmt.executeUpdate();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return result;
		}
		
		// 5. account
		public int insertProductAccount(Connection conn, String[] account) {
			// 여러번의 insert문 
			int result = 0;
			
			PreparedStatement pstmt = null;
			
			String sql = prop.getProperty("insertProductAccount");
			
			try {
				
				for(int i=0; i<account.length; i++) {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, account[i]);
				result = pstmt.executeUpdate();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return result;
		}
		
		// 6. photo
		public int insertProductPhoto(Connection conn, ArrayList<Photo> pList) {
			// 여러번의 insert문 
			int result = 0;
			
			PreparedStatement pstmt = null;
			
			String sql = prop.getProperty("insertProductPhoto");
			
			try {
				
				for(Photo pt : pList) {		
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setString(1, pt.getPhotoPath());
					pstmt.setString(2, pt.getPhotoFileName());
					pstmt.setString(3, pt.getPhotoLoadPath());
						
					result = pstmt.executeUpdate();
					
					if(result == 0) {
						return 0;
					}
					
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}finally {
				close(pstmt);
			}
			
			return result;
			
			
		}
		
		public int likeCheck(Connection conn, int bno, int memNo) {
			
			int likeCheck = 0;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = prop.getProperty("likeCheck");
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, memNo);
				pstmt.setInt(2, bno);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					likeCheck = rs.getInt("COUNT");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				close(rs);
				close(pstmt);
			}
			return likeCheck;
		}

		public int insertLike(Connection conn, int bno, int memNo) {
			
			int result = 0;
			PreparedStatement pstmt = null;
			String sql = prop.getProperty("insertLike");
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, memNo);
				pstmt.setInt(2, bno);
				
				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				close(pstmt);
			}
		
			return result;
		}

		public ArrayList<Integer> likeCountSelector(Connection conn, PageInfo pi) {
			
			ArrayList<Integer> likeCount = new ArrayList();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = prop.getProperty("likeCountSelector");
			
			int startNum = (pi.getCurrentPage()-1)*pi.getBoardLimit()+1;
			int endRow = startNum + pi.getBoardLimit() -1;
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, startNum);
				pstmt.setInt(2, endRow);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					
					likeCount.add(rs.getInt("LIKECOUNT"));
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				close(rs);
				close(pstmt);
			}

			return likeCount;
		}

		public int insertPostBoardLike(Connection conn, int bno, int memNo) {
			
			int result = 0;
			PreparedStatement pstmt = null;
			String sql = prop.getProperty("insertPostBoardLike");
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bno);
				
				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				close(pstmt);
			}
					
			return result;
		}

		
		
		
		
		public int reportCheck(Connection conn,int bno, int memNo) {
			
			int repCheck = 0;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = prop.getProperty("reportPreCheck");
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bno);
				pstmt.setInt(2, memNo);
				
			    rs = pstmt.executeQuery();
			    if(rs.next()) {
			    	repCheck = rs.getInt("COUNT(*)");
			    }
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				close(rs);
				close(pstmt);
			}
			
			
			return repCheck;
		}

		
		
		
		
		
		public int reportProduct(Connection conn, int bno) {
			int result = 0;
			
			PreparedStatement pstmt = null;
			String sql = prop.getProperty("reportProduct");
			
			try {
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, bno);

				result = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(pstmt);
			}
			
			return result;
			
		}
		
		
		
		public ArrayList<PostBoardJY> mainCategoryAjax(Connection conn, String cName,
				PageInfo pi) {
			
			ArrayList<PostBoardJY> list = new ArrayList<>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = prop.getProperty("mainCategoryAjax");
			
			int startNum = (pi.getCurrentPage()-1)*pi.getBoardLimit()+1;
			int endRow = startNum + pi.getBoardLimit() -1;
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, startNum);
				pstmt.setInt(2, endRow);
				pstmt.setString(3, "%"+ cName +"%");
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
							
					if(list.isEmpty()) {
						PostBoardJY pb = new PostBoardJY(
								                      pi.getCurrentPage()
								                     ,pi.getListCount()
								                     ,pi.getBoardLimit()
								                     ,pi.getPageLimit()
								                     ,pi.getMaxPage()
								                     ,pi.getStartPage()
								                     ,pi.getEndPage()
								                     ,rs.getInt("POST_NO")		                  
								                     ,rs.getInt("MEM_NO")
								                     ,rs.getString("LOCAL_NO")
								                     ,rs.getString("POST_NAME")
								                     ,rs.getString("CATEGORY_NAME")
								                     ,rs.getString("THUMBNAIL_PATH")
								                     ,rs.getString("THUMBNAIL_FILENAME")
								                     ,rs.getString("THUMBNAIL_LOADPATH")
								                     ,rs.getInt("POST_VIEWS")
								                     ,rs.getInt("POST_LIKES")
								                     ,rs.getInt("PROD_PRICE")
								                     ,rs.getInt("LIKECOUNT"));
						list.add(pb);
			
					} else {
				
						PostBoardJY pb = new PostBoardJY(rs.getInt("POST_NO")		                  
							                        ,rs.getInt("MEM_NO")
							                        ,rs.getString("LOCAL_NO")
							                        ,rs.getString("POST_NAME")
							                        ,rs.getString("CATEGORY_NAME")
							                        ,rs.getString("THUMBNAIL_PATH")
							                        ,rs.getString("THUMBNAIL_FILENAME")
							                        ,rs.getString("THUMBNAIL_LOADPATH")
							                        ,rs.getInt("POST_VIEWS")
							                        ,rs.getInt("POST_LIKES")
							                        ,rs.getInt("PROD_PRICE")
							                        ,rs.getInt("LIKECOUNT"));
						list.add(pb);		
					}
		
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				close(rs);
				close(pstmt);
			}	
			
			return list;
		}

	
		
		
		
		
		
		
		
		
		
		
		
		public ArrayList<Product> selectMainProductList1(Connection conn, PageInfo pi){
			// select문 => 여러행 조회
			ArrayList<Product> pList = new ArrayList<>();
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String sql = prop.getProperty("selectMainProductListSort1");
			
			try {
				pstmt = conn.prepareStatement(sql);
				int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
				int endRow = startRow + pi.getBoardLimit() - 1;
				
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
				
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					pList.add(new Product(rs.getInt("post_no"),
											rs.getString("thumbnail_path"),
											rs.getString("thumbnail_filename"),
											rs.getString("thumbnail_loadpath"),
											rs.getString("post_name"),
											rs.getInt("gp_people"),
											rs.getInt("post_likes"),
											rs.getInt("gp_price"),
											rs.getInt("gp_drate"),
											rs.getInt("gp_dprice")));
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}
	 		
			return pList;
			
		}
		
		
		public ArrayList<Product> selectMainProductList2(Connection conn, PageInfo pi){
			// select문 => 여러행 조회
			ArrayList<Product> pList = new ArrayList<>();
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String sql = prop.getProperty("selectMainProductListSort2");
			
			try {
				pstmt = conn.prepareStatement(sql);
				int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
				int endRow = startRow + pi.getBoardLimit() - 1;
				
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
				
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					pList.add(new Product(rs.getInt("post_no"),
											rs.getString("thumbnail_path"),
											rs.getString("thumbnail_filename"),
											rs.getString("thumbnail_loadpath"),
											rs.getString("post_name"),
											rs.getInt("gp_people"),
											rs.getInt("post_likes"),
											rs.getInt("gp_price"),
											rs.getInt("gp_drate"),
											rs.getInt("gp_dprice")));
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}
	 		
			return pList;
			
		}
		
		
		public ArrayList<Product> selectMainProductList3(Connection conn, PageInfo pi){
			// select문 => 여러행 조회
			ArrayList<Product> pList = new ArrayList<>();
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String sql = prop.getProperty("selectMainProductListSort3");
			
			try {
				pstmt = conn.prepareStatement(sql);
				int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
				int endRow = startRow + pi.getBoardLimit() - 1;
				
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
				
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					pList.add(new Product(rs.getInt("post_no"),
											rs.getString("thumbnail_path"),
											rs.getString("thumbnail_filename"),
											rs.getString("thumbnail_loadpath"),
											rs.getString("post_name"),
											rs.getInt("gp_people"),
											rs.getInt("post_likes"),
											rs.getInt("gp_price"),
											rs.getInt("gp_drate"),
											rs.getInt("gp_dprice")));
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}
	 		
			return pList;
			
		}
		
		
		public ArrayList<Product> selectMainProductList4(Connection conn, PageInfo pi){
			// select문 => 여러행 조회
			ArrayList<Product> pList = new ArrayList<>();
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String sql = prop.getProperty("selectMainProductListSort4");
			
			try {
				pstmt = conn.prepareStatement(sql);
				int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
				int endRow = startRow + pi.getBoardLimit() - 1;
				
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
				
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					pList.add(new Product(rs.getInt("post_no"),
											rs.getString("thumbnail_path"),
											rs.getString("thumbnail_filename"),
											rs.getString("thumbnail_loadpath"),
											rs.getString("post_name"),
											rs.getInt("gp_people"),
											rs.getInt("post_likes"),
											rs.getInt("gp_price"),
											rs.getInt("gp_drate"),
											rs.getInt("gp_dprice")));
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}
	 		
			return pList;
			
		}
		
		public ArrayList<Product> sortCategory(Connection conn, PageInfo pi){
			// select문 => 여러행 조회
			ArrayList<Product> pList = new ArrayList<>();
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String sql = prop.getProperty("sortCategory20");
			
			try {
				pstmt = conn.prepareStatement(sql);
				int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
				int endRow = startRow + pi.getBoardLimit() - 1;
				
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
				
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					pList.add(new Product(rs.getInt("post_no"),
											rs.getString("thumbnail_path"),
											rs.getString("thumbnail_filename"),
											rs.getString("thumbnail_loadpath"),
											rs.getString("POST_NAME"),
											rs.getInt("GP_PEOPLE"),
											rs.getInt("POST_LIKES"),
											rs.getInt("GP_PRICE"),
											rs.getInt("GP_DRATE"),
											rs.getInt("GP_DPRICE")));
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}
	 		
			return pList;
			
		}
		
		public ArrayList<Product> sortCategory30(Connection conn, PageInfo pi){
			// select문 => 여러행 조회
			ArrayList<Product> pList = new ArrayList<>();
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String sql = prop.getProperty("sortCategory30");
			
			try {
				pstmt = conn.prepareStatement(sql);
				int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
				int endRow = startRow + pi.getBoardLimit() - 1;
				
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
				
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					pList.add(new Product(rs.getInt("post_no"),
											rs.getString("thumbnail_path"),
											rs.getString("thumbnail_filename"),
											rs.getString("thumbnail_loadpath"),
											rs.getString("POST_NAME"),
											rs.getInt("GP_PEOPLE"),
											rs.getInt("POST_LIKES"),
											rs.getInt("GP_PRICE"),
											rs.getInt("GP_DRATE"),
											rs.getInt("GP_DPRICE")));
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}
	 		
			return pList;
			
		}
		
		public ArrayList<Product> sortCategory40(Connection conn, PageInfo pi){
			// select문 => 여러행 조회
			ArrayList<Product> pList = new ArrayList<>();
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String sql = prop.getProperty("sortCategory40");
			
			try {
				pstmt = conn.prepareStatement(sql);
				int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
				int endRow = startRow + pi.getBoardLimit() - 1;
				
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
				
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					pList.add(new Product(rs.getInt("post_no"),
											rs.getString("thumbnail_path"),
											rs.getString("thumbnail_filename"),
											rs.getString("thumbnail_loadpath"),
											rs.getString("POST_NAME"),
											rs.getInt("GP_PEOPLE"),
											rs.getInt("POST_LIKES"),
											rs.getInt("GP_PRICE"),
											rs.getInt("GP_DRATE"),
											rs.getInt("GP_DPRICE")));
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}
	 		
			return pList;
			
		}
		
		public ArrayList<Product> sortCategory50(Connection conn, PageInfo pi){
			// select문 => 여러행 조회
			ArrayList<Product> pList = new ArrayList<>();
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String sql = prop.getProperty("sortCategory50");
			
			try {
				pstmt = conn.prepareStatement(sql);
				int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
				int endRow = startRow + pi.getBoardLimit() - 1;
				
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
				
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					pList.add(new Product(rs.getInt("post_no"),
											rs.getString("thumbnail_path"),
											rs.getString("thumbnail_filename"),
											rs.getString("thumbnail_loadpath"),
											rs.getString("POST_NAME"),
											rs.getInt("GP_PEOPLE"),
											rs.getInt("POST_LIKES"),
											rs.getInt("GP_PRICE"),
											rs.getInt("GP_DRATE"),
											rs.getInt("GP_DPRICE")));
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}
	 		
			return pList;
			
		}
		
		public ArrayList<Product> sortCategory60(Connection conn, PageInfo pi){
			// select문 => 여러행 조회
			ArrayList<Product> pList = new ArrayList<>();
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String sql = prop.getProperty("sortCategory60");
			
			try {
				pstmt = conn.prepareStatement(sql);
				int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
				int endRow = startRow + pi.getBoardLimit() - 1;
				
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
				
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					pList.add(new Product(rs.getInt("post_no"),
											rs.getString("thumbnail_path"),
											rs.getString("thumbnail_filename"),
											rs.getString("thumbnail_loadpath"),
											rs.getString("POST_NAME"),
											rs.getInt("GP_PEOPLE"),
											rs.getInt("POST_LIKES"),
											rs.getInt("GP_PRICE"),
											rs.getInt("GP_DRATE"),
											rs.getInt("GP_DPRICE")));
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}
	 		
			return pList;
			
		}
		
		public ArrayList<Product> sortCategory70(Connection conn, PageInfo pi){
			// select문 => 여러행 조회
			ArrayList<Product> pList = new ArrayList<>();
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String sql = prop.getProperty("sortCategory70");
			
			try {
				pstmt = conn.prepareStatement(sql);
				int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
				int endRow = startRow + pi.getBoardLimit() - 1;
				
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
				
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					pList.add(new Product(rs.getInt("post_no"),
											rs.getString("thumbnail_path"),
											rs.getString("thumbnail_filename"),
											rs.getString("thumbnail_loadpath"),
											rs.getString("POST_NAME"),
											rs.getInt("GP_PEOPLE"),
											rs.getInt("POST_LIKES"),
											rs.getInt("GP_PRICE"),
											rs.getInt("GP_DRATE"),
											rs.getInt("GP_DPRICE")));
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}
	 		
			return pList;
			
		}
		
		public ArrayList<Product> sortCategory80(Connection conn, PageInfo pi){
			// select문 => 여러행 조회
			ArrayList<Product> pList = new ArrayList<>();
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String sql = prop.getProperty("sortCategory80");
			
			try {
				pstmt = conn.prepareStatement(sql);
				int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
				int endRow = startRow + pi.getBoardLimit() - 1;
				
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
				
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					pList.add(new Product(rs.getInt("post_no"),
											rs.getString("thumbnail_path"),
											rs.getString("thumbnail_filename"),
											rs.getString("thumbnail_loadpath"),
											rs.getString("POST_NAME"),
											rs.getInt("GP_PEOPLE"),
											rs.getInt("POST_LIKES"),
											rs.getInt("GP_PRICE"),
											rs.getInt("GP_DRATE"),
											rs.getInt("GP_DPRICE")));
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}
	 		
			return pList;
			
		}
		
		public ArrayList<Product> sortCategory90(Connection conn, PageInfo pi){
			// select문 => 여러행 조회
			ArrayList<Product> pList = new ArrayList<>();
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String sql = prop.getProperty("sortCategory90");
			
			try {
				pstmt = conn.prepareStatement(sql);
				int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
				int endRow = startRow + pi.getBoardLimit() - 1;
				
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
				
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					pList.add(new Product(rs.getInt("post_no"),
											rs.getString("thumbnail_path"),
											rs.getString("thumbnail_filename"),
											rs.getString("thumbnail_loadpath"),
											rs.getString("POST_NAME"),
											rs.getInt("GP_PEOPLE"),
											rs.getInt("POST_LIKES"),
											rs.getInt("GP_PRICE"),
											rs.getInt("GP_DRATE"),
											rs.getInt("GP_DPRICE")));
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}
	 		
			return pList;
			
		}
		
		public ArrayList<Product> sortCategory100(Connection conn, PageInfo pi){
			// select문 => 여러행 조회
			ArrayList<Product> pList = new ArrayList<>();
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String sql = prop.getProperty("sortCategory100");
			
			try {
				pstmt = conn.prepareStatement(sql);
				int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
				int endRow = startRow + pi.getBoardLimit() - 1;
				
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
				
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					pList.add(new Product(rs.getInt("post_no"),
											rs.getString("thumbnail_path"),
											rs.getString("thumbnail_filename"),
											rs.getString("thumbnail_loadpath"),
											rs.getString("POST_NAME"),
											rs.getInt("GP_PEOPLE"),
											rs.getInt("POST_LIKES"),
											rs.getInt("GP_PRICE"),
											rs.getInt("GP_DRATE"),
											rs.getInt("GP_DPRICE")));
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}
	 		
			return pList;
			
		}
		
		public ArrayList<Product> sortCategory110(Connection conn, PageInfo pi){
			// select문 => 여러행 조회
			ArrayList<Product> pList = new ArrayList<>();
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String sql = prop.getProperty("sortCategory110");
			
			try {
				pstmt = conn.prepareStatement(sql);
				int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
				int endRow = startRow + pi.getBoardLimit() - 1;
				
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
				
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					pList.add(new Product(rs.getInt("post_no"),
											rs.getString("thumbnail_path"),
											rs.getString("thumbnail_filename"),
											rs.getString("thumbnail_loadpath"),
											rs.getString("POST_NAME"),
											rs.getInt("GP_PEOPLE"),
											rs.getInt("POST_LIKES"),
											rs.getInt("GP_PRICE"),
											rs.getInt("GP_DRATE"),
											rs.getInt("GP_DPRICE")));
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}
	 		
			return pList;
			
		}
		
		public ArrayList<Product> sortCategory120(Connection conn, PageInfo pi){
			// select문 => 여러행 조회
			ArrayList<Product> pList = new ArrayList<>();
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String sql = prop.getProperty("sortCategory120");
			
			try {
				pstmt = conn.prepareStatement(sql);
				int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
				int endRow = startRow + pi.getBoardLimit() - 1;
				
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
				
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					pList.add(new Product(rs.getInt("post_no"),
											rs.getString("thumbnail_path"),
											rs.getString("thumbnail_filename"),
											rs.getString("thumbnail_loadpath"),
											rs.getString("POST_NAME"),
											rs.getInt("GP_PEOPLE"),
											rs.getInt("POST_LIKES"),
											rs.getInt("GP_PRICE"),
											rs.getInt("GP_DRATE"),
											rs.getInt("GP_DPRICE")));
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}
	 		
			return pList;
			
		}
		
		public ArrayList<Product> sortCategory130(Connection conn, PageInfo pi){
			// select문 => 여러행 조회
			ArrayList<Product> pList = new ArrayList<>();
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String sql = prop.getProperty("sortCategory130");
			
			try {
				pstmt = conn.prepareStatement(sql);
				int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
				int endRow = startRow + pi.getBoardLimit() - 1;
				
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
				
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					pList.add(new Product(rs.getInt("post_no"),
											rs.getString("thumbnail_path"),
											rs.getString("thumbnail_filename"),
											rs.getString("thumbnail_loadpath"),
											rs.getString("POST_NAME"),
											rs.getInt("GP_PEOPLE"),
											rs.getInt("POST_LIKES"),
											rs.getInt("GP_PRICE"),
											rs.getInt("GP_DRATE"),
											rs.getInt("GP_DPRICE")));
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}
	 		
			return pList;
			
		}
		
		public ArrayList<Product> sortCategory140(Connection conn, PageInfo pi){
			// select문 => 여러행 조회
			ArrayList<Product> pList = new ArrayList<>();
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String sql = prop.getProperty("sortCategory140");
			
			try {
				pstmt = conn.prepareStatement(sql);
				int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
				int endRow = startRow + pi.getBoardLimit() - 1;
				
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
				
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					pList.add(new Product(rs.getInt("post_no"),
											rs.getString("thumbnail_path"),
											rs.getString("thumbnail_filename"),
											rs.getString("thumbnail_loadpath"),
											rs.getString("POST_NAME"),
											rs.getInt("GP_PEOPLE"),
											rs.getInt("POST_LIKES"),
											rs.getInt("GP_PRICE"),
											rs.getInt("GP_DRATE"),
											rs.getInt("GP_DPRICE")));
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}
	 		
			return pList;
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		public ArrayList<Product> searchKeyword(Connection conn, PageInfo pi, String keyword){
			
			ArrayList<Product> pList = new ArrayList<>();
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = prop.getProperty("searchKeyword");
			
			try {
				pstmt = conn.prepareStatement(sql);
				int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
				int endRow = startRow + pi.getBoardLimit() - 1;
				
				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, endRow);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					pList.add(new Product(rs.getInt("post_no"),
											rs.getString("thumbnail_path"),
											rs.getString("thumbnail_filename"),
											rs.getString("thumbnail_loadpath"),
											rs.getString("POST_NAME"),
											rs.getInt("GP_PEOPLE"),
											rs.getInt("POST_LIKES"),
											rs.getInt("GP_PRICE"),
											rs.getInt("GP_DRATE"),
											rs.getInt("GP_DPRICE")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}
			return pList;
		}
		
		
		
		
		
		
		
		
		public ArrayList<Product> selectRegionProduct(Connection conn, PageInfo pi, String localGu, String localDong){
			// select문 => 여러행 조회
			ArrayList<Product> pList = new ArrayList<>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String sql = prop.getProperty("selectRegionProduct");
			
			try {
				pstmt = conn.prepareStatement(sql);
				int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
				int endRow = startRow + pi.getBoardLimit() - 1;
				
				pstmt.setString(1, localGu);
				pstmt.setString(2, localDong);
				pstmt.setInt(3, startRow);
				pstmt.setInt(4, endRow);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					pList.add(new Product(rs.getInt("post_no"),
											rs.getString("thumbnail_path"),
											rs.getString("thumbnail_filename"),
											rs.getString("thumbnail_loadpath"),
											rs.getString("post_name"),
											rs.getInt("gp_people"),
											rs.getInt("post_likes"),
											rs.getInt("gp_price"),
											rs.getInt("gp_drate"),
											rs.getInt("gp_dprice")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}
			return pList;
		}
		
		
		
		
		
		
		
		public ArrayList<PostBoardJY> mainSearchAjax(Connection conn,PageInfo pi, String keyword) {
			ArrayList<PostBoardJY> bList = new ArrayList();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = prop.getProperty("mainSearchAjax");
			

			int startNum = (pi.getCurrentPage()-1)*pi.getBoardLimit()+1;
			int endRow = startNum + pi.getBoardLimit() -1;

			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, startNum);
				pstmt.setInt(2, endRow);
				pstmt.setString(3, "%"+ keyword+"%");
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					
					if(bList.isEmpty()) {
						PostBoardJY pb = new PostBoardJY( pi.getCurrentPage()
								                     ,pi.getListCount()
								                     ,pi.getBoardLimit()
								                     ,pi.getPageLimit()
								                     ,pi.getMaxPage()
								                     ,pi.getStartPage()
								                     ,pi.getEndPage()
													 ,rs.getInt("POST_NO")		                  
								                     ,rs.getInt("MEM_NO")
								                     ,rs.getString("LOCAL_NO")
								                     ,rs.getString("POST_NAME")
								                     ,rs.getString("CATEGORY_NAME")
								                     ,rs.getString("THUMBNAIL_PATH")
								                     ,rs.getString("THUMBNAIL_FILENAME")
								                     ,rs.getString("THUMBNAIL_LOADPATH")
								                     ,rs.getInt("POST_VIEWS")
								                     ,rs.getInt("POST_LIKES")
								                     ,rs.getInt("PROD_PRICE")
								                     ,rs.getInt("LIKECOUNT"));
						bList.add(pb);
					} else {
						
						PostBoardJY pb = new PostBoardJY(rs.getInt("POST_NO")		                  
							                        ,rs.getInt("MEM_NO")
							                        ,rs.getString("LOCAL_NO")
							                        ,rs.getString("POST_NAME")
							                        ,rs.getString("CATEGORY_NAME")
							                        ,rs.getString("THUMBNAIL_PATH")
							                        ,rs.getString("THUMBNAIL_FILENAME")
							                        ,rs.getString("THUMBNAIL_LOADPATH")
							                        ,rs.getInt("POST_VIEWS")
							                        ,rs.getInt("POST_LIKES")
							                        ,rs.getInt("PROD_PRICE")
							                        ,rs.getInt("LIKECOUNT"));
						bList.add(pb);		
					}
		
				}
				
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				close(rs);
				close(pstmt);
			}
			
			return bList;
		}

		
		public ArrayList<PostBoardJY> boardTop4Selector(Connection conn, PageInfo pi) {
			
			ArrayList<PostBoardJY> pList = new ArrayList();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = prop.getProperty("boardTop4Selector");
			
			try {
				pstmt = conn.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					PostBoardJY pb = new PostBoardJY(rs.getInt("POST_NO")
												,rs.getInt("MEM_NO")
												,rs.getString("LOCATION")
												,rs.getString("POST_NAME")
												,rs.getString("CATEGORY_NAME")
												,rs.getString("THUMBNAIL_PATH")
												,rs.getString("THUMBNAIL_FILENAME")
												,rs.getString("THUMBNAIL_LOADPATH")
												,rs.getInt("POST_VIEWS")
												,rs.getInt("POST_LIKES")
												,rs.getInt("PROD_PRICE"));
					pList.add(pb);				
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				close(rs);
				close(pstmt);
			}		
			
			return pList;
		}
		
		

	
}
