package com.my.vo;

import java.util.Date;

public class Review {
	private int reviewNo;
	private String reviewDes;
	private double reviewStar;
	private Date reviewDate;
	private int stNum;
	private String memId;
	private int reviewState;
	public int getreviewNo() {
		return reviewNo;
	}
	public void setreviewNo(int reviewNo) {
		this.reviewNo = reviewNo;
	}
	public String getreviewDes() {
		return reviewDes;
	}
	public void setreviewDes(String reviewDes) {
		this.reviewDes = reviewDes;
	}
	public double getreviewStar() {
		return reviewStar;
	}
	public void setreviewStar(double reviewStar) {
		this.reviewStar = reviewStar;
	}
	public Date getreviewDate() {
		return reviewDate;
	}
	public void setreviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	public int getstNum() {
		return stNum;
	}
	public Review() {
		super();
	}
	public void setstNum(int stNum) {
		this.stNum = stNum;
	}
	public String getmemId() {
		return memId;
	}
	public void setmemId(String memId) {
		this.memId = memId;
	}
	public int getreviewState() {
		return reviewState;
	}
	public void setreviewState(int reviewState) {
		this.reviewState = reviewState;
	}
	public Review(int reviewNo, String reviewDes, double reviewStar, Date reviewDate, int stNum, String memId,
			int reviewState) {
		super();
		this.reviewNo = reviewNo;
		this.reviewDes = reviewDes;
		this.reviewStar = reviewStar;
		this.reviewDate = reviewDate;
		this.stNum = stNum;
		this.memId = memId;
		this.reviewState = reviewState;
	}
	

}
