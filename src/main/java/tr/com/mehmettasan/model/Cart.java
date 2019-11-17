package tr.com.mehmettasan.model;

import java.util.List;
import java.util.Map.Entry;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Cart implements ICart {
 
	HashMap<Product, Integer> cartList = new HashMap<Product, Integer>();
	BigDecimal totalCost = new BigDecimal(BigInteger.ZERO, 2);

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	BigDecimal campaignDiscount = new BigDecimal(BigInteger.ZERO, 2);

	public BigDecimal getCampaignDiscount() {
		return campaignDiscount;
	}

	public void setCampaignDiscount(BigDecimal campaignDiscount) {
		this.campaignDiscount = campaignDiscount;
	}

	public BigDecimal getCouponDiscount() {
		return couponDiscount;
	}

	public void setCouponDiscount(BigDecimal couponDiscount) {
		this.couponDiscount = couponDiscount;
	}

	public BigDecimal getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(BigDecimal totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public BigDecimal getDeliveryCost() {
		return deliveryCost;
	}

	public void setDeliveryCost(BigDecimal deliveryCost) {
		this.deliveryCost = deliveryCost;
	}

	BigDecimal couponDiscount;
	BigDecimal totalDiscount;
	BigDecimal deliveryCost;

	public Cart() {
	}

	public void addItem(Product product, int quantity) {
		this.cartList.put(product, quantity);
		BigDecimal itemCost = product.price.multiply(new BigDecimal(quantity));
		this.totalCost = this.totalCost.add(itemCost);
	}

	public HashMap getCartList() {
		return cartList;
	}

	public void applyDiscounts(List<Campaign> campaignList) {
		BigDecimal hundred = new BigDecimal(100);
		String discountCategoryTitle = "";
		int discountCategoryGroupCount = 0;
		BigDecimal calculatedTotalCost = new BigDecimal(BigInteger.ZERO, 2);
		BigDecimal amount = new BigDecimal(0);
		BigDecimal categoryPrice = new BigDecimal(BigInteger.ZERO, 2);
		List<HashMap<String, BigDecimal>> postDiscountCampaignList = new ArrayList();

		// Campaign List
		for (Campaign campaign : campaignList) {
			discountCategoryTitle = campaign.category.title;
			discountCategoryGroupCount = 0;
			categoryPrice = new BigDecimal(BigInteger.ZERO, 2);
			calculatedTotalCost = new BigDecimal(BigInteger.ZERO, 2);
			// Categories in cart
			for (Entry<Product, Integer> entry : this.cartList.entrySet()) {
				if (discountCategoryTitle.equals(entry.getKey().category.title)) {
					discountCategoryGroupCount += entry.getValue();
					categoryPrice = categoryPrice.add(entry.getKey().price.multiply(new BigDecimal(entry.getValue())));
				}
			}
			// Apply Discount Rules
			// System.out.println("Item Category : " + discountCategoryTitle + " Count : " +
			// discountCategoryGroupCount);
			if (discountCategoryGroupCount > campaign.itemCount) {
				if (DiscountType.RATE == campaign.discountType) {
					amount = campaign.discount.multiply(categoryPrice).divide(hundred);
					calculatedTotalCost = this.totalCost.subtract(amount);
				} else {
					amount = categoryPrice.subtract(campaign.discount);
					if (this.totalCost.compareTo(campaign.discount) == 1) // todo
						calculatedTotalCost = this.totalCost.subtract(campaign.discount);
				}
				HashMap map = new HashMap();
				map.put(campaign.campaignName, calculatedTotalCost);
				postDiscountCampaignList.add(map);

			}

		}

		// Find best price for customer
		String bestCampaign = "";
		BigDecimal bestPrice = new BigDecimal(0);
		for (int i = 0; i < postDiscountCampaignList.size(); i++) {

			for (Entry<String, BigDecimal> entry : postDiscountCampaignList.get(i).entrySet()) {
				//System.out.println(" ");
				//System.out.println("--------------------------");
				//System.out.println(entry.getKey());
				//System.out.println(entry.getValue());

				if (i == 0) {
					bestCampaign = entry.getKey();
					bestPrice = entry.getValue();
				} else {
					if (entry.getValue().compareTo(bestPrice) == -1) {
						bestPrice = entry.getValue();
						bestCampaign = entry.getKey();
					}

				}
			}
		}
		if(postDiscountCampaignList.size()==0)
			bestPrice = this.totalCost;
		this.campaignDiscount = this.totalCost.subtract(bestPrice);

	}


	public void print() {
		List sortedItems = new ArrayList(cartList.keySet());
		Collections.sort(sortedItems);

		sortedItems.forEach(item -> {
			Product prd = (Product) item;
			System.out.println("Category " + prd.category.title + " -- Product "+prd.title 
					+ " -- Quantity " + cartList.get(item) + " -- Price " + prd.price);
		});

		System.out.println("========================");
		System.out.println("Total Price " + this.totalCost);
		System.out.println("Campaign Discount " + this.campaignDiscount);
		System.out.println("Coupon Discount " + this.couponDiscount);
		System.out.println("Total Discount " + this.couponDiscount.add(this.campaignDiscount));
		System.out.println("Total Amount " + (this.totalCost.subtract(this.campaignDiscount)).subtract(this.couponDiscount));
		System.out.println("Delivery Cost " + this.deliveryCost);
	}

	@Override
	public void applyDiscount(Discount discount) {
		Coupon coupon = (Coupon) discount;
		BigDecimal firsttDiscount = this.totalCost.subtract(this.campaignDiscount);
		BigDecimal hundred = new BigDecimal(100);
		// cart total price must be grater than coupon price
		if (firsttDiscount.compareTo(coupon.minPurchaseAmount) == 1) {
			if (DiscountType.RATE == coupon.discountType) {
				this.couponDiscount = coupon.discount.multiply(firsttDiscount).divide(hundred);
			} else {
				this.couponDiscount = coupon.discount;
			}
		}else {
			   this.couponDiscount = BigDecimal.valueOf(0);
		}
		
	}

}
