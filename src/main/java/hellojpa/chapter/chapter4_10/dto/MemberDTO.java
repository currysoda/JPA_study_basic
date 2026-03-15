package hellojpa.chapter.chapter4_10.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberDTO {
	
	private String name;
	private Long   age;
	
	public MemberDTO(String name, Long age) {
		this.name = name;
		this.age = age;
	}
}
