package jpabook.jpashop.item.entity;

import jpabook.jpashop.common.BaseEntitiy;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;

import jpabook.jpashop.category.entity.Category;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
public abstract class Item extends BaseEntitiy {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private Long id;
	
	@Column(name = "item_name")
	private String name;
	
	@Column(name = "item_price")
	private int price;
	
	@Column(name = "item_stock_quantity")
	private int stockQuantity;
	
	@ManyToMany(mappedBy = "items")
	private List<Category> categories = new ArrayList<Category>();
	
	//==비즈니스 로직==//
	public void addStock(int quantity) {
		this.stockQuantity += quantity;
	}
	
	public void removeStock(int quantity) {
		int restStock = this.stockQuantity - quantity;
		if (restStock < 0)
		{
			throw new NotEnoughStockException("need more stock");
		}
		this.stockQuantity = restStock;
	}
}