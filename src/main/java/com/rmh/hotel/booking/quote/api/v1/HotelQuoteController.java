package com.rmh.hotel.booking.quote.api.v1;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rmh.hotel.booking.quote.api.v1.dto.BookingQuote;
import com.rmh.hotel.booking.quote.service.impl.BookingQuoteServiceImpl;

/**
 * @author Reginaldo Machado
 * @since 2019.06.08
 *
 * Endpoints to search booking quotes.
 */

@RestController
@RequestMapping("rest/v1/")
public class HotelQuoteController {
	
	@Autowired
	private BookingQuoteServiceImpl service;
	
	/**
	 * Search for booking quotes of provided city
	 * 
	 * @param cityId Path param city identifier 
	 * @param qtChild Query param number of child guests
	 * @param qtAdult Query param number of adult guests
	 * @param checkout Query param checkin date
	 * @param checkin  Query para 
	 * @return Response with Booking Quote list result
	 */
	@GetMapping(path = "/cities/{cityId}/hotels/quotes")
	public ResponseEntity<List<BookingQuote>> quoteHotelsFromCity(@PathVariable("cityId") Long cityId,
			@RequestParam("checkin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkin,
			@RequestParam("checkout") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkout,
			@RequestParam @Min(0) Integer qtAdult, @RequestParam @Min(0) Integer qtChild) {

		List<BookingQuote> BookingQuotes = service.quoteHotelsFromCity(cityId,checkin,checkout, qtAdult, qtChild);

		return new ResponseEntity<List<BookingQuote>>(BookingQuotes,HttpStatus.OK);

	}


	/**
	 * Get Booking quote of provided hotel
	 * 
	 * @param hotelId Path param city identifier 
	 * @param qtChild Query param number of child guests
	 * @param qtAdult Query param number of adult guests
	 * @param checkout Query param checkin date
	 * @param checkin  Query para 
	 * @return Response with Booking Quote requested
	 */
	@GetMapping(path = "/hotels/{hotelId}/quotes")
	public ResponseEntity<BookingQuote> quoteHotel(@PathVariable("hotelId") Long hotelId,
			@RequestParam("checkin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkin,
			@RequestParam("checkout") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkout,
			@RequestParam @Min(0) Integer qtAdult, @RequestParam @Min(0) Integer qtChild) {
	
		BookingQuote bookingQuote = service.quoteHotel(hotelId,checkin,checkout,qtAdult, qtChild);
		
		return new ResponseEntity<BookingQuote>(bookingQuote,HttpStatus.OK);
	}

}
