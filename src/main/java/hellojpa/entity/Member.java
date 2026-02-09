package hellojpa.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity(name = "Member")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "member_id")
	private Long id;
	
	@Column(name = "member_name")
	private String name;
	
	@Embedded // 값 타입
	private Address address;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "zipcode", column = @Column(name = "work_zipcode"))
	}) // 값 타입을 새로 매핑
	private Address workAddress;
	
	@Builder
	public Member(Long id, String name, Address address) {
		this.id = id;
		this.name = name;
		this.address = address;
	}
}
