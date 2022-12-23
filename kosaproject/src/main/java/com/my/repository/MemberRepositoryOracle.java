package com.my.repository;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.vo.Member;

@Repository("memberRepository")
public class MemberRepositoryOracle implements MemberRepository{
	
	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Override
	public void insert(Member m) throws AddException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			session.insert("com.my.mybatis.MemberMapper.insert", m);
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
	public Member selectById(String id) throws FindException {
		Member M= new Member();
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			M = session.selectOne("com.my.mybatis.MemberMapper.selectById", id);
			if(M == null) {
				throw new Exception();
			}
			return M;
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
	public void modify(Member m) throws ModifyException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			session.update("com.my.mybatis.MemberMapper.modify",m);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}

	public void delete(String id) throws RemoveException{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			session.update("com.my.mybatis.MemberMapper.delete",id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}
	
	public Member findId(Member m) throws FindException{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Member findM = session.selectOne("com.my.mybatis.MemberMapper.findId",m);
			return findM;
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}
	public Member findPwd(Member m) throws FindException{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Member findM = session.selectOne("com.my.mybatis.MemberMapper.findPwd",m);
			return findM;
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}
}
