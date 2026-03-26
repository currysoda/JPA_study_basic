package hellojpa.chapter.chapter4_10.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberAgeDTO {
	
	private Long   id;
	private String name;
	private Long   age;
	private String ageGroup;
	
	public MemberAgeDTO(Long id, String name, Long age, String ageGroup) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.ageGroup = ageGroup;
	}
}
