-- H2 DB 에 쓸 SQL 저장 못해서 만듬

-- 테이블 목록 출력
show tables;

-- 멤버 테이블의 모든 항목 출력
select *
from member
limit 100;

-- 조건에 맞는 row 출력
select *
from member m
where m.team_id is null;

-- 정렬 예제
select *
from member
order by member_age asc;

select *
from member
order by member_age desc;

select *
from team
limit 100;

select *
from locker
limit 100;

select *
from member_favorite_fruit
limit 100;

-- group by 예제
select location_name, count(location_name)
from member
group by location_name
having count(location_name) < 5;

select location_name, count(location_name)
from member
group by location_name
having count(location_name) >= 5;

-- 조인 예제
-- 세타 조인(theta join) m.team_id = null 이면 결과값에 없다.
select m.member_id, m.member_name, m.team_id, t.team_id, t.team_name
from member m, team t
where m.team_id = t.team_id
order by m.member_id;

-- 내부 조인(inner join) 연관관계의 두 엔티티에 모두 데이터가 존재해야 나온다. null 이면 생략됨
select m.member_id, m.member_name, m.team_id, t.team_id, t.team_name
from member m inner join team t on m.team_id = t.team_id;

-- 외부 조인(outer join) 외부 조인은 / 왼쪽 외부 조인 / 오른쪽 외부 조인 / 전체 외부 조인 이 있으며 왼쪽 외부 조인을 주로 쓴다.
-- 왼쪽 외부 조인은 왼쪽에 있는 값은 모두 출력하고 오른쪽 값이랑 매칭되는 것이 없으면 null 로 채운다.
select m.member_id, m.member_name, m.team_id, t.team_id, t.team_name
from member m left outer join team t on m.team_id = t.team_id;

-- 전체 외부 조인
-- H2 는 full join 을 지원하지 않음 -> union 연산 사용
select m.member_id, m.member_name, m.team_id, t.team_id, t.team_name
from member m full outer join team t on m.team_id = t.team_id
order by m.member_id;

-- 교차 조인(cross join) 모든 경우의 수를 보여줌
-- 대부분의 경우 결과값만 많이 나오고 유효한 값이 아니기 때문에 반드시 필요한 특수한 경우인가 판단 먼저할 것
select m.member_id, m.member_name, m.team_id, t.team_id, t.team_name
from member m cross join team t;

-- 조인 on 절
select m.member_id, m.member_name, m.team_id, t.team_id, t.team_name
from member m join team t on m.team_id = t.team_id and m.member_id >= 5;

-- 서브 쿼리
select *
from member m
where m.member_age > (select avg(m.member_age)
					  from member m);

-- 조건식 case 식
select m.member_id, m.member_name, m.member_age,
	case
		when m.member_age <= 10 then '어린이'
		when m.member_age >= 60 then '노인'
		else '일반인'
	end
from member m;

-- 조건식 coalesce
select m.member_id, m.member_name, coalesce(m.team_id, 0)
from member m;

-- 조건식 nullif
select m.member_id, m.member_name, nullif(m.team_id, 1)
from member m;