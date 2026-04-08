package jpabook.jpashop.member.entity;

import jakarta.persistence.*;
import jpabook.jpashop.address.Address;
import jpabook.jpashop.common.BaseEntity;
import jpabook.jpashop.order.entity.Order;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Member")
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "member_id")
	private Long id;
	
	@Column(name = "member_name", unique = true)
	@Setter
	private String name;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "city", column = @Column(name = "member_city")),
		@AttributeOverride(name = "street", column = @Column(name = "member_street")),
		@AttributeOverride(name = "zipcode", column = @Column(name = "member_zipcode"))
	})
	@Setter
	private Address address;
	
	@OneToMany(mappedBy = "member")
	private List<Order> orders = new ArrayList<>();
	
	@Builder
	public Member(String name, Address address) {
		this.name = name;
		this.address = address;
	}
}