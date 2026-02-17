package hellojpa.chapter.chapter1_3;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Chapter1_3 {
	
	public static void start(EntityManagerFactory emf) {
		
		// 엔티티 매니저를 엔티티 매니저 팩토리에서 꺼내온다.
		EntityManager em = emf.createEntityManager();
		
		// 데이터 입력
		try
		{
			DataCRUD.InsertData(em);
		}
		
		catch (Exception e)
		{
			// Log4j2 Logger를 사용하여 ERROR 레벨로 빨간색 출력
			log.error("메소드 내부 문제 발생!", e);
		}
		finally
		{
			em.close();
		}
		
		// 데이터 조회 & 비교
		em = emf.createEntityManager();
		
		try
		{
			DataCompare.dataCompare(em);
		}
		
		catch (Exception e)
		{
			// Log4j2 Logger를 사용하여 ERROR 레벨로 빨간색 출력
			log.error("메소드 내부 문제 발생!", e);
		}
		finally
		{
			em.close();
		}
		
		// 데이터 읽기
		em = emf.createEntityManager();
		
		try
		{
			DataCRUD.readData(em);
		}
		
		catch (Exception e)
		{
			// Log4j2 Logger를 사용하여 ERROR 레벨로 빨간색 출력
			log.error("메소드 내부 문제 발생!", e);
		}
		finally
		{
			em.close();
		}
		
		// 데이터 수정
		em = emf.createEntityManager();
		
		try
		{
			DataCRUD.updateData(em);
		}
		
		catch (Exception e)
		{
			// Log4j2 Logger를 사용하여 ERROR 레벨로 빨간색 출력
			log.error("메소드 내부 문제 발생!", e);
		}
		finally
		{
			em.close();
		}
		
		// 데이터 삭제
		em = emf.createEntityManager();
		
		System.out.println("데이터 삭제 예제 시작");
		try
		{
			DataCRUD.deleteData(em);
		}
		
		catch (Exception e)
		{
			// Log4j2 Logger를 사용하여 ERROR 레벨로 빨간색 출력
			log.error("메소드 내부 문제 발생!", e);
		}
		finally
		{
			em.close();
		}
	}
}
