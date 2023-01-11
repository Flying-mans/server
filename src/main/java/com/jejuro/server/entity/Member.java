package com.jejuro.server.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class Member {

	private int member_id;
	@Email
	@NotEmpty(message = "이메일은 필수항목입니다.")
	private String email;
	@NotEmpty(message = "비밀번호는 필수항목입니다.")
	private String password;
	@NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
	private String passwordCK;
	@NotEmpty(message = "닉네임은 필수항목입니다.")
	private String nickName;
	@NotEmpty(message = "전화번호는 필수항목입니다.")
	private String phoneNum;

	public Member() {
	}

	public Member(int member_id) {
		this.member_id = member_id;
	}

	public Member(String email, String password, String nickName, String phoneNum) {
		super();
		this.email = email;
		this.password = password;
		this.nickName = nickName;
		this.phoneNum = phoneNum;
	}

	public Member(int member_id, String email, String password, String nickName, String phoneNum) {
		this.member_id = member_id;
		this.email = email;
		this.password = password;
		this.nickName = nickName;
		this.phoneNum = phoneNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public String getPasswordCK() {
		return passwordCK;
	}

	public void setPasswordCK(String passwordCK) {
		this.passwordCK = passwordCK;
	}

	@Override
	public String toString() {
		return "Member [member_id=" + member_id + ", email=" + email + ", password=" + password + ", passwordCK="
				+ passwordCK + ", nickName=" + nickName + ", phoneNum=" + phoneNum + "]";
	}

}
