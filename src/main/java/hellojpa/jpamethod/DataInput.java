package hellojpa.jpamethod;

import lombok.extern.log4j.Log4j2;
import hellojpa.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

@Log4j2
public class DataInput {
	
	public static void insertH2data(EntityManager em) throws Exception {
		
		// 넣을 엔티티 배열에 선언
		Member[] arrMember = new Member[10];
		
		for (int i = 0; i < 10; i++)
		{
			String str = "hello";
			
			arrMember[i] = Member.builder()
			                     .name(str + (i + 1))
			                     .build();
		}
		
		// JPA 는 트랜잭션 안에서 실행되어야 한다.
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		
		try
		{
			// 필요한 데이터 넣기
			for (int i = 0; i < 10; i++)
			{
				em.persist(arrMember[i]);
			}
			
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
			
			return;
		}
		finally
		{
			System.out.println("=== finally 블록 호출 ===");
		}
		
		System.out.println("데이터 입력 완료");
		
		return;
	}
}
