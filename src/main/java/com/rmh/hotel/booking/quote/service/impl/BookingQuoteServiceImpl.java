package com.rmh.hotel.booking.quote.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmh.hotel.booking.quote.api.error.InvalidBookingDatesException;
import com.rmh.hotel.booking.quote.api.error.InvalidNumerOfGuestsException;
import com.rmh.hotel.booking.quote.api.error.ResourceNotFoundException;
import com.rmh.hotel.booking.quote.api.v1.dto.BookingQuote;
import com.rmh.hotel.booking.quote.api.v1.dto.PriceQuoteDetail;
import com.rmh.hotel.booking.quote.api.v1.dto.RoomQuote;
import com.rmh.hotel.booking.quote.client.CvcHotelQuote;
import com.rmh.hotel.booking.quote.client.CvcRestClient;
import com.rmh.hotel.booking.quote.client.CvcRoom;
import com.rmh.hotel.booking.quote.service.BookingQuoteService;

/**
 * @author Reginaldo Machado
 * @since 2019.06.08
 *
 * Service for quoting bookings on broker partner
 */
@Service
public class BookingQuoteServiceImpl implements BookingQuoteService {

	
	/**
	 * Fee to apply on prices 
	 */
	private static final BigDecimal FEE = new BigDecimal(0.7);
	
	
	@Autowired
	private CvcRestClient cvcRestClient;
	
	/**
	 * @Quote rooms for provided hotel and calculate final price
	 * 
	 * @param hotelId
	 * @param qtChild 
	 * @param qtAdult 
	 * @param checkout 
	 * @param checkin 
	 * @param days 
	 * @return BookingQuote for provided hotel id with final prices
	 */
	public BookingQuote quoteHotel(Long hotelId,LocalDate checkin,LocalDate checkout, Integer qtAdult,Integer qtChild) {
		validate(checkin, checkout, qtAdult, qtChild);
		Long days = ChronoUnit.DAYS.between(checkin, checkout) - 1;
		CvcHotelQuote[] cvcQuotes = cvcRestClient.quoteHotel(hotelId);
		BookingQuote result;
		if(cvcQuotes!=null && cvcQuotes.length>0) {
			 result = proccessCvcHotelQuote(days, qtAdult, qtChild,cvcQuotes[0]);
		}else {
			throw new ResourceNotFoundException("Hotel not found");
		}
		
		return result;
	}

	/**
	 *  Quote rooms for provided city and calculate final price
	 * 
	 * @param cityId city id
	 * @param qtChild number of child guests
	 * @param qtAdult number of adult guests
	 * @param checkin checkin date
	 * @param checkout checkout date 
	 * @return List of BookingQuote for provided cityId with final prices
	 */
	public List<BookingQuote> quoteHotelsFromCity(Long cityId, LocalDate checkin,LocalDate checkout, Integer qtAdult, Integer qtChild) {
		validate(checkin, checkout, qtAdult, qtChild);
		Long days = ChronoUnit.DAYS.between(checkin, checkout);
		List<CvcHotelQuote> hotelQuotesCvc = cvcRestClient.quoteInCity(cityId);
		List<BookingQuote> result = new ArrayList<BookingQuote>();
		if (hotelQuotesCvc != null && hotelQuotesCvc.size() > 0) {
		
			hotelQuotesCvc.parallelStream().forEach(hotelQuoteCvc -> {
				
				result.add(proccessCvcHotelQuote(days, qtAdult, qtChild, hotelQuoteCvc));
				
			});
		}else {
			throw new ResourceNotFoundException("Quotes not exist for city "+cityId);
		}

		return result;

	}

	/**
	 * Create Response models with final price 
	 * 
	 * @param days: number of days to stay 
 	 * @param qtChild:number of child guests
	 * @param qtAdult: number of adult guests
	 * @param result
	 * @param hotelQuoteCvc
	 * @return BookingQuote with final price
	 */
	private BookingQuote proccessCvcHotelQuote(Long days, Integer qtAdult, Integer qtChild, CvcHotelQuote hotelQuoteCvc) {
		
		List<RoomQuote> rooms = calculateRoomPrice(hotelQuoteCvc.getRooms(), days,qtAdult,qtChild);
		BookingQuote bookingQuote = new BookingQuote(hotelQuoteCvc.getId(), hotelQuoteCvc.getCityName(),rooms);
		return bookingQuote;
	}

	/**
	 *  Calculate room final prices
	 * 
	 * @param rooms list of hotel rooms to calculate final price
 	 * @param qtChild number of child guests
	 * @param qtAdult number of adult guests
	 * @param days numer of days
	 * @return List of RoomQuote with the final price
	 */
	private List<RoomQuote> calculateRoomPrice(List<CvcRoom> rooms, Long days, Integer qtAdult, Integer qtChild) {
		List<RoomQuote> roomQuotes = new ArrayList<RoomQuote>();
		rooms.parallelStream().forEach(cvcRoom -> {

			BigDecimal adultDayPrice = calculateFees(cvcRoom.getPrice().getAdult());
			BigDecimal childDayPrice = calculateFees(cvcRoom.getPrice().getChild());
			BigDecimal adultTotalPrice = adultDayPrice.multiply(new BigDecimal(qtAdult));
			BigDecimal childTotalPrice = childDayPrice.multiply(new BigDecimal(qtChild));
			BigDecimal totalPrice = adultTotalPrice.add(childTotalPrice).multiply(new BigDecimal(days));
			
			PriceQuoteDetail priceQuoteDetail = new PriceQuoteDetail(adultDayPrice, childDayPrice);
			RoomQuote quote = new RoomQuote(cvcRoom.getRoomID(),cvcRoom.getCategoryName(),totalPrice,priceQuoteDetail);
			roomQuotes.add(quote);
		});
		return roomQuotes;
	}

	
	/**
	 * Apply Fee on price
	 * 
	 * @param price CvcHotel room price.
	 * @return price with fee 
	 */
	private BigDecimal calculateFees(BigDecimal price) {
		return price.divide(FEE,2, RoundingMode.HALF_EVEN);
	}

	/**
	 * Validade inputs and throw exception if invalid.
	 * 
	 * @param checkin
	 * @param checkout
	 * @param qtChild number of child guests
	 * @param qtAdult number of adult guests
	 */
	private void validate(LocalDate checkin, LocalDate checkout, Integer qtAdult, Integer qtChild) {
		LocalDate today = LocalDate.now();

		if (checkin.isAfter(checkout)) {
			throw new InvalidBookingDatesException("Checkin is after checkout.");
		}
		if (checkout.isBefore(today)) {
			throw new InvalidBookingDatesException("Checkout is before today.");
		}
		if (checkin.isBefore(today)) {
			throw new InvalidBookingDatesException("Checkin is before today");
		}
		if (qtAdult + qtChild == 0) {
			throw new InvalidNumerOfGuestsException("Must have at least one guest");
		}

	}
	
}
