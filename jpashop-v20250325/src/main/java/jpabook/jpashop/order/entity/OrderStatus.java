package jpabook.jpashop.order.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {
	ORDER(1), CANCEL(2);
	
	private final Integer code;
	
	OrderStatus(Integer code) {
		this.code = code;
	}
}