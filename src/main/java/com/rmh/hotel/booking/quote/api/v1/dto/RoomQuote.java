package com.rmh.hotel.booking.quote.api.v1.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
/**
 * @author Reginaldo Machado
 * @since 2019.06.08
 *
 */

public class RoomQuote {

	private Long roomID;
	private String categoryName;
	private BigDecimal totalPrice;
	private PriceQuoteDetail priceDetail;
	
	/**
	 * @param roomID2
	 * @param categoryName2
	 * @param priceQuoteDetail
	 * @param bigDecimal
	 */

	public RoomQuote(Long roomID, String categoryName, BigDecimal totalPrice, PriceQuoteDetail priceDetail) {
		this.roomID = roomID;
		this.categoryName = categoryName;
		this.totalPrice = totalPrice.setScale(2, RoundingMode.HALF_EVEN);
		this.priceDetail = priceDetail;
	}
	
	public Long getRoomID() {
		return roomID;
	}

	public void setRoomID(Long roomID) {
		this.roomID = roomID;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public PriceQuoteDetail getPriceDetail() {
		return priceDetail;
	}
	public void setPriceDetail(PriceQuoteDetail priceDetail) {
		this.priceDetail = priceDetail;
	}

		
}
