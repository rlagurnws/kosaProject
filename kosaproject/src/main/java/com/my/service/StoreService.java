package com.my.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Properties;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.repository.StoreRepository;
import com.my.vo.Store;

public class StoreService {
	private StoreRepository repository;
	
	public StoreService(String propertiesFileName) {
		Properties env = new Properties();
		try {
			env.load(new FileInputStream(propertiesFileName));
			String className = env.getProperty("store");
			//클래스로드
			Class clazz = Class.forName(className);
			//객체 생성
			Object obj = clazz.getDeclaredConstructor().newInstance();
			repository = (StoreRepository)obj;
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//추가
	public void addStore(Store store) throws AddException{
		repository.insert(store);
		System.out.println("add성공");
	}
	public List<Store> findAll() throws FindException{
		return repository.selectAll();
	}
	public List<Store> searchByCate(int cate) throws FindException{
		return repository.selectByCate(cate);
	}
}
