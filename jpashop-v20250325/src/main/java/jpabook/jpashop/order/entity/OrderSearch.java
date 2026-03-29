package jpabook.jpashop.order.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderSearch {
	
	//Getter, Setter
	private String      memberName;      //회원 이름
	private OrderStatus orderStatus;//주문 상태[ORDER, CANCEL]
	
}