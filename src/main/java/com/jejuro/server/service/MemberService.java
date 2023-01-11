package com.jejuro.server.service;

import com.jejuro.server.entity.Member;

public interface MemberService {
	// 회원 추가
	void add(Member member);

	// 회원 정보 가져오기
	Member get(int id);

	// 회원 삭제
	void delete(int id);

	// 회원 수정
	int update(Member member);

	// 회원 정보 email로 가져오기
	Member getByEmail(String email);

	// 회원 정보 닉네임으로 가져오기
	Member getByNickname(String username);
}
