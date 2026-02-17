package hellojpa.chapter.chapter4_;

import hellojpa.chapter.chapter1_3.DataCRUD;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Chapter4_ {
	
	public static void start(EntityManagerFactory emf) {
		
		EntityManager em = emf.createEntityManager();
		
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
		
	}
	
}
