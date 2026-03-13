package hellojpa.chapter.chapter4_10.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {
	
	private String name;
	private Long   age;
	
	public MemberDTO(String name, Long age) {
		this.name = name;
		this.age = age;
	}
}
