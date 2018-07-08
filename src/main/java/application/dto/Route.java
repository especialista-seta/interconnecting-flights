package application.dto;

public class Route {

	/**
	 * 
	 */
	private String airportFrom;
	/**
	 * 
	 */
	private String airportTo;
	/**
	 * 
	 */
	private String connectingAirport;
	/**
	 * 
	 */
	private Boolean newRoute;
	/**
	 * 
	 */
	private Boolean seasonalRoute;
	/**
	 * 
	 */
	private String group;
	/**
	 * 
	 */
	private String operator;

	/**
	 * @return the airportFrom
	 */
	public String getAirportFrom() {
		return airportFrom;
	}

	/**
	 * 
	 */
	public Route() {
	}

	/**
	 * @param airportFrom
	 * @param airportTo
	 * @param connectingAirport
	 * @param newRoute
	 * @param seasonalRoute
	 * @param group
	 * @param operator
	 */
	public Route(String airportFrom, String airportTo, String connectingAirport, Boolean newRoute,
			Boolean seasonalRoute, String group, String operator) {
		super();
		this.airportFrom = airportFrom;
		this.airportTo = airportTo;
		this.connectingAirport = connectingAirport;
		this.newRoute = newRoute;
		this.seasonalRoute = seasonalRoute;
		this.group = group;
		this.operator = operator;
	}

	/**
	 * @param airportFrom the airportFrom to set
	 */
	public void setAirportFrom(String airportFrom) {
		this.airportFrom = airportFrom;
	}

	/**
	 * @return the airportTo
	 */
	public String getAirportTo() {
		return airportTo;
	}

	/**
	 * @param airportTo the airportTo to set
	 */
	public void setAirportTo(String airportTo) {
		this.airportTo = airportTo;
	}

	/**
	 * @return the connectingAirport
	 */
	public String getConnectingAirport() {
		return connectingAirport;
	}

	/**
	 * @param connectingAirport the connectingAirport to set
	 */
	public void setConnectingAirport(String connectingAirport) {
		this.connectingAirport = connectingAirport;
	}

	/**
	 * @return the newRoute
	 */
	public Boolean getNewRoute() {
		return newRoute;
	}

	/**
	 * @param newRoute the newRoute to set
	 */
	public void setNewRoute(Boolean newRoute) {
		this.newRoute = newRoute;
	}

	/**
	 * @return the seasonalRoute
	 */
	public Boolean getSeasonalRoute() {
		return seasonalRoute;
	}

	/**
	 * @param seasonalRoute the seasonalRoute to set
	 */
	public void setSeasonalRoute(Boolean seasonalRoute) {
		this.seasonalRoute = seasonalRoute;
	}

	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Route [airportFrom=" + airportFrom + ", airportTo=" + airportTo + ", connectingAirport="
				+ connectingAirport + ", newRoute=" + newRoute + ", seasonalRoute=" + seasonalRoute + ", group=" + group
				+ ", operator=" + operator + "]";
	}

}
