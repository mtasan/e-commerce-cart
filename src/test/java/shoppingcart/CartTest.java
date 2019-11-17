package shoppingcart;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import tr.com.mehmettasan.business.DeliveryCostCalculator;
import tr.com.mehmettasan.model.Campaign;
import tr.com.mehmettasan.model.Cart;
import tr.com.mehmettasan.model.Category;
import tr.com.mehmettasan.model.Coupon;
import tr.com.mehmettasan.model.DiscountType;
import tr.com.mehmettasan.model.Product;

import static org.junit.Assert.assertEquals;

public class CartTest {
	private Category foodCategory;
	private Category electronicCategory;
	private Product armut;
	private Product mp3;
	private Product laptop;
	private Product tablet;
	private Cart cart;
	DiscountType discountType;
	@Before
	public void before() throws Exception {
		foodCategory = new Category("food");
		electronicCategory = new Category("electronic");

		armut = new Product("Armut", new BigDecimal("100.0"), foodCategory);
		mp3 = new Product("MP3 Calar", new BigDecimal("150.0"), electronicCategory);
		laptop = new Product("Tohshiba", new BigDecimal("100.0"), electronicCategory);
		tablet = new Product("Tablet", new BigDecimal("150.0"), electronicCategory);
		
		cart = new Cart();

	}

	@Test
	public void applyCampaignAmount() {
		cart.addItem(armut, 3);
		cart.addItem(mp3, 1);
		cart.addItem(laptop, 5);
		cart.addItem(tablet, 2);
		List<Campaign> campaignList = new ArrayList();
		
		Campaign campaign1 = new Campaign("20 tl indirim",foodCategory,new BigDecimal(20.0),2,discountType.AMOUNT);
		campaignList.add(campaign1);
		cart.applyDiscounts(campaignList);
		assertEquals(20,cart.getCampaignDiscount().intValue());	
	}
	
	@Test
	public void applyCampaignRate() {
		cart.addItem(armut, 3);
		cart.addItem(mp3, 1);
		cart.addItem(laptop, 5);
		cart.addItem(tablet, 2);
		List<Campaign> campaignList = new ArrayList();
		
		Campaign campaign1 = new Campaign("Yuzde 30 indirim",foodCategory,new BigDecimal(30.0),2,discountType.RATE);
		campaignList.add(campaign1);
		cart.applyDiscounts(campaignList);
		assertEquals(90,cart.getCampaignDiscount().intValue());	
	}
	 
	@Test
	public void applyCampaignAmountCountControl() {
		cart.addItem(armut, 3);
		cart.addItem(mp3, 1);
		cart.addItem(laptop, 5);
		cart.addItem(tablet, 2);
		List<Campaign> campaignList = new ArrayList();
		
		Campaign campaign1 = new Campaign("20 tl indirim",foodCategory,new BigDecimal(20.0),5,discountType.AMOUNT);
		campaignList.add(campaign1);
		cart.applyDiscounts(campaignList);
		assertEquals(0,cart.getCampaignDiscount().intValue());	
	}
	
	@Test
	public void applyCampaignRateCountControl() {
		cart.addItem(armut, 3);
		cart.addItem(mp3, 1);
		cart.addItem(laptop, 5);
		cart.addItem(tablet, 2);
		List<Campaign> campaignList = new ArrayList();
		
		Campaign campaign1 = new Campaign("Yuzde 30 indirim",foodCategory,new BigDecimal(30.0),5,discountType.RATE);
		campaignList.add(campaign1);
		cart.applyDiscounts(campaignList);
		assertEquals(0,cart.getCampaignDiscount().intValue());	
	}
	
	@Test
	public void applyCouponRate() {
		cart.addItem(armut, 3);
		cart.addItem(mp3, 1);
		cart.addItem(laptop, 5);
		cart.addItem(tablet, 2);
	
		Coupon coupon = new Coupon("Yüzde 10 indirim",BigDecimal.valueOf(100),BigDecimal.valueOf(10),discountType.RATE);
		cart.applyCoupon(coupon);
		
		assertEquals(125,cart.getCouponDiscount().intValue());	
	}
	
	@Test
	public void applyCouponAmount() {
		cart.addItem(armut, 3);
		cart.addItem(mp3, 1);
		cart.addItem(laptop, 5);
		cart.addItem(tablet, 2);

		
		Coupon coupon = new Coupon("Yüzde 10 indirim",BigDecimal.valueOf(100),BigDecimal.valueOf(65),discountType.AMOUNT);
		cart.applyCoupon(coupon);
		
		assertEquals(65,cart.getCouponDiscount().intValue());	
	}
	
