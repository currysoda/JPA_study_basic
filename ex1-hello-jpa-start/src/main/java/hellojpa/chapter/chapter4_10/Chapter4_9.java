package hellojpa.chapter.chapter4_10;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.log4j.Log4j2;

// 연관관계
// 값 타입
@Log4j2
public class Chapter4_9 {
	
	public static void start(EntityManagerFactory emf) {
		
		EntityManager em = emf.createEntityManager();
		
		try
		{
			DataCRUD_V2.InsertData(em);
			DataCRUD_V2.memberTeamMapping(em);
			DataCRUD_V2.getProxyInstance(em);
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
