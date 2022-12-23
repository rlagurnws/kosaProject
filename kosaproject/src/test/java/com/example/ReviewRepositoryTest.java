package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.repository.ReviewRepository;
import com.my.vo.Member;
import com.my.vo.Review;

@SpringBootTest
class ReviewRepositoryTest {
	@Autowired
	ReviewRepository repository;
	@Test
	void test() {
		System.out.println("test");
	}
	@Test
	void selectBystNumTest() throws FindException {
		List<Review> list= repository.selectBystNum(1);
		//System.out.println(list);
		int expectedSize = 4;
		assertEquals(expectedSize, list.size());
		String firstExpectedDesc = "우웩";
		assertEquals(firstExpectedDesc, list.get(0).getReviewDes());
	}
	
	
	//글번호 20번 삽입 
	@Test
	void insertTest() throws AddException {
		Review rv = new Review();
		rv.setMemId("id1");
		rv.setReviewDes("오늘부터 단골하렵니다 다들 오지마세요 저만 갈겁니다ㅋㅋ");
		rv.setReviewNo(20);
		rv.setReviewStar(5);
		rv.setReviewState(0);
		rv.setStNum(2);
		repository.insert(rv);
	}
		
	//글번호 24번에
	@Test
	void updateTest() throws ModifyException, FindException{
		int reviewNo=24;
		int stNum=2;
		String expectedDes = "ㅋㅋ";
		Review rv = new Review();
		rv.setReviewNo(reviewNo);
		rv.setMemId("id1");
		rv.setReviewDes(expectedDes);
		repository.update(rv);
		
		//Review rv1 = repository.selectByReviewNo(reviewNo);
		//assertEquals(expectedDes, rv1.getReviewDes());
		
	}

	
	
		
	}

	
	
	