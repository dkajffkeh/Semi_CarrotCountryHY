package com.javachip.carrotcountry.coBuying.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javachip.carrotcountry.coBuying.model.service.ProductService;
import com.javachip.carrotcountry.coBuying.model.service.QnAService;
import com.javachip.carrotcountry.coBuying.model.vo.PageInfo;
import com.javachip.carrotcountry.coBuying.model.vo.Product;
import com.javachip.carrotcountry.coBuying.model.vo.QnA;

/**
 * Servlet implementation class MainPageController
 */
@WebServlet("/mainpage.co.jy")
public class MainPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainPageController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		// ---------------------------- 페이징 처리 -------------------------------------
		int listCount;					// 현재 일반게시판 총 갯수
		int currentPage;				// 사용자가 요청한 페이지 (즉, 현재 페이지)
		int pageLimit;					// 한 페이지 하단에 보여질 페이지 최대갯수
		int boardLimit;					// 한 페이지 내에 보여질 게시글 최대갯수
		
		int maxPage;					// 전체 페이지들 중에서 가장 마지막 페이지 수
		int startPage;					// 현재 사용자가 요청한 페이지에 하단에 보여질 페이징바의 시작수
		int endPage;					// 현재 사용자가 요청한 페이지에 하단에 보여질 페이징바의 끝수
		
		
		// * listCount : 현재 일반 게시판 총 갯수
		listCount = new ProductService().selectProductListCount();
		
		// * currentPage : 사용자가 요청한 페이지 (즉, 현재 페이지)
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
		
		// * pageLimit : 한 페이지 하단에 보여질 페이지 최대갯수 (몇 개 단위씩 보여지게 할건지)
		pageLimit = 10;
		
		// * boardLimit : 한 페이지 내에 보여질 게시글 최대갯수 (몇 개 단위씩 보여지게 할건지)
		boardLimit = 10;
		
		// * maxPage : 제일 마지막 페이지 수
		maxPage = (int)Math.ceil((double)listCount/boardLimit);

		startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
	
		endPage = startPage + pageLimit - 1;
		
		// 만약 maxPage가 고작 13까지밖에 안된다면? endPage를 다시 13으로 해줘야됨
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		// 페이징바를 만들때 필요한 정보들이 담겨있는 pageInfo 객체
		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage);
		
		
		// 메인에 띄울 공동구매 상품리스트 조회
		ArrayList<Product> pList = new ProductService().selectMainProductList(pi);
		
		
		request.setAttribute("pi", pi);
		request.setAttribute("pList", pList);
		
		request.getRequestDispatcher("views/coBuying/coBuyingMainPage.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
