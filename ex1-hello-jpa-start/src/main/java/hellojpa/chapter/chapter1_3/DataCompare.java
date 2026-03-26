package hellojpa.chapter.chapter1_3;

import hellojpa.chapter.chapter1_3.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DataCompare {
	
	public static void dataCompare(EntityManager em) {
		
		EntityTransaction tx = em.getTransaction();
		
		try
		{
			tx.begin();
			
			// 엔티티 동일성 비교
			Member member1 = em.find(Member.class, 1L);
			Member member2 = em.find(Member.class, 1L);
			
			System.out.println("동일성 비교");
			System.out.println(member1 == member2); // true
			
			tx.commit();
		}
		
		catch (Exception e)
		{
			// Log4j2 Logger를 사용하여 ERROR 레벨로 빨간색 출력
			log.error("데이터 저장 중 예외 발생!", e);
			
			// 트랜잭션에서 수행된 작업을 모두 취소하고 트랜잭션 실행 이전의 상태로 되돌린다.
			tx.rollback();
			System.out.println("=== 문제 발생(catch 블록) ===");
			System.out.println("=== 트랜잭션 롤백 ===");
		}
		
		finally
		{
			System.out.println("=== finally 블록 호출 ===");
		}
	}
}
