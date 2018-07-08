package application.service;

import java.util.List;

import application.dto.Interconnection;

public interface FlightService {

	/**
	 * Gets the list of interconnections given a departure, location, departureDateTime and
	 * an arrivalDateTime
	 *
	 * @param departure
	 * @param arrival
	 * @param departureDateTime
	 * @param arrivalDateTime
	 * @return
	 * @throws Exception 
	 */
	List<Interconnection> getInterconnections(String departure, String arrival, String departureDateTime,
			String arrivalDateTime) throws Exception;

}
