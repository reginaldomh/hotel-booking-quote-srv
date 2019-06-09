package com.rmh.hotel.booking.quote.api.error;

/**
 * @author Reginaldo Machado
 * @since 2019.06.09
 *
 */

public class InvalidBookingDatesException extends RuntimeException {

	/**
	 * @param checkin
	 * @param string
	 */
	public InvalidBookingDatesException(String message) {
		super(message, null);
	}

}
