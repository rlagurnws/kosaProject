package com.my.repository;

import java.sql.BatchUpdateException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.vo.Notice;

//@Repository
public interface NoticeRepository {
	/**
	 * 공지사항 추가
	 * @param notice
	 * @throws AddException
	 */
	void insert(Notice notice) throws AddException;
	/**
	 * 공지사항 전체
	 * @return 공지사항 전체
	 * @throws FindException
	 */
	List<Notice> selectAll() throws FindException;
	/**
	 * 공지사항 전체 개수
	 * @return
	 * @throws FindException
	 */
	int selectCount() throws FindException;
	
	List<Notice> selectAll(int currentPage, int cntPerPage) throws FindException;
	
	Notice selectByNo(int no) throws FindException;
	
	int findNext(int no) throws FindException;
	
	int findPre(int no) throws FindException;
	
	void delete(int no) throws RemoveException;
	
	void update(Notice noti) throws ModifyException;
}
