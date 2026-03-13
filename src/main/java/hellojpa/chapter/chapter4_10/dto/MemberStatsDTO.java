package hellojpa.chapter.chapter4_10.dto;

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
	
	public Long getCount() {
		return count;
	}
	
	public void setCount(Long count) {
		this.count = count;
	}
	
	public Long getSumAge() {
		return sumAge;
	}
	
	public void setSumAge(Long sumAge) {
		this.sumAge = sumAge;
	}
	
	public Double getAvgAge() {
		return avgAge;
	}
	
	public void setAvgAge(Double avgAge) {
		this.avgAge = avgAge;
	}
	
	public Long getMaxAge() {
		return maxAge;
	}
	
	public void setMaxAge(Long maxAge) {
		this.maxAge = maxAge;
	}
	
	public Long getMinAge() {
		return minAge;
	}
	
	public void setMinAge(Long minAge) {
		this.minAge = minAge;
	}
}
