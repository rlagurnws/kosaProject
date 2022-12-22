package com.my.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.my.dto.PageBean;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.repository.StoreRepository;
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
	
	/**
	 * 페이지에 해당하는 페이지빈정보를 반환한다
	 * @param currentPage
	 * @return 페이지빈 페이지빈에는 게시글목록, 총페이지수, 페이지그룹의 시작페이지, 끝페이지정보가 모두 담겨있다
	 * @throws FindException
	 */
	

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
	
	
	public Store selectByNo(int stNum) throws FindException{
		return repository.selectByNo(stNum);
	}
	public List<Menu> findMenu(int stNum) throws FindException{
		return repository.findMenu(stNum);
	}
	public void confirm(int stNum) throws ModifyException{
		repository.confirmStore(stNum);

	}
	
	public List<Store> selectById(String memId) throws FindException{
		return repository.selectById(memId);
	}
	
	
//	public PageBean<Store> getPageBeanByCate(int currentPage, int cateNum) throws FindException{
//		List<Store> list = selectByCate(currentPage, PageBean.CNT_PER_PAGE);
//		int totalCnt = repository.selectCount();
//		PageBean<Store> pb = new PageBean<>(currentPage, list, totalCnt);
//		return pb;
//	}
	

//	private List<Store> selectByCate(int cateNum, int currentPage, int cntPerPage) throws FindException {
//		return repository.selectByCate(cateNum, currentPage, cntPerPage);
//	}

	public List<Store> selectByCate(int cateNum) throws FindException {
		return repository.selectByCate(cateNum);
	}
}
