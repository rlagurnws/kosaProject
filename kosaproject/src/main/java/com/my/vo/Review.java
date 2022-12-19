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
	public int getReviewNo() {
		return reviewNo;
	}
	public void setReviewNo(int reviewNo) {
		this.reviewNo = reviewNo;
	}
	public String getReviewDes() {
		return reviewDes;
	}
	public void setReviewDes(String reviewDes) {
		this.reviewDes = reviewDes;
	}
	public double getReviewStar() {
		return reviewStar;
	}
	public void setReviewStar(double reviewStar) {
		this.reviewStar = reviewStar;
	}
	public Date getReviewDate() {
		return reviewDate;
	}
	public void setReviewDate(Date reviewDate) {
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
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public int getReviewState() {
		return reviewState;
	}
	public void setReviewState(int reviewState) {
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
