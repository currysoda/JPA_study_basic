package hellojpa;

import hellojpa.entity.Address;
import hellojpa.entity.Member;
import jakarta.persistence.*;
import java.lang.reflect.Constructor;

public class JpaMain {
	
	public static void main(String[] args) {
		
		// 엔티티 매니저 팩토리 생성
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		// 엔티티 매니저를 엔티티 매니저 팩토리에서 꺼내온다.
		EntityManager em = emf.createEntityManager();
		
		// JPA 는 트랜잭션 안에서 실행되어야 한다.
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		try
		{
			// 필요한 데이터
			Member member1 = Member.builder()
			                      .id(1L)
			                      .name("helloA")
			                      .build();
			
			
			System.out.println("=== BEFORE ===");
			// 영속성 컨텍스트가 관리함
			// persist 단계에서는 쿼리를 DB 에 반영하지 않음
			em.persist(member1);
			System.out.println("=== AFTER ===");
			
			// 1차 캐시에서 조회
			Member result1 = em.find(Member.class, 1L);
			
			System.out.println("result1.getId() = " + result1.getId());
			System.out.println("result1.getName() = " + result1.getName());
			
			// DB 에서 조회 직접 DB 에서 값을 추가하고 실행
			Member result2 = em.find(Member.class, 2L);
			
			System.out.println("result2.getId() = " + result2.getId());
			System.out.println("result2.getName() = " + result2.getName());
			
			// 영속성 컨텍스트에서 동일성 보장
			Member result3 = em.find(Member.class, 1L);
			Member result4 = em.find(Member.class, 1L);
			
			System.out.println("(result3 == result4) = " + (result3 == result4));
			
			// 변경 감지
			
			
			// 변경 후 명시적인 update 문 혹은 메소드가 없어도 영속성 컨텍스트가 변경내용을 DB에 반영한다.
			member2.setName("HI I'M C");
			
			System.out.println("=== BEFORE ===");
			// 트랜잭션 내에서 작업한 결과를 커밋함(DB에 반영함)
			// commit 단계가 직접 DB에 반영하는 단계
			tx.commit();
			System.out.println("=== AFTER ===");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			// 트랜잭션에서 수행된 작업을 모두 취소하고 트랜잭션 실행 이전의 상태로 되돌린다.
			tx.rollback();
			System.out.println("=== 문제 발생 ===");
			System.out.println("=== 트랜잭션 롤백 ===");
		}
		
		finally
		{
			// 사용 후 엔티티 매니저 반환(필수)
			em.close();
			System.out.println("=== 최종 작업 ===");
			System.out.println("=== 엔티티 매니저 반환 ===");
		}
		
		// 애플리케이션 종료시 엔티티 매니저 팩토리 종료
		emf.close();
	}
}
