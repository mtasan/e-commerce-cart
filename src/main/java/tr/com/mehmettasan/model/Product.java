package tr.com.mehmettasan.model;

import java.math.BigDecimal;

public class Product implements Comparable<Product> {
	
	String title;
	BigDecimal price;
	public Category category;

	public Product(String title, BigDecimal price, Category category) {
		super();
		this.title = title;
		this.price = price;
		this.category = category;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public int compareTo(Product other) {
		return this.category.title.compareTo(other.category.title);
	}

}
