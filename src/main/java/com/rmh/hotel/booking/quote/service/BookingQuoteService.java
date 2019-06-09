package com.rmh.hotel.booking.quote.service;

import java.time.LocalDate;
import java.util.List;

import com.rmh.hotel.booking.quote.api.v1.dto.BookingQuote;

/**
 * @author Reginaldo Machado
 * @since 2019.06.08
 *
 * Service for quoting bookings on broker partner
 */

public interface BookingQuoteService {
	
	public List<BookingQuote> quoteHotelsFromCity(Long cityId, LocalDate checkin,LocalDate checkout, Integer qtAdult, Integer qtChild);
	
	public BookingQuote quoteHotel(Long hotelId, LocalDate checkin,LocalDate checkout, Integer qtAdult,Integer qtChild);

}
