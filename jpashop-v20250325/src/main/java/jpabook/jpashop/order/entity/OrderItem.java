package jpabook.jpashop.order.entity;

import jpabook.jpashop.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jpabook.jpashop.item.entity.Item;
import jakarta.persistence.*;

@Entity(name = "OrderItem")
@Table(name = "order_item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_item_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;      //주문 상품
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;    //주문
	
	@Column(name = "order_item_per_price")
	private Integer orderPrice; // 개당 가격
	
	@Column(name = "order_item_quantity")
	private Integer quantity; //주문 수량
	
	//==생성 메서드==//
	@Builder
	public OrderItem(Integer orderPrice, Integer quantity) {
		this.orderPrice = orderPrice;
		this.quantity = quantity;
	}
	
	// 연관관계 메소드
	public void addOrderItem(Item item, Order order) {
		this.item = item;
		this.order = order;
		item.getOrderItems().add(this);
		order.getOrderItems().add(this);
	}
}