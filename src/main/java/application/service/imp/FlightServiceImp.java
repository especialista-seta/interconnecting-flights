package application.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.dao.FlightDao;
import application.dto.Day;
import application.dto.Flight;
import application.dto.Interconnection;
import application.dto.Legs;
import application.dto.Route;
import application.dto.Schedule;
import application.service.FlightService;

@Service("flightService")
public class FlightServiceImp implements FlightService {

	@Autowired
	FlightDao flightDao;

	@Override
	public List<Interconnection> getInterconnections(String departure, String arrival, String departureDateTime,
			String arrivalDateTime) throws Exception {

		List<Interconnection> toret = new ArrayList<Interconnection>();

		String step = "";
		try {
			// Recover all the routes
			step = "Recover all the routes";
			List<Route> routes = flightDao.getRoutes();

			// Create the candidate routes
			step = "Create the candidate routes";
			Set<Route[]> connectingRoutes = createCandidateRoutes(departure, arrival, routes);

			// Filter by time
			step = "Filter by time";
			List<Flight[]> qualifiedFligths = getFlightsInTime(departure, arrival, connectingRoutes, departureDateTime,
					arrivalDateTime);

			// Build the interconnections to return
			toret = buildQualifiedInterconnections(departureDateTime, qualifiedFligths);

		} catch (Exception e) {
			System.out.println("Error in step : " + step + " error :" + e.getMessage());
		}

		return toret;
	}

