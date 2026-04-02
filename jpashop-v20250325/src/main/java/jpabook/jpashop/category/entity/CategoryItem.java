package jpabook.jpashop.category.entity;

import jpabook.jpashop.item.entity.Item;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Entity(name = "CategoryItem")
@Table(name = "category_item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_item_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;
	
	@Builder
	public CategoryItem(Category category, Item item) {
		this.category = category;
		this.item = item;
	}
}