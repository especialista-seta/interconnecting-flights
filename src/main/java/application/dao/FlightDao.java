package application.dao;

import java.util.List;

import application.dto.Route;
import application.dto.Schedule;

public interface FlightDao {

	/**
	 * Gets all the routes
	 * 
	 * @return
	 * @throws Exception
	 */
	List<Route> getRoutes() throws Exception;

	/**
	 * Gets the schedule
	 * 
	 * @param airportFrom
	 * @param airportTo
	 * @param dayOfMonth
	 * @param monthOfYear
	 * @return
	 * @throws Exception
	 */
	Schedule getSchedule(String airportFrom, String airportTo, int year, int monthOfYear) throws Exception;

}
