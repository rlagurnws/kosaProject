package com.my.repository;

import java.util.List;

import com.my.exception.AddException;
import com.my.exception.FindException;

import com.my.vo.Store;

public interface StoreRepository {
	
	/**
	 * 가게등록
	 * @param store  
	 * @throws AddException
	 */
	void insert(Store store) throws AddException;
	
	/**
	 * 가게 전체 개수
	 * @return
	 * @throws FindException
	 */
	
	List<Store> selectAll() throws FindException;
	
	public List<Store> selectByCate(int cate) throws FindException;
}
