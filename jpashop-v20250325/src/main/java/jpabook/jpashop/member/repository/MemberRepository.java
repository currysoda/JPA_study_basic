package jpabook.jpashop.member.repository;

import java.util.List;
import jpabook.jpashop.member.entity.Member;

public interface MemberRepository {
	
	Member save(Member member);
	
	Member findOneByMemberId(Long id);
	
	List<Member> findAll();
	
	List<Member> findAllByName(String name);
}
