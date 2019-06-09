package com.rmh.hotel.booking.quote.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.rmh.hotel.booking.quote.api.error.InvalidBookingDatesException;
import com.rmh.hotel.booking.quote.api.error.InvalidNumerOfGuestsException;
import com.rmh.hotel.booking.quote.api.error.ResourceNotFoundException;
import com.rmh.hotel.booking.quote.api.v1.dto.BookingQuote;
import com.rmh.hotel.booking.quote.client.CvcHotelQuote;
import com.rmh.hotel.booking.quote.client.CvcPrice;
import com.rmh.hotel.booking.quote.client.CvcRestClient;
import com.rmh.hotel.booking.quote.client.CvcRoom;
import com.rmh.hotel.booking.quote.service.impl.BookingQuoteServiceImpl;


/**
 * @author Reginaldo Machado
 * @since 2019.06.09
 *
 * Tests for BookingQuoteService
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BopokingQuoteServiceTest {

	
	@InjectMocks
	BookingQuoteServiceImpl service;

	@Mock
	CvcRestClient cvcClient;

	@Rule
    public ExpectedException exceptionRule = ExpectedException.none();

	
	@Test
	public void whenGuestsNumberIsLessThrowException() {
		LocalDate today = LocalDate.now();
		LocalDate checkout = today.plusDays(1);
		exceptionRule.expect(InvalidNumerOfGuestsException.class);
		service.quoteHotelsFromCity(1L, today, checkout, 0, 0);
	}
	
	@Test
	public void whenClientsCityEndpointReturnSomeResultCalculateTotalPrice() {
		LocalDate today = LocalDate.now();
		LocalDate checkout = today.plusDays(1);
		when(cvcClient.quoteInCity(1L)).thenReturn(mockCvCCityEndpointOkResult());
		List<BookingQuote> quoteHotelsFromCity = service.quoteHotelsFromCity(1L, today, checkout, 2, 2);
		BigDecimal totalPrice = quoteHotelsFromCity.get(0).getRooms().get(0).getTotalPrice();
		assertEquals(new BigDecimal(428.58D).setScale(2, RoundingMode.HALF_EVEN),totalPrice);
	}

	@Test
	public void whenCheckinIsBeforeTodayThrowException() {
		LocalDate today = LocalDate.now();
		LocalDate checkin = today.minusDays(1);
		exceptionRule.expect(InvalidBookingDatesException.class);
		service.quoteHotelsFromCity(1L, checkin, today, 1, 1);
	}
	
	@Test
	public void whenCheckoutIsBeforeTodayThrowException() {
		LocalDate today = LocalDate.now();
		LocalDate checkout = today.minusDays(1);
		exceptionRule.expect(InvalidBookingDatesException.class);
		service.quoteHotelsFromCity(1L, today, checkout, 1, 1);
	}
	
	@Test
	public void whenClientReturnNoResultThrowException() {
		LocalDate today = LocalDate.now();
		LocalDate checkout = today.plusDays(1);
		when(cvcClient.quoteInCity(1L)).thenReturn(new ArrayList<CvcHotelQuote>());
		exceptionRule.expect(ResourceNotFoundException.class);
		service.quoteHotelsFromCity(1L, today, checkout, 1, 1);
	}

	
	private List<CvcHotelQuote> mockCvCCityEndpointOkResult() {
		List<CvcHotelQuote> cvcHotelQuotes = new ArrayList<CvcHotelQuote>();
		List<CvcRoom> rooms = new ArrayList<CvcRoom>();
		rooms.add(new CvcRoom(new CvcPrice(new BigDecimal(50),new BigDecimal(100))));
		cvcHotelQuotes.add(new CvcHotelQuote(rooms));
		return cvcHotelQuotes;
	}

}
