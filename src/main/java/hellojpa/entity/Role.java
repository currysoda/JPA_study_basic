package hellojpa.entity;

import lombok.Getter;

@Getter
public enum Role {
	
	ADMIN(1), MEMBER(2);
	
	private final int code;
	
	Role(int i) {
		this.code = i;
	}
}
