package com.jejuro.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jejuro.server.dao.MemberDao;
import com.jejuro.server.entity.Member;

@Service
public class DefaultMemberService implements MemberService{

	@Autowired
	private MemberDao dao;
	
	//회원 추가
	@Override
	public void add(Member member) {
		dao.signUp(member);
		
	}

	//회원 정보 가져오기
	@Override
	public Member get(int id) {
		return dao.get(id);
	}

	//회원 삭제
	@Override
	public void delete(int id) {
		dao.delete(id);
	}
// ! 주석해ㅈ제
	//회원 수정
	@Override
	public void update(Member member) {
		// dao.update(member);
		// return dao.getId(member);
	}

	@Override
	public Member getMemberByEmail(String email) {
		return dao.getByEmail(email);
	}

}
