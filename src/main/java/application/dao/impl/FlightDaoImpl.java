package application.dao.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.dao.FlightDao;
import application.dto.Route;
import application.dto.Schedule;

@Repository("flightDao")
public class FlightDaoImpl implements FlightDao {

	@Override
	public List<Route> getRoutes() throws Exception {

		List<Route> toret = new ArrayList<Route>();

		SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();

		ClientHttpResponse response = null;
		String data = null;
		try {
			ClientHttpRequest request = clientHttpRequestFactory
					.createRequest(new URI("https://api.ryanair.com/core/3/routes"), HttpMethod.GET);
			response = request.execute();
			data = StreamUtils.copyToString(response.getBody(), Charset.forName("UTF-8"));

		} catch (IOException | URISyntaxException e) {
			System.out.println("The data could not be recovered due to a problem : " + e.getMessage());
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (Exception e) {
					// Ignore
				}
			}
		}

		if (data != null) {

			ObjectMapper mapper = new ObjectMapper();
			try {
				toret.addAll(mapper.readValue(data, new TypeReference<List<Route>>() {
				}));
			} catch (Exception e) {
				System.out.println("The data could not be parsed due to a problem :" + e.getMessage());
			}

		}
		return toret;
	}

	@Override
	public Schedule getSchedule(String airportFrom, String airportTo, int year, int monthOfYear) throws Exception {

		Schedule toret = null;

		SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();

		ClientHttpResponse response = null;
		String data = null;
		try {

			String url = String.format("https://api.ryanair.com/timetable/3/schedules/%s/%s/years/%d/months/%d",
					airportFrom, airportTo, year, monthOfYear);
			System.out.println("url : " + url);
			ClientHttpRequest request = clientHttpRequestFactory.createRequest(new URI(url), HttpMethod.GET);
			response = request.execute();
			data = StreamUtils.copyToString(response.getBody(), Charset.forName("UTF-8"));

		} catch (IOException | URISyntaxException e) {
			System.out.println("The data could not be recovered due to a problem : " + e.getMessage());
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (Exception e) {
					// Ignore
				}
			}
		}

		if (data != null) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				toret = mapper.readValue(data, Schedule.class);
			} catch (Exception e) {
				System.out.println("The data could not be parsed due to a problem :" + e.getMessage());
			}

		}

		return toret;
	}

}
