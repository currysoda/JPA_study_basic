package hellojpa.chapter.chapter4_10.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberStatsDTO {
	
	private Long   count;
	private Long   sumAge;
	private Double avgAge;
	private Long   maxAge;
	private Long   minAge;
	
	public MemberStatsDTO(Long count, Long sumAge, Double avgAge, Long maxAge, Long minAge) {
		this.count = count;
		this.sumAge = sumAge;
		this.avgAge = avgAge;
		this.maxAge = maxAge;
		this.minAge = minAge;
	}
}
