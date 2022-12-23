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
	 * @author 장나영
	 * @param rv 리뷰
	 * @throws AddException
	 */
	int insert(Review rv) throws AddException;
	
	
	/**
	 * 가게번호에 맞는 리뷰를 검색한다. 
	 * @author 장나영
	 * @param stNum
	 * @return
	 * @throws FindException
	 */

	List<Review> selectBystNum(int stNum) throws FindException;
		
	
	/**
	 * 리뷰내용수정
	 * @author 장나영
	 * @param rv
	 * @throws ModifyException
	 */
	void update(Review rv) throws ModifyException;


	/**
	 * 리뷰 삭제 
	 * @author 장나영
	 * @param memId
	 * @throws RemoveException 
	 */
	void delete(String memId) throws RemoveException;

	
	/**
	 * 가게번호별 리뷰를 최신순으로 보여준다
	 * @author 장나영
	 * @param stNum
	 * @throws FindException
	 */
	List<Review> selectBystNumNew(int stNum) throws FindException;
	
	/**
	 * 가게번호별 리뷰를 별점순으로 보여준다 
	 * @author 장나영
	 * @param stNum
	 * @throws FindException
	 */
	List<Review> selectBystNumStar(int stNum) throws FindException;


	void delMem(String id) throws ModifyException;
	



	
	
}
