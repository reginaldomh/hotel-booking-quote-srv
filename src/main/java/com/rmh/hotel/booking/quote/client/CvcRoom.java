package com.rmh.hotel.booking.quote.client;
/**
 * @author Reginaldo Machado
 * @since 2019.06.08
 *
 */
public class CvcRoom {

	private Long roomID;
	private String categoryName;
	private CvcPrice price;

	
	public CvcRoom() {
		super();
	}

	/**
	 * @param cvcPrice
	 */
	public CvcRoom(CvcPrice price) {
		this.price=price;
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

	public CvcPrice getPrice() {
		return price;
	}

	public void setPrice(CvcPrice price) {
		this.price = price;
	}
}