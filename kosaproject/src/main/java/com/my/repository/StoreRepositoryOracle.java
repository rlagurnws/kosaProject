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
import com.my.exception.ModifyException;
import com.my.vo.Menu;
import com.my.vo.Store;

@Repository("storeRepository")
public class StoreRepositoryOracle implements StoreRepository{

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	@Override
	public int insert(Store store) throws AddException {
		SqlSession session = null;
		int stNum;
		try {
			session = sqlSessionFactory.openSession();
			session.insert("com.my.mybatis.StoreMapper.insert",store);
			System.out.println(store.getStNum());
			session.close();
			
			List<Menu> foodList = store.getStMenuList();			
			for(Menu menu: foodList) {
				session = sqlSessionFactory.openSession();
				menu.setStNum(store.getStNum());
				session.insert("com.my.mybatis.StoreMapper.insertfood",menu);
			}
			return store.getStNum();
			
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
	public int selectCount() throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			return session.selectOne("com.my.mybatis.StoreMapper.selectCount");			
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<Store> submitted(int currentPage, int cntPerPage) throws FindException {
		List<Store> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		SqlSession session = null;
		try {
			int startRow = ((currentPage-1)*cntPerPage)+1;
			int endRow = currentPage*cntPerPage;
			map.put("startRow",startRow);
			map.put("endRow", endRow);
			session = sqlSessionFactory.openSession();
			return session.selectList("com.my.mybatis.StoreMapper.submitted", map);
		} catch (Exception e){
			throw new FindException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	
	@Override
	public Store selectByNo(int storeNo) throws FindException {
		SqlSession session = null;
		Store s = new Store();
		try {
			session = sqlSessionFactory.openSession();
			return session.selectOne("com.my.mybatis.StoreMapper.selectByNo",storeNo);
		} catch(Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			if(session !=null) {
				session.close();
			}
		}
	}
	
	@Override
	public List<Menu> findMenu(int stNum) throws FindException {
		SqlSession session = null;
		List<Menu> list = new ArrayList<>();
		try {
			session = sqlSessionFactory.openSession();
			return session.selectList("com.my.mybatis.StoreMapper.selectMenu",stNum);
		} catch(Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	
	@Override
	public void confirmStore(int stNum) throws ModifyException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			session.update("com.my.mybatis.StoreMapper.confirmStore",stNum);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<Store> selectByCate(int cate) throws FindException {
		return null;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
