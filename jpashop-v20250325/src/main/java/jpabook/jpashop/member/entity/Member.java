package jpabook.jpashop.member.entity;

import jakarta.persistence.*;
import jpabook.jpashop.address.Address;
import jpabook.jpashop.order.entity.Order;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;

@Entity(name = "Member")
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "member_id")
	private Long memberId;
	
	@Column(name = "member_name")
	private String memberName;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "city", column = @Column(name = "member_city")),
		@AttributeOverride(name = "street", column = @Column(name = "member_street")),
		@AttributeOverride(name = "zipcode", column = @Column(name = "member_zipcode"))
	})
	private Address address;
	
	@OneToMany(mappedBy = "member")
	private List<Order> orders = new ArrayList<>();
	
	@Builder
	public Member(Long memberId, String memberName, Address address) {
		this.memberId = memberId;
		this.memberName = memberName;
		this.address = address;
	}
}