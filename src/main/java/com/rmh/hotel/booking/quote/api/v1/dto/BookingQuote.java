package com.rmh.hotel.booking.quote.api.v1.dto;

import java.util.List;

import com.rmh.hotel.booking.quote.client.CvcRoom;

/**
 * @author Reginaldo Machado
 * @since 2019.06.08
 *
 */

public class BookingQuote {

	private Integer id;
	private String cityName;
	private List<RoomQuote> rooms;

	/**
	 * @param id
	 * @param cityName
	 */
	public BookingQuote(Integer id, String cityName,List<RoomQuote> rooms) {
		this.id = id;
		this.cityName=cityName;
		this.rooms = rooms;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public List<RoomQuote> getRooms() {
		return rooms;
	}

	public void setRooms(List<RoomQuote> rooms) {
		this.rooms = rooms;
	}
	
}