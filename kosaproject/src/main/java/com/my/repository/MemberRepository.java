package com.my.repository;

import java.util.List;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.vo.Member;


public interface MemberRepository {
	
	void insert(Member m)throws AddException;	
	
	Member selectById(String id) throws FindException;
	
	void modify(Member m)throws ModifyException;
	
//	Member memberInfo(String id)throws FindException;
	
	void delete(String id) throws RemoveException;

	int selectCount() throws FindException;
	
	List<Member> selectAll() throws FindException;
	
	List<Member> selectAll(int currentPage, int cntPerPage) throws FindException;
	
}
