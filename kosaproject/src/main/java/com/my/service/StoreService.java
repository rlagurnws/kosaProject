package com.my.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.repository.StoreRepository;
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
	public List<Store> findAll() throws FindException{
		return repository.selectAll();
	}
	public List<Store> searchByCate(int cate) throws FindException{
		return repository.selectByCate(cate);
	}
}
