package jpabook.jpashop.service;

import jpabook.jpashop.address.Address;
import jpabook.jpashop.member.entity.Member;
import jpabook.jpashop.member.repository.MemberRepository;
import jpabook.jpashop.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
@Rollback(false) // DB에서 결과 직접 확인하고 싶을 때 주석 해제
@RequiredArgsConstructor
@TestInstance(Lifecycle.PER_CLASS)
@DisplayName("MemberService 전체 테스트")
public class MemberServiceTest {
	
	private final MemberService    memberService;
	private final MemberRepository memberRepository;
	
	@BeforeAll
	static void setUp() {
		
		Address[] addressArr = new Address[10];
		
		for (int i = 0; i < 10; i++)
		{
			addressArr[i] = Address.builder()
			                       .city("city-" + i)
			                       .zipcode("zipcode-" + i)
			                       .street("street-" + i)
			                       .build();
		}
		
		for (int i = 0; i < 10; i++)
		{
			Member.builder()
			      .name("memberName-" + i)
			      .address(addressArr[i])
			      .build();
		}
	}
	
	@Test
	@DisplayName("회원 가입")
	public void 회원가입() throws Exception {
		//Given
		Member member = Member.builder()
		                      .name("kim")
		                      .address(new Address("서울", "강남대로", "12345"))
		                      .build();
		
		//When
		Long saveId = memberService.join(member);
		
		//Then
		assertEquals(member, memberRepository.findOneByMemberId(saveId));
		log.info("저장된 회원 id={}, name={}", saveId, member.getName());
	}
	
	@Test
	@DisplayName("중복 회원 예외")
	public void 중복_회원_예외() throws Exception {
		//Given
		Member member1 = Member.builder().name("kim").build();
		Member member2 = Member.builder().name("kim").build();
		
		//When
		memberService.join(member1);
		
		//Then
		assertThrows(IllegalStateException.class, () -> memberService.join(member2));
	}
	
	@Test
	@DisplayName("전체 회원 조회")
	public void 전체_회원_조회() throws Exception {
		//Given
		memberService.join(Member.builder().name("kim").build());
		memberService.join(Member.builder().name("lee").build());
		
		//When
		List<Member> members = memberService.findMembers();
		
		//Then
		assertEquals(2, members.size());
		log.info("전체 회원 수={}", members.size());
	}
	
	@Test
	@DisplayName("id로 회원 조회")
	public void id로_회원_조회() throws Exception {
		//Given
		Member member = Member.builder().name("kim").build();
		Long   saveId = memberService.join(member);
		
		//When
		Member found = memberService.findOne(saveId);
		
		//Then
		assertNotNull(found);
		assertEquals("kim", found.getName());
	}
	
	@Test
	@DisplayName("이름으로 회원 조회")
	public void 이름으로_회원_조회() throws Exception {
		//Given
		memberService.join(Member.builder().name("kim").build());
		
		//When
		Member found = memberService.findOneByName("kim");
		
		//Then
		assertNotNull(found);
		assertEquals("kim", found.getName());
	}
	
	@Test
	@DisplayName("존재하지 않는 이름으로 조회시 null반환")
	public void 존재하지_않는_이름으로_조회시_null반환() throws Exception {
		//When
		Member found = memberService.findOneByName("없는사람");
		
		//Then
		assertNull(found);
	}
}
