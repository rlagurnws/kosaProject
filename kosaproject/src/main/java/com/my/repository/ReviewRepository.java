package com.my.repository;

import java.util.List;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.vo.Review;

public interface ReviewRepository {
	/**
	 * 글을 추가한다
	 * 추가된 글번호 조회하여 반환한다
	 * @param rv 리뷰
	 * @throws AddException
	 */
	int insert(Review rv) throws AddException;
	
	/**
	 * 모든 글을 검색한다 
	 * @return
	 * @throws FindException
	 */
	List<Review> selectAll() throws FindException;
	
	/**
	 * 페이지에 해당하는 글을 검색한다
	 * @param currentPage
	 * @param cntPerPage
	 * @return
	 * @throws FindException
	 */
	List<Review> selectAll(int currentPage, int cntPerPage) throws FindException;
	
	/**
	 * 총 게시글수를 반환한다
	 * @return 게시글개수
	 * @throws FindException
	 */
	int selectCount() throws FindException;
	
	
	/**
	 * 글제목,글내용수정
	 * @param rv
	 * @throws ModifyException
	 */
	void update(Review rv) throws ModifyException;
	

	
	
}
