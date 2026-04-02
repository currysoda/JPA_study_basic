package jpabook.jpashop.member.service;

import jpabook.jpashop.member.entity.Member;
import jpabook.jpashop.member.repository.MemberRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	private final MemberRepositoryImpl memberRepositoryImpl;
	
	/**
	 * 회원가입
	 */
	@Transactional //변경
	public Long join(Member member) {
		
		validateDuplicateMember(member); //중복 회원 검증
		memberRepositoryImpl.save(member);
		return member.getId();
	}
	
	private void validateDuplicateMember(Member member) {
		List<Member> findMembers = memberRepositoryImpl.findAllByName(member.getName());
		if (!findMembers.isEmpty())
		{
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}
	
	/**
	 * 전체 회원 조회
	 */
	public List<Member> findMembers() {
		return memberRepositoryImpl.findAll();
	}
	
	public Member findOne(Long memberId) {
		return memberRepositoryImpl.findOneByMemberId(memberId);
	}
}