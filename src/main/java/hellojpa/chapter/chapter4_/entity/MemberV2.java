package hellojpa.chapter.chapter4_.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class MemberV2 extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;
	
	@Column(name = "member_name", nullable = false)
	@Setter
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Setter
	private Role role;
	
	@Transient // JPA 에게 이 필드로 column 을 만들지 말라고 알려줌
	@Setter
	private String dontMakeColumn;
	
	// 연관관계의 주인
	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;
	
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
	public MemberV2(String name, Role role, String dontMakeColumn) {
		this.name = name;
		this.role = role;
		this.dontMakeColumn = dontMakeColumn;
	}
	
	// 클래스 내부 편의 메소드 (연관관계가 항상 같이 변하도록 준비)
	public void addTeam(Team team) {
	
	}
	
	public void removeTeam(Team team) {
	
	}
}
