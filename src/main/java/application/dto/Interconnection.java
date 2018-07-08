package application.dto;

import java.util.List;

public class Interconnection {

	/**
	 * 
	 */
	private Integer stops;

	/**
	 * 
	 */
	private List<Legs> legs;

	/**
	 * @param stops
	 * @param legs
	 */
	public Interconnection(Integer stops, List<Legs> legs) {
		super();
		this.stops = stops;
		this.legs = legs;
	}

	public Interconnection() {
	}

	/**
	 * @return the stops
	 */
	public Integer getStops() {
		return stops;
	}

	/**
	 * @param stops the stops to set
	 */
	public void setStops(Integer stops) {
		this.stops = stops;
	}

	/**
	 * @return the legs
	 */
	public List<Legs> getLegs() {
		return legs;
	}

	/**
	 * @param legs the legs to set
	 */
	public void setLegs(List<Legs> legs) {
		this.legs = legs;
	}

	@Override
	public String toString() {
		return "Interconnection [stops=" + stops + ", legs=" + legs + "]";
	}

}
