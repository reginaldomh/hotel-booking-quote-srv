package com.rmh.hotel.booking.quote.client;

import java.math.BigDecimal;

/**
 * @author Reginaldo Machado
 * @since 2019.06.08
 *
 */

public class CvcPrice {

	private BigDecimal adult;
	private BigDecimal child;
	
	
	public CvcPrice() {
		super();
	}

	/**
	 * @param bigDecimal
	 * @param bigDecimal2
	 */
	public CvcPrice(BigDecimal child, BigDecimal adult) {
		this.adult=adult;
		this.child=child;
		
	}

	public BigDecimal getAdult() {
		return adult;
	}

	public void setAdult(BigDecimal adult) {
		this.adult = adult;
	}

	public BigDecimal getChild() {
		return child;
	}

	public void setChild(BigDecimal child) {
		this.child = child;
	}

}