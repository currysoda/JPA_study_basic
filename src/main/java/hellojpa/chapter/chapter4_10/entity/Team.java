package hellojpa.chapter.chapter4_10.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Team")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "team")
public class Team extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "team_id")
	private Long id;
	
	@Column(name = "team_name")
	@Setter
	private String name;
	
	// mappedby 는 반대편의 변수명을 작성한다
	// mappedby 가 있는 필드는 DB column 이 생성되지 않는다. => 자바 프로그래밍에서 필요해서 쓴다는 뜻
	@OneToMany(mappedBy = "team", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private final List<MemberV2> memberV2List = new ArrayList<>();
	
	@Builder
	public Team(String name) {
		this.name = name;
	}
}
