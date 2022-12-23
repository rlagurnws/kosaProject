//package com.example;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.my.exception.AddException;
//import com.my.exception.FindException;
//import com.my.exception.ModifyException;
//import com.my.exception.RemoveException;
//import com.my.repository.ReviewRepository;
//import com.my.vo.Member;
//import com.my.vo.Review;
//
//@SpringBootTest
//class ReviewRepositoryTest {
//	@Autowired
//	ReviewRepository repository;
//	@Test
//	void test() {
//		System.out.println("test");
//	}
//	@Test
//	void selectBystNumTest() throws FindException {
//		List<Review> list= repository.selectBystNum(1);
//		//System.out.println(list);
//		int expectedSize = 4;
//		assertEquals(expectedSize, list.size());
//		String firstExpectedDesc = "우웩";
//		assertEquals(firstExpectedDesc, list.get(0).getreviewDes());
//		
//	}
//	
//	
//	//글번호 20번 삽입 
//	@Test
//	void insertTest() throws AddException {
//		Review rv = new Review();
//		rv.setmemId("id1");
//		rv.setreviewDes("오늘부터 단골하렵니다 다들 오지마세요 저만 갈겁니다ㅋㅋ");
//		rv.setreviewNo(20);
//		rv.setreviewStar(5);
//		rv.setreviewState(0);
//		rv.setstNum(2);
//		repository.insert(rv);
//	}
//	
//	
//	//글번호 24번, 가게번호 2번, 아이디 id1 내용 변경하기 
//	@Test
//	void updateTest() throws ModifyException, FindException{
//		int reviewNo=24;
//		int stNum=2;
//		String expectedDes = "ㅋㅋ";
//		Review rv = new Review();
//		rv.setreviewNo(24);
//		rv.setmemId("id1");
//		rv.setreviewDes(expectedDes);
//		repository.update(rv);
//
//		assertEquals(expectedDes, rv.getreviewDes());
//		
//	}
//	
//	
//	//가게번호 2번 리뷰 삭제 
//	@Test
//	void deleteTest() throws RemoveException{
//		
//		
//	}
//
//	
//	
//	
//}
