package hellojpa.chapter.chapter4_;

import hellojpa.chapter.chapter4_.entity.MemberV2;
import hellojpa.chapter.chapter4_.entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.log4j.Log4j2;

/**
 * JPA 를 이용한 데이터 기본 CRUD
 * chapter 4 이후 사용하는 CRUD
 */
@Log4j2
public class DataCRUD_V2 {
	
	// JPA 를 이용한 데이터 삽입
	public static void InsertData(EntityManager em) throws Exception {
		
		// 넣을 엔티티 배열에 선언
		MemberV2[] arrMember = new MemberV2[10];
		
		for (int i = 0; i < 10; i++)
		{
			String str = "hello";
			
			MemberV2.builder()
			        .name(str)
			        .role(Role.MEMBER)
			        .dontMakeColumn("special field")
			        .build();
		}
		
		// JPA 는 트랜잭션 안에서 실행되어야 한다.
		EntityTransaction tx = em.getTransaction();
		
		
		try
		{
			tx.begin();
			
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
			// finally 에서 특별히 할 일 없음
			// 파라미터로 전달받은 em 은 메소드 밖에서 해제
		}
		
		System.out.println("데이터 입력 완료");
	}
	
	// JPA 를 이용한 데이터 조회
	// 조회또한 트랜잭션 안에서 실행되는 것이 좋다.
	public static void readData(EntityManager em) {
		EntityTransaction tx = em.getTransaction();
		
		try
		{
			tx.begin();
			
			MemberV2 result = em.find(MemberV2.class, 2L);
			
			System.out.println("result.getClass() = " + result.getClass());
			System.out.println("result.getId() = " + result.getId());
			System.out.println("result.getName() = " + result.getName());
			
			tx.commit();
		}
		catch (Exception e)
		{
			// Log4j2 Logger를 사용하여 ERROR 레벨로 빨간색 출력
			log.error("데이터 읽는 중 예외 발생!", e);
			
			// 트랜잭션에서 수행된 작업을 모두 취소하고 트랜잭션 실행 이전의 상태로 되돌린다.
			tx.rollback();
			System.out.println("=== 문제 발생(catch 블록) ===");
			System.out.println("=== 트랜잭션 롤백 ===");
			
			return;
		}
		finally
		{
			System.out.println("=== finally 블록 호출 ===");
			// finally 에서 특별히 할 일 없음
			// 파라미터로 전달받은 em 은 메소드 밖에서 해제
		}
	}
	
	// JPA 를 이용한 엔티티 수정
	// JPA 는 setter 메소드 만으로 엔티티를 수정하고 commit 하면 반영된다.
	public static void updateData(EntityManager em) {
		EntityTransaction tx = em.getTransaction();
		
		
		try
		{
			tx.begin();
			
			MemberV2 result = em.find(MemberV2.class, 2L);
			
			result.setName("newName1111");
			
			System.out.println("result.getClass() = " + result.getClass());
			System.out.println("result.getId() = " + result.getId());
			System.out.println("result.getName() = " + result.getName());
			
			tx.commit();
		}
		catch (Exception e)
		{
			// Log4j2 Logger를 사용하여 ERROR 레벨로 빨간색 출력
			log.error("데이터 수정 중 예외 발생!", e);
			
			// 트랜잭션에서 수행된 작업을 모두 취소하고 트랜잭션 실행 이전의 상태로 되돌린다.
			tx.rollback();
			System.out.println("=== 문제 발생(catch 블록) ===");
			System.out.println("=== 트랜잭션 롤백 ===");
			
			return;
		}
		finally
		{
			System.out.println("=== finally 블록 호출 ===");
			// finally 에서 특별히 할 일 없음
			// 파라미터로 전달받은 em 은 메소드 밖에서 해제
		}
	}
	
	// JPA 를 이용한 엔티티 삭제
	// 삭제를 commit 하면 실제 DB 에 반영됨
	public static void deleteData(EntityManager em) {
		EntityTransaction tx = em.getTransaction();
		
		try
		{
			tx.begin();
			
			MemberV2 target = em.find(MemberV2.class, 3L);
			
			System.out.println("result.getClass() = " + target.getClass());
			System.out.println("result.getId() = " + target.getId());
			System.out.println("result.getName() = " + target.getName());
			
			em.remove(target);
			
			tx.commit();
			if (target == null)
			{
				System.out.println("id == 3 데이터 삭제 성공");
			}
		}
		catch (Exception e)
		{
			// Log4j2 Logger를 사용하여 ERROR 레벨로 빨간색 출력
			log.error("데이터 삭제 중 예외 발생!", e);
			
			// 트랜잭션에서 수행된 작업을 모두 취소하고 트랜잭션 실행 이전의 상태로 되돌린다.
			tx.rollback();
			System.out.println("=== 문제 발생(catch 블록) ===");
			System.out.println("=== 트랜잭션 롤백 ===");
			
			return;
		}
		finally
		{
			System.out.println("=== finally 블록 호출 ===");
			// finally 에서 특별히 할 일 없음
			// 파라미터로 전달받은 em 은 메소드 밖에서 해제
		}
	}
}
