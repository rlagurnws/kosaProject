package com.my.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.dto.PageBean;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.repository.StoreRepository;

import com.my.vo.Notice;
import com.my.vo.Store;




import com.my.vo.Menu;
import com.my.vo.Store;


@Service("storeService")
public class StoreService {
	@Autowired
	private StoreRepository repository;
	
	public StoreService() {}
	
	public StoreService(StoreRepository repository) {
		this.repository = repository;
	}
	
	//추가
	public int addStore(Store store) throws AddException{
		int stNum = repository.insert(store);
		System.out.println("add성공");
		return stNum;
	}
	
	

	public List<Store> submittedStore(int currentPage, int cntPerPage) throws FindException{
		return repository.submitted(currentPage, cntPerPage);
	}
	public PageBean<Store> getPageBean(int currentPage) throws FindException{
		List<Store> list = submittedStore(currentPage,PageBean.CNT_PER_PAGE);
		int totalCnt = repository.selectCount();
		PageBean<Store> pb = new PageBean<>(currentPage, list, totalCnt);
		return pb;
	}
	public Store selectByNo(int storeNo) throws FindException{
		return repository.selectByNo(storeNo);
	}
	public List<Menu> findMenu(int stNum) throws FindException{
		return repository.findMenu(stNum);
	}
	public void confirm(int stNum) throws ModifyException{
		repository.confirmStore(stNum);
	}

	public List<Store> searchByCate(int cate) throws FindException{
		return repository.selectByCate(cate);
	}

	
	public List<Store> findAll(int currentPage, int cntPerPage, String search) throws FindException{
		return repository.selectSearch(currentPage, cntPerPage, search);
	}
	
	public PageBean<Store> stListGetPageBean(int currentPage, String search) throws FindException{
		List<Store> list = findAll(currentPage,PageBean.CNT_PER_PAGE, search);
		int totalCnt = repository.selectStoreCount();
		PageBean<Store> pb = new PageBean<>(currentPage, list, totalCnt);
		return pb;
	}
	
	public List<Store> selectStoreNo(int stNum) throws FindException{
		return repository.selectByStoreNum(stNum);
	}
	
	public int updateStore(Store store) throws AddException{
		int stNum = repository.update(store);
		System.out.println("update성공");
		return stNum;
	}
}
