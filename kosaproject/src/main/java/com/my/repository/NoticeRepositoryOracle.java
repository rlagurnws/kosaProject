package com.my.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.vo.Notice;

@Repository("noticeRepository")
public class NoticeRepositoryOracle implements NoticeRepository {
	
	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	@Override
	public void insert(Notice noti) throws AddException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			session.insert("com.my.mybatis.NoticeMapper.insert",noti);
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
	public List<Notice> selectAll() throws FindException {
		return null;
	}
	
	public List<Notice> selectAll(int currentPage, int cntPerPage) throws FindException{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			int startRow = ((currentPage-1)*cntPerPage)+1;
			int endRow = currentPage*cntPerPage;
			Map<String, Object> map = new HashMap<>();
			map.put("startRow",startRow);
			map.put("endRow", endRow);
			return session.selectList("com.my.mybatis.NoticeMapper.selectAll",map);
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
	public int selectCount() throws FindException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			return session.selectOne("com.my.mybatis.NoticeMapper.selectCount");
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			if(session !=null) {
				session.close();
			}
		}
	}
	
	public Notice selectByNo(int no) throws FindException{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			return session.selectOne("com.my.mybatis.NoticeMapper.selectByNo", no);
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
	public int findNext(int no) throws FindException {
		SqlSession session = null;
		List<Notice> list = new ArrayList<>();
		int i = 0;
		try {
			session = sqlSessionFactory.openSession();
			list = session.selectList("com.my.mybatis.NoticeMapper.findAll");
			for(Notice n : list) {
				if(n.getNotiNo() == no) {
					break;
				}else {
					i++;
				}
			}
			i+=2;
			return list.get(i).getNotiNo();
		}catch(NullPointerException e) {
			return 0;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			if(session !=null) {
				session.close();
			}
		}
	}

	@Override
	public int findPre(int no) throws FindException {
		SqlSession session = null;
		List<Notice> list = new ArrayList<>();
		int i = 0;
		try {
			session = sqlSessionFactory.openSession();
			list = session.selectList("com.my.mybatis.NoticeMapper.findAll");
			for(Notice n : list) {
				if(n.getNotiNo() == no) {
					break;
				}else {
					i++;
				}
			}
			return list.get(i).getNotiNo();
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