	@Test
	public void applyCouponRateLessThanMinPurchaseCount() {
		cart.addItem(armut, 3);
		cart.addItem(mp3, 1);
		cart.addItem(laptop, 5);
		cart.addItem(tablet, 2);

		
		Coupon coupon = new Coupon("Yüzde 10 indirim",BigDecimal.valueOf(2000),BigDecimal.valueOf(10),discountType.RATE);
		cart.applyCoupon(coupon);
		
		assertEquals(0,cart.getCouponDiscount().intValue());	
	}
	
	@Test
	public void applyCouponAmountLessThanMinPurchaseCount() {
		cart.addItem(armut, 3);
		cart.addItem(mp3, 1);
		cart.addItem(laptop, 5);
		cart.addItem(tablet, 2);

		
		Coupon coupon = new Coupon("Yüzde 10 indirim",BigDecimal.valueOf(2000),BigDecimal.valueOf(10),discountType.AMOUNT);
		cart.applyCoupon(coupon);
		
		assertEquals(0,cart.getCouponDiscount().intValue());	
	}
	
	@Test
	public void applyMultipleCampaign() {
		cart.addItem(armut, 3);
		cart.addItem(mp3, 1);
		cart.addItem(laptop, 5);
		cart.addItem(tablet, 2);

		List<Campaign> campaignList = new ArrayList();
		Campaign campaign1 = new Campaign("20 tl indirim",foodCategory,new BigDecimal(20.0),2,discountType.AMOUNT);
		Campaign campaign2 = new Campaign("Yuzde 30 indirim",electronicCategory,new BigDecimal(30.0),2,discountType.RATE);
		
		campaignList.add(campaign1);
		campaignList.add(campaign2);
		cart.applyDiscounts(campaignList);

		
		assertEquals(285,cart.getCampaignDiscount().intValue());	
	}
	
	@Test
	public void applyCouponAfterCampaign() {
		cart.addItem(armut, 3);
		cart.addItem(mp3, 1);
		cart.addItem(laptop, 5);
		cart.addItem(tablet, 2);

		List<Campaign> campaignList = new ArrayList();
		Campaign campaign1 = new Campaign("20 tl indirim",foodCategory,new BigDecimal(20.0),2,discountType.AMOUNT);
		Campaign campaign2 = new Campaign("Yuzde 30 indirim",electronicCategory,new BigDecimal(30.0),2,discountType.RATE);
		
		campaignList.add(campaign1);
		campaignList.add(campaign2);
		cart.applyDiscounts(campaignList);
		
		Coupon coupon = new Coupon("Yüzde 10 indirim",new BigDecimal(100),new BigDecimal(10),discountType.RATE);
		cart.applyCoupon(coupon);
		
		assertEquals(new BigDecimal("96.50"),cart.getCouponDiscount());	
	}
	
	@Test
	public void calculateDeliveryCost() {
		cart.addItem(armut, 3);
		cart.addItem(mp3, 1);
		cart.addItem(laptop, 5);
		cart.addItem(tablet, 2);

		List<Campaign> campaignList = new ArrayList();
		Campaign campaign1 = new Campaign("20 tl indirim",foodCategory,new BigDecimal(20.0),2,discountType.AMOUNT);
		Campaign campaign2 = new Campaign("Yuzde 30 indirim",electronicCategory,new BigDecimal(30.0),2,discountType.RATE);
		
		campaignList.add(campaign1);
		campaignList.add(campaign2);
		cart.applyDiscounts(campaignList);
		
		Coupon coupon = new Coupon("Yüzde 10 indirim",new BigDecimal(100),new BigDecimal(10),discountType.RATE);
		cart.applyCoupon(coupon);

		DeliveryCostCalculator deliveryCC = new DeliveryCostCalculator(new BigDecimal(13),new BigDecimal(3),new BigDecimal("2.99"));
		
		assertEquals(new BigDecimal("40.99"),deliveryCC.calculateCost(cart));	
	}
	
	@Test
	public void cartPrint() {
		cart.addItem(armut, 3);
		cart.addItem(mp3, 1);
		cart.addItem(laptop, 5);
		cart.addItem(tablet, 2);

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
		cart.print();
		

	}

}
