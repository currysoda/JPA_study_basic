package jpabook.jpashop.item.entity;

import jpabook.jpashop.common.BaseEntity;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.AccessLevel;
import lombok.Getter;

import jpabook.jpashop.category.entity.CategoryItem;
import jpabook.jpashop.order.entity.OrderItem;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

@Entity(name = "Item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public abstract class Item extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	@Setter(AccessLevel.NONE)
	private Long id;
	
	@Column(name = "item_name")
	private String name;
	
	@Column(name = "item_price")
	private Integer price;
	
	@Column(name = "item_stock_quantity")
	private Integer stockQuantity;
	
	@OneToMany(mappedBy = "item")
	@Setter(AccessLevel.NONE)
	private List<CategoryItem> categoryItems = new ArrayList<>();
	
	@OneToMany(mappedBy = "item")
	@Setter(AccessLevel.NONE)
	private List<OrderItem> orderItems = new ArrayList<>();
	
	
}