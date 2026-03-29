package jpabook.jpashop.order.entity;

import java.util.UUID;
import jpabook.jpashop.address.Address;
import jpabook.jpashop.delivery.Delivery;
import jpabook.jpashop.delivery.DeliveryStatus;
import jpabook.jpashop.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "orders",
       uniqueConstraints = @UniqueConstraint(
	       name = "uk_order_number",        // 제약 조건 이름
	       columnNames = {"order_number"}
       ))
@Entity(name = "Order")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
	
	@Id
	@GeneratedValue
	@Column(name = "order_id")
	private Long orderId; // DB 용 id
	
	@Column(name = "order_number")
	private String orderNumber; // 고객이 볼 주문번호
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member; //주문 회원
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<OrderItem> orderItems = new ArrayList<>();
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "delivery_id")
	private Delivery delivery; //배송정보
	
	private LocalDateTime orderDate; //주문시간
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status; //주문상태 [ORDER, CANCEL]
	
	@Builder
	public Order(String orderNumber, Member member) {
		this.orderDate = LocalDateTime.now();
		this.orderNumber = orderNumber;
		this.member = member;
		this.status = OrderStatus.ORDER;
	}
	
	// 학습용이니 uuid 사용
	public void setOrderNumber() {
		this.orderNumber = UUID.randomUUID().toString();
	}
	
	//==연관관계 메서드==//
	public void setMember(Member member) {
		this.member = member;
		member.getOrders().add(this);
	}
	
	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}
	
	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
		delivery.setOrder(this);
	}
	
	//==생성 메서드==//
	public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
		Order order = new Order();
		order.setMember(member);
		order.setDelivery(delivery);
		for (OrderItem orderItem : orderItems)
		{
			order.addOrderItem(orderItem);
		}
		order.setStatus(OrderStatus.ORDER);
		order.setOrderDate(LocalDateTime.now());
		return order;
	}
	
	//==비즈니스 로직==//
	
	/**
	 * 주문 취소
	 */
	public void cancel() {
		if (delivery.getStatus() == DeliveryStatus.COMP)
		{
			throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
		}
		
		this.setStatus(OrderStatus.CANCEL);
		for (OrderItem orderItem : orderItems)
		{
			orderItem.cancel();
		}
	}
	
	//==조회 로직==//
	
	/**
	 * 전체 주문 가격 조회
	 */
	public int getTotalPrice() {
		int totalPrice = 0;
		for (OrderItem orderItem : orderItems)
		{
			totalPrice += orderItem.getTotalPrice();
		}
		return totalPrice;
	}
	
}