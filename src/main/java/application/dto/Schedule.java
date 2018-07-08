/**
 * 
 */
package application.dto;

import java.util.List;

public class Schedule {

	/**
	 * 
	 */
	private Integer month;
	/**
	 * 
	 */
	private List<Day> days;

	/**
	 * 
	 */
	public Schedule() {
	}

	/**
	 * @return the month
	 */
	public Integer getMonth() {
		return month;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(Integer month) {
		this.month = month;
	}

	/**
	 * @return the days
	 */
	public List<Day> getDays() {
		return days;
	}

	/**
	 * @param days the days to set
	 */
	public void setDays(List<Day> days) {
		this.days = days;
	}

	@Override
	public String toString() {
		return "Schedule [month=" + month + ", days=" + days + "]";
	}

}
