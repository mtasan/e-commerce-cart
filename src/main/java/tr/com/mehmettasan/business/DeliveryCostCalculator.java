package tr.com.mehmettasan.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import tr.com.mehmettasan.model.Cart;
import tr.com.mehmettasan.model.Product;

public class DeliveryCostCalculator implements ICostCalculator {
	public DeliveryCostCalculator(BigDecimal costPerDelivery, BigDecimal costPerProduct, BigDecimal fixedCost) {
		this.costPerDelivery = costPerDelivery;
		this.costPerProduct = costPerProduct;
		this.fixedCost = fixedCost;
	}

	BigDecimal costPerDelivery;
	BigDecimal costPerProduct;
	BigDecimal fixedCost;

	@Override
	public BigDecimal calculateCost(Cart cart) {
		int numberOfDeliveries = getDistinctCategoryCount(cart.getCartList());
		int numberOfProducts = getProductCount(cart.getCartList());
		BigDecimal categoryCost = new BigDecimal(0);
		BigDecimal productCost = new BigDecimal(0);
		
		categoryCost = this.costPerDelivery.multiply(new BigDecimal(numberOfDeliveries));
		productCost = this.costPerProduct.multiply(new BigDecimal(numberOfProducts));
		return categoryCost.add(productCost).add(fixedCost);
	}
	 
	public int getDistinctCategoryCount(HashMap<Product, Integer> cartList) {
		List<String> categories = new ArrayList<>();
		for (Entry<Product, Integer> entry : cartList.entrySet()) {
			categories.add(entry.getKey().category.title);
		}
		//System.out.println(categories.stream().distinct().count());
		return (int) (categories.stream().distinct().count());	
	}
	
	public int getProductCount(HashMap<Product, Integer> cartList) {
		//System.out.println(cartList.size());
		return cartList.size();
	}

}
