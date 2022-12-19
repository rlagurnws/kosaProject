package com.my.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.repository.MemberRepository;
import com.my.vo.Member;

@Service
public class MemberService {
	@Autowired
	private MemberRepository repository;
	
	public MemberRepository getRepository() {
		return repository;
	}
	public void setRepository(MemberRepository repository) {
		this.repository = repository;
	}
	
	public MemberService() {}
	
	public MemberService(MemberRepository repository) {
		this.repository = repository;
	}
	
	public void idDupChk(String id) throws FindException{
		repository.selectById(id);
	}
	public void signUp(Member m) throws AddException{
		repository.insert(m);
	}
	public Member searchById(String id) throws FindException{
		return repository.selectById(id);
	}

	public void memMody(Member m) throws ModifyException{
		repository.modify(m);
	}
	public void deleteMem(String id) throws RemoveException{
		repository.delete(id);
	}
}
