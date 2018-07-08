package application.dto;

public class Legs {

	/**
	 * 
	 */
	private String departureAirport;

	/**
	 * 
	 */
	private String arrivalAirport;

	/**
	 * 
	 */
	private String departureDateTime;

	/**
	 * 
	 */
	private String arrivalDateTime;

	/**
	 * @param departureAirport
	 * @param arrivalAirport
	 * @param departureDateTime
	 * @param arrivalDateTime
	 */
	public Legs(String departureAirport, String arrivalAirport, String departureDateTime, String arrivalDateTime) {
		super();
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		this.departureDateTime = departureDateTime;
		this.arrivalDateTime = arrivalDateTime;
	}

	public Legs() {
	}

	/**
	 * @return the departureAirport
	 */
	public String getDepartureAirport() {
		return departureAirport;
	}

	/**
	 * @param departureAirport the departureAirport to set
	 */
	public void setDepartureAirport(String departureAirport) {
		this.departureAirport = departureAirport;
	}

	/**
	 * @return the arrivalAirport
	 */
	public String getArrivalAirport() {
		return arrivalAirport;
	}

	/**
	 * @param arrivalAirport the arrivalAirport to set
	 */
	public void setArrivalAirport(String arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	/**
	 * @return the departureDateTime
	 */
	public String getDepartureDateTime() {
		return departureDateTime;
	}

	/**
	 * @param departureDateTime the departureDateTime to set
	 */
	public void setDepartureDateTime(String departureDateTime) {
		this.departureDateTime = departureDateTime;
	}

	/**
	 * @return the arrivalDateTime
	 */
	public String getArrivalDateTime() {
		return arrivalDateTime;
	}

	/**
	 * @param arrivalDateTime the arrivalDateTime to set
	 */
	public void setArrivalDateTime(String arrivalDateTime) {
		this.arrivalDateTime = arrivalDateTime;
	}

	@Override
	public String toString() {
		return "Legs [departureAirport=" + departureAirport + ", arrivalAirport=" + arrivalAirport
				+ ", departureDateTime=" + departureDateTime + ", arrivalDateTime=" + arrivalDateTime + "]";
	}

}
