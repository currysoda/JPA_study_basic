package jpabook.jpashop.category.entity;

import lombok.AccessLevel;
import lombok.Getter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Category")
@Table(name = "category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
	
	@Id
	@GeneratedValue
	@Column(name = "category_id")
	private Long id;
	
	@Column(name = "category_name")
	@Setter
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@Setter
	private Category parent;
	
	@OneToMany(mappedBy = "category")
	private List<CategoryItem> categoryItems = new ArrayList<>();
	
	@OneToMany(mappedBy = "parent")
	private List<Category> child = new ArrayList<>();
	
	//==연관관계 메서드==//
	public void addChildCategory(Category child) {
		this.child.add(child);
		child.setParent(this);
	}
	
}