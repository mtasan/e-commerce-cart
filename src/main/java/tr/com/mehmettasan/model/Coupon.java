package tr.com.mehmettasan.model;

import java.math.BigDecimal;

public class Coupon {

	String couponName;
	BigDecimal minPurchaseAmount;
	BigDecimal discount;
	DiscountType discountType;

	public Coupon(String couponName, BigDecimal minPurchaseAmount, BigDecimal discount, DiscountType discountType) {
		this.couponName = couponName;
		this.minPurchaseAmount = minPurchaseAmount;
		this.discount = discount;
		this.discountType = discountType;
	}

}
