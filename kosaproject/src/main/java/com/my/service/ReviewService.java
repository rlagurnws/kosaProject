package com.my.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
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
		 * 리뷰를 수정한다.
		 * @param rv
		 * @throws ModifyException
		 */
		public void modify(Review rv) throws ModifyException {
			
			repository.update(rv);
		}
		
		/**
		 * 회원에 관한 리뷰권한을 0으로 바꾼다
		 * @param memId
		 * @throws RemoveException
		 */
		public void delete(String memId) throws RemoveException {
			repository.delete(memId);
		}
		
		
		
		
	
	
	
	
}
