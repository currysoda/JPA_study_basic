package jpabook.jpashop.member.service;

import java.util.List;
import jpabook.jpashop.member.entity.Member;
import org.springframework.transaction.annotation.Transactional;

public interface MemberService {
	
	Long join(Member member);
	
	List<Member> findMembers();
	
	Member findOne(Long memberId);
	
	Member findOneByName(String name);
	
	void removeAll();
}
