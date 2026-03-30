package jpabook.jpashop.delivery.entity;

import jpabook.jpashop.address.Address;
import jpabook.jpashop.common.BaseEntitiy;
import jpabook.jpashop.order.entity.Order;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Table(name = "delivery")
public class Delivery extends BaseEntitiy {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "delivery_id")
	private Long id;
	
	@OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
	private Order order;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "city", column = @Column(name = "delivery_city")),
		@AttributeOverride(name = "street", column = @Column(name = "delivery_street")),
		@AttributeOverride(name = "zipcode", column = @Column(name = "delivery_zipcode"))
	})
	private Address address;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "delivery_status")
	private DeliveryStatus status; //ENUM [READY(준비), COMPLETE(배송)]
	
}