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
import com.my.vo.Menu;

import com.my.vo.Store;



@Repository
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
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			return session.selectOne("com.my.mybatis.StoreMapper.selectCount");
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
	public List<Store> selectByCate() throws FindException {
		return null;
	}
	
	public List<Store> selectByCate(int currentPage, int cntPerPage) throws FindException{
		SqlSession session = null;
		List<Store> list = new ArrayList<>();
		try {
			session = sqlSessionFactory.openSession();
			int startRow = ((currentPage-1)*cntPerPage)+1;
			int endRow = currentPage*cntPerPage;
			Map<String, Object> map = new HashMap<>();
			map.put("startRow",startRow);
			map.put("endRow", endRow);
			list =  session.selectList("com.my.mybatis.StoreMapper.selectByCate",map);
			
			return list;
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
