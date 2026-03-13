package hellojpa;

import eu.infomas.annotation.AnnotationDetector;
import hellojpa.chapter.chapter4_10.Chapter10;
import hellojpa.chapter.chapter4_10.Chapter4_9;
import jakarta.persistence.*;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import lombok.extern.log4j.Log4j2;
import org.hibernate.cfg.AvailableSettings;

@Log4j2
public class JpaMain {
	
	public static void main(String[] args) throws Exception {
		
		// 타임존을 UTC 로 고정(실행시 JVM 설정) -> 이후 LocalDateTime.now() 는 UTC 기준
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		
		// UTF-8 인코딩 설정 (System.out.println 한글 출력 깨짐 방지)
		System.setProperty("file.encoding", "UTF-8");
		System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
		System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));
		
		// @Entity 어노테이션 감지해서 emf 에 넣어주는 코드
		List<Class<?>> entities = new ArrayList<>();
		
		AnnotationDetector detector = new AnnotationDetector(new AnnotationDetector.TypeReporter() {
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
		
		// 패키지 경로 넣어야 함
		// @Entity(name = "Member") 가 겹치는 문제 때문에 챕터당 설정 변경 필요
		detector.detect("hellojpa.chapter.chapter4_10");
		
		Map<String, Object> props = new HashMap<>();
		props.put(AvailableSettings.LOADED_CLASSES, entities);
		
		// 엔티티 매니저 팩토리 생성
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello", props);
		
		// 1 ~ 3 장 내용
		// Chapter1_3.start(emf);
		
		// 4 ~ 장 내용
		Chapter4_9.start(emf);
		
		System.out.println(System.lineSeparator());
		System.out.println(System.lineSeparator());
		System.out.println(System.lineSeparator());
		
		// 10 장 내용 JPQL
		System.out.println("Chapter10 시작");
		Chapter10.jpqlStart(emf);
		
		// 애플리케이션 종료시 엔티티 매니저 팩토리 종료
		emf.close();
	}
}
