package com.rmh.hotel.booking.quote.client;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.rmh.hotel.booking.quote.api.error.ResourceNotFoundException;
import com.rmh.hotel.booking.quote.service.impl.BookingQuoteServiceImpl;

/**
 * @author Reginaldo Machado
 * @since 2019.06.08
 *
 * Endpoints to search booking quotes.
 */

@Component
public class CvcRestClient {
	
	private static Logger LOGGER = LoggerFactory.getLogger(CvcRestClient.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String URL = System.getenv().getOrDefault("CVC_HOST", "https://cvcbackendhotel.herokuapp.com");
	
	private static final String CITY_URL = URL+"/hotels/avail/";
	private static final String HOTEL_URL = URL+"/hotels/";
	
	/**
	 * Search for Hotel Detail in provided city
	 * 
	 * @param cityId Path param city identifier 
	 * @return Response with Hotel Details list
	 */
	@Cacheable("cvcRestResources" )
	public List<CvcHotelQuote> quoteInCity(Long cityId ) {
		LOGGER.info("INIT method=quoteInCity cityId="+cityId);

		CvcHotelQuote[] quotes = restTemplate.getForObject(CITY_URL+cityId,CvcHotelQuote[].class);
		
		LOGGER.info("RETURN CvcHotelQuote lenght="+quotes.length);
		return Arrays.asList(quotes);
	}

	/**
	 * Search for Hotel Detail in provided city
	 * 
	 * @param hotelId Path param hotel identifier 
	 * @return Response with Hotel Details
	 */
	@Cacheable("cvcRestResources" )
	public CvcHotelQuote[] quoteHotel(Long hotelId) {
		LOGGER.info("INIT method=quoteHotel hotelId="+hotelId);

		CvcHotelQuote[] quotes = restTemplate.getForObject(HOTEL_URL+hotelId,CvcHotelQuote[].class);
		
		LOGGER.info("RETURN CvcHotelQuote lenght="+quotes.length);
		return quotes;
	}

}
