package application.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import application.dto.Interconnection;
import application.service.FlightService;

@RestController
public class FlightController {


	@Autowired
	private FlightService flightService;


	@RequestMapping("/interconnections")
	public List<Interconnection> getInterconnections(
			@RequestParam(value = "departure", defaultValue = "departure") String departure,
			@RequestParam(value = "arrival", defaultValue = "arrival") String arrival,
			@RequestParam(value = "departureDateTime", defaultValue = "departureDateTime") String departureDateTime,
			@RequestParam(value = "arrivalDateTime", defaultValue = "arrivalDateTime") String arrivalDateTime) {

		List<Interconnection> toret = new ArrayList<Interconnection>();

		try {
			toret = flightService.getInterconnections(departure, arrival, departureDateTime, arrivalDateTime);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return toret;

	}
}
