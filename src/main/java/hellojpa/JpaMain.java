package hellojpa;

import eu.infomas.annotation.AnnotationDetector;
import hellojpa.jpamethod.DataCompare;
import hellojpa.jpamethod.DataCRUD;
import jakarta.persistence.*;
import java.lang.annotation.Annotation;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.hibernate.cfg.AvailableSettings;

@Log4j2
public class JpaMain {
	
	public static void main(String[] args) throws Exception {
		// UTF-8 인코딩 설정 (System.out.println 한글 출력 깨짐 방지)
		System.setProperty("file.encoding", "UTF-8");
		java.lang.System.setOut(new java.io.PrintStream(System.out, true, StandardCharsets.UTF_8));
		java.lang.System.setErr(new java.io.PrintStream(System.err, true, StandardCharsets.UTF_8));
		
		// 엔티티 매니저 팩토리 생성
		List<Class<?>> entities = new ArrayList<>();
		
		AnnotationDetector detector =
			new AnnotationDetector(new AnnotationDetector.TypeReporter() {
				@Override
				public void reportTypeAnnotation(Class<? extends Annotation> annotation, String className) {
					try
					{
						entities.add(Class.forName(className));
					}
					catch (ClassNotFoundException e)
					{
						throw new RuntimeException(e);
					}
				}
				
				@Override
				public Class<? extends Annotation>[] annotations() {
					return new Class[]{Entity.class};
				}
			});
		
		detector.detect("hellojpa");
		
		Map<String, Object> props = new HashMap<>();
		props.put(AvailableSettings.LOADED_CLASSES, entities);
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello", props);
		
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
		
		// 데이터 수정
		
		// 데이터 삭제
		
		
		// 애플리케이션 종료시 엔티티 매니저 팩토리 종료
		emf.close();
	}
}
