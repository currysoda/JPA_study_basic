package hellojpa.chapter.chapter4_.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
	
	// JPA 에게 이 필드로 column 을 만들지 말라고 알려줌
	@Transient
	@Setter
	private String dontMakeColumn;
	
	// DB CLob 타입에 매핑 문자, 문자열 타입으로 저장
	@Lob
	private String CLOB = "default string";
	
	// DB BLob 타입에 매핑 바이트 타입으로 저장
	@Lob
	private Long BLOB;
	
	// 다대일 매핑
	// 연관관계의 주인
	// JoinColumn 의 name 에는 DB 에서 실제로 사용되는 column name 을 넣어준다.
	@ManyToOne
	@JoinColumn(name = "team_id", // JOIN 할 DB column name 을 알려준다.
	            // 제약사항에 정보 넣어주기 기본 JPA 가 넣는 이름은 알아보기 힘들다.
	            foreignKey = @ForeignKey(name = "FK_MEMBER_TEAM",
	                                     foreignKeyDefinition = "FOREIGN KEY (team_id) REFERENCES team(team_id) ON DELETE CASCADE"))
	private Team mappedbyIsVariableName;
	
	// 일대일 매핑
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "locker_id", unique = true) // 1:1 매핑을 위해 unique 조건이 DB 에도 반영됨
	private Locker locker;
	
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
	public MemberV2(String name, Role role, String dontMakeColumn, String CLOB, Long BLOB) {
		this.name = name;
		this.role = role;
		this.dontMakeColumn = dontMakeColumn;
		this.CLOB = (CLOB != null) ? CLOB : "default String";
		this.BLOB = (BLOB != null) ? BLOB : 0L;
	}
	
	// 클래스 내부 편의 메소드 (연관관계가 항상 같이 변하도록 준비)
	public void addTeam(Team team) {
	
	}
	
	public void removeTeam(Team team) {
	
	}
}
