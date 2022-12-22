package com.my.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Override
//	public Member memberInfo(String id) throws FindException {
//		Member M= new Member();
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//			conn = MyConnection.getConnection();
//			String selectIDSQL = "SELECT * "
//					+ 			 "FROM MEMBER "
//					+ 			 "WHERE MEM_ID = ?";
//			pstmt = conn.prepareStatement(selectIDSQL);
//			pstmt.setString(1, id);
//			rs = pstmt.executeQuery();
//			rs.next();
//			System.out.println(id);
//			if(id.equals(rs.getString(1))) {
//				M.setMEM_id(id);
//				M.setMEM_name(rs.getString(2));
//				M.setMEM_pwd(rs.getString(3));
//				M.setMEM_birth(rs.getString(4));
//				M.setMEM_phone(rs.getString(5));
//				M.setMEM_sex(rs.getString(6));
//				M.setMEM_nick(rs.getString(7));
//				M.setMEM_power(rs.getInt(8));
//				M.setMEM_address(rs.getString(9));
//				M.setMEM_state(rs.getInt(10));
//				M.setMEM_pic(rs.getString(11));
//				return M;
//			}else {
//				return null;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		}finally {
//			MyConnection.close(rs, pstmt, conn);
//		}
//	}
//	
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

	@Override
	public int selectCount() throws FindException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			return session.selectOne("com.my.mybatis.MemberMapper.selectCount");
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			if(session !=null) {
				session.close();
			}
		}
	}

	@Override
	public List<Member> selectAll() throws FindException {
		return null;
	}

	@Override
	public List<Member> selectAll(int currentPage, int cntPerPage) throws FindException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			int startRow = ((currentPage-1)*cntPerPage)+1;
			int endRow = currentPage*cntPerPage;
			Map<String, Object> map = new HashMap<>();
			map.put("startRow",startRow);
			map.put("endRow", endRow);
			return session.selectList("com.my.mybatis.MemberMapper.selectAll",map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			if(session !=null) {
				session.close();
			}
		}
	}
}
