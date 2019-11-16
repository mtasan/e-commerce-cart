package tr.com.mehmettasan.model;

import java.math.BigDecimal;

public class Delivery implements ICost{
	Cart cart;
	
	public Delivery(Cart cart) {
		super();
		this.cart = cart;
	}
	
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public BigDecimal calculateTotalCost() {
		// TODO Auto-generated method stub
		System.out.println(cart.cartList.size());
		return null;
	}

}