	/**
	 * Gets all the flights connecting the arrival and the departure that matches
	 * the time restrictions
	 * 
	 * @param departure
	 * @param arrival
	 * @param connectingRoutes
	 * @param qualifiedFligths
	 * @param timeFormatter
	 * @param departureTimeJoda
	 * @param arrivalTimeJoda
	 * @throws Exception
	 */
	private List<Flight[]> getFlightsInTime(String departure, String arrival, Set<Route[]> connectingRoutes,
			String departureDateTime, String arrivalDateTime) throws Exception {

		List<Flight[]> toret = new ArrayList<Flight[]>();

		if (!connectingRoutes.isEmpty()) {

			DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("HH:mm");
			DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm");

			DateTime departureTimeJoda = formatter.parseDateTime(departureDateTime);
			DateTime arrivalTimeJoda = formatter.parseDateTime(arrivalDateTime);

			int departureDay = departureTimeJoda.getDayOfMonth();

			for (Route[] route : connectingRoutes) {
				if (route.length >= 1) {
					// Direct flight or first flight
					Route firstRoute = route[0];

					Schedule scheduleFirstRoute = flightDao.getSchedule(firstRoute.getAirportFrom(),
							firstRoute.getAirportTo(), departureTimeJoda.getYear(), departureTimeJoda.getMonthOfYear());

					if (scheduleFirstRoute != null && scheduleFirstRoute.getDays() != null
							&& !scheduleFirstRoute.getDays().isEmpty()) {

						for (Day dayFirstRoute : scheduleFirstRoute.getDays()) {

							if (dayFirstRoute.getDay() >= departureDay) {
								// Check the flights
								for (Flight firstFlight : dayFirstRoute.getFlights()) {
									// Check the times
									LocalTime dailyDepartureTime = timeFormatter
											.parseLocalTime(firstFlight.getDepartureTime());
									if ((dailyDepartureTime.getHourOfDay() > departureTimeJoda.getHourOfDay())
											&& (dailyDepartureTime.getMinuteOfHour() > departureTimeJoda
													.getMinuteOfHour())) {

										if (route.length == 1) {
											// Direct flight
											LocalTime dailyArrivalTime = timeFormatter
													.parseLocalTime(firstFlight.getArrivalTime());

											DateTime finalDepartureTime = new DateTime(departureTimeJoda.getYear(),
													departureTimeJoda.getMonthOfYear(), dayFirstRoute.getDay(),
													dailyArrivalTime.getHourOfDay(),
													dailyArrivalTime.getMinuteOfHour());

											if (finalDepartureTime.isBefore(arrivalTimeJoda)) {

												firstFlight.setDepartureAirport(departure);
												firstFlight.setArrivalAirport(arrival);
												firstFlight.setDay(dayFirstRoute.getDay());
												toret.add(new Flight[] { firstFlight });
											}

										} else if (route.length == 2) {
											// Connection
											Route secondRoute = route[1];

											Schedule scheduleSecondRoute = flightDao.getSchedule(
													secondRoute.getAirportFrom(), secondRoute.getAirportTo(),
													departureTimeJoda.getYear(), departureTimeJoda.getMonthOfYear());

											if (scheduleSecondRoute != null && scheduleSecondRoute.getDays() != null
													&& !scheduleSecondRoute.getDays().isEmpty()) {

												for (Day daySecondRoute : scheduleSecondRoute.getDays()) {

													if (daySecondRoute.getDay() >= dayFirstRoute.getDay()) {
														// Check the flights
														for (Flight secondFlight : daySecondRoute.getFlights()) {
															// Check the times
															LocalTime firstFlightArrivalTime = timeFormatter
																	.parseLocalTime(firstFlight.getArrivalTime());

															DateTime finalDepartureTime = new DateTime(
																	departureTimeJoda.getYear(),
																	departureTimeJoda.getMonthOfYear(),
																	daySecondRoute.getDay(),
																	firstFlightArrivalTime.getHourOfDay(),
																	firstFlightArrivalTime.getMinuteOfHour());

															LocalTime secondFlightDepartureTime = timeFormatter
																	.parseLocalTime(secondFlight.getDepartureTime());

															DateTime secondFlightFinalDepartureTime = new DateTime(
																	departureTimeJoda.getYear(),
																	departureTimeJoda.getMonthOfYear(),
																	daySecondRoute.getDay(),
																	secondFlightDepartureTime.getHourOfDay(),
																	secondFlightDepartureTime.getMinuteOfHour());

															LocalTime secondFlightArrivalTime = timeFormatter
																	.parseLocalTime(secondFlight.getArrivalTime());

															DateTime finalArrivalTime = new DateTime(
																	departureTimeJoda.getYear(),
																	departureTimeJoda.getMonthOfYear(),
																	daySecondRoute.getDay(),
																	secondFlightArrivalTime.getHourOfDay(),
																	secondFlightArrivalTime.getMinuteOfHour());

															if (secondFlightFinalDepartureTime
																	.isAfter(finalDepartureTime.plusHours(2))
																	&& finalArrivalTime.isBefore(arrivalTimeJoda)) {

																firstFlight.setDepartureAirport(
																		firstRoute.getAirportFrom());
																firstFlight
																		.setArrivalAirport(firstRoute.getAirportTo());
																secondFlight.setDepartureAirport(
																		secondRoute.getAirportFrom());
																secondFlight
																		.setArrivalAirport(secondRoute.getAirportTo());
																firstFlight.setDay(dayFirstRoute.getDay());
																secondFlight.setDay(daySecondRoute.getDay());

																toret.add(new Flight[] { firstFlight, secondFlight });
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		return toret;
	}

	/**
	 * Create the candidate routes
	 * 
	 * @param departure
	 * @param arrival
	 * @param routes
	 * @return
	 */
	private Set<Route[]> createCandidateRoutes(String departure, String arrival, List<Route> routes) {

		Set<Route[]> toret = new HashSet<Route[]>();

		if (!routes.isEmpty()) {

			// Filter the routes
			List<Route> candidatesFrom = new ArrayList<Route>();
			Map<String, Route> candidatesTo = new HashMap<String, Route>();
			for (Route r : routes) {

				if (r.getAirportFrom().equalsIgnoreCase(departure)) {

					// If is a direct flight
					if (r.getAirportTo().equalsIgnoreCase(arrival)) {
						toret.add(new Route[] { r });
					}
					// If a route starts from the given airport is a candidate
					else {
						candidatesFrom.add(r);
					}
				}
				// If a route ends at the given airport is also a candidate
				else if (r.getAirportTo().equalsIgnoreCase(arrival)) {
					candidatesTo.put(r.getAirportFrom(), r);
				}
			}

			// Now check the candidates
			for (Route candidateFrom : candidatesFrom) {
				if (candidatesTo.containsKey(candidateFrom.getAirportTo())) {
					// We have a route with 1 stop
					toret.add(new Route[] { candidateFrom, candidatesTo.get(candidateFrom.getAirportTo()) });
				}
			}
		}

		return toret;
	}

	/**
	 * Builds the interconnections
	 * 
	 * @param departureDateTime
	 * @param toret
	 * @param qualifiedFligths
	 */
	private List<Interconnection> buildQualifiedInterconnections(String departureDateTime,
			List<Flight[]> qualifiedFligths) {

		List<Interconnection> toret = new ArrayList<Interconnection>();

		if (!qualifiedFligths.isEmpty()) {
			for (Flight[] flights : qualifiedFligths) {
				// For each flight/pair of flights we create an interconnection
				toret.add(createInterconnection(departureDateTime, flights));
			}
		}

		return toret;
	}

	/**
	 * Creates an interconnection
	 * 
	 * @param formatter
	 * @param timeFormatter
	 * @param departureTimeJoda
	 * @param flights
	 * @return
	 */
	private Interconnection createInterconnection(String departureDateTime, Flight[] flights) {

		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm");
		DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("HH:mm");

		DateTime departureTimeJoda = formatter.parseDateTime(departureDateTime);

		Interconnection interconnection = new Interconnection();
		interconnection.setStops(flights.length - 1);
		List<Legs> legs = new ArrayList<Legs>();
		for (Flight flight : flights) {
			Legs leg = new Legs();
			leg.setArrivalAirport(flight.getArrivalAirport());
			leg.setDepartureAirport(flight.getDepartureAirport());

			LocalTime departureTime = timeFormatter.parseLocalTime(flight.getDepartureTime());
			DateTime departureLeg = new DateTime(departureTimeJoda.getYear(), departureTimeJoda.getMonthOfYear(),
					flight.getDay(), departureTime.getHourOfDay(), departureTime.getMinuteOfHour());
			leg.setDepartureDateTime(departureLeg.toString(formatter));

			LocalTime arrivalTime = timeFormatter.parseLocalTime(flight.getArrivalTime());
			DateTime arrivalLeg = new DateTime(departureTimeJoda.getYear(), departureTimeJoda.getMonthOfYear(),
					flight.getDay(), arrivalTime.getHourOfDay(), arrivalTime.getMinuteOfHour());
			leg.setArrivalDateTime(arrivalLeg.toString(formatter));

			legs.add(leg);
		}
		interconnection.setLegs(legs);
		return interconnection;
	}

}
