package tr.com.mehmettasan.shoppingcart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tr.com.mehmettasan.business.DeliveryCostCalculator;
import tr.com.mehmettasan.model.Campaign;
import tr.com.mehmettasan.model.Cart;
import tr.com.mehmettasan.model.Category;
import tr.com.mehmettasan.model.Coupon;
import tr.com.mehmettasan.model.DiscountType;
import tr.com.mehmettasan.model.Product;

public class Test {  

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Category foodCategory = new Category("food");
		Category electronicCategory = new Category("electronic");

		
		Product armut = new Product("Armut",new BigDecimal("100.0"),foodCategory);
		Product mp3 = new Product("MP3 Calar",new BigDecimal("150.0"),electronicCategory);
		
		Product laptop = new Product("Tohshiba",new BigDecimal("100.0"),electronicCategory);
		Product tablet = new Product("Tablet",new BigDecimal("150.0"),electronicCategory);
		
		Cart cart = new Cart();
		cart.addItem(armut, 3);
		cart.addItem(mp3, 1);
		cart.addItem(laptop, 5);
		cart.addItem(tablet, 2);
		
		HashMap chartList = cart.getCartList();
		
		/*
		chartList.forEach((k,v)->System.out.println("Item : " + ((Product) k).getTitle() +
													"Price : " + ((Product) k).getPrice() + " Count : " + v));
		*/
		DiscountType discountType = DiscountType.AMOUNT;
		List<Campaign> campaignList = new ArrayList();
		
		Campaign campaign1 = new Campaign("20 tl indirim",foodCategory,new BigDecimal(20.0),2,discountType.AMOUNT);
		Campaign campaign2 = new Campaign("Yuzde 30 indirim",electronicCategory,new BigDecimal(30.0),2,discountType.RATE);
		
		campaignList.add(campaign1);
		campaignList.add(campaign2);
		cart.applyDiscounts(campaignList);
		
		
		Coupon coupon = new Coupon("Yüzde 10 indirim",new BigDecimal(100),new BigDecimal(10),discountType.RATE);
		cart.applyCoupon(coupon);
		
		
		DeliveryCostCalculator deliveryCC = new DeliveryCostCalculator(new BigDecimal(13),new BigDecimal(3),new BigDecimal("2.99"));
		cart.setDeliveryCost(deliveryCC.calculateCost(cart));
		
		/*
		System.out.println("Total Chart " +cart.getTotalCost());
		System.out.println("Campaign Discount " +cart.getCampaignDiscount());
		System.out.println("Coupon Discount " +cart.getCouponDiscount());
		System.out.println("Delivery Cost " +deliveryCC.calculateCost(cart));
		*/
		
		cart.print();

	}

}
