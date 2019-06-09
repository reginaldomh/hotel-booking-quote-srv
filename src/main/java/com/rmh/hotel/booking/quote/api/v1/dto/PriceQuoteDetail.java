package com.rmh.hotel.booking.quote.api.v1.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Reginaldo Machado
 * @since 2019.06.08
 *
 */

public class PriceQuoteDetail {

	private BigDecimal pricePerDayAdult;
	private BigDecimal pricePerDayChild;

	/**
	 * @param pricePerDayAdult
	 * @param pricePerDayChild
	 */
	public PriceQuoteDetail(BigDecimal pricePerDayAdult, BigDecimal pricePerDayChild) {
		this.pricePerDayChild = pricePerDayChild.setScale(2, RoundingMode.HALF_EVEN);
		this.pricePerDayAdult = pricePerDayAdult.setScale(2, RoundingMode.HALF_EVEN);
	}

	public BigDecimal getPricePerDayAdult() {
		return pricePerDayAdult;
	}

	public void setPricePerDayAdult(BigDecimal pricePerDayAdult) {
		this.pricePerDayAdult = pricePerDayAdult;
	}

	public BigDecimal getPricePerDayChild() {
		return pricePerDayChild;
	}

	public void setPricePerDayChild(BigDecimal pricePerDayChild) {
		this.pricePerDayChild = pricePerDayChild;
	}

}