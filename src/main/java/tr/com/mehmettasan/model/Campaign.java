package tr.com.mehmettasan.model;

import java.math.BigDecimal;

public class Campaign {
	String campaignName;
	Category category;
	BigDecimal discount;
	int itemCount;
	DiscountType discountType;
	
	public Campaign(String campaignName,Category category,BigDecimal discount,int itemCount,DiscountType discountType) {
		this.campaignName = campaignName;
		this.category = category;
		this.discount = discount;
		this.itemCount = itemCount;
		this.discountType = discountType;
	}
	
}
