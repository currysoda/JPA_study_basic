package jpabook.jpashop.member.repository;

import java.util.List;
import jpabook.jpashop.member.entity.Member;

public interface MemberRepository {
	
	Member save(Member member);
	
	Member findOneByMemberId(Long id);
	
	Member findOneByMemberName(String name);
	
	List<Member> findAll();
	
	List<Member> findAllByName(String name);
	
	void removeAllMember();
}
