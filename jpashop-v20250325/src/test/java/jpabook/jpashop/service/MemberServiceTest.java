package jpabook.jpashop.service;

import jpabook.jpashop.member.service.MemberServiceImpl;
import jpabook.jpashop.member.entity.Member;
import jpabook.jpashop.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberServiceTest {
	
	@Autowired
	MemberServiceImpl memberService;
	@Autowired
	MemberRepository  memberRepository;
	
	@Test
	public void 회원가입() throws Exception {
		
		//Given
		Member member = new Member();
		member.setName("kim");
		
		//When
		Long saveId = memberService.join(member);
		
		//Then
		assertEquals(member, memberRepository.findOneByMemberId(saveId));
	}
	
	@Test
	public void 중복_회원_예외() throws Exception {
		//Given
		Member member1 = new Member();
		member1.setName("kim");
		
		Member member2 = new Member();
		member2.setName("kim");
		
		//When
		memberService.join(member1);
		
		//Then
		
		//IllegalStateException 예외가 발생하지 않으면 테스트 실패
		assertThrows(IllegalStateException.class, () -> memberService.join(member2));
	}
}