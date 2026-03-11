package hellojpa.chapter.chapter4_.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.antlr.v4.runtime.misc.NotNull;

// 엔티티가 아니다 -> 테이블이 안 만들어 진다.
// 값 타입의 필드는 펼쳐져서 필드 하나하나가 @Embedded 한 엔티티가 만드는 테이블의 컬럼이 된다.
// 값 타입의 필드를 final 로 만드는 것은 불가능 -> JPA 는 상속 가능한 기본 생성자 필요
@Embeddable // 값 타입 정의 임을 표시 다른 엔티티의 필드 타입으로 사용되기 위한 클래스임을 표시함
@Getter // lombok 어노테이션 Getter 는 값 변경이 없기 때문에 모두 열려도 무방하다.
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부 빈 객체 생성은 막고 JPA 는 기본 생성자가 필요하기 때문에 
@Builder // 빌더 패턴 사용
@AllArgsConstructor // 생성자를 통해 값을 넣어야 함으로 필요합니다, 빌더랑 같이 사용, 생성시 무조건 값이 있는 상태로만 생성하도록 유도
@EqualsAndHashCode  // 값 타입은 필드값이 같으면 같은 객체로 보기 위해 필수입니다
public class Address {
	
	// 실제 DB 에는 필드 하나하나 컬럼으로 저장되는데 그 때 column name 을 정해준다.
	@Column(name = "zipcode")
	@NonNull
	private Integer zipcode;
	
	@Column(name = "location_name")
	@NonNull
	private String locationName;
}
