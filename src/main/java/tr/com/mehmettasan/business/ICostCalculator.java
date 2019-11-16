package tr.com.mehmettasan.business;

import java.math.BigDecimal;
import tr.com.mehmettasan.model.Cart;


public interface ICostCalculator {
	public BigDecimal calculateCost(Cart cart);

}
 