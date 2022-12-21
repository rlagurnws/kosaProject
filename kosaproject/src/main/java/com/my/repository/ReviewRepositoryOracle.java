package com.my.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.vo.Review;


@Repository("ReviewRepository")
public class ReviewRepositoryOracle implements ReviewRepository{

	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	@Override
	public int insert(Review rv) throws AddException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
	
			session.insert("com.my.mybatis.ReviewMapper.insert",rv);
			return rv.getreviewNo();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
			
		}
	}

	@Override
	public List<Review> selectAll() throws FindException {
		List<Review> list = new ArrayList<>();
		SqlSession session = null;
		try {
		session = sqlSessionFactory.openSession();
		list = session.selectList("com.my.mybatis.ReviewMapper.selectAll");
		return list;
	} catch (Exception e) {
		e.printStackTrace();
		throw new FindException(e.getMessage());
	} finally {
		if(session != null) {
		session.close();
		}
	}
	}

	@Override
	public List<Review> selectAll(int currentPage, int cntPerPage) throws FindException {
		List<Review> list = new ArrayList<>();
		SqlSession session = null;
		try {
		session = sqlSessionFactory.openSession();
		list = session.selectList("com.my.mybatis.ReviewMapper.selectAll");
		return list;
	} catch (Exception e) {
		e.printStackTrace();
		throw new FindException(e.getMessage());
	} finally {
		if(session != null) {
		session.close();
		}
	}	
	}

	@Override
	public int selectCount() throws FindException {
		String selectCountSQL = "SELECT COUNT(*) FROM Review";
		SqlSession session = null;
		
		try {
			session = sqlSessionFactory.openSession();
			return session.selectOne("com.my.mybatis.ReviewMapper.selectCount");
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}



	@Override
	public void update(Review rv) throws ModifyException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			int rowcnt = session.update("com.my.mybatis.ReviewMapper.update",rv);
			if(rowcnt == 0) {
				throw new ModifyException("글번호가 없거나 작성자가 다른경우 수정할 수 없습니다");
			}
		
		}catch(Exception e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
		
	}
	
	

	
}
