package hellojpa.chapter.chapter4_10;

import hellojpa.chapter.chapter4_10.dto.MemberAgeDTO;
import hellojpa.chapter.chapter4_10.dto.MemberJoinTeamDTO;
import hellojpa.chapter.chapter4_10.dto.MemberStatsDTO;
import hellojpa.chapter.chapter4_10.dto.MemberDTO;
import hellojpa.chapter.chapter4_10.entity.Address;
import hellojpa.chapter.chapter4_10.entity.MemberV2;
import hellojpa.chapter.chapter4_10.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import lombok.extern.log4j.Log4j2;

// JPQL 쿼리 언어
@Log4j2
public class Chapter10 {
	
	public static void jpqlStart(EntityManagerFactory emf) {
		
		EntityManager   em     = emf.createEntityManager();
		Class<MemberV2> member = MemberV2.class;
		
		// 검색 jpql
		// 파라미터 바인딩 방식으로 :ParamterName 으로 써준다. 숫자 방식은 권장하지 않음
		String jpql1 = "select m from Member m where m.name like :namePattern escape '\\'";
		
		List<MemberV2> memberV2List = em.createQuery(jpql1, MemberV2.class)
		                                .setParameter("namePattern", "%MemberV2%") // 파라미터값을 넣어준다.
		                                .getResultList();
		
		System.out.println("JPQL 기본 조회");
		System.out.println("memberV2List.size() = " + memberV2List.size());
		for (MemberV2 m : memberV2List)
		{
			System.out.println("m.getName() = " + m.getName());
		}
		
		// 집계 함수
		String jpql2 = "select new hellojpa.chapter.chapter4_10.dto.MemberStatsDTO(count(m.age), sum(m.age), avg(m.age), max(m.age), min(m.age)) "
			+ "from Member m";
		
		MemberStatsDTO stats = em.createQuery(jpql2, MemberStatsDTO.class)
		                         .getSingleResult();
		
		System.out.println("통계 집계 함수");
		System.out.println("stats.getCount() = " + stats.getCount()); // 갯수
		System.out.println("stats.getAvgAge() = " + stats.getAvgAge()); // 평균
		System.out.println("stats.getSumAge() = " + stats.getSumAge()); // 총합
		System.out.println("stats.getMaxAge() = " + stats.getMaxAge()); // 최대값
		System.out.println("stats.getMinAge() = " + stats.getMinAge()); // 최소값
		
		// 정렬 함수 order by
		String jpql3 = "select m from Member m order by m.age desc";
		
		List<MemberV2> list1 = em.createQuery(jpql3, member).getResultList();
		
		System.out.println("정렬 함수 예제");
		for (MemberV2 m : list1)
		{
			System.out.println("member = " + m.getName());
		}
		
		// 집합 함수 group by
		String jpql7 = "select m.address, count(m) from Member m group by m.address having count(m.address) >= 5";
		String jpql8 = "select m.address, count(m) from Member m group by m.address having count(m.address) < 5";
		
		List<Object[]> list4 = em.createQuery(jpql7).getResultList();
		List<Object[]> list5 = em.createQuery(jpql8).getResultList();
		
		System.out.println("집합 함수 예제");
		for (Object[] o : list4)
		{
			Address address = (Address) o[0];
			Long    count   = (Long) o[1];
			
			System.out.println("address = " + address.getLocationName());
			System.out.println("count = " + count);
		}
		
		for (Object[] o : list5)
		{
			Address address = (Address) o[0];
			Long    count   = (Long) o[1];
			
			System.out.println("address = " + address.getLocationName());
			System.out.println("count = " + count);
		}
		
		
		// 프로젝션
		// 프로젝션 대상이 여러개의 컬럼인 경우 전용 DTO 를 설계하는 것이 권장된다.
		// 사실 클래스 설계를 바꾸면 읽어오는 곳도 바뀌기 때문에 읽어올 때는 클래스 정보를 바로 적기보다 DTO 를 쓰는게 좋다.
		String jpql4 = "select new hellojpa.chapter.chapter4_10.dto.MemberDTO(m.name, m.age) from Member m";
		
		List<MemberDTO> memberDTOList = em.createQuery(jpql4, MemberDTO.class)
		                                  .getResultList();
		
		System.out.println("DTO 로 받는 프로젝션");
		for (MemberDTO m : memberDTOList)
		{
			System.out.println("m.getName() = " + m.getName());
			System.out.println("m.getAge() = " + m.getAge());
		}
		
		// 프로젝션의 대상이 컬럼 하나라면 대응되는 기본 자료형을 쓰면 된다.
		String jpql5 = "select m.name from Member m";
		
		List<String> list2 = em.createQuery(jpql5, String.class).getResultList();
		
		System.out.println("기본형 대상 프로젝션");
		for (String s : list2)
		{
			System.out.println("name = " + s);
		}
		
		// 임베디드 타입 대상 프로젝션
		String jpql6 = "select m.address from Member m";
		
		List<Address> list3 = em.createQuery(jpql6, Address.class).getResultList();
		
		System.out.println("임베디드 타입 프로젝션");
		for (Address a : list3)
		{
			System.out.println("a.getZipcode() = " + a.getZipcode());
			System.out.println("a.getLocationName() = " + a.getLocationName());
		}
		
		// 페이징 API
		// JPA 는 페이징을 추상화한 메소드를 사용한다.
		String jpql9 = "select m from Member m order by m.id";
		
		int pageMax = 2;
		
		List<MemberV2> list6 = em.createQuery(jpql9, member)
		                         .setFirstResult(0 * pageMax)
		                         .setMaxResults(2)
		                         .getResultList();
		
		System.out.println("페이징 예제1");
		for (MemberV2 m : list6)
		{
			System.out.println("m.getName() = " + m.getName());
		}
		
		List<MemberV2> list7 = em.createQuery(jpql9, member)
		                         .setFirstResult(1 * pageMax)
		                         .setMaxResults(2)
		                         .getResultList();
		
		System.out.println("페이징 예제2");
		for (MemberV2 m : list7)
		{
			System.out.println("m.getName() = " + m.getName());
		}
		
		// 조인
		// 내부 조인
		String jpql10 = "select new hellojpa.chapter.chapter4_10.dto.MemberJoinTeamDTO(m.id, m.name, m.team.id, t.id, t.name) "
			+ "from Member m join m.team t";
		
		List<MemberJoinTeamDTO> list8 = em.createQuery(jpql10, MemberJoinTeamDTO.class).getResultList();
		
		// 결과 출력
		System.out.println("내부 조인");
		String attribute = String.format("%-15s | %-15s | %-15s | %-15s | %-15s",
		                                 "member_id",
		                                 "member_name",
		                                 "member_team_id",
		                                 "team_id",
		                                 "team_name");
		System.out.println(attribute);
		for (MemberJoinTeamDTO m : list8)
		{
			String tuple = String.format("%-15d | %-15s | %-15d | %-15d | %-15s",
			                             m.getMemberId(),
			                             m.getMemberName(),
			                             m.getMemberTeamId(),
			                             m.getTeamId(),
			                             m.getTeamName());
			System.out.println(tuple);
		}
		
		// 외부 조인
		String jpql11 = "select new hellojpa.chapter.chapter4_10.dto.MemberJoinTeamDTO(m.id, m.name, m.team.id, t.id, t.name) "
			+ "from Member m left join m.team t";
		
		List<MemberJoinTeamDTO> list9 = em.createQuery(jpql11, MemberJoinTeamDTO.class).getResultList();
		
		// 결과 출력
		System.out.println("외부 조인");
		System.out.println(attribute);
		for (MemberJoinTeamDTO m : list9)
		{
			String tuple = String.format("%-15d | %-15s | %-15d | %-15d | %-15s",
			                             m.getMemberId(),
			                             m.getMemberName(),
			                             m.getMemberTeamId(),
			                             m.getTeamId(),
			                             m.getTeamName());
			System.out.println(tuple);
		}
		
		// 세타 조인
		String jpql12 = "select new hellojpa.chapter.chapter4_10.dto.MemberJoinTeamDTO(m.id, m.name, m.team.id, t.id, t.name) "
			+ "from Member m, Team t "
			+ "where m.team.id = t.id ";
		
		List<MemberJoinTeamDTO> list10 = em.createQuery(jpql12, MemberJoinTeamDTO.class).getResultList();
		
		// 결과 출력
		System.out.println("세타 조인");
		System.out.println(attribute);
		for (MemberJoinTeamDTO m : list10)
		{
			String tuple = String.format("%-15d | %-15s | %-15d | %-15d | %-15s",
			                             m.getMemberId(),
			                             m.getMemberName(),
			                             m.getMemberTeamId(),
			                             m.getTeamId(),
			                             m.getTeamName());
			System.out.println(tuple);
		}
		
		// 조인 - on 절
		String jpql13 = "select new hellojpa.chapter.chapter4_10.dto.MemberJoinTeamDTO(m.id, m.name, m.team.id, t.id, t.name) "
			+ "from Member m join m.team t on m.id >= 5";
		
		List<MemberJoinTeamDTO> list11 = em.createQuery(jpql13, MemberJoinTeamDTO.class).getResultList();
		
		// 결과 출력
		System.out.println("조인 on절");
		System.out.println(attribute);
		for (MemberJoinTeamDTO m : list11)
		{
			String tuple = String.format("%-15d | %-15s | %-15d | %-15d | %-15s",
			                             m.getMemberId(),
			                             m.getMemberName(),
			                             m.getMemberTeamId(),
			                             m.getTeamId(),
			                             m.getTeamName());
			System.out.println(tuple);
		}
		
		// 서브 쿼리
		String subQueryAvgAge = "select avg(m.age) from Member m";
		String jpql14         = "select m from Member m where m.age > (" + subQueryAvgAge + ")";
		
		List<MemberV2> list12 = em.createQuery(jpql14, member).getResultList();
		
		System.out.println("서브 쿼리");
		for (MemberV2 m : list12)
		{
			System.out.println("m.getName() = " + m.getName());
		}
		
		// 조건문 - case
		String jpql15 = "select new hellojpa.chapter.chapter4_10.dto.MemberAgeDTO(m.id, m.name, m.age, "
			+ "case when m.age <= 10 then '어린이' when m.age >= 60 then '노인' else '일반인' end) "
			+ "from Member m";
		
		List<MemberAgeDTO> list13 = em.createQuery(jpql15, MemberAgeDTO.class).getResultList();
		
		String attribute1 = String.format("%-15s | %-15s | %-15s | %-15s",
		                                  "member_id",
		                                  "member_name",
		                                  "member_age",
		                                  "case_result");
		
		System.out.println("조건문 - case");
		System.out.println(attribute1);
		for (MemberAgeDTO m : list13)
		{
			String tuple = String.format("%-15d | %-15s | %-15d | %-15s",
			                             m.getId(),
			                             m.getName(),
			                             m.getAge(),
			                             m.getAgeGroup());
			System.out.println(tuple);
		}
		
		// 조건문 - coalesce
		String jpql16 = "select m.id, m.name, coalesce(m.team.id, 0L) "
			+ "from Member m ";
		
		List<Object[]> list14 = em.createQuery(jpql16, Object[].class)
		                          .getResultList();
		
		String attribute2 = String.format("%-15s | %-15s | %-15s",
		                                  "member_id",
		                                  "member_name",
		                                  "team_id");
		
		System.out.println("조건문 - coalesce 결과(team_id = 0 이면 팀 없음)");
		System.out.println(attribute2);
		for (Object[] o : list14)
		{
			String tuple = String.format("%-15d | %-15s | %-15d",
			                             o[0],
			                             o[1],
			                             o[2]);
			System.out.println(tuple);
		}
		
		// 조건문 - nullif
		String jpql17 = "select m.id, m.name, nullif(m.team.id, 1L) "
			+ "from Member m ";
		
		List<Object[]> list15 = em.createQuery(jpql17, Object[].class)
		                          .getResultList();
		
		System.out.println("조건문 - nullif 결과(team_id = 1 이면 값을 null 로 바꿈)");
		System.out.println(attribute2);
		for (Object[] o : list15)
		{
			String tuple = String.format("%-15d | %-15s | %-15d",
			                             o[0],
			                             o[1],
			                             o[2]);
			System.out.println(tuple);
		}
		
		// jpql 기본 함수
		String jpql18 = "select concat('a','b') "
			+ "from Member m ";
		
		List<String> list16 = em.createQuery(jpql18, String.class).getResultList();
		
		System.out.println("jpql 기본 함수");
		for (String s : list16)
		{
			System.out.println(s);
		}
		
		// concat 이외에 substring, trim, lower, upper, length, locate, abs, sqrt, mod, size, index 등이 있음
		
		// 사용자 정의 함수 - group_concat (MyH2CustomFunction 에서 등록)
		String jpql19 = "select group_concat(m.name) from Member m";
		
		List<String> list17 = em.createQuery(jpql19, String.class).getResultList();
		
		System.out.println("사용자 정의 함수 - group_concat");
		for (String s : list17)
		{
			System.out.println(s);
		}
		
		// 경로 표현식, 명시적 조인, 묵시적 조인
		// 이건 굳이 코드 예제 필요 없을 듯
		
		// jpql fetch join
		// fetch join 은 엔티티를 select 해야 함 → select new(DTO) 와 함께 사용 불가
		// 목적: 연관된 엔티티(member-team)를 쿼리 한 번에 같이 로딩 (N+1 문제 해결)
		String jpql20 = "select m from Member m left join fetch m.team t";
		
		List<MemberV2> list18 = em.createQuery(jpql20, member).getResultList();
		
		System.out.println("엔티티 fetch join 예제");
		System.out.println(attribute);
		
		for (MemberV2 m : list18)
		{
			System.out.println("SQL 이 매번 나가는지 확인"); // left join fetch -> left join 으로 바꾸면 sql 이 매번 나감
			// m.getTeam() 호출 시 추가 쿼리 없이 이미 로딩된 team 반환
			String tuple = String.format("%-15d | %-15s | %-15s | %-15s",
			                             m.getId(),
			                             m.getName(),
			                             m.getTeam() != null ? m.getTeam().getId().toString() : "null",
			                             m.getTeam() != null ? m.getTeam().getName() : "null");
			System.out.println(tuple);
		}
		
		// 컬렉션을 페치 조인으로 조회
		String jpql21 = "select t from Team t left join fetch t.memberV2List";
		
		List<Team> list19 = em.createQuery(jpql21, Team.class).getResultList();
		
		System.out.println("컬렉션 fetch join 예제");
		
		for (Team t : list19)
		{
			System.out.println("SQL 이 매번 나가는지 확인"); // left join fetch -> left join 으로 바꾸면 sql 이 매번 나감
			System.out.println("t.getId() = " + t.getId());
			System.out.println("t.getName() = " + t.getName());
			for (MemberV2 m : t.getMemberV2List())
			{
				System.out.println("  m.getId() = " + m.getId() + ", m.getName() = " + m.getName());
			}
		}
		
		// Named 쿼리
		// 미리 쓸 jpql 선언하고 사용
		List<MemberV2> list20 = em.createNamedQuery("Member.findByUsername", member)
		                          .setParameter("name", "MemberV2-1")
		                          .getResultList();
		
		System.out.println("Named 쿼리");
		for (MemberV2 m : list20)
		{
			System.out.println("m.getId() = " + m.getId());
			System.out.println("m.getName() = " + m.getName());
		}
		
		// jpql 벌크 연산
		EntityTransaction tx = em.getTransaction();
		
		String jpql22 = "update Member m set m.age = m.age + :ageMount";
		
		System.out.println("jpql 벌크 연산");
		try
		{
			tx.begin();
			List<MemberV2> list21 = em.createQuery(jpql9, MemberV2.class).getResultList();
			
			String attribute3 = String.format("%-15s | %-15s | %-15s",
			                                  "member_id",
			                                  "member_name",
			                                  "member_age"
			                                 );
			System.out.println("age 변경 전");
			System.out.println(attribute3);
			
			for (MemberV2 m : list21)
			{
				String tuple = String.format("%-15d | %-15s | %-15d",
				                             m.getId(),
				                             "member_name",
				                             m.getAge()
				                            );
				System.out.println(tuple);
			}
			
			int mount = em.createQuery(jpql22)
			              .setParameter("ageMount", 10L)
			              .executeUpdate();
			
			System.out.println("영향 받은 엔티티 수 : " + mount);
			em.clear(); // 벌크 연산 후 1차 캐시 초기화 -> 이후 조회 시 DB에서 새로 가져옴

			List<MemberV2> list22 = em.createQuery(jpql9, MemberV2.class).getResultList();
			System.out.println("age 변경 후");
			System.out.println(attribute3);
			
			for (MemberV2 m : list22)
			{
				String tuple = String.format("%-15d | %-15s | %-15d",
				                             m.getId(),
				                             "member_name",
				                             m.getAge()
				                            );
				System.out.println(tuple);
			}
			
			em.clear(); // 벌크 연산 이후 1차 영속성 컨텍스트 초기화 필요
			tx.commit();
		}
		catch (Exception e)
		{
			log.error("트랜잭션 중 예외 발생", e);
			tx.rollback();
		}
		finally
		{
			System.out.println("finally 실행");
		}
	}
}
