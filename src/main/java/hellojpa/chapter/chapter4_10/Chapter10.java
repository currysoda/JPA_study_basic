package hellojpa.chapter.chapter4_10;

import hellojpa.chapter.chapter4_10.dto.MemberStatsDTO;
import hellojpa.chapter.chapter4_10.dto.MemberDTO;
import hellojpa.chapter.chapter4_10.entity.MemberV2;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import lombok.extern.log4j.Log4j2;

// JPQL 쿼리 언어
@Log4j2
public class Chapter10 {
	
	public static void jpqlStart(EntityManagerFactory emf) {
		
		EntityManager em = emf.createEntityManager();
		
		// 검색 jpql
		// 파라미터 바인딩 방식으로 :ParamterName 으로 써준다. 숫자 방식은 권장하지 않음
		String jpql1 = "select m from Member m where m.name like :namePattern escape '\\'";
		
		List<MemberV2> memberV2List = em.createQuery(jpql1, MemberV2.class)
		                                .setParameter("namePattern", "%MemberV2%") // 파라미터값을 넣어준다.
		                                .getResultList();
		
		System.out.println("memberV2List.size() = " + memberV2List.size());
		
		for (MemberV2 m : memberV2List)
		{
			System.out.println("m.getName() = " + m.getName());
		}
		
		// 집계 함수
		String jpql2 = "select new hellojpa.chapter.chapter4_10.dto.MemberStatsDTO(count(m.age), sum(m.age), avg(m.age), max(m.age), min(m.age)) from Member m";
		
		MemberStatsDTO stats = em.createQuery(jpql2, MemberStatsDTO.class)
		                         .getSingleResult();
		
		System.out.println("stats.getCount() = " + stats.getCount()); // 갯수
		System.out.println("stats.getAvgAge() = " + stats.getAvgAge()); // 평균
		System.out.println("stats.getSumAge() = " + stats.getSumAge()); // 총합
		System.out.println("stats.getMaxAge() = " + stats.getMaxAge()); // 최대값
		System.out.println("stats.getMinAge() = " + stats.getMinAge()); // 최소값
		
		// 프로젝션
		// 프로젝션 대상이 여러개의 컬럼인 경우 전용 DTO 를 설계하는 것이 권장된다.
		// 사실 클래스 설계를 바꾸면 읽어오는 곳도 바뀌기 때문에 읽어올 때는 클래스 정보를 바로 적기보다 DTO 를 쓰는게 좋다.
		String jpql3 = "select new hellojpa.chapter.chapter4_10.dto.MemberDTO(m.name, m.age) from Member m";
		
		List<MemberDTO> memberDTOList = em.createQuery(jpql3, MemberDTO.class)
		                                  .getResultList();
		
		for (MemberDTO m : memberDTOList)
		{
			System.out.println("m.getName() = " + m.getName());
			System.out.println("m.getAge() = " + m.getAge());
		}
		
		
	}
}
