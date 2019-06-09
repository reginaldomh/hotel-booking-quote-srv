package com.rmh.hotel.booking.quote.api.error;

/**
 * @author Reginaldo Machado
 * @since 2019.06.09
 *
 */

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * @param message
	 */
	public ResourceNotFoundException(String message) {
		super(message);
	}

}
