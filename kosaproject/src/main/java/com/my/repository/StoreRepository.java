package com.my.repository;

import java.util.List;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.vo.Notice;
import com.my.vo.Store;

public interface StoreRepository {
	
	/**
	 * 가게등록
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
	
	int selectCount() throws FindException;
		

		


	

	List<Store> selectByCate(int currentPage, int cntPerPage) throws FindException;

	List<Store> selectByCate() throws FindException;




}
