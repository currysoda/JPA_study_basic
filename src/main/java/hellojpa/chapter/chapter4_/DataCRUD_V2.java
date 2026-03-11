package hellojpa.chapter.chapter4_;

import hellojpa.chapter.chapter4_.entity.Address;
import hellojpa.chapter.chapter4_.entity.Fruit;
import hellojpa.chapter.chapter4_.entity.Locker;
import hellojpa.chapter.chapter4_.entity.MemberV2;
import hellojpa.chapter.chapter4_.entity.Role;
import hellojpa.chapter.chapter4_.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;

/**
 * JPA 를 이용한 데이터 기본 CRUD
 * chapter 4 이후 사용하는 CRUD
 */
@Log4j2
public class DataCRUD_V2 {
	
	// JPA 를 이용한 데이터 삽입
	// 연관관계 설명을 위한 데이터 삽입
	public static void InsertData(EntityManager em) throws Exception {
		
		// 넣을 엔티티 배열에 선언
		MemberV2[] arrMember = new MemberV2[10];
		Team[]     arrTeam   = new Team[3];
		Locker[]   arrlocker = new Locker[10];
		
		Address address1 = Address.builder()
		                          .locationName("home-address")
		                          .zipcode(12345)
		                          .build();
		
		Address address2 = Address.builder()
		                          .locationName("corporation-address")
		                          .zipcode(12345)
		                          .build();
		
		Fruit fruit1 = Fruit.builder()
		                    .name("apple")
		                    .build();
		
		Fruit fruit2 = Fruit.builder()
		                    .name("banana")
		                    .build();
		
		Fruit fruit3 = Fruit.builder()
		                    .name("orange")
		                    .build();
		
		for (int i = 0; i < 10; i++)
		{
			String str       = "MemberV2";
			String strLocker = "locker";
			
			MemberV2 memberV2 = MemberV2.builder()
			                            .name(str + "-" + String.valueOf(i + 1))
			                            .role(Role.MEMBER)
			                            .dontMakeColumn("special field" + String.valueOf(i + 1))
			                            .CLOB("test CLOB string" + String.valueOf(i + 1))
			                            .BLOB(10000L)
			                            .build();
			
			Locker locker = Locker.builder()
			                      .name(strLocker + (i + 1))
			                      .build();
			
			// fruit 더하기
			memberV2.addFruit(fruit1);
			memberV2.addFruit(fruit2);
			memberV2.addFruit(fruit3);
			
			// fruit 빼기
			// memberV2.removeFruit(fruit3);
			
			arrMember[i] = memberV2;
			arrlocker[i] = locker;
		}
		
		for (int i = 0; i < arrTeam.length; i++)
		{
			String str = "Team";
			
			Team team = Team.builder()
			                .name(str + String.valueOf(1 + i))
			                .build();
			
			arrTeam[i] = team;
		}
		
		// JPA 는 트랜잭션 안에서 실행되어야 한다.
		EntityTransaction tx = em.getTransaction();
		
		try
		{
			tx.begin();
			
			// 필요한 데이터 넣기
			for (int i = 0; i < arrMember.length; i++)
			{
				em.persist(arrMember[i]);
				em.persist(arrlocker[i]);
			}
			
			for (int i = 0; i < arrTeam.length; i++)
			{
				em.persist(arrTeam[i]);
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
	
	// member-team 의 연관관계를 맺는 메소드
	public static void memberTeamMapping(EntityManager em) {
		
		EntityTransaction tx = em.getTransaction();
		
		try
		{
			tx.begin();
			
			MemberV2 m1 = em.find(MemberV2.class, 1L);
			MemberV2 m2 = em.find(MemberV2.class, 2L);
			MemberV2 m3 = em.find(MemberV2.class, 3L);
			MemberV2 m4 = em.find(MemberV2.class, 4L);
			
			Team t1 = em.find(Team.class, 1L);
			Team t2 = em.find(Team.class, 2L);
			Team t3 = em.find(Team.class, 3L);
			
			// team 에 들어가는 메소드
			m1.joinTeam(t1);
			m2.joinTeam(t2);
			m3.joinTeam(t3);
			m4.joinTeam(t3);
			
			// team 을 떠나는 메소드
			m4.leaveTeam();
			
			// member 의 team 필드를 이용해서 team name 조회
			System.out.println("m1.getTeam().getName() = " + m1.getTeam().getName());
			
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
	
	// 프록시 객체 조회 예제
	public static void getProxyInstance(EntityManager em) {
		
		EntityTransaction tx = em.getTransaction();
		
		try
		{
			tx.begin();
			
			em.clear();
			
			System.out.println("em.getReference() 호출 전");
			
			MemberV2 memberV2Reference = em.getReference(MemberV2.class, 1L);
			
			System.out.println("em.getReference() 호출 후");
			
			if (memberV2Reference instanceof MemberV2)
			{
				System.out.println("프록시 객체의 클래스 체크(true 시 출력)");
			}
			
			
			System.out.println("필드 접근 전");
			System.out.println("memberV2Reference.getName() = " + memberV2Reference.getName());
			System.out.println("필드 접근 후");
			
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
	
	// 값
	
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
