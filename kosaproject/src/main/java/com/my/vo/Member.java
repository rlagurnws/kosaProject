package com.my.vo;


public class Member {
	private String memId;
	private String memName;
	private String memPwd;
	private String memBirth;
	private String memPhone;
	private String memSex;
	private String memNick;
	private int memPower;
	private int memState;
	private Member memInfo;
	
	
	
	public Member getMemInfo() {
		return memInfo;
	}
	public void setMemInfo(Member memInfo) {
		this.memInfo = memInfo;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public String getMemPwd() {
		return memPwd;
	}
	public void setMemPwd(String memPwd) {
		this.memPwd = memPwd;
	}
	public String getMemBirth() {
		return memBirth;
	}
	public void setMemBirth(String memBirth) {
		this.memBirth = memBirth;
	}
	public String getMemPhone() {
		return memPhone;
	}
	public void setMemPhone(String memPhone) {
		this.memPhone = memPhone;
	}
	public String getMemSex() {
		return memSex;
	}
	public void setMemSex(String memSex) {
		this.memSex = memSex;
	}
	public String getMemNick() {
		return memNick;
	}
	public void setMemNick(String memNick) {
		this.memNick = memNick;
	}
	public int getMemPower() {
		return memPower;
	}
	public void setMemPower(int memPower) {
		this.memPower = memPower;
	}
	public int getMemState() {
		return memState;
	}
	public Member(String memId, String memName, String memPwd, String memBirth, String memPhone, String memSex,
			String memNick, int memPower, int memState) {
		super();
		this.memId = memId;
		this.memName = memName;
		this.memPwd = memPwd;
		this.memBirth = memBirth;
		this.memPhone = memPhone;
		this.memSex = memSex;
		this.memNick = memNick;
		this.memPower = memPower;
		this.memState = memState;
	}
	public Member() {
		super();
	}
	public void setMemState(int memState) {
		this.memState = memState;
	}
	
}
