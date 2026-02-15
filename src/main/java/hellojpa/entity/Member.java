package hellojpa.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity(name = "Member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;
	
	@Column(name = "member_name", nullable = false)
	@Setter
	private String name;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Transient // JPA 에게 이 필드로 column 을 만들지 말라고 알려줌
	private String dontMakeColumn;
	
	// @Embedded // 값 타입
	// private Address address;
	//
	// @Embedded // 값 타입 구조인데 column_name 을 다르게 해야하는 경우
	// @AttributeOverrides({
	// 	@AttributeOverride(name = "zipcode", column = @Column(name = "work_zipcode")),
	// 	@AttributeOverride(name = "locationName", column = @Column(name = "work_locationName"))
	// }) // 값 타입을 새로 매핑
	// private Address workAddress;
	
	// id 는 자동 생성 전략
	@Builder
	public Member(String name) {
		// this.id = id;
		this.name = name;
		// this.address = address;
	}
}
