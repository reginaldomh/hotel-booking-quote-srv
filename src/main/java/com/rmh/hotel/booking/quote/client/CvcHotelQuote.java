package com.rmh.hotel.booking.quote.client;

import java.util.List;
/**
 * @author Reginaldo Machado
 * @since 2019.06.08
 *
 */
public class CvcHotelQuote {

	private Integer id;
	private String name;
	private Integer cityCode;
	private String cityName;
	private List<CvcRoom> rooms = null;
	
	
	public CvcHotelQuote() {
		super();
	}

	/**
	 * @param rooms2
	 */
	public CvcHotelQuote(List<CvcRoom> rooms) {
		this.rooms=rooms;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCityCode() {
		return cityCode;
	}

	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public List<CvcRoom> getRooms() {
		return rooms;
	}

	public void setRooms(List<CvcRoom> rooms) {
		this.rooms = rooms;
	}

}