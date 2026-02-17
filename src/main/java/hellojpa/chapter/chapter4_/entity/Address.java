package hellojpa.chapter.chapter4_.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 엔티티가 아니다 -> 테이블이 안 만들어 진다.
// 값 타입의 필드는 펼쳐져서 필드 하나하나가 @Embedded 한 엔티티가 만드는 테이블의 컬럼이 된다.
@Embeddable // 값 타입 임을 표시 다른 엔티티의 필드 타입으로 사용되기 위한 클래스임을 표시함
@Getter // lombok 어노테이션 Getter 는 값 변경이 없기 때문에 모두 열려도 무방하다.
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부 빈 객체 생성은 막고 JPA 는 기본 생성자가 필요하기 때문에
@Builder // 빌더 패턴 사용
@AllArgsConstructor // 생성자를 통해 값을 넣어야 하므로 필요합니다, 빌더랑 같이 사용
@EqualsAndHashCode  // 값 타입은 필드값이 같으면 같은 객체로 보기 위해 필수입니다
public class Address {
	
	@Column(name = "zipcode")
	private int zipcode;
	
	@Column(name = "location_name")
	private String locationName;
	
	// 생성자는 어노테이션이 만들어준다.
	// public Address(int zipcode) {
	// 	this.zipcode = zipcode;
	// }
	
}
