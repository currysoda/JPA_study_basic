package jpabook.jpashop.order.entity;

import jakarta.annotation.Nonnull;
import java.util.UUID;
import jpabook.jpashop.common.BaseEntitiy;
import jpabook.jpashop.delivery.entity.Delivery;
import jpabook.jpashop.delivery.entity.DeliveryStatus;
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
public class Order extends BaseEntitiy {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	@Column(name = "order_date")
	private LocalDateTime orderDate; //주문시간
	
	@Column(name = "order_status")
	@Enumerated(EnumType.STRING)
	private OrderStatus status; //주문상태 [ORDER, CANCEL]
	
	@Builder
	public Order(@Nonnull Member member, Delivery delivery) {
		this.member = member;
		this.delivery = delivery;
		this.status = OrderStatus.ORDER;
		this.orderDate = LocalDateTime.now();
		makeOrderNumber();
	}
	
	// 학습용이니 uuid 사용
	// 주문번호는 생성시 바로 할당되고 변경되면 안됨
	private void makeOrderNumber() {
		if (orderNumber == null)
		{
			this.orderNumber = UUID.randomUUID().toString();
		}
		else
		{
			System.out.println("이미 주문번호가 존재합니다.");
		}
	}
	
	//==연관관계 메서드==//
	// 연관관계의 주인인 경우 or @OnetoOne
	public void addMember(Member member) {
		this.member = member;
		member.getOrders().add(this);
	}
	
	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
		delivery.setOrder(this);
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
		
		// this.setStatus(OrderStatus.CANCEL);
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