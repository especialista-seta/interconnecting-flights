package application.dto;

import java.util.List;

public class Day {

	/**
	 * 
	 */
	private Integer day;

	/**
	 * 
	 */
	private List<Flight> flights;

	/**
	 * 
	 */
	public Day() {
	}

	/**
	 * @return the day
	 */
	public Integer getDay() {
		return day;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(Integer day) {
		this.day = day;
	}

	/**
	 * @return the flights
	 */
	public List<Flight> getFlights() {
		return flights;
	}

	/**
	 * @param flights the flights to set
	 */
	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}

	@Override
	public String toString() {
		return "Day [day=" + day + ", flights=" + flights + "]";
	}

}
