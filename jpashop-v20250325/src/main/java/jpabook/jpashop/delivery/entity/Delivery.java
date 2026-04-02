package jpabook.jpashop.delivery.entity;

import jpabook.jpashop.address.Address;
import jpabook.jpashop.common.BaseEntity;
import jpabook.jpashop.order.entity.Order;
import lombok.Builder;
import lombok.Getter;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Getter
@Table(name = "delivery")
public class Delivery extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "delivery_id")
	private Long id;
	
	@OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
	@Setter
	private Order order;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "city", column = @Column(name = "delivery_city")),
		@AttributeOverride(name = "street", column = @Column(name = "delivery_street")),
		@AttributeOverride(name = "zipcode", column = @Column(name = "delivery_zipcode"))
	})
	@Setter
	private Address address;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "delivery_status")
	@Setter
	private DeliveryStatus status; //ENUM [READY(준비), COMPLETE(배송)]
	
	@Builder
	public Delivery(Address address) {
		this.status = DeliveryStatus.READY;
		this.address = address;
	}
}