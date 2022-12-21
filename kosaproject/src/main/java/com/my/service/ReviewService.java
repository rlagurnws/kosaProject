package com.my.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.repository.ReviewRepository;
import com.my.vo.Review;


@Service("ReviewService")
public class ReviewService {
	@Autowired
		private ReviewRepository repository;
		
		public ReviewService() {}
		
		public ReviewService(ReviewRepository repository) {
			this.repository = repository;
		}
		
		/**
		 * 전체 게시글 목록을 반환한다
		 * @return
		 * @throws FindException
		 */
		public List<Review> findAll() throws FindException{
			return repository.selectAll();
		}
		
		/**
		 * 페이지에 해당 게시글 목록을 반환한다
		 * @param currentPage 검색할 페이지
		 * @param cntPerPage  페이지별 보여줄 목록수
		 * @return
		 * @throws FindException
		 */
		public List<Review> findAll(int currentPage, int cntPerPage) throws FindException{
			return repository.selectAll(currentPage, cntPerPage);
		}
		
		/**
		 * 게시글 수정한다 게시글제목, 게시글내용을 변경한다
		 * @param rv
		 * @throws ModifyException
		 */
		public void modify(Review rv) throws ModifyException {
			repository.update(rv);
		}
		
		
		
	
	
	
	
}
