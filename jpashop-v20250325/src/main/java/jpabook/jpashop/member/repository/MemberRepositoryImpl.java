package jpabook.jpashop.member.repository;

import jpabook.jpashop.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor // lombok 을 이용한 주입
public class MemberRepositoryImpl implements MemberRepository {
	
	// @PersistenceContext 이 어노테이션으로도 주입 가능
	private final EntityManager em;
	
	public Member save(Member member) {
		em.persist(member);
		return member;
	}
	
	public Member findOneByMemberId(Long id) {
		return em.find(Member.class, id);
	}
	
	public List<Member> findAll() {
		return em.createQuery("select m from Member m", Member.class)
		         .getResultList();
	}
	
	public List<Member> findAllByName(String name) {
		return em.createQuery("select m from Member m where m.name = :name", Member.class)
		         .setParameter("name", name)
		         .getResultList();
	}
}