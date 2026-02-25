package hellojpa.chapter.chapter4_.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Locker")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "locker")
public class Locker extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "locker_id")
	private Long id;
	
	@Column(name = "locker_name", nullable = false)
	@Setter
	private String name;
	
	// 일대일 매핑
	@OneToOne(mappedBy = "locker", fetch = FetchType.LAZY)
	private MemberV2 memberV2;
	
	@Builder
	public Locker(String name) {
		this.name = name;
	}
}
