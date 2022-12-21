package com.my.repository;

import java.util.List;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.vo.Store;

public interface StoreRepository {
	
	/**
	 * 가게등록 
	 * @author 이남규
	 * @param store  
	 * @return 
	 * @throws AddException
	 */
	int insert(Store store) throws AddException;
	
	/**
	 * 가게 전체 개수
	 * @return
	 * @throws FindException
	 */
	
	
	
	public List<Store> selectByCate(int cate) throws FindException;
	
	
	int selectStoreCount() throws FindException;
	
	List<Store> selectAll(int currentPage, int cntPerPage) throws FindException;
	

}
